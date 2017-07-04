package com.example.administrator.lmw.mine.seting.entity;

import java.io.Serializable;

/**
 * Created by lion on 2016/9/2.
 */
public class SetDataBean implements Serializable {
    /**
     * avater	头像	string	[示例：https://www.baidu.com/img/bd_logo1.png]
     * bankCardNo	银行卡号(**尾4位)	string	[示例：**9433]
     * bankCode	银行编号	string	[示例：ICBC]
     * bankName	银行名称	string	[示例：中国银行]
     * customerName	名称	string	[示例： 张先生]
     * customerSurname	真实姓名(隐藏名)	string	[示例： 张*]
     * customerSurnameWhole	真实姓名(全称)	string	[示例： 张三]
     * email	email	string	[示例： ********xtu@163.com]
     * idNo	身份证号(首尾各两位)	string	[示例： 42**************34]
     * idNoWhole	身份证号(全部)	string	431223198501011010
     * isSetGesturePasswd	是否设置了手势密码(0:未设置;1:已设置)	number	[示例： 1]
     * isTradPwd	是否设置了交易密码(0:未设置;1:已设置)	number	[示例： 1]
     * loginPwdSafeLevel	登录密码安全等级(0:弱;1:中;2:强)	number	[示例： 1]
     * mobile	手机号码(隐藏中间4位)	string	[示例： 150****5678]
     * mobileWhole	手机号码(全部数字)	string	[示例： 15012345678]
     * regTime	注册时间	string	[示例：2015-01-01 00:00:00]
     * safeLevel	账号安全等级(0:弱;1:中;2:强)
     * dailyQuotaAmount;单日限额
     * eachQuotaAmout;单笔限额
     * bankCardNoWhole  已绑定的卡号
     * authWindow	    是否显示授权委托书窗口(0:是, 1:否)	string
     */

    private String avater;
    private String customerName;
    private String mobile;
    private String mobileWhole;
    private String regTime;
    private String safeLevel;
    private String customerSurname;
    private String customerSurnameWhole;
    private String idNo;
    private String idNoWhole;
    private String isTradPwd;
    private String bankCardNo;
    private String bankCardNoWhole;
    private String bankCode;
    private String bankName;
    private String email;
    private String isSetGesturePasswd;
    private String loginPwdSafeLevel;
    private String isLegacyUser;
    private String dailyQuotaAmount;
    private String eachQuotaAmout;
    private String memberLevel;
    private String memberLevelName;
    private String memberLevelImg;
    private String memberCenterUrl;
    private String riskCamuluateUrl;//风险评估URL
    private String riskCamuluateGrade;//风险评估等级
    private String depositAccount;//存管账号
    private String isCanUnbundle;//可解绑1 是，0 否
    private String accountMoney;//账户余额
    private String bankMobile;//银行预留手机号
    private String bankImg;
    private String authorize;
    private String authViewUrl;
    private String useFdd;//是否显示授权委托书窗口(0:是, 1:否)


    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileWhole() {
        return mobileWhole;
    }

    public void setMobileWhole(String mobileWhole) {
        this.mobileWhole = mobileWhole;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getSafeLevel() {
        return safeLevel;
    }

    public void setSafeLevel(String safeLevel) {
        this.safeLevel = safeLevel;
    }

    public String getCustomerSurname() {
        return customerSurname;
    }

    public void setCustomerSurname(String customerSurname) {
        this.customerSurname = customerSurname;
    }

    public String getCustomerSurnameWhole() {
        return customerSurnameWhole;
    }

    public void setCustomerSurnameWhole(String customerSurnameWhole) {
        this.customerSurnameWhole = customerSurnameWhole;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIdNoWhole() {
        return idNoWhole;
    }

    public void setIdNoWhole(String idNoWhole) {
        this.idNoWhole = idNoWhole;
    }

    public String getIsTradPwd() {
        return isTradPwd;
    }

    public void setIsTradPwd(String isTradPwd) {
        this.isTradPwd = isTradPwd;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankCardNoWhole() {
        return bankCardNoWhole;
    }

    public void setBankCardNoWhole(String bankCardNoWhole) {
        this.bankCardNoWhole = bankCardNoWhole;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsSetGesturePasswd() {
        return isSetGesturePasswd;
    }

    public void setIsSetGesturePasswd(String isSetGesturePasswd) {
        this.isSetGesturePasswd = isSetGesturePasswd;
    }

    public String getLoginPwdSafeLevel() {
        return loginPwdSafeLevel;
    }

    public void setLoginPwdSafeLevel(String loginPwdSafeLevel) {
        this.loginPwdSafeLevel = loginPwdSafeLevel;
    }

    public String getIsLegacyUser() {
        return isLegacyUser;
    }

    public void setIsLegacyUser(String isLegacyUser) {
        this.isLegacyUser = isLegacyUser;
    }

    public String getDailyQuotaAmount() {
        return dailyQuotaAmount;
    }

    public void setDailyQuotaAmount(String dailyQuotaAmount) {
        this.dailyQuotaAmount = dailyQuotaAmount;
    }

    public String getEachQuotaAmout() {
        return eachQuotaAmout;
    }

    public void setEachQuotaAmout(String eachQuotaAmout) {
        this.eachQuotaAmout = eachQuotaAmout;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String getMemberLevelName() {
        return memberLevelName;
    }

    public void setMemberLevelName(String memberLevelName) {
        this.memberLevelName = memberLevelName;
    }

    public String getMemberLevelImg() {
        return memberLevelImg;
    }

    public void setMemberLevelImg(String memberLevelImg) {
        this.memberLevelImg = memberLevelImg;
    }

    public String getMemberCenterUrl() {
        return memberCenterUrl;
    }

    public void setMemberCenterUrl(String memberCenterUrl) {
        this.memberCenterUrl = memberCenterUrl;
    }


    public String getRiskCamuluateUrl() {
        return riskCamuluateUrl;
    }

    public void setRiskCamuluateUrl(String riskCamuluateUrl) {
        this.riskCamuluateUrl = riskCamuluateUrl;
    }

    public String getRiskCamuluateGrade() {
        return riskCamuluateGrade;
    }

    public void setRiskCamuluateGrade(String riskCamuluateGrade) {
        this.riskCamuluateGrade = riskCamuluateGrade;
    }

    public String getDepositAccount() {
        return depositAccount;
    }

    public void setDepositAccount(String depositAccount) {
        this.depositAccount = depositAccount;
    }

    public String getIsCanUnbundle() {
        return isCanUnbundle;
    }

    public void setIsCanUnbundle(String isCanUnbundle) {
        this.isCanUnbundle = isCanUnbundle;
    }

    public String getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(String accountMoney) {
        this.accountMoney = accountMoney;
    }

    public String getBankMobile() {
        return bankMobile;
    }

    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile;
    }

    public String getBankImg() {
        return bankImg;
    }

    public void setBankImg(String bankImg) {
        this.bankImg = bankImg;
    }


    public String getAuthorize() {
        return authorize;
    }

    public void setAuthorize(String authorize) {
        this.authorize = authorize;
    }

    public String getAuthViewUrl() {
        return authViewUrl;
    }

    public void setAuthViewUrl(String authViewUrl) {
        this.authViewUrl = authViewUrl;
    }

    public String getUseFdd() {
        return useFdd;
    }

    public void setUseFdd(String useFdd) {
        this.useFdd = useFdd;
    }

}
