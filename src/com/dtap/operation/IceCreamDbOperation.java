package com.dtap.operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import com.dtap.bean.DsjIceDataBean;
import com.dtap.connect.GetConnect;

/**
 * 
 * @author lvgordon
 * 冰激凌预约数据提交和查询
 * 
 */

public class IceCreamDbOperation {
	
	public static DsjIceDataBean selectIceDsjData(String phone) {
		Connection conn = GetConnect.getConnection();
		DsjIceDataBean dsjIceDataBean = new DsjIceDataBean();
		String sql = "select * from dsj_ice_all where dx_dn= ? ORDER BY dx_adtime";
		try {
			PreparedStatement ppst = conn.prepareStatement(sql);
			ppst.setString(1, phone);
			System.out.println("查询出来的是指："+sql);
			ResultSet resultSet = ppst.executeQuery();
			while (resultSet.next()) {
				System.out.println("查询出来的是指："+resultSet.getString("dx_city"));
				dsjIceDataBean.setDx_city(resultSet.getString("dx_city"));
				dsjIceDataBean.setDx_ap(resultSet.getString("dx_ap"));
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
	/**
	 * 
	 * @param bean
	 * @return
	 * 增加客服服务数据
	 * 
	 */
	public static boolean addDsjCustomService(DsjIceDataBean bean) {
		Connection conn = GetConnect.getConnection();
		int res = 0;
		String sql = "insert into custom_service"
				+ "(dx_city,dx_dn,dx_ap,dx_sys,dx_inn,dx_rh,dx_rhlx,dx_firp,dx_firdw,dx_ifdx,dx_qd,dx_addtime) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ppst = conn.prepareStatement(sql);
			ppst.setString(1, bean.getDx_city());
			ppst.setString(2, bean.getDx_dn());
			ppst.setString(3, bean.getDx_ap());
			ppst.setString(4, bean.getDx_sys());
			ppst.setString(5, bean.getDx_inn());
			ppst.setString(6, bean.getDx_rh());
			ppst.setString(7, bean.getDx_rhlx());
			ppst.setString(8, bean.getDx_firp());
			ppst.setString(9, bean.getDx_firdw());
			ppst.setString(10, bean.getDx_ifdx());
			ppst.setInt(11, 0);
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			ppst.setTimestamp(12, timestamp);
			res = ppst.executeUpdate();
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
	 * 
	 * @param bean
	 * @return
	 * 冰激凌预约数据添加至数据表
	 * 
	 */
	public static boolean addIceDsjBookData(DsjIceDataBean bean) {
		Connection conn = GetConnect.getConnection();
		int result = 0;
		String sql = "insert into ice_dsj_orders"
				+"(dx_city,dx_dn,dx_ap,dx_sys,dx_inn,dx_rh,dx_rhlx,dx_firp,dx_firdw,dx_ifdx,dx_qd,dx_addtime) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ppst = conn.prepareStatement(sql);
			ppst.setString(1, bean.getDx_city());
			ppst.setString(2, bean.getDx_dn());
			ppst.setString(3, bean.getDx_ap());
			ppst.setString(4, bean.getDx_sys());
			ppst.setString(5, bean.getDx_inn());
			ppst.setString(6, bean.getDx_rh());
			ppst.setString(7, bean.getDx_rhlx());
			ppst.setString(8, bean.getDx_firp());
			ppst.setString(9, bean.getDx_firdw());
			ppst.setString(10, bean.getDx_ifdx());
			ppst.setInt(11, 0);
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			ppst.setTimestamp(12, timestamp);
			result = ppst.executeUpdate();
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
	
	/**
	 * 统计冰淇淋预约数据
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public static Integer selectCountByOneHour(String starttime,String endtime) {
		Connection conn = GetConnect.getConnection();
		String sql = "select count(1) from ice_dsj_orders where dx_addtime >='"+starttime+"' and dx_addtime <= '"+endtime+"'";
		Integer iceres = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				iceres = resultSet.getInt("count(1)");
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return iceres;
	}
}
