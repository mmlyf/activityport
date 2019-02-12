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

import com.dtap.bean.DsjIceDataBean;
import com.dtap.operation.IceCreamDbOperation;

import net.sf.json.JSONObject;

/**
 * 
 * @author lvgordon
 * 获取当前号码冰淇淋的档位请求
 *
 */
@WebServlet("/geticedw")
public class GetIceDataDwServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(CustomServiceServlet.class);
	static {
		PropertyConfigurator.configure("D://HSDT_Activity_port/config/log.properties");
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetIceDataDwServlet() {
        super();
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
		DsjIceDataBean dsjIceDataBean = IceCreamDbOperation.selectIceDsjData(phone);
		JSONObject json = new JSONObject();
		if(dsjIceDataBean.getDx_dn()!=null) {
			json.put("code", 0);
			json.put("dw", dsjIceDataBean.getDx_firdw());
		}else {
			log.info("号码："+phone+"不在号池内！");
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
