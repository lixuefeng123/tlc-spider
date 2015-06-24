package cn.com.fero.tlc.spider.exception;

/**
 * Created by wanghongmeng on 2015/5/13.
 */
public class TLCSpiderUtilException extends RuntimeException {
    public TLCSpiderUtilException() {
    }

    public TLCSpiderUtilException(String message) {
        super(message);
    }

    public TLCSpiderUtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public TLCSpiderUtilException(Throwable cause) {
        super(cause);
    }

    public TLCSpiderUtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
