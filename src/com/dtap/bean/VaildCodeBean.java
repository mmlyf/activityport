package com.dtap.bean;

import java.util.Date;

public class VaildCodeBean {
	private int id;
	private String code;
	private int used;
	private String phonenum;
	private Date sendDate;
	private Date checkDate;
	private int isVaild;
	private Date addTime;
	private Date modifyTime;
	private int dataState;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getUsed() {
		return used;
	}
	public void setUsed(int used) {
		this.used = used;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public int getIsVaild() {
		return isVaild;
	}
	public void setIsVaild(int isVaild) {
		this.isVaild = isVaild;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public int getDataState() {
		return dataState;
	}
	public void setDataState(int dataState) {
		this.dataState = dataState;
	}
}
