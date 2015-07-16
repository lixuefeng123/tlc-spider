package cn.com.fero.tlc.spider.schedule.impl;

import cn.com.fero.tlc.spider.exception.TLCSpiderSchedulerException;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.schedule.TLCSpiderScheduler;
import cn.com.fero.tlc.spider.util.TLCSpiderLoggerUtil;
import org.quartz.*;

/**
 * Created by gizmo on 15/6/17.
 */
public class TLCSpiderP2PScheduler extends TLCSpiderScheduler {

    @Override
    public void init() {
        try {
            TLCSpiderLoggerUtil.getLogger().info("初始化p2p");
            this.scheduler = schedulerFactory.getScheduler();
        } catch (SchedulerException e) {
            throw new TLCSpiderSchedulerException(e);
        }
    }

    @Override
    public void loadJobs() {
        try {
            TLCSpiderLoggerUtil.getLogger().info("加载p2p");
            if (null == scheduler) {
                init();
            }

            for (TLCSpiderJob job : jobList) {

                JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroupName());
                JobDetail jobDetail;
                if (scheduler.checkExists(jobKey)) {
                    jobDetail = scheduler.getJobDetail(jobKey);
                } else {
                    jobDetail = JobBuilder.newJob(job.getClass())
                            .withIdentity(jobKey)
                            .build();
                }

                TriggerKey triggerKey = TriggerKey.triggerKey(job.getTriggerName(), job.getTriggerGroupName());
                Trigger trigger;
                if (scheduler.checkExists(triggerKey)) {
                    trigger = scheduler.getTrigger(triggerKey);
                } else {
                    CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
                            .cronSchedule(job.getCronExpression());
                    trigger = TriggerBuilder.newTrigger()
                            .withIdentity(triggerKey)
                            .withSchedule(cronScheduleBuilder)
                            .startNow()
                            .build();
                }

                scheduler.scheduleJob(jobDetail, trigger);
            }
        } catch (SchedulerException e) {
            throw new TLCSpiderSchedulerException(e);
        }
    }

    @Override
    public void start() {
        try {
            TLCSpiderLoggerUtil.getLogger().info("启动p2p，开始执行抓取");
            if (!scheduler.isStarted()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            throw new TLCSpiderSchedulerException(e);
        }
    }

    @Override
    public void shutdown() {
        try {
            TLCSpiderLoggerUtil.getLogger().info("关闭p2p");
            if (!scheduler.isShutdown()) {
                scheduler.shutdown(true);
            }
        } catch (SchedulerException e) {
            throw new TLCSpiderSchedulerException(e);
        }
    }
}
