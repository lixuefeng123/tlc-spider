package cn.com.fero.tlc.spider.job.system.proxy;

import java.util.List;

/**
 * Created by wanghongmeng on 2015/7/15.
 */
public abstract class TLCSpiderIpFetcher {
    protected abstract List<String> doFetch();

}
