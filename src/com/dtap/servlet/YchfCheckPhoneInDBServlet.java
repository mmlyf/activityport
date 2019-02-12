package com.dtap.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dtap.bean.Ychf;
import com.dtap.operation.YchfDBOperation;

import net.sf.json.JSONObject;

/**
 * 
 * @author lvgordon
 * 对参加预存话费单的号码进行验证是否在号池内
 *
 */
@WebServlet("/checkychf")
public class YchfCheckPhoneInDBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public YchfCheckPhoneInDBServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=utf-8");
		String phone = request.getParameter("phone");
		Ychf ychf = YchfDBOperation.selectDataByPhone(phone);
		JSONObject json = new JSONObject();
		if (ychf.getYcDn()!=null&&!ychf.getYcDn().equals("")) {
			json.put("code", 0);
		}else {
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
