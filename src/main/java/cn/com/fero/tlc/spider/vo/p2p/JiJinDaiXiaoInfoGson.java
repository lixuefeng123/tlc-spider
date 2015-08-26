package cn.com.fero.tlc.spider.vo.p2p;

import java.util.Arrays;

/**
 * Created by lixuefeng on 2015/8/26.
 */
public class JiJinDaiXiaoInfoGson {
    private Object[] jijinExBuyLimitGsons;
    private Object[] jijinExDiscountGsons;
    private Object[] jijinExFeeGsons;
    private Object[] jijinExNetValueGsons;
    private Object[] jijinExNetValueAllGsons;
    private Object[] jijinExGradeGsons;
    private Object[] jijinExDividendGsons;
    private Object[] jijinExMfPerformGsons;
    private Object[] jijinExFxPerformGsons;
    private Object[] jijinExGoodSubjectGsons;
    private Object[] jijinExHotSubjectGsons;
    private Object[] jijinExManagerGsons;
    private Object[] applyProductFeeGsons;
    private Object[] redemptionProductFeeGsons;

    public Object[] getJijinExBuyLimitGsons() {
        return jijinExBuyLimitGsons;
    }

    public void setJijinExBuyLimitGsons(Object[] jijinExBuyLimitGsons) {
        this.jijinExBuyLimitGsons = jijinExBuyLimitGsons;
    }

    public Object[] getJijinExDiscountGsons() {
        return jijinExDiscountGsons;
    }

    public void setJijinExDiscountGsons(Object[] jijinExDiscountGsons) {
        this.jijinExDiscountGsons = jijinExDiscountGsons;
    }

    public Object[] getJijinExFeeGsons() {
        return jijinExFeeGsons;
    }

    public void setJijinExFeeGsons(Object[] jijinExFeeGsons) {
        this.jijinExFeeGsons = jijinExFeeGsons;
    }

    public Object[] getJijinExNetValueGsons() {
        return jijinExNetValueGsons;
    }

    public void setJijinExNetValueGsons(Object[] jijinExNetValueGsons) {
        this.jijinExNetValueGsons = jijinExNetValueGsons;
    }

    public Object[] getJijinExNetValueAllGsons() {
        return jijinExNetValueAllGsons;
    }

    public void setJijinExNetValueAllGsons(Object[] jijinExNetValueAllGsons) {
        this.jijinExNetValueAllGsons = jijinExNetValueAllGsons;
    }

    public Object[] getJijinExGradeGsons() {
        return jijinExGradeGsons;
    }

    public void setJijinExGradeGsons(Object[] jijinExGradeGsons) {
        this.jijinExGradeGsons = jijinExGradeGsons;
    }

    public Object[] getJijinExDividendGsons() {
        return jijinExDividendGsons;
    }

    public void setJijinExDividendGsons(Object[] jijinExDividendGsons) {
        this.jijinExDividendGsons = jijinExDividendGsons;
    }

    public Object[] getJijinExMfPerformGsons() {
        return jijinExMfPerformGsons;
    }

    public void setJijinExMfPerformGsons(Object[] jijinExMfPerformGsons) {
        this.jijinExMfPerformGsons = jijinExMfPerformGsons;
    }

    public Object[] getJijinExFxPerformGsons() {
        return jijinExFxPerformGsons;
    }

    public void setJijinExFxPerformGsons(Object[] jijinExFxPerformGsons) {
        this.jijinExFxPerformGsons = jijinExFxPerformGsons;
    }

    public Object[] getJijinExGoodSubjectGsons() {
        return jijinExGoodSubjectGsons;
    }

    public void setJijinExGoodSubjectGsons(Object[] jijinExGoodSubjectGsons) {
        this.jijinExGoodSubjectGsons = jijinExGoodSubjectGsons;
    }

    public Object[] getJijinExHotSubjectGsons() {
        return jijinExHotSubjectGsons;
    }

    public void setJijinExHotSubjectGsons(Object[] jijinExHotSubjectGsons) {
        this.jijinExHotSubjectGsons = jijinExHotSubjectGsons;
    }

    public Object[] getJijinExManagerGsons() {
        return jijinExManagerGsons;
    }

    public void setJijinExManagerGsons(Object[] jijinExManagerGsons) {
        this.jijinExManagerGsons = jijinExManagerGsons;
    }

    public Object[] getApplyProductFeeGsons() {
        return applyProductFeeGsons;
    }

    public void setApplyProductFeeGsons(Object[] applyProductFeeGsons) {
        this.applyProductFeeGsons = applyProductFeeGsons;
    }

    public Object[] getRedemptionProductFeeGsons() {
        return redemptionProductFeeGsons;
    }

