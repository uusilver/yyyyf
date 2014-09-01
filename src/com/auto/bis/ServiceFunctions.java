package com.auto.bis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.auto.msg.resp.Article;
import com.auto.msg.resp.NewsMessage;
import com.auto.msg.resp.TextMessage;
import com.auto.util.DBUtils;
import com.auto.util.InforPool;
import com.auto.util.MessageUtil;

public class ServiceFunctions {

	/** 
	 * 悠悠银艺坊。
	 * @author Junying Li
	 * @date 2014-08-05
	 * @return 
	 */  
	public static String getMainMenu() {  
	    StringBuffer buffer = new StringBuffer();  
	    buffer.append("您好，我是小悠，感谢您的关注：").append("\n\n");  
//	    buffer.append("3  周边搜索").append("\n");  
//	    buffer.append("4  歌曲点播").append("\n");  
//	    buffer.append("5  经典游戏").append("\n");  
//	    buffer.append("6  美女电台").append("\n");  
//	    buffer.append("7  人脸识别").append("\n");  
//	    buffer.append("8  聊天唠嗑").append("\n\n");  
//	    buffer.append("回复“?”显示此帮助菜单");  
	    return buffer.toString();  
	} 
	//获得当月推荐
	public static String getNewsMessage(NewsMessage newsMessage, String content){
		List<Article> articleList = new ArrayList<Article>();  
		if ("13".equals(content)) { 
			Article article = InforPool.getMonthRecFromPool("month");
			articleList.add(article);  
            newsMessage.setArticleCount(articleList.size());  
            newsMessage.setArticles(articleList);  
            return MessageUtil.newsMessageToXml(newsMessage);  
        }//end of if  
		return null;
	}
	//获得品牌介绍
	public static String getBrandNewsMessage(NewsMessage newsMessage, String content){
		List<Article> articleList = new ArrayList<Article>();  
		if ("11".equals(content)) { 
			Article article = InforPool.getBrandNewsFromPool("brand");
			articleList.add(article);  
            newsMessage.setArticleCount(articleList.size());  
            newsMessage.setArticles(articleList);  
            return MessageUtil.newsMessageToXml(newsMessage);  
        }//end of if  
		return "未知错误！";
	}
	//获得银领时尚里的内容
	public static String getDailyNewsMessage(NewsMessage newsMessage, String content){
		List<Article> articleList = new ArrayList<Article>();  
		if ("21".equals(content)) { 
			articleList = InforPool.getFashionNewsFromPool("today",1);
            newsMessage.setArticleCount(articleList.size());  
            newsMessage.setArticles(articleList);  
            return MessageUtil.newsMessageToXml(newsMessage);  
        }//end of if
		else if("22".equals(content)){
			//2表示回顾往期的刊数
			articleList = InforPool.getFashionNewsFromPool("all",3);
            newsMessage.setArticleCount(articleList.size());  
            newsMessage.setArticles(articleList);  
            return MessageUtil.newsMessageToXml(newsMessage); 
		}
		return null;
	}
	//按钮状态处理
	public static String getTextMessage(TextMessage textMessage, String content){
		if("12".equals(content)){
			//String str = "当前全场满100减5元,满200减10元！";
			textMessage.setContent(InforPool.getNewsFromPool("12"));
			return MessageUtil.textMessageToXml(textMessage); 
		}else if("14".equals(content)){
			//自助客服
			//String str = "您好，客服小悠很高兴为您服务！回复“1”查看包邮地区；回复“2”查看最新优惠";
			textMessage.setContent(InforPool.getNewsFromPool("14"));
			return MessageUtil.textMessageToXml(textMessage); 
		}else if("15".equals(content)){
			//推广码
			//String str = "您好，悠悠银艺坊欢迎您的加入，获得推广码，邀请小伙伴们消费后即可获得丰厚返利，推广越多，返利越多！回复#ZQ#姓名#手机号,立即获得推广码！例:#ZQ#张三#13841213245";
			textMessage.setContent(InforPool.getNewsFromPool("15"));
			return MessageUtil.textMessageToXml(textMessage); 
		}else if("00".equals(content)){
			//String str = "包邮地区仅限江浙沪！全场满300全国包邮！";
			textMessage.setContent(InforPool.getNewsFromPool("00"));
			return MessageUtil.textMessageToXml(textMessage); 
		}
		return null;
	}
	//进行邀请码的获取，保存
	public static String getAgentCode(String content){
		String strss = "内容错误请检查！请您仔细阅读参考示例";
		if(content.startsWith("#")){
        	String[] str = content.split("#");
        	try{
            	if(str[1].equalsIgnoreCase("ZQ")){
            		if(str[3].length()!=11){
            			return "手机格式错误，请检查";
            		}
            		Connection conn = null;
            		PreparedStatement ps = null;
            		ResultSet rs = null;
            		try{
	            		conn = DBUtils.getConnection();
	            		String sql = "INSERT INTO m_agent_tb (A_NAME, A_TELNO, A_ADDDATE, A_EDITDATE) VALUES (?,?,?,?)";
	            		ps = conn.prepareStatement(sql);
	            		ps.setString(1, str[2]);
	            		ps.setString(2, str[3]);
	            		java.util.Date date=new Date(); 
	            		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); 
	            		ps.setDate(3, sqlDate);
	            		ps.setDate(4, sqlDate);
	            		ps.executeUpdate();
	            		ps.clearBatch();
	            		rs = ps.executeQuery("SELECT MAX(ID) FROM m_agent_tb");
	            		if(rs.next()){
	            			String chars = "abcdefghijklmnopqrstuvwxyz";
	            			String extraCode = String.valueOf(chars.charAt((int)(Math.random() * 26)))+String.valueOf(chars.charAt((int)(Math.random() * 26)));
	            			String agentID = String.valueOf(rs.getInt(1));
	            			String agentCode = agentID+extraCode;
	            			sql = "INSERT INTO m_agent_code(AGENT_ID,AGENT_CODE) VALUES (?,?)";
	            			ps.clearBatch();
	            			ps = conn.prepareStatement(sql);
	            			ps.setString(1, agentID);
	            			ps.setString(2, agentCode);
	            			ps.executeUpdate();
	            			return "恭喜您！申请成功！您的推广码是：>"+agentCode+"<(不包含><符) 请回复#ZH#推广码#支付宝或者财付通#账号 来绑定您的返利账号，例：#ZH#8wq#支付宝#1234567";
	            		}
            		}catch(Exception e){
            			System.out.println(e.getMessage());
            			return strss;
            			//return "系统错误，请稍后再试!";
            		}finally{
            			DBUtils.free(conn, ps, rs);
            		}
            	}//获得邀请码分支
            	if(str[1].equalsIgnoreCase("ZH")){
            		Connection conn = null;
            		PreparedStatement ps = null;
            		conn = DBUtils.getConnection();
            		String sql = "UPDATE m_agent_code SET AGENT_ACCOUNT=?, AGENT_ACCOUNT_TYPE=? where AGENT_CODE=?";
            		ps=conn.prepareStatement(sql);
            		ps.setString(1, str[4]);
            		ps.setString(2, str[3]);
            		ps.setString(3, str[2]);
            		ps.executeUpdate();
            		return "恭喜账号绑定成功！祝您获利多多！";
            		
            	}//绑定支付宝或者财付通账号
            	else{
            		return strss; 
            	}
        	}catch(Exception e){
        		System.out.println(e.getMessage());
        		return e.getMessage();
        	}
        	
        }else{
        	return "未知请求！请确认格式！";
        }
	}
	//根据键值获得对应的相应内容
	
	//根据传入的键值获得对应的内容
	public static String emoji(int hexEmoji) {  
        return String.valueOf(Character.toChars(hexEmoji));  
    }  
}
