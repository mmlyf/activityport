package com.dtap.operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.dtap.bean.AlipayUserInfo;
import com.dtap.bean.Users;
import com.dtap.connect.GetConnect;

/**
 * 
 * @author lvgordon
 * 支付宝绑定用户的数据操作
 *
 */
public class AlipayUsersDBOperation {
	
	/**
	 * 
	 * @param openId
	 * @return
	 * 通过支付宝ID获取当前用户绑定的数据
	 * 
	 */
	public static Users getUsersDataByOpenId(String openId) {
		Connection conn = GetConnect.getConnection();
		Users users = new Users();
		String sql = "select * from users where OpenId=? ORDER BY AddTime DESC";
		try {
			PreparedStatement ppst = conn.prepareStatement(sql);
			ppst.setString(1, openId);
			ResultSet resultSet = ppst.executeQuery();
			while (resultSet.next()) {
				users.setId(resultSet.getString("Id"));
				users.setNickname(resultSet.getString("NickName"));
				users.setName(resultSet.getString("Name"));
				users.setMobile(resultSet.getString("Mobile"));
				users.setSex(resultSet.getInt("Sex"));
				users.setProvince(resultSet.getString("Province"));
				users.setCity(resultSet.getString("City"));
				users.setCountry(resultSet.getString("Country"));
				users.setHeadimgurl(resultSet.getString("Headimgurl"));
				users.setUnionid(resultSet.getString("Unionid"));
				users.setAddtime(resultSet.getDate("AddTime"));
				users.setModifytime(resultSet.getDate("ModifyTime"));
				users.setDatestate(resultSet.getInt("DataState"));
				users.setNetworktype(resultSet.getString("NetworkType"));
				users.setRegiloca(resultSet.getString("RegistationLocation"));
				users.setOpenid(resultSet.getString("OpenId"));
				users.setUsertype(resultSet.getInt("UserType"));
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
		}
		return users;
	}
	
	/**
	 * 
	 * @param userInfo
	 * @return
	 * 将支付宝绑定用户存入数据表
	 * 
	 */
	public static boolean insertUsersInfo(AlipayUserInfo userInfo) {
		Connection conn = GetConnect.getConnection();
		int res = 0;
		String sql = "insert into users"
				+ "(Id,NickName,Sex,Province,City,HeadImgUrl,AddTime,DataState,OpenId,UserType) "
				+ "values(?,?,?,?,?,?,?,?,?,?)";
		Date date = new Date();
		try {
			UUID uuid = UUID.randomUUID();
			PreparedStatement ppst = conn.prepareStatement(sql);
			ppst.setString(1, uuid.toString());
			ppst.setString(2, userInfo.getNickname());
			if(userInfo.getGender().equals("M")||userInfo.getGender().equals("m")) {
				ppst.setInt(3, 1);
			}else if(userInfo.getGender().equals("F")||userInfo.getGender().equals("f")){
				ppst.setInt(3, 0);
			}
			ppst.setString(4, userInfo.getProvince());
			ppst.setString(5, userInfo.getCity());
			ppst.setString(6, userInfo.getAvator());
			
			Timestamp timestamp = new Timestamp(date.getTime());
			ppst.setTimestamp(7, timestamp);
			ppst.setInt(8, 0);
			ppst.setString(9, userInfo.getOpenId());
			ppst.setInt(10, Integer.parseInt(userInfo.getUsertype()));
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
	 * @param openId
	 * @param mobile
	 * @return
	 * 绑定手机号，将手机号更新值数据表
	 * 
	 */
	public static boolean updateAlipayBindPhoneByOpenId(String openId,String mobile) {
		Connection conn = GetConnect.getConnection();
		int result = 0;
		String sql = "update users set Mobile=? where OpenId = ?";
		try {
			PreparedStatement ppst = conn.prepareStatement(sql);
			ppst.setString(1, mobile);
			ppst.setString(2, openId);
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
}
