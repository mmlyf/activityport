package com.dtap.timetask.taskimpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dtap.bean.OrderUnsucBean;
import com.dtap.config.Config;
import com.dtap.enums.MailTypeEnum;
import com.dtap.extendmethod.SendMail;
import com.dtap.operation.GetUnorderedDataOperation;

public class GetOneHourTimeTaskMethod extends TimerTask{

	private Logger log = Logger.getLogger(GetOneHourTimeTaskMethod.class);
	static {
		PropertyConfigurator.configure("D://HSDT_Activity_port/config/log.properties");
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int beforehour = hour - 1;
		System.out.println("小时数是："+hour);
		System.out.println("前一个小时数："+beforehour);
		calendar.set(year, month, day, hour, 0, 0);
		Date nowdate = calendar.getTime();
		calendar.set(year, month, day, beforehour, 0, 0);
		Date beforedate = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String starttime = sdf.format(beforedate);
		String endtime = sdf.format(nowdate);
		System.out.println("开始时间："+starttime);
		System.out.println("结束时间："+endtime);
		List<OrderUnsucBean> beanlist = GetUnorderedDataOperation.getOneHourOrderUnsucData(starttime, endtime);
		if (beanlist.isEmpty()) {
			SendMail.sendMailForCommon(starttime+"——"+endtime+"时间段无数据据返回", "", MailTypeEnum.DEVALOP);
		}else {
			String filepath = outputFile(beanlist);
			SendMail.sendMailForCommon("一个小时内上行有效但没有订购成功的数据", filepath,MailTypeEnum.YUNYING);
		}
	}
	
	private String outputFile(List<OrderUnsucBean> list) {
		String filepath = "";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		filepath = Config.FILEPATH + sdf.format(date) + ".xls";
		File file = new File(filepath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("未订购成功数据");
		XSSFRow row = sheet.createRow(0);
		Cell cell1 = row.createCell(0);
		Cell cell2 = row.createCell(1);
		Cell cell3 = row.createCell(2);
		Cell cell4 = row.createCell(3);
		Cell cell5 = row.createCell(4);
		cell1.setCellValue("号码");
		cell2.setCellValue("AGW");
		cell3.setCellValue("产品");
		cell4.setCellValue("档位");
		cell5.setCellValue("时间");
		int i = 1;
		for(OrderUnsucBean orderUnsucBean : list) {
			XSSFRow valuerow = sheet.createRow(i);
			Cell valuecell1 = valuerow.createCell(0);
			Cell valuecell2 = valuerow.createCell(1);
			Cell valuecell3	= valuerow.createCell(2);
			Cell valuecell4 = valuerow.createCell(3);
			Cell valuecell5 = valuerow.createCell(4);
			valuecell1.setCellValue(orderUnsucBean.getMobile());
			valuecell2.setCellValue(orderUnsucBean.getSerialNo());
			valuecell3.setCellValue(orderUnsucBean.getProduct());
			valuecell4.setCellValue(orderUnsucBean.getDangw());
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String timestr = sdf.format(orderUnsucBean.getAddtime());
			valuecell5.setCellValue(timestr);
			i += 1;
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filepath;
	}
}
