package com.leader.game.quartz.manager;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 调度管理器
 * 
 * @author Administrator
 */
public class SchedulerManager {
	/** 调度工厂 */
	private Scheduler scheduler;
	private Logger log = LoggerFactory.getLogger(SchedulerManager.class);

	private SchedulerManager() {
	}

	private static class SingletonHolder {
		static final SchedulerManager INSTANCE = new SchedulerManager();
	}

	public static SchedulerManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/** 初始化方法 */
	public void init() throws SchedulerException {
		scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
	}

	/**
	 * 添加一个CronTrigger定时任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param triggerName
	 *            触发器名
	 * @param job
	 *            任务
	 * @param expression
	 *            调度时间设置，参考quartz说明文档
	 * @throws SchedulerException
	 */
	public void addJob(String jobName, String triggerName, String expression, Job job) throws SchedulerException {
		JobDetail jobDetail = newJob(job.getClass()).withIdentity(jobName, "default_job_group_name").build();
		CronTrigger trigger = newTrigger().withIdentity(triggerName, "default_trigger_group_name")
				.withSchedule(cronSchedule(expression)).build();
		scheduler.scheduleJob(jobDetail, trigger);
	}

	/**
	 * 移除一个任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param triggerName
	 *            触发器名
	 * @throws SchedulerException
	 */
	public void removeJob(String jobName, String triggerName) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(triggerName, "default_trigger_group_name");
		JobKey jobKey = new JobKey(jobName, "default_job_group_name");
		scheduler.pauseTrigger(triggerKey);// 停止触发器
		scheduler.unscheduleJob(triggerKey);// 移除触发器
		scheduler.deleteJob(jobKey);// 删除任务
	}

	/**
	 * 数据服务器关闭时候，调用的方法
	 */
	public void shutdown() {
		try {
			scheduler.shutdown(false);
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
		}
	}

}
