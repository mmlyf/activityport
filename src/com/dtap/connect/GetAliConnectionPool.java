//package com.dtap.connect;
//
//import java.io.PrintWriter;
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.SQLFeatureNotSupportedException;
//import java.util.Vector;
//import java.util.logging.Logger;
//
//import javax.sql.DataSource;
//
//import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
//
//import com.dtap.config.Config;
//
//
//public class GetAliConnectionPool  {
//
//	//数据库连接池
//	private static BasicDataSource dbcp;
//
//	//为不同线程管理连接
//	private static ThreadLocal<Connection> tl;
//
//	//通过配置文件来获取数据库参数
//	static{
//		try{
//
//
//			//一、初始化连接池
//			dbcp = new BasicDataSource();
//
//
//			//设置驱动 (Class.forName())
//			dbcp.setDriverClassName(Config);
//			//设置url
//			dbcp.setUrl(prop.getProperty("jdbc.url"));
//			//设置数据库用户名
//			dbcp.setUsername(prop.getProperty("jdbc.user"));
//			//设置数据库密码
//			dbcp.setPassword(prop.getProperty("jdbc.password"));
//			//初始连接数量
//			dbcp.setInitialSize(
//					Integer.parseInt(
//							prop.getProperty("initsize")
//							)
//					);
//			//连接池允许的最大连接数
//			dbcp.setMaxActive(
//					Integer.parseInt(
//							prop.getProperty("maxactive")
//							)
//					);
//			//设置最大等待时间
//			dbcp.setMaxWait(
//					Integer.parseInt(
//							prop.getProperty("maxwait")
//							)
//					);
//			//设置最小空闲数
//			dbcp.setMinIdle(
//					Integer.parseInt(
//							prop.getProperty("minidle")
//							)
//					);
//			//设置最大空闲数
//			dbcp.setMaxIdle(
//					Integer.parseInt(
//							prop.getProperty("maxidle")
//							)
//					);
//			//初始化线程本地
//			tl = new ThreadLocal<Connection>();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 获取数据库连接
//	 * @return
//	 * @throws SQLException 
//	 */
//	public static Connection getConnection() throws SQLException{
//		/*
//		 * 通过连接池获取一个空闲连接
//		 */
//		Connection conn 
//		= dbcp.getConnection();
//		tl.set(conn);
//		return conn;
//	}
//
//
//	/**
//	 * 关闭数据库连接
//	 */
//	public static void closeConnection(){
//		try{
//			Connection conn = tl.get();
//			if(conn != null){
//				/*
//				 * 通过连接池获取的Connection
//				 * 的close()方法实际上并没有将
//				 * 连接关闭，而是将该链接归还。
//				 */
//				conn.close();
//				tl.remove();
//			}    
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
//}
