package cn.com.fero.tlc.spider.job.p2p;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderDateFormatUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.vo.p2p.NYYHNYE;
import cn.com.fero.tlc.spider.vo.p2p.TransObject;
import org.apache.commons.lang3.StringUtils;

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

    private static final String URL_PRODUCT_LIST = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.nyyhnye.url.list");
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.nyyhnye.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.nyyhnye.token");
    private static final String JOB_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.nyyhnye.title");
    private static final String PAGE_NAME = "page";
    private static final String PAGE_SIZE = "8";

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
        param.put(PAGE_NAME, TLCSpiderConstants.SPIDER_PARAM_PAGE_ZERO);
        param.put("pagesize", PAGE_SIZE);
        param.put("timeLimitType", "0");
        return param;
    }

    @Override
    public int getTotalPage(Map<String, String> param) {
        String countContent = TLCSpiderRequest.postViaProxy(URL_PRODUCT_LIST, param, TLCSpiderRequest.ProxyType.HTTPS);
        String count = TLCSpiderJsonUtil.getString(countContent, "count");
        int countNum = Integer.parseInt(count);
        int pageNum = Integer.parseInt(PAGE_SIZE);
        return countNum % pageNum == 0 ? countNum / pageNum : (countNum / pageNum + 1);
    }

    @Override
    public List<TransObject> getSpiderDataList(Map<String, String> param) {
        String productContent = TLCSpiderRequest.postViaProxy(URL_PRODUCT_LIST, param, TLCSpiderRequest.ProxyType.HTTPS);
        List<NYYHNYE> productList = TLCSpiderJsonUtil.json2Array(productContent, "projList", NYYHNYE.class);

        List<TransObject> transObjectList = new ArrayList();
        for (NYYHNYE product : productList) {
            TransObject transObject = convertToTransObject(product);
            transObjectList.add(transObject);
        }

        return transObjectList;
    }

    private TransObject convertToTransObject(NYYHNYE product) {
        TransObject transObject = new TransObject();
        transObject.setFinancingId(product.getProjCode());
        transObject.setProjectCode(product.getProjCode());
        transObject.setProjectName(product.getProjName());
        transObject.setAmount(product.getProductSize());
        transObject.setDuration(product.getLimitType());
        transObject.setInvestmentInterest(String.format("%.2f", Double.parseDouble(product.getYieldRate()) * 100));
        if (product.getBuyPercent().equals(TLCSpiderConstants.SPIDER_CONST_FULL_PROGRESS)) {
            transObject.setProgress(product.getBuyPercent());
            transObject.setRealProgress(product.getBuyPercent());
        } else {
            transObject.setProgress("0" + product.getBuyPercent());
            transObject.setRealProgress("0" + product.getBuyPercent());
        }

        int amountNum = Integer.parseInt(product.getProductSize());
        int minInvestUnitNum = Integer.parseInt(product.getSingleSum());
        int partsCount;
        if (amountNum % minInvestUnitNum == 0) {
            partsCount = amountNum / minInvestUnitNum;
        } else {
            partsCount = amountNum / minInvestUnitNum + 1;
        }
        transObject.setPartsCount(String.valueOf(partsCount));
        if (StringUtils.isNotEmpty(product.getPubStaDate()) && StringUtils.isNotEmpty(product.getPubStaTime())) {
            transObject.setProjectBeginTime(TLCSpiderDateFormatUtil.formatDateTime(TLCSpiderConstants.SPIDER_CONST_FORMAT_DATE_TIME, product.getPubStaDate(), product.getPubStaTime()));
        } else if (StringUtils.isNotEmpty(product.getPubStaDate())) {
            transObject.setProjectBeginTime(TLCSpiderDateFormatUtil.formatDateTime(TLCSpiderConstants.SPIDER_CONST_FORMAT_DATE, product.getPubStaDate()));
        }
        if (StringUtils.isNotEmpty(product.getSellStaDate()) && StringUtils.isNotEmpty(product.getSellStaTime())) {
            transObject.setValueBegin(TLCSpiderDateFormatUtil.formatDateTime(TLCSpiderConstants.SPIDER_CONST_FORMAT_DATE_TIME, product.getSellStaDate(), product.getSellStaTime()));
        } else if (StringUtils.isNotEmpty(product.getPubStaDate())) {
            transObject.setValueBegin(TLCSpiderDateFormatUtil.formatDateTime(TLCSpiderConstants.SPIDER_CONST_FORMAT_DATE, product.getSellStaDate()));
        }
        if (StringUtils.isNotEmpty(product.getSellEndDate()) && StringUtils.isNotEmpty(product.getSellEndTime())) {
            transObject.setRepayBegin(TLCSpiderDateFormatUtil.formatDateTime(TLCSpiderConstants.SPIDER_CONST_FORMAT_DATE_TIME, product.getSellEndDate(), product.getSellEndTime()));
        } else if (StringUtils.isNotEmpty(product.getSellEndDate())) {
            transObject.setRepayBegin(TLCSpiderDateFormatUtil.formatDateTime(TLCSpiderConstants.SPIDER_CONST_FORMAT_DATE, product.getSellEndDate()));
        }
        if (product.getInvestFlag().equals("1")) {
            transObject.setRepayType(TLCSpiderConstants.REPAY_TYPE.TOTAL.toString());
        } else if (product.getInvestFlag().equals("2")) {
            transObject.setRepayType(TLCSpiderConstants.REPAY_TYPE.MONTHLY_INTEREST.toString());
        } else {
            transObject.setRepayType(TLCSpiderConstants.REPAY_TYPE.MONTHLY_MONNEY_INTEREST.toString());
        }
        if (product.getBuyFlag().equals("0")) {
            transObject.setProjectStatus("创建");
        } else if (product.getBuyFlag().equals("01")) {
            transObject.setProjectStatus("发布处理中");
        } else if (product.getBuyFlag().equals("02")) {
            transObject.setProjectStatus("发布审批失败");
        } else if (product.getBuyFlag().equals("03")) {
            transObject.setProjectStatus("已发布");
        } else if (product.getBuyFlag().equals("1")) {
            transObject.setProjectStatus("募集中");
        } else if (product.getBuyFlag().equals("2")) {
            transObject.setProjectStatus("募集完成");
        } else if (product.getBuyFlag().equals("21")) {
            transObject.setProjectStatus("募集完成处理中");
        } else if (product.getBuyFlag().equals("22")) {
            transObject.setProjectStatus("募集审批成功");
        } else if (product.getBuyFlag().equals("3")) {
            transObject.setProjectStatus("项目不成立");
        } else if (product.getBuyFlag().equals("31")) {
            transObject.setProjectStatus("募集失败处理中");
        } else if (product.getBuyFlag().equals("32")) {
            transObject.setProjectStatus("募集审批失败");
        } else if (product.getBuyFlag().equals("33")) {
            transObject.setProjectStatus("已终止");
        } else if (product.getBuyFlag().equals("4")) {
            transObject.setProjectStatus("已划款");
        } else if (product.getBuyFlag().equals("40")) {
            transObject.setProjectStatus("划款提交");
        } else if (product.getBuyFlag().equals("41")) {
            transObject.setProjectStatus("划款处理中");
        } else if (product.getBuyFlag().equals("42")) {
            transObject.setProjectStatus("划款审批失败");
        } else if (product.getBuyFlag().equals("43")) {
            transObject.setProjectStatus("划款审批成功");
        } else if (product.getBuyFlag().equals("5")) {
            transObject.setProjectStatus("已成立");
        } else if (product.getBuyFlag().equals("51") && product.getInvestFlag().equals("1")) {
            transObject.setProjectStatus("到期待确认");
        } else if (product.getBuyFlag().equals("51") && product.getInvestFlag().equals("2")) {
            transObject.setProjectStatus("已成立");
        } else if (product.getBuyFlag().equals("6")) {
            transObject.setProjectStatus("已到期");
        } else if (product.getBuyFlag().equals("7")) {
            transObject.setProjectStatus("已清算");
        } else if (product.getBuyFlag().equals("71")) {
            transObject.setProjectStatus("清算审批中");
        } else if (product.getBuyFlag().equals("72")) {
            transObject.setProjectStatus("清算审批失败");
        } else if (product.getBuyFlag().equals("73")) {
            transObject.setProjectStatus("清算审批成功");
        } else if (product.getBuyFlag().equals("74")) {
            transObject.setProjectStatus("清算处理中");
        }
        return transObject;
    }
}
