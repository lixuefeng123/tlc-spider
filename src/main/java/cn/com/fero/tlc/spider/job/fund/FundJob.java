package cn.com.fero.tlc.spider.job.fund;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderLoggerUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.vo.fund.FundSource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.htmlcleaner.TagNode;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.*;

/**
 * Created by lixuefeng on 2015/8/10.
 */
//基金抓取
public class FundJob extends TLCSpiderJob {
    private static final String url = TLCSpiderPropertiesUtil.getResource("tlc.spider.fund.url.get");
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.fund.source.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.fund.source.token");
    String sourceContent;
    String productContent;

    public Map<String, String> constructSystemGetParam() {
        Map<String, String> param = new HashedMap();
        param.put(TLCSpiderConstants.SPIDER_PARAM_SID, SID);
        param.put(TLCSpiderConstants.SPIDER_PARAM_TOKEN, TOKEN);
        return param;
    }

    public Map<String, String> constructSystemSendParam(List<FundSource> fetchList) {
        Map<String, String> param = new HashMap();
        param.put(TLCSpiderConstants.SPIDER_PARAM_SID, SID);
        param.put(TLCSpiderConstants.SPIDER_PARAM_TOKEN, TOKEN);
        param.put(TLCSpiderConstants.SPIDER_PARAM_DATA, TLCSpiderJsonUtil.array2Json(fetchList));
        return param;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            TLCSpiderLoggerUtil.getLogger().info("获取所抓取基金代码");
            List<String> fetchCodeList = getFundCodeList();
        } catch (Exception e) {
            TLCSpiderLoggerUtil.getLogger().error("获取基金代码发生异常：" + ExceptionUtils.getFullStackTrace(e));
            Map<String, String> map = constructErrorParam("获取基金代码异常：" + ExceptionUtils.getFullStackTrace(e));
            sendDataToSystem(SPIDER_URL_FUND_SEND, map);
        }

        try {
            TLCSpiderLoggerUtil.getLogger().info("抓取基金数据");
            String fund_url = "http://www.huobijijin.com/";
            productContent = TLCSpiderRequest.getViaProxy(fund_url, TLCSpiderRequest.ProxyType.HTTP);
            List<FundSource> list = getSpiderDataList();
            Map<String, String> sendParam = constructSystemSendParam(list);
            sendDataToSystem(SPIDER_URL_FUND_SEND, sendParam);
        } catch (Exception e) {
            TLCSpiderLoggerUtil.getLogger().error("抓取基金数据异常：" + ExceptionUtils.getFullStackTrace(e));
            Map<String, String> map = constructErrorParam("抓取基金数据异常：" + ExceptionUtils.getFullStackTrace(e));
            sendDataToSystem(SPIDER_URL_FUND_SEND, map);
        }
    }

    public List<FundSource> getSpiderDataList() {
        List<TagNode> fundList  = TLCSpiderHTMLParser.parseNode(productContent,"//table[@class='table fund-list-table table-striped table-sort search-container']//tbody//tr");

        List<FundSource> fundObjectList = new ArrayList();
        for (TagNode fund : fundList) {
            String code = TLCSpiderHTMLParser.parseText(fund,"//td[4]");
            if (CODE_SELECTED.contains(code)) {
                FundSource fundObject = convertToFundObject(fund);
                fundObjectList.add(fundObject);
            }
        }
        return fundObjectList;
    }

    private List<String> getFundCodeList() {
        String param = convertToParamStr(constructSystemGetParam());
        sourceContent = TLCSpiderRequest.get(url + param);
        String sourceData = TLCSpiderJsonUtil.getString(sourceContent,"data");
        List<String> fund_code = new ArrayList();
        JSONArray data = JSONArray.fromObject(sourceData);
        for(int i=0;i<data.size();i++){
            JSONObject jobj =  (JSONObject) data.get(i);
            String code = String.valueOf(jobj.get("fund_code"));
            fund_code.add(code);
        }
        return fund_code;
    }

    private FundSource convertToFundObject(TagNode fund) {
        String code = TLCSpiderHTMLParser.parseText(fund,"//td[4]");
        String date = TLCSpiderHTMLParser.parseText(fund,"//td[3]");
        String abbreviation = TLCSpiderHTMLParser.parseText(fund,"//td[5]");
        String wanIncome = TLCSpiderHTMLParser.parseText(fund,"//td[6]");
        String sevenNianHua = TLCSpiderHTMLParser.parseText(fund,"//td[7]/span");
        String nearOneWeek = TLCSpiderHTMLParser.parseText(fund,"//td[8]/span");
        String nearOneMonth = TLCSpiderHTMLParser.parseText(fund,"//td[9]/span");
        String nearThreeMonth = TLCSpiderHTMLParser.parseText(fund,"//td[10]/span");
        String nearSixMonth = TLCSpiderHTMLParser.parseText(fund,"//td[11]/span");
        String nearOneYear = TLCSpiderHTMLParser.parseText(fund,"//td[12]/span");
        String nearTwoYear = TLCSpiderHTMLParser.parseText(fund,"//td[13]/span");
        String nearThreeYear = TLCSpiderHTMLParser.parseText(fund,"//td[14]/span");
        String thisYear = TLCSpiderHTMLParser.parseText(fund,"//td[15]/span");
        String establish = TLCSpiderHTMLParser.parseText(fund,"//td[16]/span");
        String establishDate = TLCSpiderHTMLParser.parseText(fund,"//td[17]");

        FundSource fundObject = new FundSource();
        fundObject.setCode(code);
        fundObject.setAbbreviation(abbreviation);
        fundObject.setDate(date);
        fundObject.setWanIncome(wanIncome);
        fundObject.setSevenNianHua(sevenNianHua);
        fundObject.setNearOneMonth(nearOneMonth);
        fundObject.setNearOneWeek(nearOneWeek);
        fundObject.setNearThreeMonth(nearThreeMonth);
        fundObject.setNearSixMonth(nearSixMonth);
        fundObject.setNearOneYear(nearOneYear);
        fundObject.setNearTwoYear(nearTwoYear);
        fundObject.setNearThreeYear(nearThreeYear);
        fundObject.setThisYear(thisYear);
        fundObject.setEstablish(establish);
        fundObject.setEstablishDate(establishDate);

        return fundObject;
    }
}
