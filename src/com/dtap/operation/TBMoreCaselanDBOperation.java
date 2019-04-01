package com.dtap.operation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dtap.connect.GetConnect;
/**
 * 广发银行信用卡外呼页面统计
 * @author lvgordon
 *
 */
public class TBMoreCaselanDBOperation {
	/**
	 * 查询当前时间是否有PV值，并将值返回
	 * @param addtime
	 * @return
	 */
	public static Integer selectPVCountDataByAddtime(String addtime) {
		Integer pvcount = null;
		Connection conn = GetConnect.getAliConnection();
		String sql = "select * from tb_more_caselan where addtime = '"+addtime+"'";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				pvcount = resultSet.getInt("pv");
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pvcount;
	}
	
	/**
	 * 当前时间在数据库中没有值，对当前时间的PV进行添加操作
	 * @param addtime
	 * @return
	 */
	public static boolean insertPvCountDataByAddtime(String addtime) {
		int inres = 0;
		Connection conn = GetConnect.getAliConnection();
		String sql = "insert into tb_more_caselan(pv,addtime) values(1,'"+addtime+"')";
		try {
			Statement statement = conn.createStatement();
			inres = statement.executeUpdate(sql);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (inres>0) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 更新当前时间的PV值
	 * @param pvvalue
	 * @param addtime
	 * @return
	 */
	public static boolean updatePvCountDataByAddtime(int pvvalue,String addtime) {
		int upres = 0;
		Connection conn = GetConnect.getAliConnection();
		String sql = "update tb_more_caselan set pv = "+pvvalue+" where addtime = '"+addtime+"'";
		try {
			Statement statement = conn.createStatement();
			upres = statement.executeUpdate(sql);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (upres>0) {
			return true;
		}else {
			return false;
		}
	}
}
