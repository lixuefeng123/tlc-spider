package cn.com.fero.tlc.spider.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gizmo on 15/6/17.
 */
public final class TLCSpiderLoggerUtil {
    private static final Logger logger = LoggerFactory.getLogger(TLCSpiderLoggerUtil.class);

    private TLCSpiderLoggerUtil() {
        throw new UnsupportedOperationException();
    }

    public static Logger getLogger() {
        return logger;
    }
}
