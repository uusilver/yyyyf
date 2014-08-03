package com.auto.bis;

import java.util.ArrayList;
import java.util.List;

import com.auto.msg.resp.Article;
import com.auto.msg.resp.NewsMessage;
import com.auto.util.MessageUtil;

public class ServiceFunctions {

	/** 
	 * xiaoqrobot的主菜单 
	 *  
	 * @return 
	 */  
	public static String getMainMenu() {  
	    StringBuffer buffer = new StringBuffer();  
	    buffer.append("您好，我是小悠，感谢您的关注：").append("\n\n");  
	    buffer.append("您的全场现金抵用卷:").append("\n");  
	    buffer.append("ABCD").append("\n\n");  
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
		if ("1".equals(content)) {  
            Article article = new Article();  
            article.setTitle("安可信");  
            // 图文消息中可以使用QQ表情、符号表情  
            article.setDescription("信生网络，" + emoji(0x1F6B9)  
                    + "，无锡安可信网络科技有限公司旗下IDC品牌网站！\n\n总部设在江苏无锡。\n\n基于互联网提供各类网络服务解决方案的中国网络增值服务运营商.");  
            // 将图片置为空  
            article.setPicUrl("");  
            article.setUrl("http://www.idcvisa.com");  
            articleList.add(article);  
            newsMessage.setArticleCount(articleList.size());  
            newsMessage.setArticles(articleList);  
            return MessageUtil.newsMessageToXml(newsMessage);  
        }  
		return null;
	}
	
	public static String emoji(int hexEmoji) {  
        return String.valueOf(Character.toChars(hexEmoji));  
    }  
}
