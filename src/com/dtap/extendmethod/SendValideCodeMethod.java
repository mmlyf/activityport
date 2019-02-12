package com.dtap.extendmethod;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.dtap.operation.ValideCodeDBOperation;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class SendValideCodeMethod {
	private static Logger log = Logger.getLogger(SendValideCodeMethod.class);
	private static ExecutorService pool = Executors.newSingleThreadExecutor();
	static {
		PropertyConfigurator.configure(Thread.currentThread().getContextClassLoader().getResource("log.properties").getPath());
	}
	public static boolean sendCode(String phone,String state) {
		boolean result;
		String vailcode = getVailiCode();
		String sendmsg = "";
		if (!state.equals("")) {
			sendmsg = "【视频会员抽奖验证码】"+vailcode+"，请在5分钟 内使用。";
		}else {
			sendmsg = "【河北联通生活号】您的绑定验证码为："+vailcode+"，请在五分钟内使用.";
		}
		System.out.println("phoneNum==========="+phone);
		System.out.println("messageCount==========="+sendmsg);
		Thread thread = new Thread(new HandlerSend(phone, sendmsg,0));
		thread.start();
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = ValideCodeDBOperation.insertVaildCodeToDB(vailcode, phone);
		return result;
	}
	
	private  static void addSendCodeTask(String phone,String message,int type) {
		pool.execute(new HandlerSend(phone, message, type));
	}
	
	public static String getVailiCode() {
		Random random = new Random();
		String fourRandom = random.nextInt(10000) + "";
		int randLength = fourRandom.length();
		if(randLength<4){
			for(int i=1; i<=4-randLength; i++) {
				fourRandom = "0"+fourRandom;
			}
		}
		return fourRandom;
	}
}



