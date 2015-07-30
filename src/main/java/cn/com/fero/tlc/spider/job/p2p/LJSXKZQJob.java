package cn.com.fero.tlc.spider.job.p2p;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderSplitUtil;
import cn.com.fero.tlc.spider.vo.LJSAE;
import cn.com.fero.tlc.spider.vo.TransObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shaolichao on 2015/7/28.
 */
public class LJSXKZQJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = TLCSpiderPropertiesUtil.getResource("tlc.spider.lzjxkzq.url.list");
    private static final String URL_PRODUCT_DETAIL = TLCSpiderPropertiesUtil.getResource("tlc.spider.lzjxkzq.url.detail");
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.lzjxkzq.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.lzjxkzq.token");
    private static final String JOB_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.lzjxkzq.title");
    private static final String PAGE_NAME = "currentPage";
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
//        String paramStr = convertToParamStr(param);
//        String pageContent = TLCSpiderRequest.getViaProxy(URL_PRODUCT_LIST + paramStr, TLCSpiderRequest.ProxyType.HTTP);
//        String totalPage = TLCSpiderHTMLParser.parseAttribute(pageContent, "//a[@class='btns btn_page btn_small last']", "data-val");
//        return Integer.parseInt(totalPage);
        return Integer.valueOf(TLCSpiderConstants.SPIDER_PARAM_PAGE_ONE);
    }

    @Override
    public List<TransObject> getSpiderDataList(Map<String, String> param) {
        String paramStr = convertToParamStr(param);
        String productContents = TLCSpiderRequest.getViaProxy(URL_PRODUCT_LIST + paramStr, TLCSpiderRequest.ProxyType.HTTP);
        List<TagNode> productLists = TLCSpiderHTMLParser.parseNode(productContents, "//div[@class='main-body']/ul[@class='main-list']/li[@class='more']/a");
        List<TransObject> transObjectList = new ArrayList();
        for (TagNode products : productLists) {
            String link = TLCSpiderHTMLParser.parseAttribute(products, "href");
            if (!link.equals("#") && !link.equals("")) {
                String productContent = TLCSpiderRequest.getViaProxy(link + "?" + paramStr, TLCSpiderRequest.ProxyType.HTTP);
                List<TagNode> productList = TLCSpiderHTMLParser.parseNode(productContent, "//div[@class='main-wide-wrap']//ul[@class='main-list']/li");
                for (TagNode product : productList) {
                    String expand = TLCSpiderHTMLParser.parseText(product, "//p[@class='product-count']");
                    if (StringUtils.isEmpty(expand)) {
                        TransObject transObject = convertToTransObject(product);
                        transObjectList.add(transObject);
                    } else {
                        String href = TLCSpiderHTMLParser.parseAttribute(product, "//dt[@class='product-name']/a", "href");
                        String productId = href.split("=")[1];
                        LJSXKZQSubJob subJob = new LJSXKZQSubJob(productId);
                        transObjectList.addAll(subJob.execute());
                    }
                }
            }
        }
        return transObjectList;
    }

    private TransObject convertToTransObject(TagNode product) {
        TransObject transObject = new TransObject();
        String href = TLCSpiderHTMLParser.parseAttribute(product, "//dt[@class='product-name']/a[1]", "href");
        String financingId = href.split("=")[1];
        transObject.setFinancingId(financingId);

        String projectName = TLCSpiderHTMLParser.parseText(product, "//dt[@class='product-name']/a[1]");
        String projectCode = projectName.split(" ")[1];
        transObject.setProjectName(projectName);
        transObject.setProjectCode(projectCode);

        String projectStatus = TLCSpiderHTMLParser.parseText(product, "//div[@class='product-status product-status-preview']//a[@class='list-btn is-grey']");
        transObject.setProjectStatus(projectStatus);

        String detailLink = URL_PRODUCT_DETAIL + href;
        String detailContent = TLCSpiderRequest.getViaProxy(detailLink, TLCSpiderRequest.ProxyType.HTTP);

        String newInvestor = TLCSpiderHTMLParser.parseAttribute(detailContent, "//i[@class='iconV2 new-user-icon icon-tips']", "class");
        if (StringUtils.isNotEmpty(newInvestor)) {

            String amount = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='main-wrap']//ul[@class='clearfix detail-info-list']//li[1]/p[2]/strong");
            amount = amount.split(" ")[0].replaceAll(",", "").split("\\.")[0];
            transObject.setAmount(amount);

            String investmentInterest = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='main-wrap']//ul[@class='clearfix detail-info-list']//li[2]/p[2]/strong");
            investmentInterest = investmentInterest.replaceAll("%", "");
            transObject.setInvestmentInterest(investmentInterest);

            String progress = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='main-wrap']//div[@class='progress-wrap clearfix']/span[@class='progressTxt']");
            progress = progress.replaceAll("%", "");
            if (!NumberUtils.isNumber(progress) || progress.equals("100")) {
                transObject.setProgress(TLCSpiderConstants.SPIDER_CONST_FULL_PROGRESS);
                transObject.setRealProgress(TLCSpiderConstants.SPIDER_CONST_FULL_PROGRESS);
            } else {
                double progressNum = Double.parseDouble(progress) / 100;
                transObject.setProgress(String.valueOf(progressNum));
                transObject.setRealProgress(String.valueOf(progressNum));
            }

            String duration = TLCSpiderHTMLParser.parseText(detailContent, "//ul[@class='clearfix detail-info-list']/li[3]/p[2]/strong").trim();
            if (duration.contains("月")) {
                duration = TLCSpiderSplitUtil.splitNumberChinese(duration, 1);
                duration = String.valueOf(Integer.parseInt(duration) * 30);
            } else {
                duration = TLCSpiderSplitUtil.splitNumberChinese(duration, 1);
            }
            transObject.setDuration(duration);

            String repayType = TLCSpiderHTMLParser.parseText(detailContent, "//table[@class='product-description']//tr[1]//span[@class='tips-title']");
            if (repayType.contains("一次") && repayType.contains("本") && repayType.contains("息")) {
                repayType = TLCSpiderConstants.REPAY_TYPE.TOTAL.toString();
            } else if (repayType.contains("月") && !repayType.contains("本")) {
                repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_INTEREST.toString();
            } else {
                repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_MONNEY_INTEREST.toString();
            }
            transObject.setRepayType(repayType);

            transObject.setValueBegin(TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='main-wrap']//li[@class='last-col']//strong"));

            String publishTime = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='main-wrap']//p[@class='product-published-date']");
            publishTime = publishTime.split("：")[1];
            transObject.setProjectBeginTime(publishTime);
            transObject.setReadyBeginTime(publishTime);
        }
        return transObject;
    }

    private class LJSXKZQSubJob {
        private final String URL_PRODUCT_SUB_LIST = TLCSpiderPropertiesUtil.getResource("tlc.spider.lzjxkzq.url.sublist");
        private final String PAGE_NAME = "pageNo";
        private String productId;

        public LJSXKZQSubJob(String productId) {
            this.productId = productId;
        }

        public List<TransObject> execute() {
            List<TransObject> transObjectList = new ArrayList();
            Map<String, String> spiderParam = constructSpiderParam();
            int totalPage = getTotalPage(spiderParam);

            for (int a = 1; a <= totalPage; a++) {
                spiderParam.put(PAGE_NAME, String.valueOf(a));
                transObjectList.addAll(getSpiderDataList(spiderParam));
            }

            return transObjectList;
        }

        private Map<String, String> constructSpiderParam() {
            Map<String, String> param = new HashMap();
            param.put(PAGE_NAME, "1");
            param.put("isNewUserPage", "true");
            param.put("_", String.valueOf(System.currentTimeMillis()));
            param.put("productId", productId);
            return param;
        }

        private int getTotalPage(Map<String, String> param) {
            String paramStr = convertToParamStr(param);
            String pageContent = TLCSpiderRequest.getViaProxy(URL_PRODUCT_SUB_LIST + paramStr, TLCSpiderRequest.ProxyType.HTTP);
            String totalPage = TLCSpiderJsonUtil.getString(pageContent, "totalPage");
            return Integer.parseInt(totalPage);
        }

        private List<TransObject> getSpiderDataList(Map<String, String> param) {
            String paramStr = convertToParamStr(param);
            String productContent = TLCSpiderRequest.getViaProxy(URL_PRODUCT_SUB_LIST + paramStr, TLCSpiderRequest.ProxyType.HTTP);
            List<LJSAE> productList = TLCSpiderJsonUtil.json2Array(productContent, "data", LJSAE.class, "investRewardInfo");

            List<TransObject> transObjectList = new ArrayList();
            for (LJSAE product : productList) {
                TransObject transObject = convertToTransObject(product);
                transObjectList.add(transObject);
            }

            return transObjectList;
        }

        private TransObject convertToTransObject(LJSAE product) {
            TransObject transObject = new TransObject();
            transObject.setFinancingId(product.getProductId());
            transObject.setProjectCode(product.getCode());
            transObject.setProjectName(product.getProductNameDisplay() + " " + product.getCode());
            transObject.setAmount(product.getPrice());
            transObject.setPartsCount(String.valueOf(Integer.parseInt(product.getPrice()) / Integer.parseInt(product.getMinInvestAmount())));
            transObject.setInvestmentInterest(String.valueOf(Double.parseDouble(product.getInterestRateDisplay()) * 100));
            if (product.getInvestPeriodDisplay().contains("月")) {
                transObject.setDuration(String.valueOf(Integer.parseInt(product.getInvestPeriod()) * 30));
            } else {
                transObject.setDuration(product.getInvestPeriod());
            }
            if (product.getCollectionMode().equals("1")) {
                transObject.setRepayType("2");
            } else {
                transObject.setRepayType("0");
            }

            transObject.setValueBegin(TLCSpiderConstants.SPIDER_CONST_VALUE_BEGIN);
            transObject.setRepaySourceType(product.getSourceType());
            transObject.setProjectBeginTime(product.getPublishAtCompleteTime());
            transObject.setReadyBeginTime(product.getPublishAtCompleteTime());
            transObject.setProjectStatus(product.getProductStatus());
            transObject.setUpdateTime(product.getUpdateAt());
            transObject.setProjectType(product.getProductType());
            transObject.setRealProgress(product.getProgress());
            transObject.setProgress(product.getProgress());
            System.out.println(transObject);
            return transObject;
        }
    }
}
