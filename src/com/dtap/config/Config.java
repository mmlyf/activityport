package com.dtap.config;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Config {
	/**
	 * 配置logger4j配置文件的位置
	 */
	public static final String LOGPATH = "D://HSDT_Activity_port/config/log.properties";
	
	/**
	 * 配置导出未订购成功数据的位置
	 */
	public static final String FILEPATH = "D://HSDT_Activity_port/outfile/";//实际环境

	//	public static final String FILEPATH = "/Users/lvgordon/Downloads/little/";//测试环境
	/**
	 * 邮件发送的主机
	 */
	public static final String MAILHOST = "smtp.mobile99.cn";
	/**
	 * 发送邮件的账户
	 */
	public static final String MAILACCOUNT = "lvyf@mobile99.cn";
	/**
	 * 发送邮件的密码
	 */
	public static final String  MAILPASSWORD = "Lyfl6511102516";
	
	/**
	 * 	数据库连接驱动路径
	 */
	public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	
	/**
	 * 河北本地数据库地址
	 */
	public static final String DB_LOCAL_URL = "jdbc:mysql://localhost:3306/unicom?autoReconnect=true";//河北
	
	/**
	 * 本地测试数据库地址
	 */
	public static String DB_TEST_URL = "jdbc:mysql://localhost:3306/nunicom?autoReconnect=true";
	
	/**
	 * 阿里云数据库连接地址
	 */
	public static final String DB_ALI_URL = "jdbc:mysql://rm-bp10bi1g7nt8ln13nbo.mysql.rds.aliyuncs.com:3306/hsdt-db?autoReconnect=true";
	
	/**
	 * 数据库连接用户名
	 */
	public static final String DB_USERNAME = "root";
	
	/**
	 * 河北数据库连接密码
	 */
	public static final String DB_LOCAL_PWD = "unicom-sale";//河北
	
	/**
	 * 本地测试数据库的密码
	 */
	public static final String TESTPWD = "root";
	
	/**
	 * 阿里云数据库连接密码
	 */
	public static final String DB_ALI_PWD = "Hsdt@2018";
	
	/**
     * 存放抽奖数据的文件路径（FTP文件的实际路径）
     */
    public static final String EQUITYOUT = "D://webwww/FTPdsj/QYDATA/";
    
    public static final String TESTPATH = "/Users/lvgordon/Downloads/little/";
}
