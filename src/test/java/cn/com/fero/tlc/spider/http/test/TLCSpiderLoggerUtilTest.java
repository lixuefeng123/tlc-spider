package cn.com.fero.tlc.spider.http.test;

import cn.com.fero.tlc.spider.util.TLCSpiderLoggerUtil;
import org.junit.Test;

/**
 * Created by gizmo on 15/6/17.
 */
public class TLCSpiderLoggerUtilTest {

    @Test
    public void testLoggerUtil() {
        TLCSpiderLoggerUtil.getLogger().info("test");
    }

}
