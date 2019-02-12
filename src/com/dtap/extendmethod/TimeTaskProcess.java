package com.dtap.extendmethod;

import java.util.Calendar;
import java.util.Date;

public class TimeTaskProcess {
	/**
	 * 
	 * @param date
	 * @param num
	 * @return
	 * 在当前时间上添加一天
	 * 
	 */
	public static Date addDay(Date date, int num) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.DAY_OF_MONTH, num);
		return startDT.getTime();
	}
}
