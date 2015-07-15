package cn.com.fero.tlc.spider.exception;

/**
 * Created by wanghongmeng on 2015/5/13.
 */
public final class TLCSpiderProxyException extends RuntimeException {
    public TLCSpiderProxyException() {
    }

    public TLCSpiderProxyException(String message) {
        super(message);
    }

    public TLCSpiderProxyException(String message, Throwable cause) {
        super(message, cause);
    }

    public TLCSpiderProxyException(Throwable cause) {
        super(cause);
    }

    public TLCSpiderProxyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
