package com.bi.utility;

import java.sql.Connection;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class ConnectionUtility {
	public static void main(String[] args) throws Exception {
		ConnectionUtility con=new ConnectionUtility();
		try {
			System.out.println(con.getConnection());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() throws Exception {
		String DriverClassName = "org.postgresql.Driver";
		String url = "jdbc:postgresql://localhost/postgres";
		String username = "postgres";
		String password ="rrk";

		Connection connection = null;
		// //System.out.println(DriverClassName+"::"+username+"::"+url+"::"+"::"+password);
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(DriverClassName);
		basicDataSource.setUrl(url);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);
		
		
		connection = basicDataSource.getConnection();
		return connection;
	}

	public BasicDataSource getDataSource() throws Exception {
		String DriverClassName = "org.postgresql.Driver";
		String url = "jdbc:postgresql://localhost/postgres";
		String username = "postgres";
		String password ="rrk";
        BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(DriverClassName);
		basicDataSource.setUrl(url);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);
		return basicDataSource;
	}
	
	

}
