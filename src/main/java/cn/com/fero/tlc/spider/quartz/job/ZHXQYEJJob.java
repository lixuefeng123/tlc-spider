package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.quartz.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.JsonUtil;
import cn.com.fero.tlc.spider.util.PropertiesUtil;
import cn.com.fero.tlc.spider.vo.TransObject;
import cn.com.fero.tlc.spider.vo.ZHXQYEJ;
import com.sun.media.sound.InvalidDataException;

import java.util.*;


/**
 * Created by gizmo on 15/6/17.
 */
//招商银行小企业E家抓取
public class ZHXQYEJJob extends TLCSpiderJob {
    //detail: https://ba.cmbchinaucs.com/FinanDet.aspx?FinancingId=c5af372b-cc4d-4d1d-9002-be58734ae996

    private static final String URL_PRODUCT_LIST = PropertiesUtil.getResource("tlc.spider.zhxqyej.url.list");
    private static final String SID = PropertiesUtil.getResource("tlc.spider.zhxqyej.sid");
    private static final String TOKEN = PropertiesUtil.getResource("tlc.spider.zhxqyej.token");
    private static final String JOB_TITLE = PropertiesUtil.getResource("tlc.spider.zhxqyej.title");

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
        param.put("PageSize", TLCSpiderConstants.SPIDER_PAGE_SIZE_GET);
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
    public List<TransObject> getSpiderDataList(Map<String, String> param) {
        String productContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        String productJsonStr = JsonUtil.getString(productContent, "DicData");
        List<ZHXQYEJ> productList = JsonUtil.json2Array(productJsonStr, "NormalList", ZHXQYEJ.class);

        List<TransObject> transObjectList = new ArrayList();
        for(ZHXQYEJ product : productList) {
            TransObject transObject = convertToTransObject(product);
            transObjectList.add(transObject);
        }

        return transObjectList;
    }

    private TransObject convertToTransObject(ZHXQYEJ product) {
        TransObject transObject = new TransObject();
        transObject.setFinancingId(product.getFinancingId());
        transObject.setProjectCode(product.getProjectCode());
        transObject.setProjectName(product.getProjectName());
        transObject.setBindUserId(product.getBindUserId());
        transObject.setBindUserName(product.getBindUserName());
        transObject.setBindCompanyId(product.getBindCompanyId());
        transObject.setBindCompanyName(product.getBindCompanyName());
        transObject.setAmount(product.getAmount());
        transObject.setPartsCount(product.getMinInvestPartsCount());
        transObject.setBankInterest(product.getBankInterest());
        transObject.setInvestmentInterest(product.getInvestmentInterest());
        transObject.setDuration(product.getDuration());
        transObject.setRepayType(product.getRepayType());
        transObject.setValueBegin(product.getValueBegin());
        transObject.setRepayBegin(product.getRepayBegin());
        transObject.setRepaySourceType(product.getRepaySourceType());
        transObject.setProjectBeginTime(product.getProjectBeginTime());
        transObject.setReadyBeginTime(product.getReadyBeginTime());
        transObject.setProjectStatus(product.getProjectStatus());
        transObject.setCreditLevel(product.getCreditLevel());
        transObject.setCreateUserId(product.getCreateUserId());
        transObject.setCreateCompanyId(product.getCreateCompanyId());
        transObject.setJmBeginTime(product.getjMBeginTime());
        transObject.setAreaCode(product.getAreaCode());
        transObject.setCreateTime(product.getCreateTime());
        transObject.setUpdateUserId(product.getUpdateUserId());
        transObject.setUpdateTime(product.getUpdateTime());
        transObject.setCreateUserName(product.getCreateUserName());
        transObject.setCreateCompanyName(product.getCreateCompanyName());
        transObject.setIsShow(product.getIsShow());
        transObject.setProjectType(product.getProjectType());
        transObject.setIsExclusivePublic(product.getIsExclusivePublic());
        transObject.setMinInvestPartsCount(product.getMinInvestPartsCount());
        transObject.setExclusiveCode(product.getExclusiveCode());
        transObject.setLcAmount(product.getlCAmount());
        transObject.setICount(product.getiCount());
        transObject.setIAmount(product.getiAmount());
        transObject.setRealProgress(product.getRealProgress());
        transObject.setProgress(product.getProgress());
        transObject.setFinanceApplyStatus(product.getFinanceApplyStatus());
        transObject.setHotStatus(product.getHotStatus());
        transObject.setDbType(product.getDbType());
        return transObject;
    }
}
