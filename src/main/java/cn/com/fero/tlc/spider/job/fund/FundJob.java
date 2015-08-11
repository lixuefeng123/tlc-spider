package cn.com.fero.tlc.spider.job.fund;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderLoggerUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.vo.fund.FundFetch;
import cn.com.fero.tlc.spider.vo.fund.FundSource;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.htmlcleaner.TagNode;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuefeng on 2015/8/10.
 */
//基金抓取
public class FundJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = TLCSpiderPropertiesUtil.getResource("tlc.spider.fund.url.list");
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.fund.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.fund.token");
    private static final String JOB_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.fund.title");

    public Map<String, String> constructSystemGetParam() {
        Map<String, String> param = new HashedMap();
        param.put(TLCSpiderConstants.SPIDER_PARAM_SID, SID);
        param.put(TLCSpiderConstants.SPIDER_PARAM_TOKEN, TOKEN);
        return param;
    }

    public Map<String, String> constructSystemSendParam(List<FundFetch> fetchList) {
        Map<String, String> param = new HashMap();
        param.put(TLCSpiderConstants.SPIDER_CONST_JOB_TITLE, JOB_TITLE);
        param.put(TLCSpiderConstants.SPIDER_PARAM_SID, SID);
        param.put(TLCSpiderConstants.SPIDER_PARAM_TOKEN, TOKEN);
        param.put(TLCSpiderConstants.SPIDER_PARAM_MESSAGE, JOB_TITLE);
        param.put(TLCSpiderConstants.SPIDER_PARAM_DATA, TLCSpiderJsonUtil.array2Json(fetchList));
        return param;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<String> fetchCodeList = null;
        try {
            TLCSpiderLoggerUtil.getLogger().info("获取所抓取基金代码");
            fetchCodeList = getFundCodeList();
        } catch (Exception e) {
            TLCSpiderLoggerUtil.getLogger().error("获取基金代码发生异常：" + ExceptionUtils.getFullStackTrace(e));
            Map<String, String> map = constructErrorParam("获取基金代码异常：" + ExceptionUtils.getFullStackTrace(e));
            sendDataToSystem(SPIDER_URL_FUND_SEND, map);
        }

        try {
            TLCSpiderLoggerUtil.getLogger().info("抓取基金数据");
            List<FundFetch> fundFetchList = getFundList(fetchCodeList);

            Map<String, String> sendParam = constructSystemSendParam(fundFetchList);
            sendDataToSystem(SPIDER_URL_FUND_SEND, sendParam);
        } catch (Exception e) {
            TLCSpiderLoggerUtil.getLogger().error("抓取基金数据异常：" + ExceptionUtils.getFullStackTrace(e));
            Map<String, String> map = constructErrorParam("抓取基金数据异常：" + ExceptionUtils.getFullStackTrace(e));
            sendDataToSystem(SPIDER_URL_FUND_SEND, map);
        }
    }

    private List<String> getFundCodeList() {
        String sourceContent = TLCSpiderRequest.post(SPIDER_URL_FUND_GET, constructSystemGetParam());
        List<FundSource> fundSourceList = TLCSpiderJsonUtil.json2Array(sourceContent, "data", FundSource.class);

        List<String> fundCodeList = new ArrayList();
        for (FundSource fundSource : fundSourceList) {
            fundCodeList.add(fundSource.getFund_code());
        }

        return fundCodeList;
    }

    public List<FundFetch> getFundList(List<String> fetchCodeList) {
        String fundContent = TLCSpiderRequest.getViaProxy(URL_PRODUCT_LIST, TLCSpiderRequest.ProxyType.HTTP);
        List<TagNode> fundList = TLCSpiderHTMLParser.parseNode(fundContent, "//table[@class='table fund-list-table table-striped table-sort search-container']//tbody//tr");
        return convertTofundFetchList(fetchCodeList, fundList);
    }

    private List<FundFetch> convertTofundFetchList(List<String> fetchCodeList, List<TagNode> fundList) {
        List<FundFetch> fundObjectList = new ArrayList();

        for (TagNode fund : fundList) {
            String code = TLCSpiderHTMLParser.parseText(fund, "//td[4]");
            if (fetchCodeList.contains(code)) {
                FundFetch fundFetch = convertToFundFetch(fund);
                fundObjectList.add(fundFetch);
            }
        }

        return fundObjectList;
    }

    private FundFetch convertToFundFetch(TagNode fund) {
        FundFetch fundFetch = new FundFetch();
        fundFetch.setPublishDate(TLCSpiderHTMLParser.parseText(fund, "//td[3]"));
        fundFetch.setFundCode(TLCSpiderHTMLParser.parseText(fund, "//td[4]"));
        fundFetch.setFundNick(TLCSpiderHTMLParser.parseText(fund, "//td[5]"));
        fundFetch.setTenThousandInterest(TLCSpiderHTMLParser.parseText(fund, "//td[6]"));
        fundFetch.setWeekInterest(TLCSpiderHTMLParser.parseText(fund, "//td[7]/span"));
        fundFetch.setWeekEarning(TLCSpiderHTMLParser.parseText(fund, "//td[8]/span"));
        fundFetch.setMonthEarning(TLCSpiderHTMLParser.parseText(fund, "//td[9]/span"));
        fundFetch.setThreeMonthEarning(TLCSpiderHTMLParser.parseText(fund, "//td[10]/span"));
        fundFetch.setHalfYearEarning(TLCSpiderHTMLParser.parseText(fund, "//td[11]/span"));
        fundFetch.setYearEarning(TLCSpiderHTMLParser.parseText(fund, "//td[12]/span"));
        fundFetch.setTwoYearEarning(TLCSpiderHTMLParser.parseText(fund, "//td[13]/span"));
        fundFetch.setThreeYearEarning(TLCSpiderHTMLParser.parseText(fund, "//td[14]/span"));
        fundFetch.setCurrentYearEarning(TLCSpiderHTMLParser.parseText(fund, "//td[15]/span"));
        fundFetch.setCreatedEarning(TLCSpiderHTMLParser.parseText(fund, "//td[16]/span"));
        fundFetch.setCreatedDate(TLCSpiderHTMLParser.parseText(fund, "//td[17]"));
        return fundFetch;
    }
}
