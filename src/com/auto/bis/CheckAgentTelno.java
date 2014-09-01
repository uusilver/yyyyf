package com.auto.bis;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auto.util.DBUtils;

public class CheckAgentTelno extends HttpServlet {
	 protected void doGet(HttpServletRequest req,HttpServletResponse resp)
		      throws ServletException, IOException
	{
		    PrintWriter out = resp.getWriter();
		 	Connection conn = null;
	 		PreparedStatement ps = null;
	 		ResultSet rs = null;
	 		String telno = req.getParameter("telno");
	 		try{
	     		conn = DBUtils.getConnection();
	     		String sql = "SELECT ID FROM m_agent_tb where A_TELNO=?";
	     		ps = conn.prepareStatement(sql);
	     		ps.setString(1, telno);
	     		rs = ps.executeQuery();
	     		if(rs.next()){
	     			System.out.println(telno+"有记录存在，申请失败");
	     			out.print(1);
	     		}else{
	     			out.print(0);
	     		}
	 		}catch(Exception e){
	 			e.getMessage();
	 		}finally{
	 			DBUtils.free(conn, ps, rs);
	 		}
	 		
       		
       		
      }//绑定支付宝或者财付通账号
}
