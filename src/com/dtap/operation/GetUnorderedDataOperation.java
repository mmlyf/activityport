package com.dtap.operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtap.bean.OrderUnsucBean;
import com.dtap.connect.GetConnect;

public class GetUnorderedDataOperation {
	public static List<OrderUnsucBean> getOneHourOrderUnsucData(String starttime,String endtime){
		Connection con = GetConnect.getConnection();
		List<OrderUnsucBean> listbean = new ArrayList<>();
		String sql = "SELECT Mobile,SerialNo,AddTime,dx_firp,dx_firdw\n" + 
				"FROM orders,dsj_dx_all\n" + 
				"WHERE CONCAT('86',Mobile) in (SELECT phone_num FROM mo_receive WHERE ad_time >='"+starttime+"' AND ad_time <='"+endtime+"' AND  (UPPER(message_content) IN ('Y5','Y8','Y13','Y18','Y23','Y28','Y33','Y38','Y43','Y48','Y53','Y58','Y63','Y68','Y73','Y78','Y83','Y88','Y93','Y98','Y108','Y118','Y128','Y138','Y148','Y158','Y168','Y178','Y188','Y198','Y208','Y228','Y258','Y288','Y318','Y350','Y400','Y466')\n" + 
				"OR UPPER(message_content) IN ('L5','L8','L13','L18','L23','L28','L33','L38','L43','L48','L53','L58','L63','L68','L73','L78','L83','L88','L93','L98','L108','L118','L128','L138','L148','L158','L168','L178','L188','L198','L208','L228','L258','L288','L318','L350','L400','L466' )) ORDER BY ad_time DESC) AND Mobile = dx_dn\n" + 
				"AND AddTime >= '"+starttime+"' AND AddTime <= '"+endtime+"' AND (BssState is NULL or BssState != '0')\n" + 
				"UNION\n" + 
				"SELECT Mobile,SerialNo,AddTime,NULL as dx_firp,NULL as dx_firdw\n" + 
				"FROM orders\n" + 
				"WHERE CONCAT('86',Mobile) in (SELECT phone_num FROM mo_receive WHERE ad_time >='"+starttime+"' AND ad_time <='"+endtime+"' AND message_content in ('12','13','10','20','30','50') ORDER BY ad_time DESC)\n" + 
				"AND AddTime >= '"+starttime+"' AND AddTime <= '"+endtime+"' AND (BssState is NULL or BssState != '0') \n" + 
				"ORDER BY AddTime DESC";
		try {
			PreparedStatement ppst = con.prepareStatement(sql);
			ResultSet resultSet = ppst.executeQuery();
			while(resultSet.next()) {
				OrderUnsucBean orderUnsucBean = new OrderUnsucBean();
				orderUnsucBean.setMobile(resultSet.getString("Mobile"));
				orderUnsucBean.setSerialNo(resultSet.getString("SerialNo"));
				orderUnsucBean.setDangw(resultSet.getString("dx_firdw"));
				orderUnsucBean.setProduct(resultSet.getString("dx_firp"));
				orderUnsucBean.setAddtime(resultSet.getTimestamp("AddTime"));
				listbean.add(orderUnsucBean);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listbean;
	}
}
