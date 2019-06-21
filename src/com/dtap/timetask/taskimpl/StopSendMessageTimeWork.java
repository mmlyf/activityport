package com.dtap.timetask.taskimpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.dtap.bean.TBEquityData;
import com.dtap.config.Config;
import com.dtap.enums.MailTypeEnum;
import com.dtap.extendmethod.HTTPRequest;
import com.dtap.extendmethod.SendMail;
import com.dtap.operation.OrdersDBOperation;
import com.dtap.operation.TBEquityDBOperation;

/**
 * 
 * @author lvgordon
 * 执行统计权益数据至ftp服务器的定时任务实现类
 *
 */
public class StopSendMessageTimeWork extends TimerTask {
	private Logger log = Logger.getLogger(StopSendMessageTimeWork.class);
	private SimpleDateFormat sdf = null;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		HTTPRequest.sendGet("http://aimk.mobile99.cn:8089/HSDT_Market_Platform/timetask/stopwork", "");
	}
	
}
