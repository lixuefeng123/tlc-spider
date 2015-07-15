package cn.com.fero.tlc.spider.util;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.exception.TLCSpiderUtilException;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import org.apache.commons.lang.math.RandomUtils;
import org.htmlcleaner.TagNode;

import java.io.*;
import java.util.*;

/**
 * Created by wanghongmeng on 2015/6/24.
 */
public final class TLCSpiderProxyUtil {
    private static final File ipFile = new File("src/main/resources/impl.list");
    private static final String urlPrefix = TLCSpiderPropertiesUtil.getResource("tlc.spider.proxy.url");
    private static final Set<String> usefulIp = new HashSet();
    private static final Set<String> uselessIp = new HashSet();

    private TLCSpiderProxyUtil() {
        throw new UnsupportedOperationException();
    }

    public static void fetchIpAddress() {
        try {
            FileWriter writer = new FileWriter(ipFile, true);
            String totalPage = getTotalPage(urlPrefix);
            TLCSpiderLoggerUtil.getLogger().info("抓取代理ip，总页数:" + totalPage);

            int totalPageNum = Integer.parseInt(totalPage);
            for(int page = 1; page <= totalPageNum; page++) {
                TLCSpiderLoggerUtil.getLogger().info("开始抓取代理ip第" + page + "页");
                List<TagNode> ipNodeList = getIpNode(urlPrefix, page);
                writeToFile(ipNodeList, writer);
            }
        } catch (Exception e) {
            throw new TLCSpiderUtilException(e);
        } finally {
            TLCSpiderLoggerUtil.getLogger().info("抓取代理ip结束");
        }
    }

    private static void writeToFile(List<TagNode> ipNodeList, FileWriter writer) throws IOException {
        for(TagNode ipNode : ipNodeList) {
            String ip = TLCSpiderHTMLParser.parseText(ipNode, "td[1]");
            String port = TLCSpiderHTMLParser.parseText(ipNode, "td[2]");
            String ipStr = ip + TLCSpiderConstants.SPIDER_CONST_COLON + port + System.getProperty("line.separator");
            writer.write(ipStr);
        }
        writer.flush();
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

    public static void loadIpToMemory() {
        try {
            Scanner scanner = new Scanner(ipFile);
            while(scanner.hasNext()) {
                String ip = scanner.nextLine();
                if(!uselessIp.contains(ip)) {
                    usefulIp.add(scanner.nextLine());
                }
            }
        } catch (Exception e) {
            throw new TLCSpiderUtilException(e);
        } finally {
            TLCSpiderLoggerUtil.getLogger().info("加载ip到内存结束，共加载" + usefulIp.size() + "条数据");
        }
    }

    public static void setProxy() {
        int random = RandomUtils.nextInt(usefulIp.size());
        String ipAddress = (String) usefulIp.toArray()[random];
        TLCSpiderLoggerUtil.getLogger().info("更新代理ip: " + ipAddress);
        String[] ipAddressArray = ipAddress.split(TLCSpiderConstants.SPIDER_CONST_COLON);
        String ip = ipAddressArray[0];
        String port = ipAddressArray[1];
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_SET, "true");
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_HOST, ip);
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_PORT, port);
//        String response = TLCSpiderRequest.get(TLCSpiderPropertiesUtil.getResource("tlc.spider.impl.url"));
//            if(response.contains()) {
//        }
    }

    public static void clearProxy() {
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_SET, "false");
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_HOST, null);
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_PORT, null);
    }
}
