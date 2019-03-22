package com.dtap.timetask.taskimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.dtap.bean.DataTotalBean;
import com.dtap.connect.GetConnect;
import com.dtap.enums.MailTypeEnum;
import com.dtap.extendmethod.SendMail;
import com.dtap.operation.DataTotalDBOperation;


public class MarketDataTotalTimeTask extends TimerTask{
	private Logger log = Logger.getLogger(MarketDataTotalTimeTask.class);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Date date = new Date();
		long datetime = date.getTime() - 12*60*60*1000;
		date = new Date(datetime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String datestr = sdf.format(date);
		log.info("this time is select of mo/mt/order"+datestr);
		StringBuilder stringBuilder = new StringBuilder();
		String string = totalCountForType(datestr);
		SendMail.sendMailForCommon(string, "",MailTypeEnum.DEVALOP);
	}
	/**
	 * 
	 * @param type
	 * @param datestr
	 * 分别对上行、下行和订单进行统计
	 * 
	 */
	private String totalCountForType(String datestr) {
		Connection conn = GetConnect.getConnection();
		Connection aliconn = GetConnect.getAliConnection();
		String result = "";
		int[] value;
		DataTotalBean dataTotalBean = new DataTotalBean();
		String[] sql1 = {"select count(*) from mo_receive where ad_time like ?",
				"select count(*) from mo_receive where ad_time like ? and ( UPPER(message_content) IN ('Y5','Y8','Y13','Y18','Y23','Y28','Y33','Y38','Y43',"
						+ "'Y48','Y53','Y58','Y63','Y68','Y73','Y78','Y83','Y88','Y93','Y98','Y108','Y118','Y128','Y138','Y148','Y158','Y168','Y178'," 
						+"'Y188','Y198','Y208','Y228','Y258','Y288','Y318','Y350','Y400','Y466' ) OR UPPER(message_content) IN ('L5'," 
						+"'L8','L13','L18','L23','L28','L33','L38','L43','L48','L53','L58','L63','L68','L73','L78',"
						+"'L83','L88','L93','L98','L108','L118','L128','L138','L148','L158','L168','L178','L188','L198',"
						+"'L208','L228','L258','L288','L318','L350','L400','L466' )"
						+ "OR UPPER(message_content) IN ('B38','B48','B58','B68','B78','B88','B98','B108','B118','B128','B138','B148','B158','B168','B178',"
						+ "'B188','B198','B218','B238','B258','B278','B298','B318','B358','B398' )"
						+ "OR message_content IN ('12','13','14'))",//查找上行有效所有
						"select count(*) from mo_receive where ad_time like ? and (UPPER(message_content) IN ('Y5','Y8','Y13','Y18','Y23','Y28','Y33','Y38','Y43'," + 
						"'Y48','Y53','Y58','Y63','Y68','Y73','Y78','Y83','Y88','Y93','Y98','Y108','Y118','Y128','Y138','Y148','Y158','Y168','Y178'," + 
						"'Y188','Y198','Y208','Y228','Y258','Y288','Y318','Y350','Y400','Y466' ) OR UPPER(message_content) IN ('L5'," + 
						"'L8','L13','L18','L23','L28','L33','L38','L43','L48','L53','L58','L63','L68','L73','L78'," + 
						"'L83','L88','L93','L98','L108','L118','L128','L138','L148','L158','L168','L178','L188','L198'," + 
						"'L208','L228','L258','L288','L318','L350','L400','L466' ))",//低消上行有效
						"select count(*) from mo_receive where ad_time like ? and UPPER(message_content) IN ('B38','B48','B58','B68','B78','B88','B98','B108','B118','B128','B138','B148','B158','B168','B178'," + 
						"'B188','B198','B218','B238','B258','B278','B298','B318','B358','B398' )",//冰激凌上行有效
						"select count(*) from tb_cbrecord where cd_sendtime like ?",//下行总数
						"select count(*) from tb_cbrecord where cd_sendtime like ? and cd_sendstat=1",//下行成功
						"select count(*) from orders where AddTime like ?",//所有订单
						"select count(*) from orders where AddTime like ? and BssState='0'",//订购成功
						"select count(*) from orders where AddTime like ? and BssState='0' "
						+ "and ProductId in (SELECT id  FROM products WHERE ProductName LIKE '%低消%')",//低消订购成功
						"select count(*) from orders where AddTime like ? and "
						+ "(BssState!='0' or BssState is null) and "
						+ "ProductId in (SELECT id  FROM products WHERE ProductName LIKE '%低消%')",//低消订购不成功
		"select count(*) from ice_dsj_orders where dx_addtime like ?"};
		int[] a = new int[11];
		try {
			for(int i=0;i<sql1.length;i++) {

				if (i==4||i==5) {
					PreparedStatement ppst = aliconn.prepareStatement(sql1[i]);
					ppst.setString(1, datestr+" %");
					ResultSet resultSet = ppst.executeQuery();
					while (resultSet.next()) {
						a[i] = resultSet.getInt(1);
					}
				}else {
					PreparedStatement ppst = conn.prepareStatement(sql1[i]);
					ppst.setString(1, datestr+" %");
					ResultSet resultSet = ppst.executeQuery();
					while (resultSet.next()) {
						a[i] = resultSet.getInt(1);
					}
				}
			}
			conn.close();
			aliconn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dataTotalBean.setMoall(a[0]);//上行总
		dataTotalBean.setMoable(a[1]);//上行有效
		dataTotalBean.setMoabledx(a[2]);
		dataTotalBean.setMoableice(a[3]);
		dataTotalBean.setMoablellb(a[1]-a[2]-a[3]);
		dataTotalBean.setMtall(a[4]);
		dataTotalBean.setMtsuc(a[5]);
		dataTotalBean.setOrderall(a[6]);//订购总数
		dataTotalBean.setOrdersuc(a[7]);//订购成功
		dataTotalBean.setOrdersucdx(a[8]);//低消订购成功
		dataTotalBean.setOrdersucllb(a[7]-a[8]);//流量包订购成功
		dataTotalBean.setOrderdissuc(a[6]-a[7]);//订购不成功
		dataTotalBean.setOrderdissucdx(a[9]);//低消订购不成功数
		dataTotalBean.setOrderdissucllb(a[6]-a[7]-a[9]);//流量包订购不成功
		dataTotalBean.setIcesuc(a[10]);
		dataTotalBean.setModisable(a[0]-a[1]);//上行无效
		dataTotalBean.setMtdissuc(a[4]-a[5]);//下行不成功
		float mtrate = 0;
		float morate = 0;
		float modxrate = 0;
		float moicerate = 0;
		float mollbrate = 0;
		float resorderrate = 0;
		float orderrate = 0;
		float ordersucrate = 0;
		float ordersucdxrate = 0;
		float ordersucllbrate = 0;
		if(a[4]!=0) {
			mtrate = Float.parseFloat(String.format("%.7f",((a[5]*1.0)/a[4]))); //下行率
		}
		if (a[5]!=0) {
			morate = Float.parseFloat(String.format("%.7f",((a[0]*1.0)/a[5])));//上行率
		}
		if (a[0]!=0) {
			modxrate = Float.parseFloat(String.format("%.7f",((a[2]*1.0)/a[0])));//低消上行率
			moicerate = Float.parseFloat(String.format("%.7f",((a[3]*1.0)/a[0])));//冰激凌上行率
			mollbrate = Float.parseFloat(String.format("%.7f",(((a[1]-a[2]-a[3])*1.0)/a[0])));//流量包上行率
			resorderrate = Float.parseFloat(String.format("%.7f",((a[1]*1.0)/a[0])));//回复订购率
		}
		if (a[1]!=0) {
			orderrate = Float.parseFloat(String.format("%.7f",((a[6]*1.0)/a[1])));//订购率
		}
		if (a[6]!=0) {
			ordersucrate  = Float.parseFloat(String.format("%.7f",((a[7]*1.0)/a[6])));
		}
		if(a[2]!=0) {
			ordersucdxrate = Float.parseFloat(String.format("%.7f",((a[8]*1.0)/a[2])));//低消订购成功率
		}
		if((a[1]-a[2]-a[3])!=0) {
			ordersucllbrate = Float.parseFloat(String.format("%.7f",(((a[7]-a[8])*1.0)/(a[1]-a[2]-a[3]))));//流量包订购成功
		}
		dataTotalBean.setMorate(morate);
		dataTotalBean.setMtrate(mtrate);
		dataTotalBean.setModxrate(modxrate);
		dataTotalBean.setMoicerate(moicerate);
		dataTotalBean.setMollbrate(mollbrate);
		dataTotalBean.setOrderrate(orderrate);
		dataTotalBean.setResorderrate(resorderrate);
		dataTotalBean.setOrdersucrate(ordersucrate);
		dataTotalBean.setOrdersucdxrate(ordersucdxrate);
		dataTotalBean.setOrdersucllbrate(ordersucllbrate);
		dataTotalBean.setAdtime(datestr);
		boolean resflag = DataTotalDBOperation.insertDataTotalByBean(dataTotalBean);
		while (!resflag) {
			resflag = DataTotalDBOperation.insertDataTotalByBean(dataTotalBean);
		}
		String reString = "当前数据存入数据的结果是："+resflag;
		SendMail.sendMailForCommon(reString, "", MailTypeEnum.DEVALOP);
		log.info("当前数据存入数据的结果是："+resflag);
		result = getMailMessage(dataTotalBean);
		return result;
	}
	/**
	 * 获取下发邮件的内容
	 * @param bean
	 * @return
	 */
	private String getMailMessage(DataTotalBean bean) {
		String res = "下行总数是："+bean.getMtall()+"<br>"
				+"下行成功数："+bean.getMtsuc()+"<br>"
				+"上行总数:"+bean.getMoall()+"<br>"
				+"上行有效："+bean.getMoable()+"<br>"
				+"订单总数："+bean.getOrderall()+"<br>"
				+"订单成功："+bean.getOrdersuc()+"<br><br><br><br><br>";
		res += "<table style=\"text-align:center;\" border=\"1\">\n" + 
				"							<colgroup>\n" + 
				"								<col width=\"12.5%\">\n" + 
				"								<col width=\"12.5%\">\n" + 
				"								<col width=\"12.5%\">\n" + 
				"								<col width=\"12.5%\">\n" + 
				"								<col width=\"12.5%\">\n" + 
				"								<col width=\"12.5%\">\n" + 
				"								<col width=\"12.5%\">\n" + 
				"								<col width=\"12.5%\">\n" + 
				"							</colgroup>\n" + 
				"							<thead>\n" + 
				"								<tr>\n" + 
				"									<td  colspan=8>营销数据统计</td>\n" + 
				"								</tr>\n" + 
				"								<tr>\n" + 
				"									<td>下行(总数)</td>\n" + 
				"									<td>下行(成功)</td>\n" + 
				"									<td>上行(总数)</td>\n" + 
				"									<td>上行(有效)</td>\n" + 
				"									<td>	低消上行(有效)</td>\n" + 
				"									<td>冰激凌上行(有效)</td>\n" + 
				"									<td>流量包上行(有效)</td>\n" + 
				"									<td>冰激凌预约</td>\n" + 
				"								</tr>\n" + 
				"							</thead>\n" + 
				"							<tbody>\n" + 
				"								<tr>\n" + 
				"									<td>"+bean.getMtall()+"</td>\n" + 
				"									<td>"+bean.getMtsuc()+"</td>\n" + 
				"									<td>"+bean.getMoall()+"</td>\n" + 
				"									<td>"+bean.getMoable()+"</td>\n" +
				"									<td>"+bean.getMoabledx()+"</td>\n" + 
				"									<td>"+bean.getMoableice()+"</td>\n" + 
				"									<td>"+bean.getMoablellb()+"</td>\n" +
				"									<td>"+bean.getIcesuc()+"</td>\n" + 
				"								</tr>\n" + 
				"								<tr>\n" + 
				"									<td>下行率(下行成功/下行总数)</td>\n" + 
				"									<td>回复率(上行(总数)/下行(成功))</td>\n" + 
				"									<td>低消上行率（低消上行数/上行总）</td>\n" + 
				"									<td>流量包上行率（流量包上行数/上行总）</td>\n" + 
				"									<td colspan=2>冰激凌上行率（冰激凌上行数/上行总）</td>\n" + 
				"									<td colspan=2>回复订购率(上行有效/上行总)</td>\n" + 
				"								</tr>\n" + 
				"								<tr>\n" + 
				"									<td>"+bean.getMtrate()+"</td>\n" + 
				"									<td>"+bean.getMorate()+"</td>\n" + 
				"									<td>"+bean.getModxrate()+"</td>\n" + 
				"									<td>"+bean.getMollbrate()+"</td>\n" + 
				"									<td colspan=2>"+bean.getMoicerate()+"</td>\n" + 
				"									<td colspan=2>"+bean.getResorderrate()+"</td>\n" + 
				"								</tr>\n" + 
				"							<tr>\n" + 
				"									<td>订购数</td>\n" + 
				"									<td>订购成功数</td>\n" +
				"									<td>低消订购成功</td>\n" + 
				"									<td>流量包订购成功</td>\n" +  
				"									<td>订购率</td>\n" + 
				"									<td>订购成功率</td>\n" + 
				"									<td>低消订购成功率</td>\n" + 
				"									<td>流量包订购成功率</td>\n" + 
				"								</tr>\n" + 
				"							<tr>\n" + 
				"									<td>"+bean.getOrderall()+"</td>\n" + 
				"									<td>"+bean.getOrdersuc()+"</td>\n" + 
				"									<td>"+bean.getOrdersucdx()+"</td>\n" + 
				"									<td>"+bean.getOrdersucllb()+"</td>\n" + 
				"									<td>"+bean.getOrderrate()+"</td>\n" + 
				"									<td>"+bean.getOrdersucrate()+"</td>\n" + 
				"									<td>"+bean.getOrdersucdxrate()+"</td>\n" + 
				"									<td>"+bean.getOrdersucllbrate()+"</td>\n" + 
				"								</tr>\n" +
				"							</tbody>\n" + 
				"						</table>";
		return res;
	}
}
