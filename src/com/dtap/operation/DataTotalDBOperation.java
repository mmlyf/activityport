package com.dtap.operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.dtap.bean.DataTotalBean;
import com.dtap.connect.GetConnect;


public class DataTotalDBOperation {
	public static boolean insertDataTotalByBean(DataTotalBean bean) {
		Connection conn = GetConnect.getConnection();
		int resflag = 0;
		String sql = "insert into data_total(mtall,mtsuc,mtdissuc,"
				+ "moall,moable,modisable,"
				+ "orderall,ordersuc,orderdissuc"
				+ ",mtrate,morate,orderrate,"
				+ "resorderrate,ordersucrate,add_time,"
				+ "moableice,moabledx,moablellb,"
				+ "ordersucdx,ordersucllb,orderdissucdx,"
				+ "orderdissucllb,icesuc,modxrate,"
				+ "mollbrate,moicerate,ordersucdxrate,ordersucllbrate) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ppst = conn.prepareStatement(sql);
			ppst.setInt(1, bean.getMtall());
			ppst.setInt(2, bean.getMtsuc());
			ppst.setInt(3, bean.getMtdissuc());
			ppst.setInt(4, bean.getMoall());
			ppst.setInt(5, bean.getMoable());
			ppst.setInt(6, bean.getModisable());
			ppst.setInt(7, bean.getOrderall());
			ppst.setInt(8, bean.getOrdersuc());
			ppst.setInt(9, bean.getOrderdissuc());
			ppst.setFloat(10, bean.getMtrate());
			ppst.setFloat(11, bean.getMorate());
			ppst.setFloat(12, bean.getOrderrate());
			ppst.setFloat(13, bean.getResorderrate());
			ppst.setFloat(14, bean.getOrdersucrate());
			ppst.setString(15, bean.getAdtime());
			ppst.setInt(16,bean.getMoableice());
			ppst.setInt(17, bean.getMoabledx());
			ppst.setInt(18, bean.getMoablellb());
			ppst.setInt(19, bean.getOrdersucdx());
			ppst.setInt(20, bean.getOrdersucllb());
			ppst.setInt(21, bean.getOrderdissucdx());
			ppst.setInt(22, bean.getOrderdissucllb());
			ppst.setInt(23, bean.getIcesuc());
			ppst.setFloat(24, bean.getModxrate());
			ppst.setFloat(25, bean.getMollbrate());
			ppst.setFloat(26, bean.getMoicerate());
			ppst.setFloat(27, bean.getOrdersucdxrate());
			ppst.setFloat(28, bean.getOrdersucllbrate());
			resflag = ppst.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resflag>0) {
			return true;
		}else {
			return false;
		}
	}
}
