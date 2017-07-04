package com.example.administrator.lmw.mine.invest.entity;

import java.util.List;

/**
 * 回款计划适配器
 * <p>
 * Created by Administrator on 2017/3/14.
 */

public class BackPlanEntity {

    /**
     * annualRate	        年华	string
     * borrowTitle	        标的名称	string
     * collectInterest	    待收收益	string
     * collectPrincipal	    待收本金	string
     * deadLineType	        期限类型(1=天数|2=自然月|3=年)	string
     * deadLineValue	    期限	string
     * increaseRate	        加息	string
     * investAmount	        投资本金	string
     * receivedInterest	    已收收益	string
     * receivedPrincipal	已收本金	string
     * repayDate	        下一回款日	string
     * repayMode	        回款方式	string
     * debtInvestAmount	    支付金额	string
     * isFlow	        是否浮动利率(1=固定利率|2=浮动利率)	string
     * isFlowDead	    是否浮动期限(1=固定期限|2=浮动期限)	string
     * collectLineMaxValue	浮动最大期限	string
     * collectLineMinValue	浮动最小期限	string
     * flowMaxAnnualRate	浮动最大利率	string
     * flowMinAnnualRate	浮动最小利率	string
     * lastRepayDate	    退出日期	string
     * expectProfit	        预期收益	string
     * isOutTime	        是否设置退出日期：0 未设置 1：已设置	string
     * actualDays	        实际天数	string
     */

    private String collectInterest;
    private String repayMode;
    private String deadLineType;
    private String collectPrincipal;
    private String investAmount;
    private String repayDate;
    private String increaseRate;
    private String deadLineValue;
    private String receivedInterest;
    private String borrowTitle;
    private String annualRate;
    private String receivedPrincipal;
    private String debtInvestAmount;
    private List<BackPlanBean> datas;
    private String isFlow;
    private String isFlowDead;
    private String collectLineMaxValue;
    private String collectLineMinValue;
    private String flowMaxAnnualRate;
    private String flowMinAnnualRate;
    private String lastRepayDate;
    private String expectProfit;
    private String isOutTime;
    private String actualDays;

    public String getCollectInterest() {
        return collectInterest;
    }

    public void setCollectInterest(String collectInterest) {
        this.collectInterest = collectInterest;
    }

    public String getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(String repayMode) {
        this.repayMode = repayMode;
    }

    public String getDeadLineType() {
        return deadLineType;
    }

    public void setDeadLineType(String deadLineType) {
        this.deadLineType = deadLineType;
    }

    public String getCollectPrincipal() {
        return collectPrincipal;
    }

    public void setCollectPrincipal(String collectPrincipal) {
        this.collectPrincipal = collectPrincipal;
    }

    public String getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(String investAmount) {
        this.investAmount = investAmount;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public String getIncreaseRate() {
        return increaseRate;
    }

    public void setIncreaseRate(String increaseRate) {
        this.increaseRate = increaseRate;
    }

    public String getDeadLineValue() {
        return deadLineValue;
    }

    public void setDeadLineValue(String deadLineValue) {
        this.deadLineValue = deadLineValue;
    }

    public String getReceivedInterest() {
        return receivedInterest;
    }

    public void setReceivedInterest(String receivedInterest) {
        this.receivedInterest = receivedInterest;
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

    public String getReceivedPrincipal() {
        return receivedPrincipal;
    }

    public void setReceivedPrincipal(String receivedPrincipal) {
        this.receivedPrincipal = receivedPrincipal;
    }

    public String getDebtInvestAmount() {
        return debtInvestAmount;
    }

    public void setDebtInvestAmount(String debtInvestAmount) {
        this.debtInvestAmount = debtInvestAmount;
    }

    public List<BackPlanBean> getDatas() {
        return datas;
    }

    public void setDatas(List<BackPlanBean> datas) {
        this.datas = datas;
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

    public String getLastRepayDate() {
        return lastRepayDate;
    }

    public void setLastRepayDate(String lastRepayDate) {
        this.lastRepayDate = lastRepayDate;
    }

    public String getExpectProfit() {
        return expectProfit;
    }

    public void setExpectProfit(String expectProfit) {
        this.expectProfit = expectProfit;
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

}
