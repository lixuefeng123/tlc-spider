package cn.com.fero.tlc.spider.util;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.vo.p2p.RequestProxy;
import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * Created by gizmo on 15/6/19.
 */
public final class TLCSpiderProxyUtil {
    private static final String PROXY_URL_HTTP = TLCSpiderPropertiesUtil.getResource("tlc.spider.proxy.url.http");
    private static final String PROXY_URL_HTTPS = TLCSpiderPropertiesUtil.getResource("tlc.spider.proxy.url.https");

    private TLCSpiderProxyUtil() {
        throw new UnsupportedClassVersionError();
    }

    public static RequestProxy getHttpProxy() {
        return getProxy(PROXY_URL_HTTP);
    }

    public static RequestProxy getHttpsProxy() {
        return getProxy(PROXY_URL_HTTPS);
    }

    private static RequestProxy getProxy(String url) {
        RequestProxy requestProxy = null;
        try {
            String response = TLCSpiderRequest.get(url);
            String status = TLCSpiderJsonUtil.getString(response, "status");
            int statusCode = Integer.parseInt(status);
            if (statusCode != TLCSpiderConstants.SPIDER_CONST_RESPONSE_STATUS_SUCCESS) {
                TLCSpiderLoggerUtil.getLogger().info("获取代理返回状态{}, 无代理可用", statusCode);
                return requestProxy;
            }

            String proxyStr = TLCSpiderJsonUtil.getString(response, "proxy");
            TLCSpiderLoggerUtil.getLogger().info("取得代理{}", proxyStr);
            requestProxy = (RequestProxy) TLCSpiderJsonUtil.json2Object(proxyStr, RequestProxy.class);
            return requestProxy;
        } catch (NumberFormatException e) {
            TLCSpiderLoggerUtil.getLogger().error(ExceptionUtils.getFullStackTrace(e));
            return requestProxy;
        }
    }
}
