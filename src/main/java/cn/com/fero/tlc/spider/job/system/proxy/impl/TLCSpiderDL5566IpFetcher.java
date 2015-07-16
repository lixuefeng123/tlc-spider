package cn.com.fero.tlc.spider.job.system.proxy.impl;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.exception.TLCSpiderUtilException;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.system.proxy.TLCSpiderIpFetcher;
import cn.com.fero.tlc.spider.util.TLCSpiderLoggerUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghongmeng on 2015/7/15.
 */
public class TLCSpiderDL5566IpFetcher extends TLCSpiderIpFetcher {
    private static final String urlPrefix = TLCSpiderPropertiesUtil.getResource("tlc.spider.proxy.url.dl5566");

    @Override
    protected List<String> doFetch() {
        try {
            TLCSpiderLoggerUtil.getLogger().info("开始抓取代理5566国内高匿代理");
            List<String> ipList = new ArrayList();

            String content = TLCSpiderRequest.get(urlPrefix);
            List<TagNode> ipNodeList = TLCSpiderHTMLParser.parseNode(content, "//div[@id='list']//table[@class='table table-bordered table-striped']/tbody/tr");

            for (TagNode ipNode : ipNodeList) {
                String ip = TLCSpiderHTMLParser.parseText(ipNode, "td[1]");
                String port = TLCSpiderHTMLParser.parseText(ipNode, "td[2]");
                if (StringUtils.isEmpty(ip) || StringUtils.isEmpty(port)) {
                    continue;
                }

                String ipStr = ip + TLCSpiderConstants.SPIDER_CONST_COLON + port;
                ipList.add(ipStr);
            }

            return ipList;
        } catch (Exception e) {
            throw new TLCSpiderUtilException(e);
        } finally {
            TLCSpiderLoggerUtil.getLogger().info("抓取代理5566国内高匿代理结束");
        }
    }
}
