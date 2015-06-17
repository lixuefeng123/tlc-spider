package cn.com.fero.tlc.spider.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gizmo on 15/6/17.
 */
public class LoggerUtil {
    private static final Logger logger = LoggerFactory.getLogger(LoggerUtil.class);

    private LoggerUtil(){
        throw new UnsupportedOperationException();
    }

    public static Logger getLogger() {
        return logger;
    }
}
