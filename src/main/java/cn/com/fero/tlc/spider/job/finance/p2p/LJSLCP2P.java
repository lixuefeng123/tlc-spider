package cn.com.fero.tlc.spider.job.finance.p2p;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.vo.TransObject;
import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//陆金所投资频道抓取
public class LJSLCP2P extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = TLCSpiderPropertiesUtil.getResource("tlc.spider.lzjlc.url.list");
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.lzjlc.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.lzjlc.token");
    private static final String JOB_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.lzjlc.title");
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
        transObject.setProjectCode(financingId);

        transObject.setProjectName(TLCSpiderHTMLParser.parseText(product, "//dl[@class='product-info']//dt[@class='product-name']/a[1]"));

        return transObject;
    }
}
