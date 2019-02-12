package com.dtap.bean;

import java.util.Date;

public class TBAcrecord {
	private int id;
	private String misContent;
	private String dn;
	private Date addtime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getMisContent() {
		return misContent;
	}
	public void setMisContent(String misContent) {
		this.misContent = misContent;
	}
	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
}
