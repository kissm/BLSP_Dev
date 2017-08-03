package com.lpcode.modules.blsp.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StageTaskVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Map<String, List<TaskVo>> stageTask = new HashMap<String, List<TaskVo>>();

	public Map<String, List<TaskVo>> getStageTask() {
		return stageTask;
	}

	public void setStageTask(Map<String, List<TaskVo>> stageTask) {
		this.stageTask = stageTask;
	}
}
