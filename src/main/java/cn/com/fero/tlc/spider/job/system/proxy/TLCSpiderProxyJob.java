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
public class TLCSpiderProxyJob extends TLCSpiderJob {
    private static final String URL_TEST = TLCSpiderPropertiesUtil.getResource("tlc.spider.proxy.url.test");
    private static final String IP_LOCALHOST = TLCSpiderPropertiesUtil.getResource("tlc.spider.proxy.ip.localhost");

    private static final TLCSpiderIpFetcher[] fetchers = new TLCSpiderIpFetcher[]{
            new TLCSpiderKDLIpFetcher(),
            new TLCSpiderXCNNIpFetcher(),
            new TLCSpiderXCNTIpFetcher(),
            new TLCSpiderDL5566IpFetcher()
    };
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(TLCSpiderConstants.SPIDER_CONST_THREAD_SIZE);
    private static final Set<String> usefulIp = Collections.synchronizedSet(new HashSet());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            clearProxy();

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
            updateProxy();
        } catch (InterruptedException e) {
            TLCSpiderLoggerUtil.getLogger().error(ExceptionUtils.getFullStackTrace(e));
        }
    }

//        try {
//            Scanner scanner = new Scanner(new File(IP_FILE));
//            while(scanner.hasNext()) {
//                usefulIp.add(scanner.nextLine());
//            }
//            updateProxy();
//        } catch (FileNotFoundException e) {
//            throw new TLCSpiderProxyException(e);
//        }
//    }

//    public void persistToFile() throws IOException {
//        File ipFile = new File(IP_FILE);
//        FileWriter writer = new FileWriter(ipFile, true);
//        for(String ip : usefulIp) {
//            writer.write(ip);
//        }
//        writer.flush();
//    }

    public void updateProxy() {
        if (CollectionUtils.isEmpty(usefulIp)) {
            TLCSpiderLoggerUtil.getLogger().info("无可用代理");
            return;
        }

        int random = RandomUtils.nextInt(usefulIp.size());
        String ipAddress = (String) usefulIp.toArray()[random];
        TLCSpiderLoggerUtil.getLogger().info("更新代理ip: " + ipAddress);
        String[] ipAddressArray = ipAddress.split(TLCSpiderConstants.SPIDER_CONST_COLON);
        String ip = ipAddressArray[0];
        String port = ipAddressArray[1];
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_SET, "true");
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_HOST, ip);
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_PORT, port);
        if (BooleanUtils.isFalse(isValidateProxy())) {
            usefulIp.remove(ipAddress);
            updateProxy();
        }
    }

    private boolean isValidateProxy() {
        String response;
        try {
            response = TLCSpiderRequest.get(URL_TEST);
        } catch (Exception e) {
            TLCSpiderLoggerUtil.getLogger().info("代理不可用，重新更新代理");
            return false;
        }

        if (!response.contains(IP_LOCALHOST)) {
            TLCSpiderLoggerUtil.getLogger().info("代理可用，执行后续抓取");
            return true;
        }

        TLCSpiderLoggerUtil.getLogger().info("代理不可用，请重新更新代理");
        return false;
    }

    public void clearProxy() {
        TLCSpiderLoggerUtil.getLogger().info("清除代理");
        usefulIp.clear();
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_SET, "false");
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_HOST, StringUtils.EMPTY);
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_PORT, StringUtils.EMPTY);
    }
}
