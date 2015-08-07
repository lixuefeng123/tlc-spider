package cn.com.fero.tlc.spider.http.test;

import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.vo.p2p.ZHEFP;
import net.sf.ezmorph.bean.MorphDynaBean;
import org.junit.Test;

import java.util.List;

/**
 * Created by gizmo on 15/8/6.
 */
public class TLCSpiderJsonUtilTest {

    @Test
    public void testJsonConvert() {
        String str = "{\"Data\":null,\"ResultCode\":null,\"DicData\":{\"EFPList\":[{\"ProjectId\":\"15060ed0-9b4e-484f-b5a1-e971a1a12bd3\",\"RealProjectId\":\"15060ed0-9b4e-484f-b5a1-e971a1a12bd3\",\"ProjectCode\":\"EFP201508061022111644\",\"ProjectName\":\"e+稳健融资项目【18620150229】\",\"ProjectAmount\":16750200.00,\"ValueBegin\":\"2015-08-07 00:00:00\",\"BeginTime\":\"2015-08-06 00:00:00\",\"EndTime\":\"2015-08-07 14:00:00\",\"InStatus\":0,\"ProjectCategory\":\"EFP\",\"OrderCfgList\":[{\"InterestRate\":4.50,\"RemaindAmount\":249200.00,\"RemainPartsCount\":2492,\"ValueBegin\":\"2015-08-07 00:00:00\",\"RepayBegin\":\"2015-08-14 00:00:00\",\"Duration\":7}],\"IsExclusive\":true,\"HasAward\":false,\"IsInPartOpen\":false},{\"ProjectId\":\"ff8157e5-a9bb-4570-b08b-636981421fcc\",\"RealProjectId\":\"ff8157e5-a9bb-4570-b08b-636981421fcc\",\"ProjectCode\":\"EFP201508061020509116\",\"ProjectName\":\"e+稳健融资项目【66620150226】\",\"ProjectAmount\":25144200.00,\"ValueBegin\":\"2015-08-07 00:00:00\",\"BeginTime\":\"2015-08-06 00:00:00\",\"EndTime\":\"2015-08-07 14:00:00\",\"InStatus\":0,\"ProjectCategory\":\"EFP\",\"OrderCfgList\":[{\"InterestRate\":4.50,\"RemaindAmount\":25144200.00,\"RemainPartsCount\":251442,\"ValueBegin\":\"2015-08-07 00:00:00\",\"RepayBegin\":\"2015-08-14 00:00:00\",\"Duration\":7}],\"IsExclusive\":true,\"HasAward\":false,\"IsInPartOpen\":false},{\"ProjectId\":\"49201917-790d-4652-a00a-b92bee42be1c\",\"RealProjectId\":\"49201917-790d-4652-a00a-b92bee42be1c\",\"ProjectCode\":\"EFP201508061134341080\",\"ProjectName\":\"e+稳健融资项目【75820150248】\",\"ProjectAmount\":3892100.00,\"ValueBegin\":\"2015-08-07 00:00:00\",\"BeginTime\":\"2015-08-06 00:00:00\",\"EndTime\":\"2015-08-07 14:00:00\",\"InStatus\":1,\"ProjectCategory\":\"EFP\",\"OrderCfgList\":[{\"InterestRate\":4.50,\"RemaindAmount\":0.00,\"RemainPartsCount\":0,\"ValueBegin\":\"2015-08-07 00:00:00\",\"RepayBegin\":\"2015-08-14 00:00:00\",\"Duration\":7}],\"IsExclusive\":true,\"HasAward\":false,\"IsInPartOpen\":false},{\"ProjectId\":\"570b5528-2962-4054-8aaa-6e561d05517b\",\"RealProjectId\":\"570b5528-2962-4054-8aaa-6e561d05517b\",\"ProjectCode\":\"EFP201508061102034403\",\"ProjectName\":\"e+稳健融资项目【13720150454】\",\"ProjectAmount\":2784600.00,\"ValueBegin\":\"2015-08-07 00:00:00\",\"BeginTime\":\"2015-08-06 00:00:00\",\"EndTime\":\"2015-08-07 14:00:00\",\"InStatus\":1,\"ProjectCategory\":\"EFP\",\"OrderCfgList\":[{\"InterestRate\":4.60,\"RemaindAmount\":0.00,\"RemainPartsCount\":0,\"ValueBegin\":\"2015-08-07 00:00:00\",\"RepayBegin\":\"2015-08-18 00:00:00\",\"Duration\":11}],\"IsExclusive\":false,\"HasAward\":false,\"IsInPartOpen\":false},{\"ProjectId\":\"ee640c4b-428f-419e-bd0a-a43a92a0699f\",\"RealProjectId\":\"ee640c4b-428f-419e-bd0a-a43a92a0699f\",\"ProjectCode\":\"EFP201508061029276172\",\"ProjectName\":\"e+稳健融资项目【94720150079】\",\"ProjectAmount\":4647300.00,\"ValueBegin\":\"2015-08-07 00:00:00\",\"BeginTime\":\"2015-08-06 00:00:00\",\"EndTime\":\"2015-08-07 14:00:00\",\"InStatus\":1,\"ProjectCategory\":\"EFP\",\"OrderCfgList\":[{\"InterestRate\":4.42,\"RemaindAmount\":0.00,\"RemainPartsCount\":0,\"ValueBegin\":\"2015-08-07 00:00:00\",\"RepayBegin\":\"2015-09-11 00:00:00\",\"Duration\":35}],\"IsExclusive\":false,\"HasAward\":false,\"IsInPartOpen\":false},{\"ProjectId\":\"9895e463-3a27-47cb-aa6e-50373e3ff2a5\",\"RealProjectId\":\"9895e463-3a27-47cb-aa6e-50373e3ff2a5\",\"ProjectCode\":\"EFP201508061028265467\",\"ProjectName\":\"e+稳健融资项目【55420150078】\",\"ProjectAmount\":7792600.00,\"ValueBegin\":\"2015-08-07 00:00:00\",\"BeginTime\":\"2015-08-06 00:00:00\",\"EndTime\":\"2015-08-07 14:00:00\",\"InStatus\":1,\"ProjectCategory\":\"EFP\",\"OrderCfgList\":[{\"InterestRate\":4.43,\"RemaindAmount\":0.00,\"RemainPartsCount\":0,\"ValueBegin\":\"2015-08-07 00:00:00\",\"RepayBegin\":\"2015-09-10 00:00:00\",\"Duration\":34}],\"IsExclusive\":false,\"HasAward\":false,\"IsInPartOpen\":false},{\"ProjectId\":\"94ddbe2f-5c7f-4498-9719-127358dd5a04\",\"RealProjectId\":\"94ddbe2f-5c7f-4498-9719-127358dd5a04\",\"ProjectCode\":\"EFP201508061022539727\",\"ProjectName\":\"e+稳健融资项目【99120150231】\",\"ProjectAmount\":19636900.00,\"ValueBegin\":\"2015-08-07 00:00:00\",\"BeginTime\":\"2015-08-06 00:00:00\",\"EndTime\":\"2015-08-07 14:00:00\",\"InStatus\":1,\"ProjectCategory\":\"EFP\",\"OrderCfgList\":[{\"InterestRate\":4.50,\"RemaindAmount\":0.00,\"RemainPartsCount\":0,\"ValueBegin\":\"2015-08-07 00:00:00\",\"RepayBegin\":\"2015-08-14 00:00:00\",\"Duration\":7}],\"IsExclusive\":true,\"HasAward\":false,\"IsInPartOpen\":false},{\"ProjectId\":\"e1838bae-1ae2-491a-947e-e28b9aa65bf1\",\"RealProjectId\":\"e1838bae-1ae2-491a-947e-e28b9aa65bf1\",\"ProjectCode\":\"EFP201508061022319563\",\"ProjectName\":\"e+稳健融资项目【94620150230】\",\"ProjectAmount\":3680000.00,\"ValueBegin\":\"2015-08-07 00:00:00\",\"BeginTime\":\"2015-08-06 00:00:00\",\"EndTime\":\"2015-08-07 14:00:00\",\"InStatus\":1,\"ProjectCategory\":\"EFP\",\"OrderCfgList\":[{\"InterestRate\":4.50,\"RemaindAmount\":0.00,\"RemainPartsCount\":0,\"ValueBegin\":\"2015-08-07 00:00:00\",\"RepayBegin\":\"2015-08-14 00:00:00\",\"Duration\":7}],\"IsExclusive\":true,\"HasAward\":false,\"IsInPartOpen\":false},{\"ProjectId\":\"15060ed0-9b4e-484f-b5a1-e971a1a12bd3\",\"RealProjectId\":\"15060ed0-9b4e-484f-b5a1-e971a1a12bd3\",\"ProjectCode\":\"EFP201508061022111644\",\"ProjectName\":\"e+稳健融资项目【97520150229】\",\"ProjectAmount\":880000.00,\"ValueBegin\":\"2015-08-07 00:00:00\",\"BeginTime\":\"2015-08-06 00:00:00\",\"EndTime\":\"2015-08-07 14:00:00\",\"InStatus\":1,\"ProjectCategory\":\"EFP\",\"OrderCfgList\":[{\"InterestRate\":4.50,\"RemaindAmount\":0.00,\"RemainPartsCount\":0,\"ValueBegin\":\"2015-08-07 00:00:00\",\"RepayBegin\":\"2015-08-14 00:00:00\",\"Duration\":7}],\"IsExclusive\":false,\"HasAward\":false,\"IsInPartOpen\":true},{\"ProjectId\":\"69ea9320-e1de-4aef-9358-986b56d4d050\",\"RealProjectId\":\"69ea9320-e1de-4aef-9358-986b56d4d050\",\"ProjectCode\":\"EFP201508061021438486\",\"ProjectName\":\"e+稳健融资项目【24820150228】\",\"ProjectAmount\":29748700.00,\"ValueBegin\":\"2015-08-07 00:00:00\",\"BeginTime\":\"2015-08-06 00:00:00\",\"EndTime\":\"2015-08-07 14:00:00\",\"InStatus\":1,\"ProjectCategory\":\"EFP\",\"OrderCfgList\":[{\"InterestRate\":4.50,\"RemaindAmount\":0.00,\"RemainPartsCount\":0,\"ValueBegin\":\"2015-08-07 00:00:00\",\"RepayBegin\":\"2015-08-14 00:00:00\",\"Duration\":7}],\"IsExclusive\":true,\"HasAward\":false,\"IsInPartOpen\":false}],\"TotalCount\":133,\"TotalPage\":14,\"CTBList\":null},\"IsSuccess\":true,\"IsDone\":true,\"IsSysError\":false,\"Message\":\"获取首页项目列表操作成功\",\"Action\":\"获取首页项目列表\",\"ResultType\":0}";
        String dic = TLCSpiderJsonUtil.getString(str, "DicData");
        List<ZHEFP> list = TLCSpiderJsonUtil.json2Array(dic, "EFPList", ZHEFP.class);
        for (ZHEFP zhefp : list) {
            List configList = zhefp.getOrderCfgList();
            MorphDynaBean m = (MorphDynaBean) configList.get(0);
            String s = (String) m.get("RemaindAmount");
            System.out.println(s);
        }
    }

    @Test
    public void testNumberConvert() {
        String str = "1.67502E7";
        Double numner = Double.parseDouble(str);
        System.out.println(numner.intValue());
    }
}