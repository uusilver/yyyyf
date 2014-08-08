package com.auto.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import com.auto.msg.resp.Article;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//邀请码测试
//		String s = "#ZQ#王二麻#12345678901";
//		System.out.println(testAgentCode(s));
		
		//
		//testConn();
//		Article a = InforPool.getMonthRecFromPool("MONTH");
//		System.out.println(a.getTitle());
		
		//System.out.println(getKeyValue("00"));
		List<Article> list = InforPool.getFashionNewsFromPool("today",2); 
		for(Article a:list){
			System.out.println(a.getTitle());
		}
	}

	public static String testAgentCode(String content){
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
	            		while(rs.next()){
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
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void testConn(){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
    		conn = DBUtils.getConnection();
    		String sql = "SELECT TITLE, DESCP, PICURL, URL FROM M_MONTH_RECOM WHERE FLAG='Y'";
    		ps = conn.prepareStatement(sql);
    		rs = ps.executeQuery();
    		if(rs.next()){
    			System.out.println(rs.getString("TITLE"));
    			System.out.println(rs.getString("DESCP"));
    			System.out.println(rs.getString("PICURL"));
    			System.out.println(rs.getString("URL"));
    		}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			DBUtils.free(conn, ps, rs);
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static String getKeyValue(String key){
		String result = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
    		conn = DBUtils.getConnection();
    		String sql = "SELECT MSG_RESP FROM M_MONTH_NEWS_RESP WHERE FLAG='Y' AND KEY_VALUE=?";
    		ps = conn.prepareStatement(sql);
    		ps.setString(1, key);
    		rs = ps.executeQuery();
    		if(rs.next()){
    			result = rs.getString("MSG_RESP");
    		}
		}catch(Exception e){
			result = "未知错误！请稍后再试！";
			System.out.println(e.getMessage());
		}finally{
			DBUtils.free(conn, ps, rs);
		}
		return result;
	}
}
