package cn.com.fero.tlc.spider.job.p2p;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderSplitUtil;
import cn.com.fero.tlc.spider.vo.p2p.LJSXB;
import cn.com.fero.tlc.spider.vo.p2p.TransObject;
import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shaolichao on 2015/7/20.
 */
//陆金所投资频道稳盈-鑫保抓取
public class LJSXBJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.lzjxb.url.list");
    private static final String URL_PRODUCT_DETAIL = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.lzjxb.url.detail");
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.lzjxb.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.lzjxb.token");
    private static final String JOB_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.lzjxb.title");
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
        LJSXB ljsxb = (LJSXB) TLCSpiderJsonUtil.json2Object(productContent, LJSXB.class, "investRewardInfo");

        transObject.setFinancingId(ljsxb.getProductId());
        transObject.setProjectCode(ljsxb.getProductId());
        transObject.setProjectName(ljsxb.getDisplayName() + "" + ljsxb.getCode());
        transObject.setAmount(ljsxb.getPrincipal());
        transObject.setValueBegin(ljsxb.getValueDate());
        transObject.setProjectBeginTime(ljsxb.getPublishedAtDateTime());
        transObject.setReadyBeginTime(ljsxb.getPublishedAtDateTime());
        transObject.setCreateTime(ljsxb.getPublishedAtDateTime());

        int duration = Integer.parseInt(TLCSpiderSplitUtil.splitNumberChinese(ljsxb.getInvestPeriodDisplay(), 1)) * 30;
        transObject.setDuration(String.valueOf(duration));

        String repayType = ljsxb.getCollectionModeDisplay();
        if (repayType.contains("到期") && repayType.contains("本") && repayType.contains("息")) {
            repayType = TLCSpiderConstants.REPAY_TYPE.TOTAL.toString();
        } else if (repayType.contains("按月")) {
            repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_INTEREST.toString();
        } else {
            repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_MONNEY_INTEREST.toString();
        }
        transObject.setRepayType(repayType);

        if (Float.valueOf(ljsxb.getProgress()) == 1.0) {
            String progress = TLCSpiderConstants.SPIDER_CONST_FULL_PROGRESS;
            transObject.setRealProgress(progress);
            transObject.setProgress(progress);
        } else {
            transObject.setRealProgress(ljsxb.getProgress());
            transObject.setProgress(ljsxb.getProgress());
        }

        if (ljsxb.getMinInvestAmount() != null) {
            int priceNum = Integer.parseInt(ljsxb.getPrice());
            int amountNum = Integer.parseInt(ljsxb.getMinInvestAmount());

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
