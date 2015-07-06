package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.quartz.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.LoggerUtil;
import cn.com.fero.tlc.spider.util.SplitUtil;
import cn.com.fero.tlc.spider.vo.TransObject;
import org.htmlcleaner.TagNode;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//包商银行小马bank抓取
public class BSYHBankJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = "https://www.xiaomabank.com/finance.do";
    private static final String URL_PRODUCT_DETAIL = "https://www.xiaomabank.com/";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LoggerUtil.getLogger().info("开始抓取包商银行小马bank");

        Map<String, String> param = new HashMap();
        param.put("borrowTypeXM", "1");
        param.put("statusXM", "1");
        param.put("annualRateXM", "desc");
        param.put("deadlineXM", "asc");
        param.put("schedulesXM", "desc");
        param.put("sortByXM", "none");
        param.put("remainTimeXM", "1");

        String productContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        List<TagNode> productList = TLCSpiderHTMLParser.parseNode(productContent, "//div[@class='wrap2 wrap3 clear']//table[@class='pil_table']/tbody/tr");
        List<TransObject> transObjectList = new ArrayList();

        for (TagNode product : productList) {
            String projectName = TLCSpiderHTMLParser.parseText(product, "//td[1]/a[1]");
            String investmentInterest = TLCSpiderHTMLParser.parseText(product, "//td[3]");
            String duration = TLCSpiderHTMLParser.parseText(product, "//td[4]");
            duration = String.valueOf(Integer.parseInt(SplitUtil.splitNumberChinese(duration, 1)) * 30);
            String amount = TLCSpiderHTMLParser.parseText(product, "//td[5]/strong").split("\\.")[0].replaceAll(",", "");
            String realProgress = TLCSpiderHTMLParser.parseText(product, "//td[6]/div[@class='persent']").replaceAll("&nbsp;", "").trim();
            String progress = realProgress;
            String detailLink = TLCSpiderHTMLParser.parseAttribute(product, "//td[1]/a[1]", "href");
            String financingId = detailLink.split("=")[1];
            String projectCode = financingId;

            String detailContent = TLCSpiderRequest.get(URL_PRODUCT_DETAIL + detailLink);
            String repayType = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='pi_top_right_refund']//p[@class='pi_refund_text_right']/em");
            if (repayType.contains("到期") && (repayType.contains("本") || repayType.contains("息"))) {
                repayType = "0";
            } else if (repayType.contains("按月")) {
                repayType = "1";
            } else {
                repayType = "2";
            }

            int minAmount = 1;
            String minInvestPartsCountStr = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='pi_tl_middle clear']//li[@class='eq2']//span[@id='pi_date_left']").split("\\.")[0];
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

    @Override
    protected Map<String, String> constructPostParam() {
        return null;
    }

    @Override
    public void doExecute() {

    }
}
