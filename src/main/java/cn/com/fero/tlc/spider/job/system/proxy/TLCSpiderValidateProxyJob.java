package cn.com.fero.tlc.spider.job.system.proxy;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderLoggerUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Set;

/**
 * Created by wanghongmeng on 2015/7/15.
 */
public class TLCSpiderValidateProxyJob extends TLCSpiderJob {
    private static final String URL_TEST = TLCSpiderPropertiesUtil.getResource("tlc.spider.proxy.url.test");
    private static final String IP_LOCALHOST = TLCSpiderPropertiesUtil.getResource("tlc.spider.proxy.ip.localhost");

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if(!isValidateProxy()) {
            updateProxy();
        }
    }

    public void updateProxy() {
        Set<String> usefulIp = TLCSpiderFetchProxyJob.usefulIp;
        if (CollectionUtils.isEmpty(usefulIp)) {
            TLCSpiderLoggerUtil.getLogger().info("代理IP列表为空");
            clearProxy();
            return;
        }

        int random = RandomUtils.nextInt(usefulIp.size());
        String ipAddress = (String) usefulIp.toArray()[random];
        TLCSpiderLoggerUtil.getLogger().info("更新代理IP: " + ipAddress);
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

    public static boolean isValidateProxy() {
        String ip = System.getProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_HOST);
        String port = System.getProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_PORT);

        if(StringUtils.isEmpty(ip) || StringUtils.isEmpty(port)) {
            return false;
        }

        if(BooleanUtils.isFalse(NumberUtils.isNumber(port))) {
            return false;
        }

        String response;
        try {
            response = TLCSpiderRequest.get(URL_TEST, true);
        } catch (Exception e) {
            TLCSpiderLoggerUtil.getLogger().info("代理不可用，重新更新代理");
            return false;
        }

        if (!response.contains(IP_LOCALHOST)) {
            TLCSpiderLoggerUtil.getLogger().info("代理可用，执行后续抓取");
            return true;
        }

        TLCSpiderLoggerUtil.getLogger().info("代理不可用，重新更新代理");
        return false;
    }

    public void clearProxy() {
        TLCSpiderLoggerUtil.getLogger().info("清除代理");
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_SET, "false");
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_HOST, StringUtils.EMPTY);
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_PORT, StringUtils.EMPTY);
    }
}
