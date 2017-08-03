package com.framework.osp.modules.web.bean;

import java.io.Serializable;

public class PwbmRequestBody<T,T1> implements Serializable {
	
	private static final long serialVersionUID = 9198632849439673248L;

	private FormReqHeader reqHeader;
	
	private T data;
	
	private T1 data1;

	public FormReqHeader getReqHeader() {
		return reqHeader;
	}

	public void setReqHeader(FormReqHeader reqHeader) {
		this.reqHeader = reqHeader;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public T1 getData1() {
		return data1;
	}

	public void setData1(T1 data1) {
		this.data1 = data1;
	}
}
