package cn.com.fero.tlc.spider.quartz.job.p2p;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.quartz.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.DateFormatUtil;
import cn.com.fero.tlc.spider.util.JsonUtil;
import cn.com.fero.tlc.spider.util.LoggerUtil;
import cn.com.fero.tlc.spider.util.PropertiesUtil;
import cn.com.fero.tlc.spider.vo.CZNSYH;
import cn.com.fero.tlc.spider.vo.TransObject;
import cn.com.fero.tlc.spider.vo.ZHXQYEJ;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//长子农商银行长青融E贷抓取
public class CZNSYHJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = PropertiesUtil.getResource("tlc.spider.cznsyh.url.list");
    private static final String URL_PRODUCT_DETAIL = PropertiesUtil.getResource("tlc.spider.cznsyh.url.detail");
    private static final String SID = PropertiesUtil.getResource("tlc.spider.cznsyh.sid");
    private static final String TOKEN = PropertiesUtil.getResource("tlc.spider.cznsyh.token");
    private static final String JOB_TITLE = PropertiesUtil.getResource("tlc.spider.cznsyh.title");
    private static final String PAGE_NAME = "index";
    private static final String PAGE_SIZE = "5";

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
        param.put("size", PAGE_SIZE);
        param.put("orderMap[presaleDateEnd]", "DESC");
        param.put("_", String.valueOf(System.currentTimeMillis()));
        return param;
    }

    @Override
    public int getTotalPage(Map<String, String> param) {
        String paramStr = convertToParamStr(param);
        String pageContent = TLCSpiderRequest.get(URL_PRODUCT_LIST + paramStr);
        String dataStr = JsonUtil.getString(pageContent, "data");
        String paginationStr = JsonUtil.getString(dataStr, "pagination");
        String totalCount = JsonUtil.getString(paginationStr, "count");
        int pageSize = Integer.parseInt(PAGE_SIZE);
        int totalCountNum = Integer.parseInt(totalCount) % pageSize == 0 ? Integer.parseInt(totalCount) / pageSize : (Integer.parseInt(totalCount) / pageSize + 1);
        return totalCountNum;
    }

    @Override
    public List<TransObject> getSpiderDataList(Map<String, String> param) {
        String paramStr = convertToParamStr(param);
        String productContent = TLCSpiderRequest.get(URL_PRODUCT_LIST + paramStr);
        String productJsonStr = JsonUtil.getString(productContent, "data");
        List<CZNSYH> productList = JsonUtil.json2Array(productJsonStr, "data", CZNSYH.class);

        List<TransObject> transObjectList = new ArrayList();
        for(CZNSYH product : productList) {
            TransObject transObject = convertToTransObject(product);
            transObjectList.add(transObject);
        }

        return transObjectList;
    }

    private TransObject convertToTransObject(CZNSYH product) {
        TransObject transObject = new TransObject();
        transObject.setFinancingId(product.getId());
        transObject.setProjectCode(product.getId());
        transObject.setProjectName(product.getName() + product.getNumber());
        transObject.setBindUserId(product.getBorrowerId());
        transObject.setAmount(product.getAmount() + "0000");
        transObject.setPartsCount(product.getSellLimit());
        transObject.setInvestmentInterest(product.getYieldRate());
        transObject.setDuration(product.getLoanPeriod());
        if(product.getIncreaseTypeId().equals("1")) {
            transObject.setRepayType(TLCSpiderConstants.REPAY_TYPE.TOTAL.toString());
        } else if(product.getIncreaseTypeId().equals("2")) {
            transObject.setRepayType(TLCSpiderConstants.REPAY_TYPE.MONTHLY_MONNEY_INTEREST.toString());
        } else {
            transObject.setRepayType(TLCSpiderConstants.REPAY_TYPE.MONTHLY_INTEREST.toString());
        }
        transObject.setValueBegin(DateFormatUtils.format(Long.valueOf(product.getLoanDate()), TLCSpiderConstants.SPIDER_CONST_FORMAT_DISPLAY_DATE_TIME));

        String detailLink = URL_PRODUCT_DETAIL + product.getId();
        String detailContent = TLCSpiderRequest.get(detailLink);
        String repayBegin = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='wrap']//ul[@class='cons']/li[@class='item border-b-1'][3]/span[@class='w300'][2]");
        repayBegin = repayBegin.split("：")[1].trim();
        transObject.setRepayBegin(repayBegin);
        transObject.setProjectBeginTime(DateFormatUtils.format(Long.valueOf(product.getPresaleDateStart()), TLCSpiderConstants.SPIDER_CONST_FORMAT_DISPLAY_DATE_TIME));
        transObject.setReadyBeginTime(DateFormatUtils.format(Long.valueOf(product.getPresaleDateStart()), TLCSpiderConstants.SPIDER_CONST_FORMAT_DISPLAY_DATE_TIME));

        if(product.getPercent().equals("100") && product.getStatus().equals("2") && !product.getRealStatus().equals("22")) {
            transObject.setProjectStatus("满标");
        } else if(product.getRealStatus().equals("2")) {
            transObject.setProjectStatus("投标");
        } else if(product.getRealStatus().equals("1")) {
            transObject.setProjectStatus("预热");
        }else  {
            transObject.setProjectStatus("已结束");
        }
        transObject.setCreateTime(DateFormatUtils.format(Long.valueOf(product.getCreateTime()), TLCSpiderConstants.SPIDER_CONST_FORMAT_DISPLAY_DATE_TIME));
        transObject.setUpdateTime(product.getUpdateTime());
        if(product.getPercent().equals("100")) {
            transObject.setProgress("1");
            transObject.setRealProgress("1");
        } else {
            transObject.setProgress(String.format("%.2f", Double.parseDouble(product.getPercent()) / 100));
            transObject.setRealProgress(String.format("%.2f", Double.parseDouble(product.getPercent()) / 100));
        }
        return transObject;
    }

    private String convertToParamStr(Map<String, String> param) {
        StringBuilder paramBuilder = new StringBuilder();
        paramBuilder.append("?");
        for(Map.Entry<String, String> entry : param.entrySet()) {
            paramBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return paramBuilder.substring(0, paramBuilder.length() - 1);
    }
}
