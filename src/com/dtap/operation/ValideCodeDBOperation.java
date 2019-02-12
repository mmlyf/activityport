package com.dtap.operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dtap.bean.VaildCodeBean;
import com.dtap.connect.GetConnect;
/**
 * 
 * @author lvgordon
 * 对验证数据表的操作（tb_validcodes）
 *
 */
public class ValideCodeDBOperation { 
	public static boolean insertVaildCodeToDB(String code,String phone) {
		Connection conn = GetConnect.getConnection();
		int res = 0;
		String sql = "insert into tb_validcodes(Code,Used,PhoneNum,SendDate,IsValid,Addtime,DataState) "
				+ "values(?,?,?,?,?,?,?)";
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		try {
			PreparedStatement ppst = conn.prepareStatement(sql);
			ppst.setString(1, code);
			ppst.setInt(2, 0);
			ppst.setString(3, phone);
			ppst.setTimestamp(4, timestamp);
			ppst.setInt(5, 0);
			ppst.setTimestamp(6, timestamp);
			ppst.setInt(7, 0);
			res = ppst.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(res>0) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param phone
	 * @return
	 * 根据号码查找当前号码最近的获取验证码的记录
	 * 
	 */
	public static VaildCodeBean getVaildCodeDetailByPhone(String phone) {
		Connection conn = GetConnect.getConnection();
		VaildCodeBean vaildCodeBean = new VaildCodeBean();
		String sql = "select * from tb_validcodes where PhoneNum=? ORDER BY SendDate DESC LIMIT 1";
		try {
			PreparedStatement ppst = conn.prepareStatement(sql);
			ppst.setString(1, phone);
			ResultSet resultSet = ppst.executeQuery();
			while (resultSet.next()) {
				vaildCodeBean.setId(resultSet.getInt("Id"));
				vaildCodeBean.setCode(resultSet.getString("Code"));
				vaildCodeBean.setPhonenum(resultSet.getString("PhoneNum"));
				vaildCodeBean.setSendDate(resultSet.getTimestamp("SendDate"));
				vaildCodeBean.setIsVaild(resultSet.getInt("IsValid"));
				vaildCodeBean.setAddTime(resultSet.getTimestamp("Addtime"));
				vaildCodeBean.setModifyTime(resultSet.getTimestamp("ModifyTime"));
				vaildCodeBean.setDataState(resultSet.getInt("DataState"));
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vaildCodeBean;
	}
	
	/**
	 * 
	 * @param phone
	 * @return
	 * 验证码验证成功之后，更新数据表中的部分标志数据
	 * 
	 */
	public static boolean updateVaildCodeCheckByPhone(String phone) {
		Connection conn = GetConnect.getConnection();
		int res = 0;
		String sql = "update tb_validcodes set Used=?,"
				+ "CheckDate=?,IsValid=?,DataState=? "
				+ "where PhoneNum=? ORDER BY Addtime DESC LIMIT 1";
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		try {
			PreparedStatement ppst = conn.prepareStatement(sql);
			ppst.setInt(1, 1);
			ppst.setTimestamp(2, timestamp);
			ppst.setInt(3, 1);
			ppst.setInt(4, 1);
			ppst.setString(5, phone);
			res = ppst.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(res>0) {
			return true;
		}else {
			return false;
		}
	}
}
