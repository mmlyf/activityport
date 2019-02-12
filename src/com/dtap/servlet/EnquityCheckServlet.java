package com.dtap.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dtap.extendmethod.CheckVaildCodeMethod;
import com.dtap.operation.TBEquityDBOperation;

import net.sf.json.JSONObject;

/**
 * 
 * @author lvgordon
 * 检查指定权益活动的号码在号池内是否有数据
 *
 */
@WebServlet("/checkEquity")
public class EnquityCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnquityCheckServlet() {
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
		String phone = request.getParameter("phoneNumber");
		String code = request.getParameter("code");
		JSONObject json = CheckVaildCodeMethod.checkValidCode(phone, code);
		JSONObject jsonmap = new JSONObject();
		if (json.getInt("code")==0) {
			String resultdn = TBEquityDBOperation.selectPhoneByDN(phone);//判断当前号码是否在号池内
			if (!resultdn.equals("")) {
				String phonedata = TBEquityDBOperation.selectPhoneInData(phone);//判断当前号码是否已经抽过奖
				if (phonedata.equals("")) {
					jsonmap.put("code", 0);
					jsonmap.put("msg", "");
				}else {
					jsonmap.put("code", 4);
					jsonmap.put("msg", "当前手机号已经抽过奖");
				}				
			}else {
				jsonmap.put("code", 3);
				jsonmap.put("msg", "验证码正确，不在号池内");
			}
		}else if(json.getInt("code")==1) {
			jsonmap.put("code", 1);
			jsonmap.put("msg", "验证码不正确");
		}else {
			jsonmap.put("code", 2);
			jsonmap.put("msg", "验证码超时");
		}
		PrintWriter pw = response.getWriter();
		pw.write(jsonmap.toString());
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
