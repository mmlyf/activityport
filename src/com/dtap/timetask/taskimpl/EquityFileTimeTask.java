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
import com.dtap.extendmethod.SendMail;
import com.dtap.operation.OrdersDBOperation;
import com.dtap.operation.TBEquityDBOperation;

/**
 * 
 * @author lvgordon
 * 执行统计权益数据至ftp服务器的定时任务实现类
 *
 */
public class EquityFileTimeTask extends TimerTask {
	private Logger log = Logger.getLogger(EquityFileTimeTask.class);
	private SimpleDateFormat sdf = null;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Date date = new Date();
		long newtimeval = date.getTime() - 24*60*60*1000;
		date = new Date(newtimeval);
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sectime = sdf.format(date);
		List<TBEquityData> list = TBEquityDBOperation.selectTbEquityDataByTime(sectime);
		sdf = new SimpleDateFormat("yyyyMMdd");
		String filename = "qycj_result_"+sdf.format(date)+".txt";
		String filepath = outputDataToFTP(list,filename);
		String message = "Today's equity data already in FTP Service,It Service path is in "+filepath;
		log.debug(message);
		SendMail.sendMailForCommon(message, "", MailTypeEnum.DEVALOP);
	}

	/**
	 * 
	 * @param list
	 * @param filename
	 * @return
	 * 将文件导出并存入txt文本，保存至ftp服务器上
	 * 
	 */
	private String outputDataToFTP(List<TBEquityData> list,String filename) {
		String allpath = Config.EQUITYOUT+filename;
		System.out.println(allpath);
		System.out.println(list.size());
		try {
			File file = new File(allpath);
			if (!file.exists()) {
				boolean createfile = createFile(allpath);
				if (!createfile) {
					log.debug("文件创建失败；");
				}
			}
			FileWriter fw = new FileWriter(allpath);
			BufferedWriter bWriter = new BufferedWriter(fw);
			if (list.isEmpty()) {
				log.info("当前无权益数据！");
				bWriter.write("");
				bWriter.newLine();
			}else {
				for(TBEquityData tbEquityData:list) {
					if (tbEquityData.getSource()==3) {
						Integer bssstate = OrdersDBOperation.selectDataState(tbEquityData.getDn(),tbEquityData.getAddtime());
						if (bssstate!=null&&bssstate==0) {
							String str = tbEquityData.getQyId()+"|"
									+tbEquityData.getDn()+"|"
									+tbEquityData.getQyName();
							bWriter.write(str);
							bWriter.newLine();	
							TBEquityDBOperation.updateTBEquityDataState(tbEquityData.getDn());
						}
					}else {
						String str = tbEquityData.getQyId()+"|"
								+tbEquityData.getDn()+"|"
								+tbEquityData.getQyName();
						bWriter.write(str);
						bWriter.newLine();	
						TBEquityDBOperation.updateTBEquityDataState(tbEquityData.getDn());
					}

				}
			}
			bWriter.flush();
			bWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return allpath;
	}
	/*************************************************
	 * 
	 * @param allpath
	 * @return
	 * 创建指定的文件
	 */
	private boolean createFile(String allpath) {
		File file = new File(allpath);
		boolean isCreate = false;
		if (!file.exists()) {
			try {
				isCreate = file.createNewFile();
				if(isCreate) {
					log.info("文件创建成功");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				isCreate = false;
				log.error("创建文件失败");
				log.error(e.getMessage());
			}
		}
		return isCreate;
	}
}
