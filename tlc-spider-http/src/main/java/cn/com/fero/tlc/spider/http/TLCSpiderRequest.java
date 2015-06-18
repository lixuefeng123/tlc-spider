package cn.com.fero.tlc.spider.http;

import cn.com.fero.tlc.spider.exception.TLCSpiderRequestException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by gizmo on 15/6/18.
 */
public class TLCSpiderRequest {
     private static final String CHARACTER_ENCODING = "UTF-8";

     public static String get(String url) {
          CloseableHttpClient httpClient = null;
          CloseableHttpResponse response = null;
          try {
               httpClient = HttpClients.createDefault();
               HttpGet httpget = new HttpGet(url);
               response = httpClient.execute(httpget);
               return EntityUtils.toString(response.getEntity(), CHARACTER_ENCODING);
          } catch (Exception e) {
               throw new TLCSpiderRequestException(e);
          } finally {
               try {
                    if(null != httpClient) {
                         httpClient.close();
                    }
                    if(null != response) {
                         response.close();
                    }
               } catch (Exception e) {
                    throw new TLCSpiderRequestException(e);
               }
          }
     }
}
