package com.lpcode.client.push;

import java.io.Serializable;

public class PushInvokResult  implements Serializable{
	
	private static final long serialVersionUID = -7496684755363029069L;
	
	private PushRespHeader respHeader;

	public PushRespHeader getRespHeader() {
		return respHeader;
	}

	public void setRespHeader(PushRespHeader respHeader) {
		this.respHeader = respHeader;
	}
	
	
}
