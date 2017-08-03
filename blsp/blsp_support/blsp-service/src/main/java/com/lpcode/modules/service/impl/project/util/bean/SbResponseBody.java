package com.lpcode.modules.service.impl.project.util.bean;

import java.io.Serializable;

public class SbResponseBody implements Serializable {
	
	private static final long serialVersionUID = 9198632849439673248L;

	private SbRespHeader respHeader;
	
	private SbRespBean data;

	public SbRespHeader getRespHeader() {
		return respHeader;
	}

	public void setRespHeader(SbRespHeader respHeader) {
		this.respHeader = respHeader;
	}

	public SbRespBean getData() {
		return data;
	}

	public void setData(SbRespBean data) {
		this.data = data;
	}
	
}
