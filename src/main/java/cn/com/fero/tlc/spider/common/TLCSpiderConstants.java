package cn.com.fero.tlc.spider.common;

/**
 * Created by wanghongmeng on 2015/6/24.
 */
public final class TLCSpiderConstants {
    private TLCSpiderConstants() {
        throw new UnsupportedOperationException();
    }

    public static final String ENCODING = "UTF-8";
    public static final String CONTENT_TYPE_JSON = "text/json";
    public static final String PAGE_SIZE = "8";
    public static final String NYYHNYE_DATE_TIME_FORMAT = "yyyyMMddHHmmss";
    public static final String NYYHNYE_DATE_FORMAT = "yyyyMMdd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String PARAM_JOB_TITLE = "jobTitle";
    public static final String PARAM_STATUS_NAME = "status";
    public static final String PARAM_STATUS_SUCCESS_CODE = "200";
    public static final String PARAM_STATUS_FAIL_CODE = "500";
    public static final String PARAM_SID = "sid";
    public static final String PARAM_TOKEN = "token";
    public static final String PARAM_DATA = "data";
    public static final String PARAM_MESSAGE = "message";
    public static final int POST_DATA_SIZE = 100;
    public static final String TLC_SPIDER_SEND_URL = "http://tailicaiop.fero.com.cn/spiderapi/p2p/post";
    public static final String TLC_SPIDER_GET_URL = "http://tailicaiop.fero.com.cn/spiderapi/p2p/updatelist";
}
