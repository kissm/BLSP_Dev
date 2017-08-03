package com.lpcode.client.newreport;

import java.io.Serializable;

public class NewReqData implements Serializable{

	private static final long serialVersionUID = 1507102676148065550L;

	private String sourceSystem;
	
	private String sbStatus;
	
	private String jsonObject;

	public NewReqData(String sourceSystem,String sbStatus,String jsonObject){
		this.setSourceSystem(sourceSystem);
		this.setSbStatus(sbStatus);
		this.setJsonObject(jsonObject);
	}
	
	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getSbStatus() {
		return sbStatus;
	}

	public void setSbStatus(String sbStatus) {
		this.sbStatus = sbStatus;
	}

	public String getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(String jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	
	
}
