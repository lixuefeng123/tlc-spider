package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.util.LoggerUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by gizmo on 15/6/17.
 */
//华夏银行E网通抓取
public class HXEWTJob extends TLCSpiderJob {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LoggerUtil.getLogger().info("开始抓取华夏银行E网通");
    }
}
