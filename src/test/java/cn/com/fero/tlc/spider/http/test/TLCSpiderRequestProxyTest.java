package cn.com.fero.tlc.spider.http.test;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderProxyUtil;
import cn.com.fero.tlc.spider.vo.RequestProxy;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gizmo on 15/6/19.
 */
public class TLCSpiderRequestProxyTest {

    @Test
    public void testProxy() {
        String response = TLCSpiderRequest.getViaProxy("http://1111.ip138.com/ic.asp", TLCSpiderRequest.ProxyType.HTTP);
        System.out.println(response);
    }

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
        String countStr = TLCSpiderJsonUtil.getString(countContent, "Data");
        String totalCount = TLCSpiderJsonUtil.getString(countStr, "TotalCount");
        System.out.println(totalCount);
    }
}
