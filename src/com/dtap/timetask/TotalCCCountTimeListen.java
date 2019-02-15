package com.dtap.timetask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.dtap.extendmethod.TimeTaskProcess;
import com.dtap.operation.IceCreamDbOperation;
import com.dtap.operation.OrdersDBOperation;
import com.dtap.operation.TBCCTotalDBOperation;
import com.dtap.timetask.taskimpl.TotalCCCountTimeTask;

/**
 * 
 * @author lvgordon
 * 每个小时对信用卡办理页面的冰淇淋预约数量、低消订购数据、短信下发用户数
 *ListenTimeTaskToTotalCCCount
 */
@WebListener
public class TotalCCCountTimeListen implements ServletContextListener {
	private Logger log = Logger.getLogger(TotalCCCountTimeListen.class);
	private Timer timer = new Timer();
	static {
		PropertyConfigurator.configure("D://HSDT_Activity_port/config/log.properties");
	}
    /**
     * Default constructor. 
     */
    public TotalCCCountTimeListen() {
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
         timer.schedule(new TotalCCCountTimeTask(), date, period);
    }
	
}
