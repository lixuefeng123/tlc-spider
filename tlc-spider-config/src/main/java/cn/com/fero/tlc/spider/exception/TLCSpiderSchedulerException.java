package cn.com.fero.tlc.spider.exception;

/**
 * Created by wanghongmeng on 2015/5/13.
 */
public final class TLCSpiderSchedulerException extends RuntimeException {
    public TLCSpiderSchedulerException() {
    }

    public TLCSpiderSchedulerException(String message) {
        super(message);
    }

    public TLCSpiderSchedulerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TLCSpiderSchedulerException(Throwable cause) {
        super(cause);
    }

    public TLCSpiderSchedulerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
