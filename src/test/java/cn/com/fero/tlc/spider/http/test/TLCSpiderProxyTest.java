package cn.com.fero.tlc.spider.http.test;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderProxyUtil;
import cn.com.fero.tlc.spider.vo.p2p.RequestProxy;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gizmo on 15/6/19.
 */
public class TLCSpiderProxyTest {

    @Test
    public void testProxy() {
        Map<String, String> map = new HashMap();
        map.put("ip", "myip");
        String response = TLCSpiderRequest.postViaProxy("http://ip.taobao.com/service/getIpInfo2.php", map, TLCSpiderRequest.ProxyType.HTTP);
        System.out.println(response);
    }

//
//    public String postViaProxy(String url, Map<String, String> param, TLCSpiderRequest.ProxyType proxyType) {
//        try {
//            RequestProxy requestProxy = TLCSpiderProxyUtil.getHttpsProxy();
//            RequestConfig.Builder builder = RequestConfig.custom();
//            builder.setConnectTimeout(TLCSpiderConstants.SPIDER_CONST_HTTP_TIMEOUT)
//                    .setSocketTimeout(TLCSpiderConstants.SPIDER_CONST_HTTP_TIMEOUT);
//            builder.setProxy(new HttpHost(requestProxy.getIp(), requestProxy.getPort()));
//            RequestConfig config = builder.build();
//
//            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
////            CloseableHttpClient httpClient = HttpClients.createDefault();
//            HttpPost httpPost = new HttpPost(url);
//
//            List<NameValuePair> paramList = new ArrayList();
//            for (Map.Entry<String, String> entry : param.entrySet()) {
//                paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//            }
//            httpPost.setEntity(new UrlEncodedFormEntity(paramList, CharsetUtils.get(TLCSpiderConstants.SPIDER_CONST_ENCODING)));
//
////            httpPost.setConfig(config);
//            CloseableHttpResponse response = httpClient.execute(httpPost);
//            return EntityUtils.toString(response.getEntity(), TLCSpiderConstants.SPIDER_CONST_ENCODING);
//        } catch (Exception e) {
//            TLCSpiderLoggerUtil.getLogger().error("使用{}发生异常，去除代理重新请求", proxyType.toString());
//            return null;
//        }
//    }

    @Test
    public void testGetHttpProxy() {
        RequestProxy requestProxy = TLCSpiderProxyUtil.getHttpProxy();
        System.out.println(requestProxy);
    }

    @Test
    public void testGetHttpsProxy() {
        RequestProxy requestProxy = TLCSpiderProxyUtil.getHttpsProxy();
        System.out.println(requestProxy);
    }


    @Test
    public void testHttpsProxy() {
        String url = TLCSpiderPropertiesUtil.getResource("tlc.spider.p2p.rdnsyherjz.url.list");

        Map<String, String> param = new HashMap();
        param.put("PageIndex", TLCSpiderConstants.SPIDER_PARAM_PAGE_ONE);
        param.put("PageSize", "10");
        param.put("targetAction", "CmbFinancingSearch");
        param.put("Interest", "");
        param.put("Duration", "");
        param.put("ProjectStatus", "");
        param.put("ProjectAmount", "");

        String countContent = TLCSpiderRequest.postViaProxy(url, param, TLCSpiderRequest.ProxyType.HTTPS);
//        String countContent = TLCSpiderRequest.post(url, param);
        String countStr = TLCSpiderJsonUtil.getString(countContent, "Data");
        String totalCount = TLCSpiderJsonUtil.getString(countStr, "TotalCount");
        System.out.println(totalCount);
    }

    @Test
    public void testGetUpdateArticle() {
        String updateUrl = "http://tailicaiop.fero.com.cn/spiderapi/article/source";
        String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.article.source.sid");
        String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.article.source.token");

        Map<String, String> param = new HashMap();
        param.put(TLCSpiderConstants.SPIDER_PARAM_SID, SID);
        param.put(TLCSpiderConstants.SPIDER_PARAM_TOKEN, TOKEN);

        String response = TLCSpiderRequest.post(updateUrl, param);
        System.out.println(response);
    }

