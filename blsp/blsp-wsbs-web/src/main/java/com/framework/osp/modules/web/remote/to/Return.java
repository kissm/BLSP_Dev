package com.framework.osp.modules.web.remote.to;

import java.io.Serializable;

public class Return implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Data data;
	RespHeader respHeader;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public RespHeader getRespHeader() {
		return respHeader;
	}

	public void setRespHeader(RespHeader respHeader) {
		this.respHeader = respHeader;
	}
}
