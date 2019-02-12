package com.dtap.extendmethod;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.dtap.config.Config;
import com.dtap.enums.MailTypeEnum;
import com.sun.mail.util.MailSSLSocketFactory;

public class SendMail {
	private static Address[] receiceone ;
	private static Address[] receivedeve;
	static {
		try {
			receiceone = InternetAddress.parse("32223815@qq.com,1453806177@qq.com");
			receivedeve = InternetAddress.parse("1453806177@qq.com");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 设置配置信息
	 * @return
	 */
	private static Properties setProperties() {
		Properties properties = new Properties();
		properties.setProperty("mail.debug", "true");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.host", Config.MAILHOST);
		properties.setProperty("mail.transport.protocol", "smtp");
		try {
			MailSSLSocketFactory factory = new MailSSLSocketFactory();
			factory.setTrustAllHosts(true);
			properties.put("mail.smtp.ssl.enable", "true");
			properties.put("mail.smtp.ssl.socketFactory", factory);
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return properties;
	}
	
	/**
	 * 
	 * @param msg
	 * @param filepath
	 * @param type
	 * 发送邮件
	 * 
	 */
	public static void sendMailForCommon(String msg,String filepath,MailTypeEnum type) {
		Properties properties = setProperties();
		Session session = Session.getInstance(properties);
		Message message = new MimeMessage(session);
		try {
			message.setSubject("汇视达通邮件下发通知");
			message.setFrom(new InternetAddress(Config.MAILACCOUNT, "汇视达通"));
			Multipart part = new MimeMultipart();
			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(msg, "text/html;charset=utf-8");
			part.addBodyPart(bodyPart);
			if (!"".equals(filepath)&&filepath!=null) {
				BodyPart filebody = new MimeBodyPart();
				FileDataSource fSource = new FileDataSource(filepath);
				filebody.setDataHandler(new DataHandler(fSource));
				String filename = MimeUtility.encodeWord(fSource.getFile().getName());
				filebody.setFileName(filename);
				part.addBodyPart(filebody);
			}
			message.setContent(part);
			Transport transport = session.getTransport();
			transport.connect(Config.MAILHOST, Config.MAILACCOUNT, Config.MAILPASSWORD);
			switch (type) {
			case YUNYING:
				transport.sendMessage(message, receiceone);
				break;
			case DEVALOP:
				transport.sendMessage(message, receivedeve);
				break;
			default:
				break;
			}
			transport.close();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
