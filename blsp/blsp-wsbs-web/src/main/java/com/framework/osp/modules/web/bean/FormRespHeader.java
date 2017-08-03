package com.framework.osp.modules.web.bean;

import java.io.Serializable;

public class FormRespHeader implements Serializable {
	
	private static final long serialVersionUID = 6112789606438345918L;
	
	public FormRespHeader(){}
	
	public FormRespHeader(String reqno,String respno,String status,String respmsg,String resptime){
		this.reqno =  reqno; //请求的源流水号
		this.respno = respno ;//响应的流水号，总线唯一标识号
		this.status = status;//返回代码，0为正常返回
		this.respmsg = respmsg;//返回消息串，如果没有则为空字符串
		this.resptime = resptime;//系统时间戳,格式为 YYYYMMDDHHmmss
	}

	private String reqno; //请求的源流水号
	
	private String respno;//响应的流水号，总线唯一标识号
	
	private String status;//返回代码，0为正常返回
	
	private String respmsg;//返回消息串，如果没有则为空字符串
	
	private String resptime;//系统时间戳,格式为 YYYYMMDDHHmmss

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
