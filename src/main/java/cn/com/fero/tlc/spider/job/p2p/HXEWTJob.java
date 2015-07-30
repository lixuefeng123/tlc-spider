package cn.com.fero.tlc.spider.job.p2p;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderSplitUtil;
import cn.com.fero.tlc.spider.vo.TransObject;
import org.apache.commons.lang.math.NumberUtils;
import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//华夏银行E网通抓取
public class HXEWTJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = TLCSpiderPropertiesUtil.getResource("tlc.spider.hxewt.url.list");
    private static final String URL_PRODUCT_DETAIL = TLCSpiderPropertiesUtil.getResource("tlc.spider.hxewt.url.detail");
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.hxewt.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.hxewt.token");
    private static final String JOB_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.hxewt.title");
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
        return param;
    }

    @Override
    public int getTotalPage(Map<String, String> param) {
        return Integer.parseInt(TLCSpiderConstants.SPIDER_PARAM_PAGE_ONE);
    }

    @Override
    public List<TransObject> getSpiderDataList(Map<String, String> param) {
        String paramStr = convertToParamStr(param);
        String productContent = TLCSpiderRequest.getViaProxy(URL_PRODUCT_LIST + paramStr, TLCSpiderRequest.ProxyType.HTTP);
        List<TagNode> productList = TLCSpiderHTMLParser.parseNode(productContent, "//div[@class='list_con']");

        List<TransObject> transObjectList = new ArrayList();
        for (TagNode product : productList) {
            TransObject transObject = convertToTransObject(product);
            transObjectList.add(transObject);
        }

        return transObjectList;
    }

    private TransObject convertToTransObject(TagNode product) {
        String projectName = TLCSpiderHTMLParser.parseText(product, "//span[1]/a[1]").replaceAll(TLCSpiderConstants.SPIDER_CONST_HTML_BLANK, "");
        String projectBeginTime = TLCSpiderHTMLParser.parseText(product, "//div[@class='time'][1]/strong").split(":", 2)[1];
        String investmentInterest = TLCSpiderHTMLParser.parseText(product, "//ul[1]/li[2]/span[1]/strong[1]").replaceAll("%", "");
        String duration = TLCSpiderHTMLParser.parseText(product, "//ul[1]/li[3]/span[1]/strong[1]");
        duration = TLCSpiderSplitUtil.splitNumberChinese(duration, 1);
        String progress = TLCSpiderHTMLParser.parseText(product, "//div[@class='jd'][1]/strong[1]");
        if (!NumberUtils.isNumber(progress) || progress.equals(TLCSpiderConstants.SPIDER_CONST_FULL_PROGRESS)) {
            progress = TLCSpiderConstants.SPIDER_PARAM_PAGE_ONE;
        } else {
            progress = String.format("%.2f", Double.parseDouble(progress) / 100);
        }
        String detailLink = TLCSpiderHTMLParser.parseAttribute(product, "//ul[1]/li[5]/a", "href");
        String financingId = detailLink.split("/")[2].split("\\.")[0];
        String projectCode = financingId;

        String detailContent = TLCSpiderRequest.get(URL_PRODUCT_DETAIL + detailLink);
        String amount = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[1]/td[1]").split(" ")[1].split("\\.")[0].replaceAll(",", "");
        String type1 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[5]/td[2]/p[1]/span[1]");
        String type2 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[5]/td[2]/p[1]/span[2]");
        String type3 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[5]/td[2]/p[1]/span[3]");
        String type4 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[5]/td[2]/p[1]/span[4]");
        String type5 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[5]/td[2]/p[1]/span[5]");
        String repayType = type1 + type2 + type3 + type4 + type5;
        if (repayType.contains("到期") && repayType.contains("本") && repayType.contains("息")) {
            repayType = TLCSpiderConstants.REPAY_TYPE.TOTAL.toString();
        } else if (repayType.contains("按月")) {
            repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_INTEREST.toString();
        } else {
            repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_MONNEY_INTEREST.toString();
        }

        String begin1 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[1]");
        String begin2 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[2]");
        String begin3 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[3]");
        String begin4 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[4]");
        String begin5 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[5]");
        String valueBegin = begin1 + begin2 + begin3 + begin4 + begin5;
        valueBegin = valueBegin.replaceAll("年", "-").replaceAll("月", "-");

        String repay1 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[1]");
        String repay2 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[2]");
        String repay3 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[3]");
        String repay4 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[4]");
        String repay5 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[5]");
        String repayBegin = repay1 + repay2 + repay3 + repay4 + repay5;
        repayBegin = repayBegin.replaceAll("年", "-").replaceAll("月", "-");

        int minAmount = 1;
        String minInvestPartsCountStr = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[2]/td[4]").split("：")[1];
        minInvestPartsCountStr = minInvestPartsCountStr.split(" ")[0].replaceAll(",", "");
        minInvestPartsCountStr = TLCSpiderSplitUtil.splitNumberChinese(minInvestPartsCountStr, 1);
        minInvestPartsCountStr = minInvestPartsCountStr.replaceAll(",", "");
        Integer minInvestPartsCount = Integer.parseInt(minInvestPartsCountStr) / minAmount;
        Integer partsCount = Integer.parseInt(amount) / minAmount;


        TransObject transObject = new TransObject();
        transObject.setProjectName(projectName);
        transObject.setProjectBeginTime(projectBeginTime);
        transObject.setAmount(amount);
        transObject.setInvestmentInterest(investmentInterest);
        transObject.setDuration(duration);
        transObject.setProgress(progress);
        transObject.setRealProgress(progress);
        transObject.setFinancingId(financingId);
        transObject.setProjectCode(projectCode);
        transObject.setRepayType(repayType);
        transObject.setValueBegin(valueBegin);
        transObject.setRepayBegin(repayBegin);
        transObject.setMinInvestPartsCount(minInvestPartsCount.toString());
        transObject.setPartsCount(partsCount.toString());

        System.out.println(transObject);
        return transObject;
    }
}
