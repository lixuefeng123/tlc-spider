package cn.com.fero.tlc.spider.quartz.job;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.util.JsonUtil;
import cn.com.fero.tlc.spider.util.LoggerUtil;
import cn.com.fero.tlc.spider.vo.TransObject;
import org.apache.commons.collections4.CollectionUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.StatefulJob;
import org.springframework.beans.factory.annotation.Required;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gizmo on 15/6/17.
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public abstract class TLCSpiderJob implements Job {
    private String jobName;
    private String jobGroupName;
    private String triggerName;
    private String triggerGroupName;
    private String cronExpression;
    private String sid;
    private String token;
    private List<TransObject> transObjectList;

    public String getJobName() {
        return jobName;
    }

    @Required
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    @Required
    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    @Required
    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroupName() {
        return triggerGroupName;
    }

    @Required
    public void setTriggerGroupName(String triggerGroupName) {
        this.triggerGroupName = triggerGroupName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    @Required
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<TransObject> getTransObjectList() {
        return transObjectList;
    }

    public void setTransObjectList(List<TransObject> transObjectList) {
        this.transObjectList = transObjectList;
    }

    protected void print(List list) {
        if(CollectionUtils.isEmpty(list)) {
            return;
        }

        for(Object obj : list) {
            System.out.println(obj);
        }
    }

    protected void postData(TLCSpiderJob tlcSpiderJob) {
        Map<String, String> map = new HashMap();
        map.put("sid", this.getSid());
        map.put("token", this.getToken());
        map.put("data", JsonUtil.array2Json(this.getTransObjectList()));
        String response = TLCSpiderRequest.post(TLCSpiderConstants.TLC_POST_URL, map);
        LoggerUtil.getLogger().info("发送招商银行小企业E家状态：" + response);
    }
}
