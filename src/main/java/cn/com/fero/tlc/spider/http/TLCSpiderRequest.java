package cn.com.fero.tlc.spider.http;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.exception.TLCSpiderRequestException;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

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

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            response = httpClient.execute(httpget);
            return EntityUtils.toString(response.getEntity(), TLCSpiderConstants.SPIDER_CONST_CHARACTER_ENCODING);
        } catch (Exception e) {
            throw new TLCSpiderRequestException(e);
        } finally {
            try {
                if (null != httpClient) {
                    httpClient.close();
                }
                if (null != response) {
                    response.close();
                }
            } catch (Exception e) {
                throw new TLCSpiderRequestException(e);
            }
        }
    }

    public static String post(String url, Map<String, String> param) {
        if (StringUtils.isEmpty(url) || MapUtils.isEmpty(param)) {
            throw new IllegalArgumentException();
        }

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> paramList = new ArrayList();
            for (Map.Entry<String, String> entry : param.entrySet()) {
                paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(paramList, CharsetUtils.get(TLCSpiderConstants.SPIDER_CONST_CHARACTER_ENCODING)));

            response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity(), TLCSpiderConstants.SPIDER_CONST_CHARACTER_ENCODING);
        } catch (Exception e) {
            throw new TLCSpiderRequestException(e);
        } finally {
            try {
                if (null != httpClient) {
                    httpClient.close();
                }
                if (null != response) {
                    response.close();
                }
            } catch (Exception e) {
                throw new TLCSpiderRequestException(e);
            }
        }
    }
}
