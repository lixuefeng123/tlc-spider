package cn.com.fero.tlc.spider.vo.p2p;

import java.util.List;

/**
 * Created by gizmo on 15/8/6.
 */
public class ZHEFP {
    private String projectId;
    private String realProjectId;
    private String projectCode;
    private String projectName;
    private String projectAmount;
    private String valueBegin;
    private String beginTime;
    private String endTime;
    private String inStatus;
    private String projectCategory;
    private String isExclusive;
    private String hasAward;
    private String isInPartOpen;
    private List orderCfgList;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getRealProjectId() {
        return realProjectId;
    }

    public void setRealProjectId(String realProjectId) {
        this.realProjectId = realProjectId;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectAmount() {
        return projectAmount;
    }

    public void setProjectAmount(String projectAmount) {
        this.projectAmount = projectAmount;
    }

    public String getValueBegin() {
        return valueBegin;
    }

    public void setValueBegin(String valueBegin) {
        this.valueBegin = valueBegin;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getInStatus() {
        return inStatus;
    }

    public void setInStatus(String inStatus) {
        this.inStatus = inStatus;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public String getIsExclusive() {
        return isExclusive;
    }

    public void setIsExclusive(String isExclusive) {
        this.isExclusive = isExclusive;
    }

    public String getHasAward() {
        return hasAward;
    }

    public void setHasAward(String hasAward) {
        this.hasAward = hasAward;
    }

    public String getIsInPartOpen() {
        return isInPartOpen;
    }

    public void setIsInPartOpen(String isInPartOpen) {
        this.isInPartOpen = isInPartOpen;
    }

    public List getOrderCfgList() {
        return orderCfgList;
    }

    public void setOrderCfgList(List orderCfgList) {
        this.orderCfgList = orderCfgList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZHEFP zhefp = (ZHEFP) o;

        if (beginTime != null ? !beginTime.equals(zhefp.beginTime) : zhefp.beginTime != null) return false;
        if (endTime != null ? !endTime.equals(zhefp.endTime) : zhefp.endTime != null) return false;
        if (hasAward != null ? !hasAward.equals(zhefp.hasAward) : zhefp.hasAward != null) return false;
        if (inStatus != null ? !inStatus.equals(zhefp.inStatus) : zhefp.inStatus != null) return false;
        if (isExclusive != null ? !isExclusive.equals(zhefp.isExclusive) : zhefp.isExclusive != null) return false;
        if (isInPartOpen != null ? !isInPartOpen.equals(zhefp.isInPartOpen) : zhefp.isInPartOpen != null) return false;
        if (orderCfgList != null ? !orderCfgList.equals(zhefp.orderCfgList) : zhefp.orderCfgList != null) return false;
        if (projectAmount != null ? !projectAmount.equals(zhefp.projectAmount) : zhefp.projectAmount != null)
            return false;
        if (projectCategory != null ? !projectCategory.equals(zhefp.projectCategory) : zhefp.projectCategory != null)
            return false;
        if (projectCode != null ? !projectCode.equals(zhefp.projectCode) : zhefp.projectCode != null) return false;
        if (projectId != null ? !projectId.equals(zhefp.projectId) : zhefp.projectId != null) return false;
        if (projectName != null ? !projectName.equals(zhefp.projectName) : zhefp.projectName != null) return false;
        if (realProjectId != null ? !realProjectId.equals(zhefp.realProjectId) : zhefp.realProjectId != null)
            return false;
        if (valueBegin != null ? !valueBegin.equals(zhefp.valueBegin) : zhefp.valueBegin != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = projectId != null ? projectId.hashCode() : 0;
        result = 31 * result + (realProjectId != null ? realProjectId.hashCode() : 0);
        result = 31 * result + (projectCode != null ? projectCode.hashCode() : 0);
        result = 31 * result + (projectName != null ? projectName.hashCode() : 0);
        result = 31 * result + (projectAmount != null ? projectAmount.hashCode() : 0);
        result = 31 * result + (valueBegin != null ? valueBegin.hashCode() : 0);
        result = 31 * result + (beginTime != null ? beginTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (inStatus != null ? inStatus.hashCode() : 0);
        result = 31 * result + (projectCategory != null ? projectCategory.hashCode() : 0);
        result = 31 * result + (isExclusive != null ? isExclusive.hashCode() : 0);
        result = 31 * result + (hasAward != null ? hasAward.hashCode() : 0);
        result = 31 * result + (isInPartOpen != null ? isInPartOpen.hashCode() : 0);
        result = 31 * result + (orderCfgList != null ? orderCfgList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ZHEFP{");
        sb.append("projectId='").append(projectId).append('\'');
        sb.append(", realProjectId='").append(realProjectId).append('\'');
        sb.append(", projectCode='").append(projectCode).append('\'');
        sb.append(", projectName='").append(projectName).append('\'');
        sb.append(", projectAmount='").append(projectAmount).append('\'');
        sb.append(", valueBegin='").append(valueBegin).append('\'');
        sb.append(", beginTime='").append(beginTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", inStatus='").append(inStatus).append('\'');
        sb.append(", projectCategory='").append(projectCategory).append('\'');
        sb.append(", isExclusive='").append(isExclusive).append('\'');
        sb.append(", hasAward='").append(hasAward).append('\'');
        sb.append(", isInPartOpen='").append(isInPartOpen).append('\'');
        sb.append(", orderCfgList=").append(orderCfgList);
        sb.append('}');
        return sb.toString();
    }
}
