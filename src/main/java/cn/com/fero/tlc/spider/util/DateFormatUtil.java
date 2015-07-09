package cn.com.fero.tlc.spider.util;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.exception.TLCSpiderUtilException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.ParseException;
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
            if (StringUtils.isEmpty(format)) {
                return StringUtils.EMPTY;
            }

            for(String arg : args) {
                if (StringUtils.isEmpty(arg)) {
                    return StringUtils.EMPTY;
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern(format);

            StringBuilder str = new StringBuilder();
            for (String arg : args) {
                str.append(arg);
            }

            Date date = sdf.parse(str.toString());
            return DateFormatUtils.format(date, TLCSpiderConstants.SPIDER_CONST_FORMAT_DISPLAY_DATE_TIME);
        } catch (Exception e) {
            throw new TLCSpiderUtilException(e);
        }
    }


    public static String formatDate(String dateStr, String format) {
        try {
            Date date = new Date(dateStr);
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            throw new TLCSpiderUtilException(e);
        }
    }
}
