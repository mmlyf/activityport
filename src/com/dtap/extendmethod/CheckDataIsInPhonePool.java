package com.dtap.extendmethod;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.dtap.bean.DsjIceDataBean;
import com.dtap.enums.DsjDbType;
import com.dtap.operation.DsjDataDBOperation;
import com.dtap.operation.OrdersDBOperation;
import com.dtap.operation.TBMssageOperation;

import sun.util.logging.resources.logging;

/**
 * 
 * @author lvgordon
 * 检查当前号码是否在低消或者冰淇淋号池内
 * 并根据号池内的产品和档位发送指定的营销语 
 *
 */
public class CheckDataIsInPhonePool {
	private static Logger log = Logger.getLogger(CheckDataIsInPhonePool.class);
	private static ExecutorService actipool = Executors.newSingleThreadExecutor();
	
	/**
	 * 查询号码是否在号池内
	 * 并定时进行触发短信
	 * @param phone
	 * @return
	 */
	public static Map<String, String> selectDataInPool(String phone){
		Map<String, String> map = new HashMap<>();
		DsjIceDataBean dataBean;
		dataBean = DsjDataDBOperation.selectDataInDsjByPhone(phone, DsjDbType.ICE);
		if (dataBean==null||dataBean.getDx_dn().equals("")) {
			dataBean = DsjDataDBOperation.selectDataInDsjByPhone(phone, DsjDbType.DX);
		}
		if (dataBean!=null&&!dataBean.getDx_dn().equals("")) {
			map.put("phone", dataBean.getDx_dn());
			map.put("dw", dataBean.getDx_firdw());
			map.put("firp", dataBean.getDx_firp());
			if (dataBean.getDx_firp().equals("畅越冰")) {
				map.put("type", "ice");
			}else {
				map.put("type", "dx");
			}
			sendMessageByFirpDw(phone, dataBean.getDx_firdw(), dataBean.getDx_firp());
			return map;
		}else {
			return null;
		}
	}
	/**
	 * 根据产品类型和档位获取当前的营销短信的内容
	 * @param phone
	 * @param dw
	 * @param firp
	 * @param source
	 */
	private static void sendMessageByFirpDw(String phone,String dw,String firp) {
		String mistitle;
		String miscontent;
		if (firp.equals("畅越冰")) {
			mistitle = "冰淇淋"+dw;
			miscontent = TBMssageOperation.selectByMisTitle(mistitle);
		}else {
			if(firp.equals("低消送流量")) {
				mistitle = "C"+dw;
			}else {
				mistitle = "语音"+dw;
			}
			miscontent = TBMssageOperation.selectByMisTitle(mistitle);
		}
		addTimeTask(phone, miscontent);

	}

	/**
	 * 定时进行触发营销短信
	 * @param phone
	 * @param miscontent
	 */
	public static void addTimeTask(String phone,String miscontent) {
		Timer timer = new Timer();
		actipool.execute(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
						String addtime = sdf.format(date);
						boolean res = OrdersDBOperation.getOrdersDataByPhone(phone, addtime);
						if (!res) {
							int count = DsjDataDBOperation.countShortMessageSend(phone,addtime);
							if (count<=3) {
								Thread thread = new Thread(new HandlerSend(phone, miscontent, 1));
								thread.start();
							}else {
								log.info(phone+"当前已发送三条，无法再发");
							}
							
						}else {
							log.info(phone+"已有二次确认订购。");
						}

					}
				}, 1800000);
			}
		});
	}
	
	public static void main(String[] args) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String sectime = sdf.format(date);
		int count = DsjDataDBOperation.countShortMessageSend("15531461574", sectime);
		System.out.println(count);
	}
}
