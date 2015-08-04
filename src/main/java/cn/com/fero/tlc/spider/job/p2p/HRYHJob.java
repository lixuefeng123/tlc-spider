package cn.com.fero.tlc.spider.job.p2p;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderDateFormatUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.vo.p2p.HRYH;
import cn.com.fero.tlc.spider.vo.p2p.TransObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//华润银行资产交易平台抓取
public class HRYHJob extends TLCSpiderJob {
    //detail: https://e.qdccb.com/home/detail?FinancingId=85433605-1ad3-4243-bb4e-836f8310cf62

    private static final String URL_PRODUCT_LIST = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.hryh.url.list");
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.hryh.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.hryh.token");
    private static final String JOB_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.hryh.title");
    private static final String PAGE_NAME = "PageIndex";
    private static final String PAGE_SIZE = "10";

    @Override
    public Map<String, String> constructSystemInteractiveParam() {
        Map<String, String> param = new HashMap();
        param.put(TLCSpiderConstants.SPIDER_PARAM_STATUS_NAME, TLCSpiderConstants.SPIDER_PARAM_STATUS_SUCCESS_CODE);
        param.put(TLCSpiderConstants.SPIDER_PARAM_SID, SID);
        param.put(TLCSpiderConstants.SPIDER_PARAM_TOKEN, TOKEN);
        param.put(TLCSpiderConstants.SPIDER_CONST_JOB_TITLE, JOB_TITLE);
        param.put(TLCSpiderConstants.SPIDER_PARAM_PAGE_NAME, PAGE_NAME);
        return param;
    }

    @Override
    public Map<String, String> constructSpiderFetchParam() {
        Map<String, String> param = new HashMap();
        param.put(PAGE_NAME, TLCSpiderConstants.SPIDER_PARAM_PAGE_ONE);
        param.put("PageSize", PAGE_SIZE);
        param.put("targetAction", "CmbFinancingSearch");
        param.put("Interest", "");
        param.put("Duration", "");
        param.put("ProjectStatus", "");
        return param;
    }

    @Override
    public int getTotalPage(Map<String, String> param) {
        String countContent = TLCSpiderRequest.postViaProxy(URL_PRODUCT_LIST, param, TLCSpiderRequest.ProxyType.HTTP);
        String dataStr = TLCSpiderJsonUtil.getString(countContent, "Data");
        String totalCountStr = TLCSpiderJsonUtil.getString(dataStr, "TotalCount");
        int pageSize = Integer.parseInt(PAGE_SIZE);
        int totalPage = Integer.parseInt(totalCountStr) % pageSize == 0 ? Integer.parseInt(totalCountStr) / pageSize : (Integer.parseInt(totalCountStr) / pageSize + 1);
        return totalPage;
    }

    @Override
    public List<TransObject> getSpiderDataList(Map<String, String> param) {
        String productContent = TLCSpiderRequest.postViaProxy(URL_PRODUCT_LIST, param, TLCSpiderRequest.ProxyType.HTTP);
        String productJsonStr = TLCSpiderJsonUtil.getString(productContent, "Data");
        List<HRYH> productList = TLCSpiderJsonUtil.json2Array(productJsonStr, "ResultList", HRYH.class);

        List<TransObject> transObjectList = new ArrayList();
        for (HRYH product : productList) {
            TransObject transObject = convertToTransObject(product);
            transObjectList.add(transObject);
        }

        return transObjectList;
    }

    private TransObject convertToTransObject(HRYH product) {
        TransObject transObject = new TransObject();
        transObject.setFinancingId(product.getFinancingId());
        transObject.setProjectCode(product.getProjectCode());
        transObject.setProjectName(product.getProjectName());
        transObject.setBindUserId(product.getBindUserId());
        transObject.setBindUserName(product.getBindUserName());
        transObject.setBindCompanyId(product.getBindCompanyId());
        transObject.setBindCompanyName(product.getBindCompanyName());
        transObject.setAmount(product.getAmount());
        transObject.setPartsCount(product.getPartsCount());
        transObject.setBankInterest(product.getBankInterest());
        transObject.setInvestmentInterest(product.getInvestmentInterest());
        transObject.setDuration(product.getDuration());
        transObject.setRepayType(product.getRepayType());
        transObject.setValueBegin(TLCSpiderDateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getValueBegin()));
        transObject.setRepayBegin(TLCSpiderDateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getRepayBegin()));
        transObject.setRepaySourceType(product.getRepaySourceType());
        transObject.setProjectBeginTime(TLCSpiderDateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getProjectBeginTime()));
        transObject.setReadyBeginTime(TLCSpiderDateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getReadyBeginTime()));
        transObject.setProjectStatus(product.getProjectStatus());
        transObject.setCreditLevel(product.getCreditLevel());
        transObject.setCreateUserId(product.getCreateUserId());
        transObject.setCreateCompanyId(product.getCreateCompanyId());
        transObject.setJmBeginTime(TLCSpiderDateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getjMBeginTime()));
        transObject.setAreaCode(product.getAreaCode());
        transObject.setCreateTime(TLCSpiderDateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getCreateTime()));
        transObject.setUpdateUserId(product.getUpdateUserId());
        transObject.setUpdateTime(TLCSpiderDateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getUpdateTime()));
        transObject.setCreateUserName(product.getCreateUserName());
        transObject.setCreateCompanyName(product.getCreateCompanyName());
        transObject.setIsShow(product.getIsShow());
        transObject.setProjectType(product.getProjectType());
        transObject.setIsExclusivePublic(product.getIsExclusivePublic());
        transObject.setMinInvestPartsCount(product.getMinInvestPartsCount());
        transObject.setExclusiveCode(product.getExclusiveCode());
        transObject.setLcAmount(product.getlCAmount());
        transObject.setiCount(product.getiCount());
        transObject.setiAmount(product.getiAmount());
        transObject.setRealProgress(product.getRealProgress());
        transObject.setProgress(product.getProgress());
        transObject.setFinanceApplyStatus(product.getFinanceApplyStatus());
        transObject.setHotStatus(product.getHotStatus());
        transObject.setContent(product.getContent());
        transObject.setTitle(product.getTitle());
        transObject.setIsLimitCount(product.getIsLimitCount());
        return transObject;
    }
}
