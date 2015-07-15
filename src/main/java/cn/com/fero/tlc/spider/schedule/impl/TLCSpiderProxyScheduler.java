package cn.com.fero.tlc.spider.schedule.impl;

import cn.com.fero.tlc.spider.exception.TLCSpiderSchedulerException;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.schedule.TLCSpiderScheduler;
import cn.com.fero.tlc.spider.util.TLCSpiderLoggerUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Required;

/**
 * Created by gizmo on 15/6/17.
 */
public class TLCSpiderProxyScheduler extends TLCSpiderScheduler {
    private Scheduler scheduler;
    private TLCSpiderJob proxyJob;

    @Required
    public void setProxyJob(TLCSpiderJob proxyJob) {
        this.proxyJob = proxyJob;
    }

    @Override
    public void init() {
        TLCSpiderLoggerUtil.getLogger().info("初始化proxy");
        try {
            this.scheduler = schedulerFactory.getScheduler();
        } catch (SchedulerException e) {
            TLCSpiderLoggerUtil.getLogger().error(ExceptionUtils.getFullStackTrace(e));
        }
    }

    @Override
    public void loadJobs() {
        try {
            TLCSpiderLoggerUtil.getLogger().info("加载proxy");
            if (null == scheduler) {
                init();
            }

            JobKey jobKey = JobKey.jobKey(proxyJob.getJobName(), proxyJob.getJobGroupName());
            JobDetail jobDetail;
            if (scheduler.checkExists(jobKey)) {
                jobDetail = scheduler.getJobDetail(jobKey);
            } else {
                jobDetail = JobBuilder.newJob(proxyJob.getClass())
                        .withIdentity(jobKey)
                        .build();
            }

            TriggerKey triggerKey = TriggerKey.triggerKey(proxyJob.getTriggerName(), proxyJob.getTriggerGroupName());
            Trigger trigger;
            if (scheduler.checkExists(triggerKey)) {
                trigger = scheduler.getTrigger(triggerKey);
            } else {
                CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
                        .cronSchedule(proxyJob.getCronExpression());
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        .withSchedule(cronScheduleBuilder)
                        .startNow()
                        .build();
            }

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            TLCSpiderLoggerUtil.getLogger().error(ExceptionUtils.getFullStackTrace(e));
        }
    }

    @Override
    public void start() {
        try {
            TLCSpiderLoggerUtil.getLogger().info("启动proxy，开始执行ip代理");
            if (!scheduler.isStarted()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            TLCSpiderLoggerUtil.getLogger().error(ExceptionUtils.getFullStackTrace(e));
        }
    }

    @Override
    public void shutdown() {
        try {
            TLCSpiderLoggerUtil.getLogger().info("关闭proxy");
            if (!scheduler.isShutdown()) {
                scheduler.shutdown(true);
            }
        } catch (SchedulerException e) {
            TLCSpiderLoggerUtil.getLogger().error(ExceptionUtils.getFullStackTrace(e));
        }
    }
}
