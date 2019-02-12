package com.dtap.connect;

import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Vector;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.dtap.config.Config;

public class NewGetConnection implements DataSource{

	private static Vector<Connection> Connections = new Vector<Connection>();

	static{
		try {
			System.out.println("获取到了链接");
			//加载数据库驱动
			Class.forName(Config.DB_DRIVER);
			Connection conn = DriverManager.getConnection(Config.DB_LOCAL_URL, Config.DB_USERNAME, Config.DB_LOCAL_PWD);
			System.out.println("获取到了链接" + conn);
			//将获取到的数据库连接加入到Connections集合中，Connections此时就是一个存放了数据库连接的连接池
			Connections.addElement(conn);
			System.out.println("获取到了链接");
		} catch (SQLException e) {
			System.out.println(" 创建数据库连接失败！ " + e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		//如果数据库连接池中的连接对象的个数大于0
		
		if (Connections.size()>0) {
			//从Connections集合中获取一个数据库连接
			final Connection conn = (Connection) Connections.remove(0);
			System.out.println("Connections数据库连接池大小是" + Connections.size());
			//返回Connection对象的代理对象
			return (Connection) Proxy.newProxyInstance(NewGetConnection.class.getClassLoader(), conn.getClass().getInterfaces(), new InvocationHandler(){
				@Override
				public Object invoke(Object proxy, Method method, Object[] args)
						throws Throwable {
					if(!method.getName().equals("close")){
						return method.invoke(conn, args);
					}else{
						//如果调用的是Connection对象的close方法，就把conn还给数据库连接池
						Connections.addElement(conn);
						System.out.println(conn + "被还给Connections数据库连接池了！！");
						System.out.println("Connections数据库连接池大小为" + Connections.size());
						return null;
					}
				}
			});
		}else {
			throw new RuntimeException("对不起，数据库忙");
		}

	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		NewGetConnection get = new NewGetConnection();
		try {
			Connection con = get.getConnection();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
