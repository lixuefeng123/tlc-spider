package cn.com.fero.tlc.spider.job.article;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderLoggerUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.vo.article.ArticleSource;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//文章抓取
public class ArticleJob extends TLCSpiderJob{
    private static final String URL_ARTICLE_SOURCE = TLCSpiderPropertiesUtil.getResource("tlc.spider.article.source.url");
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.article.source.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.article.source.token");
    private static final String JOB_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.article.source.title");
    private static final Map<String, String> HEAD = new HashMap(){
        {
            put("Cookie", "CXID=C2D908DCA2484D12F57C0A1D143A7B66; SUID=97017D7B142D900A55B5C349000477E0; SUV=1507271026258805; ABTEST=0|1438588933|v1; SNUID=C9EE0F0001071D6194556AA302CBA565; ad=Iyllllllll2qHt2JlllllVQ@JAZlllllWT1xOZllll9llllllCxlw@@@@@@@@@@@; IPLOC=CN1100");
        }
    };

    public Map<String, String> constructGetParam() {
        Map<String, String> param = new HashMap();
        param.put(TLCSpiderConstants.SPIDER_PARAM_SID, SID);
        param.put(TLCSpiderConstants.SPIDER_PARAM_TOKEN, TOKEN);
        return param;
    }

    public Map<String, String> constructSendParam() {
        Map<String, String> param = new HashMap();
        param.put(TLCSpiderConstants.SPIDER_PARAM_SID, SID);
        param.put(TLCSpiderConstants.SPIDER_PARAM_TOKEN, TOKEN);
        param.put("article_source_id", "");
        param.put(TLCSpiderConstants.SPIDER_PARAM_DATA, "");
        return param;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Map<String, String> getParam = constructGetParam();
        String sourceContent = TLCSpiderRequest.post(URL_ARTICLE_SOURCE, getParam);
        String sourceData = TLCSpiderJsonUtil.getString(sourceContent, "data");
        ArticleSource articleSource = (ArticleSource) TLCSpiderJsonUtil.json2Object(sourceData, ArticleSource.class);

        String artileUrl = articleSource.getUrl();
        String fetchContent = TLCSpiderRequest.getViaProxy(artileUrl, TLCSpiderRequest.ProxyType.HTTP);
        if(!fetchContent.contains("totalPages")) {
            TLCSpiderLoggerUtil.getLogger().info("使用代理抓取文章异常，去除代理重新请求");
            fetchContent = TLCSpiderRequest.get(artileUrl);
        }

        if(fetchContent.contains("totalPages")) {
            String fetchData = fetchContent.substring(fetchContent.indexOf("{"), fetchContent.lastIndexOf("}") + 1);
            String totalPages = TLCSpiderJsonUtil.getString(fetchData, "totalPages");
            String items = TLCSpiderJsonUtil.getString(fetchData, "items");


        } else {
            TLCSpiderLoggerUtil.getLogger().info("返回内容错误，抓取文章结束");
        }
    }
}
