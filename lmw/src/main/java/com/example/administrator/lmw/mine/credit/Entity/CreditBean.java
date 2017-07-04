package com.example.administrator.lmw.mine.credit.Entity;

import java.io.Serializable;

/**
 * Created by lion on 2016/9/13.
 */
public class CreditBean implements Serializable {

    /**
     * freeWithdrawalsCount : 58251
     * mobileDesc : 测试内容4pi5
     * bankName : 测试内容20lw
     * bankCodeDesc : 1
     * poundage : 测试内容2jut
     * balance : 测试内容r72p
     * withDrawAmount  可提现金额
     * bankWithdrawalsLimit 单笔提现限额
     */

    private String freeWithdrawalsCount;//免费提现次数
    private String mobileDesc;//手机号描述
    private String bankName;//银行名称
    private int bankCodeDesc;//银行卡号描述
    private String poundage;//提现手续费率
    private String balance;//余额
    private String withDrawAmount;//可提现金额
    private String bankWithdrawalsLimit;//单笔提现限额

    public String getFreeWithdrawalsCount() {
        return freeWithdrawalsCount;
    }

    public void setFreeWithdrawalsCount(String freeWithdrawalsCount) {
        this.freeWithdrawalsCount = freeWithdrawalsCount;
    }

    public String getMobileDesc() {
        return mobileDesc;
    }

    public void setMobileDesc(String mobileDesc) {
        this.mobileDesc = mobileDesc;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getBankCodeDesc() {
        return bankCodeDesc;
    }

    public void setBankCodeDesc(int bankCodeDesc) {
        this.bankCodeDesc = bankCodeDesc;
    }

    public String getPoundage() {
        return poundage;
    }

    public void setPoundage(String poundage) {
        this.poundage = poundage;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getWithDrawAmount() {
        return withDrawAmount;
    }

    public void setWithDrawAmount(String withDrawAmount) {
        this.withDrawAmount = withDrawAmount;
    }

    public String getBankWithdrawalsLimit() {
        return bankWithdrawalsLimit;
    }

    public void setBankWithdrawalsLimit(String bankWithdrawalsLimit) {
        this.bankWithdrawalsLimit = bankWithdrawalsLimit;
    }
}


