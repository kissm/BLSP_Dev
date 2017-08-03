package com.lpcode.client.newreport;

import java.io.Serializable;

public class NewInvokResult  implements Serializable{
	
	private static final long serialVersionUID = -7496684755363029069L;
	
	private NewRespHeader respHeader;

	public NewRespHeader getRespHeader() {
		return respHeader;
	}

	public void setRespHeader(NewRespHeader respHeader) {
		this.respHeader = respHeader;
	}
	
	
}
