package cn.com.fero.tlc.spider.vo;

/**
 * Created by wanghongmeng on 2015/6/29.
 */
public class RDNSYHERJZ {
    private String amount;
    private String bankInterest;
    private String bindCompanyId;
    private String bindCompanyName;
    private String bindUserId;
    private String bindUserName;
    private String createTime;
    private String duration;
    private String exclusiveCode;
    private String financeApplyStatus;
    private String financingId;
    private String hotStatus;
    private String investmentInterest;
    private String isExclusivePublic;
    private String isShow;
    private String jMBeginTime;
    private String minInvestPartsCount;
    private String partsCount;
    private String progress;
    private String projectBeginTime;
    private String projectCode;
    private String projectName;
    private String projectStatus;
    private String projectType;
    private String readyBeginTime;
    private String realProgress;
    private String repayBegin;
    private String repayType;
    private String valueBegin;
    private String yMInterest;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBankInterest() {
        return bankInterest;
    }

    public void setBankInterest(String bankInterest) {
        this.bankInterest = bankInterest;
    }

    public String getBindCompanyId() {
        return bindCompanyId;
    }

    public void setBindCompanyId(String bindCompanyId) {
        this.bindCompanyId = bindCompanyId;
    }

    public String getBindCompanyName() {
        return bindCompanyName;
    }

    public void setBindCompanyName(String bindCompanyName) {
        this.bindCompanyName = bindCompanyName;
    }

    public String getBindUserId() {
        return bindUserId;
    }

    public void setBindUserId(String bindUserId) {
        this.bindUserId = bindUserId;
    }

    public String getBindUserName() {
        return bindUserName;
    }

