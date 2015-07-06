package cn.com.fero.tlc.spider.util;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.exception.TLCSpiderUtilException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wanghongmeng on 2015/6/24.
 */
public final class DateFormatUtil {
    private DateFormatUtil() {
        throw new UnsupportedOperationException();
    }

    public static String formatDateTime(String format, String... args) {
        try {
            if(StringUtils.isEmpty(format) || ArrayUtils.isEmpty(args)) {
                throw new IllegalArgumentException();
            }

            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern(format);

            StringBuilder str = new StringBuilder();
            for(String arg : args) {
                str.append(arg);
            }

            Date date = sdf.parse(str.toString());
            return DateFormatUtils.format(date, TLCSpiderConstants.SPIDER_DEFAULT_FORMAT_DISPLAY_DATE_TIME);
        } catch (Exception e) {
            throw new TLCSpiderUtilException(e);
        }
    }
}
