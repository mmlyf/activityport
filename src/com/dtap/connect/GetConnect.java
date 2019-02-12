package com.dtap.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.DataSource;

import com.dtap.config.Config;

public class GetConnect{
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(Config.DB_DRIVER);
			conn = DriverManager.getConnection(Config.DB_LOCAL_URL, Config.DB_USERNAME, Config.DB_LOCAL_PWD);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static Connection getAliConnection() {
		Connection con = null;
		try {
			Class.forName(Config.DB_DRIVER);
			con = DriverManager.getConnection(Config.DB_ALI_URL,Config.DB_USERNAME,Config.DB_ALI_PWD);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	
	
}
