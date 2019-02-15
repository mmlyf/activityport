package com.dtap.timetask;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.dtap.extendmethod.TimeTaskProcess;
import com.dtap.operation.GetUnorderedDataOperation;
import com.dtap.timetask.taskimpl.GetOneHourTimeTaskMethod;

/**
 * 
 * @author lvgordon
 * 定时任务，用于获取每个小时内上行有效、且在同一小时内有订购记录但是没有订购成功的号码
 * TimeTaskForGetOneHour
 *
 */
@WebListener
public class GetOneHourTimeListen implements ServletContextListener {
	
	private Logger log = Logger.getLogger(GetOneHourTimeListen.class);
	static {
		PropertyConfigurator.configure("D://HSDT_Activity_port/config/log.properties");
	}
	private Timer timer = new Timer();
    /**
     * Default constructor. 
     */
    public GetOneHourTimeListen() {
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
         Calendar calendar = Calendar.getInstance();
         int year = calendar.get(Calendar.YEAR);
         int month = calendar.get(Calendar.MONTH);
         int day = calendar.get(Calendar.DATE);
         calendar.set(year, month, day, 02, 10, 00);
         Date date = calendar.getTime();
         if (date.before(new Date())) {
			date = TimeTaskProcess.addDay(date, 1);
		}
        long period = 60*60*1000;
        timer.schedule(new GetOneHourTimeTaskMethod(), date, period);
    }
}
