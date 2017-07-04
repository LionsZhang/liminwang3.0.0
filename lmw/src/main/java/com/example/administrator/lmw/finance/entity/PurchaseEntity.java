package com.example.administrator.lmw.finance.entity;

/**
 * Created by Administrator on 2016/9/7 0007.
 */
public class PurchaseEntity {


    /**
     * buyedSucSpanDesc	    购买成功描述信息	string
     * canTransfer	        0-可以转让、1-不可以转让	string
     * canTransferTime	    可转让时间	string
     * endTime	            回款时间	string
     * investAmount	        购买金额	number
     * investId	            投资记录ID	string
     * returnUrl	        购买成功返回URl	string
     * startTime	        计息时间	string
     */

    private String startTime;
    private String canTransfer;
    private String endTime;
    private String buyedSucSpanDesc;
    private String returnUrl;
    private String canTransferTime;
    private double investAmount;
    private String investId;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCanTransfer() {
        return canTransfer;
    }

    public void setCanTransfer(String canTransfer) {
        this.canTransfer = canTransfer;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBuyedSucSpanDesc() {
        return buyedSucSpanDesc;
    }
        private String cipher;
        private String rspData;
    public void setBuyedSucSpanDesc(String buyedSucSpanDesc) {
        this.buyedSucSpanDesc = buyedSucSpanDesc;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getCanTransferTime() {
        return canTransferTime;
    }

    public void setCanTransferTime(String canTransferTime) {
        this.canTransferTime = canTransferTime;
    }

    public double getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(double investAmount) {
        this.investAmount = investAmount;
    }

    public String getInvestId() {
        return investId;
    }

    public void setInvestId(String investId) {
        this.investId = investId;
    }
        public String getCipher() {
            return cipher;
        }

        public void setCipher(String cipher) {
            this.cipher = cipher;
        }

        public String getRspData() {
            return rspData;
        }

        public void setRspData(String rspData) {
            this.rspData = rspData;
        }
    }

