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

import com.dtap.operation.DBOperation;
import com.dtap.operation.TBEquityDBOperation;

import net.sf.json.JSONObject;

/**
 * 
 * @author lvgordon
 * 统计权益页面的浏览数
 *
 */
@WebServlet("/equitypv")
public class EnquityPVCountServlet extends HttpServlet {
	private Logger log = Logger.getLogger(EnquityInsertServlet.class);
	static {
		PropertyConfigurator.configure("D://HSDT_Activity_port/config/log.properties");
	}
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnquityPVCountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 
		response.addHeader("Access-Control-Allow-Origin", "*");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String datestr = sdf.format(date);
		int pvcount = 0;
		Map<String, Object> map = TBEquityDBOperation.getEquptyPBCount(datestr);
		int upincount = 0;
		if ((boolean) map.get("isempty")) {
			Map<String, Integer> mapin = new HashMap<>();
			pvcount = 1;
			mapin.put("pvcount", pvcount);
			mapin.put("bccount", 0);
			mapin.put("youkucount", 0);
			mapin.put("aqiycount", 0);
			mapin.put("mgcount", 0);
			mapin.put("txcount", 0);
			upincount = TBEquityDBOperation.insertEquityDataForTime(mapin);
		}else {
			pvcount = (int) map.get("pvcount") + 1;
			upincount = TBEquityDBOperation.updateEquityPvCount(pvcount, datestr);
		}
		if (upincount>0) {
			log.info("数据pv更新成功！");
		}else {
			log.info("数据pv更新失败！");
		}
		JSONObject json = new JSONObject();
		json.put("pvcount", pvcount);
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
