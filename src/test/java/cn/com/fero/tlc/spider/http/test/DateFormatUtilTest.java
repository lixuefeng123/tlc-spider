package cn.com.fero.tlc.spider.http.test;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.util.DateFormatUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

/**
 * Created by wanghongmeng on 2015/6/25.
 */
public class DateFormatUtilTest {

    @Test
    public void test() {
        System.out.println(DateFormatUtils.format(1435276800000L, TLCSpiderConstants.DATE_TIME_FORMAT));
    }
}
