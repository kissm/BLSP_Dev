package com.lpcode.client.push;

import java.io.Serializable;

public class PushRespHeader implements Serializable{
	
	private static final long serialVersionUID = 50646215269230480L;

	private String reqno;
	
	private String respno;
	
	private String status;
	
	private String respmsg;
	
	private String resptime;

	public String getReqno() {
		return reqno;
	}

	public void setReqno(String reqno) {
		this.reqno = reqno;
	}

	public String getRespno() {
		return respno;
	}

	public void setRespno(String respno) {
		this.respno = respno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRespmsg() {
		return respmsg;
	}

	public void setRespmsg(String respmsg) {
		this.respmsg = respmsg;
	}

	public String getResptime() {
		return resptime;
	}

	public void setResptime(String resptime) {
		this.resptime = resptime;
	}
	
}
