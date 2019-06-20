package com.dtap.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dtap.extendmethod.CheckDataIsInPhonePool;
import com.dtap.extendmethod.CheckVaildCodeMethod;
import com.dtap.operation.DBOperation;

import net.sf.json.JSONObject;

/**
 * 
 * @author lvgordon
 * 判断当前号码是否在号池内（低消和冰淇淋）
 *
 */
@WebServlet("/checkInPool")
public class CheckDataInPhonePool extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckDataInPhonePool() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 用户点击链接后台逻辑处理
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=utf-8");
		String phone = request.getParameter("phone");
		JSONObject json = new JSONObject();
		Map<String, String> map = CheckDataIsInPhonePool.selectDataInPool(phone);//判断当前号码是否在号池内
		if (map!=null) {
			json.put("code", 0);
			json.put("product", map.get("firp"));
			json.put("dangw", map.get("dw"));
			json.put("type", map.get("type"));
		}else {
			json.put("code", 1);//验证码正确不在号池内
			json.put("product", "");
			json.put("dangw", ""	);
			json.put("type", "");
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
