package cn.com.fero.tlc.spider.quartz;

import cn.com.fero.tlc.spider.vo.TransObject;
import com.sun.media.sound.InvalidDataException;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghongmeng on 2015/7/6.
 */
public interface TLCSpiderJobExecutor {
    public abstract Map<String, String> constructSystemParam();

    public abstract Map<String, String> constructSpiderParam();

    public abstract int getTotalPage(Map<String, String> param) throws Exception;

    public Map<String, TransObject> getUpdateDataMap(Map<String, String> param) throws InvalidDataException;

    public List<TransObject> getSpiderDataList(Map<String, String> param);
}