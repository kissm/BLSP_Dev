package com.lpcode.modules.blsp.bean;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LpcodeHolidayBean implements Serializable{

	private static final long serialVersionUID = 1231231231231231L;

	private Date date;
	
	private Integer amount;

	@JsonFormat(pattern = "yyyyMMdd")
	public Date getDate() {
		return date;
	}
	@JsonFormat(pattern = "yyyyMMdd")
	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
}
