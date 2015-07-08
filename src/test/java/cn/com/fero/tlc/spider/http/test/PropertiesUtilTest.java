package cn.com.fero.tlc.spider.http.test;

import cn.com.fero.tlc.spider.util.PropertiesUtil;
import org.junit.Test;

/**
 * Created by gizmo on 15/6/19.
 */
public class PropertiesUtilTest {

    @Test
    public void testExtract() {
        System.out.println(PropertiesUtil.getResource("tlc.spider.zhxqyej.title"));
    }
}
