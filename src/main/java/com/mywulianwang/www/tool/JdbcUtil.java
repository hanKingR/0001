package com.mywulianwang.www.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class JdbcUtil {

	// 加载驱动、创建连接、释放资源
	public static Connection getConnection() {
		ResourceBundle bundle = ResourceBundle.getBundle("jdbcblack"); 
		String DRIVER_CLASS = bundle.getString("jdbc.driverClassName");
		String CONN_URL = bundle.getString("jdbc.url");
		String USER_NAME = bundle.getString("jdbc.username");
		String USER_PWD =bundle.getString("jdbc.password");
		Connection con = null;
		try {
			Class.forName(DRIVER_CLASS);
 			con = DriverManager.getConnection(CONN_URL, USER_NAME, USER_PWD);
		} catch (SQLException e) {
			System.out.println("数据库连接失败");
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("找不到驱动程序类,加载失败");
			e.printStackTrace();
		}
		return con;
	}

	public static void release(Statement stmt, Connection con) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void release(ResultSet rs, Statement stmt, Connection con) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void release(Object o) {
		try {
			if (o instanceof ResultSet) {
				((ResultSet) o).close();
			} else if (o instanceof Statement) {
				((Statement) o).close();
			} else if (o instanceof Connection) {
				((Connection) o).close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
//	public  static void main(String[]args){
//		Connection con =null;
//		ResultSet rs = null;
//		String sql ="select * from [CS_BlackLists] where Phone='13691403336'";
//		try {
//			con = JdbcUtil.getConnection();
//			PreparedStatement ps = con.prepareStatement(sql);
//			rs=ps.executeQuery();
//			while(rs.next()){
//				System.out.println(rs.getString("CustomerName"));
//			}
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
//		
//	}
}
