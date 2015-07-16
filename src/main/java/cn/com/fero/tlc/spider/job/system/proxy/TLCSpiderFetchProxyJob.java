package cn.com.fero.tlc.spider.job.system.proxy;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.exception.TLCSpiderProxyException;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.job.system.proxy.impl.TLCSpiderDL5566IpFetcher;
import cn.com.fero.tlc.spider.job.system.proxy.impl.TLCSpiderKDLIpFetcher;
import cn.com.fero.tlc.spider.job.system.proxy.impl.TLCSpiderXCNNIpFetcher;
import cn.com.fero.tlc.spider.job.system.proxy.impl.TLCSpiderXCNTIpFetcher;
import cn.com.fero.tlc.spider.util.TLCSpiderLoggerUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wanghongmeng on 2015/7/15.
 */
public class TLCSpiderFetchProxyJob extends TLCSpiderJob {
    private static final TLCSpiderIpFetcher[] fetchers = new TLCSpiderIpFetcher[]{
            new TLCSpiderKDLIpFetcher(),
            new TLCSpiderXCNNIpFetcher(),
            new TLCSpiderXCNTIpFetcher(),
            new TLCSpiderDL5566IpFetcher()
    };
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(TLCSpiderConstants.SPIDER_CONST_THREAD_SIZE);
    public static final Set<String> usefulIp = Collections.synchronizedSet(new HashSet());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            usefulIp.clear();

            final CountDownLatch gate = new CountDownLatch(fetchers.length);
            for (final TLCSpiderIpFetcher ipFetcher : fetchers) {
                THREAD_POOL.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            usefulIp.addAll(ipFetcher.doFetch());
                        } catch (Exception e) {
                            throw new TLCSpiderProxyException(e);
                        } finally {
                            gate.countDown();
                        }
                    }
                });
            }

            gate.await();
            TLCSpiderLoggerUtil.getLogger().info("抓取代理IP结束");
        } catch (InterruptedException e) {
            TLCSpiderLoggerUtil.getLogger().error(ExceptionUtils.getFullStackTrace(e));
        }
    }
}
