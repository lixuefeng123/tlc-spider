package cn.com.fero.tlc.spider;

import cn.com.fero.tlc.spider.schedule.TLCSpiderScheduler;
import cn.com.fero.tlc.spider.util.TLCSpiderLoggerUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wanghongmeng on 2015/6/16.
 */
public final class TCLSpiderServer {
    private static ApplicationContext applicationContext;

    public static void main(String[] args) throws InterruptedException {
        TLCSpiderScheduler p2pScheduler = null;
        TLCSpiderScheduler articleScheduler = null;
        try {
            TLCSpiderLoggerUtil.getLogger().info("加载spring配置文件");
            applicationContext = new ClassPathXmlApplicationContext("classpath*: spring-*.xml");
//            p2pScheduler = initScheduler("tlcSpiderP2PScheduler");
            articleScheduler = initScheduler("tlcSpiderArticleScheduler");
        } catch (BeansException e) {
            TLCSpiderLoggerUtil.getLogger().error(ExceptionUtils.getFullStackTrace(e));
            if (null != p2pScheduler) {
                p2pScheduler.shutdown();
            }
            if (null != articleScheduler) {
                articleScheduler.shutdown();
            }
        }
    }

    private static TLCSpiderScheduler initScheduler(String scheduleName) {
        TLCSpiderScheduler scheduler = (TLCSpiderScheduler) applicationContext.getBean(scheduleName);
        scheduler.init();
        scheduler.loadJobs();
        scheduler.start();
        return scheduler;
    }
}