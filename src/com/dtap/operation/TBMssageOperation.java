package com.dtap.operation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dtap.connect.GetConnect;

public class TBMssageOperation {
	public static String selectByMisTitle(String mistitle) {
		Connection conn = GetConnect.getConnection();
		String sql = "select * from tb_mssage where mis_title='"+mistitle+"'";
		String miscontent = "";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				miscontent = resultSet.getString("mis_content");
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return miscontent;
	}
}
