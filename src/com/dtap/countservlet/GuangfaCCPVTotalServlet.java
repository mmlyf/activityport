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

import com.dtap.operation.TBGFCCTotalDBOperation;


/**
 * Servlet implementation class GuangfaCCPVTotalServlet
 */
@WebServlet("/gfccTotal")
public class GuangfaCCPVTotalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(GuangfaCCPVTotalServlet.class);
	static {
		PropertyConfigurator.configure("D://HSDT_Activity_port/config/log.properties");
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GuangfaCCPVTotalServlet() {
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
		Integer pvValue = TBGFCCTotalDBOperation.selectPVCountDataByAddtime(addtime);
		boolean res = false;
		if (pvValue==null) {
			boolean inres = TBGFCCTotalDBOperation.insertPvCountDataByAddtime(addtime);
			if (inres) {
				log.info(addtime + "当前时间pv插入成功");
				res = true;
			}else {
				log.info(addtime+"当前时间插入失败");
			}
		}else {
			pvValue += 1;
			boolean upres = TBGFCCTotalDBOperation.updatePvCountDataByAddtime(pvValue, addtime);
			if (upres) {
				log.info("当前通建浦发外呼pv更新成功");
				res = true;
			}
		}
		if (res) {
			response.sendRedirect("http://95508.com/zctDQ0KK");
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
