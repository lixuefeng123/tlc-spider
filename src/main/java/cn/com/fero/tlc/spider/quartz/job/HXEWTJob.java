package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.util.LoggerUtil;
import cn.com.fero.tlc.spider.util.SplitUtil;
import cn.com.fero.tlc.spider.vo.TransObject;
import org.htmlcleaner.TagNode;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gizmo on 15/6/17.
 */
//华夏银行E网通抓取
public class HXEWTJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = "http://hxewt.com/invest/main.html";
    private static final String URL_PRODUCT_DETAIL = "http://hxewt.com";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LoggerUtil.getLogger().info("开始抓取华夏银行E网通");
        String productContent = TLCSpiderRequest.get(URL_PRODUCT_LIST);
        List<TagNode> productList = TLCSpiderHTMLParser.parseNode(productContent, "//div[@class='list_con']");
        List<TransObject> transObjectList = new ArrayList();

        for (TagNode product : productList) {
            String projectName = TLCSpiderHTMLParser.parseText(product, "//span[1]/a[1]");
            String projectBeginTime = TLCSpiderHTMLParser.parseText(product, "//div[@class='time'][1]/strong").split(":", 2)[1];
//            String amount = TLCSpiderHTMLParser.parseText(product, "//ul[1]/li[1]/span[1]/strong[1]");
            String investmentInterest = TLCSpiderHTMLParser.parseText(product, "//ul[1]/li[2]/span[1]/strong[1]");
            String duration = TLCSpiderHTMLParser.parseText(product, "//ul[1]/li[3]/span[1]/strong[1]");
            duration = SplitUtil.splitNumberChinese(duration, 1);
            String leftAmount = TLCSpiderHTMLParser.parseText(product, "//ul[1]/li[4]/span[1]/strong[1]");
            String realProgress = TLCSpiderHTMLParser.parseText(product, "//div[@class='jd'][1]/strong[1]");
            String progress = realProgress;
            String detailLink = TLCSpiderHTMLParser.parseAttribute(product, "//ul[1]/li[5]/a", "href");
            String financingId = detailLink.split("/")[2].split("\\.")[0];
//            String financingId = detailLink.split("/")[2].split(".")[0];
            String projectCode = financingId;

            String detailContent = TLCSpiderRequest.get(URL_PRODUCT_DETAIL + detailLink);
            String amount = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[1]/td[1]").split(" ")[1];
            String type1 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[5]/td[2]/p[1]/span[1]");
            String type2 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[5]/td[2]/p[1]/span[2]");
            String type3 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[5]/td[2]/p[1]/span[3]");
            String type4 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[5]/td[2]/p[1]/span[4]");
            String type5 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[5]/td[2]/p[1]/span[5]");
            String repayType = type1 + type2 + type3 + type4 + type5;
            if (repayType.contains("到期") && repayType.contains("本") && repayType.contains("息")) {
                repayType = "0";
            } else if (repayType.contains("按月")) {
                repayType = "1";
            } else {
                repayType = "2";
            }

            String begin1 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[1]");
            String begin2 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[2]");
            String begin3 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[3]");
            String begin4 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[4]");
            String begin5 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[5]");
            String valueBegin = begin1 + begin2 + begin3 + begin4 + begin5;

            String repay1 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[1]");
            String repay2 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[2]");
            String repay3 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[3]");
            String repay4 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[4]");
            String repay5 = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[5]");
            String repayBegin = repay1 + repay2 + repay3 + repay4 + repay5;

            int minAmount = 1;
            String minInvestPartsCountStr = TLCSpiderHTMLParser.parseText(detailContent, "//table[1]//tr[2]/td[4]").split("：")[1];
            minInvestPartsCountStr = minInvestPartsCountStr.split(" ")[0].replaceAll(",", "");
            minInvestPartsCountStr = SplitUtil.splitNumberChinese(minInvestPartsCountStr, 1);
            minInvestPartsCountStr = minInvestPartsCountStr.replaceAll(",", "");
            Integer minInvestPartsCount = Integer.parseInt(minInvestPartsCountStr) / minAmount;
            Integer partsCount = Integer.parseInt(amount.substring(0, amount.lastIndexOf(".")).replaceAll(",", "")) / minAmount;


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
            transObject.setRepayType(repayType);
            transObject.setValueBegin(valueBegin);
            transObject.setRepayBegin(repayBegin);
            transObject.setMinInvestPartsCount(minInvestPartsCount.toString());
            transObject.setPartsCount(partsCount.toString());

            transObjectList.add(transObject);
        }

        print(transObjectList);
    }
}
