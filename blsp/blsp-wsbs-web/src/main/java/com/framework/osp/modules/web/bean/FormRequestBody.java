package com.framework.osp.modules.web.bean;

import java.io.Serializable;

public class FormRequestBody<T> implements Serializable {
	
	private static final long serialVersionUID = 9198632849439673248L;

	private FormReqHeader reqHeader;
	
	private T data;

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
	
}
