package com.dtap.timetask.taskimpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.dtap.bean.InFlowBean;
import com.dtap.operation.MutidayTotalDBOperation;
import com.dtap.operation.OrdersDBOperation;
import com.dtap.operation.TBCCTotalDBOperation;
import com.dtap.operation.TBFlowRedEveDBOperation;
import com.dtap.operation.TBThFlowTotalDBOperation;

import sun.util.logging.resources.logging;
/**
 * 多日包、流量红包、特惠流量大颗粒包定时任务的实现
 * @author lvgordon
 *
 */
public class MTAndFRTAndIFTimeTask extends TimerTask{
	private Logger log = Logger.getLogger(MTAndFRTAndIFTimeTask.class);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String timestr = sdf.format(date);
		String starttime = timestr+" 00:00:00";
		String endtime = timestr + " 23:59:59";
		
		/**
		 * 信用卡多日包定时数据统计
		 */
		Integer sixcount = OrdersDBOperation.selectMutidayCount(starttime, endtime, "1");
		Integer ninecount = OrdersDBOperation.selectMutidayCount(starttime, endtime, "2");
		log.info("查询six的值是："+sixcount);
		int six = sixcount!=null?sixcount:0;
		int nine = ninecount!=null?ninecount:0;
		log.info("比较完之后的six的值是："+six);
		boolean upsix = TBCCTotalDBOperation.updateCountByType("17", six, timestr);
		boolean upnine = TBCCTotalDBOperation.updateCountByType("18", nine, timestr);
		boolean muupsix = MutidayTotalDBOperation.updateDataByAddtime(six, "3", timestr);
		boolean muupnine = MutidayTotalDBOperation.updateDataByAddtime(nine, "4", timestr);
		log.info("信用卡多日包6.6元订购记录数"+upsix);
		log.info("信用卡多日包9.9元订购记录数"+upnine);
		log.info("多日包6.6元订购记录数"+muupsix);
		log.info("多日包9.9元订购记录数"+muupnine);
		
		/**
		 * 流量红包数据定时统计
		 */
		Integer flowordersuc = OrdersDBOperation.selectTwentySevenday(starttime, endtime, "0");
		Integer floworderunsuc = OrdersDBOperation.selectTwentySevenday(starttime, endtime, "1");
		int ordersuc = flowordersuc!=null?flowordersuc:0;
		int orderunsuc = floworderunsuc!=null?floworderunsuc:0;
		boolean upflowsuc = TBFlowRedEveDBOperation.updateTBFlowCountByType("8", ordersuc, timestr);
		boolean upflowunsuc = TBFlowRedEveDBOperation.updateTBFlowCountByType("9", orderunsuc, timestr);
		
		log.info("流量红包20元7天假日包订购成功数更新"+upflowsuc);
		log.info("流量红包20元7天假日包订购失败数更新"+upflowunsuc);
		/**
		 * 特惠流量包定时数据统计
		 */
		Integer _3gsuccount = OrdersDBOperation.selectIndulgenceFlowTotal(starttime, endtime, "3", "0");
		Integer _3gunsuccount = OrdersDBOperation.selectIndulgenceFlowTotal(starttime, endtime, "3", "1");
		Integer _8gsuccount = OrdersDBOperation.selectIndulgenceFlowTotal(starttime, endtime, "8", "0");
		Integer _8gunsuccount = OrdersDBOperation.selectIndulgenceFlowTotal(starttime, endtime, "8", "1");
		Integer _12gsuccount = OrdersDBOperation.selectIndulgenceFlowTotal(starttime, endtime, "12", "0");
		Integer _12gunsuccount = OrdersDBOperation.selectIndulgenceFlowTotal(starttime, endtime, "12", "1");
		Integer _25gsuccount = OrdersDBOperation.selectIndulgenceFlowTotal(starttime, endtime, "25", "0");
		Integer _25gunsuccount = OrdersDBOperation.selectIndulgenceFlowTotal(starttime, endtime, "25", "1");
		Integer _40gsuccount = OrdersDBOperation.selectIndulgenceFlowTotal(starttime, endtime, "40", "0");
		Integer _40gunsuccount = OrdersDBOperation.selectIndulgenceFlowTotal(starttime, endtime, "40", "1");
		
		_3gsuccount = _3gsuccount!=null?_3gsuccount:0;
		_3gunsuccount = _3gunsuccount!=null?_3gunsuccount:0;
		_8gsuccount = _8gsuccount!=null?_8gsuccount:0;
		_8gunsuccount = _8gunsuccount!=null?_8gunsuccount:0;
		_12gsuccount = _12gsuccount!=null?_12gsuccount:0;
		_12gunsuccount = _12gunsuccount!=null?_12gunsuccount:0;
		_25gsuccount = _25gsuccount!=null?_25gsuccount:0;
		_25gunsuccount = _25gunsuccount!=null?_25gunsuccount:0;
		_40gsuccount = _40gsuccount!=null?_40gsuccount:0;
		_40gunsuccount = _40gunsuccount!=null?_40gunsuccount:0;
		
		InFlowBean inFlowBean = new InFlowBean(_3gsuccount, _3gunsuccount, _8gsuccount, _8gunsuccount, _12gsuccount, _12gunsuccount, _25gsuccount, _25gunsuccount, _40gsuccount, _40gunsuccount);
		boolean uporderres = TBThFlowTotalDBOperation.updateIndulgenceFlowOrderData(timestr, inFlowBean);
		log.info("更新特惠流量包订购数据统计结果："+uporderres);
	}

}
