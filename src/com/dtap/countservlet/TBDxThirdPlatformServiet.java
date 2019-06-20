package com.dtap.countservlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dtap.operation.ThirdPlatformPageTotalDBOperation;

/**
 * Servlet implementation class TBDxThirdPlatformServiet
 */
@WebServlet("/dxthirdtotal")
public class TBDxThirdPlatformServiet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TBDxThirdPlatformServiet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		ThirdPlatformPageTotalDBOperation thirdPlatformPageTotalDBOperation = new ThirdPlatformPageTotalDBOperation(2);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String timestr = sdf.format(date);
 		Integer oldpv = thirdPlatformPageTotalDBOperation.selectPVCountDataByAddtime(timestr);
 		if (oldpv==null) {
			thirdPlatformPageTotalDBOperation.insertPvCountDataByAddtime(timestr);
		}else {
			int newpv = oldpv + 1;
			boolean upres = thirdPlatformPageTotalDBOperation.updatePvCountDataByAddtime(newpv, timestr);
			System.out.println(upres);
		}
 		response.sendRedirect("http://mobile99.uninforun.com/hst/index.php/api/dxuser");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
