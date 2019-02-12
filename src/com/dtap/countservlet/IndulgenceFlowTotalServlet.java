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

import com.dtap.operation.TBThFlowTotalDBOperation;

/**
 * 新春特惠流量包的数据统计接口
 * @author lvgordon
 *
 */
@WebServlet("/inflowtotal")
public class IndulgenceFlowTotalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(IndulgenceFlowTotalServlet.class);
	static {
		PropertyConfigurator.configure("D://HSDT_Activity_port/config/log.properties");
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndulgenceFlowTotalServlet() {
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
		String type = request.getParameter("type");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sectime = sdf.format(date);
		Integer totalvalue = TBThFlowTotalDBOperation.selectThFlowTotalDataByType(type, sectime);
		if (totalvalue==null) {
			boolean inres = TBThFlowTotalDBOperation.insertThFlowTotalDataByTime(sectime);
			log.info("特惠流量包添加时间"+sectime+"结果："+inres);
		}else {
			int value = totalvalue + 1;
			boolean upres = TBThFlowTotalDBOperation.updateThFlowTotalDataByType(type, sectime, value);
			log.info("特惠流量包type为"+type+"数据更新"+upres);
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
