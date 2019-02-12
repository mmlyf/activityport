package com.dtap.operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.dtap.bean.TBAcrecord;
import com.dtap.connect.GetConnect;

/**
 * 
 * @author lvgordon
 * 点击活动出发的短信的记录
 * 
 *
 */
public class TBACRecordOpration {
	public static boolean insert(TBAcrecord acrecord) {
		Connection con = GetConnect.getAliConnection();
		int inres = 0;
		String sql = "insert into tb_acrecord(mis_content,dn) values(?,?)";
		try {
			PreparedStatement ppst = con.prepareStatement(sql);
			ppst.setString(1, acrecord.getMisContent());
			ppst.setString(2, acrecord.getDn());
			inres = ppst.executeUpdate();
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
}
