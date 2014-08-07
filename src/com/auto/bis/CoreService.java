package com.auto.bis;
import java.util.Date;  
import java.util.Map;  
import javax.servlet.http.HttpServletRequest;  

import com.auto.msg.resp.NewsMessage;
import com.auto.msg.resp.TextMessage;  
import com.auto.util.MessageUtil; 

public class CoreService {
	
public static String processRequest(HttpServletRequest request) {  
	String respMessage = null;  
    try {  
        // 默认返回的文本消息内容  
        String respContent = "请求处理异常，请稍候尝试！";  

        // xml请求解析  
        Map<String, String> requestMap = MessageUtil.parseXml(request);  

        // 发送方帐号（open_id）  
        String fromUserName = requestMap.get("FromUserName");  
        // 公众帐号  
        String toUserName = requestMap.get("ToUserName");  
        // 消息类型  
        String msgType = requestMap.get("MsgType");  

        // 回复文本消息  
        TextMessage textMessage = new TextMessage();  
        textMessage.setToUserName(fromUserName);  
        textMessage.setFromUserName(toUserName);  
        textMessage.setCreateTime(new Date().getTime());  
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
        textMessage.setFuncFlag(0);  
        // 创建图文消息  
        NewsMessage newsMessage = new NewsMessage();  
        newsMessage.setToUserName(fromUserName);  
        newsMessage.setFromUserName(toUserName);  
        newsMessage.setCreateTime(new Date().getTime());  
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
        newsMessage.setFuncFlag(0);  
        
        // 文本消息  
        if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {  
            //respContent = "您发送的是文本消息！";  
            String content = requestMap.get("Content");
            if(content.equalsIgnoreCase("1")){
            	//包邮信息
            	return ServiceFunctions.getTextMessage(textMessage, "00");  
            }else if(content.equalsIgnoreCase("2")){
            	//返回图文信息
            	return ServiceFunctions.getTextMessage(textMessage, "12");
            }else if(content.startsWith("#")){
            	//单独处理邀请码的生成
	            textMessage.setContent(ServiceFunctions.getAgentCode(content));  
	            respMessage = MessageUtil.textMessageToXml(textMessage);
            }
            
        }  
        // 图片消息  
        else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {  
            respContent = "您发送的是图片消息！";  
        }  
        // 地理位置消息  
        else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {  
            respContent = "您发送的是地理位置消息！";  
        }  
        // 链接消息  
        else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {  
            respContent = "您发送的是链接消息！";  
        }  
        // 音频消息  
        else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {  
            respContent = "您发送的是音频消息！";  
        }  
        // 事件推送  
        else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
            // 事件类型  
            String eventType = requestMap.get("Event");  
            // 订阅  
            if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
            	StringBuffer buffer = new StringBuffer();  
         	    buffer.append("您好，我是小悠，感谢您的关注：").append("\n\n");  
         	    buffer.append("您的全场现金抵用卷:").append("\n");  
         	    buffer.append("ABCD").append("\n\n"); 
                respContent = buffer.toString();  
            }  
            // 取消订阅  
            else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息  
            }  
            // 自定义菜单点击事件  
            else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
                // TODO 自定义菜单权没有开放，暂不处理该类消息  
            	String eventKey = requestMap.get("EventKey");  
            	  
                if (eventKey.equals("11")) {  
                    respContent = "品牌介绍被点击！";  
                } else if (eventKey.equals("12")) { 
                	//最新优惠
                    return ServiceFunctions.getTextMessage(textMessage, "12");
                } else if (eventKey.equals("13")) {  
                	// 创建图文消息  
                	return ServiceFunctions.getNewsMessage(newsMessage, "13");
                } else if (eventKey.equals("14")) {  
                    //respContent = "自助客服被点击！";  
                    return ServiceFunctions.getTextMessage(textMessage, "14");
                } else if (eventKey.equals("15")) {  
                    //respContent = "我要赚钱被点击！";  
                    return ServiceFunctions.getTextMessage(textMessage, "15");
                } else if (eventKey.equals("21")) {  
                    respContent = "今日亮点被点击！";  
                } else if (eventKey.equals("22")) {  
                    respContent = "往期精华被点击！";  
                }
            }  
        }  
        //textMessage.setContent(respContent);  
        //respMessage = MessageUtil.textMessageToXml(textMessage);  
      
    } catch (Exception e) {  
        e.printStackTrace();  
    }  

    return respMessage;  
	}  
}
