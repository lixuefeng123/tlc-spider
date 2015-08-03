package cn.com.fero.tlc.spider.job.p2p;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderSplitUtil;
import cn.com.fero.tlc.spider.vo.TransObject;
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
    private static final String URL_PRODUCT_LIST = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.lzjbxt.url.list");
    private static final String URL_PRODUCT_DETAIL = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.lzjbxt.url.detail");
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.lzjbxt.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.lzjbxt.token");
    private static final String JOB_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.lzjbxt.title");
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
        String financingId = href.split("=")[1];
        transObject.setFinancingId(financingId);

        String projectName = TLCSpiderHTMLParser.parseText(product, "//dt[@class='product-name']/a[1]");
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

        String projectStatus = TLCSpiderHTMLParser.parseText(product, "//div[@class='product-status product-status-preview']//a[@class='list-btn is-grey']");
        transObject.setProjectStatus(projectStatus);

        String detailLink = URL_PRODUCT_DETAIL + href;
        String detailContent = TLCSpiderRequest.getViaProxy(detailLink, TLCSpiderRequest.ProxyType.HTTP);

        String investmentInterest = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='main-wrap']//ul[@class='clearfix detail-info-list']//li[1]/p[2]/strong");
        investmentInterest = investmentInterest.replaceAll("%", "");
        transObject.setInvestmentInterest(investmentInterest);

        if (projectStatus.equals("已结束")) {
            transObject.setProgress(TLCSpiderConstants.SPIDER_CONST_FULL_PROGRESS);
            transObject.setRealProgress(TLCSpiderConstants.SPIDER_CONST_FULL_PROGRESS);
        } else {
            String progress = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='main-wrap']//div[@class='progress-wrap clearfix']/span[@class='progressTxt']");
            progress = progress.replaceAll("%", "");
            if (progress.equals("100")) {
                transObject.setProgress(TLCSpiderConstants.SPIDER_CONST_FULL_PROGRESS);
                transObject.setRealProgress(TLCSpiderConstants.SPIDER_CONST_FULL_PROGRESS);
            } else if (!progress.equals("")) {
                double progressNum = Double.parseDouble(progress) / 100;
                transObject.setProgress(String.valueOf(progressNum));
                transObject.setRealProgress(String.valueOf(progressNum));
            }
        }

        String valueBegin = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='main-wrap']//li[@class='last-col']//strong");
        transObject.setValueBegin(valueBegin);

//        String publishTime = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='main-wrap']//p[@class='product-published-date']");
//        if (StringUtils.isNotEmpty(publishTime)) {
//            publishTime = publishTime.split("：")[1];
//            transObject.setProjectBeginTime(publishTime);
//        }

        String amount = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='main-wrap']//div[@class='invest-amount']//strong[@class='orange-word']");
        amount = amount.split(" ")[0].replaceAll(",", "").split("\\.")[0];
        transObject.setAmount(amount);

        String repayType = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='main-wrap']//table[@class='product-description']//span[@class='tips-title']");
        if (repayType.contains("一次") && repayType.contains("本") && repayType.contains("息")) {
            repayType = TLCSpiderConstants.REPAY_TYPE.TOTAL.toString();
        } else if (repayType.contains("月") && !repayType.contains("本")) {
            repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_INTEREST.toString();
        } else {
            repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_MONNEY_INTEREST.toString();
        }
        transObject.setRepayType(repayType);

        return transObject;
    }
}
