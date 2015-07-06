package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.quartz.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.DateFormatUtil;
import cn.com.fero.tlc.spider.util.JsonUtil;
import cn.com.fero.tlc.spider.util.LoggerUtil;
import cn.com.fero.tlc.spider.vo.NYYHNYE;
import cn.com.fero.tlc.spider.vo.TransObject;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//广东南粤银行南粤E+抓取
public class NYYHNYEJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = "https://one.gdnybank.com/directbank/erongProjectsList.do";

    public void execute(JobExecutionContext context) throws JobExecutionException {
        LoggerUtil.getLogger().info("开始抓取广东南粤银行南粤E+");

        Map<String, String> param = new HashMap();
        param.put("timeLimitType", "0");
        param.put("page", "0");
        param.put("pagesize", TLCSpiderConstants.SPIDER_PAGE_SIZE_GET);

        String countContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        String totalCount = JsonUtil.getString(countContent, "count");

        param.put("pagesize", totalCount);
        String productContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        List<NYYHNYE> nyyhnyeList = JsonUtil.json2Array(productContent, "projList", NYYHNYE.class);
        List<TransObject> transObjectList = new ArrayList();


//        "sumAmt": "23900",
//        "leftAmt": "476100",
//        "buyFlag": "1",
//        "limitNum": "3",
//        "setupDate": "20150626",
//        "singleSum": "100",
//        "recomType": "0",
//        "leftTime": "-1天-3小时-53分-13秒",
//        "investFlag": "1",
//        "acceptor": "",
//        "startFlag": "1",
//        "setlBatchDate": "20150805",

        for (NYYHNYE nyyhnye : nyyhnyeList) {
            TransObject transObject = new TransObject();
            transObject.setFinancingId(nyyhnye.getProdCode());
            transObject.setProjectCode(nyyhnye.getProjCode());
            transObject.setProjectName(nyyhnye.getProjName());
            transObject.setAmount(nyyhnye.getProductSize());
            transObject.setDuration(nyyhnye.getLimitType());
            transObject.setInvestmentInterest(Double.parseDouble(nyyhnye.getYieldRate()) * 100 + "%");
            transObject.setRealProgress(Double.parseDouble(nyyhnye.getBuyPercent()) * 100 + "%");
            transObject.setProgress(Double.parseDouble(nyyhnye.getBuyPercent()) * 100 + "%");
            if (StringUtils.isNotEmpty(nyyhnye.getPubStaDate()) && StringUtils.isNotEmpty(nyyhnye.getPubStaTime())) {
                transObject.setProjectBeginTime(DateFormatUtil.formatDateTime(TLCSpiderConstants.SPIDER_DEFAULT_FORMAT_DATE_TIME, nyyhnye.getPubStaDate(), nyyhnye.getPubStaTime()));
            } else if (StringUtils.isNotEmpty(nyyhnye.getPubStaDate())) {
                transObject.setProjectBeginTime(DateFormatUtil.formatDateTime(TLCSpiderConstants.SPIDER_DEFAULT_FORMAT_DATA, nyyhnye.getPubStaDate()));
            }
            if (StringUtils.isNotEmpty(nyyhnye.getSellStaDate()) && StringUtils.isNotEmpty(nyyhnye.getSellStaTime())) {
                transObject.setValueBegin(DateFormatUtil.formatDateTime(TLCSpiderConstants.SPIDER_DEFAULT_FORMAT_DATE_TIME, nyyhnye.getSellStaDate(), nyyhnye.getSellStaTime()));
            } else if (StringUtils.isNotEmpty(nyyhnye.getPubStaDate())) {
                transObject.setValueBegin(DateFormatUtil.formatDateTime(TLCSpiderConstants.SPIDER_DEFAULT_FORMAT_DATA, nyyhnye.getSellStaDate()));
            }
            if (StringUtils.isNotEmpty(nyyhnye.getSellEndDate()) && StringUtils.isNotEmpty(nyyhnye.getSellEndTime())) {
                transObject.setRepayBegin(DateFormatUtil.formatDateTime(TLCSpiderConstants.SPIDER_DEFAULT_FORMAT_DATE_TIME, nyyhnye.getSellEndDate(), nyyhnye.getSellEndTime()));
            } else if (StringUtils.isNotEmpty(nyyhnye.getSellEndDate())) {
                transObject.setRepayBegin(DateFormatUtil.formatDateTime(TLCSpiderConstants.SPIDER_DEFAULT_FORMAT_DATA, nyyhnye.getSellEndDate()));
            }

            //TODO 转换
//            financeValue.setPartsCount();
//            financeValue.setBankInterest();
//            financeValue.setRepayType();
//            financeValue.setRepaySourceType();
//            financeValue.setReadyBeginTime();
//            financeValue.setProjectStatus();
//            financeValue.setCreditLevel();
//            financeValue.setCreateUserId();
//            financeValue.setCreateCompanyId();
//            financeValue.setJmBeginTime();
//            financeValue.setAreaCode();
//            financeValue.setCreateTime();
//            financeValue.setUpdateUserId();
//            financeValue.setUpdateTime();
//            financeValue.setCreateUserName();
//            financeValue.setCreateCompanyName();
//            financeValue.setIsShow();
//            financeValue.setProjectType();
//            financeValue.setIsExclusivePublic();
//            financeValue.setMinInvestPartsCount();
//            financeValue.setExclusiveCode();
//            financeValue.setLcAmount();
//            financeValue.setICount();
//            financeValue.setIAmount();
//            financeValue.setFinanceApplyStatus();
//            financeValue.setHotStatus();
//            financeValue.setDbType();
//            financeValue.setTitle();
//            financeValue.setContent();
//            financeValue.setIsLimitCount();
            transObjectList.add(transObject);
        }

        print(transObjectList);
    }
}
