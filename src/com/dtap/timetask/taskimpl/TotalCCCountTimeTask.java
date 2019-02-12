package com.dtap.timetask.taskimpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.dtap.extendmethod.TimeTaskProcess;
import com.dtap.operation.IceCreamDbOperation;
import com.dtap.operation.OrdersDBOperation;
import com.dtap.operation.TBCCTotalDBOperation;
import com.dtap.timetask.TotalCCCountTimeListen;

public class TotalCCCountTimeTask extends TimerTask{
	private Logger log = Logger.getLogger(TotalCCCountTimeTask.class);
	static {
		PropertyConfigurator.configure("D://HSDT_Activity_port/config/log.properties");
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		log.info("当前数据的执行");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sectime = sdf.format(date);
		String starttime = sectime+" 00:00:00";
		String endtime = sectime + " 23:59:59";
		Integer bcount = TBCCTotalDBOperation.selectCountByType("1", sectime);
		if (bcount!=null) {
			Integer icebookcount = IceCreamDbOperation.selectCountByOneHour(starttime, endtime);
			Integer dxordersuc = OrdersDBOperation.totalOrdersCount(starttime, endtime);
			Integer dxorderunsuc = OrdersDBOperation.totalOrdersUnsucCount(starttime, endtime);
			Integer smusers = TBCCTotalDBOperation.selectCountActRecord(starttime, endtime);
			int icecount = (icebookcount!=null)?icebookcount:0;
			int dxsuccount = (dxordersuc!=null)?dxordersuc:0;
			int dxunsuccount = (dxorderunsuc!=null)?dxorderunsuc:0;
			int smusercount = (smusers!=null)?smusers:0;
			TBCCTotalDBOperation.updateCountByType("12", icecount, sectime);
			TBCCTotalDBOperation.updateCountByType("13", dxsuccount, sectime);
			TBCCTotalDBOperation.updateCountByType("14", dxunsuccount, sectime);
			TBCCTotalDBOperation.updateCountByType("15", smusercount, sectime);
		}else {
			log.info("当前时间无人访问页面");
		}
	}

}
