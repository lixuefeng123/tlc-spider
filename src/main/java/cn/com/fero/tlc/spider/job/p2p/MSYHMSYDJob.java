package cn.com.fero.tlc.spider.job.p2p;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderDateFormatUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.vo.Tag;
import cn.com.fero.tlc.spider.vo.TransObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import net.sf.json.JSONArray;
import net.sf.json.util.JSONUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//民生银行民生易贷抓取
public class MSYHMSYDJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = TLCSpiderPropertiesUtil.getResource("tlc.spider.msyhmsyd.url.list");
    private static final String URL_PRODUCT_DETAIL = TLCSpiderPropertiesUtil.getResource("tlc.spider.msyhmsyd.url.detail");
    private static final String URL_PRODUCT_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.msyhmsyd.url.title");
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.msyhmsyd.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.msyhmsyd.token");
    private static final String JOB_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.msyhmsyd.title");
    private static final String PAGE_NAME = "page";

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
        param.put("_GLOBAL_REQUEST_DUPLICATE_KEY", "");
        param.put("_GLOBAL_SESSION_DUPLICATE_SWITCH", "true");
        param.put("_GLOBAL_SUBMIT_TYPE_KEY", "form");
        return param;
    }

    @Override
    public int getTotalPage(Map<String, String> param) {
        String pageContent = TLCSpiderRequest.postViaProxy(URL_PRODUCT_LIST, param, TLCSpiderRequest.ProxyType.HTTP);
//        String totalPage = TLCSpiderHTMLParser.parseText(pageContent, "//div[@class='u-content']//div[@class='bottom-page']//a[last()-1]");
        List<TagNode> pageNodeList = TLCSpiderHTMLParser.parseNode(pageContent, "//div[@class='u-content']//div[@class='bottom-page']//a");
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
        String productContent = TLCSpiderRequest.postViaProxy(URL_PRODUCT_LIST, param, TLCSpiderRequest.ProxyType.HTTP);
        List<TagNode> productList = TLCSpiderHTMLParser.parseNode(productContent, "//div[@id='content-gray']//ul[@class='c-list']/li");

        List<TransObject> transObjectList = new ArrayList();
        for (TagNode product : productList) {
            TransObject transObject = convertToTransObject(product);
            transObjectList.add(transObject);
        }

        return transObjectList;
    }

    private TransObject convertToTransObject(TagNode product) {
        TransObject transObject = new TransObject();
        String[] metaArray = TLCSpiderHTMLParser.parseText(product, "//div[@class='center-top']//script").split("\\(")[1].split("\\)")[0].split(",");
        String id = metaArray[0].replaceAll("'", "");
        String projectName = metaArray[2].replaceAll("'", "");
        transObject.setFinancingId(id);
        transObject.setProjectCode(id);
        transObject.setProjectName(projectName);
        transObject.setTag(getTag(id));

        transObject.setInvestmentInterest(TLCSpiderHTMLParser.parseText(product, "//ul/li[2]/strong/span").replaceAll("%", ""));

        String amount = TLCSpiderHTMLParser.parseText(product, "//ul/li[3]/span/script");
        amount = amount.split("\\(")[1].split("\\)")[0].split("\\.")[0];
        transObject.setAmount(amount);

        String duration = TLCSpiderHTMLParser.parseText(product, "//ul//script");
        duration = duration.split("\\(")[2].split("\\+")[0].replaceAll("'", "");
        transObject.setDuration(duration);

        String progress = TLCSpiderHTMLParser.parseText(product, "//ul/li[4]/span[2]/em");
        progress = progress.replaceAll("%", "");
        if ("100".equals(progress)) {
            progress = "1";
        } else {
            progress = String.valueOf(Double.valueOf(progress) / 100);
        }
        transObject.setProgress(progress);
        transObject.setRealProgress(progress);

        String detailLink = URL_PRODUCT_DETAIL + id;
        String detailContent = TLCSpiderRequest.getViaProxy(detailLink, TLCSpiderRequest.ProxyType.HTTPS);
        String minInvestUnit = TLCSpiderHTMLParser.parseText(detailContent, "//strong[@id='loan_minInvestUnit']");
        minInvestUnit = minInvestUnit.replace(",", "");
        if (StringUtils.isNotEmpty(minInvestUnit)) {
            int amountNum = Integer.parseInt(amount);
            int minInvestUnitNum = Integer.parseInt(minInvestUnit);

            int partsCount;
            if (amountNum % minInvestUnitNum == 0) {
                partsCount = amountNum / minInvestUnitNum;
            } else {
                partsCount = amountNum / minInvestUnitNum + 1;
            }
            transObject.setPartsCount(String.valueOf(partsCount));
        }

        String repayType = TLCSpiderHTMLParser.parseText(detailContent, "//span[@id='loan_productId']");
        if (repayType.contains("到期") && repayType.contains("本") && repayType.contains("息")) {
            repayType = TLCSpiderConstants.REPAY_TYPE.TOTAL.toString();
        } else if (repayType.contains("按月")) {
            repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_INTEREST.toString();
        } else {
            repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_MONNEY_INTEREST.toString();
        }
        transObject.setRepayType(repayType);

        String valueBegin = TLCSpiderHTMLParser.parseText(detailContent, "//span[@id='t_m_loan_borrowBearingStartDate']");
        transObject.setValueBegin(TLCSpiderDateFormatUtil.formatDate(valueBegin, TLCSpiderConstants.SPIDER_CONST_FORMAT_DISPLAY_DATE_TIME));
        String repayBegin = TLCSpiderHTMLParser.parseText(detailContent, "//span[@id='t_m_loan_borrowBearingEndDate']");
        transObject.setRepayBegin(TLCSpiderDateFormatUtil.formatDate(repayBegin, TLCSpiderConstants.SPIDER_CONST_FORMAT_DISPLAY_DATE_TIME));

        return transObject;
    }

    private String getTag(String id) {
        Map<String, String> titleParam = new HashMap();
        titleParam.put("loanId", id);
        titleParam.put("purpose", "1");

        String titleContent = TLCSpiderRequest.postViaProxy(URL_PRODUCT_TITLE, titleParam, TLCSpiderRequest.ProxyType.HTTP);
        List<Tag> tagList = TLCSpiderJsonUtil.json2Array(titleContent, "promotions", Tag.class);

        if(CollectionUtils.isEmpty(tagList)) {
            return StringUtils.EMPTY;
        }

        Tag tag = tagList.get(0);
        return tag.getName();
    }
}
