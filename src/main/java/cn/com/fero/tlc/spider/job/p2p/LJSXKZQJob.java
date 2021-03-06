package cn.com.fero.tlc.spider.job.p2p;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderSplitUtil;
import cn.com.fero.tlc.spider.vo.p2p.LJSAE;
import cn.com.fero.tlc.spider.vo.p2p.TransObject;
import org.apache.commons.lang.StringUtils;
import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shaolichao on 2015/7/28.
 */
//陆金所新客专区抓取
public class LJSXKZQJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.ljsxkzq.url.list");
    private static final String URL_PRODUCT_DETAIL = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.ljs.url.detail");
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.ljsxkzq.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.ljsxkzq.token");
    private static final String JOB_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.ljsxkzq.title");
    private static final String PAGE_NAME = "currentPage";
    private static final String PAGE_SIZE = "5";
    private static final String TAG_NAME = "新客";

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
        String id = href.split("=")[1];

        String detailLink = URL_PRODUCT_DETAIL + id + "/productDetail?_=" + System.currentTimeMillis();
        String productContent = TLCSpiderRequest.getViaProxy(detailLink, TLCSpiderRequest.ProxyType.HTTP);
        LJSAE ljsae = (LJSAE) TLCSpiderJsonUtil.json2Object(productContent, LJSAE.class, "investRewardInfo");

        transObject.setFinancingId(ljsae.getProductId());
        transObject.setProjectName(ljsae.getProductId());
        transObject.setProjectCode(ljsae.getCode());
        transObject.setProjectName(ljsae.getDisplayName() + " " + ljsae.getCode());
        transObject.setValueBegin(ljsae.getPublishedAtDateTime());
        transObject.setCreateTime(ljsae.getPublishedAtDateTime());
        transObject.setInvestmentInterest(String.valueOf(Double.parseDouble(ljsae.getInterestRateDisplay()) * 100));
        transObject.setAmount(ljsae.getRaisedAmount());

        String duration = ljsae.getInvestPeriodDisplay();
        if (duration.contains("月")) {
            duration = TLCSpiderSplitUtil.splitNumberChinese(duration, 1);
            duration = String.valueOf(Integer.parseInt(duration) * 30);
        } else {
            duration = TLCSpiderSplitUtil.splitNumberChinese(duration, 1);
        }
        transObject.setDuration(duration);

        String repayType = ljsae.getCollectionModeDisplay();
        if (repayType.contains("一次") && repayType.contains("本") && repayType.contains("息")) {
            repayType = TLCSpiderConstants.REPAY_TYPE.TOTAL.toString();
        } else if (repayType.contains("月") && !repayType.contains("本")) {
            repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_INTEREST.toString();
        } else {
            repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_MONNEY_INTEREST.toString();
        }
        transObject.setRepayType(repayType);

        String progress = ljsae.getProgress();
        if (!org.apache.commons.lang.math.NumberUtils.isNumber(progress) || Float.valueOf(progress) == 1.0) {
            progress = TLCSpiderConstants.SPIDER_PARAM_PAGE_ONE;
        } else {
            progress = String.format("%.2f", Double.parseDouble(progress));
        }
        transObject.setRealProgress(progress);
        transObject.setProgress(progress);

        if (ljsae.getMinInvestAmount() != null) {
            int priceNum = Integer.parseInt(ljsae.getPrice());
            int amountNum = Integer.parseInt(ljsae.getMinInvestAmount());

            int partsCount = priceNum / amountNum;
            if ((priceNum % amountNum) == 0) {
                transObject.setPartsCount(String.valueOf(partsCount));
            } else {
                transObject.setPartsCount(String.valueOf(partsCount + 1));
            }
        }

        return transObject;
    }

    private class LJSXKZQSubJob {
        private final String URL_PRODUCT_SUB_LIST = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.ljsxkzq.url.sublist");
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
            transObject.setInvestmentInterest(String.valueOf(Double.parseDouble(product.getInterestRateDisplay()) * 100));
            transObject.setValueBegin(TLCSpiderConstants.SPIDER_CONST_VALUE_BEGIN);
            transObject.setRepaySourceType(product.getSourceType());
            transObject.setProjectBeginTime(product.getPublishAtCompleteTime());
            transObject.setReadyBeginTime(product.getPublishAtCompleteTime());
            transObject.setProjectStatus(product.getProductStatus());
            transObject.setUpdateTime(product.getUpdateAt());
            transObject.setProjectType(product.getProductType());
            transObject.setRealProgress(product.getProgress());
            transObject.setProgress(product.getProgress());
            transObject.setTag(TAG_NAME);
            transObject.setAmount(product.getPrice());

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

            if (product.getMinInvestAmount() != null) {
                int priceNum = Integer.parseInt(product.getPrice());
                int amountNum = Integer.parseInt(product.getMinInvestAmount());

                int partsCount = priceNum / amountNum;
                if ((priceNum % amountNum) == 0) {
                    transObject.setPartsCount(String.valueOf(partsCount));
                } else {
                    transObject.setPartsCount(String.valueOf(partsCount + 1));
                }
            }

            return transObject;
        }
    }
}
