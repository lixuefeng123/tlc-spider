package cn.com.fero.tlc.spider.http.test;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

/**
 * Created by wanghongmeng on 2015/6/25.
 */
public class DateFormatUtilTest {

    @Test
    public void test() {
        System.out.println(DateFormatUtils.format(1436227231000L, TLCSpiderConstants.SPIDER_CONST_FORMAT_DISPLAY_DATE_TIME));
    }
}