    @Test
    public void testGetArticle() throws InterruptedException {
        String url = "http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=oIWsFtzcuQtoMO739-mwrqoaWPi4&eqs=pLsqoWtgfIG6osjbygAtCud8xqO5CwnfW%2FMb%2F1qtjEICoi1ZVmfZcmuCOexPe1wuwFIBJ&ekv=7";
        Map<String, String> head = new HashMap();
        head.put("Cookie", "CXID=C2D908DCA2484D12F57C0A1D143A7B66; SUID=97017D7B142D900A55B5C349000477E0; SUV=1507271026258805; ABTEST=0|1438588933|v1; SNUID=C9EE0F0001071D6194556AA302CBA565; ad=Iyllllllll2qHt2JlllllVQ@JAZlllllWT1xOZllll9llllllCxlw@@@@@@@@@@@; IPLOC=CN1100");
        String articleContent = TLCSpiderRequest.getViaProxy(url, TLCSpiderRequest.ProxyType.HTTP, head);
        System.out.println(articleContent);
    }

    @Test
    public void testPorxy() throws InterruptedException {
        final AtomicInteger count = new AtomicInteger(0);
        for (int a = 0; a < 100; a++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    TLCSpiderRequest.getViaProxy("http://119.29.62.227:9999", TLCSpiderRequest.ProxyType.HTTP);
                }
            }).start();
        }

        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void testSocket() {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println(socket.getInetAddress().getHostAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSplit() {
        String str = "\n" +
                "sogou.weixin.gzhcb({\"totalItems\":488,\"totalPages\":10,\"page\":1,\"items\":[\"<?xml version=\\\"1.0\\\" encoding=\\\"gbk\\\"?><DOCUMENT><docid><\\/docid><item> <key><![CDATA[\uE468http://mp.weixin.qq.com/]]><\\/key><tplid><![CDATA[555]]><\\/tplid><classid>11002601<\\/classid> <display> <docid>ab735a258a90e8e1-6bee54fcbd896b2a-cc019d0833835ba12e77624c352bc77a<\\/docid> <tplid>550<\\/tplid><title><![CDATA[太平洋产险接入央行征信系统]]><\\/title><url><![CDATA[http://mp.weixin.qq.com/s?__biz=MzAwMTMwMzQwOA==&mid=208437359&idx=3&sn=3752a57b939c80997bf5de59dae18c4a&3rd=MzA3MDU4NTYzMw==&scene=6#rd]]><\\/url><title1><![CDATA[太平洋产险接入央行征信系统]]><\\/title1><imglink><![CDATA[http://mmbiz.qpic.cn/mmbiz/VY5nQhmoHlehvRPEFYplpRV21iaibFE4fic3icY4qfzmc7CxUD8vlPicqic37WZzlgmjQesdlj7atiazicKxJVIIlrLIXA/0?wx_fmt=jpeg]]><\\/imglink><headimage><![CDATA[http://wx.qlogo.cn/mmhead/Q3auHgzwzM4trgxuedYg2SUtfknZibK7iarW5ysiaiaAH25MYPlZ6vy1hw/0]]><\\/headimage><sourcename><![CDATA[人人征信]]><\\/sourcename><content168><![CDATA[日前,太平洋保险(601601,股吧)与中国人民银行征信中心在北京签署合作协议,双方就太平洋产险接入央行征信系统开展合作.中国人民银行征信中心副主任汪路和太平洋产险非车险业务总监孙海洋作为双方代表,签署相关协议.  据悉,双方的合作是贯彻落实保监会、人民银行等五部委...]]><\\/content168><isV><![CDATA[1]]><\\/isV><openid><![CDATA[oIWsFt4qB1pL53pFpe1w7f-nx05Q]]><\\/openid><content><![CDATA[日前,太平洋保险(601601,股吧)与中国人民银行征信中心在北京签署合作协议,双方就太平洋产险接入央行征信系统开展合作....]]><\\/content><showurl><![CDATA[微信 - mp.weixin.qq.com]]><\\/showurl><date><![CDATA[2015-8-4]]><\\/date><pagesize><![CDATA[18k]]><\\/pagesize> <lastModified>1438658280<\\/lastModified><pagesize>19k<\\/pagesize> <\\/display><\\/item><\\/DOCUMENT>\",\"<?xml version=\\\"1.0\\\" encoding=\\\"gbk\\\"?><DOCUMENT><docid><\\/docid><item> <key><![CDATA[\uE468http://mp.weixin.qq.com/]]><\\/key><tplid><![CDATA[555]]><\\/tplid><classid>11002601<\\/classid> <display> <docid>ab735a258a90e8e1-6bee54fcbd896b2a-537308efe62d56a26ae0058b2eef993e<\\/docid> <tplid>550<\\/tplid><title><![CDATA[你的芝麻信用分怎么算?背后有FICO科学家]]><\\/title><url><![CDATA[http://mp.weixin.qq.com/s?__biz=MzAwMTMwMzQwOA==&mid=208437359&idx=5&sn=e20d74266f401727c3962547c3b84bfb&3rd=MzA3MDU4NTYzMw==&scene=6#rd]]><\\/url><title1><![CDATA[你的芝麻信用分怎么算?背后有FICO科学家]]><\\/title1><imglink><![CDATA[http://mmbiz.qpic.cn/mmbiz/VY5nQhmoHlehvRPEFYplpRV21iaibFE4ficrgglN3ZnALdr794svwEZ9697EkUGoxwwxyaaASI7Wiaj8Xv6wAdsscg/0?wx_fmt=jpeg]]><\\/imglink><headimage><![CDATA[http://wx.qlogo.cn/mmhead/Q3auHgzwzM4trgxuedYg2SUtfknZibK7iarW5ysiaiaAH25MYPlZ6vy1hw/0]]><\\/headimage><sourcename><![CDATA[人人征信]]><\\/sourcename><content168><![CDATA[今年1月,中国人民银行印发《关于做好个人征信业务准备工作的通知》,要求八家机构做好个人征信业务的准备工作,准备时间为六个月.这被业界视为个人征信市场化将正式开闸的信号.为了了解这八家企业的模式以及运营状况,网易科技推出了《大数据征信系列报道》.本文是大数据征...]]><\\/content168><isV><![CDATA[1]]><\\/isV><openid><![CDATA[oIWsFt4qB1pL53pFpe1w7f-nx05Q]]><\\/openid><content><![CDATA[今年1月,中国人民银行印发《关于做好个人征信业务准备工作的通知》,要求八家机构做好个人征信业务的准备工作,准备时间为六...]]><\\/content><showurl><![CDATA[微信 - mp.weixin.qq.com]]><\\/showurl><date><![CDATA[2015-8-4]]><\\/date><pagesize><![CDATA[114k]]><\\/pagesize> <lastModified>1438658280<\\/lastModified><pagesize>115k<\\/pagesize> <\\/display><\\/item><\\/DOCUMENT>\",\"<?xml version=\\\"1.0\\\" encoding=\\\"gbk\\\"?><DOCUMENT><docid><\\/docid><item> <key><![CDATA[\uE468http://mp.weixin.qq.com/]]><\\/key><tplid><![CDATA[555]]><\\/tplid><classid>11002601<\\/classid> <display> <docid>ab735a258a90e8e1-6bee54fcbd896b2a-cb3ac06d5f0e106e7679d8736b2bc1c5<\\/docid> <tplid>550<\\/tplid><title><![CDATA[【干货】论欧盟征信机构监管法律制度]]><\\/title><url><![CDATA[http://mp.weixin.qq.com/s?__biz=MzAwMTMwMzQwOA==&mid=208437359&idx=1&sn=bfb0d8185dc6237be362c642e7c065d3&3rd=MzA3MDU4NTYzMw==&scene=6#rd]]><\\/url><title1><![CDATA[【干货】论欧盟征信机构监管法律制度]]><\\/title1><imglink><![CDATA[http://mmbiz.qpic.cn/mmbiz/VY5nQhmoHlehvRPEFYplpRV21iaibFE4ficf2UqfDTVLMoMAG07wgHb3lRwMckFpFC6OPTTpSVUqt9CfMJH8iapvnA/0?wx_fmt=jpeg]]><\\/imglink><headimage><![CDATA[http://wx.qlogo.cn/mmhead/Q3auHgzwzM4trgxuedYg2SUtfknZibK7iarW5ysiaiaAH25MYPlZ6vy1hw/0]]><\\/headimage><sourcename><![CDATA[人人征信]]><\\/sourcename><content168><![CDATA[内容提要 20世纪70年代至今,欧盟在对征信机构监管方面形成了完善的以平衡信息共享与隐私保护为核心的法律框架,建立了比较完备的征信体系,对我国当下征信业健康发展有重要借鉴意义.本文主要从欧盟征信机构类型、征信机构的监管框架、征信行为的法律规制和征信机构的法律责任...]]><\\/content168><isV><![CDATA[1]]><\\/isV><openid><![CDATA[oIWsFt4qB1pL53pFpe1w7f-nx05Q]]><\\/openid><content><![CDATA[内容提要 20世纪70年代至今,欧盟在对征信机构监管方面形成了完善的以平衡信息共享与隐私保护为核心的法律框架,建立了比较完...]]><\\/content><showurl><![CDATA[微信 - mp.weixin.qq.com]]><\\/showurl><date><![CDATA[2015-8-4]]><\\/date><pagesize><![CDATA[179k]]><\\/pagesize> <lastModified>1438658281<\\/lastModified><pagesize>180k<\\/pagesize> <\\/display><\\/item><\\/DOCUMENT>\",\"<?xml version=\\\"1.0\\\" encoding=\\\"gbk\\\"?><DOCUMENT><docid><\\/docid><item> <key><![CDATA[\uE468http://mp.weixin.qq.com/]]><\\/key><tplid><![CDATA[555]]><\\/tplid><classid>11002601<\\/classid> <display> <docid>ab735a258a90e8e1-6bee54fcbd896b2a-2bbff6bbb262d94981b1f8246c43e1e0<\\/docid> <tplid>550<\\/tplid><title><![CDATA[信用卡成不良资产重灾区 银行组成专项小组清收]]><\\/title><url><![CDATA[http://mp.weixin.qq.com/s?__biz=MzAwMTMwMzQwOA==&mid=208437359&idx=6&sn=26168a807525a40ca4ba09ca59d726b3&3rd=MzA3MDU4NTYzMw==&scene=6#rd]]><\\/url><title1><![CDATA[信用卡成不良资产重灾区 银行组成专项小组清收]]><\\/title1><imglink><![CDATA[http://mmbiz.qpic.cn/mmbiz/VY5nQhmoHlehvRPEFYplpRV21iaibFE4ficfBTsUSiaPfgVoQagcBLNf1Zzib3kBQzDq5JPEvCibMuvGWhd3gcHQgM4w/0?wx_fmt=jpeg]]><\\/imglink><headimage><![CDATA[http://wx.qlogo.cn/mmhead/Q3auHgzwzM4trgxuedYg2SUtfknZibK7iarW5ysiaiaAH25MYPlZ6vy1hw/0]]><\\/headimage><sourcename><![CDATA[人人征信]]><\\/sourcename><content168><![CDATA[近日,中国银行业协会发布了《中国信用卡发展蓝皮书》,报告指出,目前,市场上多家银行向一名持卡人发放信用卡的现象时有发生,各发卡行对同一持卡客户的授信总额过高,使得银行垫付资金面临风险,多头授信累加授信现象使得信用风险膨胀系数成倍增长.  数据显示,截至2014年年...]]><\\/content168><isV><![CDATA[1]]><\\/isV><openid><![CDATA[oIWsFt4qB1pL53pFpe1w7f-nx05Q]]><\\/openid><content><![CDATA[近日,中国银行业协会发布了《中国信用卡发展蓝皮书》,报告指出,目前,市场上多家银行向一名持卡人发放信用卡的现象时有发生...]]><\\/content><showurl><![CDATA[微信 - mp.weixin.qq.com]]><\\/showurl><date><![CDATA[2015-8-4]]><\\/date><pagesize><![CDATA[26k]]><\\/pagesize> <lastModified>1438658280<\\/lastModified><pagesize>27k<\\/pagesize> <\\/display><\\/item><\\/DOCUMENT>\",\"<?xml version=\\\"1.0\\\" encoding=\\\"gbk\\\"?><DOCUMENT><docid><\\/docid><item> <key><![CDATA[\uE468http://mp.weixin.qq.com/]]><\\/key><tplid><![CDATA[555]]><\\/tplid><classid>11002601<\\/classid> <display> <docid>ab735a258a90e8e1-6bee54fcbd896b2a-19bab77191bf098d27826cb8c58f16f2<\\/docid> <tplid>550<\\/tplid><title><![CDATA[垃圾分类管理将纳入征信系统]]><\\/title><url><![CDATA[http://mp.weixin.qq.com/s?__biz=MzAwMTMwMzQwOA==&mid=208437359&idx=2&sn=3085b881226211948619478ba506f117&3rd=MzA3MDU4NTYzMw==&scene=6#rd]]><\\/url><title1><![CDATA[垃圾分类管理将纳入征信系统]]><\\/title1><imglink><![CDATA[http://mmbiz.qpic.cn/mmbiz/VY5nQhmoHlehvRPEFYplpRV21iaibFE4ficyqGDticLkVX9I3emGWCKV9c1xLsQ4bvUTv84REVCMpQNGgHXY1WCVibQ/0?wx_fmt=jpeg]]><\\/imglink><headimage><![CDATA[http://wx.qlogo.cn/mmhead/Q3auHgzwzM4trgxuedYg2SUtfknZibK7iarW5ysiaiaAH25MYPlZ6vy1hw/0]]><\\/headimage><sourcename><![CDATA[人人征信]]><\\/sourcename><content168><![CDATA[广州日报讯(记者崔宁宁 通讯员瞿明光、 向江平)为让市民更快地熟悉新法规,提高环保意识,融入到日常生活中去,前日下午,宝安福永街道义工和城管市政部门在桥头社区鸿德小区开展了生活垃圾分类和减量宣传活动.从8月1日起,《深圳市生活垃圾分类和减量管理办法》正式实施,根...]]><\\/content168><isV><![CDATA[1]]><\\/isV><openid><![CDATA[oIWsFt4qB1pL53pFpe1w7f-nx05Q]]><\\/openid><content><![CDATA[广州日报讯(记者崔宁宁 通讯员瞿明光、 向江平)为让市民更快地熟悉新法规,提高环保意识,融入到日常生活中去,前日下午,宝...]]><\\/content><showurl><![CDATA[微信 - mp.weixin.qq.com]]><\\/showurl><date><![CDATA[2015-8-4]]><\\/date><pagesize><![CDATA[21k]]><\\/pagesize> <lastModified>1438658280<\\/lastModified><pagesize>22k<\\/pagesize> <\\/display><\\/item><\\/DOCUMENT>\",\"<?xml version=\\\"1.0\\\" encoding=\\\"gbk\\\"?><DOCUMENT><docid><\\/docid><item> <key><![CDATA[\uE468http://mp.weixin.qq.com/]]><\\/key><tplid><![CDATA[555]]><\\/tplid><classid>11002601<\\/classid> <display> <docid>ab735a258a90e8e1-6bee54fcbd896b2a-50f8f99e4997be56664e93f9a291454c<\\/docid> <tplid>550<\\/tplid><title><![CDATA[阿里巴巴升级国际信用保障体系 重整跨境电商成全了谁?]]><\\/title><url><![CDATA[http://mp.weixin.qq.com/s?__biz=MzAwMTMwMzQwOA==&mid=208426457&idx=1&sn=e694f2ee7286d25117557732f65b2937&3rd=MzA3MDU4NTYzMw==&scene=6#rd]]><\\/url><title1><![CDATA[阿里巴巴升级国际信用保障体系 重整跨境电商成全了谁?]]><\\/title1><imglink><![CDATA[http://mmbiz.qpic.cn/mmbiz/VY5nQhmoHldRsP6knke6HibcicClUUUkg61dYJADA6dPA6CIFBKgibicWUTFEPv6t7CAouhQns8N2UcADFnrvXMialw/0?wx_fmt=jpeg]]><\\/imglink><headimage><![CDATA[http://wx.qlogo.cn/mmhead/Q3auHgzwzM4trgxuedYg2SUtfknZibK7iarW5ysiaiaAH25MYPlZ6vy1hw/0]]><\\/headimage><sourcename><![CDATA[人人征信]]><\\/sourcename><content168><![CDATA[阿里巴巴旗下领先的全球贸易批发平台Alibaba.com近日在悉尼宣布,推出升级版的信用保障体系,一项旨在提升贸易双方信任、便利买家与海外供应商进行贸易的措施.阿里巴巴B2B事业群总裁吴敏芝表示,通过提供最大程度的贸易保障,Alibaba.com这一平台希望促使跨境贸易更加便利,从...]]><\\/content168><isV><![CDATA[1]]><\\/isV><openid><![CDATA[oIWsFt4qB1pL53pFpe1w7f-nx05Q]]><\\/openid><content><![CDATA[阿里巴巴旗下领先的全球贸易批发平台Alibaba.com近日在悉尼宣布,推出升级版的信用保障体系,一项旨在提升贸易双方信任、便利...]]><\\/content><showurl><![CDATA[微信 - mp.weixin.qq.com]]><\\/showurl><date><![CDATA[2015-8-3]]><\\/date><pagesize><![CDATA[43k]]><\\/pagesize> <lastModified>1438571602<\\/lastModified><pagesize>44k<\\/pagesize> <\\/display><\\/item><\\/DOCUMENT>\",\"<?xml version=\\\"1.0\\\" encoding=\\\"gbk\\\"?><DOCUMENT><docid><\\/docid><item> <key><![CDATA[\uE468http://mp.weixin.qq.com/]]><\\/key><tplid><![CDATA[555]]><\\/tplid><classid>11002601<\\/classid> <display> <docid>ab735a258a90e8e1-6bee54fcbd896b2a-6b224f9fe41a304a988513f3938588dc<\\/docid> <tplid>550<\\/tplid><title><![CDATA[个人征信大时代   占领市场=占领应用场景入口]]><\\/title><url><![CDATA[http://mp.weixin.qq.com/s?__biz=MzAwMTMwMzQwOA==&mid=208426457&idx=2&sn=b022d5e2bb83e210949e97f075793d16&3rd=MzA3MDU4NTYzMw==&scene=6#rd]]><\\/url><title1><![CDATA[个人征信大时代   占领市场=占领应用场景入口]]><\\/title1><imglink><![CDATA[http://mmbiz.qpic.cn/mmbiz/VY5nQhmoHldRsP6knke6HibcicClUUUkg627ylGB5UcUcJq37vp7MUrLt5Q5icMFMohSeXfWGTKmtqI9qb3MeAzHQ/0?wx_fmt=jpeg]]><\\/imglink><headimage><![CDATA[http://wx.qlogo.cn/mmhead/Q3auHgzwzM4trgxuedYg2SUtfknZibK7iarW5ysiaiaAH25MYPlZ6vy1hw/0]]><\\/headimage><sourcename><![CDATA[人人征信]]><\\/sourcename><content168><![CDATA[您必须要在今日内还款,否则如果逾期,将被记录到央行的征信系统里.”电话里,一家P2P公司的催收员一本正经的告诉借款人.事实上,至今为止全国并没有一家P2P公司接入了央行征信系统.这只是催收员的无奈手段而已.  据考拉征信总裁李广雨介绍,P2P公司近两年的高速发展还催...]]><\\/content168><isV><![CDATA[1]]><\\/isV><openid><![CDATA[oIWsFt4qB1pL53pFpe1w7f-nx05Q]]><\\/openid><content><![CDATA[您必须要在今日内还款,否则如果逾期,将被记录到央行的征信系统里.”电话里,一家P2P公司的催收员一本正经的告诉借款人.事...]]><\\/content><showurl><![CDATA[微信 - mp.weixin.qq.com]]><\\/showurl><date><![CDATA[2015-8-3]]><\\/date><pagesize><![CDATA[116k]]><\\/pagesize> <lastModified>1438571601<\\/lastModified><pagesize>117k<\\/pagesize> <\\/display><\\/item><\\/DOCUMENT>\",\"<?xml version=\\\"1.0\\\" encoding=\\\"gbk\\\"?><DOCUMENT><docid><\\/docid><item> <key><![CDATA[\uE468http://mp.weixin.qq.com/]]><\\/key><tplid><![CDATA[555]]><\\/tplid><classid>11002601<\\/classid> <display> <docid>ab735a258a90e8e1-6bee54fcbd896b2a-cfd9985d36523ed03e45611baffa4bcd<\\/docid> <tplid>550<\\/tplid><title><![CDATA[你以为销户了,信用卡逾期就没了?]]><\\/title><url><![CDATA[http://mp.weixin.qq.com/s?__biz=MzAwMTMwMzQwOA==&mid=208426457&idx=3&sn=3d8148dbdba37d01532b53baca5e64f1&3rd=MzA3MDU4NTYzMw==&scene=6#rd]]><\\/url><title1><![CDATA[你以为销户了,信用卡逾期就没了?]]><\\/title1><imglink><![CDATA[http://mmbiz.qpic.cn/mmbiz/VY5nQhmoHldRsP6knke6HibcicClUUUkg6TMlT8CSOE9LEEnp5G6nNVsB6TGpKFQ8UzsXB8LcibUbZbP9ib7yEtoWg/0?wx_fmt=jpeg]]><\\/imglink><headimage><![CDATA[http://wx.qlogo.cn/mmhead/Q3auHgzwzM4trgxuedYg2SUtfknZibK7iarW5ysiaiaAH25MYPlZ6vy1hw/0]]><\\/headimage><sourcename><![CDATA[人人征信]]><\\/sourcename><content168><![CDATA[~最近遇到很多盆友来咨询我们,说自己是黑户,自己有多少次逾期,现在想急用钱,想贷款,想买房;你现在来想这个,我只能笑而不语;还有一大部分的伙伴明知道自己逾期了,然后天真的和我说,我销户了,你以为征信上就没有记录了?  今天,你一定记住一句话:信用卡逾期,销户是解...]]><\\/content168><isV><![CDATA[1]]><\\/isV><openid><![CDATA[oIWsFt4qB1pL53pFpe1w7f-nx05Q]]><\\/openid><content><![CDATA[~最近遇到很多盆友来咨询我们,说自己是黑户,自己有多少次逾期,现在想急用钱,想贷款,想买房;你现在来想这个,我只能笑而...]]><\\/content><showurl><![CDATA[微信 - mp.weixin.qq.com]]><\\/showurl><date><![CDATA[2015-8-3]]><\\/date><pagesize><![CDATA[24k]]><\\/pagesize> <lastModified>1438571601<\\/lastModified><pagesize>25k<\\/pagesize> <\\/display><\\/item><\\/DOCUMENT>\",\"<?xml version=\\\"1.0\\\" encoding=\\\"gbk\\\"?><DOCUMENT><docid><\\/docid><item> <key><![CDATA[\uE468http://mp.weixin.qq.com/]]><\\/key><tplid><![CDATA[555]]><\\/tplid><classid>11002601<\\/classid> <display> <docid>ab735a258a90e8e1-6bee54fcbd896b2a-e152dc6cbf8d81269ead543a0c48f1b5<\\/docid> <tplid>550<\\/tplid><title><![CDATA[众筹上半年募资近47亿元 完善信用体系迎黄金时代]]><\\/title><url><![CDATA[http://mp.weixin.qq.com/s?__biz=MzAwMTMwMzQwOA==&mid=208426457&idx=5&sn=35bd2fb810f8285816e9eb428c6a5523&3rd=MzA3MDU4NTYzMw==&scene=6#rd]]><\\/url><title1><![CDATA[众筹上半年募资近47亿元 完善信用体系迎黄金时代]]><\\/title1><imglink><![CDATA[http://mmbiz.qpic.cn/mmbiz/VY5nQhmoHldRsP6knke6HibcicClUUUkg6gr7xJLQCBtTmyibAJIqA2xbFNdCOopB551ae7fcF07xs1OHicoDibBhicA/0?wx_fmt=jpeg]]><\\/imglink><headimage><![CDATA[http://wx.qlogo.cn/mmhead/Q3auHgzwzM4trgxuedYg2SUtfknZibK7iarW5ysiaiaAH25MYPlZ6vy1hw/0]]><\\/headimage><sourcename><![CDATA[人人征信]]><\\/sourcename><content168><![CDATA[众筹行业风生水起.据网贷之家联合盈灿咨询发布《2015年中国众筹行业半年报》的数据显示,2015年上半年众筹行业共募集资金46.66亿元.另外,央行、工信部、公安部等十部门日前联合发布了《关于促进互联网金融健康发展的指导意见》.作为互联网金融的新兴业态,股权众筹行业在《...]]><\\/content168><isV><![CDATA[1]]><\\/isV><openid><![CDATA[oIWsFt4qB1pL53pFpe1w7f-nx05Q]]><\\/openid><content><![CDATA[众筹行业风生水起.据网贷之家联合盈灿咨询发布《2015年中国众筹行业半年报》的数据显示,2015年上半年众筹行业共募集资金46....]]><\\/content><showurl><![CDATA[微信 - mp.weixin.qq.com]]><\\/showurl><date><![CDATA[2015-8-3]]><\\/date><pagesize><![CDATA[46k]]><\\/pagesize> <lastModified>1438571601<\\/lastModified><pagesize>47k<\\/pagesize> <\\/display><\\/item><\\/DOCUMENT>\",\"<?xml version=\\\"1.0\\\" encoding=\\\"gbk\\\"?><DOCUMENT><docid><\\/docid><item> <key><![CDATA[\uE468http://mp.weixin.qq.com/]]><\\/key><tplid><![CDATA[555]]><\\/tplid><classid>11002601<\\/classid> <display> <docid>ab735a258a90e8e1-6bee54fcbd896b2a-8e3cb9448d829486f40b5e28c6a580bf<\\/docid> <tplid>550<\\/tplid><title><![CDATA[央行非银行支付新规加速助推互联网个人征信业务]]><\\/title><url><![CDATA[http://mp.weixin.qq.com/s?__biz=MzAwMTMwMzQwOA==&mid=208426457&idx=4&sn=20232677a4c68dc104a6005817c30684&3rd=MzA3MDU4NTYzMw==&scene=6#rd]]><\\/url><title1><![CDATA[央行非银行支付新规加速助推互联网个人征信业务]]><\\/title1><imglink><![CDATA[http://mmbiz.qpic.cn/mmbiz/VY5nQhmoHldRsP6knke6HibcicClUUUkg6NQmuMHKDeJpnUydzHQc9DtMVCqicpZyaRpeEJQgribpnIpGTq7IbozgA/0?wx_fmt=jpeg]]><\\/imglink><headimage><![CDATA[http://wx.qlogo.cn/mmhead/Q3auHgzwzM4trgxuedYg2SUtfknZibK7iarW5ysiaiaAH25MYPlZ6vy1hw/0]]><\\/headimage><sourcename><![CDATA[人人征信]]><\\/sourcename><content168><![CDATA[8月2日消息,中银国际证券就央行[微博]上周五发布的《非银行支付机构网络支付业务管理办法(征求意见稿)》召开解读电话会议称,央行意欲直接限制第三方支付争抢的P2P的网贷资金托管业务,基于用户交易数据的金融产品创新会受到一定影响,政策表面上会影响支付体验、减少支付平...]]><\\/content168><isV><![CDATA[1]]><\\/isV><openid><![CDATA[oIWsFt4qB1pL53pFpe1w7f-nx05Q]]><\\/openid><content><![CDATA[8月2日消息,中银国际证券就央行[微博]上周五发布的《非银行支付机构网络支付业务管理办法(征求意见稿)》召开解读电话会议称...]]><\\/content><showurl><![CDATA[微信 - mp.weixin.qq.com]]><\\/showurl><date><![CDATA[2015-8-3]]><\\/date><pagesize><![CDATA[24k]]><\\/pagesize> <lastModified>1438571601<\\/lastModified><pagesize>25k<\\/pagesize> <\\/display><\\/item><\\/DOCUMENT>\"]})\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<!--STATUS total 10 time 11 page 0 maxEnd 100 totalItems 488-->\n" +
                "<!--real_pageno:1-->\n" +
                "<!--zly-->\n" +
                "\n" +
                "//<!--d1751c83-39b3-4f4b-91f4-f95117cd0c9a--><!--1438689733243-->";
        if (str.contains("sogou\\.weixin\\.gzhcb")) {
            System.out.println("ok");
        } else {
            System.out.println("wrong");
        }
    }
}
