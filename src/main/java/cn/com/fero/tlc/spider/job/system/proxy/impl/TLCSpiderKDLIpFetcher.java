package cn.com.fero.tlc.spider.job.system.proxy.impl;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.exception.TLCSpiderUtilException;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.system.proxy.TLCSpiderIpFetcher;
import cn.com.fero.tlc.spider.util.TLCSpiderLoggerUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import org.htmlcleaner.TagNode;

import java.io.IOException;
import java.util.*;

/**
 * Created by wanghongmeng on 2015/7/15.
 */
public class TLCSpiderKDLIpFetcher extends TLCSpiderIpFetcher {
    private static final String urlPrefix = TLCSpiderPropertiesUtil.getResource("tlc.spider.proxy.url.kdl");

    @Override
    protected List<String> doFetch() {
        List<String> ipList = new ArrayList();
        try {
            String totalPage = getTotalPage(urlPrefix);
            TLCSpiderLoggerUtil.getLogger().info("抓取快代理ip，总页数:" + totalPage);

//            int totalPageNum = Integer.parseInt(totalPage);
            int totalPageNum = 10;
            for(int page = 1; page <= totalPageNum; page++) {
                TLCSpiderLoggerUtil.getLogger().info("开始抓取快代理ip第" + page + "页");
                List<TagNode> ipNodeList = getIpNode(urlPrefix, page);
                addToIpList(ipNodeList, ipList);
            }

            return ipList;
        } catch (Exception e) {
            throw new TLCSpiderUtilException(e);
        } finally {
            TLCSpiderLoggerUtil.getLogger().info("抓取快代理ip结束");
        }
    }

    private void addToIpList(List<TagNode> ipNodeList, List<String> ipList) throws IOException {
        for(TagNode ipNode : ipNodeList) {
            String ip = TLCSpiderHTMLParser.parseText(ipNode, "td[1]");
            String port = TLCSpiderHTMLParser.parseText(ipNode, "td[2]");
            String ipStr = ip + TLCSpiderConstants.SPIDER_CONST_COLON + port;
            ipList.add(ipStr);
        }
    }

    private static List<TagNode> getIpNode(String urlPrefix, int page) {
        String url = urlPrefix + page;
        String content = TLCSpiderRequest.get(url);
        return TLCSpiderHTMLParser.parseNode(content, "//table[@class='table table-bordered table-striped']/tbody/tr");
    }

    private static String getTotalPage(String urlPrefix) {
        String url = urlPrefix + 1;
        String content = TLCSpiderRequest.get(url);
        List<TagNode> pageNodeList = TLCSpiderHTMLParser.parseNode(content, "//div[@id='listnav']/ul/li");
        int length = pageNodeList.size();
        return pageNodeList.get(length - 2).getText().toString();
    }
}
