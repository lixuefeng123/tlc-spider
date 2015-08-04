package cn.com.fero.tlc.spider.job;

import cn.com.fero.tlc.spider.vo.p2p.TransObject;

import java.util.List;
import java.util.Map;

/**
 * Created by wanghongmeng on 2015/7/6.
 */
public interface TLCSpiderP2PExecutor {
    public abstract Map<String, String> constructSystemInteractiveParam();

    public abstract Map<String, String> constructSpiderFetchParam();

    public abstract int getTotalPage(Map<String, String> param);

    public Map<String, TransObject> getUpdateDataMap(Map<String, String> param);

    public List<TransObject> getSpiderDataList(Map<String, String> param);
}
