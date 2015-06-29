package cn.com.fero.tlc.spider.http.test;

import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import org.junit.Test;

/**
 * Created by wanghongmeng on 2015/6/29.
 */
public class TestInteract {
    @Test
    public void testGetUpdateList() {
        Object obj = TLCSpiderRequest.get("http://tailicaiop.fero.com.cn/spiderapi/p2p/updatelist");
        System.out.println(obj);
    }
}
