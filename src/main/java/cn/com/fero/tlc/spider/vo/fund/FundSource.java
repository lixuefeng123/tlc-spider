package cn.com.fero.tlc.spider.vo.fund;

/**
 * Created by wanghongmeng on 2015/8/11.
 */
public class FundSource {
    private String id;
    private String fund_code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFund_code() {
        return fund_code;
    }

    public void setFund_code(String fund_code) {
        this.fund_code = fund_code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FundSource that = (FundSource) o;

        if (fund_code != null ? !fund_code.equals(that.fund_code) : that.fund_code != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fund_code != null ? fund_code.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FoundSource{");
        sb.append("id='").append(id).append('\'');
        sb.append(", fund_code='").append(fund_code).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
