package cn.com.fero.tlc.spider.quartz.job.p2p;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.quartz.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderSplitUtil;
import cn.com.fero.tlc.spider.vo.TransObject;
import org.apache.commons.lang3.StringUtils;
import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//陆金所投资频道抓取
public class LJSTZJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = TLCSpiderPropertiesUtil.getResource("tlc.spider.lzjtz.url.list");
    private static final String URL_PRODUCT_DETAIL = TLCSpiderPropertiesUtil.getResource("tlc.spider.lzjtz.url.detail");
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.lzjtz.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.lzjtz.token");
    private static final String JOB_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.lzjtz.title");
    private static final String PAGE_NAME = "currentPage";

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
        param.put("minMoney", "");
        param.put("maxMoney", "");
        param.put("minDays", "");
        param.put("maxDays", "");
        param.put("minRate", "");
        param.put("maxRate", "");
        param.put("mode", "");
        param.put("subType", "");
        param.put("instId", "");
        param.put("trade", "");
        param.put("isCx", "");
        param.put("currentPage", "");
        param.put("orderType", "");
        param.put("orderAsc", "");
        return param;
    }

    @Override
    public int getTotalPage(Map<String, String> param) {
        String paramStr = convertToParamStr(param);
        String pageContent = TLCSpiderRequest.get(URL_PRODUCT_LIST + paramStr);
        String totalPage = TLCSpiderHTMLParser.parseAttribute(pageContent, "//a[@class='btns btn_page btn_small last']", "data-val");
        return Integer.parseInt(totalPage);
    }

    @Override
    public List<TransObject> getSpiderDataList(Map<String, String> param) {
        String paramStr = convertToParamStr(param);
        String productContent = TLCSpiderRequest.get(URL_PRODUCT_LIST + paramStr);
        List<TagNode> productList = TLCSpiderHTMLParser.parseNode(productContent, "//div[@class='main-wide-wrap']//ul[@class='main-list']/li");

        List<TransObject> transObjectList = new ArrayList();
        for (TagNode product : productList) {
            TransObject transObject = convertToTransObject(product);
            transObjectList.add(transObject);
        }

        return transObjectList;
    }

    private TransObject convertToTransObject(TagNode product) {
        TransObject transObject = new TransObject();
        String href = TLCSpiderHTMLParser.parseAttribute(product, "//dl[@class='product-info']//dt[@class='product-name']/a[1]", "href");
        String financingId = href.split("&")[0].split("=")[1];
        transObject.setFinancingId(financingId);

        String projectName = TLCSpiderHTMLParser.parseText(product, "//dl[@class='product-info']//dt[@class='product-name']/a[1]");
        String projectCode = projectName.split(" ")[1];
        transObject.setProjectName(projectName);
        transObject.setProjectCode(projectCode);

        String duration = TLCSpiderHTMLParser.parseText(product, "//ul[@class='clearfix']//li[@class='invest-period']/p").trim();
        if (duration.contains("月")) {
            duration = TLCSpiderSplitUtil.splitNumberChinese(duration, 1);
            duration = String.valueOf(Integer.parseInt(duration) * 30);
        } else {
            duration = TLCSpiderSplitUtil.splitNumberChinese(duration, 1);
        }
        transObject.setDuration(duration);

        String repayType = TLCSpiderHTMLParser.parseText(product, "//ul[@class='clearfix']//li[@class='collection-mode']/p");
        if (repayType.contains("到期") && repayType.contains("本") && repayType.contains("息")) {
            repayType = TLCSpiderConstants.REPAY_TYPE.TOTAL.toString();
        } else if (repayType.contains("月") && !repayType.contains("本")) {
            repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_INTEREST.toString();
        } else {
            repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_MONNEY_INTEREST.toString();
        }
        transObject.setRepayType(repayType);

        String progress = TLCSpiderHTMLParser.parseText(product, "//div[@class='product-status product-status-raise']//span[@class='progress-txt']");
        progress = progress.replaceAll("%", "");
        if (progress.equals("100")) {
            transObject.setProgress(TLCSpiderConstants.SPIDER_CONST_FULL_PROGRESS);
            transObject.setRealProgress(TLCSpiderConstants.SPIDER_CONST_FULL_PROGRESS);
        } else {
            double progressNum = Double.parseDouble(progress) / 100;
            transObject.setProgress(String.valueOf(progressNum));
            transObject.setRealProgress(String.valueOf(progressNum));
        }

        String detailLink = URL_PRODUCT_DETAIL + href;
        String detailContent = TLCSpiderRequest.get(detailLink);


        String amount = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='main-wrap']//ul[@class='clearfix detail-info-list']//li[1]/p[2]/strong");
        amount = amount.split(" ")[0].replaceAll(",", "").split("\\.")[0];
        transObject.setAmount(amount);

        String investmentInterest = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='main-wrap']//ul[@class='clearfix detail-info-list']//li[2]/p[2]/strong");
        investmentInterest = investmentInterest.replaceAll("%", "");
        transObject.setInvestmentInterest(investmentInterest);

        String increaseAmount = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='raise-status-wrap']//div[@class='markUpInfo']//p[@class='mark-up-info']/span[2]");
        if (StringUtils.isNotEmpty(increaseAmount)) {
            increaseAmount = increaseAmount.split("：")[1].split("\\.")[0].replaceAll(",", "");
            int partsCount = Integer.parseInt(amount) / Integer.parseInt(increaseAmount);
            transObject.setPartsCount(String.valueOf(partsCount));
        }

        String publishTime = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='main-wrap']//p[@class='product-published-date']");
        publishTime = publishTime.split("：", 2)[1];
        transObject.setProjectBeginTime(publishTime);
        transObject.setReadyBeginTime(publishTime);
        return transObject;
    }
}
