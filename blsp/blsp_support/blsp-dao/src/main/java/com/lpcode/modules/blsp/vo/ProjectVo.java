package com.lpcode.modules.blsp.vo;

import java.io.Serializable;
import java.util.Date;

public class ProjectVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String stageName;
	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
	public String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String projectName;
	public String projectCode;
	public Date applyDate;
	public Date endDate;
	public String status;
}
