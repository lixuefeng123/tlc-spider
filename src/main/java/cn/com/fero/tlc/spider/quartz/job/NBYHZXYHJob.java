package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.quartz.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.JsonUtil;
import cn.com.fero.tlc.spider.util.LoggerUtil;
import cn.com.fero.tlc.spider.vo.NBYHZXYH;
import cn.com.fero.tlc.spider.vo.TransObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//宁波银行投融资平台抓取
public class NBYHZXYHJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = "https://u.zxyh.nbcb.com.cn/Ajax/CmbFinancingSearch";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LoggerUtil.getLogger().info("开始抓取宁波银行投融资平台");
        List<TransObject> transObjectList = new ArrayList();
        Integer pageSize = 10;

        Map<String, String> param = new HashMap();
        param.put("targetAction", "CmbFinancingSearch");
        param.put("PageIndex", "1");
        param.put("PageSize", pageSize.toString());
        param.put("Interest", "");
        param.put("Duration", "");
        param.put("ProjectStatus", "");
        param.put("ProjectAmount", "");

        String countContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        String countStr = JsonUtil.getString(countContent, "Data");
        String totalCount = JsonUtil.getString(countStr, "TotalCount");
        int totalCountNum = Integer.parseInt(totalCount) % pageSize == 0 ? Integer.parseInt(totalCount) / pageSize : (Integer.parseInt(totalCount) / pageSize + 1);

        for (Integer a = 1; a <= totalCountNum; a++) {
            param.put("PageIndex", a.toString());
            String productContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
            String dataStr = JsonUtil.getString(productContent, "Data");
            List<NBYHZXYH> nbyhzxyhList = JsonUtil.json2Array(dataStr, "ResultList", NBYHZXYH.class);
            for (NBYHZXYH nbyhzxyh : nbyhzxyhList) {
                TransObject transObject = new TransObject();
                transObject.setFinancingId(nbyhzxyh.getFinancingId());
                transObject.setProjectCode(nbyhzxyh.getProjectCode());
                transObject.setProjectName(nbyhzxyh.getProjectName());
                transObject.setAmount(nbyhzxyh.getAmount());
                transObject.setPartsCount(nbyhzxyh.getMinInvestPartsCount());
                transObject.setInvestmentInterest(nbyhzxyh.getInvestmentInterest());
                transObject.setDuration(nbyhzxyh.getDuration());
                transObject.setValueBegin(nbyhzxyh.getValueBegin());
                transObject.setProjectBeginTime(nbyhzxyh.getProjectBeginTime());
                transObject.setReadyBeginTime(nbyhzxyh.getReadyBeginTime());
                transObject.setProjectStatus(nbyhzxyh.getProjectStatus());
                transObject.setJmBeginTime(nbyhzxyh.getjMBeginTime());
                transObject.setCreateTime(nbyhzxyh.getCreateTime());
                transObject.setProjectType(nbyhzxyh.getProjectType());
                transObject.setIsExclusivePublic(nbyhzxyh.getIsExclusivePublic());
                transObject.setMinInvestPartsCount(nbyhzxyh.getMinInvestPartsCount());
                transObject.setProgress(nbyhzxyh.getProgress());
                transObject.setFinanceApplyStatus(nbyhzxyh.getFinanceApplyStatus());
                transObject.setHotStatus(nbyhzxyh.getHotStatus());
                //TODO 未处理属性 ProgressForOrder: 0.99 RemainPartsCount: 3 UnrealJMBeginTime: "/Date(1435736774353)/"
                transObjectList.add(transObject);
            }
        }

        print(transObjectList);
    }

    @Override
    protected Map<String, String> constructPostParam(List<TransObject> transObjectList) {
        return null;
    }

    @Override
    public void doExecute(Map<String, TransObject> updateMap) throws Exception {

    }
}
