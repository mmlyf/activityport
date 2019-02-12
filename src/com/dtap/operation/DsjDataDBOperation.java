package com.dtap.operation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dtap.bean.DsjIceDataBean;
import com.dtap.connect.GetConnect;
import com.dtap.enums.DsjDbType;

/**
 * 	
 * @author lvgordon
 * 对大数据号池的操作（冰淇淋和低消的数据）
 *
 */
public class DsjDataDBOperation {
	
	public static DsjIceDataBean selectDataInDsjByPhone(String phone,DsjDbType type) {
		Connection conn = GetConnect.getConnection();
		String sql = "";
		switch (type) {
		case DX:
			sql = "select * from dsj_dx_all where dx_dn = "+phone;
			break;
		case ICE:
			sql = "select * from dsj_ice_all where dx_dn = "+phone;
			break;
		default:
			break;
		}
		DsjIceDataBean dsjIceDataBean = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				dsjIceDataBean = new DsjIceDataBean();
				dsjIceDataBean.setDx_ap(resultSet.getString("dx_ap"));
				dsjIceDataBean.setDx_city(resultSet.getString("dx_city"));
				dsjIceDataBean.setDx_dn(resultSet.getString("dx_dn"));
				dsjIceDataBean.setDx_firdw(resultSet.getString("dx_firdw"));
				dsjIceDataBean.setDx_firp(resultSet.getString("dx_firp"));
				dsjIceDataBean.setDx_ifdx(resultSet.getString("dx_ifdx"));
				dsjIceDataBean.setDx_inn(resultSet.getString("dx_inn"));
				dsjIceDataBean.setDx_rh(resultSet.getString("dx_rh"));
				dsjIceDataBean.setDx_rhlx(resultSet.getString("dx_rhlx"));
				dsjIceDataBean.setDx_sys(resultSet.getString("dx_sys"));
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dsjIceDataBean;
	}
	
	public static int countShortMessageSend(String dn,String sectime) {
		Connection alicon = GetConnect.getAliConnection();
		String sql = "select count(dn) from tb_acrecord where dn='"+dn+"' "
				+ "and adtime like '"+sectime+"%' ORDER BY adtime DESC" ;
		int dncount = 0;
		try {
			Statement statement = alicon.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				dncount = resultSet.getInt("count(dn)");
			}
			alicon.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dncount;
	}
}
