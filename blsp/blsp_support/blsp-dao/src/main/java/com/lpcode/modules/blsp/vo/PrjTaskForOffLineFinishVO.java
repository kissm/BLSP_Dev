package com.lpcode.modules.blsp.vo;

import java.io.Serializable;

/**
 * 
 *
 * @author wangyazhou
 *
 */
public class PrjTaskForOffLineFinishVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4840366202012315746L;
	private Long id; // 
	private String deptName; // 
	private String taskName; // 
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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

	
}
