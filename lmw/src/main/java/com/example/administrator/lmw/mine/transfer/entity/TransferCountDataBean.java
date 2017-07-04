package com.example.administrator.lmw.mine.transfer.entity;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class TransferCountDataBean {
    private int repayCount;
    private double repayAmt;
    private int isDebtRepay;

    public int getRepayCount() {
        return repayCount;
    }

    public void setRepayCount(int repayCount) {
        this.repayCount = repayCount;
    }

    public double getRepayAmt() {
        return repayAmt;
    }

    public void setRepayAmt(double repayAmt) {
        this.repayAmt = repayAmt;
    }

    public int getIsDebtRepay() {
        return isDebtRepay;
    }

    public void setIsDebtRepay(int isDebtRepay) {
        this.isDebtRepay = isDebtRepay;
    }
}
