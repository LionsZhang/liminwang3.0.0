package com.example.administrator.lmw.mine.transfer.entity;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class TransferDataBean {

    private String title;
    private double repayAmt;
    private String repayDate;
    private String periods;
    private String repayStatus;
    private double stillPrincipal;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRepayAmt() {
        return repayAmt;
    }

    public void setRepayAmt(double repayAmt) {
        this.repayAmt = repayAmt;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }

    public double getStillPrincipal() {
        return stillPrincipal;
    }

    public void setStillPrincipal(double stillPrincipal) {
        this.stillPrincipal = stillPrincipal;
    }
}
