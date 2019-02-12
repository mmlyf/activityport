package com.dtap.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.dtap.alipay.AlipayCheckIsBind;
import com.dtap.alipay.AlipayConfig;
import com.dtap.alipay.AlipaySubmit;
import com.dtap.bean.AlipayUserInfo;
import com.dtap.extendmethod.HTTPRequest;

import net.sf.json.JSONObject;

/**
 * 
 * @author lvgordon
 * 支付宝请求的入口
 *
 */
@WebServlet("/bindindex")
public class AlipayBindForGift extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlipayBindForGift() {
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
		String redict_url = "http://221.192.138.29:8089/HSDT_Activity_Port/callBack";
		String state = request.getParameter("state");
		redict_url = URLEncoder.encode(redict_url);
		String url = AlipayConfig.ALIPAY_URL+"?app_id="+AlipayConfig.APP_ID+"&scope=auth_user,auth_base&redirect_uri="+redict_url+"&state="+state;
		PrintWriter pw = response.getWriter();
		pw.write("<script>window.location.href=\""+url+"\"</script>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
