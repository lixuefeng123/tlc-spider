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
        List<TransObject> transObjectList = new ArrayList();

        //TODO 抓取页数， 抓取所有
        Map<String, String> param = new HashMap();
        param.put("TargetAction", "GetProjectList_Index");
//        param.put("PageSize", "10");
        param.put("PageIndex", "1");
        param.put("Sort", "normal");


        String pageContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        String pageStr = JsonUtil.getString(pageContent, "DicData");
//        String totalCount = JsonUtil.getString(pageStr, "TotalCount");
        String totalPage = JsonUtil.getString(pageStr, "TotalPage");
        int totalPageNum = Integer.parseInt(totalPage);
        for(int a = 1; a <= totalPageNum; a++) {
            param.put("PageIndex", String.valueOf(a));
            String listContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
            String listStr = JsonUtil.getString(listContent, "DicData");
            List<ZHXQYEJ> zhxqyejList = JsonUtil.getArray(listStr, "NormalList", ZHXQYEJ.class);
            for(ZHXQYEJ zhxqyej : zhxqyejList) {
                TransObject transObject = new TransObject();
                transObject.setFinancingId(zhxqyej.getFinancingId());
                transObject.setProjectCode(zhxqyej.getProjectCode());
                transObject.setProjectName(zhxqyej.getProjectName());
                transObject.setBindUserId(zhxqyej.getBindUserId());
                transObject.setBindUserName(zhxqyej.getBindUserName());
                transObject.setBindCompanyId(zhxqyej.getBindCompanyId());
                transObject.setBindCompanyName(zhxqyej.getBindCompanyName());
                transObject.setAmount(zhxqyej.getAmount());
                transObject.setPartsCount(zhxqyej.getMinInvestPartsCount());
                transObject.setBankInterest(zhxqyej.getBankInterest());
                transObject.setInvestmentInterest(zhxqyej.getInvestmentInterest());
                transObject.setDuration(zhxqyej.getDuration());
                transObject.setRepayType(zhxqyej.getRepayType());
                transObject.setValueBegin(zhxqyej.getValueBegin());
                transObject.setRepayBegin(zhxqyej.getRepayBegin());
                transObject.setRepaySourceType(zhxqyej.getRepaySourceType());
                transObject.setProjectBeginTime(zhxqyej.getProjectBeginTime());
                transObject.setReadyBeginTime(zhxqyej.getReadyBeginTime());
                transObject.setProjectStatus(zhxqyej.getProjectStatus());
                transObject.setCreditLevel(zhxqyej.getCreditLevel());
                transObject.setCreateUserId(zhxqyej.getCreateUserId());
                transObject.setCreateCompanyId(zhxqyej.getCreateCompanyId());
                transObject.setJmBeginTime(zhxqyej.getjMBeginTime());
                transObject.setAreaCode(zhxqyej.getAreaCode());
                transObject.setCreateTime(zhxqyej.getCreateTime());
                transObject.setUpdateUserId(zhxqyej.getUpdateUserId());
                transObject.setUpdateTime(zhxqyej.getUpdateTime());
                transObject.setCreateUserName(zhxqyej.getCreateUserName());
                transObject.setCreateCompanyName(zhxqyej.getCreateCompanyName());
                transObject.setIsShow(zhxqyej.getIsShow());
                transObject.setProjectType(zhxqyej.getProjectType());
                transObject.setIsExclusivePublic(zhxqyej.getIsExclusivePublic());
                transObject.setMinInvestPartsCount(zhxqyej.getMinInvestPartsCount());
                transObject.setExclusiveCode(zhxqyej.getExclusiveCode());
                transObject.setLcAmount(zhxqyej.getlCAmount());
                transObject.setICount(zhxqyej.getiCount());
                transObject.setIAmount(zhxqyej.getiAmount());
                transObject.setRealProgress(zhxqyej.getRealProgress());
                transObject.setProgress(zhxqyej.getProgress());
                transObject.setFinanceApplyStatus(zhxqyej.getFinanceApplyStatus());
                transObject.setHotStatus(zhxqyej.getHotStatus());
                transObject.setDbType(zhxqyej.getDbType());
                transObjectList.add(transObject);
            }
        }


        print(transObjectList);
    }
}
