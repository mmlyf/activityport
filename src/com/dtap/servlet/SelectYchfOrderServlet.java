package com.dtap.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dtap.bean.YchfOrderBean;
import com.dtap.bean.page.YchfPage;
import com.dtap.operation.YchfDBOperation;

import net.sf.json.JSONObject;

/**
 * 
 * @author lvgordon
 * 查询话费订单的数量
 *
 */
@WebServlet("/selectychf")
public class SelectYchfOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectYchfOrderServlet() {
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
		String dn = request.getParameter("dn");
		String orderno = request.getParameter("orderno");
		String datestar = request.getParameter("date_star");
		String dateend = request.getParameter("date_end");
		YchfPage ychfPage = new YchfPage();
		ychfPage.setDn(dn);
		ychfPage.setEndtime(dateend);
		ychfPage.setStarttime(datestar);
		ychfPage.setOrderno(orderno);
		List<YchfOrderBean> list = YchfDBOperation.selectYchfOrderData(ychfPage);
		JSONObject jsonmap = new JSONObject();
		List<JSONObject> jsonlist = new ArrayList<>();
		int i = 1;
		for(YchfOrderBean ychfOrderBean : list) {
			JSONObject json = new JSONObject();
			json.put("id", i);
			json.put("orderno", ychfOrderBean.getPy_seno());
			json.put("dn", ychfOrderBean.getPy_dn());
			json.put("proname", ychfOrderBean.getPy_oftitle());
			json.put("price", ychfOrderBean.getPy_totalmoney());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String creatime = sdf.format(ychfOrderBean.getPy_createtime());
			json.put("ordertime", creatime);
			if(ychfOrderBean.getPy_ifpay()==0) {
				json.put("zfstate", "未充值");
			}else if(ychfOrderBean.getPy_ifpay()==1) {
				json.put("zfstate", "已充值");
			}else {
				json.put("zfstate", "无效");
			}
			if(ychfOrderBean.getPy_ofcode()==1) {
				json.put("czstate", "充值成功");
			}else if(ychfOrderBean.getPy_ofcode()==0) {
				json.put("czstate", "充值中");
			}else {
				json.put("czstate", "撤销（失败）");
			}
			if(ychfOrderBean.getPy_zfqd()==1) {
				json.put("qudao", "支付宝");
			}else if(ychfOrderBean.getPy_zfqd()==2) {
				json.put("qudao", "微信");
			}else {
				json.put("qudao", "");
			}
			i++;
			jsonlist.add(json);
		}
		jsonmap.put("code", 0);
		jsonmap.put("msg", "");
		jsonmap.put("data", jsonlist);
		jsonmap.put("count", list.size());
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
