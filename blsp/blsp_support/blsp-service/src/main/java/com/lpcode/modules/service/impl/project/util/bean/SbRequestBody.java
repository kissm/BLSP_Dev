package com.lpcode.modules.service.impl.project.util.bean;

import java.io.Serializable;

public class SbRequestBody<T,T1> implements Serializable {
	
	private static final long serialVersionUID = 9198632849439673248L;

	private SbReqHeader reqHeader;
	
	private T data;
	
	private T data1;

	public SbReqHeader getReqHeader() {
		return reqHeader;
	}

	public void setReqHeader(SbReqHeader reqHeader) {
		this.reqHeader = reqHeader;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public T getData1() {
		return data1;
	}

	public void setData1(T data1) {
		this.data1 = data1;
	}
	
}
