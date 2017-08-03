package com.framework.core.result;

import java.io.Serializable;


public abstract class AbstractResult implements Serializable{
	
	private static final long serialVersionUID = 1L;
	protected String resCode;
	protected String msg;
	
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
