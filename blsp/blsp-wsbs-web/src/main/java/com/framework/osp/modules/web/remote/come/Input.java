package com.framework.osp.modules.web.remote.come;

import java.io.Serializable;

public class Input implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public ReqHeader getReqHeader() {
		return reqHeader;
	}

	public void setReqHeader(ReqHeader reqHeader) {
		this.reqHeader = reqHeader;
	}

	private ReqHeader reqHeader;
}
