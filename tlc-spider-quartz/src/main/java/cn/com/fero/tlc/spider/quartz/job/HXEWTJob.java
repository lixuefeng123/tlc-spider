package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.quartz.vo.FinanceValue;
import cn.com.fero.tlc.spider.util.LoggerUtil;
import org.htmlcleaner.TagNode;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;


/**
 * Created by gizmo on 15/6/17.
 */
//华夏银行E网通抓取
public class HXEWTJob extends TLCSpiderJob {
    private static final String URL_PRODUCT = "http://hxewt.com/invest/main.html";
    private static final String URL_PRODUCT_DETAIL = "http://hxewt.com";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LoggerUtil.getLogger().info("开始抓取华夏银行E网通");
        String productContent = TLCSpiderRequest.get(URL_PRODUCT);
        List<TagNode> productList = TLCSpiderHTMLParser.parseNode(productContent, "//div[@class='list_con']");
        for(TagNode product : productList) {
            String projectName = TLCSpiderHTMLParser.parserText(product, "/span[1]/a[1]");
            String projectBeginTime = TLCSpiderHTMLParser.parserText(product, "/div[@class='time'][1]/strong");
            String amount = TLCSpiderHTMLParser.parserText(product, "/ul[1]/li[1]/span[1]/strong[1]");
            String investmentInterest = TLCSpiderHTMLParser.parserText(product, "/ul[1]/li[2]/span[1]/strong[1]");
            String duration = TLCSpiderHTMLParser.parserText(product, "/ul[1]/li[3]/span[1]/strong[1]");
            String leftAmount = TLCSpiderHTMLParser.parserText(product, "/ul[1]/li[4]/span[1]/strong[1]");
            String realProgress = TLCSpiderHTMLParser.parserText(product, "/div[@class='jd'][1]/strong[1]");
            String progress = realProgress;
            String detailLink = TLCSpiderHTMLParser.parserText(product, "/ul[1]/li[5]/a");
            String financingId = detailLink.split("/")[1].split(".")[0];
            String projectCode = financingId;

            String detailContent = TLCSpiderRequest.get(URL_PRODUCT_DETAIL + detailLink);
            String amount2 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[1]/td[1]");
            String type1 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[5]/td[2]/p[1]/span[1]");
            String type2 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[5]/td[2]/p[1]/span[2]");
            String type3 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[5]/td[2]/p[1]/span[3]");
            String type4 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[5]/td[2]/p[1]/span[4]");
            String type5 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[5]/td[2]/p[1]/span[5]");
            String repayType = type1 + type2 + type3 + type4 + type5;
            if(repayType.contains("到期") && repayType.contains("本") && repayType.contains("息")) {
                repayType = "0";
            } else if(repayType.contains("按月")) {
                repayType = "1";
            } else {
                repayType = "2";
            }

            String begin1 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[1]");
            String begin2 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[1]/span[1]");
            String begin3 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[1]/span[2]");
            String begin4 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[2]");
            String begin5 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[3]");
            String begin6 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[4]");
            String begin7 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[5]");
            String valueBegin = begin1 + begin2 + begin3 + begin4 + begin5 + begin6 + begin7;

            String repay1 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[1]");
            String repay2 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[1]/span[1]");
            String repay3 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[2]");
            String repay4 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[3]");
            String repay5 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[4]");
            String repay6 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[4]/span[1]");
            String repay7 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[5]");
            String repayBegin = repay1 + repay2 + repay3 + repay4 + repay5 + repay6 + repay7;

            FinanceValue financeValue = new FinanceValue();
            financeValue.setProjectName(projectName);
            financeValue.setProjectBeginTime(projectBeginTime);
            financeValue.setAmount(amount);
            financeValue.setInvestmentInterest(investmentInterest);
            financeValue.setDuration(duration);
            financeValue.setRealProgress(realProgress);
            financeValue.setProgress(progress);
            financeValue.setFinancingId(financingId);
            financeValue.setProjectCode(projectCode);
            financeValue.setRepayType(repayType);
            financeValue.setValueBegin(valueBegin);
            financeValue.setRepayBegin(repayBegin);
            System.out.println(financeValue);

        }
    }
}
