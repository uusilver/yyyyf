package com.auto.bis;

import java.util.ArrayList;
import java.util.List;

import com.auto.msg.resp.Article;
import com.auto.msg.resp.NewsMessage;
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
		if ("1".equals(content)) {  
            Article article = new Article();  
            article.setTitle("九月主打");  
            // 图文消息中可以使用QQ表情、符号表情  
            article.setDescription("悠悠银艺坊，" + emoji(0x1F6B9)  
                    + "，开学季主打！\n\n蝴蝶款耳钉。\n\n青春如霓裳翩舞的蝴蝶，青春就该绽放！.");  
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
	
	public static String emoji(int hexEmoji) {  
        return String.valueOf(Character.toChars(hexEmoji));  
    }  
}
