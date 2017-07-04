package com.example.administrator.lmw.mine.seting.entity;

import java.io.Serializable;

/**
 * Created by lion on 2016/9/5.
 */
public class BankCardInfo implements Serializable {
    /*"bankId" : 4,
                "bankName" : "中国工商银行",
				"bankCode" : "ICBC",
				"dailyQuotaAmount" : 0.00,
				"eachQuotaAmout" : 0.00*/
    private int bankId;//银行卡ID
    private String bankName;//银行名字
    private double dailyQuotaAmount;
    private double eachQuotaAmout;
    private String bankCode;
    private String bankImg;

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public double getDailyQuotaAmount() {
        return dailyQuotaAmount;
    }

    public void setDailyQuotaAmount(double dailyQuotaAmount) {
        this.dailyQuotaAmount = dailyQuotaAmount;
    }

    public double getEachQuotaAmout() {
        return eachQuotaAmout;
    }

    public void setEachQuotaAmout(double eachQuotaAmout) {
        this.eachQuotaAmout = eachQuotaAmout;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankImg() {
        return bankImg;
    }

    public void setBankImg(String bankImg) {
        this.bankImg = bankImg;
    }
}
