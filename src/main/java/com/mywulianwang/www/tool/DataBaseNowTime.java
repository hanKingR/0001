package com.mywulianwang.www.tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class DataBaseNowTime {
	public static String getDataBaseNowTime(){
		Session session = null;
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String dataBase = null;
			SessionFactory sessionFactory = (SessionFactory)(Constants.CTX.getBean("shopSessionFactory"));
			session = sessionFactory.openSession();
			conn = session.connection();
			
			if(conn !=null){
				String querySQL = "select GETDATE() as nowTime";
				preparedStatement = conn.prepareStatement(querySQL);
				resultSet = preparedStatement.executeQuery();
				if(resultSet != null){
					resultSet.next();
					dataBase = resultSet.getString(1);
				}else{
					System.out.println("get getDataBaseNowTime is null");
				}
				return dataBase;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(resultSet != null){
					resultSet.close();
					resultSet = null;
				}
				if(preparedStatement != null){
					preparedStatement.close();
					preparedStatement = null;
				}
				if(null != session){
              	  	session.close();
              	  	session = null;
                }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public static String getDataBaseNowTime2(){
		Session session = null;
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String dataBase = null;
			SessionFactory sessionFactory = (SessionFactory)(Constants.CTX.getBean("backSessionFactory"));
			session = sessionFactory.openSession();
			conn = session.connection();
			
			if(conn !=null){
				String querySQL = "select GETDATE() as nowTime";
				preparedStatement = conn.prepareStatement(querySQL);
				resultSet = preparedStatement.executeQuery();
				if(resultSet != null){
					resultSet.next();
					dataBase = resultSet.getString(1);
				}else{
					System.out.println("get getDataBaseNowTime is null");
				}
				return dataBase;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(resultSet != null){
					resultSet.close();
					resultSet = null;
				}
				if(preparedStatement != null){
					preparedStatement.close();
					preparedStatement = null;
				}
				if(null != session){
              	  	session.close();
              	  	session = null;
                }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
