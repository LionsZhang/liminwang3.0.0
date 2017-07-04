package com.example.administrator.lmw.login.entity;

import java.io.Serializable;

/**
 * Created by lion on 2016/8/30.
 */
public class MineDataBean implements Serializable {

    /**
     *
     * data : {"withdrawingAmount":84762,"accuIncome":67772,"mobile":"测试内容7431","totalAssets":81713,
     * "investAmount":74537,"couponNum":31562,"customerName":"测试内容4484","usableAmount":41020,"debtAmount":12584}
     *
     */

    /**
     * accuIncome	累计收益(单位:元)	number	[示例：10000.00]
     * couponNum	卡卷红包数目	number	[示例：10]
     * customerName	姓名	string	[示例：张*]
     * debtAmount	我的转让金额(单位:元)(转让中的)	number	[示例：4000.00]
     * debtNum	我的转让标数(转让中的)	number	[示例：4]
     * investAmount	我的投资(单位:元)	number	[示例：1000.00]
     * mobile	手机号	string	[示例：150****6291]
     * totalAssets	总资产(单位:元)	number	[示例：5000.00]
     * usableAmount	可用余额(单位:元)	number	[示例：3000.00]
     * withdrawingAmount	提现中(单位:元)
     * repayAmount	待回款金额	number	[示例：1000.00]
     * repayNum	待回款笔数
     * memberLevel	会员等级	string	@mock=1
     * memberLevelName	会员等级名称	string	@mock=青铜会员
     */

    private double withdrawingAmount;
    private double accuIncome;
    private String mobile;
    private double totalAssets;
    private double investAmount;
    private int couponNum;
    private String customerName;
    private double usableAmount;
    private double debtAmount;
    private double repayAmount;
    private long repayNum;
    private String memberLevel;
    private String memberLevelName;
    private String memberLevelImg;
    private String liminDataUrl;
    private String financialCalculatorUrl;

    public double getWithdrawingAmount() {
        return withdrawingAmount;
    }

    public void setWithdrawingAmount(double withdrawingAmount) {
        this.withdrawingAmount = withdrawingAmount;
    }

    public double getAccuIncome() {
        return accuIncome;
    }

    public void setAccuIncome(double accuIncome) {
        this.accuIncome = accuIncome;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public double getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(double totalAssets) {
        this.totalAssets = totalAssets;
    }

    public double getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(double investAmount) {
        this.investAmount = investAmount;
    }

    public int getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(int couponNum) {
        this.couponNum = couponNum;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getUsableAmount() {
        return usableAmount;
    }

    public void setUsableAmount(double usableAmount) {
        this.usableAmount = usableAmount;
    }

    public double getDebtAmount() {
        return debtAmount;
    }

    public void setDebtAmount(double debtAmount) {
        this.debtAmount = debtAmount;
    }

    public double getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(double repayAmount) {
        this.repayAmount = repayAmount;
    }

    public long getRepayNum() {
        return repayNum;
    }

    public void setRepayNum(long repayNum) {
        this.repayNum = repayNum;
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

    public String getLiminDataUrl() {
        return liminDataUrl;
    }

    public void setLiminDataUrl(String liminDataUrl) {
        this.liminDataUrl = liminDataUrl;
    }

    public String getFinancialCalculatorUrl() {
        return financialCalculatorUrl;
    }

    public void setFinancialCalculatorUrl(String financialCalculatorUrl) {
        this.financialCalculatorUrl = financialCalculatorUrl;
    }
}
