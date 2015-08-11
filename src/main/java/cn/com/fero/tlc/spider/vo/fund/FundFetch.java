package cn.com.fero.tlc.spider.vo.fund;

/**
 * Created by lixuefeng on 2015/8/10.
 */
public class FundFetch {
    private String date;
    private String code;
    private String abbreviation;
    private String wanIncome;
    private String sevenNianHua;
    private String nearOneWeek;
    private String nearOneMonth;
    private String nearThreeMonth;
    private String nearSixMonth;
    private String nearOneYear;
    private String nearTwoYear;
    private String nearThreeYear;
    private String thisYear;
    private String establish;
    private String establishDate;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getWanIncome() {
        return wanIncome;
    }

    public void setWanIncome(String wanIncome) {
        this.wanIncome = wanIncome;
    }

    public String getSevenNianHua() {
        return sevenNianHua;
    }

    public void setSevenNianHua(String sevenNianHua) {
        this.sevenNianHua = sevenNianHua;
    }

    public String getNearOneWeek() {
        return nearOneWeek;
    }

    public void setNearOneWeek(String nearOneWeek) {
        this.nearOneWeek = nearOneWeek;
    }

    public String getNearOneMonth() {
        return nearOneMonth;
    }

    public void setNearOneMonth(String nearOneMonth) {
        this.nearOneMonth = nearOneMonth;
    }

    public String getNearThreeMonth() {
        return nearThreeMonth;
    }

    public void setNearThreeMonth(String nearThreeMonth) {
        this.nearThreeMonth = nearThreeMonth;
    }

    public String getNearSixMonth() {
        return nearSixMonth;
    }

    public void setNearSixMonth(String nearSixMonth) {
        this.nearSixMonth = nearSixMonth;
    }

    public String getNearOneYear() {
        return nearOneYear;
    }

    public void setNearOneYear(String nearOneYear) {
        this.nearOneYear = nearOneYear;
    }

    public String getNearTwoYear() {
        return nearTwoYear;
    }

    public void setNearTwoYear(String nearTwoYear) {
        this.nearTwoYear = nearTwoYear;
    }

    public String getNearThreeYear() {
        return nearThreeYear;
    }

    public void setNearThreeYear(String nearThreeYear) {
        this.nearThreeYear = nearThreeYear;
    }

    public String getThisYear() {
        return thisYear;
    }

    public void setThisYear(String thisYear) {
        this.thisYear = thisYear;
    }

    public String getEstablish() {
        return establish;
    }

    public void setEstablish(String establish) {
        this.establish = establish;
    }

    public String getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(String establishDate) {
        this.establishDate = establishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FundFetch that = (FundFetch) o;

        if (abbreviation != null ? !abbreviation.equals(that.abbreviation) : that.abbreviation != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (establish != null ? !establish.equals(that.establish) : that.establish != null) return false;
        if (establishDate != null ? !establishDate.equals(that.establishDate) : that.establishDate != null)
            return false;
        if (nearOneMonth != null ? !nearOneMonth.equals(that.nearOneMonth) : that.nearOneMonth != null) return false;
        if (nearOneWeek != null ? !nearOneWeek.equals(that.nearOneWeek) : that.nearOneWeek != null) return false;
        if (nearOneYear != null ? !nearOneYear.equals(that.nearOneYear) : that.nearOneYear != null) return false;
        if (nearSixMonth != null ? !nearSixMonth.equals(that.nearSixMonth) : that.nearSixMonth != null) return false;
        if (nearThreeMonth != null ? !nearThreeMonth.equals(that.nearThreeMonth) : that.nearThreeMonth != null)
            return false;
        if (nearThreeYear != null ? !nearThreeYear.equals(that.nearThreeYear) : that.nearThreeYear != null)
            return false;
        if (nearTwoYear != null ? !nearTwoYear.equals(that.nearTwoYear) : that.nearTwoYear != null) return false;
        if (sevenNianHua != null ? !sevenNianHua.equals(that.sevenNianHua) : that.sevenNianHua != null) return false;
        if (thisYear != null ? !thisYear.equals(that.thisYear) : that.thisYear != null) return false;
        if (wanIncome != null ? !wanIncome.equals(that.wanIncome) : that.wanIncome != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (abbreviation != null ? abbreviation.hashCode() : 0);
        result = 31 * result + (wanIncome != null ? wanIncome.hashCode() : 0);
        result = 31 * result + (sevenNianHua != null ? sevenNianHua.hashCode() : 0);
        result = 31 * result + (nearOneWeek != null ? nearOneWeek.hashCode() : 0);
        result = 31 * result + (nearOneMonth != null ? nearOneMonth.hashCode() : 0);
        result = 31 * result + (nearThreeMonth != null ? nearThreeMonth.hashCode() : 0);
        result = 31 * result + (nearSixMonth != null ? nearSixMonth.hashCode() : 0);
        result = 31 * result + (nearOneYear != null ? nearOneYear.hashCode() : 0);
        result = 31 * result + (nearTwoYear != null ? nearTwoYear.hashCode() : 0);
        result = 31 * result + (nearThreeYear != null ? nearThreeYear.hashCode() : 0);
        result = 31 * result + (thisYear != null ? thisYear.hashCode() : 0);
        result = 31 * result + (establish != null ? establish.hashCode() : 0);
        result = 31 * result + (establishDate != null ? establishDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FundSource{");
        sb.append("date='").append(date).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(", abbreviation='").append(abbreviation).append('\'');
        sb.append(", wanIncome='").append(wanIncome).append('\'');
        sb.append(", sevenNianHua='").append(sevenNianHua).append('\'');
        sb.append(", nearOneWeek='").append(nearOneWeek).append('\'');
        sb.append(", nearOneMonth='").append(nearOneMonth).append('\'');
        sb.append(", nearThreeMonth='").append(nearThreeMonth).append('\'');
        sb.append(", nearSixMonth='").append(nearSixMonth).append('\'');
        sb.append(", nearOneYear='").append(nearOneYear).append('\'');
        sb.append(", nearTwoYear='").append(nearTwoYear).append('\'');
        sb.append(", nearThreeYear='").append(nearThreeYear).append('\'');
        sb.append(", thisYear='").append(thisYear).append('\'');
        sb.append(", establish='").append(establish).append('\'');
        sb.append(", establishDate='").append(establishDate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
