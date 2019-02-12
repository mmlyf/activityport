package com.dtap.extendmethod;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.dtap.bean.TBAcrecord;
import com.dtap.operation.TBACRecordOpration;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class HandlerSend implements Runnable{
	private Logger log = Logger.getLogger(HandlerSend.class);

	private String phoneNumber ;
	private String messageContent;
	private int type;//0:验证码短信下发；1：活动触发的短信
	//产品接入号
//	String spNumber="10655883";
	String spNumber = "1065572778";//河北接入号
	//业务代码
	String servcieType="90860230";
	//linkId
	String linkId = "MOODDDS";
	char reportflag = '1';
	public HandlerSend(String phoneNumber,String messageContent,int type) {
		this.phoneNumber = phoneNumber;
		this.messageContent = messageContent;
		this.type = type;
	}
	public void run() {
		// TODO Auto-generated method stub
		String strxml;
		try {
			strxml = "{<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
					+ "<gwsmip>\n" + "  <message_header>\n"
					+ "    <command_id>0x3</command_id>\n"
					+ "    <sequence_number/>\n" + "  </message_header>\n"
					+ "  <message_body>\n" + "    <pk_total>1</pk_total>\n"
					+ "    <pk_number>1</pk_number>\n" + "    <user_numbers>\n"
					+ "<user_number>"+phoneNumber+"</user_number>"
					+ "    </user_numbers>\n"
					+ "    <sp_number>"+spNumber+"</sp_number>\n"
					+ "    <service_type>"+servcieType+"</service_type>\n"
					+ "    <link_id>"+linkId+"</link_id>\n"
					+ "    <message_content>" + Base64.encode(messageContent.getBytes())
					+ "</message_content>\n"
					+ "    <report_flag>"+reportflag+"</report_flag>\n"
					+ "   </message_body>\n" + "</gwsmip>\n}";

			System.out.println("strxml的值是："+strxml);
			if (type==1) {
				TBAcrecord acrecord = new TBAcrecord();
				acrecord.setMisContent(messageContent);
				acrecord.setDn(phoneNumber);
				boolean res = TBACRecordOpration.insert(acrecord);
				if (res) {
					log.info("存入成功");
				}
			}
			Socket socket = new Socket("127.0.0.1", 8805);
			OutputStream out = socket.getOutputStream();
			out.write(strxml.getBytes());
			out.flush();
			out.close();
			Thread.sleep(1000);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
