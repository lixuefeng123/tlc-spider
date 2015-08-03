package cn.com.fero.tlc.spider.job;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.util.TLCSpiderLoggerUtil;
import cn.com.fero.tlc.spider.vo.p2p.TransObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Required;

import java.util.*;

/**
 * Created by gizmo on 15/6/17.
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public abstract class TLCSpiderJob implements Job, TLCSpiderP2PExecutor {
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
        List<TransObject> transObjectList = new ArrayList();
        Map<String, String> systemParam = constructSystemParam();
        String jobTitle = systemParam.get(TLCSpiderConstants.SPIDER_CONST_JOB_TITLE);
        String pageName = systemParam.get(TLCSpiderConstants.SPIDER_PARAM_PAGE_NAME);
        try {
            Map<String, TransObject> updateMap = getUpdateDataMap(systemParam);
            TLCSpiderLoggerUtil.getLogger().info(jobTitle + "更新条数 = " + updateMap.size());

            TLCSpiderLoggerUtil.getLogger().info("开始抓取" + jobTitle + "总页数");
            Map<String, String> spiderParam = constructSpiderParam();
            int totalPage = getTotalPage(spiderParam);
            TLCSpiderLoggerUtil.getLogger().info(jobTitle + "总页数数 = " + totalPage);

            boolean isContinue = true;
            for (Integer page = 1; page <= totalPage; page++) {
                if (!isContinue) {
                    break;
                }

                TLCSpiderLoggerUtil.getLogger().info("开始抓取" + jobTitle + "第" + page + "页");
                String startPage = spiderParam.get(pageName);
                if (TLCSpiderConstants.SPIDER_PARAM_PAGE_ZERO.equals(startPage)) {
                    spiderParam.put(pageName, String.valueOf((page - 1)));
                } else {
                    spiderParam.put(pageName, page.toString());
                }

                List<TransObject> resultList = getSpiderDataList(spiderParam);
                for (TransObject transObject : resultList) {
                    if (StringUtils.equalsIgnoreCase(transObject.getProgress(), TLCSpiderConstants.SPIDER_CONST_FULL_PROGRESS)) {
                        isContinue = false;
                        break;
                    } else {
                        if (updateMap.containsKey(transObject.getFinancingId())) {
                            updateMap.remove(updateMap.get(transObject.getFinancingId()));
                        }

                        transObjectList.add(transObject);
                    }
                }

                if (transObjectList.size() >= TLCSpiderConstants.SPIDER_PAGE_SIZE_SEND) {
                    sendDataToSystem(transObjectList, jobTitle);
                }
            }

            setUpdateToFullProgress(transObjectList, updateMap);
            sendDataToSystem(transObjectList, jobTitle);
        } catch (Exception e) {
            TLCSpiderLoggerUtil.getLogger().error("发生异常：" + ExceptionUtils.getFullStackTrace(e));
            Map<String, String> map = constructSystemParam();
            map.put(TLCSpiderConstants.SPIDER_PARAM_STATUS_NAME, TLCSpiderConstants.SPIDER_PARAM_STATUS_FAIL_CODE);
            map.put(TLCSpiderConstants.SPIDER_PARAM_MESSAGE, ExceptionUtils.getFullStackTrace(e));
            sendDataToSystem(map);
        } finally {
            TLCSpiderLoggerUtil.getLogger().info("抓取" + jobTitle + "结束");
        }
    }

    private void setUpdateToFullProgress(List<TransObject> transObjectList, Map<String, TransObject> updateMap) {
        for (Map.Entry<String, TransObject> entry : updateMap.entrySet()) {
            TransObject to = entry.getValue();
            to.setProgress(TLCSpiderConstants.SPIDER_CONST_FULL_PROGRESS);
            to.setRealProgress(TLCSpiderConstants.SPIDER_CONST_FULL_PROGRESS);
            transObjectList.add(to);
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
        TLCSpiderLoggerUtil.getLogger().info("发送" + jobTitle + "数据, size = " + transObjectList.size());
        Map<String, String> map = constructSystemParam();
        checkBeginAndFinishDate(transObjectList);
        map.put(TLCSpiderConstants.SPIDER_PARAM_DATA, TLCSpiderJsonUtil.array2Json(transObjectList));
        sendDataToSystem(map);
        transObjectList.clear();
    }

    private void checkBeginAndFinishDate(List<TransObject> transObjectList) {
        for (TransObject transObject : transObjectList) {
            if (StringUtils.isEmpty(transObject.getProjectBeginTime())) {
                transObject.setProjectBeginTime(DateFormatUtils.format(new Date(), TLCSpiderConstants.SPIDER_CONST_FORMAT_DISPLAY_DATE));
            }

            if (StringUtils.isEmpty(transObject.getProgress())) {
                transObject.setProgress("0");
            } else if (transObject.getProgress().equals(TLCSpiderConstants.SPIDER_CONST_FULL_PROGRESS)) {
                if (StringUtils.isEmpty(transObject.getProgressFinishTime())) {
                    transObject.setProgressFinishTime(DateFormatUtils.format(new Date(), TLCSpiderConstants.SPIDER_CONST_FORMAT_DISPLAY_DATE));
                }
            }
        }
    }

    protected void sendDataToSystem(Map<String, String> map) {
        String response = TLCSpiderRequest.post(TLCSpiderConstants.SPIDER_URL_SEND, map);
        TLCSpiderLoggerUtil.getLogger().info("发送" + map.get(TLCSpiderConstants.SPIDER_CONST_JOB_TITLE) + "状态：" + response);
    }

    protected String convertToParamStr(Map<String, String> param) {
        StringBuilder paramBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            paramBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return paramBuilder.substring(0, paramBuilder.length() - 1);
    }

    @Override
    public Map<String, String> constructSystemParam() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public Map<String, String> constructSpiderParam() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public int getTotalPage(Map<String, String> param) {
        return Integer.parseInt(TLCSpiderConstants.SPIDER_PARAM_PAGE_ONE);
    }

    @Override
    public Map<String, TransObject> getUpdateDataMap(Map<String, String> param) {
        String result = TLCSpiderRequest.post(TLCSpiderConstants.SPIDER_URL_GET, param);
        String status = TLCSpiderJsonUtil.getString(result, TLCSpiderConstants.SPIDER_PARAM_STATUS_NAME);
        if (!TLCSpiderConstants.SPIDER_PARAM_STATUS_SUCCESS_CODE.equals(status)) {
            throw new IllegalStateException(result);
        }

        List<TransObject> updateList = TLCSpiderJsonUtil.json2Array(result, TLCSpiderConstants.SPIDER_PARAM_DATA, TransObject.class);
        Map<String, TransObject> updateMap = new HashMap();
        for (TransObject transObject : updateList) {
            updateMap.put(transObject.getFinancingId(), transObject);
        }
        return updateMap;
//        return Collections.EMPTY_MAP;
    }

    @Override
    public List<TransObject> getSpiderDataList(Map<String, String> param) {
        return Collections.EMPTY_LIST;
    }
}
