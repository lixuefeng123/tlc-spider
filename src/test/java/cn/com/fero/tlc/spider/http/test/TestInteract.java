package cn.com.fero.tlc.spider.http.test;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.vo.TransObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghongmeng on 2015/6/29.
 */
public class TestInteract {
    @Test
    public void testGetUpdateList() {
        Map<String, String> map = new HashMap();
        map.put("sid", "1");
        map.put("token", "m88l48Mguil4+F9ilbodCID5MFnHl30cQmzVJ7FeAmOV");
        Object obj = TLCSpiderRequest.post("http://192.168.2.19:3005/spiderapi/p2p/updatelist", map);
        System.out.println(obj);
    }

    @Test
    public void testExtract() {
        String result = "{\n" +
                "status: 200,\n" +
                "data: [\n" +
                "{\n" +
                "IAmount: \"\",\n" +
                "ICount: \"\",\n" +
                "amount: \"2000000\",\n" +
                "areaCode: \"\",\n" +
                "bankInterest: \"2\",\n" +
                "bindCompanyId: \"0baff93b-71c9-40cf-b62f-cf791e984f09\",\n" +
                "bindCompanyName: \"user996932\",\n" +
                "bindUserId: \"0baff93b-71c9-40cf-b62f-cf791e984f09\",\n" +
                "bindUserName: \"13834582399\",\n" +
                "content: \"\",\n" +
                "createCompanyId: \"\",\n" +
                "createCompanyName: \"\",\n" +
                "createTime: \"2015-04-29 10:39:10\",\n" +
                "createUserId: \"\",\n" +
                "createUserName: \"\",\n" +
                "creditLevel: \"\",\n" +
                "dbType: \"\",\n" +
                "duration: \"180\",\n" +
                "exclusiveCode: \"\",\n" +
                "financeApplyStatus: \"5\",\n" +
                "financingId: \"d8cb6109-058f-4632-8dc2-4b558edb9c81\",\n" +
                "hotStatus: \"9\",\n" +
                "id: 921,\n" +
                "investmentInterest: \"7\",\n" +
                "isExclusivePublic: \"false\",\n" +
                "isLimitCount: \"\",\n" +
                "isShow: \"true\",\n" +
                "jmBeginTime: \"2015-05-05 14:30:00\",\n" +
                "lcAmount: \"\",\n" +
                "minInvestPartsCount: \"1\",\n" +
                "partsCount: \"2000\",\n" +
                "progress: \"1\",\n" +
                "projectBeginTime: \"2015-04-30 00:00:00\",\n" +
                "projectCode: \"PRJ201504291039106188\",\n" +
                "projectName: \"e融安稳融资项目【TY20150002】\",\n" +
                "projectStatus: \"6\",\n" +
                "projectType: \"0\",\n" +
                "readyBeginTime: \"2015-04-30 00:00:00\",\n" +
                "realProgress: \"1\",\n" +
                "repayBegin: \"2015-11-02 00:00:00\",\n" +
                "repaySourceType: \"\",\n" +
                "repayType: \"0\",\n" +
                "title: \"\",\n" +
                "updateTime: \"\",\n" +
                "updateUserId: \"\",\n" +
                "valueBegin: \"2015-05-06 00:00:00\"\n" +
                "}\n" +
                "],\n" +
                "message: \"发送成功\"\n" +
                "}\n";

        String status = TLCSpiderJsonUtil.getString(result, TLCSpiderConstants.SPIDER_PARAM_STATUS_NAME);
        if (!TLCSpiderConstants.SPIDER_PARAM_STATUS_SUCCESS_CODE.equals(status)) {
            throw new IllegalStateException(result);
        }

        List<TransObject> updateList = TLCSpiderJsonUtil.json2Array(result, TLCSpiderConstants.SPIDER_PARAM_DATA, TransObject.class);
        for (TransObject transObject : updateList) {
            System.out.println(transObject);
        }
    }
}
