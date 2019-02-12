package com.dtap.extendmethod;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sun.corba.se.spi.orb.StringPair;

import net.sf.json.JSONObject;

/**
 * 
 * @author lvgordon
 * 流量赠送
 *
 */
public class FlowGiftMethod {
	private static Logger log = Logger.getLogger(FlowGiftMethod.class);
	private static String path = "http://mobile99.uninforun.com/unicom-hb/api/Unicom/PresendFlow";
	
	public static void flowGiftMethod(String mobile,Integer flow) {
		JSONObject paramjson = new JSONObject();
		List<JSONObject> listjson  = new ArrayList<JSONObject>();
		paramjson.put("PhoneNum", mobile);
		paramjson.put("Amount", flow);
		paramjson.put("FlowType", "BDZS");
		listjson.add(paramjson);
		System.out.println(listjson.toString());
		String resultstr = HTTPRequest.sendPostJson(path, listjson.toString());
		System.out.println("关注绑定流量赠送结果："+resultstr);
	}
	
}
