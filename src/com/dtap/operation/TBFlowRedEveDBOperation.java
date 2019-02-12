package com.dtap.operation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dtap.connect.GetConnect;

/**
 * 
 * @author lvgordon
 * 对流量红包的数据库进行操作
 * 
 *
 */
public class TBFlowRedEveDBOperation {

	/**
	 * 
	 * @param type
	 * @param sectime
	 * @return
	 * 根据type值查找不同的值
	 * 
	 */
	public static Integer selectTBFlowCountDataByType(String type,String sectime) {
		Connection con = GetConnect.getAliConnection();
		String sql = "select * from tb_flowredeve_total where addtime='"+sectime+"'";
		Integer rescount = null;
		try {
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				switch (type) {
				case "1":
					rescount = resultSet.getInt("pv");
					break;
				case "2":
					rescount = resultSet.getInt("liji_bc");
					break;
				case "3":
					rescount = resultSet.getInt("comfir_bc");
					break;
				case "4":
					rescount = resultSet.getInt("tx_count");
					break;
				case "5":
					rescount = resultSet.getInt("pp_count");
					break;
				case "6":
					rescount = resultSet.getInt("aqy_count");
					break;
				case "7":
					rescount = resultSet.getInt("yk_count");
					break;
				case "8":
					rescount = resultSet.getInt("ordersuc_count");
					break;
				case "9":
					rescount = resultSet.getInt("orderunsuc_count");
					break;
				default:
					break;
				}
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rescount;
	}

	
	/**
	 * 当前时间没有记录新增一条当前时间的记录
	 * @param sectime
	 * @return
	 */
	public static boolean insertTBFlowCountByAddtime(String sectime) {
		Connection conn = GetConnect.getAliConnection();
		String sql = "insert into tb_flowredeve_total(pv,liji_bc,comfir_bc,"
				+ "tx_count,pp_count,aqy_count,yk_count,ordersuc_count,orderunsuc_count,addtime) "
				+ "values(1,0,0,0,0,0,0,0,0,'"+sectime+"')";
		int res = 0;
		try {
			Statement statement = conn.createStatement();
			res = statement.executeUpdate(sql);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (res>0) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 根据时间和对应的type值更新数据
	 * @param type
	 * @param value
	 * @param sectime
	 * @return
	 */
	public static boolean updateTBFlowCountByType(String type,int value,String sectime) {
		Connection con = GetConnect.getAliConnection();
		String sql = "update tb_flowredeve_total set ";
		int rescount = 0;
		switch (type) {
		case "1":
			sql += "pv="+value;
			break;
		case "2":
			sql += "liji_bc="+value;
			break;
		case "3":
			sql += "comfir_bc="+value;
			break;
		case "4":
			sql += "tx_count="+value;
			break;
		case "5":
			sql += "pp_count="+value;
			break;
		case "6":
			sql += "aqy_count="+value;
			break;
		case "7":
			sql += "yk_count="+value;
			break;
		case "8":
			sql += "ordersuc_count="+value;
			break;
		case "9":
			sql += "orderunsuc_count="+value;
			break;
		default:
			break;
		}
		sql += " where addtime='"+sectime+"'";
		try {
			Statement statement = con.createStatement();
			rescount = statement.executeUpdate(sql);
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (rescount>0) {
			return true;
		}else {
			return false;
		}
	}

}
