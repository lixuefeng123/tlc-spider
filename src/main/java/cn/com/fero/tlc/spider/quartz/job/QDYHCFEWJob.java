package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.quartz.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.JsonUtil;
import cn.com.fero.tlc.spider.util.LoggerUtil;
import cn.com.fero.tlc.spider.vo.QDYHCFEW;
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
//青岛银行财富E屋抓取
public class QDYHCFEWJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = "https://e.qdccb.com/Ajax/ProcessRequest";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LoggerUtil.getLogger().info("开始抓取青岛银行财富E屋");
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
            List<QDYHCFEW> qdyhcfewList = JsonUtil.json2Array(dataStr, "ResultList", QDYHCFEW.class);
            for (QDYHCFEW qdyhcfew : qdyhcfewList) {
                TransObject transObject = new TransObject();
                transObject.setFinancingId(qdyhcfew.getFinancingId());
                transObject.setProjectCode(qdyhcfew.getProjectCode());
                transObject.setProjectName(qdyhcfew.getProjectName());
                transObject.setBindUserId(qdyhcfew.getBindUserId());
                transObject.setBindUserName(qdyhcfew.getBindUserName());
                transObject.setBindCompanyId(qdyhcfew.getBindCompanyId());
                transObject.setBindCompanyName(qdyhcfew.getBindCompanyName());
                transObject.setAmount(qdyhcfew.getAmount());
                transObject.setPartsCount(qdyhcfew.getMinInvestPartsCount());
                transObject.setBankInterest(qdyhcfew.getBankInterest());
                transObject.setInvestmentInterest(qdyhcfew.getInvestmentInterest());
                transObject.setDuration(qdyhcfew.getDuration());
                transObject.setRepayType(qdyhcfew.getRepayType());
                transObject.setValueBegin(qdyhcfew.getValueBegin());
                transObject.setRepayBegin(qdyhcfew.getRepayBegin());
                transObject.setRepaySourceType(qdyhcfew.getRepaySourceType());
                transObject.setProjectBeginTime(qdyhcfew.getProjectBeginTime());
                transObject.setReadyBeginTime(qdyhcfew.getReadyBeginTime());
                transObject.setProjectStatus(qdyhcfew.getProjectStatus());
                transObject.setCreditLevel(qdyhcfew.getCreditLevel());
                transObject.setCreateUserId(qdyhcfew.getCreateUserId());
                transObject.setCreateCompanyId(qdyhcfew.getCreateCompanyId());
                transObject.setJmBeginTime(qdyhcfew.getjMBeginTime());
                transObject.setAreaCode(qdyhcfew.getAreaCode());
                transObject.setCreateTime(qdyhcfew.getCreateTime());
                transObject.setUpdateUserId(qdyhcfew.getUpdateUserId());
                transObject.setUpdateTime(qdyhcfew.getUpdateTime());
                transObject.setCreateUserName(qdyhcfew.getCreateUserName());
                transObject.setCreateCompanyName(qdyhcfew.getCreateCompanyName());
                transObject.setIsShow(qdyhcfew.getIsShow());
                transObject.setProjectType(qdyhcfew.getProjectType());
                transObject.setIsExclusivePublic(qdyhcfew.getIsExclusivePublic());
                transObject.setMinInvestPartsCount(qdyhcfew.getMinInvestPartsCount());
                transObject.setExclusiveCode(qdyhcfew.getExclusiveCode());
                transObject.setLcAmount(qdyhcfew.getlCAmount());
                transObject.setICount(qdyhcfew.getiCount());
                transObject.setIAmount(qdyhcfew.getiAmount());
                transObject.setRealProgress(qdyhcfew.getRealProgress());
                transObject.setProgress(qdyhcfew.getProgress());
                transObject.setFinanceApplyStatus(qdyhcfew.getFinanceApplyStatus());
                transObject.setHotStatus(qdyhcfew.getHotStatus());
                //TODO 未处理属性 AgreementType: 2 F_Financing_InitId: "B5BFDBCB-AEA6-4437-A48B-C5DA2E3BACAA" IsVIP: false
                //               RemainPartsCount: 0 SettlementType: 0 ToInvestmentTime: "12/16/2015 00:00:00" YMInterest: 0.2
                transObjectList.add(transObject);
            }
        }

        print(transObjectList);
    }

    @Override
    protected Map<String, String> constructPostParam() {
        return null;
    }

    @Override
    public void doExecute() {

    }
}
