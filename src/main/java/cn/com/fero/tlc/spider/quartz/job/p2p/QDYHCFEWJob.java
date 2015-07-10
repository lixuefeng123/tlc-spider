package cn.com.fero.tlc.spider.quartz.job.p2p;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.quartz.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.DateFormatUtil;
import cn.com.fero.tlc.spider.util.JsonUtil;
import cn.com.fero.tlc.spider.util.PropertiesUtil;
import cn.com.fero.tlc.spider.vo.QDYHCFEW;
import cn.com.fero.tlc.spider.vo.TransObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//青岛银行财富E屋抓取
public class QDYHCFEWJob extends TLCSpiderJob {
    //detail: https://e.qdccb.com/home/detail?FinancingId=85433605-1ad3-4243-bb4e-836f8310cf62

    private static final String URL_PRODUCT_LIST = PropertiesUtil.getResource("tlc.spider.qdyhcfew.url.list");
    private static final String SID = PropertiesUtil.getResource("tlc.spider.qdyhcfew.sid");
    private static final String TOKEN = PropertiesUtil.getResource("tlc.spider.qdyhcfew.token");
    private static final String JOB_TITLE = PropertiesUtil.getResource("tlc.spider.qdyhcfew.title");
    private static final String PAGE_NAME = "PageIndex";
    private static final String PAGE_SIZE = "6";

    @Override
    public Map<String, String> constructSystemParam() {
        Map<String, String> param = new HashMap();
        param.put(TLCSpiderConstants.SPIDER_PARAM_STATUS_NAME, TLCSpiderConstants.SPIDER_PARAM_STATUS_SUCCESS_CODE);
        param.put(TLCSpiderConstants.SPIDER_PARAM_SID, SID);
        param.put(TLCSpiderConstants.SPIDER_PARAM_TOKEN, TOKEN);
        param.put(TLCSpiderConstants.SPIDER_CONST_JOB_TITLE, JOB_TITLE);
        param.put(TLCSpiderConstants.SPIDER_PARAM_PAGE_NAME, PAGE_NAME);
        return param;
    }

    @Override
    public Map<String, String> constructSpiderParam() {
        Map<String, String> param = new HashMap();
        param.put(PAGE_NAME, TLCSpiderConstants.SPIDER_PARAM_PAGE_ONE);
        param.put("PageSize", PAGE_SIZE);
        param.put("targetAction", "CmbFinancingSearch");
        param.put("Interest", "");
        param.put("Duration", "");
        param.put("ProjectStatus", "");
        param.put("ProjectAmount", "");
        return param;
    }

    @Override
    public int getTotalPage(Map<String, String> param) {
        String countContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        String dataStr = JsonUtil.getString(countContent, "Data");
        String totalCountStr = JsonUtil.getString(dataStr, "TotalCount");
        int pageSize = Integer.parseInt(PAGE_SIZE);
        int totalPage = Integer.parseInt(totalCountStr) % pageSize == 0 ? Integer.parseInt(totalCountStr) / pageSize : (Integer.parseInt(totalCountStr) / pageSize + 1);
        return totalPage;
    }

    @Override
    public List<TransObject> getSpiderDataList(Map<String, String> param) {
        String productContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        String productJsonStr = JsonUtil.getString(productContent, "Data");
        List<QDYHCFEW> productList = JsonUtil.json2Array(productJsonStr, "ResultList", QDYHCFEW.class);

        List<TransObject> transObjectList = new ArrayList();
        for (QDYHCFEW product : productList) {
            TransObject transObject = convertToTransObject(product);
            transObjectList.add(transObject);
        }

        return transObjectList;
    }

    private TransObject convertToTransObject(QDYHCFEW product) {
        TransObject transObject = new TransObject();
        transObject.setFinancingId(product.getFinancingId());
        transObject.setProjectCode(product.getProjectCode());
        transObject.setProjectName(product.getProjectName());
        transObject.setBindUserId(product.getBindUserId());
        transObject.setBindUserName(product.getBindUserName());
        transObject.setBindCompanyId(product.getBindCompanyId());
        transObject.setBindCompanyName(product.getBindCompanyName());
        transObject.setAmount(product.getAmount());
        transObject.setPartsCount(product.getMinInvestPartsCount());
        transObject.setBankInterest(product.getBankInterest());
        transObject.setInvestmentInterest(product.getInvestmentInterest());
        transObject.setDuration(product.getDuration());
        transObject.setRepayType(product.getRepayType());
        transObject.setValueBegin(DateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getValueBegin()));
        transObject.setRepayBegin(DateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getRepayBegin()));
        transObject.setRepaySourceType(product.getRepaySourceType());
        transObject.setProjectBeginTime(DateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getProjectBeginTime()));
        transObject.setReadyBeginTime(DateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getReadyBeginTime()));
        transObject.setProjectStatus(product.getProjectStatus());
        transObject.setCreditLevel(product.getCreditLevel());
        transObject.setCreateUserId(product.getCreateUserId());
        transObject.setCreateCompanyId(product.getCreateCompanyId());
        transObject.setJmBeginTime(DateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getjMBeginTime()));
        transObject.setAreaCode(product.getAreaCode());
        transObject.setCreateTime(DateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getCreateTime()));
        transObject.setUpdateUserId(product.getUpdateUserId());
        transObject.setUpdateTime(DateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getUpdateTime()));
        transObject.setCreateUserName(product.getCreateUserName());
        transObject.setCreateCompanyName(product.getCreateCompanyName());
        transObject.setIsShow(product.getIsShow());
        transObject.setProjectType(product.getProjectType());
        transObject.setIsExclusivePublic(product.getIsExclusivePublic());
        transObject.setMinInvestPartsCount(product.getMinInvestPartsCount());
        transObject.setExclusiveCode(product.getExclusiveCode());
        transObject.setLcAmount(product.getlCAmount());
        transObject.setICount(product.getiCount());
        transObject.setIAmount(product.getiAmount());
        transObject.setRealProgress(product.getRealProgress());
        transObject.setProgress(product.getProgress());
        transObject.setFinanceApplyStatus(product.getFinanceApplyStatus());
        transObject.setHotStatus(product.getHotStatus());
        transObject.setContent(product.getcONTENT());
        transObject.setTitle(product.getTitle());
        //TODO 未处理属性 AgreementType: 2 F_Financing_InitId: "B5BFDBCB-AEA6-4437-A48B-C5DA2E3BACAA" IsVIP: false
        //               RemainPartsCount: 0 SettlementType: 0 ToInvestmentTime: "12/16/2015 00:00:00" YMInterest: 0.2
        return transObject;
    }
}
