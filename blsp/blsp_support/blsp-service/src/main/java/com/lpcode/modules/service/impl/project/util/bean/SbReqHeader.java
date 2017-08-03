package com.lpcode.modules.service.impl.project.util.bean;

import java.io.Serializable;

public class SbReqHeader implements Serializable{
	
	private static final long serialVersionUID = -4937141714435885622L;
	
	private String version;//单字符，从1开始，1-9A-Z依次使用，当前版本为1
	
	private String reqno;//发送方流水号，用于跟踪接口调用情况
	
	private String timestamp;//系统时间戳,格式为YYYYMMDDHHmmss
	
	private String platformId;//请求平台编号  1：信息展示屏 2：自助终端 3：网站 4：App 5：微信
	
	private String username;//平台用户名，由总线分配，本期可先不实现
	
	private String password;//平台密码, 由总线分配，本期可先不实现
	
	private String proportion;
	
	private String scale;
	
	private String gap;
	
	public String getGap() {
		return gap;
	}
	public void setGap(String gap) {
		this.gap = gap;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getReqno() {
		return reqno;
	}
	public void setReqno(String reqno) {
		this.reqno = reqno;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProportion() {
		return proportion;
	}
	public void setProportion(String proportion) {
		this.proportion = proportion;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	public String getPlatformId() {
		return platformId;
	}
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
}
