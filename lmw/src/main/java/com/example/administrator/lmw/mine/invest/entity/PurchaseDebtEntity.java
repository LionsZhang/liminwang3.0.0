package com.example.administrator.lmw.mine.invest.entity;

/**
 * Created by Administrator on 2016/9/23 0023.
 */
public class PurchaseDebtEntity {

    /**
     * holdDay	持有天数
     * holdInterest	转让人当期应收利息（多条还款计划时使用）
     * principalAmt	转让本金	number
     * principalDiscountAmt	本金让利价格	number
     * principalPriceAmt	转让本金价格	number
     * surplusDay	转让天数
     * tempInterestAmt	转让利息（转让人应收利息）	number
     * tempInterestDiscountAmt	利息让利价格	number
     * tempInterestPriceAmt	转让利息价格	number
     * transferRate	转让利率
     */

    private String principalAmt;//债权本金
    private String principalPriceAmt;//债权转让后本金
    private String principalDiscountAmt;//本金让利价格
    private String tempInterestAmt;//债权利息（转让人应收利息）
    private String tempInterestPriceAmt;//债权转让后利息
    private String tempInterestDiscountAmt;//利息让利价格
    private int surplusDay;//转让天数
    private String holdInterest;//转让人当期应收利息（多条还款计划时使用）
    private int holdDay;//持有天数
    private String transferRate;//转让利率

    public String getPrincipalAmt() {
        return principalAmt;
    }

    public void setPrincipalAmt(String principalAmt) {
        this.principalAmt = principalAmt;
    }

    public String getPrincipalPriceAmt() {
        return principalPriceAmt;
    }

    public void setPrincipalPriceAmt(String principalPriceAmt) {
        this.principalPriceAmt = principalPriceAmt;
    }

    public String getPrincipalDiscountAmt() {
        return principalDiscountAmt;
    }

    public void setPrincipalDiscountAmt(String principalDiscountAmt) {
        this.principalDiscountAmt = principalDiscountAmt;
    }

    public String getTempInterestAmt() {
        return tempInterestAmt;
    }

    public void setTempInterestAmt(String tempInterestAmt) {
        this.tempInterestAmt = tempInterestAmt;
    }

    public String getTempInterestPriceAmt() {
        return tempInterestPriceAmt;
    }

    public void setTempInterestPriceAmt(String tempInterestPriceAmt) {
        this.tempInterestPriceAmt = tempInterestPriceAmt;
    }

    public String getTempInterestDiscountAmt() {
        return tempInterestDiscountAmt;
    }

    public void setTempInterestDiscountAmt(String tempInterestDiscountAmt) {
        this.tempInterestDiscountAmt = tempInterestDiscountAmt;
    }

    public int getSurplusDay() {
        return surplusDay;
    }

    public void setSurplusDay(int surplusDay) {
        this.surplusDay = surplusDay;
    }

    public String getHoldInterest() {
        return holdInterest;
    }

    public void setHoldInterest(String holdInterest) {
        this.holdInterest = holdInterest;
    }

    public int getHoldDay() {
        return holdDay;
    }

    public void setHoldDay(int holdDay) {
        this.holdDay = holdDay;
    }

    public String getTransferRate() {
        return transferRate;
    }

    public void setTransferRate(String transferRate) {
        this.transferRate = transferRate;
    }
}
