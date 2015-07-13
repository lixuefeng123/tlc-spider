package cn.com.fero.tlc.spider.http.test;

import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import org.junit.Test;

/**
 * Created by gizmo on 15/6/19.
 */
public class TLCSpiderPropertiesUtilTest {

    @Test
    public void testExtract() {
        System.out.println(TLCSpiderPropertiesUtil.getResource("tlc.spider.zhxqyej.title"));
    }
}
