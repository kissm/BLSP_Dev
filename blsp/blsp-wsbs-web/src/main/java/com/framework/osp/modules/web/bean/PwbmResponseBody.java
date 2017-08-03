package com.framework.osp.modules.web.bean;

import java.io.Serializable;

public class PwbmResponseBody implements Serializable {
	
	private static final long serialVersionUID = 9198632849439673248L;

	private FormRespHeader respHeader;
	
	private FormBmRespBean data;

	public FormRespHeader getRespHeader() {
		return respHeader;
	}

	public void setRespHeader(FormRespHeader respHeader) {
		this.respHeader = respHeader;
	}

	public FormBmRespBean getData() {
		return data;
	}

	public void setData(FormBmRespBean data) {
		this.data = data;
	}
	
}
