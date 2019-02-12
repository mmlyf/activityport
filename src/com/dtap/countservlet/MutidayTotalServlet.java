package com.dtap.countservlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.dtap.operation.MutidayTotalDBOperation;

/**
 * Servlet implementation class MutidayTotalServlet
 */
@WebServlet("/mutidaytotal")
public class MutidayTotalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(CreditCardPVServlet.class);
	static {
		PropertyConfigurator.configure("D://HSDT_Activity_port/config/log.properties");
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MutidayTotalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Orogin", "*");
		String type = request.getParameter("type");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sectime = sdf.format(date);
		Integer selvalue = MutidayTotalDBOperation.selectDataByAddtime(sectime, type);
		if (selvalue!=null) {
			selvalue += 1;
			boolean upres = MutidayTotalDBOperation.updateDataByAddtime(selvalue, type, sectime);
			if (upres) {
				String str = "";
				switch (type) {
				case "1":	
					str +="pv";
					break;
				case "2":
					str += "bc";
					break;
				case "3":
					str += "six_orderc";
					break;
				case "4":
					str+="nine_orderc";
					break;
				default:
					break;
				}
				log.info(str+"数据更新成功");
			}
		}else {
			boolean inres = MutidayTotalDBOperation.insertDataForAddtime(sectime);
			if (inres) {
				log.info("多日包"+sectime+"数据插入成功！");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
