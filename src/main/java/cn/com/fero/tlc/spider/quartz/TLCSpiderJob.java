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

import java.util.*;

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

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            List<TransObject> transObjectList = new ArrayList();

            Map<String, String> systemParam = constructSystemParam();
            String jobTitle = systemParam.get(TLCSpiderConstants.HTTP_PARAM_JOB_TITLE);

            LoggerUtil.getLogger().info("开始获取" + jobTitle + "更新列表");
            Map<String, TransObject> updateMap = getUpdateMap();
            LoggerUtil.getLogger().info(jobTitle + "更新条数 = "+ updateMap.size());

            LoggerUtil.getLogger().info("开始抓取" + jobTitle + "总页数");
            int totalPage = getTotalPage();
            LoggerUtil.getLogger().info(jobTitle + "总页数数 = " + totalPage);

            Map<String, String> spiderParam = constructSpiderParam();
            boolean isContinue = true;

            for (Integer page = 1; page <= totalPage; page++) {
                if (!isContinue) {
                    break;
                }

                LoggerUtil.getLogger().info("开始抓取" + jobTitle + "第" + page + "页");
                spiderParam.put(spiderParam.get(TLCSpiderConstants.HTTP_PARAM_PAGE), page.toString());
                List<TransObject> resultList = getDataList();

                for (TransObject transObject : resultList) {
                    if (!transObject.getProgress().equals("100%") || updateMap.containsKey(transObject.getFinancingId())) {
                        transObjectList.add(transObject);
                    } else {
                        isContinue = false;
                        sendDataToSystem(transObjectList, jobTitle);
                        break;
                    }

                    if (transObjectList.size() >= TLCSpiderConstants.SPIDER_PAGE_SIZE_SEND) {
                        sendDataToSystem(transObjectList, jobTitle);
                    }
                }
            }

            if(transObjectList.size() > 0) {
                sendDataToSystem(transObjectList, jobTitle);
            }
        } catch (Exception e) {
            Map<String, String> map = constructSystemParam();
            map.put(TLCSpiderConstants.HTTP_PARAM_STATUS_NAME, TLCSpiderConstants.HTTP_PARAM_STATUS_FAIL_CODE);
            map.put(TLCSpiderConstants.HTTP_PARAM_MESSAGE, ExceptionUtils.getFullStackTrace(e));
            sendDataToSystem(map);
        }
    }

    protected void print(List list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    protected void sendDataToSystem(List<TransObject> transObjectList, String jobTitle) {
        LoggerUtil.getLogger().info("发送" + jobTitle + "数据, size = " + transObjectList.size());
        Map<String, String> map = constructSystemParam();
        map.put(TLCSpiderConstants.HTTP_PARAM_DATA, JsonUtil.array2Json(transObjectList));
        sendDataToSystem(map);
        transObjectList.clear();
    }

    protected void sendDataToSystem(Map<String, String> map) {
        String response = TLCSpiderRequest.post(TLCSpiderConstants.SPIDER_SEND_URL, map);
        LoggerUtil.getLogger().info("发送" + map.get(TLCSpiderConstants.HTTP_PARAM_JOB_TITLE) + "状态：" + response);
    }

    @Override
    public int getTotalPage() throws Exception {
        return 0;
    }

    @Override
    public Map<String, String> constructSystemParam() {
        return Collections.EMPTY_MAP;
    }

//    @Override
//    public Map<String, String> constructSystemParam(List<TransObject> transObjectList) {
//        return Collections.EMPTY_MAP;
//    }

    @Override
    public Map<String, String> constructSpiderParam() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public Map<String, TransObject> getUpdateMap() throws InvalidDataException {
        return Collections.EMPTY_MAP;
    }

    @Override
    public List<TransObject> getDataList() {
        return Collections.EMPTY_LIST;
    }
}
