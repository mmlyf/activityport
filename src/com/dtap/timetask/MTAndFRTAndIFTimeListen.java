package com.dtap.timetask;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.dtap.extendmethod.TimeTaskProcess;
import com.dtap.timetask.taskimpl.MTAndFRTAndIFTimeTask;

/**
 * 
 * @author lvgordon
 * 监听多日包、流量红包、特惠流量大颗粒包定时任务数据统计
 *
 */
@WebListener
public class MTAndFRTAndIFTimeListen implements ServletContextListener {
	private Timer timer = new Timer();
	/**
	 * Default constructor. 
	 */
	public MTAndFRTAndIFTimeListen() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0)  { 
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0)  { 
		// TODO Auto-generat
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		calendar.set(year, month, day, 13, 10, 00);
		Date date = calendar.getTime();
		if (date.before(new Date())) {
			date = TimeTaskProcess.addDay(date, 1);
		}
		long period = 60*60*1000;
		timer.schedule(new MTAndFRTAndIFTimeTask(), date, period);
	}

}
