package com.dtap.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dtap.extendmethod.SendValideCodeMethod;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class ValideCodeServlet
 */
@WebServlet("/sendcode")
public class SendValideCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendValideCodeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String phone = request.getParameter("phoneNumber");
		String state ;
		if (request.getParameter("state")==null||request.getParameter("state").equals("")) {
			state = "";
		}else {
			state = request.getParameter("state");
		}
		boolean result = SendValideCodeMethod.sendCode(phone,state);
		JSONObject json = new JSONObject();
		if (result) {
			json.put("result", true);
			json.put("data", true);
		}else {
			json.put("result", true);
			json.put("data", false);
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
