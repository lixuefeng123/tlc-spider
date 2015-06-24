package cn.com.fero.tlc.spider.exception;

/**
 * Created by wanghongmeng on 2015/5/13.
 */
public final class TLCSpiderRequestException extends RuntimeException {
    public TLCSpiderRequestException() {
    }

    public TLCSpiderRequestException(String message) {
        super(message);
    }

    public TLCSpiderRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public TLCSpiderRequestException(Throwable cause) {
        super(cause);
    }

    public TLCSpiderRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
