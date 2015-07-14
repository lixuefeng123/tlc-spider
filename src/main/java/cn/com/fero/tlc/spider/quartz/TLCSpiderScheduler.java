package cn.com.fero.tlc.spider.quartz;

import cn.com.fero.tlc.spider.exception.TLCSpiderSchedulerException;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

/**
 * Created by gizmo on 15/6/17.
 */
public class TLCSpiderScheduler {
    private SchedulerFactory schedulerFactory;
    private Scheduler scheduler;
    private List<TLCSpiderJob> jobList;

    @Required
    public void setSchedulerFactory(SchedulerFactory schedulerFactory) {
        this.schedulerFactory = schedulerFactory;
    }

    @Required
    public void setJobList(List<TLCSpiderJob> jobList) {
        this.jobList = jobList;
    }

    public void init() {
        try {
            this.scheduler = schedulerFactory.getScheduler();
        } catch (SchedulerException e) {
            throw new TLCSpiderSchedulerException(e);
        }
    }

    public void loadJobs() {
        try {
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
            if (!scheduler.isStarted()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            throw new TLCSpiderSchedulerException(e);
        }
    }

    public void shutdown() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown(true);
            }
        } catch (SchedulerException e) {
            throw new TLCSpiderSchedulerException(e);
        }
    }
}
