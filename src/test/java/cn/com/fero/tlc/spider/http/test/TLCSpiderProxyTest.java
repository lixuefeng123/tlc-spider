package cn.com.fero.tlc.spider.http.test;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderProxyUtil;
import cn.com.fero.tlc.spider.vo.p2p.RequestProxy;
import org.junit.Test;

import java.util.HashMap;
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
//            httpPost.setEntity(new UrlEncodedFormEntity(paramList, CharsetUtils.get(TLCSpiderConstants.SPIDER_CONST_ENCODING)));
//
////            httpPost.setConfig(config);
//            CloseableHttpResponse response = httpClient.execute(httpPost);
//            return EntityUtils.toString(response.getEntity(), TLCSpiderConstants.SPIDER_CONST_ENCODING);
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
        String url = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.rdnsyherjz.url.list");

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

    @Test
    public void testGetUpdateArticle() {
        String updateUrl = "http://tailicaiop.fero.com.cn/spiderapi/article/source";
        String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.article.source.sid");
        String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.article.source.token");

        Map<String, String> param = new HashMap();
        param.put(TLCSpiderConstants.SPIDER_PARAM_SID, SID);
        param.put(TLCSpiderConstants.SPIDER_PARAM_TOKEN, TOKEN);

        String response = TLCSpiderRequest.post(updateUrl, param);
        System.out.println(response);
    }

    @Test
    public void testGetArticle() {
        String url = "http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=oIWsFtzcuQtoMO739-mwrqoaWPi4&eqs=pLsqoWtgfIG6osjbygAtCud8xqO5CwnfW%2FMb%2F1qtjEICoi1ZVmfZcmuCOexPe1wuwFIBJ&ekv=7";
        Map<String, String> head = new HashMap();
        head.put("Cookie", "CXID=C2D908DCA2484D12F57C0A1D143A7B66; SUID=97017D7B142D900A55B5C349000477E0; SUV=1507271026258805; ABTEST=0|1438588933|v1; SNUID=C9EE0F0001071D6194556AA302CBA565; ad=Iyllllllll2qHt2JlllllVQ@JAZlllllWT1xOZllll9llllllCxlw@@@@@@@@@@@; IPLOC=CN1100");
        String articleContent = TLCSpiderRequest.getViaProxy(url, TLCSpiderRequest.ProxyType.HTTP, head);
        System.out.println(articleContent);
    }
}
