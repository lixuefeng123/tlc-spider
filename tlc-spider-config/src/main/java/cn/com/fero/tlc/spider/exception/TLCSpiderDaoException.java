package cn.com.fero.tlc.spider.exception;

/**
 * Created by wanghongmeng on 2015/5/13.
 */
public final class TLCSpiderDaoException extends RuntimeException {
    public TLCSpiderDaoException() {
    }

    public TLCSpiderDaoException(String message) {
        super(message);
    }

    public TLCSpiderDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public TLCSpiderDaoException(Throwable cause) {
        super(cause);
    }

    public TLCSpiderDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
