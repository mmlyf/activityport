package com.dtap.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.dtap.operation.TBEquityDBOperation;

import net.sf.json.JSONObject;

/**
 * 
 * @author lvgordon
 * 对权益数据的按钮点击数量的增加
 *
 */
@WebServlet("/equitybc")
public class EnquityBCCountServlet extends HttpServlet {
	private Logger log = Logger.getLogger(EnquityInsertServlet.class);
	static {
		PropertyConfigurator.configure("D://HSDT_Activity_port/config/log.properties");
	}
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EnquityBCCountServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO 
		response.addHeader("Access-Control-Allow-Origin", "*");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String datestr = sdf.format(date);
		int bccount = 0;
		Map<String, Object> map = TBEquityDBOperation.getEquptyPBCount(datestr);
		bccount = (int) map.get("bccount") + 1;
		int upcount = TBEquityDBOperation.updateEquityBcCount(bccount, datestr);
		if (upcount>0) {
			log.info("当前按钮数量更新");
		}else {
			log.info("按钮数量更新失败");
		}
		JSONObject json = new JSONObject();
		json.put("bccount", bccount);
		PrintWriter pw = response.getWriter();
		pw.write(json.toString());
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
