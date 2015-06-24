package cn.com.fero.tlc.spider.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gizmo on 15/6/19.
 */
public final class SplitUtil {
    private SplitUtil() {
        throw new UnsupportedClassVersionError();
    }

    public static String splitChineseNumber(String str, int index) {
        Pattern pattern = Pattern.compile("([\u4E00-\u9FA5]*)([0-9|a-z|A-Z]*)");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            return matcher.group(index);
        }

        return StringUtils.EMPTY;
    }

    public static String splitNumberChinese(String str, int index) {
        Pattern pattern = Pattern.compile("([0-9|a-z|A-Z]*)([\u4E00-\u9FA5]*)");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            return matcher.group(index);
        }

        return StringUtils.EMPTY;
    }
}
