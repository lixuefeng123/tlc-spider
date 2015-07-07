package cn.com.fero.tlc.spider.common;

/**
 * Created by wanghongmeng on 2015/6/24.
 */
public final class TLCSpiderConstants {
    public static final String SPIDER_CONST_CHARACTER_ENCODING = "UTF-8";
    public static final String SPIDER_CONST_CONTENT_TYPE_JSON = "text/json";
    public static final String SPIDER_CONST_JOB_TITLE = "jobTitle";
    public static final String SPIDER_CONST_FORMAT_DATE_TIME = "yyyyMMddHHmmss";
    public static final String SPIDER_CONST_FORMAT_DATA = "yyyyMMdd";
    public static final String SPIDER_CONST_FORMAT_DISPLAY_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String SPIDER_CONST_FULL_PROGRESS = "1";
    public static final String SPIDER_PARAM_STATUS_NAME = "status";
    public static final String SPIDER_PARAM_SID = "sid";
    public static final String SPIDER_PARAM_TOKEN = "token";
    public static final String SPIDER_PARAM_DATA = "data";
    public static final String SPIDER_PARAM_MESSAGE = "message";
    public static final String SPIDER_PARAM_PAGE_INDEX = "pageIndex";
    public static final String SPIDER_PARAM_STATUS_SUCCESS_CODE = "200";
    public static final String SPIDER_PARAM_STATUS_FAIL_CODE = "500";
    public static final String SPIDER_PAGE_SIZE_GET = "8";
    public static final int SPIDER_PAGE_SIZE_SEND = 100;
    public static final String SPIDER_URL_SEND = "http://tailicaiop.fero.com.cn/spiderapi/p2p/post";
    public static final String SPIDER_URL_GET = "http://tailicaiop.fero.com.cn/spiderapi/p2p/updatelist";
    private TLCSpiderConstants() {
        throw new UnsupportedOperationException();
    }
}
