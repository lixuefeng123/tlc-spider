package cn.com.fero.tlc.spider.exception;

/**
 * Created by wanghongmeng on 2015/5/19.
 */
public class TLCSpiderServiceException extends RuntimeException {
    public TLCSpiderServiceException() {
    }

    public TLCSpiderServiceException(String message) {
        super(message);
    }

    public TLCSpiderServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TLCSpiderServiceException(Throwable cause) {
        super(cause);
    }

    public TLCSpiderServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
