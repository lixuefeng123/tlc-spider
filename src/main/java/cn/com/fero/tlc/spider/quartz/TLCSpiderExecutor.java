package cn.com.fero.tlc.spider.quartz;

import cn.com.fero.tlc.spider.vo.TransObject;
import com.sun.media.sound.InvalidDataException;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghongmeng on 2015/7/6.
 */
public interface TLCSpiderExecutor {
    public abstract int getTotalPage() throws Exception;

    public abstract Map<String, String> constructSystemParam();

    public abstract Map<String, String> constructSpiderParam();

    public Map<String, TransObject> getUpdateMap() throws InvalidDataException;

    public List<TransObject> getDataList();

}
