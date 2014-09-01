package com.auto.bis;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auto.util.DBUtils;

public class QueryAgentNo extends HttpServlet {

	protected void doGet(HttpServletRequest req,HttpServletResponse resp)
		      throws ServletException, IOException
	{
		PrintWriter out = resp.getWriter();
		Connection conn = null;
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		String result = null;
 		String telno = req.getParameter("telno");
 		try{
     		conn = DBUtils.getConnection();
     		String sql = "SELECT AGENT_CODE FROM m_agent_code a, m_agent_tb b where a.AGENT_ID=b.ID and b.A_TELNO=?";
     		ps = conn.prepareStatement(sql);
     		ps.setString(1, telno);
     		rs = ps.executeQuery();
     		if(rs.next()){
     			result = rs.getString("AGENT_CODE");
     			System.out.println(telno+"的推广码是:"+result);
     		}else{
     			result = "none";
     		}
 		}catch(Exception e){
 			e.getMessage();
 		}finally{
 			DBUtils.free(conn, ps, rs);
 		}
 		out.print(result);
	 		
     		
    }//绑定支付宝或者财付通账号
}
