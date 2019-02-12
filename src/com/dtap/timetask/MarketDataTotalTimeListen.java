package com.dtap.timetask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.dtap.extendmethod.TimeTaskProcess;
import com.dtap.timetask.taskimpl.MarketDataTotalTimeTask;

/**
 * Application Lifecycle Listener implementation class MarketDataTotalTimeListen
 *
 */
@WebListener
public class MarketDataTotalTimeListen implements ServletContextListener {
	private static Logger log = Logger.getLogger(MarketDataTotalTimeListen.class);
	private Timer timer = new Timer();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static {
		PropertyConfigurator.configure(Thread.currentThread().getContextClassLoader().getResource("log.properties").getPath());
	}
	/**
	 * Default constructor. 
	 */
	public MarketDataTotalTimeListen() {
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
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		calendar.set(year,month, day, 7, 00, 00);
		log.info("执行的时间是："+calendar.getTime());
		Date date = calendar.getTime();
		if (date.before(new Date())) {
			date = TimeTaskProcess.addDay(date, 1);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String datestr = sdf.format(date);
		log.info("当前执行的时间是："+datestr);
		long period = 24*60*60*1000;
		timer.schedule(new MarketDataTotalTimeTask(), date, period);
	}

}
