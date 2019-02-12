package com.dtap.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.Configurator;

import com.dtap.bean.DsjIceDataBean;
import com.dtap.operation.IceCreamDbOperation;

import net.sf.json.JSONObject;

/**
 * 
 * @author lvgordon
 * 申请客诉预约的请求
 *
 */
@WebServlet("/customservice")
public class CustomServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(CustomServiceServlet.class);
	static {
		PropertyConfigurator.configure("D://HSDT_Activity_port/config/log.properties");
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomServiceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=utf-8");
		String phone = request.getParameter("phone");
		JSONObject json = new JSONObject();
		log.info("号码："+phone+"申请客服预约！");
		DsjIceDataBean dsjIceDataBean = IceCreamDbOperation.selectIceDsjData(phone);
		if (dsjIceDataBean.getDx_dn()!=null) {
			boolean result = IceCreamDbOperation.addDsjCustomService(dsjIceDataBean);
			if (result) {
				log.info("号码："+phone+"客服预约成功！");
				json.put("code", 0);
			}else {
				log.info("号码："+phone+"客服预约失败！");
				json.put("code", 2);
			}
		}else {
			log.info("号码："+phone+"不在号池内!");
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
