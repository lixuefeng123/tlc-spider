package cn.com.fero.tlc.spider.util.test;

import cn.com.fero.tlc.spider.util.LoggerUtil;
import org.junit.Test;

/**
 * Created by gizmo on 15/6/17.
 */
public class LoggerUtilTest {

    @Test
    public void testLoggerUtil() {
        LoggerUtil.getLogger().info("test");
    }

}
