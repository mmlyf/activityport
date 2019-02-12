package com.dtap.operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.dtap.bean.BanKaBean;
import com.dtap.connect.GetConnect;

/**
 * 
 * @author lvgordon
 * 浦发银行办卡活动的pv和uv的统计
 *
 */
public class DBOperation {
	private static Logger log = Logger.getLogger(DBOperation.class);
	/**
	 * 
	 * @param pvcount
	 * @param adtime
	 * 更新pv的数量
	 * 
	 */
	public static void updatePvCount(int pvcount,String adtime) {
		Connection con = GetConnect.getConnection();
		String sql = "update tb_banka set ac_pv=? where adtime like '"+adtime+" %'";
		int result = 0;
		try {
			PreparedStatement ppst = con.prepareStatement(sql);
			ppst.setInt(1, pvcount);
			result = ppst.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result>0) {
			log.info("数据更新成功！");
		}else {
			log.info("数据更新失败");
		}
	}
	
	/**
	 * 
	 * @param adtime
	 * @return
	 * 获取按钮的数量
	 * 
	 */
	public static int getBcCount(String adtime) {
		Connection con = GetConnect.getConnection();
		String sql = "select ac_bc from tb_banka where adtime like '"+adtime+" %'";
		int pv_count = 0;
		try {
			PreparedStatement ppst = con.prepareStatement(sql);
			ResultSet resultSet = ppst.executeQuery();
			while(resultSet.next()) {
				pv_count = resultSet.getInt("ac_bc");
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pv_count;
 	}
	/**
	 * 
	 * @param bccount
	 * @param adtime
	 * 更新按钮的数量
	 * 
	 */
	public static void updateBcCount(int bccount,String adtime) {
		Connection con = GetConnect.getConnection();
		String sql = "update tb_banka set ac_bc=? where adtime like '"+adtime+" %'";
		int result = 0;
		try {
			PreparedStatement ppst = con.prepareStatement(sql);
			ppst.setInt(1, bccount);
			result = ppst.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result>0) {
			log.info("数据更新成功！");
		}else {
			log.info("数据更新失败");
		}
	}
	/**
	 * 
	 * @param adtime
	 * @return
	 * 获取页面浏览量的数量
	 * 
	 */
	public static int getPvCount(String adtime) {
		Connection con = GetConnect.getConnection();
		String sql = "select ac_pv from tb_banka where adtime like '"+adtime+" %'";
		int pv_count = 0;
		try {
			PreparedStatement ppst = con.prepareStatement(sql);
			ResultSet resultSet = ppst.executeQuery(sql);
			while(resultSet.next()) {
				pv_count = resultSet.getInt("ac_pv");
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pv_count;
 	}
	
	/**
	 * 
	 * @param adtime
	 * @return
	 * 当前时间没有记录，则新插入当前时间的数据
	 * 
	 */
	public static boolean insertNewTimePBCount(Date adtime) {
		Connection con = GetConnect.getConnection();
		int result = 0;
		String sql = "insert into tb_banka(ac_name,ac_pv,ac_bc,adtime) values(?,?,?,?)";
		try {
			PreparedStatement ppst = con.prepareStatement(sql);
			ppst.setString(1, "办卡");
			ppst.setInt(2, 1);
			ppst.setInt(3, 0);
			Timestamp timestamp = new Timestamp(adtime.getTime());
			ppst.setTimestamp(4, timestamp);
			result = ppst.executeUpdate();
			 con.close();
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
	 * 
	 * @return
	 * 
	 * 
	 */
	public static List<BanKaBean> selectBanKaData() {
		Connection con = GetConnect.getConnection();
		String sql  = "select * from tb_banka";
		List<BanKaBean> list = new ArrayList<>();
		try {
			Statement state = con.createStatement();
			ResultSet resultSet = state.executeQuery(sql);
			while (resultSet.next()) {
				BanKaBean banKaBean = new BanKaBean();
				banKaBean.setPv(resultSet.getInt("ac_pv"));
				banKaBean.setBc(resultSet.getInt("ac_bc"));
				banKaBean.setIntime(resultSet.getDate("adtime"));
				list.add(banKaBean);	
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
