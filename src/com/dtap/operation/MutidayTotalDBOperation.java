package com.dtap.operation;
/**
 * 
 * @author lvgordon
 * 流量包多日包订购活动的统计表 
 *  
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.dtap.connect.GetConnect;

public class MutidayTotalDBOperation {

	/**
	 * 
	 * @param addtime
	 * @return
	 * 根据时间查询当前时间多日包订购页面的统计数据
	 * 
	 */
	public static Integer selectDataByAddtime(String addtime,String type){
		Connection conn = GetConnect.getAliConnection();
		String sql = "select * from mutiday_total where addtime='"+addtime+"'";
		Integer resultcount = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				switch (type) {
				case "1":
					resultcount = resultSet.getInt("pv");
					break;
				case "2":
					resultcount = resultSet.getInt("bc");
					break;
				case "3":
					resultcount = resultSet.getInt("six_orderc");
					break;
				case "4":
					resultcount = resultSet.getInt("nine_orderc");
					break;
				default:
					break;
				}
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultcount;
	}

	/**
	 * 
	 * @param value
	 * @param type
	 * @param sectime
	 * @return
	 * 跟新数据表的数据
	 * 
	 */
	public static boolean updateDataByAddtime(int value,String type,String sectime) {
		Connection conn = GetConnect.getAliConnection();
		int result = 0;
		String sql = "update mutiday_total set ";
		switch (type) {
		case "1"://更新pv
			sql += "pv = "+value;
			break;
		case "2"://更新按钮数量
			sql+="bc="+value;
			break;
		case "3"://更新6.6元多日包的订购数量
			sql+="six_orderc="+value;
			break;
		case "4"://更新9.9元多日包的订购数量	
			sql+="nine_orderc="+value;
			break;
		default:
			break;
		}
		sql += " where addtime='"+sectime+"'";
		try {
			Statement statement = conn.createStatement();
			result = statement.executeUpdate(sql);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result>0) {
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean insertDataForAddtime(String sectime) {
		Connection conn = GetConnect.getAliConnection();
		int result = 0;
		String sql = "insert into mutiday_total(pv,bc,six_orderc,nine_orderc,addtime) values(1,0,0,0,'"+sectime+"')";
		try {
			Statement statement = conn.createStatement();
			result = statement.executeUpdate(sql);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result>0) {
			return true;
		}else {
			return false;
		}
	}
	
	public static void main(String args[]) {
		boolean res = updateDataByAddtime(2, "4", "2019-01-29");
		System.out.println(res);
	}
}
