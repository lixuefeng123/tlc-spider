package cn.com.fero.tlc.spider.vo.fund;

/**
 * Created by lixuefeng on 2015/8/10.
 */
public class FundFetch {
    private String publishDate;
    private String fundCode;
    private String fundNick;
    private String tenThousandInterest;
    private String weekInterest;
    private String weekEarning;
    private String monthEarning;
    private String threeMonthEarning;
    private String halfYearEarning;
    private String yearEarning;
    private String twoYearEarning;
    private String threeYearEarning;
    private String currentYearEarning;
    private String createdEarning;
    private String createdDate;

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getFundNick() {
        return fundNick;
    }

    public void setFundNick(String fundNick) {
        this.fundNick = fundNick;
    }

    public String getTenThousandInterest() {
        return tenThousandInterest;
    }

    public void setTenThousandInterest(String tenThousandInterest) {
        this.tenThousandInterest = tenThousandInterest;
    }

    public String getWeekInterest() {
        return weekInterest;
    }

    public void setWeekInterest(String weekInterest) {
        this.weekInterest = weekInterest;
    }

    public String getWeekEarning() {
        return weekEarning;
    }

    public void setWeekEarning(String weekEarning) {
        this.weekEarning = weekEarning;
    }

    public String getMonthEarning() {
        return monthEarning;
    }

    public void setMonthEarning(String monthEarning) {
        this.monthEarning = monthEarning;
    }

    public String getThreeMonthEarning() {
        return threeMonthEarning;
    }

    public void setThreeMonthEarning(String threeMonthEarning) {
        this.threeMonthEarning = threeMonthEarning;
    }

    public String getHalfYearEarning() {
        return halfYearEarning;
    }

    public void setHalfYearEarning(String halfYearEarning) {
        this.halfYearEarning = halfYearEarning;
    }

    public String getYearEarning() {
        return yearEarning;
    }

    public void setYearEarning(String yearEarning) {
        this.yearEarning = yearEarning;
    }

    public String getTwoYearEarning() {
        return twoYearEarning;
    }

    public void setTwoYearEarning(String twoYearEarning) {
        this.twoYearEarning = twoYearEarning;
    }

    public String getThreeYearEarning() {
        return threeYearEarning;
    }

    public void setThreeYearEarning(String threeYearEarning) {
        this.threeYearEarning = threeYearEarning;
    }

    public String getCurrentYearEarning() {
        return currentYearEarning;
    }

    public void setCurrentYearEarning(String currentYearEarning) {
        this.currentYearEarning = currentYearEarning;
    }

    public String getCreatedEarning() {
        return createdEarning;
    }

    public void setCreatedEarning(String createdEarning) {
        this.createdEarning = createdEarning;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FundFetch fundFetch = (FundFetch) o;

        if (createdDate != null ? !createdDate.equals(fundFetch.createdDate) : fundFetch.createdDate != null)
            return false;
        if (createdEarning != null ? !createdEarning.equals(fundFetch.createdEarning) : fundFetch.createdEarning != null)
            return false;
        if (currentYearEarning != null ? !currentYearEarning.equals(fundFetch.currentYearEarning) : fundFetch.currentYearEarning != null)
            return false;
        if (fundCode != null ? !fundCode.equals(fundFetch.fundCode) : fundFetch.fundCode != null) return false;
        if (fundNick != null ? !fundNick.equals(fundFetch.fundNick) : fundFetch.fundNick != null) return false;
        if (halfYearEarning != null ? !halfYearEarning.equals(fundFetch.halfYearEarning) : fundFetch.halfYearEarning != null)
            return false;
        if (monthEarning != null ? !monthEarning.equals(fundFetch.monthEarning) : fundFetch.monthEarning != null)
            return false;
        if (publishDate != null ? !publishDate.equals(fundFetch.publishDate) : fundFetch.publishDate != null)
            return false;
        if (tenThousandInterest != null ? !tenThousandInterest.equals(fundFetch.tenThousandInterest) : fundFetch.tenThousandInterest != null)
            return false;
        if (threeMonthEarning != null ? !threeMonthEarning.equals(fundFetch.threeMonthEarning) : fundFetch.threeMonthEarning != null)
            return false;
        if (threeYearEarning != null ? !threeYearEarning.equals(fundFetch.threeYearEarning) : fundFetch.threeYearEarning != null)
            return false;
        if (twoYearEarning != null ? !twoYearEarning.equals(fundFetch.twoYearEarning) : fundFetch.twoYearEarning != null)
            return false;
        if (weekEarning != null ? !weekEarning.equals(fundFetch.weekEarning) : fundFetch.weekEarning != null)
            return false;
        if (weekInterest != null ? !weekInterest.equals(fundFetch.weekInterest) : fundFetch.weekInterest != null)
            return false;
        if (yearEarning != null ? !yearEarning.equals(fundFetch.yearEarning) : fundFetch.yearEarning != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = publishDate != null ? publishDate.hashCode() : 0;
        result = 31 * result + (fundCode != null ? fundCode.hashCode() : 0);
        result = 31 * result + (fundNick != null ? fundNick.hashCode() : 0);
        result = 31 * result + (tenThousandInterest != null ? tenThousandInterest.hashCode() : 0);
        result = 31 * result + (weekInterest != null ? weekInterest.hashCode() : 0);
        result = 31 * result + (weekEarning != null ? weekEarning.hashCode() : 0);
        result = 31 * result + (monthEarning != null ? monthEarning.hashCode() : 0);
        result = 31 * result + (threeMonthEarning != null ? threeMonthEarning.hashCode() : 0);
        result = 31 * result + (halfYearEarning != null ? halfYearEarning.hashCode() : 0);
        result = 31 * result + (yearEarning != null ? yearEarning.hashCode() : 0);
        result = 31 * result + (twoYearEarning != null ? twoYearEarning.hashCode() : 0);
        result = 31 * result + (threeYearEarning != null ? threeYearEarning.hashCode() : 0);
        result = 31 * result + (currentYearEarning != null ? currentYearEarning.hashCode() : 0);
        result = 31 * result + (createdEarning != null ? createdEarning.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FundFetch{");
        sb.append("publishDate='").append(publishDate).append('\'');
        sb.append(", fundCode='").append(fundCode).append('\'');
        sb.append(", fundNick='").append(fundNick).append('\'');
        sb.append(", tenThousandInterest='").append(tenThousandInterest).append('\'');
        sb.append(", weekInterest='").append(weekInterest).append('\'');
        sb.append(", weekEarning='").append(weekEarning).append('\'');
        sb.append(", monthEarning='").append(monthEarning).append('\'');
        sb.append(", threeMonthEarning='").append(threeMonthEarning).append('\'');
        sb.append(", halfYearEarning='").append(halfYearEarning).append('\'');
        sb.append(", yearEarning='").append(yearEarning).append('\'');
        sb.append(", twoYearEarning='").append(twoYearEarning).append('\'');
        sb.append(", threeYearEarning='").append(threeYearEarning).append('\'');
        sb.append(", currentYearEarning='").append(currentYearEarning).append('\'');
        sb.append(", createdEarning='").append(createdEarning).append('\'');
        sb.append(", createdDate='").append(createdDate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
