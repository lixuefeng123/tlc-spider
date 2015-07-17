package cn.com.fero.tlc.spider.http.test;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import org.junit.Test;

/**
 * Created by gizmo on 15/6/19.
 */
public class TLCSpiderProxyTest {

    @Test
    public void testProxy() {
        TLCSpiderConstants.SPIDER_CONST_PROXY_STATUS = true;
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_SET, "true");
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_HOST, "218.92.227.172");
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_PORT, "34034");
//        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_HOST, "58.220.2.140");
//        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_PORT, "80");

//        QDYHCFEWJob job = new QDYHCFEWJob();
//        int totalPage = job.getTotalPage(job.constructSpiderParam());
//        System.out.println(totalPage);
        String response = TLCSpiderRequest.get("http://1111.ip138.com/ic.asp", true);
        System.out.println(response);
    }
}
