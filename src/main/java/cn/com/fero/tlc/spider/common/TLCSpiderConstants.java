package cn.com.fero.tlc.spider.common;

import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;

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
    public static final String SPIDER_CONST_COLON = ":";
    public static final String SPIDER_CONST_HTTP_PROXY_SET = "http.proxySet";
    public static final String SPIDER_CONST_HTTP_PROXY_HOST = "http.proxyHost";
    public static final String SPIDER_CONST_HTTP_PROXY_PORT = "http.proxyPort";
    public static final String SPIDER_CONST_HTTPS = "HTTPS";
    public static final String SPIDER_CONST_RESPONSE_STATUS_CODE = "statusCode";
    public static final String SPIDER_CONST_RESPONSE_CONTENT = "content";
    public static final int SPIDER_CONST_RESPONSE_STATUS_SUCCESS = 200;
    public static final int SPIDER_CONST_HTTP_TIMEOUT = 10000;
    public static final int SPIDER_CONST_THREAD_SIZE = 5;
    public static final String SPIDER_PARAM_STATUS_NAME = "status";
    public static final String SPIDER_PARAM_SID = "sid";
    public static final String SPIDER_PARAM_TOKEN = "token";
    public static final String SPIDER_PARAM_DATA = "data";
    public static final String SPIDER_PARAM_MESSAGE = "message";
    public static final String SPIDER_PARAM_PAGE_NAME = "page";
    public static final String SPIDER_PARAM_PAGE_ONE = "1";
    public static final String SPIDER_PARAM_PAGE_ZERO = "0";
    public static final String SPIDER_PARAM_STATUS_SUCCESS_CODE = "200";
    public static final String SPIDER_PARAM_STATUS_FAIL_CODE = "500";
    public static final String SPIDER_PAGE_SIZE_GET = "10";
    public static final int SPIDER_PAGE_SIZE_SEND = 100;
    public static final String SPIDER_URL_SEND = TLCSpiderPropertiesUtil.getResource("tlc.spider.interactive.url.send");
    public static final String SPIDER_URL_GET = TLCSpiderPropertiesUtil.getResource("tlc.spider.interactive.url.get");
    public static volatile boolean SPIDER_CONST_PROXY_STATUS = false;

    private TLCSpiderConstants() {
        throw new UnsupportedOperationException();
    }

    public enum REPAY_TYPE {
        TOTAL("0"), MONTHLY_INTEREST("1"), MONTHLY_MONNEY_INTEREST("2");

        private String value;

        private REPAY_TYPE(String value) {
            this.value = value;
        }

        public String toString() {
            return this.value;
        }
    }
}
