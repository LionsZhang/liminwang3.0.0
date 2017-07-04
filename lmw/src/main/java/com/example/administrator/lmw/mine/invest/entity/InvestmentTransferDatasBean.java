package com.example.administrator.lmw.mine.invest.entity;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class InvestmentTransferDatasBean {


    /**
     * borrowTitle : 定期宝A16102501
     * annualRate : 3.00
     * deadLineType : 1
     * deadLineValue : 30
     * investTime : null
     * stillPrincipal : 100.00
     * stillInterest : 0.00
     * culInterestStartTime : null
     * culInterestEndTime : null
     * isInterest : 1
     * investId : INVEST_j9jToo0rskrKRxIXY1P
     * url : checkContract?investId=INVEST_j9jToo0rskrKRxIXY1P
     * isFlow	        是否浮动利率(1=固定利率|2=浮动利率)	string
     * isFlowDead	    是否浮动期限(1=固定期限|2=浮动期限)	string
     * collectLineMaxValue	浮动最大期限	string
     * collectLineMinValue	浮动最小期限	string
     * flowMaxAnnualRate	浮动最大利率	string
     * flowMinAnnualRate	浮动最小利率	string
     * isOutTime	        是否设置退出日期：0 未设置 1：已设置	string
     * actualDays	        实际天数	string
     */

    private String borrowTitle;
    private String annualRate;
    private String deadLineType;
    private String deadLineValue;
    private String investTime;
    private String stillPrincipal;
    private String stillInterest;
    private String culInterestStartTime;
    private String culInterestEndTime;
    private String isInterest;
    private String investId;
    private String increaseRate;
    private String url;
    private String repayPeriodStr;
    private String repayMode;
    private String disabled;
    private String isContinue;
    private String isFlow;
    private String isFlowDead;
    private String collectLineMaxValue;
    private String collectLineMinValue;
    private String flowMaxAnnualRate;
    private String flowMinAnnualRate;
    private String isOutTime;
    private String actualDays;
    private String expectProfit;

    public String getIsContinue() {
        return isContinue;
    }

    public void setIsContinue(String isContinue) {
        this.isContinue = isContinue;
    }

    public String getBorrowTitle() {
        return borrowTitle;
    }

    public void setBorrowTitle(String borrowTitle) {
        this.borrowTitle = borrowTitle;
    }

    public String getAnnualRate() {
        return annualRate;
    }

    public void setAnnualRate(String annualRate) {
        this.annualRate = annualRate;
    }

    public String getDeadLineType() {
        return deadLineType;
    }

    public void setDeadLineType(String deadLineType) {
        this.deadLineType = deadLineType;
    }

    public String getDeadLineValue() {
        return deadLineValue;
    }

    public void setDeadLineValue(String deadLineValue) {
        this.deadLineValue = deadLineValue;
    }

    public String getInvestTime() {
        return investTime;
    }

    public void setInvestTime(String investTime) {
        this.investTime = investTime;
    }

    public String getStillPrincipal() {
        return stillPrincipal;
    }

    public void setStillPrincipal(String stillPrincipal) {
        this.stillPrincipal = stillPrincipal;
    }

    public String getStillInterest() {
        return stillInterest;
    }

    public void setStillInterest(String stillInterest) {
        this.stillInterest = stillInterest;
    }

    public String getCulInterestStartTime() {
        return culInterestStartTime;
    }

    public void setCulInterestStartTime(String culInterestStartTime) {
        this.culInterestStartTime = culInterestStartTime;
    }

    public String getCulInterestEndTime() {
        return culInterestEndTime;
    }

    public void setCulInterestEndTime(String culInterestEndTime) {
        this.culInterestEndTime = culInterestEndTime;
    }

    public String getIsInterest() {
        return isInterest;
    }

    public void setIsInterest(String isInterest) {
        this.isInterest = isInterest;
    }

    public String getInvestId() {
        return investId;
    }

    public void setInvestId(String investId) {
        this.investId = investId;
    }

    public String getIncreaseRate() {
        return increaseRate;
    }

    public void setIncreaseRate(String increaseRate) {
        this.increaseRate = increaseRate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRepayPeriodStr() {
        return repayPeriodStr;
    }

    public void setRepayPeriodStr(String repayPeriodStr) {
        this.repayPeriodStr = repayPeriodStr;
    }

    public String getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(String repayMode) {
        this.repayMode = repayMode;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getIsFlow() {
        return isFlow;
    }

    public void setIsFlow(String isFlow) {
        this.isFlow = isFlow;
    }

    public String getIsFlowDead() {
        return isFlowDead;
    }

    public void setIsFlowDead(String isFlowDead) {
        this.isFlowDead = isFlowDead;
    }

    public String getCollectLineMaxValue() {
        return collectLineMaxValue;
    }

    public void setCollectLineMaxValue(String collectLineMaxValue) {
        this.collectLineMaxValue = collectLineMaxValue;
    }

    public String getCollectLineMinValue() {
        return collectLineMinValue;
    }

    public void setCollectLineMinValue(String collectLineMinValue) {
        this.collectLineMinValue = collectLineMinValue;
    }

    public String getFlowMaxAnnualRate() {
        return flowMaxAnnualRate;
    }

    public void setFlowMaxAnnualRate(String flowMaxAnnualRate) {
        this.flowMaxAnnualRate = flowMaxAnnualRate;
    }

    public String getFlowMinAnnualRate() {
        return flowMinAnnualRate;
    }

    public void setFlowMinAnnualRate(String flowMinAnnualRate) {
        this.flowMinAnnualRate = flowMinAnnualRate;
    }

    public String getIsOutTime() {
        return isOutTime;
    }

    public void setIsOutTime(String isOutTime) {
        this.isOutTime = isOutTime;
    }

    public String getActualDays() {
        return actualDays;
    }

    public void setActualDays(String actualDays) {
        this.actualDays = actualDays;
    }

    public String getExpectProfit() {
        return expectProfit;
    }

    public void setExpectProfit(String expectProfit) {
        this.expectProfit = expectProfit;
    }
}
