package com.dtap.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dtap.bean.BanKaBean;
import com.dtap.operation.DBOperation;

import net.sf.json.JSONObject;

/**
 * 
 * @author lvgordon
 * 查询办卡活动的统计数量
 *
 */
@WebServlet("/selectbanka")
public class SelectBanKaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectBanKaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<JSONObject> jsonlist = new ArrayList<>();
		List<BanKaBean> list = DBOperation.selectBanKaData();
		int count = 1;
		for(BanKaBean banKaBean : list) {
			JSONObject json = new JSONObject();
			json.put("id", count);
			json.put("pv", banKaBean.getPv());
			json.put("bc", banKaBean.getBc());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String intime = sdf.format(banKaBean.getIntime());
			json.put("time", intime);
			count++;
			jsonlist.add(json);
		}
		JSONObject map = new JSONObject();
		map.put("msg", "");
		map.put("code", 0);
		map.put("data", jsonlist);
		map.put("count", list.size());
		PrintWriter pw = response.getWriter();
		pw.write(map.toString());
		pw.flush();
		pw.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
