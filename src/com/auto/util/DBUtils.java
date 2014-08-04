package com.auto.util;

import java.io.InputStream;  
import java.sql.Connection;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  
import java.util.Properties;  
  
import javax.sql.DataSource;  
  
import org.apache.commons.dbcp.BasicDataSourceFactory;  
/** 
 * JDBC工具类 
 * @author bird 
 * 
 */  
public final class DBUtils {  
    private static DataSource dataSource;  
      
    private DBUtils() {  
    }  
  
    static {  
        try {  
            Class.forName("com.mysql.jdbc.Driver");  
              
            Properties pro = new Properties();  
            InputStream in = DBUtils.class.getClassLoader().getResourceAsStream("dbcp.properties");  
            pro.load(in);  
              
            dataSource = BasicDataSourceFactory.createDataSource(pro);//注意这段代码！！！  
        } catch (Exception e) {  
            throw new ExceptionInInitializerError(e);  
        }  
    }  
    public static Connection getConnection() throws SQLException {  
        //System.out.print(dataSource.getConnection("root","family"));  
        return dataSource.getConnection();    
    }  
    public static void free(Connection con, Statement st, ResultSet rs) {  
        try {  
            if (rs != null)  
                rs.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (st != null)  
                    st.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            } finally {  
  
                if (con != null)  
                    try {  
                        con.close();  
                    } catch (SQLException e) {  
                        // TODO Auto-generated catch block  
                        e.printStackTrace();  
                    }  
            }  
        }  
    }  
    public static void dbcper(){  
        ResultSet rs = null;//建立一个数据库表的对象  
        Statement stmt = null;//用于执行静态SQL的对象  
        Connection conn = null;//用于建立联系的对象  
        //Temple T = new Temple();  
        try {  
            conn = DBUtils.getConnection();  
            stmt = conn.createStatement();//创建一个 Statement 对象来将 SQL 语句发送到数据库  
            rs=stmt.executeQuery("select * from tb_tab1");//执行给定的 SQL 语句，该语句返回单个 ResultSet 对象  
            while(rs.next()){//当有下一个节点时  
                 System.out.println(rs.getString("name"));//输出对应的数据库的成员的name  
                 System.out.println(rs.getInt("passwords"));//输出对应的数据库成员的id  
             }  
        }catch (SQLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
}  
