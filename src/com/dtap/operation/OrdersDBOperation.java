package com.dtap.operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.dtap.connect.GetConnect;

public class OrdersDBOperation {
	public static boolean getOrdersDataByPhone(String phone,String addtime) {
		Connection conn = GetConnect.getConnection();
		String sql = "select * from orders where Mobile='"+phone+"' "
				+ "and AddTime like '"+addtime+"%' ORDER BY AddTime DESC";
		ResultSet resultSet = null;
		boolean result = false;
		try {
			Statement statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				result = true;
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 统计订单成功数
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public static Integer totalOrdersCount(String starttime,String endtime) {
		Connection conn = GetConnect.getConnection();
		String sql = "select count(1) from orders where BssState=0 AND AddTime >= '"+starttime+"' "
				+ "and AddTime <= '"+endtime+"' AND ProductId in (SELECT Id FROM products where ProductName LIKE '%低消%')";
		Integer counttotal = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				counttotal = resultSet.getInt("count(1)");
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return counttotal;
	}

	/**
	 * 统计订单不成功数
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public static Integer totalOrdersUnsucCount(String starttime,String endtime) {
		Connection conn = GetConnect.getConnection();
		String sql = "select count(1) from orders where (BssState is null or BssState!=0) AND AddTime >= '"+starttime+"' "
				+ "and AddTime <= '"+endtime+"' AND ProductId in (SELECT Id FROM products where ProductName LIKE '%低消%')";
		Integer counttotal = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				counttotal = resultSet.getInt("count(1)");
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return counttotal;
	}

	/**
	 * 
	 * @param starttime
	 * @param endtime
	 * @return
	 * 获取多日包6.6元的和9.9元的值
	 * 
	 */
	public static Integer selectMutidayCount(String starttime,String endtime,String type){
		Connection conn = GetConnect.getConnection();
		String sql = "select count(1) from orders where BssState = '0' AND AddTime >='"+starttime+"' and AddTime<='"+endtime+"'"
				+ " and ProductId in ";
		switch (type) {
		case "1":
			sql += "(SELECT Id FROM products where ProductName='6.6元3G国内流量3天包（河北）')";
			break;
		case "2":
			sql += "(SELECT Id FROM products where ProductName='9.9元7G国内流量7天包（河北）')";
		default:
			break;
		}
		Integer res = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				res = resultSet.getInt("count(1)");
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	/**
	 * 查询20元假日包订购成功和失败数
	 * @param starttime
	 * @param endtime
	 * @param type
	 * @return
	 */
	public static Integer selectTwentySevenday(String starttime,String endtime,String type) {
		Connection conn = GetConnect.getConnection();
		Integer rescount = 0;
		String sql = "select count(1) from orders where AddTime>=? AND AddTime<=? ";
		switch (type) {
		case "0":
			sql += "AND BssState=0 ";//20元7日假日包订购成功数
			break;
		case "1":
			sql += "AND (BssState!=0 or BssState is null) ";//20元7日假日包失败数
		default:
			break;
		}
		sql += " AND ProductId in (SELECT Id FROM products where ProductName='20元包7G全国+7G省内流量7天假日包')";
		try {
			PreparedStatement ppst = conn.prepareStatement(sql);
			ppst.setString(1, starttime);
			ppst.setString(2, endtime);
			ResultSet resultSet = ppst.executeQuery();
			while (resultSet.next()) {
				rescount = resultSet.getInt("count(1)");
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rescount;
	}

	
	/**
	 * 统计特惠流量大颗粒包成功或失败数据
	 * @param starttime
	 * @param endtime
	 * @param type
	 * @param state
	 * @return
	 */
	public static Integer selectIndulgenceFlowTotal(String starttime,String endtime,String type,String state) {
		Connection con = GetConnect.getConnection();
		Integer totalres = null;
		String sql = "select count(1) from orders where AddTime>=? and AddTime<=? AND ProductId in ";
		switch (type) {
		case "3":
			sql += "(SELECT Id FROM products where ProductName='10元3G特惠流量月包（4G）-立即生效-后付【省分】')";
			break;
		case "8":
			sql += "(SELECT Id FROM products where ProductName='20元8G特惠流量月包（4G）-立即生效（河北）')";
			break;
		case "12":
			sql += "(SELECT Id FROM products where ProductName='30元12G特惠流量月包（4G）-立即生效（河北）')";
			break;
		case "25":
			sql += "(SELECT Id FROM products where ProductName='50元25G特惠流量月包（4G）-立即生效-后付【省分】')";
			break;
		case "40":
			sql += "(SELECT Id FROM products where ProductName='70元40G特惠流量月包（4G）-立即生效（河北）')";
			break;
		default:
			break;
		}
		switch (state) {
		case "0":
			sql += " AND BssState=0";
			break;
		case "1":
			sql += " AND (BssState is null or BssState!=0)";
		default:
			break;
		}
		try {
			PreparedStatement ppst = con.prepareStatement(sql);
			ppst.setString(1, starttime);
			ppst.setString(2, endtime);
			ResultSet resultSet = ppst.executeQuery();
			while (resultSet.next()) {
				totalres = resultSet.getInt("count(1)");
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return totalres;
	}
	
	/**
	 * 查询新春红包活动权益添加时间的30秒的时间间隔是否有订购记录
	 * 并且返回订购记录状态
	 * @param phone
	 * @param date
	 * @return
	 */
	public static Integer selectDataState(String phone,String starttime,String endtime) {
		Connection conn = GetConnect.getConnection();
		
		String sql = "select * from orders where Mobile='"+phone+"' AND ProductId='3c40d03b-0d06-4e5b-82be-1ca2f59f338c' "
				+ "AND AddTime >= '"+starttime+"' AND AddTime <= '"+endtime+"' and BssState=0 ORDER BY AddTime DESC";
		Integer bssstate = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				bssstate = resultSet.getInt("BssState");
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bssstate;
	}
	
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		System.out.println(sdf.format(date));
		long time = date.getTime() + 30*1000;
		date = new Date(time);
		System.out.println(sdf.format(date));
	}
}
