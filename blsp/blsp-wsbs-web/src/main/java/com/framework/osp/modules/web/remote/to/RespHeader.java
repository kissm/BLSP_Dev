package com.framework.osp.modules.web.remote.to;

import java.io.Serializable;

public class RespHeader  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String reqno;
	String respno;
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
	String status;
	String respmsg;
	String resptime;
}
