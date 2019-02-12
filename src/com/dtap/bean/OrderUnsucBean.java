package com.dtap.bean;

import java.util.Date;

public class OrderUnsucBean {
	private String mobile;
	private String serialNo;
	private String dangw;
	private String product;
	private Date addtime;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getDangw() {
		return dangw;
	}
	public void setDangw(String dangw) {
		this.dangw = dangw;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
}
