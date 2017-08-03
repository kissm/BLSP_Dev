package com.framework.base.task;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.base.support.spring.SpringUtils;
import com.framework.base.task.domain.ScheduleJob;

/**
 * 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作
 * @Class Name QuartzJobFactoryDisallowConcurrentExecution
 * @Author mogu
 * @Create In 2014年12月18日
 */
@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrentExecution implements Job {
    // 日志记录器
    private static final Logger LOG = LoggerFactory.getLogger(QuartzJobFactoryDisallowConcurrentExecution.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
		UnityTask task=(UnityTask)SpringUtils.getBean(UnityTask.class);
		boolean thirdResult=task.run(scheduleJob.getJobId(),scheduleJob.getUrl(), scheduleJob.getParas());
		LOG.info("当前的执行的调度对象为：scheduleJob={},result={}",new Object[]{scheduleJob,thirdResult});
//		TaskUtils.invokMethod(scheduleJob);

	}
}