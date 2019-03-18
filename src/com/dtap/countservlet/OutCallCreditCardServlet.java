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

import com.dtap.operation.OutCallCcountDBOperation;

/**
 * Servlet implementation class OutCallCreditCardServlet
 */
@WebServlet("/outcallcount")
public class OutCallCreditCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(CreditCardPVServlet.class);
	static {
		PropertyConfigurator.configure("D://HSDT_Activity_port/config/log.properties");
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OutCallCreditCardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String addtime = sdf.format(date);
		Integer pvcount = OutCallCcountDBOperation.selectOutCallCCPvCountByTime(addtime);
		if (pvcount!=null) {
			pvcount += 1;
			boolean upres = OutCallCcountDBOperation.updateOutCallPvCountCCByAddtime(pvcount, addtime);
			if (upres) {
				log.info("外呼信用卡页面数据更新成功");
				response.sendRedirect("https://ecentre.spdbccc.com.cn/creditcard/indexActivity.htm?data=I2442238&itemcode=NJFH000012");
			}else {
				log.info("外呼信用卡页面数据更新失败");
			}
		}else {
			boolean inres = OutCallCcountDBOperation.insertCalloutCCPvByAddtime(addtime);
			if (inres) {
				log.info(addtime+"当前时间数据插入成功");
				response.sendRedirect("https://ecentre.spdbccc.com.cn/creditcard/indexActivity.htm?data=I2442238&itemcode=NJFH000012");
			}else {
				log.info(addtime+"当前数据插入失败");
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
