package cn.com.fero.tlc.spider.job.article;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderHTMLParser;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderLoggerUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import cn.com.fero.tlc.spider.vo.article.ArticleFetch;
import cn.com.fero.tlc.spider.vo.article.ArticleSource;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.*;


/**
 * Created by gizmo on 15/6/17.
 */
//文章抓取
public class ArticleJob extends TLCSpiderJob{
    private static final String URL_ARTICLE_SOURCE = TLCSpiderPropertiesUtil.getResource("tlc.spider.article.source.url");
    private static final String URL_ARTICLE_POST = TLCSpiderPropertiesUtil.getResource("tlc.spider.article.post.url");
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.article.source.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.article.source.token");
    private static final String JOB_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.article.source.title");

    public Map<String, String> constructGetParam() {
        Map<String, String> param = new HashMap();
        param.put(TLCSpiderConstants.SPIDER_PARAM_SID, SID);
        param.put(TLCSpiderConstants.SPIDER_PARAM_TOKEN, TOKEN);
        return param;
    }

    public Map<String, String> constructSendParam(String article_source_id, List<ArticleFetch> fetchList) {
        Map<String, String> param = new HashMap();
        param.put(TLCSpiderConstants.SPIDER_PARAM_SID, SID);
        param.put(TLCSpiderConstants.SPIDER_PARAM_TOKEN, TOKEN);
        param.put(TLCSpiderConstants.SPIDER_PARAM_DATA, TLCSpiderJsonUtil.array2Json(fetchList));
        param.put("article_source_id", article_source_id);
        return param;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        TLCSpiderLoggerUtil.getLogger().info("获取抓取文章源");
        ArticleSource articleSource = getArticleSource();
        String name = articleSource.getName();
        String article_source_id = articleSource.getId();
        String artileUrl = articleSource.getUrl();
        TLCSpiderLoggerUtil.getLogger().info("取得文章源，id={}, name={}, url={}", new String[]{article_source_id, name, artileUrl});

        TLCSpiderLoggerUtil.getLogger().info("获取{}文章总页数", name);
        int totalPage = getTotalPage(artileUrl);

        List<ArticleFetch> fetchList = new ArrayList();
        for(int a = 1; a <= totalPage; a++) {
            TLCSpiderLoggerUtil.getLogger().info("抓取{}第{}页", name, a);
            String fetchUrl = artileUrl + "&page=" + a;
            fetchList.addAll(getArticleList(fetchUrl, article_source_id));
        }

        TLCSpiderLoggerUtil.getLogger().info("发送抓取{}数据，总条数{}", name, fetchList.size());
        sendDataToSystem(article_source_id, fetchList);
    }

    private ArticleSource getArticleSource() {
        Map<String, String> getParam = constructGetParam();
        String sourceContent = TLCSpiderRequest.post(URL_ARTICLE_SOURCE, getParam);
        String sourceData = TLCSpiderJsonUtil.getString(sourceContent, "data");
        return (ArticleSource) TLCSpiderJsonUtil.json2Object(sourceData, ArticleSource.class);
    }

    private int getTotalPage(String artileUrl) {
        String articleContent = TLCSpiderRequest.getViaProxy(artileUrl, TLCSpiderRequest.ProxyType.HTTP);

        if(articleContent.contains("totalPages")) {
            String fetchData = formatFetchContent(articleContent);
            String totalPage = TLCSpiderJsonUtil.getString(fetchData, "totalPages");
            return Integer.parseInt(totalPage);
        } else {
            TLCSpiderLoggerUtil.getLogger().error("返回总页数错误，抓取文章结束");
            return 0;
        }
    }

    private List<ArticleFetch> getArticleList(String url, String article_source_id) {
        String fetchContent = TLCSpiderRequest.getViaProxy(url, TLCSpiderRequest.ProxyType.HTTP);

        if(fetchContent.contains("totalPages")) {
            String fetchData = formatFetchContent(fetchContent);
            List<String> itemList = getItemList(fetchData);
            return convertToArticleFetch(article_source_id, itemList);
        } else {
            TLCSpiderLoggerUtil.getLogger().error("返回内容错误，抓取文章结束");
            return Collections.EMPTY_LIST;
        }
    }

    private String formatFetchContent(String fetchContent) {
        return fetchContent.substring(fetchContent.indexOf("{"), fetchContent.lastIndexOf("}") + 1)
                .replaceAll("<!\\[CDATA\\[", "").replaceAll("]]>", "");
    }

    private List<String> getItemList(String fetchData) {
        String items = TLCSpiderJsonUtil.getString(fetchData, "items");
        List<String> jsonList = TLCSpiderJsonUtil.json2Array(items);
        return jsonList;
    }

    private List<ArticleFetch> convertToArticleFetch(String article_source_id, List<String> itemList) {
        List<ArticleFetch> articleFetchList = new ArrayList();

        for(String item : itemList) {
            ArticleFetch articleFetch = new ArticleFetch();
            articleFetch.setArticle_source_id(article_source_id);
            articleFetch.setTitle(TLCSpiderHTMLParser.parseText(item, "//title"));
            articleFetch.setUrl(TLCSpiderHTMLParser.parseText(item, "//url"));
            articleFetch.setArticle_cd(TLCSpiderHTMLParser.parseText(item, "//display//docid"));
            articleFetch.setPost_at(TLCSpiderHTMLParser.parseText(item, "//date"));

            articleFetchList.add(articleFetch);
        }

        return articleFetchList;
    }

    protected void sendDataToSystem(String article_source_id, List<ArticleFetch> fetchList) {
        Map<String, String> sendParam = constructSendParam(article_source_id, fetchList);
        String response = TLCSpiderRequest.post(URL_ARTICLE_POST, sendParam);
        TLCSpiderLoggerUtil.getLogger().info("发送" + JOB_TITLE + "状态：" + response);
    }
}
