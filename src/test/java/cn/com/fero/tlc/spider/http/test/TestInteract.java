package cn.com.fero.tlc.spider.http.test;

import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghongmeng on 2015/6/29.
 */
public class TestInteract {
    @Test
    public void testGetUpdateList() {
        Map<String, String> map = new HashMap();
        map.put("sid", "1");
        map.put("token", "m88l48Mguil4+F9ilbodCID5MFnHl30cQmzVJ7FeAmOV");
        Object obj = TLCSpiderRequest.post("http://192.168.2.19:3005/spiderapi/p2p/updatelist", map);
        System.out.println(obj);
    }
}
