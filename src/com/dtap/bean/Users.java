package com.dtap.bean;

import java.util.Date;

public class Users {
	private String Id;
	private String nickname;
	private String name;
	private String mobile;
	private int sex;
	private String province;
	private String city;
	private String country;
	private String headimgurl;
	private String unionid;
	private Date addtime;
	private Date modifytime;
	private int datestate;
	private String networktype;
	private String regiloca;
	private String openid;
	private int usertype;
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public Date getModifytime() {
		return modifytime;
	}
	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
	public int getDatestate() {
		return datestate;
	}
	public void setDatestate(int datestate) {
		this.datestate = datestate;
	}
	public String getNetworktype() {
		return networktype;
	}
	public void setNetworktype(String networktype) {
		this.networktype = networktype;
	}
	public String getRegiloca() {
		return regiloca;
	}
	public void setRegiloca(String regiloca) {
		this.regiloca = regiloca;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public int getUsertype() {
		return usertype;
	}
	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}
}