    public void setRedemptionProductFeeGsons(Object[] redemptionProductFeeGsons) {
        this.redemptionProductFeeGsons = redemptionProductFeeGsons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JiJinDaiXiaoInfoGson that = (JiJinDaiXiaoInfoGson) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(applyProductFeeGsons, that.applyProductFeeGsons)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(jijinExBuyLimitGsons, that.jijinExBuyLimitGsons)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(jijinExDiscountGsons, that.jijinExDiscountGsons)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(jijinExDividendGsons, that.jijinExDividendGsons)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(jijinExFeeGsons, that.jijinExFeeGsons)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(jijinExFxPerformGsons, that.jijinExFxPerformGsons)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(jijinExGoodSubjectGsons, that.jijinExGoodSubjectGsons)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(jijinExGradeGsons, that.jijinExGradeGsons)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(jijinExHotSubjectGsons, that.jijinExHotSubjectGsons)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(jijinExManagerGsons, that.jijinExManagerGsons)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(jijinExMfPerformGsons, that.jijinExMfPerformGsons)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(jijinExNetValueAllGsons, that.jijinExNetValueAllGsons)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(jijinExNetValueGsons, that.jijinExNetValueGsons)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(redemptionProductFeeGsons, that.redemptionProductFeeGsons)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = jijinExBuyLimitGsons != null ? Arrays.hashCode(jijinExBuyLimitGsons) : 0;
        result = 31 * result + (jijinExDiscountGsons != null ? Arrays.hashCode(jijinExDiscountGsons) : 0);
        result = 31 * result + (jijinExFeeGsons != null ? Arrays.hashCode(jijinExFeeGsons) : 0);
        result = 31 * result + (jijinExNetValueGsons != null ? Arrays.hashCode(jijinExNetValueGsons) : 0);
        result = 31 * result + (jijinExNetValueAllGsons != null ? Arrays.hashCode(jijinExNetValueAllGsons) : 0);
        result = 31 * result + (jijinExGradeGsons != null ? Arrays.hashCode(jijinExGradeGsons) : 0);
        result = 31 * result + (jijinExDividendGsons != null ? Arrays.hashCode(jijinExDividendGsons) : 0);
        result = 31 * result + (jijinExMfPerformGsons != null ? Arrays.hashCode(jijinExMfPerformGsons) : 0);
        result = 31 * result + (jijinExFxPerformGsons != null ? Arrays.hashCode(jijinExFxPerformGsons) : 0);
        result = 31 * result + (jijinExGoodSubjectGsons != null ? Arrays.hashCode(jijinExGoodSubjectGsons) : 0);
        result = 31 * result + (jijinExHotSubjectGsons != null ? Arrays.hashCode(jijinExHotSubjectGsons) : 0);
        result = 31 * result + (jijinExManagerGsons != null ? Arrays.hashCode(jijinExManagerGsons) : 0);
        result = 31 * result + (applyProductFeeGsons != null ? Arrays.hashCode(applyProductFeeGsons) : 0);
        result = 31 * result + (redemptionProductFeeGsons != null ? Arrays.hashCode(redemptionProductFeeGsons) : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JiJinDaiXiaoInfoGson{");
        sb.append("jijinExBuyLimitGsons=").append(Arrays.toString(jijinExBuyLimitGsons));
        sb.append(", jijinExDiscountGsons=").append(Arrays.toString(jijinExDiscountGsons));
        sb.append(", jijinExFeeGsons=").append(Arrays.toString(jijinExFeeGsons));
        sb.append(", jijinExNetValueGsons=").append(Arrays.toString(jijinExNetValueGsons));
        sb.append(", jijinExNetValueAllGsons=").append(Arrays.toString(jijinExNetValueAllGsons));
        sb.append(", jijinExGradeGsons=").append(Arrays.toString(jijinExGradeGsons));
        sb.append(", jijinExDividendGsons=").append(Arrays.toString(jijinExDividendGsons));
        sb.append(", jijinExMfPerformGsons=").append(Arrays.toString(jijinExMfPerformGsons));
        sb.append(", jijinExFxPerformGsons=").append(Arrays.toString(jijinExFxPerformGsons));
        sb.append(", jijinExGoodSubjectGsons=").append(Arrays.toString(jijinExGoodSubjectGsons));
        sb.append(", jijinExHotSubjectGsons=").append(Arrays.toString(jijinExHotSubjectGsons));
        sb.append(", jijinExManagerGsons=").append(Arrays.toString(jijinExManagerGsons));
        sb.append(", applyProductFeeGsons=").append(Arrays.toString(applyProductFeeGsons));
        sb.append(", redemptionProductFeeGsons=").append(Arrays.toString(redemptionProductFeeGsons));
        sb.append('}');
        return sb.toString();
    }
}
