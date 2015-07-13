package cn.com.fero.tlc.spider.start;

import cn.com.fero.tlc.spider.quartz.TLCSpiderScheduler;
import cn.com.fero.tlc.spider.util.TLCSpiderLoggerUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wanghongmeng on 2015/6/16.
 */
public final class TCLSpiderStarter {

    public static void main(String[] args) throws InterruptedException {
        TLCSpiderScheduler tlcSpiderScheduler = null;
        try {
            TLCSpiderLoggerUtil.getLogger().info("加载spring配置文件");
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*: spring-*.xml");

            TLCSpiderLoggerUtil.getLogger().info("获取钛理财scheduler");
            tlcSpiderScheduler = (TLCSpiderScheduler) applicationContext.getBean("tlcSpiderScheduler");

            TLCSpiderLoggerUtil.getLogger().info("加载钛理财jobs");
            tlcSpiderScheduler.loadJobs();

            TLCSpiderLoggerUtil.getLogger().info("开始钛理财抓取");
            tlcSpiderScheduler.start();
        } catch (BeansException e) {
            TLCSpiderLoggerUtil.getLogger().error(ExceptionUtils.getFullStackTrace(e));
            if (null != tlcSpiderScheduler) {
                tlcSpiderScheduler.shutdown();
            }
        }
    }
}