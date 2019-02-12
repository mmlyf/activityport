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

import com.dtap.operation.TBCCTotalDBOperation;

/**
 * 
 * @author lvgordon
 * 用于统计新信用卡办理页面的pv
 *
 */
@WebServlet("/ccpc")
public class CreditCardPVServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(CreditCardPVServlet.class);
	static {
		PropertyConfigurator.configure("D://HSDT_Activity_port/config/log.properties");
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreditCardPVServlet() {
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
		Integer oldcount = TBCCTotalDBOperation.selectCountByType(type, sectime);
		if (oldcount==null) {
			boolean inres = TBCCTotalDBOperation.insertCountByTime(sectime);
			if (inres) {
				log.info("信用卡页面新时间"+sectime+"数据添加成功");
			}
		}else {
			int nowcount = oldcount + 1;
			boolean upres = TBCCTotalDBOperation.updateCountByType(type, nowcount, sectime);
			if (upres) {
				log.info("信用卡页面"+type+"数据更新成功");
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