    public void setBindUserName(String bindUserName) {
        this.bindUserName = bindUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getExclusiveCode() {
        return exclusiveCode;
    }

    public void setExclusiveCode(String exclusiveCode) {
        this.exclusiveCode = exclusiveCode;
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

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
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

    public String getRealProgress() {
        return realProgress;
    }

    public void setRealProgress(String realProgress) {
        this.realProgress = realProgress;
    }

    public String getRepayBegin() {
        return repayBegin;
    }

    public void setRepayBegin(String repayBegin) {
        this.repayBegin = repayBegin;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public String getValueBegin() {
        return valueBegin;
    }

    public void setValueBegin(String valueBegin) {
        this.valueBegin = valueBegin;
    }

    public String getYMInterest() {
        return yMInterest;
    }

    public void setYMInterest(String yMInterest) {
        this.yMInterest = yMInterest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RDNSYHERJZ that = (RDNSYHERJZ) o;

        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (bankInterest != null ? !bankInterest.equals(that.bankInterest) : that.bankInterest != null) return false;
        if (bindCompanyId != null ? !bindCompanyId.equals(that.bindCompanyId) : that.bindCompanyId != null)
            return false;
        if (bindCompanyName != null ? !bindCompanyName.equals(that.bindCompanyName) : that.bindCompanyName != null)
            return false;
        if (bindUserId != null ? !bindUserId.equals(that.bindUserId) : that.bindUserId != null) return false;
        if (bindUserName != null ? !bindUserName.equals(that.bindUserName) : that.bindUserName != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (exclusiveCode != null ? !exclusiveCode.equals(that.exclusiveCode) : that.exclusiveCode != null)
            return false;
        if (financeApplyStatus != null ? !financeApplyStatus.equals(that.financeApplyStatus) : that.financeApplyStatus != null)
            return false;
        if (financingId != null ? !financingId.equals(that.financingId) : that.financingId != null) return false;
        if (hotStatus != null ? !hotStatus.equals(that.hotStatus) : that.hotStatus != null) return false;
        if (investmentInterest != null ? !investmentInterest.equals(that.investmentInterest) : that.investmentInterest != null)
            return false;
        if (isExclusivePublic != null ? !isExclusivePublic.equals(that.isExclusivePublic) : that.isExclusivePublic != null)
            return false;
        if (isShow != null ? !isShow.equals(that.isShow) : that.isShow != null) return false;
        if (jMBeginTime != null ? !jMBeginTime.equals(that.jMBeginTime) : that.jMBeginTime != null) return false;
        if (minInvestPartsCount != null ? !minInvestPartsCount.equals(that.minInvestPartsCount) : that.minInvestPartsCount != null)
            return false;
        if (partsCount != null ? !partsCount.equals(that.partsCount) : that.partsCount != null) return false;
        if (progress != null ? !progress.equals(that.progress) : that.progress != null) return false;
        if (projectBeginTime != null ? !projectBeginTime.equals(that.projectBeginTime) : that.projectBeginTime != null)
            return false;
        if (projectCode != null ? !projectCode.equals(that.projectCode) : that.projectCode != null) return false;
        if (projectName != null ? !projectName.equals(that.projectName) : that.projectName != null) return false;
        if (projectStatus != null ? !projectStatus.equals(that.projectStatus) : that.projectStatus != null)
            return false;
        if (projectType != null ? !projectType.equals(that.projectType) : that.projectType != null) return false;
        if (readyBeginTime != null ? !readyBeginTime.equals(that.readyBeginTime) : that.readyBeginTime != null)
            return false;
        if (realProgress != null ? !realProgress.equals(that.realProgress) : that.realProgress != null) return false;
        if (repayBegin != null ? !repayBegin.equals(that.repayBegin) : that.repayBegin != null) return false;
        if (repayType != null ? !repayType.equals(that.repayType) : that.repayType != null) return false;
        if (valueBegin != null ? !valueBegin.equals(that.valueBegin) : that.valueBegin != null) return false;
        if (yMInterest != null ? !yMInterest.equals(that.yMInterest) : that.yMInterest != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (bankInterest != null ? bankInterest.hashCode() : 0);
        result = 31 * result + (bindCompanyId != null ? bindCompanyId.hashCode() : 0);
        result = 31 * result + (bindCompanyName != null ? bindCompanyName.hashCode() : 0);
        result = 31 * result + (bindUserId != null ? bindUserId.hashCode() : 0);
        result = 31 * result + (bindUserName != null ? bindUserName.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (exclusiveCode != null ? exclusiveCode.hashCode() : 0);
        result = 31 * result + (financeApplyStatus != null ? financeApplyStatus.hashCode() : 0);
        result = 31 * result + (financingId != null ? financingId.hashCode() : 0);
        result = 31 * result + (hotStatus != null ? hotStatus.hashCode() : 0);
        result = 31 * result + (investmentInterest != null ? investmentInterest.hashCode() : 0);
        result = 31 * result + (isExclusivePublic != null ? isExclusivePublic.hashCode() : 0);
        result = 31 * result + (isShow != null ? isShow.hashCode() : 0);
        result = 31 * result + (jMBeginTime != null ? jMBeginTime.hashCode() : 0);
        result = 31 * result + (minInvestPartsCount != null ? minInvestPartsCount.hashCode() : 0);
        result = 31 * result + (partsCount != null ? partsCount.hashCode() : 0);
        result = 31 * result + (progress != null ? progress.hashCode() : 0);
        result = 31 * result + (projectBeginTime != null ? projectBeginTime.hashCode() : 0);
        result = 31 * result + (projectCode != null ? projectCode.hashCode() : 0);
        result = 31 * result + (projectName != null ? projectName.hashCode() : 0);
        result = 31 * result + (projectStatus != null ? projectStatus.hashCode() : 0);
        result = 31 * result + (projectType != null ? projectType.hashCode() : 0);
        result = 31 * result + (readyBeginTime != null ? readyBeginTime.hashCode() : 0);
        result = 31 * result + (realProgress != null ? realProgress.hashCode() : 0);
        result = 31 * result + (repayBegin != null ? repayBegin.hashCode() : 0);
        result = 31 * result + (repayType != null ? repayType.hashCode() : 0);
        result = 31 * result + (valueBegin != null ? valueBegin.hashCode() : 0);
        result = 31 * result + (yMInterest != null ? yMInterest.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RDNSYHERJZ{");
        sb.append("amount='").append(amount).append('\'');
        sb.append(", bankInterest='").append(bankInterest).append('\'');
        sb.append(", bindCompanyId='").append(bindCompanyId).append('\'');
        sb.append(", bindCompanyName='").append(bindCompanyName).append('\'');
        sb.append(", bindUserId='").append(bindUserId).append('\'');
        sb.append(", bindUserName='").append(bindUserName).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", duration='").append(duration).append('\'');
        sb.append(", exclusiveCode='").append(exclusiveCode).append('\'');
        sb.append(", financeApplyStatus='").append(financeApplyStatus).append('\'');
        sb.append(", financingId='").append(financingId).append('\'');
        sb.append(", hotStatus='").append(hotStatus).append('\'');
        sb.append(", investmentInterest='").append(investmentInterest).append('\'');
        sb.append(", isExclusivePublic='").append(isExclusivePublic).append('\'');
        sb.append(", isShow='").append(isShow).append('\'');
        sb.append(", jMBeginTime='").append(jMBeginTime).append('\'');
        sb.append(", minInvestPartsCount='").append(minInvestPartsCount).append('\'');
        sb.append(", partsCount='").append(partsCount).append('\'');
        sb.append(", progress='").append(progress).append('\'');
        sb.append(", projectBeginTime='").append(projectBeginTime).append('\'');
        sb.append(", projectCode='").append(projectCode).append('\'');
        sb.append(", projectName='").append(projectName).append('\'');
        sb.append(", projectStatus='").append(projectStatus).append('\'');
        sb.append(", projectType='").append(projectType).append('\'');
        sb.append(", readyBeginTime='").append(readyBeginTime).append('\'');
        sb.append(", realProgress='").append(realProgress).append('\'');
        sb.append(", repayBegin='").append(repayBegin).append('\'');
        sb.append(", repayType='").append(repayType).append('\'');
        sb.append(", valueBegin='").append(valueBegin).append('\'');
        sb.append(", yMInterest='").append(yMInterest).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
