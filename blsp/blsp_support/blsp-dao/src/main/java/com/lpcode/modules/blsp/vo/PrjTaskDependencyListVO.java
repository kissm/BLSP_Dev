package com.lpcode.modules.blsp.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目批文领取DTO
 *
 * @author wangyazhou
 *
 */
public class PrjTaskDependencyListVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6580073218177410490L;
	private String deptName; // 
	private String taskName; // 
	private String finishedMan; // 
	private Date finishedTime; // 
	private String auditDesc; // 
	private String auditAttachName; // 
	private String auditAttachAddr; // 
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getFinishedMan() {
		return finishedMan;
	}
	public void setFinishedMan(String finishedMan) {
		this.finishedMan = finishedMan;
	}
	public Date getFinishedTime() {
		return finishedTime;
	}
	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = finishedTime;
	}
	public String getAuditDesc() {
		return auditDesc;
	}
	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}
	public String getAuditAttachAddr() {
		return auditAttachAddr;
	}
	public void setAuditAttachAddr(String auditAttachAddr) {
		this.auditAttachAddr = auditAttachAddr;
	}
	public String getAuditAttachName() {
		return auditAttachName;
	}
	public void setAuditAttachName(String auditAttachName) {
		this.auditAttachName = auditAttachName;
	}
	
}
