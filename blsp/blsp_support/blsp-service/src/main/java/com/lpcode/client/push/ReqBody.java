package com.lpcode.client.push;

import java.io.Serializable;

public class ReqBody<T> implements Serializable{

	private static final long serialVersionUID = 6970420975473043663L;

	private ReqHeader reqHeader;
	
	private T data;

	public ReqHeader getReqHeader() {
		return reqHeader;
	}

	public void setReqHeader(ReqHeader reqHeader) {
		this.reqHeader = reqHeader;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
