package com.example.administrator.lmw.mine.invest.entity;

/**
 * Created by snubee on 2017/3/21.
 */

public class TranferRewardBean {

    /**
     * annualRate : 10.00
     * repayDate : 2017-03-08
     * couponType : 3
     * realAmount : 5000.00
     * deadLineValue : 360
     * deadLineType : 1
     * investTime : 2017-03-08 00:00:00
     * borrowTitle : 定期宝172635
     * couponAmount : 3.00
     * increaseRate : 1.00
     * stillInterest : 20.00
     */

    private String annualRate;
    private String repayDate;
    private int couponType;
    private String realAmount;
    private String deadLineValue;
    private int deadLineType;
    private String investTime;
    private String borrowTitle;
    private String couponAmount;
    private String increaseRate;
    private String stillInterest;
    private String maxActiveDay;//卡券有效天数

    public String getAnnualRate() {
        return annualRate;
    }

    public void setAnnualRate(String annualRate) {
        this.annualRate = annualRate;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public String getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(String realAmount) {
        this.realAmount = realAmount;
    }

    public String getDeadLineValue() {
        return deadLineValue;
    }

    public void setDeadLineValue(String deadLineValue) {
        this.deadLineValue = deadLineValue;
    }

    public int getDeadLineType() {
        return deadLineType;
    }

    public void setDeadLineType(int deadLineType) {
        this.deadLineType = deadLineType;
    }

    public String getInvestTime() {
        return investTime;
    }

    public void setInvestTime(String investTime) {
        this.investTime = investTime;
    }

    public String getBorrowTitle() {
        return borrowTitle;
    }

    public void setBorrowTitle(String borrowTitle) {
        this.borrowTitle = borrowTitle;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getIncreaseRate() {
        return increaseRate;
    }

    public void setIncreaseRate(String increaseRate) {
        this.increaseRate = increaseRate;
    }

    public String getStillInterest() {
        return stillInterest;
    }

    public void setStillInterest(String stillInterest) {
        this.stillInterest = stillInterest;
    }

    public String getMaxActiveDay() {
        return maxActiveDay;
    }

    public void setMaxActiveDay(String maxActiveDay) {
        this.maxActiveDay = maxActiveDay;
    }
}
