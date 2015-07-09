package cn.com.fero.tlc.spider.vo;

/**
 * Created by wanghongmeng on 2015/7/9.
 */
public class PFYH {
    private String P2BProjectStatus;
    private String InterestRateExpect;
    private String AmountBegin;
    private String AmountBal;
    private String AmountTarget;
    private String InvestDuration;
    private String BorrowerType;
    private String RaiseBeginTime;
    private String ProjectCode;
    private String ProjectId;

    public String getP2BProjectStatus() {
        return P2BProjectStatus;
    }

    public void setP2BProjectStatus(String p2BProjectStatus) {
        P2BProjectStatus = p2BProjectStatus;
    }

    public String getInterestRateExpect() {
        return InterestRateExpect;
    }

    public void setInterestRateExpect(String interestRateExpect) {
        InterestRateExpect = interestRateExpect;
    }

    public String getAmountBegin() {
        return AmountBegin;
    }

    public void setAmountBegin(String amountBegin) {
        AmountBegin = amountBegin;
    }

    public String getAmountBal() {
        return AmountBal;
    }

    public void setAmountBal(String amountBal) {
        AmountBal = amountBal;
    }

    public String getAmountTarget() {
        return AmountTarget;
    }

    public void setAmountTarget(String amountTarget) {
        AmountTarget = amountTarget;
    }

    public String getInvestDuration() {
        return InvestDuration;
    }

    public void setInvestDuration(String investDuration) {
        InvestDuration = investDuration;
    }

    public String getBorrowerType() {
        return BorrowerType;
    }

    public void setBorrowerType(String borrowerType) {
        BorrowerType = borrowerType;
    }

    public String getRaiseBeginTime() {
        return RaiseBeginTime;
    }

    public void setRaiseBeginTime(String raiseBeginTime) {
        RaiseBeginTime = raiseBeginTime;
    }

    public String getProjectCode() {
        return ProjectCode;
    }

    public void setProjectCode(String projectCode) {
        ProjectCode = projectCode;
    }

    public String getProjectId() {
        return ProjectId;
    }

    public void setProjectId(String projectId) {
        ProjectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PFYH pfyh = (PFYH) o;

        if (AmountBal != null ? !AmountBal.equals(pfyh.AmountBal) : pfyh.AmountBal != null) return false;
        if (AmountBegin != null ? !AmountBegin.equals(pfyh.AmountBegin) : pfyh.AmountBegin != null) return false;
        if (AmountTarget != null ? !AmountTarget.equals(pfyh.AmountTarget) : pfyh.AmountTarget != null) return false;
        if (BorrowerType != null ? !BorrowerType.equals(pfyh.BorrowerType) : pfyh.BorrowerType != null) return false;
        if (InterestRateExpect != null ? !InterestRateExpect.equals(pfyh.InterestRateExpect) : pfyh.InterestRateExpect != null)
            return false;
        if (InvestDuration != null ? !InvestDuration.equals(pfyh.InvestDuration) : pfyh.InvestDuration != null)
            return false;
        if (P2BProjectStatus != null ? !P2BProjectStatus.equals(pfyh.P2BProjectStatus) : pfyh.P2BProjectStatus != null)
            return false;
        if (ProjectCode != null ? !ProjectCode.equals(pfyh.ProjectCode) : pfyh.ProjectCode != null) return false;
        if (ProjectId != null ? !ProjectId.equals(pfyh.ProjectId) : pfyh.ProjectId != null) return false;
        if (RaiseBeginTime != null ? !RaiseBeginTime.equals(pfyh.RaiseBeginTime) : pfyh.RaiseBeginTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = P2BProjectStatus != null ? P2BProjectStatus.hashCode() : 0;
        result = 31 * result + (InterestRateExpect != null ? InterestRateExpect.hashCode() : 0);
        result = 31 * result + (AmountBegin != null ? AmountBegin.hashCode() : 0);
        result = 31 * result + (AmountBal != null ? AmountBal.hashCode() : 0);
        result = 31 * result + (AmountTarget != null ? AmountTarget.hashCode() : 0);
        result = 31 * result + (InvestDuration != null ? InvestDuration.hashCode() : 0);
        result = 31 * result + (BorrowerType != null ? BorrowerType.hashCode() : 0);
        result = 31 * result + (RaiseBeginTime != null ? RaiseBeginTime.hashCode() : 0);
        result = 31 * result + (ProjectCode != null ? ProjectCode.hashCode() : 0);
        result = 31 * result + (ProjectId != null ? ProjectId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PFYH{");
        sb.append("P2BProjectStatus='").append(P2BProjectStatus).append('\'');
        sb.append(", InterestRateExpect='").append(InterestRateExpect).append('\'');
        sb.append(", AmountBegin='").append(AmountBegin).append('\'');
        sb.append(", AmountBal='").append(AmountBal).append('\'');
        sb.append(", AmountTarget='").append(AmountTarget).append('\'');
        sb.append(", InvestDuration='").append(InvestDuration).append('\'');
        sb.append(", BorrowerType='").append(BorrowerType).append('\'');
        sb.append(", RaiseBeginTime='").append(RaiseBeginTime).append('\'');
        sb.append(", ProjectCode='").append(ProjectCode).append('\'');
        sb.append(", ProjectId='").append(ProjectId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
