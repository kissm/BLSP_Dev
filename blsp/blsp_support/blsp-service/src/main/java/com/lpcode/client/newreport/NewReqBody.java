package com.lpcode.client.newreport;

import java.io.Serializable;

public class NewReqBody implements Serializable{

	private static final long serialVersionUID = 6970420975473043663L;

	private NewReqHeader reqHeader;
	
	private NewReqData data;

	public NewReqHeader getReqHeader() {
		return reqHeader;
	}

	public void setReqHeader(NewReqHeader reqHeader) {
		this.reqHeader = reqHeader;
	}

	public NewReqData getData() {
		return data;
	}

	public void setData(NewReqData data) {
		this.data = data;
	}

}
