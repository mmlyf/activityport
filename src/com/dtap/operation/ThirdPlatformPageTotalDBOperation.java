package com.dtap.operation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.dtap.connect.GetConnect;

public class ThirdPlatformPageTotalDBOperation {
	
	private String dbname = "";
	
	public ThirdPlatformPageTotalDBOperation(int type ) {
		// TODO Auto-generated constructor stub
		switch (type) {
		case 1:
			dbname = "tb_icegod_pagetotal";//冰神卡
			break;
		case 2:
			dbname = "tb_dxthird_pagetotal";//低消
			break;
		case 3:
			dbname = "tb_icethird_pagetotal";//冰激凌
			break;
		default:
			break;
		}
	}
	/**
	 * 查询当前时间是否有PV值，并将值返回
	 * @param addtime
	 * @return
	 */
	public Integer selectPVCountDataByAddtime(String addtime) {		
		Integer pvcount = null;
		Connection conn = GetConnect.getAliConnection();
		String sql = "select * from "+dbname+" where addtime = '"+addtime+"'";
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
	public boolean insertPvCountDataByAddtime(String addtime) {
		int inres = 0;
		Connection conn = GetConnect.getAliConnection();
		String sql = "insert into "+dbname+"(pv,addtime) values(1,'"+addtime+"')";
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
	public boolean updatePvCountDataByAddtime(int pvvalue,String addtime) {
		int upres = 0;
		Connection conn = GetConnect.getAliConnection();
		String sql = "update "+dbname+" set pv = "+pvvalue+" where addtime = '"+addtime+"'";
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
