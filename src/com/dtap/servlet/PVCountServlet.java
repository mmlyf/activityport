package com.dtap.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dtap.operation.DBOperation;

import net.sf.json.JSONObject;

/**
 * 
 * @author lvgordon
 * 对浦发银行的办理了信用卡的活动的页面浏览数
 *
 */
@WebServlet("/pvcount")
public class PVCountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PVCountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 
		response.addHeader("Access-Control-Allow-Origin", "*");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String datestr = sdf.format(date);
		int pvcount = DBOperation.getPvCount(datestr);
		if(pvcount==0) {
			pvcount = 1;
			System.out.println("当前时间无记录！");
			DBOperation.insertNewTimePBCount(date);
		}else {
			pvcount+=1;
			DBOperation.updatePvCount(pvcount,datestr);
		}
		JSONObject json = new JSONObject();
		json.put("pvcount", pvcount);
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
