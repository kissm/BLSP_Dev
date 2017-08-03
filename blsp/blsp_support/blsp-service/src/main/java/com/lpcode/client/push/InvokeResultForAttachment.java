package com.lpcode.client.push;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class InvokeResultForAttachment  implements Serializable{
	
	private static final long serialVersionUID = -7496684755363029069L;
	
	private RespHeader respHeader;
	
	private List<Map<String,String>> data;

	public RespHeader getRespHeader() {
		return respHeader;
	}

	public void setRespHeader(RespHeader respHeader) {
		this.respHeader = respHeader;
	}

	public List<Map<String, String>> getData() {
		return data;
	}

	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}

}
