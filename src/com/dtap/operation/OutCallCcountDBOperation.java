package com.dtap.operation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dtap.connect.GetConnect;

import apple.laf.JRSUIConstants.State;

public class OutCallCcountDBOperation {
	
	/**
	 * 根据时间查询当前页面的pv数
	 * @param addtime
	 * @return
	 */
	public static Integer selectOutCallCCPvCountByTime(String addtime) {
		Integer pagepv = null;
		Connection conn = GetConnect.getAliConnection();
		String sql = "select * from tb_outcall_ccount where addtime='"+addtime+"'";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				pagepv = resultSet.getInt("pv");
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pagepv;
	}
	
	/**
	 * 添加当前时间外呼信用卡页面的访问数
	 * @param addtime
	 * @return
	 */
	public static boolean insertCalloutCCPvByAddtime(String addtime) {
		int inoutres = 0;
		Connection con = GetConnect.getAliConnection();
		String sql = "insert into tb_outcall_ccount(pv,addtime) values(1,'"+addtime+"')";
		try {
			Statement statement = con.createStatement();
			inoutres = statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (inoutres>0) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 根据时间更新当前时间外呼信用卡的数量
	 * @param count
	 * @param addtime
	 * @return
	 */
	public static boolean updateOutCallPvCountCCByAddtime(int count,String addtime) {
		int upoutres = 0;
		String sql = "update tb_outcall_ccount set pv="+count+" where addtime='"+addtime+"'";
		Connection con = GetConnect.getAliConnection();
		try {
			Statement statement = con.createStatement();
			upoutres = statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (upoutres>0) {
			return true;
		}else {
			return false;
		}
	}
}
