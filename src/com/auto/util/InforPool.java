package com.auto.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auto.msg.resp.Article;

public class InforPool {
	
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(InforPool.class);  
	
	private final static Map<String, Article> monNewsPool = new HashMap<String, Article>();
	private final static Map<String, String> respPool = new HashMap<String, String>();
	private final static Map<String, List<Article>> newsPool = new HashMap<String, List<Article>>();
	
	public static Article getMonthRecFromPool(String key){
		Article article = monNewsPool.get(key);
		if(article==null||article.getTitle().length()==0){
			//Load from JDBC
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try{
	    		conn = DBUtils.getConnection();
	    		String sql = "SELECT TITLE, DESCP, PICURL, URL FROM m_month_recom WHERE FLAG='Y' AND A_REMARKS1='RECM'";
	    		ps = conn.prepareStatement(sql);
	    		rs = ps.executeQuery();
	    		if(rs.next()){
	    			article = new Article();
	    	        article.setTitle(rs.getString("TITLE"));  
	    	        // 图文消息中可以使用QQ表情、符号表情  
	    	        article.setDescription(rs.getString("DESCP"));  
	    	        // 将图片置为空  
	    	        article.setPicUrl(rs.getString("PICURL"));  
	    	        article.setUrl(rs.getString("URL"));  
	    		}
	    		monNewsPool.put(key, article);
	    		//System.out.println("New one added in pool");
			}catch(Exception e){
				System.out.println(e.getMessage());
			}finally{
				DBUtils.free(conn, ps, rs);
			}
		}else{
			//System.out.println("Directly get from pool");
		}
		return article;
	}
	//获得品牌信息
	public static Article getBrandNewsFromPool(String key){
		Article article = monNewsPool.get(key);
		if(article==null||article.getTitle().length()==0){
			//Load from JDBC
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try{
	    		conn = DBUtils.getConnection();
	    		String sql = "SELECT TITLE, DESCP, PICURL, URL FROM m_month_recom WHERE FLAG='Y' AND A_REMARKS1='BRAND'";
	    		ps = conn.prepareStatement(sql);
	    		rs = ps.executeQuery();
	    		if(rs.next()){
	    			article = new Article();
	    	        article.setTitle(rs.getString("TITLE"));  
	    	        // 图文消息中可以使用QQ表情、符号表情  
	    	        article.setDescription(rs.getString("DESCP"));  
	    	        // 将图片置为空  
	    	        article.setPicUrl(rs.getString("PICURL"));  
	    	        article.setUrl(rs.getString("URL"));  
	    		}
	    		monNewsPool.put(key, article);
	    		//System.out.println("New one added in pool");
			}catch(Exception e){
				System.out.println("Errors happened at InforPool.getBrandNewsFromPool:"+e.getMessage());
			}finally{
				DBUtils.free(conn, ps, rs);
			}
		}else{
			//log.info("Directly get from pool");
		}
		return article;
	}
	/////////////////////////////////////////////////
	//获得各色新闻
	public static String getNewsFromPool(String key){
		String result = respPool.get(key);
		if(result==null){
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try{
	    		conn = DBUtils.getConnection();
	    		String sql = "SELECT MSG_RESP FROM m_month_news_resp WHERE FLAG='Y' AND KEY_VALUE=?";
	    		ps = conn.prepareStatement(sql);
	    		ps.setString(1, key);
	    		rs = ps.executeQuery();
	    		if(rs.next()){
	    			result = rs.getString("MSG_RESP");
	    		}
	    		respPool.put(key, result);
	    		//log.info("Put new in respPool");
			}catch(Exception e){
				result = e.getMessage();
				System.out.println("Errors happened at InforPool.getNewsFromPool:"+e.getMessage());
			}finally{
				DBUtils.free(conn, ps, rs);
			}
		}else{
			//log.info("From Pool");
		}
		return result;
	}
	/////////////////////////////////////今日要闻、往期回顾////////////////////////////////////////////////
	public static List<Article> getFashionNewsFromPool(String key, int num){
		List<Article> list = newsPool.get(key);
		if(list==null){
			//Load from JDBC
			list = new ArrayList<Article>();
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try{
	    		conn = DBUtils.getConnection();
	    		
	    		String sql = "SELECT TITLE, DESCP, PICURL, URL FROM m_month_recom WHERE FLAG='Y' AND A_REMARKS1='NEWS' ORDER BY ID DESC";
	    		ps = conn.prepareStatement(sql);
	    		rs = ps.executeQuery();
	    		int index = 0;
	    		while(rs.next()&&(index<num)){
	    			Article article = new Article();
	    	        article.setTitle(rs.getString("TITLE"));  
	    	        // 图文消息中可以使用QQ表情、符号表情  
	    	        article.setDescription(rs.getString("DESCP"));  
	    	        // 将图片置为空  
	    	        article.setPicUrl(rs.getString("PICURL"));  
	    	        article.setUrl(rs.getString("URL"));  
	    	        list.add(article);
	    	        index++;
	    		}
	    		newsPool.put(key, list);
	    		//log.info("New one added in pool");
			}catch(Exception e){
				System.out.println("Errors happened at InforPool.getFashionNewsFromPool:"+e.getMessage());
			}finally{
				DBUtils.free(conn, ps, rs);
			}
		}else{
			//log.info("Directly get from pool");
		}
		return list;
	}
}
