package com.dtap.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
 * 流量红包权益数据保存
 */
@WebServlet("/trafficred")
public class TrafficRedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(TrafficRedServlet.class);
	static {
		PropertyConfigurator.configure("D://HSDT_Activity_port/config/log.properties");
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrafficRedServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Origin", "*");
		String phone = request.getParameter("phone");
		int qynum = Integer.parseInt(request.getParameter("qynum"));
		Map<String, Object> map = TBEquityDBOperation.selectQyNameByNum(qynum);
		String qyid = (String) map.get("qyid");
		String qyname = (String) map.get("qyname");
		Integer rescount = TBEquityDBOperation.selectEquityDataByPhone(phone);
		Integer code = null;
		if (rescount==null||rescount==0) {
			boolean inres = TBEquityDBOperation.insertQYData(qyid, qyname, phone, 3);
			if (inres) {
				code = 0;//权益数据保存成功
			}else {
				code = 1;//权益数据保存失败
			}
		}else {
			log.info("号码"+phone+"已经提交！");
			code = 2;//你已提交订单
		}

		JSONObject json = new JSONObject();

		log.info("流量红包数据存入成功");
		json.put("code", code);
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
