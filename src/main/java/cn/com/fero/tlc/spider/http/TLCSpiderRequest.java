package cn.com.fero.tlc.spider.http;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.exception.TLCSpiderRequestException;
import cn.com.fero.tlc.spider.util.TLCSpiderLoggerUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderProxyUtil;
import cn.com.fero.tlc.spider.vo.p2p.RequestProxy;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by gizmo on 15/6/18.
 */
public class TLCSpiderRequest {
    public static String getViaProxy(String url, ProxyType proxyType, Map<String, String> header) {
        if (StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException();
        }

        try {
            RequestConfig config = constructProxyConfig(proxyType);
            Map<String, Object> responseMap = executeGetRequest(url, config, header);

            int status = (int) responseMap.get(TLCSpiderConstants.SPIDER_PARAM_STATUS_NAME);
            if (status != TLCSpiderConstants.SPIDER_CONST_RESPONSE_STATUS_SUCCESS) {
                throw new TLCSpiderRequestException("not response 200 via proxy");
            }

            return (String) responseMap.get(TLCSpiderConstants.SPIDER_CONST_RESPONSE_CONTENT);
        } catch (Exception e) {
            TLCSpiderLoggerUtil.getLogger().error("使用{}代理发生异常，去除代理重新请求", proxyType.toString());
            return get(url);
        }
    }

    public static String getViaProxy(String url, ProxyType proxyType) {
        if (StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException();
        }

        try {
            RequestConfig config = constructProxyConfig(proxyType);
            Map<String, Object> responseMap = executeGetRequest(url, config, Collections.EMPTY_MAP);

            int status = (int) responseMap.get(TLCSpiderConstants.SPIDER_PARAM_STATUS_NAME);
            if (status != TLCSpiderConstants.SPIDER_CONST_RESPONSE_STATUS_SUCCESS) {
                throw new TLCSpiderRequestException("not response 200 via proxy");
            }

            return (String) responseMap.get(TLCSpiderConstants.SPIDER_CONST_RESPONSE_CONTENT);
        } catch (Exception e) {
            TLCSpiderLoggerUtil.getLogger().error("使用{}代理发生异常，去除代理重新请求", proxyType.toString());
            return get(url);
        }
    }

    public static String get(String url) {
        if (StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException();
        }

        try {
            RequestConfig config = constructHttpConfig();
            Map<String, Object> responseMap = executeGetRequest(url, config, Collections.EMPTY_MAP);
            return (String) responseMap.get(TLCSpiderConstants.SPIDER_CONST_RESPONSE_CONTENT);
        } catch (Exception e) {
            throw new TLCSpiderRequestException(e);
        }
    }

    public static String get(String url, Map<String, String> header) {
        if (StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException();
        }

        try {
            RequestConfig config = constructHttpConfig();
            Map<String, Object> responseMap = executeGetRequest(url, config, header);
            return (String) responseMap.get(TLCSpiderConstants.SPIDER_CONST_RESPONSE_CONTENT);
        } catch (Exception e) {
            throw new TLCSpiderRequestException(e);
        }
    }

    public static String postViaProxy(String url, Map<String, String> param, ProxyType proxyType, Map<String, String> header) {
        if (StringUtils.isEmpty(url) || MapUtils.isEmpty(param)) {
            throw new IllegalArgumentException();
        }

        try {
            RequestConfig config = constructProxyConfig(proxyType);
            Map<String, Object> responseMap = executePostRequest(url, param, config, header);

            int status = (int) responseMap.get(TLCSpiderConstants.SPIDER_PARAM_STATUS_NAME);
            if (status != TLCSpiderConstants.SPIDER_CONST_RESPONSE_STATUS_SUCCESS) {
                throw new TLCSpiderRequestException("not response 200 via proxy");
            }

            return (String) responseMap.get(TLCSpiderConstants.SPIDER_CONST_RESPONSE_CONTENT);
        } catch (Exception e) {
            TLCSpiderLoggerUtil.getLogger().error("使用{}代理发生异常，去除代理重新请求", proxyType.toString());
            return post(url, param, header);
        }
    }

    public static String postViaProxy(String url, Map<String, String> param, ProxyType proxyType) {
        if (StringUtils.isEmpty(url) || MapUtils.isEmpty(param)) {
            throw new IllegalArgumentException();
        }

        try {
            RequestConfig config = constructProxyConfig(proxyType);
            Map<String, Object> responseMap = executePostRequest(url, param, config, Collections.EMPTY_MAP);

            int status = (int) responseMap.get(TLCSpiderConstants.SPIDER_PARAM_STATUS_NAME);
            if (status != TLCSpiderConstants.SPIDER_CONST_RESPONSE_STATUS_SUCCESS) {
                throw new TLCSpiderRequestException("not response 200 via proxy");
            }

            return (String) responseMap.get(TLCSpiderConstants.SPIDER_CONST_RESPONSE_CONTENT);
        } catch (Exception e) {
            TLCSpiderLoggerUtil.getLogger().error("使用{}代理发生异常，去除代理重新请求", proxyType.toString());
            return post(url, param);
        }
    }

