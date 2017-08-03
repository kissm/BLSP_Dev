package com.lpcode.modules.blsp.vo;

import java.io.Serializable;

public class ProjectStageVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String stageName;
	public String stageid;
	public StageTaskVo stageTaskVo;
	public Integer pendingNum;//受理中的

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public Integer getPendingNum() {
		return pendingNum;
	}

	public void setPendingNum(Integer pendingNum) {
		this.pendingNum = pendingNum;
	}

	public String getStageid() {
		return stageid;
	}

	public void setStageid(String stageid) {
		this.stageid = stageid;
	}

	public StageTaskVo getStageTaskVo() {
		return stageTaskVo;
	}

	public void setStageTaskVo(StageTaskVo stageTaskVo) {
		this.stageTaskVo = stageTaskVo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
