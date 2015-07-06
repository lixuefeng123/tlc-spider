package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.quartz.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.JsonUtil;
import cn.com.fero.tlc.spider.util.LoggerUtil;
import cn.com.fero.tlc.spider.vo.TransObject;
import cn.com.fero.tlc.spider.vo.ZHXQYEJ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//招商银行小企业E家抓取
public class ZHXQYEJJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = "https://efinance.cmbchinaucs.com/Handler/ActionPageV4.aspx";
    private static final String SID = "1";
    private static final String TOKEN = "2kd2Z1U=VbNhBw1XYxiuMJBaYP9FB=oPEJn3wn3qxKWU";
    private static final String JOB_TITLE = "招商银行小企业E家";

    @Override
    public void doExecute( Map<String, TransObject> updateMap) throws Exception {
        List<TransObject> transObjectList = new ArrayList();

//        LoggerUtil.getLogger().info("开始获取" + JOB_TITLE + "更新列表");
//        Map<String, TransObject> updateMap = getUpdateMap();
//        LoggerUtil.getLogger().info(JOB_TITLE + "更新条数 = "+ updateMap.size());

        Map<String, String> param = constructRequestParam();
        LoggerUtil.getLogger().info("开始抓取" + JOB_TITLE + "总页数");
        int totalPageNum = getTotalCount(param);
        LoggerUtil.getLogger().info(JOB_TITLE + "总页数数 = " + totalPageNum);

        boolean isContinue = true;
        for (Integer page = 1; page <= totalPageNum; page++) {
            if (!isContinue) {
                break;
            }

            LoggerUtil.getLogger().info("开始抓取" + JOB_TITLE + "第" + page + "页");
            param.put("PageIndex", page.toString());
            List<ZHXQYEJ> zhxqyejList = getZHXQYEList(param);

            for (ZHXQYEJ zhxqyej : zhxqyejList) {
                TransObject transObject = constructTransObject(zhxqyej);
                if (!transObject.getProgress().equals("100%") || updateMap.containsKey(transObject.getFinancingId())) {
                    transObjectList.add(transObject);
                } else {
                    isContinue = false;
                    LoggerUtil.getLogger().info("发送" + JOB_TITLE + "数据, size = " + transObjectList.size());
                    postData(transObjectList);
                    transObjectList.clear();
                    break;
                }

                if (transObjectList.size() >= TLCSpiderConstants.SPIDER_PAGE_SIZE_SEND) {
                    LoggerUtil.getLogger().info("发送" + JOB_TITLE + "数据, size = " + transObjectList.size());
                    postData(transObjectList);
                    transObjectList.clear();
                }
            }
        }

        if(transObjectList.size() > 0) {
            LoggerUtil.getLogger().info("发送" + JOB_TITLE + "数据, size = " + transObjectList.size());
            postData(transObjectList);
            transObjectList.clear();
        }
    }

    private List<ZHXQYEJ> getZHXQYEList(Map<String, String> param) {
        String listContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        String listStr = JsonUtil.getString(listContent, "DicData");
        return JsonUtil.json2Array(listStr, "NormalList", ZHXQYEJ.class);
    }

    private int getTotalCount(Map<String, String> param) {
        String pageContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        String pageStr = JsonUtil.getString(pageContent, "DicData");
        String totalPage = JsonUtil.getString(pageStr, "TotalPage");
        return Integer.parseInt(totalPage);
    }

    @Override
    public Map<String, String> constructPostParam(List<TransObject> transObjectList) {
        Map<String, String> map = new HashMap();
        map.put(TLCSpiderConstants.HTTP_PARAM_STATUS_NAME, TLCSpiderConstants.HTTP_PARAM_STATUS_SUCCESS_CODE);
        map.put(TLCSpiderConstants.HTTP_PARAM_SID, SID);
        map.put(TLCSpiderConstants.HTTP_PARAM_TOKEN, TOKEN);
        map.put(TLCSpiderConstants.HTTP_PARAM_DATA, JsonUtil.array2Json(transObjectList));
        map.put(TLCSpiderConstants.HTTP_PARAM_JOB_TITLE, JOB_TITLE);
        return map;
    }

    private Map<String, String> constructRequestParam() {
        Map<String, String> param = new HashMap();
        param.put("TargetAction", "GetProjectList_Index");
        param.put("PageIndex", "1");
        param.put("Sort", "normal");
        return param;
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
        transObject.setICount(zhxqyej.getiCount());
        transObject.setIAmount(zhxqyej.getiAmount());
        transObject.setRealProgress(zhxqyej.getRealProgress());
        transObject.setProgress(zhxqyej.getProgress());
        transObject.setFinanceApplyStatus(zhxqyej.getFinanceApplyStatus());
        transObject.setHotStatus(zhxqyej.getHotStatus());
        transObject.setDbType(zhxqyej.getDbType());
        return transObject;
    }
}
