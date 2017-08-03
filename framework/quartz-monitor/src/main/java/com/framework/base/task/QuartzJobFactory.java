package com.framework.base.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.base.support.spring.SpringUtils;
import com.framework.base.task.domain.ScheduleJob;

/**
 * 计划任务执行处 无状态
 * @Class Name QuartzJobFactory
 * @Author mogu
 * @Create In 2014年12月18日
 */
public class QuartzJobFactory implements Job {
    // 日志记录器
    private static final Logger LOG = LoggerFactory.getLogger(QuartzJobFactory.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
		UnityTask task=(UnityTask)SpringUtils.getBean(UnityTask.class);
		boolean thirdResult=task.run(scheduleJob.getJobId(),scheduleJob.getUrl(), scheduleJob.getParas());
		LOG.info("当前的执行的调度对象为：scheduleJob={},result={}",new Object[]{scheduleJob,thirdResult});
	}
}