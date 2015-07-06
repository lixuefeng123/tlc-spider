package cn.com.fero.tlc.spider.server;

import cn.com.fero.tlc.spider.quartz.TLCSpiderScheduler;
import cn.com.fero.tlc.spider.util.LoggerUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wanghongmeng on 2015/6/16.
 */
public final class TCLSpiderServer {

    public static void main(String[] args) throws InterruptedException {
        TLCSpiderScheduler tlcSpiderScheduler = null;
        try {
            LoggerUtil.getLogger().info("加载spring配置文件");
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*: spring-*.xml");

            LoggerUtil.getLogger().info("获取钛理财scheduler");
            tlcSpiderScheduler = (TLCSpiderScheduler) applicationContext.getBean("tlcSpiderScheduler");

            LoggerUtil.getLogger().info("加载钛理财jobs");
            tlcSpiderScheduler.loadJobs();

            LoggerUtil.getLogger().info("开始钛理财抓取");
            tlcSpiderScheduler.start();
        } catch (BeansException e) {
            LoggerUtil.getLogger().error(e.toString());
            if (null != tlcSpiderScheduler) {
                tlcSpiderScheduler.shutdown();
            }
        }
    }
}