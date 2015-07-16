package cn.com.fero.tlc.spider.http.test;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.finance.p2p.CZNSYHJob;
import cn.com.fero.tlc.spider.job.finance.p2p.HRYHJob;
import cn.com.fero.tlc.spider.job.finance.p2p.QDYHCFEWJob;
import org.junit.Test;

/**
 * Created by gizmo on 15/6/19.
 */
public class TLCSpiderProxyTest {

    @Test
    public void testProxy() {
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_SET, "true");
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_HOST, "58.220.2.140");
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_PORT, "80");

        HRYHJob job = new HRYHJob();
        int totalPage = job.getTotalPage(job.constructSpiderParam());
        System.out.println(totalPage);
//        String response = TLCSpiderRequest.get("http://1111.ip138.com/ic.asp", true);
//        System.out.println(response);
    }
}
