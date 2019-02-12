package com.dtap.extendmethod;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.dtap.bean.VaildCodeBean;
import com.dtap.operation.ValideCodeDBOperation;

import net.sf.json.JSONObject;
/**
 * 
 * @author lvgordon
 * 验证码验证的方法，根据号码判断当前验证码中是否正确
 *
 */
public class CheckVaildCodeMethod {
	private static Logger log = Logger.getLogger(CheckVaildCodeMethod.class);
	static {
		PropertyConfigurator.configure(Thread.currentThread().getContextClassLoader().getResource("log.properties").getPath());
	}
	
	public static JSONObject checkValidCode(String phone,String code) {
		JSONObject json = new JSONObject();
		VaildCodeBean vaildCodeBean = ValideCodeDBOperation.getVaildCodeDetailByPhone(phone);
		Date date = new Date();
		long delaytime = date.getTime() - vaildCodeBean.getSendDate().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info(sdf.format(vaildCodeBean.getSendDate()));
		log.info(delaytime);
		if (delaytime>5*60*1000) {
			log.info("当前验证码超时");
			json.put("result", true);
			json.put("data", false);
			json.put("code", 2);
		}else {
			if (vaildCodeBean.getCode().equals(code)) {
				boolean res = ValideCodeDBOperation.updateVaildCodeCheckByPhone(phone);
				if (res) {
					log.info("当前更新成功！");
				}
				json.put("result", true);
				json.put("data", true);
				json.put("code", 0);
			}else {
				json.put("result", true);
				json.put("data", false);
				json.put("code", 1);
			}
		}
		return json;
	}
}
