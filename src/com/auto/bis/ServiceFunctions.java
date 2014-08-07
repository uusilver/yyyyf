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
	
	public static String getNewsMessage(NewsMessage newsMessage, String content){
		List<Article> articleList = new ArrayList<Article>();  
		if ("13".equals(content)) {  
            Article article = new Article();  
            article.setTitle("九月主打");  
            // 图文消息中可以使用QQ表情、符号表情  
            article.setDescription("悠悠银艺坊，开学季主打！\n\n蝴蝶款耳钉。\n\n青春如霓裳翩舞的蝴蝶，青春就该绽放！");  
            // 将图片置为空  
            article.setPicUrl("http://wd.geilicdn.com/vshop692450-1394027937-5.jpg?w=480&h=0");  
            article.setUrl("http://wd.koudai.com/item.html?itemID=21169354");  
            articleList.add(article);  
            newsMessage.setArticleCount(articleList.size());  
            newsMessage.setArticles(articleList);  
            return MessageUtil.newsMessageToXml(newsMessage);  
        }  
		return null;
	}
	//按钮状态处理
	public static String getTextMessage(TextMessage textMessage, String content){
		if("12".equals(content)){
			String str = "当前全场满100减5元,满200减10元！";
			textMessage.setContent(str);
			return MessageUtil.textMessageToXml(textMessage); 
		}else if("14".equals(content)){
			String str = "您好，客服小悠很高兴为您服务！回复“1”查看包邮地区；回复“2”查看最新优惠";
			textMessage.setContent(str);
			return MessageUtil.textMessageToXml(textMessage); 
		}else if("15".equals(content)){
			String str = "您好，悠悠银艺坊欢迎您的加入，获得推广码，邀请小伙伴们消费后即可获得丰厚返利，推广越多，返利越多！回复#ZQ#姓名#手机号,立即获得推广码！例:#ZQ#张三#13841213245";
			textMessage.setContent(str);
			return MessageUtil.textMessageToXml(textMessage); 
		}else if("00".equals(content)){
			String str = "包邮地区仅限江浙沪！全场满300全国包邮！";
			textMessage.setContent(str);
			return MessageUtil.textMessageToXml(textMessage); 
		}
		return null;
	}
	//进行邀请码的获取，保存
	public static String getAgentCode(String content){
		String strss = "格式错误请检查！格式:#ZQ#姓名#手机号，例子：#ZQ#张三#13841213245";
		if(content.startsWith("#")){
        	String[] str = content.split("#");
        	try{
        		if(str[3].length()!=11){
        			return "手机格式错误，请检查";
        		}
            	if(str[1].equalsIgnoreCase("ZQ")){
            		Connection conn = null;
            		PreparedStatement ps = null;
            		ResultSet rs = null;
            		try{
	            		conn = DBUtils.getConnection();
	            		String sql = "INSERT INTO M_AGENT_TB (A_NAME, A_TELNO, A_ADDDATE, A_EDITDATE) VALUES (?,?,?,?)";
	            		ps = conn.prepareStatement(sql);
	            		ps.setString(1, str[2]);
	            		ps.setString(2, str[3]);
	            		java.util.Date date=new Date(); 
	            		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); 
	            		ps.setDate(3, sqlDate);
	            		ps.setDate(4, sqlDate);
	            		ps.executeUpdate();
	            		ps.clearBatch();
	            		rs = ps.executeQuery("SELECT MAX(ID) FROM M_AGENT_TB");
	            		if(rs.next()){
	            			String chars = "abcdefghijklmnopqrstuvwxyz";
	            			String extraCode = String.valueOf(chars.charAt((int)(Math.random() * 26)))+String.valueOf(chars.charAt((int)(Math.random() * 26)));
	            			String agentID = String.valueOf(rs.getInt(1));
	            			String agentCode = agentID+extraCode;
	            			sql = "INSERT INTO M_AGENT_CODE(AGENT_ID,AGENT_CODE) VALUES (?,?)";
	            			ps.clearBatch();
	            			ps = conn.prepareStatement(sql);
	            			ps.setString(1, agentID);
	            			ps.setString(2, agentCode);
	            			ps.executeUpdate();
	            			return agentCode;
	            		}
            		}catch(Exception e){
            			System.out.println(e.getMessage());
            			return "系统错误，请稍后再试!";
            		}finally{
            			DBUtils.free(conn, ps, rs);
            		}
            	}else{
            		return strss; 
            	}
        	}catch(Exception e){
        		System.out.println(e.getMessage());
        		return strss;
        	}
        	
        }
		return strss;
	}
	
	
	public static String emoji(int hexEmoji) {  
        return String.valueOf(Character.toChars(hexEmoji));  
    }  
}
