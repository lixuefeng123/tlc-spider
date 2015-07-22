package cn.com.fero.tlc.spider.http.test;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderLoggerUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderProxyUtil;
import cn.com.fero.tlc.spider.vo.RequestProxy;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gizmo on 15/6/19.
 */
public class TLCSpiderProxyTest {

    @Test
    public void testProxy() {
        Map<String, String> map = new HashMap();
        map.put("ip", "myip");
        String response = TLCSpiderRequest.postViaProxy("http://ip.taobao.com/service/getIpInfo2.php", map, TLCSpiderRequest.ProxyType.HTTP);
        System.out.println(response);
    }

//
//    public String postViaProxy(String url, Map<String, String> param, TLCSpiderRequest.ProxyType proxyType) {
//        try {
//            RequestProxy requestProxy = TLCSpiderProxyUtil.getHttpsProxy();
//            RequestConfig.Builder builder = RequestConfig.custom();
//            builder.setConnectTimeout(TLCSpiderConstants.SPIDER_CONST_HTTP_TIMEOUT)
//                    .setSocketTimeout(TLCSpiderConstants.SPIDER_CONST_HTTP_TIMEOUT);
//            builder.setProxy(new HttpHost(requestProxy.getIp(), requestProxy.getPort()));
//            RequestConfig config = builder.build();
//
//            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
////            CloseableHttpClient httpClient = HttpClients.createDefault();
//            HttpPost httpPost = new HttpPost(url);
//
//            List<NameValuePair> paramList = new ArrayList();
//            for (Map.Entry<String, String> entry : param.entrySet()) {
//                paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//            }
//            httpPost.setEntity(new UrlEncodedFormEntity(paramList, CharsetUtils.get(TLCSpiderConstants.SPIDER_CONST_CHARACTER_ENCODING)));
//
////            httpPost.setConfig(config);
//            CloseableHttpResponse response = httpClient.execute(httpPost);
//            return EntityUtils.toString(response.getEntity(), TLCSpiderConstants.SPIDER_CONST_CHARACTER_ENCODING);
//        } catch (Exception e) {
//            TLCSpiderLoggerUtil.getLogger().error("使用{}发生异常，去除代理重新请求", proxyType.toString());
//            return null;
//        }
//    }

    @Test
    public void testGetHttpProxy() {
        RequestProxy requestProxy = TLCSpiderProxyUtil.getHttpProxy();
        System.out.println(requestProxy);
    }

    @Test
    public void testGetHttpsProxy() {
        RequestProxy requestProxy = TLCSpiderProxyUtil.getHttpsProxy();
        System.out.println(requestProxy);
    }


    @Test
    public void testHttpsProxy() {
        String url = TLCSpiderPropertiesUtil.getResource("tlc.spider.rdnsyherjz.url.list");

        Map<String, String> param = new HashMap();
        param.put("PageIndex", TLCSpiderConstants.SPIDER_PARAM_PAGE_ONE);
        param.put("PageSize", "10");
        param.put("targetAction", "CmbFinancingSearch");
        param.put("Interest", "");
        param.put("Duration", "");
        param.put("ProjectStatus", "");
        param.put("ProjectAmount", "");

        String countContent = TLCSpiderRequest.postViaProxy(url, param, TLCSpiderRequest.ProxyType.HTTPS);
//        String countContent = TLCSpiderRequest.post(url, param);
        String countStr = TLCSpiderJsonUtil.getString(countContent, "Data");
        String totalCount = TLCSpiderJsonUtil.getString(countStr, "TotalCount");
        System.out.println(totalCount);
    }
}
