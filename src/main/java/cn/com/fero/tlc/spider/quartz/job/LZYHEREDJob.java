package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.util.DateFormatUtil;
import cn.com.fero.tlc.spider.util.JsonUtil;
import cn.com.fero.tlc.spider.util.LoggerUtil;
import cn.com.fero.tlc.spider.util.SplitUtil;
import cn.com.fero.tlc.spider.vo.NYYHNYE;
import cn.com.fero.tlc.spider.vo.TransObject;
import org.apache.commons.lang3.StringUtils;
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
//兰州银行E融E贷抓取
public class LZYHEREDJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = "https://eeonline.lzbank.com/eplus-frontend/Projects_list.action";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LoggerUtil.getLogger().info("开始抓取兰州银行E融E贷");
        List<TransObject> transObjectList = new ArrayList();

        Map<String, String> param = new HashMap();
        param.put("cond1", "0");
        param.put("cond2", "0");
        param.put("cond3", "0");
        param.put("cond4", "0");
        param.put("cond5", "0");
        param.put("id", ")");
        param.put("pre", ")");

        String countContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        String totalCount = TLCSpiderHTMLParser.parserText(countContent, "//div[@class='main_m_line1']//a[@class='inv_b_div1a'][last()]");
        int totalCountNum = Integer.parseInt(totalCount);

        for(int a = 1; a <= totalCountNum; a++) {
            param.put("pn", totalCount);
            String productContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
            List<TagNode> productList = TLCSpiderHTMLParser.parseNode(productContent, "//div[@class='main_l_main']/div[@class='main_m_line']");


            for(TagNode product : productList) {
                String projectName = TLCSpiderHTMLParser.parserText(product, "//div[@class='m_l_left']/div[@class='m_l_title']/span");
                String projectBeginTime = TLCSpiderHTMLParser.parserText(product, "//div[@class='time'][1]/strong").split(":", 2)[1];
//            String amount = TLCSpiderHTMLParser.parserText(product, "//ul[1]/li[1]/span[1]/strong[1]");
                String investmentInterest = TLCSpiderHTMLParser.parserText(product, "//ul[1]/li[2]/span[1]/strong[1]");
                String duration = TLCSpiderHTMLParser.parserText(product, "//ul[1]/li[3]/span[1]/strong[1]");
                duration = SplitUtil.splitNumberChinese(duration, 1);
                String leftAmount = TLCSpiderHTMLParser.parserText(product, "//ul[1]/li[4]/span[1]/strong[1]");
                String realProgress = TLCSpiderHTMLParser.parserText(product, "//div[@class='jd'][1]/strong[1]");
                String progress = realProgress;
                String detailLink = TLCSpiderHTMLParser.parseAttribute(product, "//ul[1]/li[5]/a", "href");
                String financingId = detailLink.split("/")[2].split("\\.")[0];
//            String financingId = detailLink.split("/")[2].split(".")[0];
                String projectCode = financingId;

                String detailContent = TLCSpiderRequest.get(URL_PRODUCT_DETAIL + detailLink);
                String amount = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[1]/td[1]").split(" ")[1];
                String type1 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[5]/td[2]/p[1]/span[1]");
                String type2 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[5]/td[2]/p[1]/span[2]");
                String type3 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[5]/td[2]/p[1]/span[3]");
                String type4 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[5]/td[2]/p[1]/span[4]");
                String type5 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[5]/td[2]/p[1]/span[5]");
                String repayType = type1 + type2 + type3 + type4 + type5;
                if (repayType.contains("到期") && repayType.contains("本") && repayType.contains("息")) {
                    repayType = "0";
                } else if (repayType.contains("按月")) {
                    repayType = "1";
                } else {
                    repayType = "2";
                }

                String begin1 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[1]");
                String begin2 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[2]");
                String begin3 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[3]");
                String begin4 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[4]");
                String begin5 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[3]/td[2]/p[1]/span[5]");
                String valueBegin = begin1 + begin2 + begin3 + begin4 + begin5;

                String repay1 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[1]");
                String repay2 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[2]");
                String repay3 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[3]");
                String repay4 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[4]");
                String repay5 = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[4]/td[2]/p[1]/span[5]");
                String repayBegin = repay1 + repay2 + repay3 + repay4 + repay5;

                int minAmount = 1;
                String minInvestPartsCountStr = TLCSpiderHTMLParser.parserText(detailContent, "//table[1]//tr[2]/td[4]").split("：")[1];
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
        }

        print(transObjectList);
    }
}
