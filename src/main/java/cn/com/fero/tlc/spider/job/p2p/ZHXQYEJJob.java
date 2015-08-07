package cn.com.fero.tlc.spider.job.p2p;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.vo.p2p.TransObject;
import cn.com.fero.tlc.spider.vo.p2p.ZHEFP;
import cn.com.fero.tlc.spider.vo.p2p.ZHNORMAL;
import net.sf.ezmorph.bean.MorphDynaBean;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//招商银行小企业E家抓取
public class ZHXQYEJJob extends TLCSpiderJob {
    //detail: https://ba.cmbchinaucs.com/FinanDet.aspx?FinancingId=c5af372b-cc4d-4d1d-9002-be58734ae996

    private static final String URL_PRODUCT_LIST = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.zhxqyej.url.list");
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.zhxqyej.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.zhxqyej.token");
    private static final String JOB_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.zhxqyej.title");
    private static final String PAGE_NAME = "PageIndex";
    private static final String PAGE_SIZE = "10";

    @Override
    public Map<String, String> constructSystemInteractiveParam() {
        Map<String, String> param = new HashMap();
        param.put(TLCSpiderConstants.SPIDER_PARAM_STATUS_NAME, TLCSpiderConstants.SPIDER_PARAM_STATUS_SUCCESS_CODE);
        param.put(TLCSpiderConstants.SPIDER_PARAM_SID, SID);
        param.put(TLCSpiderConstants.SPIDER_PARAM_TOKEN, TOKEN);
        param.put(TLCSpiderConstants.SPIDER_CONST_JOB_TITLE, JOB_TITLE);
        param.put(TLCSpiderConstants.SPIDER_PARAM_PAGE_NAME, PAGE_NAME);
        return param;
    }

    @Override
    public Map<String, String> constructSpiderFetchParam() {
        Map<String, String> param = new HashMap();
        param.put(PAGE_NAME, TLCSpiderConstants.SPIDER_PARAM_PAGE_ONE);
        param.put("PageSize", PAGE_SIZE);
        param.put("TargetAction", "GetProjectList_Index");
        param.put("Sort", "normal");
        return param;
    }

    @Override
    public int getTotalPage(Map<String, String> param) {
        String pageContent = TLCSpiderRequest.postViaProxy(URL_PRODUCT_LIST, param, TLCSpiderRequest.ProxyType.HTTPS);
        String pageStr = TLCSpiderJsonUtil.getString(pageContent, "DicData");
        String totalPage = TLCSpiderJsonUtil.getString(pageStr, "TotalPage");
        return Integer.parseInt(totalPage);
    }

    @Override
    public List<TransObject> getSpiderDataList(Map<String, String> param) {
        String productContent = TLCSpiderRequest.postViaProxy(URL_PRODUCT_LIST, param, TLCSpiderRequest.ProxyType.HTTPS);
        String productJsonStr = TLCSpiderJsonUtil.getString(productContent, "DicData");

        List<TransObject> transObjectList = new ArrayList();
        String efp = "EFPList";
        String normal = "NormalList";

        if (productJsonStr.contains(efp)) {
//            List<ZHEFP> productList = TLCSpiderJsonUtil.json2Array(productJsonStr, efp, ZHEFP.class);
//            for (ZHEFP product : productList) {
//                TransObject transObject = convertToTransObject(product);
//                transObjectList.add(transObject);
//            }
        } else if (productJsonStr.contains(normal)) {
            List<ZHNORMAL> productList = TLCSpiderJsonUtil.json2Array(productJsonStr, normal, ZHNORMAL.class);
            for (ZHNORMAL product : productList) {
                TransObject transObject = convertToTransObject(product);
                transObjectList.add(transObject);
            }
        }

        return transObjectList;
    }

    private TransObject convertToTransObject(ZHNORMAL product) {
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
        transObject.setiCount(product.getiCount());
        transObject.setiAmount(product.getiAmount());
        transObject.setRealProgress(product.getRealProgress());
        transObject.setProgress(product.getProgress());
        transObject.setFinanceApplyStatus(product.getFinanceApplyStatus());
        transObject.setHotStatus(product.getHotStatus());
        transObject.setDbType(product.getDbType());
        return transObject;
    }

    private TransObject convertToTransObject(ZHEFP product) {
        TransObject transObject = new TransObject();
        transObject.setFinancingId(product.getProjectId());
        transObject.setProjectCode(product.getProjectCode());
        transObject.setProjectName(product.getProjectName());

        Double amount = Double.parseDouble(product.getProjectAmount());
        transObject.setAmount(String.valueOf(amount.intValue()));

        if (CollectionUtils.isNotEmpty(product.getOrderCfgList())) {
            MorphDynaBean dynaBean = (MorphDynaBean) product.getOrderCfgList().get(0);

            Double remainAmount = Double.parseDouble(dynaBean.get("RemaindAmount").toString());
            int remainAmountNum = remainAmount.intValue();
            Double remainPartsCount = Double.parseDouble(dynaBean.get("RemainPartsCount").toString());
            int remainPartsCountNum = remainPartsCount.intValue();
            if (remainPartsCountNum > 0) {
                int minInvestAmount = remainAmountNum / remainPartsCountNum;
                int partsCount = Integer.parseInt(transObject.getAmount()) / minInvestAmount;
                transObject.setPartsCount(String.valueOf(partsCount));
            }


            transObject.setInvestmentInterest(dynaBean.get("InterestRate").toString());
            transObject.setValueBegin(dynaBean.get("ValueBegin").toString());
            transObject.setRepayBegin(dynaBean.get("RepayBegin").toString());
            transObject.setDuration(dynaBean.get("Duration").toString());
        }

        transObject.setProjectBeginTime(product.getBeginTime());
        transObject.setReadyBeginTime(product.getBeginTime());
        transObject.setProjectStatus(product.getInStatus());
        transObject.setProjectType(product.getProjectCategory());
        transObject.setIsExclusivePublic(product.getIsExclusive());
        transObject.setRealProgress(TLCSpiderConstants.SPIDER_CONST_FULL_PROGRESS);
        transObject.setProgress(TLCSpiderConstants.SPIDER_CONST_FULL_PROGRESS);
        return transObject;
    }
}
