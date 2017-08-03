package com.framework.osp.modules.web.job;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.jobs.executor.TaskOvertimeReminderExecutor;
import com.lpcode.modules.jobs.executor.TaskPauseDueCheckExecutor;

/**
 * @author Pengs
 * @package com.framework.osp.modules.web.material
 * @fileName MaterialController
 * @date 16/2/19.
 */

@RestController
@RequestMapping(value = "/schedule/task")
public class ScheduleJobController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleJobController.class);

	@Resource(name = "scheduleTask")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	@Autowired
	private TaskPauseDueCheckExecutor pauseDueCheckExecutor;
	@Autowired
	private TaskOvertimeReminderExecutor overtimeCheckExecutor;

	@RequestMapping(value = "/taskPauseOverdueScan")
	public Map<String, Object> taskPauseOverdueScan(@RequestBody Map paras, HttpServletRequest request) {
		LOGGER.info("线程池数量为:" + threadPoolTaskExecutor.getCorePoolSize());
		Integer id = (Integer) paras.get("id");
		String name = (String) paras.get("name");
		pauseDueCheckExecutor.setName(name);
		pauseDueCheckExecutor.setId(new Long(id));
		threadPoolTaskExecutor.execute(pauseDueCheckExecutor);
		Map<String, Object> resultMap = resultMap = new HashMap<String, Object>();
		resultMap.put("resCode", "0");
		resultMap.put("taskId", id);
		return resultMap;
	}

	@RequestMapping(value = "/taskOvertimeScan")
	public Map<String, Object> taskOvertimeScan(@RequestBody Map paras, HttpServletRequest request) {
		LOGGER.info("线程池数量为:" + threadPoolTaskExecutor.getCorePoolSize());
		Integer id = (Integer) paras.get("id");
		String name = (String) paras.get("name");
		overtimeCheckExecutor.setName(name);
		overtimeCheckExecutor.setId(new Long(id));
		threadPoolTaskExecutor.execute(overtimeCheckExecutor);
		Map<String, Object> resultMap = resultMap = new HashMap<String, Object>();
		resultMap.put("resCode", "0");
		resultMap.put("taskId", id);
		return resultMap;
	}

}
