package com.lpcode.client.push;


import com.lpcode.modules.dto.report.ServiceBasicInfoPushDto;

import java.io.Serializable;

public class InvokeResult  implements Serializable{
	
	private static final long serialVersionUID = -7496684755363029069L;
	
	private RespHeader respHeader;
	
	private ServiceBasicInfoPushDto data;

	public RespHeader getRespHeader() {
		return respHeader;
	}

	public void setRespHeader(RespHeader respHeader) {
		this.respHeader = respHeader;
	}

	public ServiceBasicInfoPushDto getData() {
		return data;
	}

	public void setData(ServiceBasicInfoPushDto data) {
		this.data = data;
	}
	
}
