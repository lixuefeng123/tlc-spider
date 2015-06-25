package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.util.JsonUtil;
import cn.com.fero.tlc.spider.util.LoggerUtil;
import cn.com.fero.tlc.spider.vo.CZNSYH;
import cn.com.fero.tlc.spider.vo.TransObject;
import cn.com.fero.tlc.spider.vo.ZHXQYEJ;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//招商银行小企业E家抓取
public class ZHXQYEJJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = "https://efinance.cmbchinaucs.com/Handler/ActionPageV4.aspx";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LoggerUtil.getLogger().info("开始抓取招商银行小企业E家");

        Map<String, String> param = new HashMap();
        param.put("TargetAction", "GetProjectList_Index");
        param.put("PageSize", "10");
        param.put("PageIndex", "4");
        param.put("Sort", "normal");

        String content = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        String dataStr = JsonUtil.getString(content, "DicData");
        List<ZHXQYEJ> zhxqyejList = JsonUtil.getArray(dataStr, "NormalList", ZHXQYEJ.class);
        List<TransObject> transObjectList = new ArrayList();

        for(ZHXQYEJ zhxqyej : zhxqyejList) {
            TransObject transObject = new TransObject();
            //TODO 转换
            transObjectList.add(transObject);
        }

        print(zhxqyejList);
    }
}
