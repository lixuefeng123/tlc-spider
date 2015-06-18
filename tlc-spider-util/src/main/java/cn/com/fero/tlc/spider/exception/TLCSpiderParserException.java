package cn.com.fero.tlc.spider.exception;

/**
 * Created by wanghongmeng on 2015/5/19.
 */
public class TLCSpiderParserException extends RuntimeException {
    public TLCSpiderParserException() {
    }

    public TLCSpiderParserException(String message) {
        super(message);
    }

    public TLCSpiderParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public TLCSpiderParserException(Throwable cause) {
        super(cause);
    }

    public TLCSpiderParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
