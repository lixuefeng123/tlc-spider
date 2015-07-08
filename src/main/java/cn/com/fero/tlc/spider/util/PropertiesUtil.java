package cn.com.fero.tlc.spider.util;

import org.apache.commons.lang.StringUtils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by wanghongmeng on 2015/7/8.
 */
public final class PropertiesUtil {
    private static final ResourceBundle resource;

    static {
        resource = ResourceBundle.getBundle("tlc-spider", Locale.CHINESE);
    }

    public static String getResource(String key) {
        if (StringUtils.isEmpty(key)) {
            return StringUtils.EMPTY;
        }

        return resource.getString(key);
    }
}
