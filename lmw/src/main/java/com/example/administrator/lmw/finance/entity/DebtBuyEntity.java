package com.example.administrator.lmw.finance.entity;

/**
 * Created by Administrator on 2016/9/7 0007.
 */
public class DebtBuyEntity {


    /**
     * culInterestStartTime	起息时间	number	用的时候截取为：yyyy-mm-dd
     * investId	投资记录id	string
     * nowTime	现在时间	string	yyyy-mm-dd
     * payAmount	购买支付金额	string
     * repayDate	还款时间	string
     * returnUrl	返回url	string
     */

    private String returnUrl;
    private String culInterestStartTime;
    private String nowTime;
    private String repayDate;
    private String payAmount;
    private String investId;
    private String buyedSucSpanDesc;

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getCulInterestStartTime() {
        return culInterestStartTime;
    }

    public void setCulInterestStartTime(String culInterestStartTime) {
        this.culInterestStartTime = culInterestStartTime;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getRepayDate() {
        return repayDate;
    }
        private String cipher;
        private String rspData;

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getInvestId() {
        return investId;
    }

    public void setInvestId(String investId) {
        this.investId = investId;
    }

    public String getBuyedSucSpanDesc() {
        return buyedSucSpanDesc;
    }

    public void setBuyedSucSpanDesc(String buyedSucSpanDesc) {
        this.buyedSucSpanDesc = buyedSucSpanDesc;
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
