package com.dtap.operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dtap.bean.TBEquityData;
import com.dtap.connect.GetConnect;

public class TBEquityDBOperation {
	
	/**
	 * 
	 * @param dn
	 * @return
	 * 根据号码查询当前号码是否再号池内
	 * 
	 */
	public static String selectPhoneByDN(String dn) {
		Connection con = GetConnect.getAliConnection();
		String sql = "select * from tb_dsj_equity where dn="+dn;
		String resultdn = "";
		try {
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				resultdn = resultSet.getString("dn");
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultdn;
	}
	
	/**
	 * 
	 * @param qynum
	 * @param qyname
	 * @param dn
	 * @param source
	 * @return
	 * 添加权益数据
	 * 
	 */
	public static boolean insertQYData(String qyid,String qyname,String dn,int source) {
		Connection con = GetConnect.getAliConnection();
		String sql = "insert into tb_equity_data(qy_id,qy_name,dn,source,addtime) values(?,?,?,?,?)";
		int result = 0;
		try {
			PreparedStatement ppst =  con.prepareStatement(sql);
			ppst.setString(1, qyid);
			ppst.setString(2, qyname);
			ppst.setString(3, dn);
			ppst.setInt(4, source);
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			ppst.setTimestamp(5, timestamp);
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
	 * @param qynum
	 * @return
	 * 通过权益编码获取权益名称
	 * 
	 */
	public static Map<String, Object> selectQyNameByNum(int qynum) {
		Connection con = GetConnect.getAliConnection();
		String sql = "select * from tb_equity_type where qy_num = "+qynum;
		Map<String, Object> map = new HashMap<>();
		try {
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				map.put("qyid", resultSet.getString("qy_id"));
				map.put("qyname", resultSet.getString("qy_name"));
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 
	 * @param phone
	 * @return
	 * 查询此号码是否已经抽过奖
	 * 
	 */
	public static String selectPhoneInData(String phone) {
		Connection con = GetConnect.getAliConnection();
		String resultphone = "";
		String sql = "select * from tb_equity_data where dn = '"+phone+"'";
		try {
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				resultphone = resultSet.getString("dn"); 
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultphone;
	}
	
	/**
	 * 
	 * @param sectime
	 * @return
	 * 根据时间获取页面点击量和按钮点击数
	 * 
	 */
	public static Map<String, Object> getEquptyPBCount(String sectime) {
		Connection con = GetConnect.getAliConnection();
		String sql = "select * from tb_equity_pctotal where addtime like '"+sectime+" %'";
		boolean isEmpty = true;
		Map<String, Object> map = new HashMap<>();
		try {
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				isEmpty = false;
				map.put("pvcount", resultSet.getInt("pv_count"));
				map.put("bccount", resultSet.getInt("bc_count"));
				map.put("youkucount", resultSet.getInt("youku_count"));
				map.put("aqiycount", resultSet.getInt("aqiy_count"));
				map.put("mgcount", resultSet.getInt("mg_count"));
				map.put("txcount", resultSet.getInt("tx_count"));
				
			}
			map.put("isempty", isEmpty);
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 
	 * @param pvcount
	 * @param sectime
	 * @return
	 * 根据时间更新pv
	 * 
	 */
	public static int updateEquityPvCount(int pvcount,String sectime) {
		Connection con = GetConnect.getAliConnection();
		String sql = "update tb_equity_pctotal set pv_count="+pvcount+" where addtime like '"+sectime+" %'";
		int result = 0 ;
		try {
			Statement statement = con.createStatement();
			result = statement.executeUpdate(sql);
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 * @param pvcount
	 * @param sectime
	 * @return
	 * 根据时间更新pv
	 * 
	 */
	public static int updateEquityBcCount(int bccount,String sectime) {
		Connection con = GetConnect.getAliConnection();
		String sql = "update tb_equity_pctotal set bc_count="+bccount+" where addtime like '"+sectime+" %'";
		int result = 0 ;
		try {
			Statement statement = con.createStatement();
			result = statement.executeUpdate(sql);
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 * @param map
	 * @param sectime
	 * @return
	 * 更新权益的数据量
	 * 
	 */
	public static int updateEquityYALTCount(Map<String, Integer> map, String sectime) {
		Connection con = GetConnect.getAliConnection();
		String sql = "update tb_equity_pctotal set youku_count=?,aqiy_count=?,mg_count=?,tx_count=?"
				+ " where addtime like '"+sectime+" %'";
		int result = 0;
		try {
			PreparedStatement ppst = con.prepareStatement(sql);
			ppst.setInt(1, map.get("youkucount"));
			ppst.setInt(2, map.get("aqiycount"));
			ppst.setInt(3, map.get("mgcount"));
			ppst.setInt(4, map.get("txcount"));
			result = ppst.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 * @param map
	 * @return
	 * 对各项权益数据进行更新
	 * 
	 */
	public static int insertEquityDataForTime(Map<String, Integer> map) {
		Connection con = GetConnect.getAliConnection();
		String sql = "insert into tb_equity_pctotal(pv_count,bc_count,youku_count,aqiy_count,mg_count,tx_count)"
				+ " values(?,?,?,?,?,?)";
		int result = 0;
		try {
			PreparedStatement ppst = con.prepareStatement(sql);
			ppst.setInt(1, map.get("pvcount"));
			ppst.setInt(2, map.get("bccount"));
			ppst.setInt(3, map.get("youkucount"));
			ppst.setInt(4, map.get("aqiycount"));
			ppst.setInt(5, map.get("mgcount"));
			ppst.setInt(6, map.get("txcount"));
			result = ppst.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static List<TBEquityData> selectTbEquityDataByTime(String sectime){
		Connection con = GetConnect.getAliConnection();
		List<TBEquityData> list = new ArrayList<>();
		String sql = "select distinct dn,qy_id,qy_name,source,addtime from tb_equity_data where addtime like '"+sectime+" %'";
		try {
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				TBEquityData tbEquityData = new TBEquityData();
				tbEquityData.setQyId(resultSet.getString("qy_id"));
				tbEquityData.setDn(resultSet.getString("dn"));
				tbEquityData.setQyName(resultSet.getString("qy_name"));
				tbEquityData.setSource(resultSet.getInt("source"));
				tbEquityData.setAddtime(resultSet.getTimestamp("addtime"));
				list.add(tbEquityData);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static boolean updateTBEquityDataState(String phone) {
		Connection conn = GetConnect.getAliConnection();
		String sql = "update tb_equity_data set state=1 where dn='"+phone+"'";
		int upres = 0;
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
	
	/**
	 * 查询当前号码是否已经提交权益数据
	 * @param phone
	 * @return
	 */
	public static Integer selectEquityDataByPhone(String phone) {
		Connection conn = GetConnect.getAliConnection();
		String sql = "select count(1) from tb_equity_data where dn='"+phone+"'";
		Integer rescount = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				rescount = resultSet.getInt("count(1)");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rescount;
	}
	
	public static void main(String[] args) {
		System.out.println("11111");
		List<TBEquityData> list = selectTbEquityDataByTime("2019-02-01");
		for(TBEquityData tbEquityData:list) {
			System.out.println(tbEquityData.getDn()+"/"+tbEquityData.getQyName());
		}
	}
}
