package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.quartz.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.DateFormatUtil;
import cn.com.fero.tlc.spider.util.JsonUtil;
import cn.com.fero.tlc.spider.util.LoggerUtil;
import cn.com.fero.tlc.spider.util.PropertiesUtil;
import cn.com.fero.tlc.spider.vo.NBYHZXYH;
import cn.com.fero.tlc.spider.vo.TransObject;
import cn.com.fero.tlc.spider.vo.ZHXQYEJ;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//宁波银行投融资平台抓取
public class NBYHZXYHJob extends TLCSpiderJob {
    //detail: https://u.zxyh.nbcb.com.cn/home/detail?FinancingId=6f15a3a0-4a8c-42db-9e39-aaaaf1548e8c

    private static final String URL_PRODUCT_LIST = PropertiesUtil.getResource("tlc.spider.nbyhzxyh.url.list");
    private static final String SID = PropertiesUtil.getResource("tlc.spider.nbyhzxyh.sid");
    private static final String TOKEN = PropertiesUtil.getResource("tlc.spider.nbyhzxyh.token");
    private static final String JOB_TITLE = PropertiesUtil.getResource("tlc.spider.nbyhzxyh.title");

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
        param.put("targetAction", "CmbFinancingSearch");
        param.put("PageIndex", "1");
        param.put("PageSize", "10");
        param.put("Interest", "");
        param.put("Duration", "");
        param.put("ProjectStatus", "");
        param.put("ProjectAmount", "");
        return param;
    }

    @Override
    public int getTotalPage(Map<String, String> param) {
        String countContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        String countStr = JsonUtil.getString(countContent, "Data");
        String totalCount = JsonUtil.getString(countStr, "TotalCount");
        int pageSize = Integer.parseInt(TLCSpiderConstants.SPIDER_PAGE_SIZE_GET);
        int totalPage = Integer.parseInt(totalCount) % pageSize == 0 ? Integer.parseInt(totalCount) / pageSize : (Integer.parseInt(totalCount) / pageSize + 1);
        return totalPage;
    }

    @Override
    public List<TransObject> getSpiderDataList(Map<String, String> param) {
        String productContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        String productJsonStr = JsonUtil.getString(productContent, "Data");
        List<NBYHZXYH> productList = JsonUtil.json2Array(productJsonStr, "ResultList", NBYHZXYH.class);

        List<TransObject> transObjectList = new ArrayList();
        for(NBYHZXYH product : productList) {
            TransObject transObject = convertToTransObject(product);
            transObjectList.add(transObject);
        }

        return transObjectList;
    }

    private TransObject convertToTransObject(NBYHZXYH product) {
        TransObject transObject = new TransObject();
        transObject.setFinancingId(product.getFinancingId());
        transObject.setProjectCode(product.getProjectCode());
        transObject.setProjectName(product.getProjectName());
        transObject.setAmount(product.getAmount());
        transObject.setPartsCount(product.getMinInvestPartsCount());
        transObject.setInvestmentInterest(product.getInvestmentInterest());
        transObject.setDuration(product.getDuration());
        transObject.setValueBegin(convertDate(product.getValueBegin()));
        transObject.setProjectBeginTime(convertDate(product.getProjectBeginTime()));
        transObject.setReadyBeginTime(convertDate(product.getReadyBeginTime()));
        transObject.setProjectStatus(product.getProjectStatus());
        transObject.setJmBeginTime(convertDate(product.getjMBeginTime()));
        transObject.setCreateTime(convertDate(product.getCreateTime()));
        transObject.setProjectType(product.getProjectType());
        transObject.setIsExclusivePublic(product.getIsExclusivePublic());
        transObject.setMinInvestPartsCount(product.getMinInvestPartsCount());
        transObject.setProgress(product.getProgress());
        transObject.setRealProgress(product.getProgress());
        transObject.setFinanceApplyStatus(product.getFinanceApplyStatus());
        transObject.setHotStatus(product.getHotStatus());
        //TODO 未处理属性 ProgressForOrder: 0.99 RemainPartsCount: 3 UnrealJMBeginTime: "/Date(1435736774353)/"
        return transObject;
    }

    private String convertDate(String originalDate) {
        originalDate = originalDate.split("\\(")[1].split("\\)")[0];
        Long originalDateNum = Long.valueOf(originalDate);
        return DateFormatUtils.format(originalDateNum, TLCSpiderConstants.SPIDER_CONST_FORMAT_DISPLAY_DATE_TIME);
    }
}
