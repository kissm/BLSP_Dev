package com.lpcode.modules.blsp.vo;

import java.io.Serializable;
import java.util.List;

public class ProjectReportVo implements Serializable {
	private static final long serialVersionUID = 1L;
	public List<ProjectStageVo> projectStageVoList;// 项目
	public Integer acceptNum;// 已受理数
	public Integer stopNum;// 终止项目数
	public Integer processNum;// 审批中项目数
	public Integer pendingNum;
	public Integer getAcceptNum() {
		return acceptNum;
	}

	public Integer getProcessNum() {
		return processNum;
	}

	public void setProcessNum(Integer processNum) {
		this.processNum = processNum;
	}

	public void setAcceptNum(Integer acceptNum) {
		this.acceptNum = acceptNum;
	}

	public Integer getOverNum() {
		return overNum;
	}

	public void setOverNum(Integer overNum) {
		this.overNum = overNum;
	}

	public Integer overNum;// 已结束数
	public List<ProjectStageVo> getProjectStageVoList() {
		return projectStageVoList;
	}

	public Integer getPendingNum() {
		return pendingNum;
	}

	public void setPendingNum(Integer pendingNum) {
		this.pendingNum = pendingNum;
	}

	public void setProjectStageVoList(List<ProjectStageVo> projectStageVoList) {
		this.projectStageVoList = projectStageVoList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getStopNum() {
		return stopNum;
	}

	public void setStopNum(Integer stopNum) {
		this.stopNum = stopNum;
	}
}
