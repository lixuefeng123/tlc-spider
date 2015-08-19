package cn.com.fero.tlc.spider.job.p2p;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderSplitUtil;
import cn.com.fero.tlc.spider.vo.p2p.LJSBXT;
import cn.com.fero.tlc.spider.vo.p2p.TransObject;
import org.apache.commons.lang.math.NumberUtils;
import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shaolichao on 2015/7/20.
 */
//陆金所投资频道稳盈-变现通抓取
public class LJSBXTJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.ljsbxt.url.list");
    private static final String URL_PRODUCT_DETAIL = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.ljs.url.detail");
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.ljsbxt.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.ljsbxt.token");
    private static final String JOB_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.ljsbxt.title");
    private static final String PAGE_NAME = "currentPage";

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
//        String paramStr = convertToParamStr(param);
//        String pageContent = TLCSpiderRequest.get(URL_PRODUCT_LIST + paramStr, true);
//        String totalPage = TLCSpiderHTMLParser.parseAttribute(pageContent, "//a[@class='btns btn_page btn_small last']", "data-val");
        return Integer.valueOf(TLCSpiderConstants.SPIDER_PARAM_PAGE_ONE);
    }

    @Override
    public List<TransObject> getSpiderDataList(Map<String, String> param) {
        String paramStr = convertToParamStr(param);
        String productContent = TLCSpiderRequest.getViaProxy(URL_PRODUCT_LIST + paramStr, TLCSpiderRequest.ProxyType.HTTP);
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
        String href = TLCSpiderHTMLParser.parseAttribute(product, "//dt[@class='product-name']/a[1]", "href");
        String id = href.split("=")[1];

        String detailLink = URL_PRODUCT_DETAIL + id + "/productDetail?_=" + System.currentTimeMillis();
        String productContent = TLCSpiderRequest.getViaProxy(detailLink, TLCSpiderRequest.ProxyType.HTTP);
        LJSBXT ljsbxt = (LJSBXT) TLCSpiderJsonUtil.json2Object(productContent, LJSBXT.class, "investRewardInfo");

        transObject.setFinancingId(ljsbxt.getProductId());
        transObject.setProjectName(ljsbxt.getProductId());
        transObject.setProjectCode(ljsbxt.getCode());
        transObject.setProjectName(ljsbxt.getDisplayName() + " " + ljsbxt.getCode());
        transObject.setValueBegin(ljsbxt.getPublishedAtDateTime());
        transObject.setCreateTime(ljsbxt.getPublishedAtDateTime());
        transObject.setInvestmentInterest(String.valueOf(Double.parseDouble(ljsbxt.getInterestRateDisplay()) * 100));
        transObject.setAmount(ljsbxt.getPrice());

        String duration = ljsbxt.getInvestPeriodDisplay();
        if (duration.contains("月")) {
            duration = TLCSpiderSplitUtil.splitNumberChinese(duration, 1);
            duration = String.valueOf(Integer.parseInt(duration) * 30);
        } else {
            duration = TLCSpiderSplitUtil.splitNumberChinese(duration, 1);
        }
        transObject.setDuration(duration);

        String repayType = ljsbxt.getCollectionModeDisplay();
        if (repayType.contains("一次") && repayType.contains("本") && repayType.contains("息")) {
            repayType = TLCSpiderConstants.REPAY_TYPE.TOTAL.toString();
        } else if (repayType.contains("月") && !repayType.contains("本")) {
            repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_INTEREST.toString();
        } else {
            repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_MONNEY_INTEREST.toString();
        }
        transObject.setRepayType(repayType);

        String progress = ljsbxt.getProgress();
        if (!NumberUtils.isNumber(progress) || Float.valueOf(progress) == 1.0) {
            progress = TLCSpiderConstants.SPIDER_PARAM_PAGE_ONE;
        } else {
            progress = String.format("%.2f", Double.parseDouble(progress));
        }
        transObject.setRealProgress(progress);
        transObject.setProgress(progress);

        if (ljsbxt.getMinInvestAmount() != null) {
            int priceNum = Integer.parseInt(ljsbxt.getPrice());
            int amountNum = Integer.parseInt(ljsbxt.getMinInvestAmount());

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
