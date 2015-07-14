package cn.com.fero.tlc.spider.quartz.job.p2p;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.quartz.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.vo.PFYH;
import cn.com.fero.tlc.spider.vo.TransObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//浦发银行轻松理财抓取
public class PFYHJob extends TLCSpiderJob {
    //detail: https://ebank.spdb.com.cn/fmall/#/P2BBuyInput/15070800000261

    private static final String URL_PRODUCT_LIST = TLCSpiderPropertiesUtil.getResource("tlc.spider.pfyh.url.list");
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.pfyh.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.pfyh.token");
    private static final String JOB_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.pfyh.title");
    private static final String PAGE_NAME = "BeginNumber";
    private static final String PAGE_SIZE = "10";

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
        param.put("QueryNumber", PAGE_SIZE);
        param.put("AmountBegin", "");
        param.put("InterestRateExpect", "");
        param.put("InvestDuration", "");
        param.put("_locale", "zh_CN");
        return param;
    }

    @Override
    public List<TransObject> getSpiderDataList(Map<String, String> param) {
        String productContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        List<PFYH> productList = TLCSpiderJsonUtil.json2Array(productContent, "LoopResult", PFYH.class);

        List<TransObject> transObjectList = new ArrayList();
        for (PFYH product : productList) {
            TransObject transObject = convertToTransObject(product);
            transObjectList.add(transObject);
        }

        return transObjectList;
    }

    private TransObject convertToTransObject(PFYH product) {
        TransObject transObject = new TransObject();
//        transObject.setFinancingId(product.getFinancingId());
//        transObject.setProjectCode(product.getProjectCode());
//        transObject.setProjectName(product.getProjectName());
//        transObject.setBindUserId(product.getBindUserId());
//        transObject.setBindUserName(product.getBindUserName());
//        transObject.setBindCompanyId(product.getBindCompanyId());
//        transObject.setBindCompanyName(product.getBindCompanyName());
//        transObject.setAmount(product.getAmount());
//        transObject.setPartsCount(product.getMinInvestPartsCount());
//        transObject.setBankInterest(product.getBankInterest());
//        transObject.setInvestmentInterest(product.getInvestmentInterest());
//        transObject.setDuration(product.getDuration());
//        transObject.setRepayType(product.getRepayType());
//        transObject.setValueBegin(TLCSpiderDateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getValueBegin()));
//        transObject.setRepayBegin(TLCSpiderDateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getRepayBegin()));
//        transObject.setRepaySourceType(product.getRepaySourceType());
//        transObject.setProjectBeginTime(TLCSpiderDateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getProjectBeginTime()) );
//        transObject.setReadyBeginTime(TLCSpiderDateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getReadyBeginTime()));
//        transObject.setProjectStatus(product.getProjectStatus());
//        transObject.setCreditLevel(product.getCreditLevel());
//        transObject.setCreateUserId(product.getCreateUserId());
//        transObject.setCreateCompanyId(product.getCreateCompanyId());
//        transObject.setJmBeginTime(TLCSpiderDateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getjMBeginTime()));
//        transObject.setAreaCode(product.getAreaCode());
//        transObject.setCreateTime(TLCSpiderDateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getCreateTime()));
//        transObject.setUpdateUserId(product.getUpdateUserId());
//        transObject.setUpdateTime(TLCSpiderDateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getUpdateTime()));
//        transObject.setCreateUserName(product.getCreateUserName());
//        transObject.setCreateCompanyName(product.getCreateCompanyName());
//        transObject.setIsShow(product.getIsShow());
//        transObject.setProjectType(product.getProjectType());
//        transObject.setIsExclusivePublic(product.getIsExclusivePublic());
//        transObject.setMinInvestPartsCount(product.getMinInvestPartsCount());
//        transObject.setExclusiveCode(product.getExclusiveCode());
//        transObject.setLcAmount(product.getLcAmount());
//        transObject.setICount(product.getiCount());
//        transObject.setIAmount(product.getiAmount());
//        transObject.setRealProgress(product.getRealProgress());
//        transObject.setProgress(product.getProgress());
//        transObject.setFinanceApplyStatus(product.getFinanceApplyStatus());
//        transObject.setHotStatus(product.getHotStatus());
//        transObject.setContent(product.getContent());
//        transObject.setTitle(product.getTitle());
//        transObject.setIsLimitCount(product.getIsLimitCount());
        return transObject;
    }
}
