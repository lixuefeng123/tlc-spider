package cn.com.fero.tlc.spider.quartz;

import cn.com.fero.tlc.spider.vo.TransObject;

import java.util.Map;

/**
 * Created by wanghongmeng on 2015/7/6.
 */
public interface TLCSpiderExecutor {
    public void doExecute(Map<String, TransObject> updateMap) throws Exception;
}
