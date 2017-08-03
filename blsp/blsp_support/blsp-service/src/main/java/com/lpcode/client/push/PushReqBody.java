package com.lpcode.client.push;

import java.io.Serializable;

public class PushReqBody<T> implements Serializable{

	private static final long serialVersionUID = 6970420975473043663L;

	private PushReqHeader reqHeader;
	
	private T data;

	public PushReqHeader getReqHeader() {
		return reqHeader;
	}

	public void setReqHeader(PushReqHeader reqHeader) {
		this.reqHeader = reqHeader;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
