package cn.com.fero.tlc.spider.schedule;

import cn.com.fero.tlc.spider.exception.TLCSpiderSchedulerException;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderLoggerUtil;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

/**
 * Created by gizmo on 15/6/17.
 */
public class TLCSpiderScheduler {
    protected SchedulerFactory schedulerFactory;
    protected Scheduler scheduler;
    protected List<TLCSpiderJob> jobList;
    protected String schedulerName;

    @Required
    public void setSchedulerFactory(SchedulerFactory schedulerFactory) {
        this.schedulerFactory = schedulerFactory;
    }

    @Required
    public void setJobList(List<TLCSpiderJob> jobList) {
        this.jobList = jobList;
    }

    @Required
    public void setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    public void init() {
        try {
            TLCSpiderLoggerUtil.getLogger().info("初始化{}", schedulerName);
            this.scheduler = schedulerFactory.getScheduler();
        } catch (SchedulerException e) {
            throw new TLCSpiderSchedulerException(e);
        }
    }

    public void loadJobs() {
        try {
            TLCSpiderLoggerUtil.getLogger().info("加载{}" , schedulerName);
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

    public void start() {
        try {
            TLCSpiderLoggerUtil.getLogger().info("启动{}，开始执行抓取", schedulerName);
            if (!scheduler.isStarted()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            throw new TLCSpiderSchedulerException(e);
        }
    }

    public void shutdown() {
        try {
            TLCSpiderLoggerUtil.getLogger().info("关闭{}", schedulerName);
            if (!scheduler.isShutdown()) {
                scheduler.shutdown(true);
            }
        } catch (SchedulerException e) {
            throw new TLCSpiderSchedulerException(e);
        }
    }
}
