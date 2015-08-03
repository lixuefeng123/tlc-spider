package cn.com.fero.tlc.spider.job.article;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.job.TLCSpiderJob;
import cn.com.fero.tlc.spider.util.TLCSpiderPropertiesUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by gizmo on 15/6/17.
 */
//文章抓取
public class ArticleJob extends TLCSpiderJob{
    private static final String SID = TLCSpiderPropertiesUtil.getResource("tlc.spider.article.source.sid");
    private static final String TOKEN = TLCSpiderPropertiesUtil.getResource("tlc.spider.article.source.token");
    private static final String JOB_TITLE = TLCSpiderPropertiesUtil.getResource("tlc.spider.article.source.title");
    private static final Map<String, String> HEAD = new HashMap(){
        {
            put("Cookie", "CXID=C2D908DCA2484D12F57C0A1D143A7B66; SUID=97017D7B142D900A55B5C349000477E0; SUV=1507271026258805; ABTEST=0|1438588933|v1; SNUID=C9EE0F0001071D6194556AA302CBA565; ad=Iyllllllll2qHt2JlllllVQ@JAZlllllWT1xOZllll9llllllCxlw@@@@@@@@@@@; IPLOC=CN1100");
        }
    };

    public Map<String, String> constructSystemParam() {
        Map<String, String> param = new HashMap();
        param.put(TLCSpiderConstants.SPIDER_PARAM_SID, SID);
        param.put(TLCSpiderConstants.SPIDER_PARAM_TOKEN, TOKEN);
        return param;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String updateUrl = "http://localhost:3005/spiderapi/article/post?sid=5&token=%3dM8Amx29CyeP8b2Ne9KC0dJOUFQ5O%3dMQG0gnTV9%2b0tf8";
        Map<String, String> param = constructSystemParam();
        String response = TLCSpiderRequest.post(updateUrl, param);
        System.out.println(response);

        String url = "http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=oIWsFtzcuQtoMO739-mwrqoaWPi4&eqs=pLsqoWtgfIG6osjbygAtCud8xqO5CwnfW%2FMb%2F1qtjEICoi1ZVmfZcmuCOexPe1wuwFIBJ&ekv=7";
        String articleContent = TLCSpiderRequest.getViaProxy(url, TLCSpiderRequest.ProxyType.HTTP, HEAD);
        System.out.println(articleContent);
    }
}
