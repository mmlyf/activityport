package com.dtap.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.dtap.alipay.AlipayCheckIsBind;
import com.dtap.alipay.AlipaySubmit;
import com.dtap.bean.AlipayUserInfo;

import net.sf.json.JSONObject;

/**
 * 
 * @author lvgordon
 * 支付宝的回调函数
 *
 */
@WebServlet("/callBack")
public class AlipayCallBackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(AlipayCallBackServlet.class);
	static {
		PropertyConfigurator.configure("D://HSDT_Activity_port/config/log.properties");
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AlipayCallBackServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		Map<String,String> params = new HashMap<String,String>();
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession();
		Map requestParams = request.getParameterMap();
		
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == (values.length-1)) ? valueStr + values[i]:valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		String accessToken= AlipaySubmit.buildRequest(params);
		AlipayUserInfo alipayUserInfo = new AlipayUserInfo();
		Map<String, Object> map = new HashMap<>();
		if(accessToken!=null && accessToken!=""){
			AlipayUserInfoShareResponse userInfoShareResponse  =  AlipaySubmit.get(accessToken);
			if (userInfoShareResponse!=null&&userInfoShareResponse.isSuccess()) {
				if (userInfoShareResponse.getCode().equals("10000")) {
					alipayUserInfo.setOpenId(userInfoShareResponse.getUserId());
					alipayUserInfo.setAvator(userInfoShareResponse.getAvatar());
					alipayUserInfo.setProvince(userInfoShareResponse.getProvince());
					alipayUserInfo.setCity(userInfoShareResponse.getCity());
					alipayUserInfo.setNickname(userInfoShareResponse.getNickName());
					alipayUserInfo.setGender(userInfoShareResponse.getGender());
					alipayUserInfo.setIscertified(userInfoShareResponse.getIsCertified());
					alipayUserInfo.setIsstudent(userInfoShareResponse.getIsStudentCertified());
					alipayUserInfo.setUserstatus(userInfoShareResponse.getUserStatus());
					alipayUserInfo.setUsertype(userInfoShareResponse.getUserType());
					map = AlipayCheckIsBind.isBind(alipayUserInfo);
				}else if(userInfoShareResponse.getCode().equals("20000")){
					map.put("code", 20000);
					map.put("msg", "系统繁忙！");
				}
			}else {
				map.put("code", 404);
				map.put("msg", "请求用户资料失败！");
				log.error("请求用户资料失败！");
			}
		}else {
			map.put("code", 404);
			map.put("msg", "请求失败");
			log.error("accessToken获取失败！");
		}
		int code = (int) map.get("code");
		if(params.get("state").equals("1001")) {//关注有礼
			String mobile = (String) map.get("mobile");
			String openId = (String) map.get("openId");
			String param = "mobile="+mobile+"&openId="+openId;
			if(code==0) {
//				String url = "http://localhost:8085/HSDT_Activity_Port/AlipayLife/view/binding.jsp?mobile="+json.getString("mobile")+"&openId="+json.getString("openId");
				String url = "http://221.192.138.29:8089/HSDT_Activity_Port/AlipayLife/view/bindsuc.jsp?"+param;
				response.sendRedirect(url);
			}else if(code==1) {
				String url = "http://221.192.138.29:8089/HSDT_Activity_Port/AlipayLife/view/binding.jsp?"+param;
				response.sendRedirect(url);
			}
		}else if(params.get("state").equals("1002")){//绑定号码
			String mobile = (String) map.get("mobile");
			String openId = (String) map.get("openId");
			String param = "mobile="+mobile+"&openId="+openId;
			if(code==0) {
//				String url = "http://localhost:8085/HSDT_Activity_Port/AlipayLife/view/register.jsp?mobile="+json.getString("mobile")+"&openId="+json.getString("openId");
				String url = "http://221.192.138.29:8089/HSDT_Activity_Port/AlipayLife/view/register.jsp?"+param;
				response.sendRedirect(url);
			}else if(code==1){
//				String url = "http://localhost:8085/HSDT_Activity_Port/AlipayLife/view/binding.jsp?mobile="+json.getString("mobile")+"&openId="+json.getString("openId");
				String url = "http://221.192.138.29:8089/HSDT_Activity_Port/AlipayLife/view/binding.jsp?"+param;
				response.sendRedirect(url);
			}else {
				log.error("请求失败！");
			}
		}else if(params.get("state").equals("1003")){//锦鲤
			String openId = (String) map.get("openId");
			String param = "openid="+openId;
			String url = "http://mobile99.uninforun.com/hst/index.php/api/Bncf?"+param;
			response.sendRedirect(url);
		}else if(params.get("state").equals("1004")){//余量查询
			String openId = (String) map.get("openId");
			String param = "openId="+openId;
			String url = "http://mobile99.uninforun.com/unicom-hb/Home/UserDetail?"+param;
			response.sendRedirect(url);
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
