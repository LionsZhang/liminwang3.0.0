package com.example.administrator.lmw.mine.invest.entity;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class ConfirmTransferEntity {


    /**
     * borrowTitle	原标的名称	string
     * debtInterest	转让利息
     * debtInterestPriceAmt	转让利息价格	number
     * debtPrincipalAmt	转让本金	string
     * debtPrincipalPriceAmt	转让本金价格	string
     * repayMode	还款方式	string
     * returnUrl	成功后返回url	string
     * transferTerm	转让期数	string
     * transferTime	开始转让时间	string
     */

    private String returnUrl;
    private String debtPrincipalAmt;
    private String transferTerm;
    private String repayMode;
    private String debtInterest;
    private String debtPrincipalPriceAmt;
    private String borrowTitle;
    private String transferTime;
    private String debtInterestPriceAmt;

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getDebtPrincipalAmt() {
        return debtPrincipalAmt;
    }

    public void setDebtPrincipalAmt(String debtPrincipalAmt) {
        this.debtPrincipalAmt = debtPrincipalAmt;
    }

    public String getTransferTerm() {
        return transferTerm;
    }

    public void setTransferTerm(String transferTerm) {
        this.transferTerm = transferTerm;
    }

    public String getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(String repayMode) {
        this.repayMode = repayMode;
    }

    public String getDebtInterest() {
        return debtInterest;
    }

    public void setDebtInterest(String debtInterest) {
        this.debtInterest = debtInterest;
    }

    public String getDebtPrincipalPriceAmt() {
        return debtPrincipalPriceAmt;
    }

    public void setDebtPrincipalPriceAmt(String debtPrincipalPriceAmt) {
        this.debtPrincipalPriceAmt = debtPrincipalPriceAmt;
    }

    public String getBorrowTitle() {
        return borrowTitle;
    }

    public void setBorrowTitle(String borrowTitle) {
        this.borrowTitle = borrowTitle;
    }

    public String getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(String transferTime) {
        this.transferTime = transferTime;
    }

    public String getDebtInterestPriceAmt() {
        return debtInterestPriceAmt;
    }

    public void setDebtInterestPriceAmt(String debtInterestPriceAmt) {
        this.debtInterestPriceAmt = debtInterestPriceAmt;
    }
}
