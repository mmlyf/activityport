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

import com.dtap.bean.Users;
import com.dtap.extendmethod.CheckVaildCodeMethod;
import com.dtap.extendmethod.FlowGiftMethod;
import com.dtap.operation.AlipayUsersDBOperation;

import net.sf.json.JSONObject;

/**
 * 
 * @author lvgordon
 * 支付宝绑定页面的绑定接口
 *
 */
@WebServlet("/savebind")
public class AlipaySaveBindServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(AlipaySaveBindServlet.class);
	static {
		PropertyConfigurator.configure("D://HSDT_Activity_port/config/log.properties");
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlipaySaveBindServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		String mobile = request.getParameter("mobile");
		String code = request.getParameter("code");
		String openId = request.getParameter("openId");
		JSONObject checkcode = CheckVaildCodeMethod.checkValidCode(mobile, code);
		if (checkcode.getBoolean("result")&&checkcode.getBoolean("data")) {
			Users users = AlipayUsersDBOperation.getUsersDataByOpenId(openId);
			if (users.getMobile()!=null&&users.getMobile().equals(mobile)) {
				log.info("当前号码未更新！");
				json.put("code", 0);
				json.put("msg", "绑定成功");
			}else {
				boolean result = AlipayUsersDBOperation.updateAlipayBindPhoneByOpenId(openId, mobile);
				if (result) {
					log.info("号码更新成功，并赠送流量！");
					json.put("code", 0);
					json.put("msg", "绑定成功");
					FlowGiftMethod.flowGiftMethod(mobile, 200);
				}else {
					json.put("code", 2);
					json.put("msg", "手机号绑定失败，稍后重试......!");
				}
			}
		}else {
			json.put("code", 1);
			json.put("msg", "验证码不正确");
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
