package cn.com.fero.tlc.spider.job.p2p;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderSplitUtil;
import cn.com.fero.tlc.spider.vo.p2p.LJSPJ;
import cn.com.fero.tlc.spider.vo.p2p.TransObject;
import org.apache.commons.lang.math.NumberUtils;
import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shaolichao on 2015/7/17.
 */
//陆金所投资频道安盈-票据抓取
public class LJSPJJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.ljspj.url.list");
    private static final String URL_PRODUCT_DETAIL = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.ljs.url.detail");
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.ljspj.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.ljspj.token");
    private static final String JOB_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.ljspj.title");
    private static final String PAGE_NAME = "currentPage";
    private static final String PAGE_SIZE = "5";

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
        LJSPJ ljspj = (LJSPJ) TLCSpiderJsonUtil.json2Object(productContent, LJSPJ.class, "investRewardInfo");

        transObject.setFinancingId(ljspj.getProductId());
        transObject.setProjectCode(ljspj.getProductId());
        transObject.setProjectName(ljspj.getDisplayName() + " " + ljspj.getCode());
        transObject.setValueBegin(ljspj.getValueDate());
        transObject.setProjectBeginTime(ljspj.getPublishedAtDateTime());
        transObject.setReadyBeginTime(ljspj.getPublishedAtDateTime());
        transObject.setCreateTime(ljspj.getPublishedAtDateTime());
        transObject.setInvestmentInterest(String.valueOf(Double.parseDouble(ljspj.getInterestRateDisplay()) * 100));
        transObject.setAmount(ljspj.getPrice());

        String duration = ljspj.getInvestPeriodDisplay();
        if (duration.contains("月")) {
            duration = TLCSpiderSplitUtil.splitNumberChinese(duration, 1);
            duration = String.valueOf(Integer.parseInt(duration) * 30);
        } else {
            duration = TLCSpiderSplitUtil.splitNumberChinese(duration, 1);
        }
        transObject.setDuration(duration);

        String repayType = ljspj.getCollectionModeDisplay();
        if (repayType.contains("一次") && repayType.contains("本") && repayType.contains("息")) {
            repayType = TLCSpiderConstants.REPAY_TYPE.TOTAL.toString();
        } else if (repayType.contains("月") && !repayType.contains("本")) {
            repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_INTEREST.toString();
        } else {
            repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_MONNEY_INTEREST.toString();
        }
        transObject.setRepayType(repayType);

        String progress = ljspj.getProgress();
        if (!NumberUtils.isNumber(progress) || Float.valueOf(progress) == 1.0) {
            progress = TLCSpiderConstants.SPIDER_PARAM_PAGE_ONE;
        } else {
            progress = String.format("%.2f", Double.parseDouble(progress));
        }
        transObject.setRealProgress(progress);
        transObject.setProgress(progress);

        if (ljspj.getMinInvestAmount() != null) {
            int priceNum = Integer.parseInt(ljspj.getPrice());
            int amountNum = Integer.parseInt(ljspj.getMinInvestAmount());

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
