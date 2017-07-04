package com.example.administrator.lmw.mine.fill.entity;

import com.example.administrator.lmw.entity.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lion on 2016/9/9.
 */
public class UserBankInfo implements Serializable {

    /**
     * balance	用户余额	string
     * bankList	网银支付银行列表	array<object>
     * bankCode	银行code	string
     * bankName	银行名称	string
     * bindBankCode	绑定的银行code	string
     * bindBankName	绑定的银行	string
     * bindBankNo	绑定的银行卡卡号	string 绑定银行的尾4位
     * customerName	用户姓名	string
     * dailyLimit	单日限额（-1表示无限额）	string
     * singleLimit	单笔限额（-1表示无限额）	string
     */
    private String bindBankName;//平安银行
    private String bindBankNo;
    private String customerName;//客户名字
    private String balance;//余额
    private String bindBankCode;//PAB
    private String singleLimit;
    private String dailyLimit;

    /**
     * bankCode : 测试内容od6q
     * bankName : 测试内容oo36
     */

    public String getBindBankName() {
        return bindBankName;
    }

    public void setBindBankName(String bindBankName) {
        this.bindBankName = bindBankName;
    }

    public String getBindBankNo() {
        return bindBankNo;
    }

    public void setBindBankNo(String bindBankNo) {
        this.bindBankNo = bindBankNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(String dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public String getBindBankCode() {
        return bindBankCode;
    }

    public void setBindBankCode(String bindBankCode) {
        this.bindBankCode = bindBankCode;
    }

    public String getSingleLimit() {
        return singleLimit;
    }

    public void setSingleLimit(String singleLimit) {
        this.singleLimit = singleLimit;
    }


}

