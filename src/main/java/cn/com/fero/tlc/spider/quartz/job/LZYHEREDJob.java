package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.quartz.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.JsonUtil;
import cn.com.fero.tlc.spider.util.PropertiesUtil;
import cn.com.fero.tlc.spider.util.SplitUtil;
import cn.com.fero.tlc.spider.vo.TransObject;
import com.sun.media.sound.InvalidDataException;
import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//兰州银行E融E贷抓取
public class LZYHEREDJob extends TLCSpiderJob {

    private static final String URL_PRODUCT_LIST = PropertiesUtil.getResource("tlc.spider.lzyhered.url.list");
    private static final String URL_PRODUCT_DETAIL = PropertiesUtil.getResource("tlc.spider.lzyhered.url.detail");
    private static final String SID = PropertiesUtil.getResource("tlc.spider.lzyhered.sid");
    private static final String TOKEN = PropertiesUtil.getResource("tlc.spider.lzyhered.token");
    private static final String JOB_TITLE = PropertiesUtil.getResource("tlc.spider.lzyhered.title");

    @Override
    public Map<String, String> constructSystemParam() {
        Map<String, String> param = new HashMap();
        param.put(TLCSpiderConstants.SPIDER_PARAM_STATUS_NAME, TLCSpiderConstants.SPIDER_PARAM_STATUS_SUCCESS_CODE);
        param.put(TLCSpiderConstants.SPIDER_PARAM_SID, SID);
        param.put(TLCSpiderConstants.SPIDER_PARAM_TOKEN, TOKEN);
        param.put(TLCSpiderConstants.SPIDER_CONST_JOB_TITLE, JOB_TITLE);
        param.put(TLCSpiderConstants.SPIDER_PARAM_PAGE_NAME, "pn");
        return param;
    }

    @Override
    public Map<String, String> constructSpiderParam() {
        Map<String, String> param = new HashMap();
        param.put("cond1", "0");
        param.put("cond2", "0");
        param.put("cond3", "0");
        param.put("cond4", "0");
        param.put("cond5", "0");
        param.put("pn", "1");
        param.put("id", "");
        param.put("pre", "");
        return param;
    }

    @Override
    public int getTotalPage(Map<String, String> param) {
        String countContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        String totalCount = TLCSpiderHTMLParser.parseText(countContent, "//div[@class='main_m_line1']//a[@class='inv_b_div1a'][last()]");
        return Integer.parseInt(totalCount);
    }

    @Override
    public Map<String, TransObject> getUpdateDataMap(Map<String, String> param) throws InvalidDataException {
        String result = TLCSpiderRequest.post(TLCSpiderConstants.SPIDER_URL_GET, param);
        String status = JsonUtil.getString(result, "state");
        if (!TLCSpiderConstants.SPIDER_PARAM_STATUS_SUCCESS_CODE.equals(status)) {
            throw new InvalidDataException(result);
        }

        List<TransObject> updateList = JsonUtil.json2Array(result, TLCSpiderConstants.SPIDER_PARAM_DATA, TransObject.class);
        Map<String, TransObject> updateMap = new HashMap();
        for (TransObject transObject : updateList) {
            updateMap.put(transObject.getFinancingId(), null);
        }
        return updateMap;
    }

    @Override
    public List<TransObject> getSpiderDataList(Map<String, String> param) {
        String productContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        List<TagNode> productList = TLCSpiderHTMLParser.parseNode(productContent, "//div[@class='main_l_main']/div[@class='main_m_line']");

        List<TransObject> transObjectList = new ArrayList();
        for (TagNode product : productList) {
            TransObject transObject = convertToTransObject(product);
            transObjectList.add(transObject);
        }

        return transObjectList;
    }

    private TransObject convertToTransObject(TagNode product) {
        String projectName = TLCSpiderHTMLParser.parseText(product, "//div[@class='m_l_left']/div[@class='m_l_title']/span");
        String amount = TLCSpiderHTMLParser.parseText(product, "//div[@class='m_l_left']/div[@class='title_second']/div[@class='left2'][2]");
        amount = SplitUtil.splitNumberChinese(amount, 1);
        amount += "00000";

        String investmentInterest = TLCSpiderHTMLParser.parseText(product, "//div[@class='m_l_left']/div[@class='title_second']/div[@class='left3']/font");
        investmentInterest = investmentInterest.replaceAll("%", "");
        String duration = TLCSpiderHTMLParser.parseText(product, "//div[@class='m_l_left']/div[@class='title_second']/div[@class='left1']");
        duration = SplitUtil.splitNumberChinese(duration, 1);
        String realProgress =TLCSpiderHTMLParser.parseText(product, "//div[@class='svgDemo']/script");

        realProgress = realProgress.replaceAll("\\n", "").replaceAll("\t", "").replaceAll(" ", "");
        realProgress = realProgress.split("\"")[3];
        String progress = realProgress;
        String detailLink = TLCSpiderHTMLParser.parseAttribute(product, "//a[@class='m_l_right right_unok']", "onclick");

        String projectBeginTime = null;
        String financingId = null;
        String projectCode = null;
        String valueBegin = null;
        String repayBegin = null;
        String minInvestPartsCount = null;
        String partsCount = null;
        if (detailLink.contains("viewProject")) {
            financingId = detailLink.split("'")[1];
            projectCode = financingId;
            String detailContent = TLCSpiderRequest.get(URL_PRODUCT_DETAIL + financingId);

            minInvestPartsCount = "1";
            String partsAmountStr = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='middle_bg']//div[@class='invest_l_bottom']//span[@class='tzdw']");
            int partsAmount = Integer.parseInt(partsAmountStr);
            partsCount = String.valueOf(Integer.parseInt(amount) / partsAmount);

            projectBeginTime = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='invest_l_top1']/div[@class='l_t_tfont_date']/span[@class='date2_span']");
            projectBeginTime = projectBeginTime.split(":", 2)[1].trim();

            valueBegin = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='invest_l_top1']/div[@class='l_t_tfont_date']");
            valueBegin = valueBegin.split(":")[2].trim();
        }

        TransObject transObject = new TransObject();
        transObject.setProjectName(projectName);
        transObject.setProjectBeginTime(projectBeginTime);
        transObject.setAmount(amount);
        transObject.setInvestmentInterest(investmentInterest);
        transObject.setDuration(duration);
        transObject.setRealProgress(realProgress);
        transObject.setProgress(progress);
        transObject.setFinancingId(financingId);
        transObject.setProjectCode(projectCode);
        transObject.setValueBegin(valueBegin);
        transObject.setRepayBegin(repayBegin);
        transObject.setMinInvestPartsCount(minInvestPartsCount.toString());
        transObject.setPartsCount(partsCount);

        return transObject;
    }
}
