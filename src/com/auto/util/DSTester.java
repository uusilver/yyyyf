package com.auto.util;

import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
  
public class DSTester{  
    public static void karl() {  
        try {  
            Connection con = null;  
            con = DBUtils.getConnection();  
            String sql = "select * from user";  
            PreparedStatement ps = con.prepareStatement(sql);  
            ResultSet rs = null;  
            rs = ps.executeQuery();  
            while(rs.next())  
            System.out.println(rs.getString("name"));  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
    public static void main(String args[]){  
        for(int i=0;i<20;i++)  
        DSTester.karl();  
    }  
}  
