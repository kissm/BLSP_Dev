package com.lpcode.client.push;

import java.io.Serializable;

public class PushReqHeader implements Serializable {

	private static final long serialVersionUID = 3110293961486497393L;
	
	private String version;
	
	private String reqno;
	
	private String timestamp;
	
	private String platformId;
	
	private String username;
	
	private String password;
	
	public PushReqHeader(String username,String password,String timestamp,String reqno,String platformId,String version){
		this.setUsername(username);
		this.setPassword(password);
		this.setTimestamp(timestamp);
		this.setReqno(reqno);
		this.setPlatformId(platformId);
		this.setVersion(version);
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

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
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

}
