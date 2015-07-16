package cn.com.fero.tlc.spider.start;

import cn.com.fero.tlc.spider.schedule.TLCSpiderScheduler;
import cn.com.fero.tlc.spider.schedule.impl.TLCSpiderP2PScheduler;
import cn.com.fero.tlc.spider.schedule.impl.TLCSpiderProxyScheduler;
import cn.com.fero.tlc.spider.util.TLCSpiderLoggerUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wanghongmeng on 2015/6/16.
 */
public final class TCLSpiderStarter {

    private static ApplicationContext applicationContext;
    private static TLCSpiderScheduler proxyScheduler;
    private static TLCSpiderScheduler p2pScheduler;


    public static void main(String[] args) throws InterruptedException {
        try {
            TLCSpiderLoggerUtil.getLogger().info("加载spring配置文件");
            applicationContext = new ClassPathXmlApplicationContext("classpath*: spring-*.xml");

            proxyScheduler = initProxyScheduler();
            p2pScheduler = initP2PScheduler();
        } catch (BeansException e) {
            TLCSpiderLoggerUtil.getLogger().error(ExceptionUtils.getFullStackTrace(e));
            if (null != proxyScheduler) {
                proxyScheduler.shutdown();
            }
            if (null != p2pScheduler) {
                p2pScheduler.shutdown();
            }
        }
    }

    private static TLCSpiderScheduler initProxyScheduler() {
        proxyScheduler = (TLCSpiderScheduler) applicationContext.getBean("tlcSpiderProxyScheduler");
        proxyScheduler.init();
        proxyScheduler.loadJobs();
        proxyScheduler.start();
        return proxyScheduler;
    }

    private static TLCSpiderScheduler initP2PScheduler() {
        p2pScheduler = (TLCSpiderScheduler) applicationContext.getBean("tlcSpiderP2PScheduler");
        p2pScheduler.init();
        p2pScheduler.loadJobs();
        p2pScheduler.start();
        return p2pScheduler;
    }
}