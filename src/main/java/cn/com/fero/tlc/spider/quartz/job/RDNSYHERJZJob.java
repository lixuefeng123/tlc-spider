package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.quartz.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.DateFormatUtil;
import cn.com.fero.tlc.spider.util.JsonUtil;
import cn.com.fero.tlc.spider.util.PropertiesUtil;
import cn.com.fero.tlc.spider.vo.RDNSYHERJZ;
import cn.com.fero.tlc.spider.vo.TransObject;
import com.sun.media.sound.InvalidDataException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//尧都农商银行E融九州抓取
public class RDNSYHERJZJob extends TLCSpiderJob {

    private static final String URL_PRODUCT_LIST = PropertiesUtil.getResource("tlc.spider.rdnsyherjz.url.list");
    private static final String SID = PropertiesUtil.getResource("tlc.spider.rdnsyherjz.sid");
    private static final String TOKEN = PropertiesUtil.getResource("tlc.spider.rdnsyherjz.token");
    private static final String JOB_TITLE = PropertiesUtil.getResource("tlc.spider.rdnsyherjz.title");

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
        param.put("PageSize", TLCSpiderConstants.SPIDER_PAGE_SIZE_GET);
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
        int totalCountNum = Integer.parseInt(totalCount) % pageSize == 0 ? Integer.parseInt(totalCount) / pageSize : (Integer.parseInt(totalCount) / pageSize + 1);
        return totalCountNum;
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
        String productContent = TLCSpiderRequest.post(URL_PRODUCT_LIST, param);
        String productJsonStr = JsonUtil.getString(productContent, "Data");
        List<RDNSYHERJZ> productList = JsonUtil.json2Array(productJsonStr, "ResultList", RDNSYHERJZ.class, "YMInterest");

        List<TransObject> transObjectList = new ArrayList();
        for(RDNSYHERJZ product : productList) {
            TransObject transObject = convertToTransObject(product);
            transObjectList.add(transObject);
        }

        return transObjectList;
    }

    private TransObject convertToTransObject(RDNSYHERJZ product) {
        TransObject transObject = new TransObject();
        transObject.setFinancingId(product.getFinancingId());
        transObject.setProjectCode(product.getProjectCode());
        transObject.setProjectName(product.getProjectName());
        transObject.setBindUserId(product.getBindUserId());
        transObject.setBindUserName(product.getBindUserName());
        transObject.setBindCompanyId(product.getBindCompanyId());
        transObject.setBindCompanyName(product.getBindCompanyName());
        transObject.setAmount(product.getAmount());
        transObject.setPartsCount(product.getPartsCount());
        transObject.setBankInterest(product.getBankInterest());
        transObject.setInvestmentInterest(product.getInvestmentInterest());
        transObject.setDuration(product.getDuration());
        transObject.setRepayType(product.getRepayType());
        transObject.setValueBegin(DateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getValueBegin()));
        transObject.setRepayBegin(DateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getRepayBegin()));
        transObject.setProjectBeginTime(DateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getProjectBeginTime()));
        transObject.setReadyBeginTime(DateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getReadyBeginTime()));
        transObject.setProjectStatus(product.getProjectStatus());
        transObject.setJmBeginTime(DateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getjMBeginTime()));
        transObject.setCreateTime(DateFormatUtil.formatDateTime("MM/dd/yyyy HH:mm:ss", product.getCreateTime()));
        transObject.setIsShow(product.getIsShow());
        transObject.setProjectType(product.getProjectType());
        transObject.setIsExclusivePublic(product.getIsExclusivePublic());
        transObject.setMinInvestPartsCount(product.getMinInvestPartsCount());
        transObject.setExclusiveCode(product.getExclusiveCode());
        transObject.setRealProgress(product.getRealProgress());
        transObject.setProgress(product.getProgress());
        transObject.setFinanceApplyStatus(product.getFinanceApplyStatus());
        transObject.setHotStatus(product.getHotStatus());
        //TODO 未处理属性 YMInterest: 0.5
        return transObject;
    }
}
