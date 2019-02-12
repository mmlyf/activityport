package com.dtap.alipay;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.dtap.bean.AlipayUserInfo;
import com.dtap.bean.Users;
import com.dtap.operation.AlipayUsersDBOperation;

import net.sf.json.JSONObject;

public class AlipayCheckIsBind {
	private static Logger log = Logger.getLogger(AlipayCheckIsBind.class);
	static {
		PropertyConfigurator.configure("D://HSDT_Activity_port/config/log.properties");
	}
	public static Map<String, Object> isBind(AlipayUserInfo userInfo) {
		Map<String, Object> map = new HashMap<>();
		String openId = userInfo.getOpenId();
		Users users = AlipayUsersDBOperation.getUsersDataByOpenId(openId);
		if (users.getOpenid()!=null&&!users.getOpenid().equals("")) {
			if(users.getMobile()!=null&&!users.getMobile().equals("")) {
				log.info("当前支付宝用户存在，mobile为："+users.getMobile());
				map.put("code", 0);
				map.put("mobile", users.getMobile());
				map.put("openId", users.getOpenid());
			}else {
				log.info("当前支付宝用户存在，但是号码为空");
				map.put("code", 1);
				map.put("mobile", users.getMobile());
				map.put("openId", users.getOpenid());
			}
		}else {
			boolean result = AlipayUsersDBOperation.insertUsersInfo(userInfo);
			log.info("支付宝用户信息保存"+result);
			map.put("code", 1);
			map.put("mobile", "");
			map.put("openId", userInfo.getOpenId());
		}
		return map;
	}
}
