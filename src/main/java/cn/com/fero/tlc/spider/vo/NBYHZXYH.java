package cn.com.fero.tlc.spider.vo;

/**
 * Created by wanghongmeng on 2015/6/29.
 */
public class NBYHZXYH {
    private String amount;
    private String createTime;
    private String dbType;
    private String duration;
    private String financeApplyStatus;
    private String financingId;
    private String hotStatus;
    private String investmentInterest;
    private String isExclusivePublic;
    private String jMBeginTime;
    private String minInvestPartsCount;
    private String partsCount;
    private String progress;
    private String progressForOrder;
    private String projectBeginTime;
    private String projectCode;
    private String projectName;
    private String projectStatus;
    private String projectType;
    private String readyBeginTime;
    private String remainPartsCount;
    private String unrealJMBeginTime;
    private String valueBegin;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFinanceApplyStatus() {
        return financeApplyStatus;
    }

    public void setFinanceApplyStatus(String financeApplyStatus) {
        this.financeApplyStatus = financeApplyStatus;
    }

    public String getFinancingId() {
        return financingId;
    }

    public void setFinancingId(String financingId) {
        this.financingId = financingId;
    }

    public String getHotStatus() {
        return hotStatus;
    }

    public void setHotStatus(String hotStatus) {
        this.hotStatus = hotStatus;
    }

    public String getInvestmentInterest() {
        return investmentInterest;
    }

    public void setInvestmentInterest(String investmentInterest) {
        this.investmentInterest = investmentInterest;
    }

    public String getIsExclusivePublic() {
        return isExclusivePublic;
    }

    public void setIsExclusivePublic(String isExclusivePublic) {
        this.isExclusivePublic = isExclusivePublic;
    }

    public String getjMBeginTime() {
        return jMBeginTime;
    }

    public void setjMBeginTime(String jMBeginTime) {
        this.jMBeginTime = jMBeginTime;
    }

    public String getMinInvestPartsCount() {
        return minInvestPartsCount;
    }

    public void setMinInvestPartsCount(String minInvestPartsCount) {
        this.minInvestPartsCount = minInvestPartsCount;
    }

