package cn.com.fero.tlc.spider.http;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.exception.TLCSpiderRequestException;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gizmo on 15/6/18.
 */
public class TLCSpiderRequest {
    public static String get(String url) {
        if (StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException();
        }

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);

            if(isUsingProxy()) {
                HttpHost proxy = getHost();
                RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
                httpGet.setConfig(config);
            }

            CloseableHttpResponse response = httpClient.execute(httpGet);
            return EntityUtils.toString(response.getEntity(), TLCSpiderConstants.SPIDER_CONST_CHARACTER_ENCODING);
        } catch (Exception e) {
            throw new TLCSpiderRequestException(e);
        }
    }

    public static String post(String url, Map<String, String> param) {
        if (StringUtils.isEmpty(url) || MapUtils.isEmpty(param)) {
            throw new IllegalArgumentException();
        }

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> paramList = new ArrayList();
            for (Map.Entry<String, String> entry : param.entrySet()) {
                paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(paramList, CharsetUtils.get(TLCSpiderConstants.SPIDER_CONST_CHARACTER_ENCODING)));

            if(isUsingProxy()) {
                HttpHost proxy = getHost();
                RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
                httpPost.setConfig(config);
            }

            CloseableHttpResponse response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity(), TLCSpiderConstants.SPIDER_CONST_CHARACTER_ENCODING);
        } catch (Exception e) {
            throw new TLCSpiderRequestException(e);
        }
    }

    private static boolean isUsingProxy() {
        String ip = System.getProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_HOST);
        String port = System.getProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_PORT);
        if(StringUtils.isEmpty(ip) || StringUtils.isEmpty(port)) {
            return false;
        }

        return NumberUtils.isNumber(port);
    }

    private static HttpHost getHost() throws UnknownHostException {
        String ip = System.getProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_HOST);
        String port = System.getProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_PORT);
        int portNum = Integer.parseInt(port);
        return new HttpHost(ip, portNum);
    }
}
