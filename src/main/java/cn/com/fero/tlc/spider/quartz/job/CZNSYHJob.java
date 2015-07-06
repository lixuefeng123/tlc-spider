package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.quartz.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.JsonUtil;
import cn.com.fero.tlc.spider.util.LoggerUtil;
import cn.com.fero.tlc.spider.vo.CZNSYH;
import cn.com.fero.tlc.spider.vo.TransObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//长子农商银行长青融E贷抓取
public class CZNSYHJob extends TLCSpiderJob {
    private static final String URL_PRODUCT_LIST = "https://ryd.zznsyh.cn/client/rest/home/data?index=1&size=1000&orderMap%5BpresaleDateEnd%5D=DESC&_=1435210991402";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LoggerUtil.getLogger().info("开始抓取长子农商银行长青融E贷");

        String content = TLCSpiderRequest.get(URL_PRODUCT_LIST);
        String dataStr = JsonUtil.getString(content, "data");
        List<CZNSYH> cznsyhList = JsonUtil.json2Array(dataStr, "data", CZNSYH.class);
        List<TransObject> transObjectList = new ArrayList();
//
//        {
//            "id": "0a41300b-198f-11e5-8d67-38eaa78ddb5d",
//            "name": "长青e贷稳盈融资项目",
//            "borrowerId": "0a2b4b32-198f-11e5-8d67-38eaa78ddb5d",
//            "number": "【CQWY20150006】",
//            "typeId": "2",
//            "increaseTypeId": "1",
//            "originAgency": "经营收入",
//            "amount": 100,
//            "yieldRate": 7.2,
//            "minInvestAmount": 100,
//            "sellLimit": 10000,
//            "yieldDays": 1,
//            "loanPeriod": 298,
//            "presaleDateStart": 1435054713000,
//            "presaleDateEnd": 1435060800000,
//            "tenderDateStart": 1435060800000,
//            "tenderDateEnd": 1435276800000,
//            "status": "2",
//            "loanDate": null,
//            "repaymentDate": null,
//            "settlementDate": null,
//            "createTime": 1435053784000,
//            "updateTime": 1435054557000,
//            "projectFinancle": {
//                "id": "23dd366d-19a0-11e5-8d67-38eaa78ddb5d",
//                "projectId": "0a41300b-198f-11e5-8d67-38eaa78ddb5d",
//                "financleAmount": 999500,
//                "createTime": 1435061128000,
//                "updateTime": 1435121517000
//            },
//            "remainMoney": 500,
//            "percent": 100,
//            "realStatus": "2",
//            "interestDay": null
//        }

        for (CZNSYH cznsyh : cznsyhList) {
            TransObject transObject = new TransObject();
            //TODO 转换
            transObjectList.add(transObject);
        }

        print(cznsyhList);
    }

    @Override
    protected Map<String, String> constructPostParam(List<TransObject> transObjectList) {
        return null;
    }

    @Override
    public void doExecute(Map<String, TransObject> updateMap) throws Exception {

    }
}