    public String getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(String partsCount) {
        this.partsCount = partsCount;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getProgressForOrder() {
        return progressForOrder;
    }

    public void setProgressForOrder(String progressForOrder) {
        this.progressForOrder = progressForOrder;
    }

    public String getProjectBeginTime() {
        return projectBeginTime;
    }

    public void setProjectBeginTime(String projectBeginTime) {
        this.projectBeginTime = projectBeginTime;
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

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getReadyBeginTime() {
        return readyBeginTime;
    }

    public void setReadyBeginTime(String readyBeginTime) {
        this.readyBeginTime = readyBeginTime;
    }

    public String getRemainPartsCount() {
        return remainPartsCount;
    }

    public void setRemainPartsCount(String remainPartsCount) {
        this.remainPartsCount = remainPartsCount;
    }

    public String getUnrealJMBeginTime() {
        return unrealJMBeginTime;
    }

    public void setUnrealJMBeginTime(String unrealJMBeginTime) {
        this.unrealJMBeginTime = unrealJMBeginTime;
    }

    public String getValueBegin() {
        return valueBegin;
    }

    public void setValueBegin(String valueBegin) {
        this.valueBegin = valueBegin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NBYHZXYH nbyhzxyh = (NBYHZXYH) o;

        if (amount != null ? !amount.equals(nbyhzxyh.amount) : nbyhzxyh.amount != null) return false;
        if (createTime != null ? !createTime.equals(nbyhzxyh.createTime) : nbyhzxyh.createTime != null) return false;
        if (dbType != null ? !dbType.equals(nbyhzxyh.dbType) : nbyhzxyh.dbType != null) return false;
        if (duration != null ? !duration.equals(nbyhzxyh.duration) : nbyhzxyh.duration != null) return false;
        if (financeApplyStatus != null ? !financeApplyStatus.equals(nbyhzxyh.financeApplyStatus) : nbyhzxyh.financeApplyStatus != null)
            return false;
        if (financingId != null ? !financingId.equals(nbyhzxyh.financingId) : nbyhzxyh.financingId != null)
            return false;
        if (hotStatus != null ? !hotStatus.equals(nbyhzxyh.hotStatus) : nbyhzxyh.hotStatus != null) return false;
        if (investmentInterest != null ? !investmentInterest.equals(nbyhzxyh.investmentInterest) : nbyhzxyh.investmentInterest != null)
            return false;
        if (isExclusivePublic != null ? !isExclusivePublic.equals(nbyhzxyh.isExclusivePublic) : nbyhzxyh.isExclusivePublic != null)
            return false;
        if (jMBeginTime != null ? !jMBeginTime.equals(nbyhzxyh.jMBeginTime) : nbyhzxyh.jMBeginTime != null)
            return false;
        if (minInvestPartsCount != null ? !minInvestPartsCount.equals(nbyhzxyh.minInvestPartsCount) : nbyhzxyh.minInvestPartsCount != null)
            return false;
        if (partsCount != null ? !partsCount.equals(nbyhzxyh.partsCount) : nbyhzxyh.partsCount != null) return false;
        if (progress != null ? !progress.equals(nbyhzxyh.progress) : nbyhzxyh.progress != null) return false;
        if (progressForOrder != null ? !progressForOrder.equals(nbyhzxyh.progressForOrder) : nbyhzxyh.progressForOrder != null)
            return false;
        if (projectBeginTime != null ? !projectBeginTime.equals(nbyhzxyh.projectBeginTime) : nbyhzxyh.projectBeginTime != null)
            return false;
        if (projectCode != null ? !projectCode.equals(nbyhzxyh.projectCode) : nbyhzxyh.projectCode != null)
            return false;
        if (projectName != null ? !projectName.equals(nbyhzxyh.projectName) : nbyhzxyh.projectName != null)
            return false;
        if (projectStatus != null ? !projectStatus.equals(nbyhzxyh.projectStatus) : nbyhzxyh.projectStatus != null)
            return false;
        if (projectType != null ? !projectType.equals(nbyhzxyh.projectType) : nbyhzxyh.projectType != null)
            return false;
        if (readyBeginTime != null ? !readyBeginTime.equals(nbyhzxyh.readyBeginTime) : nbyhzxyh.readyBeginTime != null)
            return false;
        if (remainPartsCount != null ? !remainPartsCount.equals(nbyhzxyh.remainPartsCount) : nbyhzxyh.remainPartsCount != null)
            return false;
        if (unrealJMBeginTime != null ? !unrealJMBeginTime.equals(nbyhzxyh.unrealJMBeginTime) : nbyhzxyh.unrealJMBeginTime != null)
            return false;
        if (valueBegin != null ? !valueBegin.equals(nbyhzxyh.valueBegin) : nbyhzxyh.valueBegin != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (dbType != null ? dbType.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (financeApplyStatus != null ? financeApplyStatus.hashCode() : 0);
        result = 31 * result + (financingId != null ? financingId.hashCode() : 0);
        result = 31 * result + (hotStatus != null ? hotStatus.hashCode() : 0);
        result = 31 * result + (investmentInterest != null ? investmentInterest.hashCode() : 0);
        result = 31 * result + (isExclusivePublic != null ? isExclusivePublic.hashCode() : 0);
        result = 31 * result + (jMBeginTime != null ? jMBeginTime.hashCode() : 0);
        result = 31 * result + (minInvestPartsCount != null ? minInvestPartsCount.hashCode() : 0);
        result = 31 * result + (partsCount != null ? partsCount.hashCode() : 0);
        result = 31 * result + (progress != null ? progress.hashCode() : 0);
        result = 31 * result + (progressForOrder != null ? progressForOrder.hashCode() : 0);
        result = 31 * result + (projectBeginTime != null ? projectBeginTime.hashCode() : 0);
        result = 31 * result + (projectCode != null ? projectCode.hashCode() : 0);
        result = 31 * result + (projectName != null ? projectName.hashCode() : 0);
        result = 31 * result + (projectStatus != null ? projectStatus.hashCode() : 0);
        result = 31 * result + (projectType != null ? projectType.hashCode() : 0);
        result = 31 * result + (readyBeginTime != null ? readyBeginTime.hashCode() : 0);
        result = 31 * result + (remainPartsCount != null ? remainPartsCount.hashCode() : 0);
        result = 31 * result + (unrealJMBeginTime != null ? unrealJMBeginTime.hashCode() : 0);
        result = 31 * result + (valueBegin != null ? valueBegin.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NBYHZXYH{");
        sb.append("amount='").append(amount).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", dbType='").append(dbType).append('\'');
        sb.append(", duration='").append(duration).append('\'');
        sb.append(", financeApplyStatus='").append(financeApplyStatus).append('\'');
        sb.append(", financingId='").append(financingId).append('\'');
        sb.append(", hotStatus='").append(hotStatus).append('\'');
        sb.append(", investmentInterest='").append(investmentInterest).append('\'');
        sb.append(", isExclusivePublic='").append(isExclusivePublic).append('\'');
        sb.append(", jMBeginTime='").append(jMBeginTime).append('\'');
        sb.append(", minInvestPartsCount='").append(minInvestPartsCount).append('\'');
        sb.append(", partsCount='").append(partsCount).append('\'');
        sb.append(", progress='").append(progress).append('\'');
        sb.append(", progressForOrder='").append(progressForOrder).append('\'');
        sb.append(", projectBeginTime='").append(projectBeginTime).append('\'');
        sb.append(", projectCode='").append(projectCode).append('\'');
        sb.append(", projectName='").append(projectName).append('\'');
        sb.append(", projectStatus='").append(projectStatus).append('\'');
        sb.append(", projectType='").append(projectType).append('\'');
        sb.append(", readyBeginTime='").append(readyBeginTime).append('\'');
        sb.append(", remainPartsCount='").append(remainPartsCount).append('\'');
        sb.append(", unrealJMBeginTime='").append(unrealJMBeginTime).append('\'');
        sb.append(", valueBegin='").append(valueBegin).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
