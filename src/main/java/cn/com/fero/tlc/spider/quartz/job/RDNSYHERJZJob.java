package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.quartz.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.JsonUtil;
import cn.com.fero.tlc.spider.util.LoggerUtil;
import cn.com.fero.tlc.spider.vo.RDNSYHERJZ;
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
//尧都农商银行E融九州抓取
public class RDNSYHERJZJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = "https://e.ydnsh.com/Ajax/ProcessRequest";

    public void execute(JobExecutionContext context) throws JobExecutionException {
        LoggerUtil.getLogger().info("开始抓取尧都农商银行E融九州");
        List<TransObject> transObjectList = new ArrayList();
        Integer pageSize = 10;

        Map<String, String> param = new HashMap();
        param.put("targetAction", "CmbFinancingSearch");
        param.put("PageIndex", "1");
        param.put("PageSize", "1000");
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
            List<RDNSYHERJZ> rdnsyherjzList = JsonUtil.json2Array(countStr, "ResultList", RDNSYHERJZ.class, "YMInterest");
            for (RDNSYHERJZ rdnsyherjz : rdnsyherjzList) {
                TransObject transObject = new TransObject();
                transObject.setFinancingId(rdnsyherjz.getFinancingId());
                transObject.setProjectCode(rdnsyherjz.getProjectCode());
                transObject.setProjectName(rdnsyherjz.getProjectName());
                transObject.setBindUserId(rdnsyherjz.getBindUserId());
                transObject.setBindUserName(rdnsyherjz.getBindUserName());
                transObject.setBindCompanyId(rdnsyherjz.getBindCompanyId());
                transObject.setBindCompanyName(rdnsyherjz.getBindCompanyName());
                transObject.setAmount(rdnsyherjz.getAmount());
                transObject.setPartsCount(rdnsyherjz.getMinInvestPartsCount());
                transObject.setBankInterest(rdnsyherjz.getBankInterest());
                transObject.setInvestmentInterest(rdnsyherjz.getInvestmentInterest());
                transObject.setDuration(rdnsyherjz.getDuration());
                transObject.setRepayType(rdnsyherjz.getRepayType());
                transObject.setValueBegin(rdnsyherjz.getValueBegin());
                transObject.setRepayBegin(rdnsyherjz.getRepayBegin());
                transObject.setProjectBeginTime(rdnsyherjz.getProjectBeginTime());
                transObject.setReadyBeginTime(rdnsyherjz.getReadyBeginTime());
                transObject.setProjectStatus(rdnsyherjz.getProjectStatus());
                transObject.setJmBeginTime(rdnsyherjz.getjMBeginTime());
                transObject.setCreateTime(rdnsyherjz.getCreateTime());
                transObject.setIsShow(rdnsyherjz.getIsShow());
                transObject.setProjectType(rdnsyherjz.getProjectType());
                transObject.setIsExclusivePublic(rdnsyherjz.getIsExclusivePublic());
                transObject.setMinInvestPartsCount(rdnsyherjz.getMinInvestPartsCount());
                transObject.setExclusiveCode(rdnsyherjz.getExclusiveCode());
                transObject.setRealProgress(rdnsyherjz.getRealProgress());
                transObject.setProgress(rdnsyherjz.getProgress());
                transObject.setFinanceApplyStatus(rdnsyherjz.getFinanceApplyStatus());
                transObject.setHotStatus(rdnsyherjz.getHotStatus());
                //TODO 未处理属性 YMInterest: 0.5
                transObjectList.add(transObject);
            }
        }

        print(transObjectList);
    }
}
