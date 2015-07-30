package cn.com.fero.tlc.spider.vo;

/**
 * Created by wanghongmeng on 2015/6/25.
 */
public class CZNSYH {
    private String id;
    private String name;
    private String borrowerId;
    private String number;
    private String typeId;
    private String increaseTypeId;
    private String originAgency;
    private String amount;
    private String yieldRate;
    private String minInvestAmount;
    private String sellLimit;
    private String yieldDays;
    private String loanPeriod;
    private String presaleDateStart;
    private String presaleDateEnd;
    private String tenderDateStart;
    private String tenderDateEnd;
    private String status;
    private String loanDate;
    private String repaymentDate;
    private String settlementDate;
    private String createTime;
    private String updateTime;
    private String remainMoney;
    private String percent;
    private String realStatus;
    private String interestDay;
    private String vip;
    private ProjectFinancle projectFinancle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getIncreaseTypeId() {
        return increaseTypeId;
    }

    public void setIncreaseTypeId(String increaseTypeId) {
        this.increaseTypeId = increaseTypeId;
    }

    public String getOriginAgency() {
        return originAgency;
    }

    public void setOriginAgency(String originAgency) {
        this.originAgency = originAgency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getYieldRate() {
        return yieldRate;
    }

    public void setYieldRate(String yieldRate) {
        this.yieldRate = yieldRate;
    }

    public String getMinInvestAmount() {
        return minInvestAmount;
    }

    public void setMinInvestAmount(String minInvestAmount) {
        this.minInvestAmount = minInvestAmount;
    }

    public String getSellLimit() {
        return sellLimit;
    }

    public void setSellLimit(String sellLimit) {
        this.sellLimit = sellLimit;
    }

    public String getYieldDays() {
        return yieldDays;
    }

    public void setYieldDays(String yieldDays) {
        this.yieldDays = yieldDays;
    }

    public String getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(String loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public String getPresaleDateStart() {
        return presaleDateStart;
    }

    public void setPresaleDateStart(String presaleDateStart) {
        this.presaleDateStart = presaleDateStart;
    }

    public String getPresaleDateEnd() {
        return presaleDateEnd;
    }

    public void setPresaleDateEnd(String presaleDateEnd) {
        this.presaleDateEnd = presaleDateEnd;
    }

    public String getTenderDateStart() {
        return tenderDateStart;
    }

    public void setTenderDateStart(String tenderDateStart) {
        this.tenderDateStart = tenderDateStart;
    }

    public String getTenderDateEnd() {
        return tenderDateEnd;
    }

    public void setTenderDateEnd(String tenderDateEnd) {
        this.tenderDateEnd = tenderDateEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(String remainMoney) {
        this.remainMoney = remainMoney;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getRealStatus() {
        return realStatus;
    }

    public void setRealStatus(String realStatus) {
        this.realStatus = realStatus;
    }

    public String getInterestDay() {
        return interestDay;
    }

    public void setInterestDay(String interestDay) {
        this.interestDay = interestDay;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public ProjectFinancle getProjectFinancle() {
        return projectFinancle;
    }

    public void setProjectFinancle(ProjectFinancle projectFinancle) {
        this.projectFinancle = projectFinancle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CZNSYH cznsyh = (CZNSYH) o;

        if (amount != null ? !amount.equals(cznsyh.amount) : cznsyh.amount != null) return false;
        if (borrowerId != null ? !borrowerId.equals(cznsyh.borrowerId) : cznsyh.borrowerId != null) return false;
        if (createTime != null ? !createTime.equals(cznsyh.createTime) : cznsyh.createTime != null) return false;
        if (id != null ? !id.equals(cznsyh.id) : cznsyh.id != null) return false;
        if (increaseTypeId != null ? !increaseTypeId.equals(cznsyh.increaseTypeId) : cznsyh.increaseTypeId != null)
            return false;
        if (interestDay != null ? !interestDay.equals(cznsyh.interestDay) : cznsyh.interestDay != null) return false;
        if (loanDate != null ? !loanDate.equals(cznsyh.loanDate) : cznsyh.loanDate != null) return false;
        if (loanPeriod != null ? !loanPeriod.equals(cznsyh.loanPeriod) : cznsyh.loanPeriod != null) return false;
        if (minInvestAmount != null ? !minInvestAmount.equals(cznsyh.minInvestAmount) : cznsyh.minInvestAmount != null)
            return false;
        if (name != null ? !name.equals(cznsyh.name) : cznsyh.name != null) return false;
        if (number != null ? !number.equals(cznsyh.number) : cznsyh.number != null) return false;
        if (originAgency != null ? !originAgency.equals(cznsyh.originAgency) : cznsyh.originAgency != null)
            return false;
        if (percent != null ? !percent.equals(cznsyh.percent) : cznsyh.percent != null) return false;
        if (presaleDateEnd != null ? !presaleDateEnd.equals(cznsyh.presaleDateEnd) : cznsyh.presaleDateEnd != null)
            return false;
        if (presaleDateStart != null ? !presaleDateStart.equals(cznsyh.presaleDateStart) : cznsyh.presaleDateStart != null)
            return false;
        if (projectFinancle != null ? !projectFinancle.equals(cznsyh.projectFinancle) : cznsyh.projectFinancle != null)
            return false;
        if (realStatus != null ? !realStatus.equals(cznsyh.realStatus) : cznsyh.realStatus != null) return false;
        if (remainMoney != null ? !remainMoney.equals(cznsyh.remainMoney) : cznsyh.remainMoney != null) return false;
        if (repaymentDate != null ? !repaymentDate.equals(cznsyh.repaymentDate) : cznsyh.repaymentDate != null)
            return false;
        if (sellLimit != null ? !sellLimit.equals(cznsyh.sellLimit) : cznsyh.sellLimit != null) return false;
        if (settlementDate != null ? !settlementDate.equals(cznsyh.settlementDate) : cznsyh.settlementDate != null)
            return false;
        if (status != null ? !status.equals(cznsyh.status) : cznsyh.status != null) return false;
        if (tenderDateEnd != null ? !tenderDateEnd.equals(cznsyh.tenderDateEnd) : cznsyh.tenderDateEnd != null)
            return false;
        if (tenderDateStart != null ? !tenderDateStart.equals(cznsyh.tenderDateStart) : cznsyh.tenderDateStart != null)
            return false;
        if (typeId != null ? !typeId.equals(cznsyh.typeId) : cznsyh.typeId != null) return false;
        if (updateTime != null ? !updateTime.equals(cznsyh.updateTime) : cznsyh.updateTime != null) return false;
        if (vip != null ? !vip.equals(cznsyh.vip) : cznsyh.vip != null) return false;
        if (yieldDays != null ? !yieldDays.equals(cznsyh.yieldDays) : cznsyh.yieldDays != null) return false;
        if (yieldRate != null ? !yieldRate.equals(cznsyh.yieldRate) : cznsyh.yieldRate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (borrowerId != null ? borrowerId.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (typeId != null ? typeId.hashCode() : 0);
        result = 31 * result + (increaseTypeId != null ? increaseTypeId.hashCode() : 0);
        result = 31 * result + (originAgency != null ? originAgency.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (yieldRate != null ? yieldRate.hashCode() : 0);
        result = 31 * result + (minInvestAmount != null ? minInvestAmount.hashCode() : 0);
        result = 31 * result + (sellLimit != null ? sellLimit.hashCode() : 0);
        result = 31 * result + (yieldDays != null ? yieldDays.hashCode() : 0);
        result = 31 * result + (loanPeriod != null ? loanPeriod.hashCode() : 0);
        result = 31 * result + (presaleDateStart != null ? presaleDateStart.hashCode() : 0);
        result = 31 * result + (presaleDateEnd != null ? presaleDateEnd.hashCode() : 0);
        result = 31 * result + (tenderDateStart != null ? tenderDateStart.hashCode() : 0);
        result = 31 * result + (tenderDateEnd != null ? tenderDateEnd.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (loanDate != null ? loanDate.hashCode() : 0);
        result = 31 * result + (repaymentDate != null ? repaymentDate.hashCode() : 0);
        result = 31 * result + (settlementDate != null ? settlementDate.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (remainMoney != null ? remainMoney.hashCode() : 0);
        result = 31 * result + (percent != null ? percent.hashCode() : 0);
        result = 31 * result + (realStatus != null ? realStatus.hashCode() : 0);
        result = 31 * result + (interestDay != null ? interestDay.hashCode() : 0);
        result = 31 * result + (vip != null ? vip.hashCode() : 0);
        result = 31 * result + (projectFinancle != null ? projectFinancle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CZNSYH{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", borrowerId='").append(borrowerId).append('\'');
        sb.append(", number='").append(number).append('\'');
        sb.append(", typeId='").append(typeId).append('\'');
        sb.append(", increaseTypeId='").append(increaseTypeId).append('\'');
        sb.append(", originAgency='").append(originAgency).append('\'');
        sb.append(", amount='").append(amount).append('\'');
        sb.append(", yieldRate='").append(yieldRate).append('\'');
        sb.append(", minInvestAmount='").append(minInvestAmount).append('\'');
        sb.append(", sellLimit='").append(sellLimit).append('\'');
        sb.append(", yieldDays='").append(yieldDays).append('\'');
        sb.append(", loanPeriod='").append(loanPeriod).append('\'');
        sb.append(", presaleDateStart='").append(presaleDateStart).append('\'');
        sb.append(", presaleDateEnd='").append(presaleDateEnd).append('\'');
        sb.append(", tenderDateStart='").append(tenderDateStart).append('\'');
        sb.append(", tenderDateEnd='").append(tenderDateEnd).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", loanDate='").append(loanDate).append('\'');
        sb.append(", repaymentDate='").append(repaymentDate).append('\'');
        sb.append(", settlementDate='").append(settlementDate).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", updateTime='").append(updateTime).append('\'');
        sb.append(", remainMoney='").append(remainMoney).append('\'');
        sb.append(", percent='").append(percent).append('\'');
        sb.append(", realStatus='").append(realStatus).append('\'');
        sb.append(", interestDay='").append(interestDay).append('\'');
        sb.append(", vip='").append(vip).append('\'');
        sb.append(", projectFinancle=").append(projectFinancle);
        sb.append('}');
        return sb.toString();
    }
}