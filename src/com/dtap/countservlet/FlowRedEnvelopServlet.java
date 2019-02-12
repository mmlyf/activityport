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

import com.dtap.operation.TBFlowRedEveDBOperation;
/**
 * 
 * @author lvgordon
 * 流量红包请求接口
 *
 */
@WebServlet("/flowredenve")
public class FlowRedEnvelopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(FlowRedEnvelopServlet.class);
	static {
		PropertyConfigurator.configure("D://HSDT_Activity_port/config/log.properties");
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FlowRedEnvelopServlet() {
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
		Integer countvalue = TBFlowRedEveDBOperation.selectTBFlowCountDataByType(type, sectime);
		if (countvalue==null) {
			boolean inres = TBFlowRedEveDBOperation.insertTBFlowCountByAddtime(sectime);
			if (inres) {
				log.info("流量红包"+sectime+"时间数据新增成功");
			}
		}else {
			int value = countvalue + 1;
			boolean upres = TBFlowRedEveDBOperation.updateTBFlowCountByType(type, value, sectime);
			if (upres) {
				log.info("流量红包type值是"+type+"更新成功");
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
