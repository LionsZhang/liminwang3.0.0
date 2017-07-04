package com.example.administrator.lmw.mine.invest.entity;

/**
 * Created by Administrator on 2017/3/14.
 */

public class BackPlanBean {
    /**
     * repayDate	        回款日期	string
     * repayPeriod	        期数	string	1/2期
     * repayStatus	        还款状态（0-默认未偿还 1-已偿还)	string
     * receivedInterest	    应收利息	string
     * receivedPrincipal	应收本金	string
     * stillSum	            应收总额	string
     */

    private String repayStatus;
    private String stillSum;
    private String repayDate;
    private String receivedInterest;
    private String receivedPrincipal;
    private String repayPeriod;

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }

    public String getStillSum() {
        return stillSum;
    }

    public void setStillSum(String stillSum) {
        this.stillSum = stillSum;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public String getReceivedInterest() {
        return receivedInterest;
    }

    public void setReceivedInterest(String receivedInterest) {
        this.receivedInterest = receivedInterest;
    }

    public String getReceivedPrincipal() {
        return receivedPrincipal;
    }

    public void setReceivedPrincipal(String receivedPrincipal) {
        this.receivedPrincipal = receivedPrincipal;
    }

    public String getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(String repayPeriod) {
        this.repayPeriod = repayPeriod;
    }
}
