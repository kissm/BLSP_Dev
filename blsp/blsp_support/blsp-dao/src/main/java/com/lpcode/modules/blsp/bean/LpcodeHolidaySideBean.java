package com.lpcode.modules.blsp.bean;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LpcodeHolidaySideBean implements Serializable{

	private static final long serialVersionUID = 1231231231231231L;

	private Date startDate;
	
	private Date endDate;
	
	private String type;
	
	
	@JsonFormat(pattern = "yyyyMMdd")
	public Date getStartDate() {
		return startDate;
	}
	@JsonFormat(pattern = "yyyyMMdd")
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JsonFormat(pattern = "yyyyMMdd")
	public Date getEndDate() {
		return endDate;
	}
	@JsonFormat(pattern = "yyyyMMdd")
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
