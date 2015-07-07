package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.quartz.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.JsonUtil;
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
//兰州银行E融E贷抓取
public class LZYHEREDJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = "https://eeonline.lzbank.com/eplus-frontend/Projects_list.action";
    private static final String URL_PRODUCT_DETAIL = "https://eeonline.lzbank.com/eplus-frontend/Projects_viewProject.action?projectId=";

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
        String totalCount = TLCSpiderHTMLParser.parseText(countContent, "//div[@class='main_m_line1']//a[@class='inv_b_div1a'][last()]");
        int totalCountNum = Integer.parseInt(totalCount);

        for (Integer a = 1; a <= totalCountNum; a++) {
            param.put("pn", a.toString());
            String productContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
            List<TagNode> productList = TLCSpiderHTMLParser.parseNode(productContent, "//div[@class='main_l_main']/div[@class='main_m_line']");

            for (TagNode product : productList) {
                String projectName = TLCSpiderHTMLParser.parseText(product, "//div[@class='m_l_left']/div[@class='m_l_title']/span");
                String amount = TLCSpiderHTMLParser.parseText(product, "//div[@class='m_l_left']/div[@class='title_second']/div[@class='left2'][2]");
                amount = SplitUtil.splitNumberChinese(amount, 1);
                amount += "00000";

                int minAmount = 1;
                String minInvestPartsCountStr = TLCSpiderHTMLParser.parseText(product, "//div[@class='m_l_left']/div[@class='title_second']/div[@class='left2'][1]");
                minInvestPartsCountStr = SplitUtil.splitNumberChinese(minInvestPartsCountStr, 1);
                Integer minInvestPartsCount = Integer.parseInt(minInvestPartsCountStr) / minAmount;
                Integer partsCount = Integer.parseInt(amount) / minAmount;

                String investmentInterest = TLCSpiderHTMLParser.parseText(product, "//div[@class='m_l_left']/div[@class='title_second']/div[@class='left3']/font");
                String duration = TLCSpiderHTMLParser.parseText(product, "//div[@class='m_l_left']/div[@class='title_second']/div[@class='left1']");
                duration = SplitUtil.splitNumberChinese(duration, 1);
//                String realProgress =TLCSpiderHTMLParser.parseText(product, "//div[@0 class='svgDemo']");
//                String realProgress =TLCSpiderHTMLParser.parseText(product, "//div[@class='svgDemo']//script");
//                realProgress = Double.parseDouble(realProgress) * 100 + "%";
//                String progress = realProgress;
                String detailLink = TLCSpiderHTMLParser.parseAttribute(product, "//a[@class='m_l_right right_unok']", "onclick");

                String projectBeginTime = null;
                String financingId = null;
                String projectCode = null;
                String valueBegin = null;
                String repayBegin = null;
                if (detailLink.contains("viewProject")) {
                    financingId = detailLink.split("'")[1];
                    projectCode = financingId;
                    String detailContent = TLCSpiderRequest.get(URL_PRODUCT_DETAIL + financingId);

                    projectBeginTime = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='invest_l_top1']/div[@class='l_t_tfont_date']/span[@class='date_span']");
//                    projectBeginTime = projectBeginTime.split(":", 2)[1];

                    valueBegin = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='invest_l_top1']/div[@class='l_t_tfont_date2']/span[@class='date2_span']");
//                    valueBegin = valueBegin.split(":")[1].trim();

//                    repayBegin = TLCSpiderHTMLParser.parseText(detailContent, "//div[@class='invest_l_top1']/div[@class='l_t_tfont_date2']").split(":")[2].trim();
                }

                TransObject transObject = new TransObject();
                transObject.setProjectName(projectName);
                transObject.setProjectBeginTime(projectBeginTime);
                transObject.setAmount(amount);
                transObject.setInvestmentInterest(investmentInterest);
                transObject.setDuration(duration);
//                transObject.setRealProgress(realProgress);
//                transObject.setProgress(progress);
                transObject.setFinancingId(financingId);
                transObject.setProjectCode(projectCode);
                transObject.setValueBegin(valueBegin);
                transObject.setRepayBegin(repayBegin);
                transObject.setMinInvestPartsCount(minInvestPartsCount.toString());
                transObject.setPartsCount(partsCount.toString());

                transObjectList.add(transObject);
            }
        }

        Map<String, String> map = new HashMap();
        map.put("sid", "2");
        map.put("token", "j1zTVyiJLtJK51tbCdEY5d3DO6W6VxW+ZAK4l3zrWZOx");
        map.put("data", JsonUtil.array2Json(transObjectList));
        String response = TLCSpiderRequest.post("http://192.168.2.19:3005/spiderapi/p2p/post", map);
        LoggerUtil.getLogger().info("发送招商银行小企业E家状态：" + response);
    }
}
