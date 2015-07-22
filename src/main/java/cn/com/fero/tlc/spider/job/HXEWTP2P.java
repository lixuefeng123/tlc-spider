package cn.com.fero.tlc.spider.job;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderLoggerUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderSplitUtil;
import cn.com.fero.tlc.spider.vo.TransObject;
import cn.com.fero.tlc.spider.vo.ZHXQYEJ;
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
//华夏银行E网通抓取
public class HXEWTP2P extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = "https://efinance.cmbchinaucs.com/Handler/ActionPageV4.aspx";
    private static final String URL_PRODUCT_DETAIL = "http://hxewt.com";
    private static final String SID = "1";
    private static final String TOKEN = "2kd2Z1U=VbNhBw1XYxiuMJBaYP9FB=oPEJn3wn3qxKWU";
    private static final String JOB_TITLE = "华夏银行E网通";

    @Override
    public Map<String, String> constructSystemParam() {
        Map<String, String> param = new HashMap();
        param.put(TLCSpiderConstants.SPIDER_PARAM_STATUS_NAME, TLCSpiderConstants.SPIDER_PARAM_STATUS_SUCCESS_CODE);
        param.put(TLCSpiderConstants.SPIDER_PARAM_SID, SID);
        param.put(TLCSpiderConstants.SPIDER_PARAM_TOKEN, TOKEN);
        param.put(TLCSpiderConstants.SPIDER_CONST_JOB_TITLE, JOB_TITLE);
        param.put(TLCSpiderConstants.SPIDER_PARAM_PAGE_NAME, "PageIndex");
        return param;
    }

    @Override
    public Map<String, String> constructSpiderParam() {
        Map<String, String> param = new HashMap();
        param.put("TargetAction", "GetProjectList_Index");
        param.put("PageSize", "10");
        param.put("PageIndex", "1");
        param.put("Sort", "normal");
        return param;
    }

    @Override
    public int getTotalPage(Map<String, String> param) {
        String pageContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        String pageStr = TLCSpiderJsonUtil.getString(pageContent, "DicData");
        String totalPage = TLCSpiderJsonUtil.getString(pageStr, "TotalPage");
        return Integer.parseInt(totalPage);
    }

    @Override
    public Map<String, TransObject> getUpdateDataMap(Map<String, String> param) {
        String result = TLCSpiderRequest.post(TLCSpiderConstants.SPIDER_URL_GET, param);
        String status = TLCSpiderJsonUtil.getString(result, "state");
        if (!TLCSpiderConstants.SPIDER_PARAM_STATUS_SUCCESS_CODE.equals(status)) {
            throw new IllegalStateException(result);
        }

        List<TransObject> updateList = TLCSpiderJsonUtil.json2Array(result, TLCSpiderConstants.SPIDER_PARAM_DATA, TransObject.class);
        Map<String, TransObject> updateMap = new HashMap();
        for (TransObject transObject : updateList) {
            updateMap.put(transObject.getFinancingId(), null);
        }
        return updateMap;
    }

    @Override
    public List<TransObject> getSpiderDataList(Map<String, String> param) {
        String listContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        String listStr = TLCSpiderJsonUtil.getString(listContent, "DicData");
        List<ZHXQYEJ> zhxqyejList = TLCSpiderJsonUtil.json2Array(listStr, "NormalList", ZHXQYEJ.class);

        List<TransObject> transObjectList = new ArrayList();
        for (ZHXQYEJ zhxqyej : zhxqyejList) {
            TransObject transObject = constructTransObject(zhxqyej);
            transObjectList.add(transObject);
        }

        return transObjectList;
    }

    private TransObject constructTransObject(ZHXQYEJ zhxqyej) {
        TransObject transObject = new TransObject();
        transObject.setFinancingId(zhxqyej.getFinancingId());
        transObject.setProjectCode(zhxqyej.getProjectCode());
        transObject.setProjectName(zhxqyej.getProjectName());
        transObject.setBindUserId(zhxqyej.getBindUserId());
        transObject.setBindUserName(zhxqyej.getBindUserName());
        transObject.setBindCompanyId(zhxqyej.getBindCompanyId());
        transObject.setBindCompanyName(zhxqyej.getBindCompanyName());
        transObject.setAmount(zhxqyej.getAmount());
        transObject.setPartsCount(zhxqyej.getMinInvestPartsCount());
        transObject.setBankInterest(zhxqyej.getBankInterest());
        transObject.setInvestmentInterest(zhxqyej.getInvestmentInterest());
        transObject.setDuration(zhxqyej.getDuration());
        transObject.setRepayType(zhxqyej.getRepayType());
        transObject.setValueBegin(zhxqyej.getValueBegin());
        transObject.setRepayBegin(zhxqyej.getRepayBegin());
        transObject.setRepaySourceType(zhxqyej.getRepaySourceType());
        transObject.setProjectBeginTime(zhxqyej.getProjectBeginTime());
        transObject.setReadyBeginTime(zhxqyej.getReadyBeginTime());
        transObject.setProjectStatus(zhxqyej.getProjectStatus());
        transObject.setCreditLevel(zhxqyej.getCreditLevel());
        transObject.setCreateUserId(zhxqyej.getCreateUserId());
        transObject.setCreateCompanyId(zhxqyej.getCreateCompanyId());
        transObject.setJmBeginTime(zhxqyej.getjMBeginTime());
        transObject.setAreaCode(zhxqyej.getAreaCode());
        transObject.setCreateTime(zhxqyej.getCreateTime());
        transObject.setUpdateUserId(zhxqyej.getUpdateUserId());
        transObject.setUpdateTime(zhxqyej.getUpdateTime());
        transObject.setCreateUserName(zhxqyej.getCreateUserName());
        transObject.setCreateCompanyName(zhxqyej.getCreateCompanyName());
        transObject.setIsShow(zhxqyej.getIsShow());
        transObject.setProjectType(zhxqyej.getProjectType());
        transObject.setIsExclusivePublic(zhxqyej.getIsExclusivePublic());
        transObject.setMinInvestPartsCount(zhxqyej.getMinInvestPartsCount());
        transObject.setExclusiveCode(zhxqyej.getExclusiveCode());
        transObject.setLcAmount(zhxqyej.getlCAmount());
        transObject.setiCount(zhxqyej.getiCount());
        transObject.setiAmount(zhxqyej.getiAmount());
        transObject.setRealProgress(zhxqyej.getRealProgress());
        transObject.setProgress(zhxqyej.getProgress());
        transObject.setFinanceApplyStatus(zhxqyej.getFinanceApplyStatus());
        transObject.setHotStatus(zhxqyej.getHotStatus());
        transObject.setDbType(zhxqyej.getDbType());
        return transObject;
    }


    public void execute(JobExecutionContext context) throws JobExecutionException {
        TLCSpiderLoggerUtil.getLogger().info("开始抓取华夏银行E网通");
        String productContent = TLCSpiderRequest.get(URL_PRODUCT_LIST);
        List<TagNode> productList = TLCSpiderHTMLParser.parseNode(productContent, "//div[@class='list_con']");
        List<TransObject> transObjectList = new ArrayList();

        for (TagNode product : productList) {
            String projectName = TLCSpiderHTMLParser.parseText(product, "//span[1]/a[1]");
            String projectBeginTime = TLCSpiderHTMLParser.parseText(product, "//div[@class='time'][1]/strong").split(":", 2)[1];
//            String amount = TLCSpiderHTMLParser.parseText(product, "//ul[1]/li[1]/span[1]/strong[1]");
            String investmentInterest = TLCSpiderHTMLParser.parseText(product, "//ul[1]/li[2]/span[1]/strong[1]");
            String duration = TLCSpiderHTMLParser.parseText(product, "//ul[1]/li[3]/span[1]/strong[1]");
            duration = TLCSpiderSplitUtil.splitNumberChinese(duration, 1);
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
                repayType = TLCSpiderConstants.REPAY_TYPE.TOTAL.toString();
            } else if (repayType.contains("按月")) {
                repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_INTEREST.toString();
            } else {
                repayType = TLCSpiderConstants.REPAY_TYPE.MONTHLY_MONNEY_INTEREST.toString();
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
            minInvestPartsCountStr = TLCSpiderSplitUtil.splitNumberChinese(minInvestPartsCountStr, 1);
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
