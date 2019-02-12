package com.dtap.operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dtap.bean.InFlowBean;
import com.dtap.connect.GetConnect;

/**
 * 
 * @author lvgordon
 * 特惠流量红包统计数据库操作
 *
 */
public class TBThFlowTotalDBOperation {

	/**
	 * 根据type和时间获取指定的值。
	 * @param type
	 * @param sectime
	 * @return
	 */
	public static Integer selectThFlowTotalDataByType(String type,String sectime) {
		Connection conn = GetConnect.getAliConnection();
		String sql = "select * from tb_tehuiflow_total where addtime='"+sectime+"'";
		Integer resvalue = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				switch (type) {
				case "1"://页面pv的值
					resvalue = resultSet.getInt("pv");
					break;
				case "2"://点击立即办理按钮的点击数
					resvalue = resultSet.getInt("liji_bc");
					break;
				case "3"://确认按钮点击数
					resvalue = resultSet.getInt("comfir_bc");
					break;
				case "4"://弹框去多日包页面数
					resvalue = resultSet.getInt("tan_seebc");
					break;
				case "5"://3g流量包订购成功数
					resvalue = resultSet.getInt("3g_succount");
					break;
				case "6":
					resvalue = resultSet.getInt("8g_succount");
					break;
				case "7":
					resvalue = resultSet.getInt("12g_succount");
					break;
				case "8":
					resvalue = resultSet.getInt("25g_succount");
					break;
				case "9":
					resvalue = resultSet.getInt("40g_succount");
					break;
				case "10"://3G订购不成功数
					resvalue = resultSet.getInt("3g_unsuccount");
					break;
				case "11":
					resvalue = resultSet.getInt("8g_unsuccount");
					break;
				case "12":
					resvalue = resultSet.getInt("12g_unsuccount");
					break;
				case "13":
					resvalue = resultSet.getInt("25g_unsuccount");
					break;
				case "14":
					resvalue = resultSet.getInt("40g_unsuccount");
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
		return resvalue;
	}

	/**
	 * 新增一条数据记录
	 * @param sectime
	 * @return
	 */
	public static boolean insertThFlowTotalDataByTime(String sectime) {
		Connection con = GetConnect.getAliConnection();
		String sql = "insert into tb_tehuiflow_total(pv,liji_bc,comfir_bc,"
				+ "tan_seebc,3g_succount,8g_succount,12g_succount,25g_succount,"
				+ "40g_succount,3g_unsuccount,8g_unsuccount,12g_unsuccount,25g_unsuccount,40g_unsuccount,addtime)"
				+ "values(1,0,0,0,0,0,0,0,0,0,0,0,0,0,'"+sectime+"')";
		int inres = 0;
		try {
			Statement statement = con.createStatement();
			inres = statement.executeUpdate(sql);
			con.close();
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

	public static boolean updateThFlowTotalDataByType(String type,String sectime,Integer value) {
		Connection conn = GetConnect.getAliConnection();
		int upres = 0;
		String sql = "update tb_tehuiflow_total set ";
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
			sql += "tan_seebc="+value;
			break;
		case "5":
			sql += "3g_succount="+value;
			break;
		case "6":
			sql += "8g_succount="+value;
			break;
		case "7":
			sql += "12g_succount="+value;
			break;
		case "8":
			sql += "25g_succount="+value;
			break;
		case "9":
			sql += "40g_succount="+value;
			break;
		case "10":
			sql += "3g_unsuccount="+value;
			break;
		case "11":
			sql += "8g_unsuccount="+value;
			break;
		case "12":
			sql += "12g_unsuccount="+value;
			break;
		case "13":
			sql += "25g_unsuccount="+value;
			break;
		case "14":
			sql += "40g_unsuccount="+value;
			break;
		default:
			break;
		}
		sql += " where addtime='"+sectime+"'";
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

	public static boolean updateIndulgenceFlowOrderData(String sectime,InFlowBean bean) {
		Connection conn = GetConnect.getAliConnection();
		int upres = 0;
		String sql = "update tb_tehuiflow_total set 3g_succount=?,3g_unsuccount=?,8g_succount=?,8g_unsuccount=?,"
				+ "12g_succount=?,12g_unsuccount=?,25g_succount=?,25g_unsuccount=?,40g_succount=?,40g_unsuccount=? "
				+ " where addtime=?";
		try {
			PreparedStatement ppst = conn.prepareStatement(sql);
			ppst.setInt(1, bean.get_3gsuccount());
			ppst.setInt(2, bean.get_3gunsuccount());
			ppst.setInt(3, bean.get_8gsuccount());
			ppst.setInt(4, bean.get_8gunsuccount());
			ppst.setInt(5, bean.get_12gsuccount());
			ppst.setInt(6, bean.get_12gunsuccount());
			ppst.setInt(7, bean.get_25gsuccount());
			ppst.setInt(8, bean.get_25gunsuccount());
			ppst.setInt(9, bean.get_40gsuccount());
			ppst.setInt(10, bean.get_40gunsuccount());
			ppst.setString(11, sectime);
			upres = ppst.executeUpdate();
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