    public static String post(String url, Map<String, String> param, Map<String, String> header) {
        if (StringUtils.isEmpty(url) || MapUtils.isEmpty(param)) {
            throw new IllegalArgumentException();
        }

        try {
            RequestConfig config = constructHttpConfig();
            Map<String, Object> responseMap = executePostRequest(url, param, config, header);
            return (String) responseMap.get(TLCSpiderConstants.SPIDER_CONST_RESPONSE_CONTENT);
        } catch (Exception e) {
            throw new TLCSpiderRequestException(e);
        }
    }

    public static String post(String url, Map<String, String> param) {
        if (StringUtils.isEmpty(url) || MapUtils.isEmpty(param)) {
            throw new IllegalArgumentException();
        }

        try {
            RequestConfig config = constructHttpConfig();
            Map<String, Object> responseMap = executePostRequest(url, param, config, Collections.EMPTY_MAP);
            return (String) responseMap.get(TLCSpiderConstants.SPIDER_CONST_RESPONSE_CONTENT);
        } catch (Exception e) {
            throw new TLCSpiderRequestException(e);
        }
    }

    private static Map<String, Object> executeGetRequest(String url, RequestConfig config, Map<String, String> header) throws IOException {
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        HttpGet httpGet = new HttpGet(url);
        return executeRequest(httpClient, httpGet, header);
    }

    private static Map<String, Object> executePostRequest(String url, Map<String, String> param, RequestConfig config, Map<String, String> header) throws IOException {
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        HttpPost httpPost = new HttpPost(url);

        HttpEntity entity = constructPostEntity(param);
        httpPost.setEntity(entity);

        return executeRequest(httpClient, httpPost, header);
    }

    private static HttpEntity constructPostEntity(Map<String, String> param) throws UnsupportedEncodingException {
        List<NameValuePair> paramList = new ArrayList();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return new UrlEncodedFormEntity(paramList, CharsetUtils.get(TLCSpiderConstants.SPIDER_CONST_ENCODING));
    }

    private static RequestConfig constructProxyConfig(ProxyType proxyType) {
        RequestProxy requestProxy = null;
        switch (proxyType) {
            case HTTP:
                requestProxy = TLCSpiderProxyUtil.getHttpProxy();
                break;
            case HTTPS:
                requestProxy = TLCSpiderProxyUtil.getHttpsProxy();
                break;
        }
        if (null == requestProxy) {
            TLCSpiderLoggerUtil.getLogger().info("无可用{}代理", proxyType.toString());
            return constructHttpConfig();
        }

        RequestConfig.Builder builder = RequestConfig.custom();
        builder.setConnectTimeout(TLCSpiderConstants.SPIDER_CONST_HTTP_TIMEOUT);
        builder.setConnectionRequestTimeout(TLCSpiderConstants.SPIDER_CONST_HTTP_TIMEOUT);
        builder.setSocketTimeout(TLCSpiderConstants.SPIDER_CONST_HTTP_TIMEOUT);
        builder.setProxy(new HttpHost(requestProxy.getIp(), requestProxy.getPort()));
        return builder.build();
    }

    private static RequestConfig constructHttpConfig() {
        RequestConfig.Builder builder = RequestConfig.custom();
        builder.setConnectTimeout(TLCSpiderConstants.SPIDER_CONST_HTTP_TIMEOUT);
        builder.setConnectionRequestTimeout(TLCSpiderConstants.SPIDER_CONST_HTTP_TIMEOUT);
        builder.setSocketTimeout(TLCSpiderConstants.SPIDER_CONST_HTTP_TIMEOUT);
        return builder.build();
    }

    private static Map<String, Object> executeRequest(CloseableHttpClient httpClient, HttpRequestBase request, Map<String, String> header) throws IOException {
        Map<String, Object> responseMap = new HashMap();

        for (Map.Entry<String, String> entry : header.entrySet()) {
            request.setHeader(entry.getKey(), entry.getValue());
        }

        CloseableHttpResponse response = httpClient.execute(request);
        responseMap.put(TLCSpiderConstants.SPIDER_PARAM_STATUS_NAME, response.getStatusLine().getStatusCode());
        responseMap.put(TLCSpiderConstants.SPIDER_CONST_RESPONSE_CONTENT, EntityUtils.toString(response.getEntity(), TLCSpiderConstants.SPIDER_CONST_ENCODING));

        return responseMap;
    }

    public enum ProxyType {HTTP, HTTPS}
}
