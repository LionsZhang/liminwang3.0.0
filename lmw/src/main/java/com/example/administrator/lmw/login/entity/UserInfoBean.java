package com.example.administrator.lmw.login.entity;

import java.io.Serializable;

/**
 * Created by lion on 2016/8/29.
 */
public class UserInfoBean implements Serializable {
    /**
     * avater	        用户头像(绝对路径)	string
     * continuedFlag	是否续投过,0-没有、1-续投过	string
     * customerName	    用户名	string
     * customerRespectName	敬称(已实名认证才有)	string
     * customerSurname	用户真名(已实名认证才有)	string
     * isBindBankCard	是否绑定银行卡(0:未绑定;1:已绑定;2:需重新绑定)	number
     * isBindEmail	    是否绑定邮箱(0:未绑定;1:已绑定)	number
     * isBindPhone	    是否已绑定手机(0:未绑定;1:已绑定)	number
     * isLegacyUser	    是否遗留用户(0:否;1:是),(遗留用户:即系统升级之前的用户)	number
     * isRealnameAuth	是否实名认证(0:未认证;1:已认证)	number
     * isSetTradepasswd	是否已设置交易密码(0:未设置;1:已设置)	number
     * memberLevel	    会员等级	string
     * memberLevelImg	会员等级图像	string
     * memberLevelName	会员等级名称	string
     * mobile	        手机(隐藏中间4位)	string
     * scode	        邀请码	string
     * sex	            性别(0:未知;1:男;2:女)	number
     * usableAccount	可用余额(单位:元)	number
     * userTag	        用户标签属性(0:新用户;1:之前实名正确老用户;2:之前实名不通过老用户)	number
     * authWindow	    是否显示授权委托书窗口(0:是, 1:否)	string
     */
    private double usableAccount;//可用余额
    private int isSetTradepasswd;
    private int isRealnameAuth;
    private String avater;
    private int sex;
    private int isBindPhone;
    private int isBindEmail;
    private int isBindBankCard;
    private String customerSurname;
    private String mobile;
    private String customerName;
    private String customerId;
    private String customerRespectName;
    private int isLegacyUser;//是否老用户 1 是，0 否
    private String scode;//邀请码
    private String continuedFlag;//是否续投过,0-没有、1-续投过
    private int userTag;
    private String isRiskCamuluate;//风险评估;0-没有评估，1已经评估
    private String isEsignatureAuthorize;//0-没有、1-授权
    private String authorize;//0:未授权, 1:已授权
    private String authViewUrl;//0:未授权, 1:已授权
    private String useFdd;//是否显示授权委托书窗口(0:是, 1:否)
    private String openDpStatus;//存管状态(-1未开通；0待激活(存量用户迁移专用)；1审核中；2审核通过；3：审核回退；4审核拒绝)
    private String openDpStatusDesc;

    public String getContinuedFlag() {
        return continuedFlag;
    }

    public void setContinuedFlag(String continuedFlag) {
        this.continuedFlag = continuedFlag;
    }

    public double getUsableAccount() {
        return usableAccount;
    }

    public void setUsableAccount(double usableAccount) {
        this.usableAccount = usableAccount;
    }

    public int getIsSetTradepasswd() {
        return isSetTradepasswd;
    }

    public void setIsSetTradepasswd(int isSetTradepasswd) {
        this.isSetTradepasswd = isSetTradepasswd;
    }

    public int getIsRealnameAuth() {
        return isRealnameAuth;
    }

    public void setIsRealnameAuth(int isRealnameAuth) {
        this.isRealnameAuth = isRealnameAuth;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getIsBindPhone() {
        return isBindPhone;
    }

    public void setIsBindPhone(int isBindPhone) {
        this.isBindPhone = isBindPhone;
    }

    public int getIsBindEmail() {
        return isBindEmail;
    }

    public void setIsBindEmail(int isBindEmail) {
        this.isBindEmail = isBindEmail;
    }

    public int getIsBindBankCard() {
        return isBindBankCard;
    }

    public void setIsBindBankCard(int isBindBankCard) {
        this.isBindBankCard = isBindBankCard;
    }

    public String getCustomerSurname() {
        return customerSurname;
    }

    public void setCustomerSurname(String customerSurname) {
        this.customerSurname = customerSurname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerRespectName() {
        return customerRespectName;
    }

    public void setCustomerRespectName(String customerRespectName) {
        this.customerRespectName = customerRespectName;
    }

    public int getIsLegacyUser() {
        return isLegacyUser;
    }

    public void setIsLegacyUser(int isLegacyUser) {
        this.isLegacyUser = isLegacyUser;
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public int getUserTag() {
        return userTag;
    }

    public void setUserTag(int userTag) {
        this.userTag = userTag;
    }

    public String getAuthorize() {
        return authorize;
    }


    public String getOpenDpStatus() {
        return openDpStatus;
    }

    public void setOpenDpStatus(String openDpStatus) {
        this.openDpStatus = openDpStatus;
    }

    public void setIsEsignatureAuthorize(String isEsignatureAuthorize) {
        this.isEsignatureAuthorize = isEsignatureAuthorize;
    }
    public void setAuthorize(String authorize) {
        this.authorize = authorize;


    }


    public String getIsRiskCamuluate() {
        return isRiskCamuluate;
    }

    public void setIsRiskCamuluate(String isRiskCamuluate) {
        this.isRiskCamuluate = isRiskCamuluate;
    }

    public String getOpenDpStatusDesc() {
        return openDpStatusDesc;
    }

    public void setOpenDpStatusDesc(String openDpStatusDesc) {
        this.openDpStatusDesc = openDpStatusDesc;
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

