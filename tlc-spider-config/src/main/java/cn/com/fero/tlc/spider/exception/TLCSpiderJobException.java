package cn.com.fero.tlc.spider.exception;

/**
 * Created by wanghongmeng on 2015/5/13.
 */
public final class TLCSpiderJobException extends RuntimeException {
    public TLCSpiderJobException() {
    }

    public TLCSpiderJobException(String message) {
        super(message);
    }

    public TLCSpiderJobException(String message, Throwable cause) {
        super(message, cause);
    }

    public TLCSpiderJobException(Throwable cause) {
        super(cause);
    }

    public TLCSpiderJobException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
