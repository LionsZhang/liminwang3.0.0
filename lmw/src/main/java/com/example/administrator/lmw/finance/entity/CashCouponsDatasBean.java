package com.example.administrator.lmw.finance.entity;

/**
 * 现金券
 * <p/>
 * Created by Administrator on 2016/9/6 0006.
 */
public class CashCouponsDatasBean {


    /**
     * useRule : 单笔投资>=1000.00
     * couponId : 11
     * couponCode : code11
     * couponAmount : 10.00
     * acquireSource :
     * useScope : 理财产品
     * endTime : 2016-11-04 20:01:13
     */

    private String useRule;
    private String couponId;
    private String couponCode;
    private String couponAmount;
    private String acquireSource;
    private String useScope;
    private String endTime;
    private String minInvestDeadline;

    public String getUseRule() {
        return useRule;
    }

    public void setUseRule(String useRule) {
        this.useRule = useRule;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getAcquireSource() {
        return acquireSource;
    }

    public void setAcquireSource(String acquireSource) {
        this.acquireSource = acquireSource;
    }

    public String getUseScope() {
        return useScope;
    }

    public void setUseScope(String useScope) {
        this.useScope = useScope;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMinInvestDeadline() {
        return minInvestDeadline;
    }

    public void setMinInvestDeadline(String minInvestDeadline) {
        this.minInvestDeadline = minInvestDeadline;
    }
}
