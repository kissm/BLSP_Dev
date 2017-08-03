package com.framework.base.task.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.framework.base.support.BaseException;
import com.framework.base.support.Page;
import com.framework.base.support.constants.ScheduleConstants;
import com.framework.base.support.spring.SpringUtils;
import com.framework.base.task.QuartzJobFactory;
import com.framework.base.task.QuartzJobFactoryDisallowConcurrentExecution;
import com.framework.base.task.UnityTask;
import com.framework.base.task.dao.ScheduleJobMapper;
import com.framework.base.task.dao.TaskExcuteDetailMapper;
import com.framework.base.task.domain.ScheduleJob;
import com.framework.base.task.domain.TaskExcuteDetail;

/**
 * 计划任务管理
 * @Class Name JobTaskService
 * @Author mogu
 * @Create In 2014年12月18日
 */
@Service
public class JobTaskService {
    // 日志记录器
    private static final Logger LOG = LoggerFactory.getLogger(JobTaskService.class);
	
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	private ScheduleJobMapper scheduleJobMapper;
	
	@Autowired
	private TaskExcuteDetailMapper taskExcuteDetailMapper;
	
	/**
	 * 从数据库中取 区别于getAllJob
	 * 
	 * @return
	 */
	public List<ScheduleJob> getAllTask() {
		return scheduleJobMapper.getAll();
	}
	
	public List<ScheduleJob> queryTaskList(ScheduleJob job){
	    return scheduleJobMapper.queryTaskList(job);
	}
	
	public List<ScheduleJob> queryTaskListPage(ScheduleJob job,Page page){
	    int count=scheduleJobMapper.queryTaskListCount(job);
	    page.setTotal(count);
        return scheduleJobMapper.queryTaskListPage(job);
    }
	
	public ScheduleJob queryTask(Long jobId){
        return scheduleJobMapper.selectByPrimaryKey(jobId);
    }
	/**
	 * 添加到数据库中 区别于addJob
	 */
	public void addTask(ScheduleJob job) {
		job.setCreateTime(new Date());
		job.setUpdateTime(new Date());
		scheduleJobMapper.insertSelective(job);
	}

	/**
	 * 从数据库中查询job
	 */
	public ScheduleJob getTaskById(Long jobId) {
		return scheduleJobMapper.selectByPrimaryKey(jobId);
	}

	/**
	 * 更改任务状态
	 * 
	 * @throws SchedulerException
	 */
	public void changeStatus(Long jobId, String cmd) throws SchedulerException {
		ScheduleJob job = getTaskById(jobId);
		if (job == null) {
			return;
		}
		if ("stop".equals(cmd)) {
		    Scheduler scheduler = schedulerFactoryBean.getScheduler();
	        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
	        scheduler.deleteJob(jobKey);
			job.setJobStatus(ScheduleConstants.STATUS_NOT_RUNNING);
		} else if ("start".equals(cmd)) {
			job.setJobStatus(ScheduleConstants.STATUS_RUNNING);
			addJob(job);
		}
		scheduleJobMapper.updateByPrimaryKeySelective(job);
	}

	/**
	 * 更改任务 cron表达式
	 * 
	 * @throws SchedulerException
	 */
	public void updateCron(Long jobId, String cron) throws SchedulerException {
		ScheduleJob job = getTaskById(jobId);
		if (job == null) {
			return;
		}
		job.setCronExpression(cron);
		if (ScheduleConstants.STATUS_RUNNING.equals(job.getJobStatus())) {
			updateJobCron(job);
		}
		scheduleJobMapper.updateByPrimaryKeySelective(job);

	}

