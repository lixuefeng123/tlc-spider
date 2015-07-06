package cn.com.fero.tlc.spider.quartz;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.util.JsonUtil;
import cn.com.fero.tlc.spider.util.LoggerUtil;
import cn.com.fero.tlc.spider.vo.TransObject;
import com.sun.media.sound.InvalidDataException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Required;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gizmo on 15/6/17.
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public abstract class TLCSpiderJob implements Job, TLCSpiderExecutor {
    private String jobName;
    private String jobGroupName;
    private String triggerName;
    private String triggerGroupName;
    private String cronExpression;

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

    protected void print(List list) {
        if(CollectionUtils.isEmpty(list)) {
            return;
        }

        for(Object obj : list) {
            System.out.println(obj);
        }
    }

    protected Map<String, TransObject> getUpdateMap() throws InvalidDataException {
        Map<String, String> map = constructPostParam();
        String result = TLCSpiderRequest.post(TLCSpiderConstants.SPIDER_GET_URL, map);
        String status = JsonUtil.getString(result, "state");
        if(!TLCSpiderConstants.HTTP_PARAM_STATUS_SUCCESS_CODE.equals(status)) {
            throw new InvalidDataException(result);
        }

        List<TransObject> updateList = JsonUtil.json2Array(result, TLCSpiderConstants.HTTP_PARAM_DATA, TransObject.class);
        Map<String, TransObject> updateMap = new HashMap();
        for(TransObject transObject : updateList) {
            map.put(transObject.getFinancingId(), null);
        }
        return updateMap;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            this.doExecute();
        } catch(Exception e) {
            Map<String, String> map = constructPostParam();
            map.put(TLCSpiderConstants.HTTP_PARAM_STATUS_NAME, TLCSpiderConstants.HTTP_PARAM_STATUS_FAIL_CODE);
            map.put(TLCSpiderConstants.HTTP_PARAM_MESSAGE, ExceptionUtils.getFullStackTrace(e));
            postData(map);
        }
    }

    protected void postData() {
        Map<String, String> map = constructPostParam();
        postData(map);
    }

    protected void postData(Map<String, String> map) {
        String response = TLCSpiderRequest.post(TLCSpiderConstants.SPIDER_SEND_URL, map);
        LoggerUtil.getLogger().info("发送" + map.get(TLCSpiderConstants.HTTP_PARAM_JOB_TITLE) + "状态：" + response);
    }

    abstract protected Map<String, String> constructPostParam();
}
