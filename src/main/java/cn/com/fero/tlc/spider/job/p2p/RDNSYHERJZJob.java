package cn.com.fero.tlc.spider.job.p2p;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderDateFormatUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.vo.p2p.RDNSYHERJZ;
import cn.com.fero.tlc.spider.vo.p2p.TransObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//尧都农商银行E融九州抓取
public class RDNSYHERJZJob extends TLCSpiderJob {
    //detail: https://e.ydnsh.com/home/detail?FinancingId=15b43001-555c-4dda-845c-31b62879fbe7

    private static final String URL_PRODUCT_LIST = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.rdnsyherjz.url.list");
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.rdnsyherjz.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.rdnsyherjz.token");
    private static final String JOB_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.rdnsyherjz.title");
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
        param.put("ProjectAmount", "");
        return param;
    }

    @Override
    public int getTotalPage(Map<String, String> param) {
        String countContent = TLCSpiderRequest.postViaProxy(URL_PRODUCT_LIST, param, TLCSpiderRequest.ProxyType.HTTPS);
        String countStr = TLCSpiderJsonUtil.getString(countContent, "Data");
        String totalCount = TLCSpiderJsonUtil.getString(countStr, "TotalCount");
        int pageSize = Integer.parseInt(PAGE_SIZE);
        int totalPage = Integer.parseInt(totalCount) % pageSize == 0 ? Integer.parseInt(totalCount) / pageSize : (Integer.parseInt(totalCount) / pageSize + 1);
        return totalPage;
    }

    @Override
    public List<TransObject> getSpiderDataList(Map<String, String> param) {
        String productContent = TLCSpiderRequest.postViaProxy(URL_PRODUCT_LIST, param, TLCSpiderRequest.ProxyType.HTTPS);
        String productJsonStr = TLCSpiderJsonUtil.getString(productContent, "Data");
        List<RDNSYHERJZ> productList = TLCSpiderJsonUtil.json2Array(productJsonStr, "ResultList", RDNSYHERJZ.class, "YMInterest");

        List<TransObject> transObjectList = new ArrayList();
        for (RDNSYHERJZ product : productList) {
            TransObject transObject = convertToTransObject(product);
            transObjectList.add(transObject);
        }

        return transObjectList;
    }

    private TransObject convertToTransObject(RDNSYHERJZ product) {
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
        transObject.setProjectBeginTime(TLCSpiderDateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getProjectBeginTime()));
        transObject.setReadyBeginTime(TLCSpiderDateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getReadyBeginTime()));
        transObject.setProjectStatus(product.getProjectStatus());
        transObject.setJmBeginTime(TLCSpiderDateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getjMBeginTime()));
        transObject.setCreateTime(TLCSpiderDateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getCreateTime()));
        transObject.setIsShow(product.getIsShow());
        transObject.setProjectType(product.getProjectType());
        transObject.setIsExclusivePublic(product.getIsExclusivePublic());
        transObject.setMinInvestPartsCount(product.getMinInvestPartsCount());
        transObject.setExclusiveCode(product.getExclusiveCode());
        transObject.setRealProgress(product.getRealProgress());
        transObject.setProgress(product.getProgress());
        transObject.setFinanceApplyStatus(product.getFinanceApplyStatus());
        transObject.setHotStatus(product.getHotStatus());
        //TODO 未处理属性 YMInterest: 0.5
        return transObject;
    }
}
