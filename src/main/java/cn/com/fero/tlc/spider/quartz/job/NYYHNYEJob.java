package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.quartz.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.DateFormatUtil;
import cn.com.fero.tlc.spider.util.JsonUtil;
import cn.com.fero.tlc.spider.util.LoggerUtil;
import cn.com.fero.tlc.spider.util.PropertiesUtil;
import cn.com.fero.tlc.spider.vo.NYYHNYE;
import cn.com.fero.tlc.spider.vo.RDNSYHERJZ;
import cn.com.fero.tlc.spider.vo.TransObject;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//广东南粤银行南粤E+抓取
public class NYYHNYEJob extends TLCSpiderJob {
    //detail: https://one.gdnybank.com/pages/er_product_detail.html

    private static final String URL_PRODUCT_LIST = PropertiesUtil.getResource("tlc.spider.nyyhnye.url.list");
    private static final String SID = PropertiesUtil.getResource("tlc.spider.nyyhnye.sid");
    private static final String TOKEN = PropertiesUtil.getResource("tlc.spider.nyyhnye.token");
    private static final String JOB_TITLE = PropertiesUtil.getResource("tlc.spider.nyyhnye.title");

    @Override
    public Map<String, String> constructSystemParam() {
        Map<String, String> param = new HashMap();
        param.put(TLCSpiderConstants.SPIDER_PARAM_STATUS_NAME, TLCSpiderConstants.SPIDER_PARAM_STATUS_SUCCESS_CODE);
        param.put(TLCSpiderConstants.SPIDER_PARAM_SID, SID);
        param.put(TLCSpiderConstants.SPIDER_PARAM_TOKEN, TOKEN);
        param.put(TLCSpiderConstants.SPIDER_CONST_JOB_TITLE, JOB_TITLE);
        param.put(TLCSpiderConstants.SPIDER_PARAM_PAGE_NAME, "page");
        return param;
    }

    @Override
    public Map<String, String> constructSpiderParam() {
        Map<String, String> param = new HashMap();
        param.put("timeLimitType", "0");
        param.put("page", "0");
        param.put("pagesize", TLCSpiderConstants.SPIDER_PAGE_SIZE_GET);
        return param;
    }

    @Override
    public int getTotalPage(Map<String, String> param) {
        String countContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        String totalCount = JsonUtil.getString(countContent, "count");
        return Integer.parseInt(totalCount);
    }

    @Override
    public List<TransObject> getSpiderDataList(Map<String, String> param) {
        String productContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        List<NYYHNYE> productList = JsonUtil.json2Array(productContent, "projList", NYYHNYE.class);

        List<TransObject> transObjectList = new ArrayList();
        for(NYYHNYE product : productList) {
            TransObject transObject = convertToTransObject(product);
            transObjectList.add(transObject);
        }

        return transObjectList;
    }

    private TransObject convertToTransObject(NYYHNYE product) {
        TransObject transObject = new TransObject();
        transObject.setFinancingId(product.getProdCode());
        transObject.setProjectCode(product.getProjCode());
        transObject.setProjectName(product.getProjName());
        transObject.setAmount(product.getProductSize());
        transObject.setDuration(product.getLimitType());
        transObject.setInvestmentInterest(Double.parseDouble(product.getYieldRate()) * 100 + "%");
        transObject.setRealProgress(Double.parseDouble(product.getBuyPercent()) * 100 + "%");
        transObject.setProgress(Double.parseDouble(product.getBuyPercent()) * 100 + "%");
        if (StringUtils.isNotEmpty(product.getPubStaDate()) && StringUtils.isNotEmpty(product.getPubStaTime())) {
            transObject.setProjectBeginTime(DateFormatUtil.formatDateTime(TLCSpiderConstants.SPIDER_CONST_FORMAT_DATE_TIME, product.getPubStaDate(), product.getPubStaTime()));
        } else if (StringUtils.isNotEmpty(product.getPubStaDate())) {
            transObject.setProjectBeginTime(DateFormatUtil.formatDateTime(TLCSpiderConstants.SPIDER_CONST_FORMAT_DATA, product.getPubStaDate()));
        }
        if (StringUtils.isNotEmpty(product.getSellStaDate()) && StringUtils.isNotEmpty(product.getSellStaTime())) {
            transObject.setValueBegin(DateFormatUtil.formatDateTime(TLCSpiderConstants.SPIDER_CONST_FORMAT_DATE_TIME, product.getSellStaDate(), product.getSellStaTime()));
        } else if (StringUtils.isNotEmpty(product.getPubStaDate())) {
            transObject.setValueBegin(DateFormatUtil.formatDateTime(TLCSpiderConstants.SPIDER_CONST_FORMAT_DATA, product.getSellStaDate()));
        }
        if (StringUtils.isNotEmpty(product.getSellEndDate()) && StringUtils.isNotEmpty(product.getSellEndTime())) {
            transObject.setRepayBegin(DateFormatUtil.formatDateTime(TLCSpiderConstants.SPIDER_CONST_FORMAT_DATE_TIME, product.getSellEndDate(), product.getSellEndTime()));
        } else if (StringUtils.isNotEmpty(product.getSellEndDate())) {
            transObject.setRepayBegin(DateFormatUtil.formatDateTime(TLCSpiderConstants.SPIDER_CONST_FORMAT_DATA, product.getSellEndDate()));
        }

        //TODO 转换
//            financeValue.setPartsCount();
//            financeValue.setBankInterest();
//            financeValue.setRepayType();
//            financeValue.setRepaySourceType();
//            financeValue.setReadyBeginTime();
//            financeValue.setProjectStatus();
//            financeValue.setCreditLevel();
//            financeValue.setCreateUserId();
//            financeValue.setCreateCompanyId();
//            financeValue.setJmBeginTime();
//            financeValue.setAreaCode();
//            financeValue.setCreateTime();
//            financeValue.setUpdateUserId();
//            financeValue.setUpdateTime();
//            financeValue.setCreateUserName();
//            financeValue.setCreateCompanyName();
//            financeValue.setIsShow();
//            financeValue.setProjectType();
//            financeValue.setIsExclusivePublic();
//            financeValue.setMinInvestPartsCount();
//            financeValue.setExclusiveCode();
//            financeValue.setLcAmount();
//            financeValue.setICount();
//            financeValue.setIAmount();
//            financeValue.setFinanceApplyStatus();
//            financeValue.setHotStatus();
//            financeValue.setDbType();
//            financeValue.setTitle();
//            financeValue.setContent();
//            financeValue.setIsLimitCount();
        return transObject;
    }
}
