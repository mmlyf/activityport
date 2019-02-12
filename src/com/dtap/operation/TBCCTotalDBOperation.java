package com.dtap.operation;
/**
 * 
 * @author lvgordon
 * 对信用卡的pv和按钮数量的统计
 * 
 *
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dtap.connect.GetConnect;

import sun.launcher.resources.launcher;

public class TBCCTotalDBOperation {
	
	/**
	 * 
	 * @param type
	 * @param sectime
	 * @return
	 * 根据特定的type值查询数量
	 * 
	 */
	public static Integer selectCountByType(String type,String sectime) {
		Connection con = GetConnect.getAliConnection();
		String sql = "select * from tb_cctotal where addtime ='"+sectime+"'";
		Integer selres = null;
		try {
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				switch (type) {
				case "1"://大页面的pv
					selres = resultSet.getInt("b_pv");
					break;
				case "2"://立即申请的bc
					selres = resultSet.getInt("li_allbc");
					break;
				case "3"://弹框中的bc
					selres = resultSet.getInt("tan_seebc");
					break;
				case "4"://弹框中ice的pv
					selres = resultSet.getInt("tan_seeicepv");
					break;
				case "5"://弹框中dx的pv
					selres = resultSet.getInt("tan_seedxpv");
					break;
				case "6"://点击去看看的冰淇淋预约的bc
					selres = resultSet.getInt("tanli_icebc");
					break;
				case "7"://点击去看看的低消的订购bc
					selres = resultSet.getInt("tanli_dxbc");
					break;
				case "8"://关闭弹框后信用卡的pv
					selres = resultSet.getInt("ccc_pv");
					break;
				case "9"://点击立即申请后号码不在号池内的信用卡的pv
					selres = resultSet.getInt("li_npccpv");
					break;
				case "10"://直接点击去看看的冰淇淋页面pv
					selres = resultSet.getInt("see_icepv");
					break;
				case "11"://直接点击去看看低消的页面pv
					selres = resultSet.getInt("see_dxpv");
					break;
				case "12"://冰淇淋预约数
					selres = resultSet.getInt("ice_bookc");
					break;
				case "13"://低消订购成功数
					selres = resultSet.getInt("dx_ordersucc");
					break;
				case "14"://低消订购失败数
					selres = resultSet.getInt("dx_ordersunsucc");
					break;
				case "15"://短信下发的用户数
					selres = resultSet.getInt("sm_sendc");
					break;
				case "16"://流量包页面pv
					selres = resultSet.getInt("see_llbpv");
					break;
				case "17"://6.6元流量包订购数量
					selres = resultSet.getInt("six_orderc");
					break;
				case "18"://9.9元流量包订购数	
					selres = resultSet.getInt("nine_orderc");
					break;
				case "19"://多日包快点点击按钮数
					selres = resultSet.getInt("muti_bc");
					break;
				case "20"://特惠流量包订购点击按钮数
					selres = resultSet.getInt("tehui_bc");
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
		return selres;
	}
	
	/**
	 * 
	 * @param type
	 * @param value
	 * @param sectime
	 * @return
	 * 更新指定的数量
	 * 
	 */
	public static boolean updateCountByType(String type,int value,String sectime) {
		Connection con = GetConnect.getAliConnection();
		Integer upres = 0;
		String sql = "update tb_cctotal set ";
		switch (type) {
		case "1"://大页面的pv
			sql += "b_pv="+value;
			break;
		case "2"://立即申请的bc
			sql += "li_allbc="+value;
			break;
		case "3"://弹框中的bc
			sql += "tan_seebc="+value;
			break;
		case "4"://弹框中ice的pv
			sql += "tan_seeicepv="+value;
			break;
		case "5"://弹框中dx的pv
			sql += "tan_seedxpv="+value;
			break;
		case "6"://点击去看看的冰淇淋预约的bc
			sql += "tanli_icebc="+value;
			break;
		case "7"://点击去看看的低消的订购bc
			sql += "tanli_dxbc="+value;
			break;
		case "8"://关闭弹框后信用卡的pv
			sql += "ccc_pv="+value;
			break;
		case "9"://点击立即申请后号码不在号池内的信用卡的pv
			sql += "li_npccpv="+value;
			break;
		case "10"://直接点击去看看的冰淇淋页面pv
			sql += "see_icepv="+value;
			break;
		case "11"://直接点击去看看低消的页面pv
			sql += "see_dxpv="+value;
			break;
		case "12"://冰淇淋预约数
			sql += "ice_bookc="+value;
			break;
		case "13"://低消订购成功数
			sql += "dx_ordersucc="+value;
			break;
		case "14"://低消订购失败数
			sql += "dx_ordersunsucc="+value;
			break;
		case "15"://短信下发的用户数
			sql += "sm_sendc="+value;
			break;
		case "16":
			sql += "see_llbpv="+value;
			break;
		case "17":
			sql += "six_orderc="+value;
			break;
		case "18":
			sql += "nine_orderc="+value;
			break;
		case "19"://多日包按钮数
			sql+="muti_bc="+value;
			break;
		case "20"://特惠包订购按钮数
			sql+="tehui_bc="+value;
			break;			
		default:
			break;
		}
		sql += " where addtime='"+sectime+"'";
		try {
			Statement statement = con.createStatement();
			upres = statement.executeUpdate(sql);
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(upres>0) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param sectime
	 * @return
	 * 添加新时间的记录
	 * 
	 */
	public static boolean insertCountByTime(String sectime) {
		Connection con = GetConnect.getAliConnection();
		String sql = "insert into tb_cctotal"
				+ "(b_pv,li_allbc,tan_seebc,tan_seeicepv,tan_seedxpv,tanli_icebc,tanli_dxbc,ccc_pv,li_npccpv,"
				+ "see_icepv,see_dxpv,see_llbpv,ice_bookc,dx_ordersucc,dx_ordersunsucc,six_orderc,nine_orderc,sm_sendc,muti_bc,tehui_bc,addtime) "
				+ "values(1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'"+sectime+"')";
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
	
	/**
	 * 获取当前活动页面触发的短信的用户数
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public static Integer selectCountActRecord(String starttime,String endtime) {
		Connection con = GetConnect.getAliConnection();
		String sql = "select count(distinct dn) from tb_acrecord where adtime>='"+starttime+"' and adtime <= '"+endtime+"'";
		Integer actcount = null;
		try {
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				actcount = resultSet.getInt("count(distinct dn)");
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return actcount;
	}
}
