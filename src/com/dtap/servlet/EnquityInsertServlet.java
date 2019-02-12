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
 * 将权益活动的数据存入指定的数据表中
 *
 */
@WebServlet("/insertData")
public class EnquityInsertServlet extends HttpServlet {
	private Logger log = Logger.getLogger(EnquityInsertServlet.class);
	static {
		PropertyConfigurator.configure("D://HSDT_Activity_port/config/log.properties");
	}
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnquityInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=utf-8");
		String phone = request.getParameter("phoneNumber");
		int qynum = Integer.parseInt(request.getParameter("type"));
		int source = Integer.parseInt(request.getParameter("source"));//1:老虎机2：大转盘
		Map<String, Object> qymap = TBEquityDBOperation.selectQyNameByNum(qynum);
		String qyname = (String) qymap.get("qyname");
		String qyid = (String) qymap.get("qyid");
		boolean result = TBEquityDBOperation.insertQYData(qyid, qyname, phone,source);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String datestr = sdf.format(date);
		Map<String, Object> map = TBEquityDBOperation.getEquptyPBCount(datestr);
		int mgcount = (int) map.get("mgcount");
		int txcount = (int) map.get("txcount");
		int aqiycount = (int) map.get("aqiycount");
		int youkucount = (int) map.get("youkucount");
		switch (qynum) {
		case 1:
			mgcount += 1;
			break;
		case 2:
			txcount += 1;
			break;
		case 3:
			aqiycount += 1;
			break;
		case 4:
			youkucount +=1;
			break;
		default:
			break;
		}
		Map<String, Integer> yaltmap = new HashMap<>();
		yaltmap.put("mgcount", mgcount);
		yaltmap.put("txcount", txcount);
		yaltmap.put("aqiycount", aqiycount);
		yaltmap.put("youkucount", youkucount);
		int upcount = TBEquityDBOperation.updateEquityYALTCount(yaltmap, datestr);
		if (upcount>0) {
			log.info("权益数据更新成功");
		}else {
			log.info("权益数据更新失败");
		}
		JSONObject json = new JSONObject();
		if (result) {
			json.put("code", 0);
		}else {
			json.put("code", 1);
		}
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
