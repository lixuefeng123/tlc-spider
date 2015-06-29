package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.util.LoggerUtil;
import cn.com.fero.tlc.spider.util.SplitUtil;
import cn.com.fero.tlc.spider.vo.TransObject;
import org.apache.commons.lang3.StringUtils;
import org.htmlcleaner.TagNode;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gizmo on 15/6/17.
 */
//包商银行小马金融抓取
public class BSYHFinanceJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = "https://www.xmjr.com/finance.do";
//    private static final String URL_PRODUCT_DETAIL = "https://www.xmjr.com/";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LoggerUtil.getLogger().info("开始抓取包商银行小马金融");

        String productContent = TLCSpiderRequest.get(URL_PRODUCT_LIST);
        List<TagNode> productList = TLCSpiderHTMLParser.parseNode(productContent, "//div[@class='wrap2 wrap3 clear']//table[@class='pil_table']/tbody/tr");
        List<TransObject> transObjectList = new ArrayList();

        for (TagNode product : productList) {
            String projectName = TLCSpiderHTMLParser.parserText(product, "//td[1]/a[1]");
            String investmentInterest = TLCSpiderHTMLParser.parserText(product, "//td[2]");
            String duration = TLCSpiderHTMLParser.parserText(product, "//td[3]");
            duration = String.valueOf(Integer.parseInt(SplitUtil.splitNumberChinese(duration, 1)) * 30);
            String amount = TLCSpiderHTMLParser.parserText(product, "//td[4]/strong").split("\\.")[0].replaceAll(",", "");
            String repayType = TLCSpiderHTMLParser.parserText(product, "//td[5]/a");
            if(StringUtils.isEmpty(repayType)) {
                repayType = TLCSpiderHTMLParser.parserText(product, "//td[5]");
            }
            if (repayType.contains("到期") && (repayType.contains("本") || repayType.contains("息"))) {
                repayType = "0";
            } else if (repayType.contains("按月") && repayType.contains("付息")) {
                repayType = "1";
            } else {
                repayType = "2";
            }
            String realProgress = TLCSpiderHTMLParser.parserText(product, "//td[6]/div[@class='persent']").replaceAll("&nbsp;", "").split("\\n")[1].trim() + "%";
            String progress = realProgress;
            String detailLink = TLCSpiderHTMLParser.parseAttribute(product, "//td[1]/a[1]", "href");
            String financingId = detailLink.split("=")[2];
            String projectCode = financingId;

            String detailContent = TLCSpiderRequest.get(detailLink);

            int minAmount = 1;
            String minInvestPartsCountStr = TLCSpiderHTMLParser.parserText(detailContent, "//div[@class='pi_tl_middle clear']//li[@class='eq2']//span[@id='pi_date_left']").split("\\.")[0];
            Integer minInvestPartsCount = Integer.parseInt(minInvestPartsCountStr) / minAmount;
            Integer partsCount = Integer.parseInt(amount) / minAmount;


            TransObject transObject = new TransObject();
            transObject.setProjectName(projectName);
            transObject.setAmount(amount);
            transObject.setInvestmentInterest(investmentInterest);
            transObject.setDuration(duration);
            transObject.setRealProgress(realProgress);
            transObject.setProgress(progress);
            transObject.setFinancingId(financingId);
            transObject.setProjectCode(projectCode);
            transObject.setRepayType(repayType);
            transObject.setMinInvestPartsCount(minInvestPartsCount.toString());
            transObject.setPartsCount(partsCount.toString());

            transObjectList.add(transObject);
        }

        print(transObjectList);
    }
}