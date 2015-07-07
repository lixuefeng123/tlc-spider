package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.quartz.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.JsonUtil;
import cn.com.fero.tlc.spider.vo.TransObject;
import cn.com.fero.tlc.spider.vo.ZHXQYEJ;
import com.sun.media.sound.InvalidDataException;

import java.util.*;


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
        String pageStr = JsonUtil.getString(pageContent, "DicData");
        String totalPage = JsonUtil.getString(pageStr, "TotalPage");
        return Integer.parseInt(totalPage);
    }

    @Override
    public Map<String, TransObject> getUpdateDataMap(Map<String, String> param) throws InvalidDataException {
        String result = TLCSpiderRequest.post(TLCSpiderConstants.SPIDER_URL_GET, param);
        String status = JsonUtil.getString(result, "state");
        if (!TLCSpiderConstants.SPIDER_PARAM_STATUS_SUCCESS_CODE.equals(status)) {
            throw new InvalidDataException(result);
        }

        List<TransObject> updateList = JsonUtil.json2Array(result, TLCSpiderConstants.SPIDER_PARAM_DATA, TransObject.class);
        Map<String, TransObject> updateMap = new HashMap();
        for (TransObject transObject : updateList) {
            updateMap.put(transObject.getFinancingId(), null);
        }
        return updateMap;
    }

    @Override
    public List<TransObject> getSpiderDataList(Map<String, String> param) {
        String listContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        String listStr = JsonUtil.getString(listContent, "DicData");
        List<ZHXQYEJ> zhxqyejList = JsonUtil.json2Array(listStr, "NormalList", ZHXQYEJ.class);

        List<TransObject> transObjectList = new ArrayList();
        for(ZHXQYEJ zhxqyej : zhxqyejList) {
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
