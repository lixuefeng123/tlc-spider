package cn.com.fero.tlc.spider.job.p2p;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderDateFormatUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.vo.p2p.TransObject;
import org.apache.commons.lang3.StringUtils;
import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//民生银行民生转赚抓取
public class MSYHMSZZJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.msyhmszz.url.list");
    private static final String URL_PRODUCT_DETAIL = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.msyhmszz.url.detail");
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.msyhmszz.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.msyhmszz.token");
    private static final String JOB_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.msyhmszz.title");
    private static final String PAGE_NAME = "page";

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
        return param;
    }

    @Override
    public int getTotalPage(Map<String, String> param) {
        String pageContent = TLCSpiderRequest.postViaProxy(URL_PRODUCT_LIST, param, TLCSpiderRequest.ProxyType.HTTPS);
//        String totalPage = TLCSpiderHTMLParser.parseText(pageContent, "//div[@class='container']//div[@class='mod-page']/a[last()-1]");
        List<TagNode> pageNodeList = TLCSpiderHTMLParser.parseNode(pageContent, "//div[@class='container']//div[@class='mod-page']/a");
        int pageNodeLength = pageNodeList.size();
        String totalPage;
        if (pageNodeLength <= 2) {
            totalPage = TLCSpiderConstants.SPIDER_PARAM_PAGE_ONE;
        } else {
            totalPage = pageNodeList.get(pageNodeLength - 2).getText().toString().trim();
        }
        return Integer.parseInt(totalPage);
    }

    @Override
    public List<TransObject> getSpiderDataList(Map<String, String> param) {
        String productContent = TLCSpiderRequest.postViaProxy(URL_PRODUCT_LIST, param, TLCSpiderRequest.ProxyType.HTTPS);
        List<TagNode> productList = TLCSpiderHTMLParser.parseNode(productContent, "//div[@class='container']//div[@class='assets-inner']/ul/li");

        List<TransObject> transObjectList = new ArrayList();
        for (TagNode product : productList) {
            TransObject transObject = convertToTransObject(product);
            transObjectList.add(transObject);
        }

        return transObjectList;
    }

    private TransObject convertToTransObject(TagNode product) {
        TransObject transObject = new TransObject();
        String id = TLCSpiderHTMLParser.parseAttribute(product, "//div[@class='product-item']//h1[@class='product-title']//a", "href").split("=")[1];
        transObject.setFinancingId(id);
        transObject.setProjectCode(id);
        transObject.setProjectName(TLCSpiderHTMLParser.parseText(product, "//div[@class='product-item']//h1[@class='product-title']//a"));
        transObject.setTag(TLCSpiderHTMLParser.parseText(product, "//div[@class='product-item']//div[@class='org-tab']"));

        String detailLink = URL_PRODUCT_DETAIL + id;
        String detailContent = TLCSpiderRequest.getViaProxy(detailLink, TLCSpiderRequest.ProxyType.HTTPS);
        transObject.setInvestmentInterest(TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='lxybody']//li[1]//div[@class='num1']//i"));
        transObject.setDuration(TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='lxybody']//li[2]//div[@class='num2']//i"));

        String amount = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='lxybody']//li[3]//div[@class='num2']//script");
        amount = amount.split("\\(")[1].split("\\.")[0];
        transObject.setAmount(amount);

        String progress = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='lxybody']//div[@class='plan-num']");
        progress = progress.replaceAll("%", "");
        if ("100".equals(progress)) {
            progress = "1";
        } else {
            progress = String.valueOf(Double.valueOf(progress) / 100);
        }
        transObject.setProgress(progress);
        transObject.setRealProgress(progress);

        String minInvestUnit = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='lxybody']//span[@class='h-min']//script").split("\\(")[1];
        String[] investArray = minInvestUnit.split("\\*");
        String parts = investArray[0].replaceAll("'", "").trim();
        String base = investArray[1].replaceAll("'", "").split("\\.")[0].trim();
        if (StringUtils.isNotEmpty(minInvestUnit)) {
            int amountNum = Integer.parseInt(amount);
            int minInvestUnitNum = (Integer.parseInt(parts) * Integer.parseInt(base));

            int partsCount;
            if (amountNum % minInvestUnitNum == 0) {
                partsCount = amountNum / minInvestUnitNum;
            } else {
                partsCount = amountNum / minInvestUnitNum + 1;
            }

            transObject.setPartsCount(String.valueOf(partsCount));
        }

        String repayType = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='lxybody']//div[@class='buybox frt']/div[@class='info'][2]//script");
        if (StringUtils.isNotEmpty(repayType)) {
            repayType = repayType.split("\\(")[1].split(",")[0];
            if (repayType.contains("2")) {
                repayType = TLCSpiderConstants.REPAY_TYPE.TOTAL.toString();
            } else if (repayType.contains("1")) {
                repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_INTEREST.toString();
            } else {
                repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_MONNEY_INTEREST.toString();
            }
            transObject.setRepayType(repayType);
        }

        String valueBegin = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='lxybody']//div[@class='item-plan flt']//ul[@class='stepbox']//li[@class='step1-1']//div[@class='stepText'][2]");
        transObject.setValueBegin(TLCSpiderDateFormatUtil.formatDate(valueBegin, TLCSpiderConstants.SPIDER_CONST_FORMAT_DISPLAY_DATE_TIME));
        String repayBegin = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='lxybody']//div[@class='item-plan flt']//ul[@class='stepbox']//li[@class='step1-2']//div[@class='stepText'][2]");
        transObject.setRepayBegin(TLCSpiderDateFormatUtil.formatDate(repayBegin, TLCSpiderConstants.SPIDER_CONST_FORMAT_DISPLAY_DATE_TIME));

        return transObject;
    }
}
