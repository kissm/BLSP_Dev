package com.framework.base.task.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.quartz.CronScheduleBuilder;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.framework.base.support.Page;
import com.framework.base.support.RetObj;
import com.framework.base.support.constants.GroupConstants;
import com.framework.base.support.constants.ScheduleConstants;
import com.framework.base.task.domain.ScheduleJob;
import com.framework.base.task.domain.TaskExcuteDetail;
import com.framework.base.task.service.JobTaskService;

/**
 * job入口服务类
 * @Class Name JobTaskController
 * @Author mogu
 * @Create In 2014年12月18日
 */
@Controller
@RequestMapping("/task")
public class JobTaskController {
	// 日志记录器
    private static final Logger log = LoggerFactory.getLogger(JobTaskController.class);
	@Autowired
	private JobTaskService taskService;

	@RequestMapping("taskList")
	public String taskList(HttpServletRequest request) {
		request.setAttribute("detailStatusMap", JSON.toJSONString(ScheduleConstants.EXCUTE_DETAIL_MAP));
		request.setAttribute("groupMap", JSON.toJSONString(GroupConstants.GROUP_INFO_MAP));
		return "base/task/taskList";
	}
	
	@RequestMapping("querList")
	@ResponseBody
    public Map<String, Object> querList(HttpServletRequest request,ScheduleJob scheduleJob) {
	    request.getParameterMap();
	    Page page=new Page();
        List<ScheduleJob> taskList = taskService.queryTaskListPage(scheduleJob,page);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", page.getTotal());
        map.put("rows", taskList);
        return map;
    }
	
	@RequestMapping("saveOrUpdateTask")
	@ResponseBody
	public RetObj saveOrUpdateTask(HttpServletRequest request, ScheduleJob scheduleJob) {
		RetObj retObj = new RetObj();
		retObj.setFlag(false);
		try {
			CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
		} catch (Exception e) {
			retObj.setMsg("cron表达式有误，不能被解析！");
			return retObj;
		}
		try {
		    taskService.saveOrUpdateTask(scheduleJob);
		} catch (Exception e) {
			e.printStackTrace();
			retObj.setFlag(false);
			retObj.setMsg("保存失败，检查 name group 组合是否有重复！");
			return retObj;
		}

		retObj.setFlag(true);
		return retObj;
	}

	@RequestMapping("changeJobStatus")
	@ResponseBody
	public RetObj changeJobStatus(HttpServletRequest request, Long jobId, String cmd) {
		RetObj retObj = new RetObj();
		retObj.setFlag(false);
		try {
			taskService.changeStatus(jobId, cmd);
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
			retObj.setMsg("任务状态改变失败！");
			return retObj;
		}
		retObj.setFlag(true);
		return retObj;
	}
	
	/**
	 * 批量刪除任務
	 * @Methods Name batchDelJob
	 * @Create In 2014年12月23日 By mogu
	 * @param request
	 * @param jobIds
	 * @return RetObj
	 */
	@RequestMapping("batchDelJob")
    @ResponseBody
    public RetObj batchDelJob(HttpServletRequest request, Long[] jobIds) {
        RetObj retObj = new RetObj();
        retObj.setFlag(false);
        try {
            taskService.batchDeleteJob(jobIds);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            retObj.setMsg(e.getMessage());
            return retObj;
        }
        retObj.setFlag(true);
        return retObj;
    }
	
	@RequestMapping("loadJobInfo")
    @ResponseBody
    public Object loadJobInfo(HttpServletRequest request, Long jobId) {
	    ScheduleJob job=null;
        try {
            job=taskService.queryTask(jobId);
            if(job==null){
                log.debug("查询不到job信息内容!");
            }
            else{
                return job;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return job;
    }
    
	@RequestMapping("updateCron")
	@ResponseBody
	public RetObj updateCron(HttpServletRequest request, Long jobId, String cron) {
		RetObj retObj = new RetObj();
		retObj.setFlag(false);
		try {
			 CronScheduleBuilder.cronSchedule(cron);
		} catch (Exception e) {
			retObj.setMsg("cron表达式有误，不能被解析！");
			return retObj;
		}
		try {
			taskService.updateCron(jobId, cron);
		} catch (SchedulerException e) {
			retObj.setMsg("cron更新失败！");
			return retObj;
		}
		retObj.setFlag(true);
		return retObj;
	}
	
	/**
	 * 查询job执行明细
	 * @Methods Name taskDetailList
	 * @Create In 2014年12月24日 By mogu
	 * @param request
	 * @param jobId
	 * @return Map<String,Object>
	 */
	@RequestMapping("taskDetailList")
    @ResponseBody
    public Map<String, Object> taskDetailList(HttpServletRequest request, Long jobId) {
	    List<TaskExcuteDetail> detailList = taskService.taskDetailList(jobId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", detailList.size());
        map.put("rows", detailList);
        return map;
	}
	

	/**
	 * 立即执行job任务
	 * @Methods Name immediateExc
	 * @Create In 2015年4月10日 By mogu
	 * @param request
	 * @param jobId
	 * @return Map<String,Object>
	 */
    @RequestMapping("immediateExc")
    @ResponseBody
    public RetObj immediateExc(HttpServletRequest request, Long jobId) {
        taskService.immediateExc(jobId);
        RetObj retObj = new RetObj();
        retObj.setFlag(true);
        return retObj;
    }
}