	/**
	 * 添加任务
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void addJob(ScheduleJob job) throws SchedulerException {
		if (job == null || !ScheduleConstants.STATUS_RUNNING.equals(job.getJobStatus())) {
			return;
		}

		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		LOG.debug(scheduler + ".......................................................................................add");
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		// 不存在，创建一个
		if (null == trigger) {
			Class clazz = ScheduleConstants.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;

			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();

			jobDetail.getJobDataMap().put("scheduleJob", job);

			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

			trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();

			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}

	@PostConstruct
	public void init() throws Exception {

		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		// 这里获取任务信息数据
		List<ScheduleJob> jobList = scheduleJobMapper.getAll();
	
		for (ScheduleJob job : jobList) {
			addJob(job);
		}
	}

	/**
	 * 获取所有计划中的任务列表
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getAllJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				ScheduleJob job = new ScheduleJob();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				job.setDescription("触发器:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				job.setJobStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
				}
				jobList.add(job);
			}
		}
		return jobList;
	}

	/**
	 * 所有正在运行的job
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getRunningJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			ScheduleJob job = new ScheduleJob();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJobName(jobKey.getName());
			job.setJobGroup(jobKey.getGroup());
			job.setDescription("触发器:" + trigger.getKey());
			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			job.setJobStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
			}
			jobList.add(job);
		}
		return jobList;
	}

	/**
	 * 暂停一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);
	}

	/**
	 * 恢复一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}

	/**
	 * 批量删除job
	 * 
	 * @param list
	 * @throws SchedulerException
	 */
	public void batchDeleteJob(Long[] jobIds) throws SchedulerException {
	    if(jobIds!=null&&jobIds.length>0){
	        Scheduler scheduler = schedulerFactoryBean.getScheduler();
            for(Long jobId:jobIds){
                ScheduleJob selectJob=scheduleJobMapper.selectByPrimaryKey(jobId);
                if(selectJob==null){
                    LOG.debug("当前查询出的job信息为空，jobId={}"+jobId);
                    continue ;
                }
                JobKey jobKey = JobKey.jobKey(selectJob.getJobName(), selectJob.getJobGroup());
                scheduleJobMapper.deleteByPrimaryKey(jobId);
                scheduler.deleteJob(jobKey);
            }
	    }
	    else{
	        throw new BaseException("jobId数组不能为空");   
	    }
	}

	/**
	 * 立即执行job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}

	/**
	 * 更新job时间表达式
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

		scheduler.rescheduleJob(triggerKey, trigger);
	}
	
	public List<TaskExcuteDetail> taskDetailList(Long jobId){
	    TaskExcuteDetail record=new TaskExcuteDetail();
	    record.setJobId(jobId);
	    return  taskExcuteDetailMapper.queryDetailList(record);
	}
	
	/**
     * 更新job时间表达式
     * 
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void saveOrUpdateTask(ScheduleJob scheduleJob) throws SchedulerException {
        Long jobId=scheduleJob.getJobId();
        if(jobId==null){
            addTask(scheduleJob);
            return;
        }
        //1.获取任务信息
        ScheduleJob job = getTaskById(jobId);
        if (job == null) {
            return;
        }
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        if (ScheduleConstants.STATUS_RUNNING.equals(scheduleJob.getJobStatus())) {
              JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
//              scheduleJobMapper.deleteByPrimaryKey(jobId);
              scheduler.deleteJob(jobKey);
              addJob(scheduleJob);
        }
        scheduleJob.setCreateTime(job.getCreateTime());
        scheduleJob.setUpdateTime(new Date());
        scheduleJobMapper.updateByPrimaryKey(scheduleJob);
    }
    
    /**
     * 更新任务执行明细信息
     * @Methods Name updateTaskDetail
     * @Create In 2014年12月26日 By mogu
     * @param deatail void
     */
    public void updateTaskDetail(TaskExcuteDetail deatail){
        deatail.setUpdateDate(new Date());
        taskExcuteDetailMapper.updateByPrimaryKeySelective(deatail);
    }
    
    /**
     * 立即执行任务作为job补偿
     * @Methods Name immediateExc
     * @Create In 2015年4月10日 By mogu
     * @param jobId void
     */
    public void immediateExc(Long jobId){
        ScheduleJob job = getTaskById(jobId);
        if (job == null) {
            return;
        }
        UnityTask task=(UnityTask)SpringUtils.getBean(UnityTask.class);
        boolean thirdResult=task.run(job.getJobId(),job.getUrl(), job.getParas());
        LOG.info("当前立即执行的对象为：scheduleJob={},result={}",new Object[]{job,thirdResult});
    }
}
