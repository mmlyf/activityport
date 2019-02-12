package com.dtap.operation;
/**
 * 
 * @author lvgordon
 * 对预存话费的活动的数据的验证和操作
 *
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtap.bean.Ychf;
import com.dtap.bean.YchfOrderBean;
import com.dtap.bean.page.YchfPage;
import com.dtap.connect.GetConnect;

public class YchfDBOperation {
	public static Ychf selectDataByPhone(String phone) {
		Connection con = GetConnect.getConnection();
		String sql = "select * from tb_ychf where yc_dn = ?";
		Ychf ychf = new Ychf();
		try {
			PreparedStatement ppst = con.prepareStatement(sql);
			ppst.setString(1, phone);
			ResultSet resultSet = ppst.executeQuery();
			while(resultSet.next()) {
				ychf.setId(resultSet.getInt("id"));
				ychf.setYcDn(resultSet.getString("yc_dn"));
				ychf.setYcCity(resultSet.getString("yc_city"));
				ychf.setAddtime(resultSet.getDate("addtime"));
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ychf;
	}
	
	public static List<YchfOrderBean> selectYchfOrderData(YchfPage ychfPage) {
		Connection con = GetConnect.getConnection();
		List<YchfOrderBean> list = new ArrayList<>();
		
		String sql = "select * from zfbcz where 1=1 ";
		if(ychfPage.getDn()!=null&&!ychfPage.getDn().equals("")) {
			sql += "AND py_dn like '%"+ychfPage.getDn()+"%' ";
		}
		if(ychfPage.getOrderno()!=null&&!ychfPage.getOrderno().equals("")) {
			sql += "AND py_seno like '%"+ychfPage.getOrderno()+"%' ";
		}
		if(ychfPage.getStarttime()!=null&&!ychfPage.getStarttime().equals("")
				&&ychfPage.getEndtime()!=null&&!ychfPage.getEndtime().equals("")) {
			sql += "AND py_creatime >= '"+ychfPage.getStarttime()+"' AND py_creatime <= '"+ychfPage.getEndtime()+"'";
		}
		
		try {
			PreparedStatement ppst = con.prepareStatement(sql);
			ResultSet resultSet = ppst.executeQuery();
			while(resultSet.next()) {
				YchfOrderBean ychfOrderBean = new YchfOrderBean();
				ychfOrderBean.setPy_dn(resultSet.getString("py_dn"));
				ychfOrderBean.setPy_seno(resultSet.getString("py_seno"));
				ychfOrderBean.setPy_oftitle(resultSet.getString("py_oftitle"));
				ychfOrderBean.setPy_totalmoney(resultSet.getDouble("py_totalmoey"));
				ychfOrderBean.setPy_createtime(resultSet.getTimestamp("py_creatime"));
				ychfOrderBean.setPy_ifpay(resultSet.getInt("py_ifpay"));
				ychfOrderBean.setPy_ofcode(resultSet.getInt("py_ofcode"));
				ychfOrderBean.setPy_zfqd(resultSet.getInt("py_zfqd"));
				list.add(ychfOrderBean);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
