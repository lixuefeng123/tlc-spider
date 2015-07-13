package cn.com.fero.tlc.spider.http.test;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wanghongmeng on 2015/6/25.
 */
public class TLCSpiderDateFormatUtilTest {

    @Test
    public void test() {
        System.out.println(DateFormatUtils.format(1436227231000L, TLCSpiderConstants.SPIDER_CONST_FORMAT_DISPLAY_DATE_TIME));
    }

    @Test
    public void testFormat() throws ParseException {
        String dateStr = "Sat Jul 11 00:00:00 CST 2015";
        Date date = new Date(dateStr);
        SimpleDateFormat sdf = new SimpleDateFormat(TLCSpiderConstants.SPIDER_CONST_FORMAT_DISPLAY_DATE_TIME);
        System.out.println(sdf.format(date));
    }
}
