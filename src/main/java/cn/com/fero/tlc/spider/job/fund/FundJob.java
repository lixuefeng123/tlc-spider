package cn.com.fero.tlc.spider.job.fund;

import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.vo.fund.FundSource;
import org.htmlcleaner.TagNode;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.*;

/**
 * Created by lixuefeng on 2015/8/10.
 */
//基金抓取
public class FundJob extends TLCSpiderJob {

    private static final String CODE_SELECTED = TLCSpiderPropertiesUtil.getResource("tlc.spider.fund.selected");
    private static final String url = TLCSpiderPropertiesUtil.getResource("tlc.spider.fund.url.get");
    String fundContent;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        fundContent = TLCSpiderRequest.getViaProxy(url, TLCSpiderRequest.ProxyType.HTTP);
    }

    public List<FundSource> getSpiderDataList() {
        List<TagNode> fundList  = TLCSpiderHTMLParser.parseNode(fundContent,"//table[@class='table fund-list-table table-striped table-sort search-container']//tbody//tr");

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
