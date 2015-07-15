package cn.com.fero.tlc.spider.schedule;

import org.quartz.SchedulerFactory;
import org.springframework.beans.factory.annotation.Required;

/**
 * Created by gizmo on 15/6/17.
 */
public abstract class TLCSpiderScheduler {
    protected SchedulerFactory schedulerFactory;

    @Required
    public void setSchedulerFactory(SchedulerFactory schedulerFactory) {
        this.schedulerFactory = schedulerFactory;
    }

    public abstract void init();

    public abstract void loadJobs();

    public abstract void start();

    public abstract void shutdown();
}
