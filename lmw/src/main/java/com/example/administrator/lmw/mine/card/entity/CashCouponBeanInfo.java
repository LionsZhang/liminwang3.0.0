package com.example.administrator.lmw.mine.card.entity;

import java.io.Serializable;

/**
 * Created by lion on 2017/2/20.
 */

public class CashCouponBeanInfo implements Serializable {


    /**
     * maxActiveDay : '90'
     * minInvestAmount : '2000'
     * beginTime : '2016-08-01 00:00:00'
     * couponName : '新年快乐'
     * groupItemIndex : '0'
     * couponType : '0'
     * acquireSource : '新年快乐'
     * useRule : '单笔投资5000元及以上可用'
     * couponTypeName : '加息券‘
     * minInvestDeadline : '90'
     * couponId : '123456789'
     * assignInvestDeadline : '30,60'
     * couponProfit : '1000'
     * effectiveDate : '2017.02.25 - 2017.02.28'
     * endTime : '2016.02.25'
     * supportedProduct : '定期宝、车贷宝'
     * couponAmount : '1.50'
     * supportedDeadline : '90天及以上'
     * couponAmountFmt : '1.50%'
     * supportedPlatform : 'app端、pc网页端、微信端'
     * usableStatus : '1'
     * realActiveDay": '30'
     */



    private String maxActiveDay;
    private String minInvestAmount;
    private String beginTime;
    private String couponName;
    private int groupItemIndex = -1;////服务端配置为0时表示一组的第一个
    private String couponType;
    private String acquireSource;
    private String useRule;
    private String couponTypeName;
    private String minInvestDeadline;
    private String couponId;
    private String assignInvestDeadline;
    private String couponProfit;
    private String effectiveDate;
    private String endTime;
    private String supportedProduct;
    private String couponAmount;
    private String supportedDeadline;
    private String couponAmountFmt;
    private String supportedPlatform;
    private String usableStatus;
    private String realActiveDay;//实际理财和加息天数

    private boolean isSelect;//是否选中
    private int mCanUseCouponNum; //可用现金券数量

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getCanUseCouponNum() {
        return mCanUseCouponNum;
    }

    public void setCanUseCouponNum(int canUseCouponNum) {
        mCanUseCouponNum = canUseCouponNum;
    }

    public String getMaxActiveDay() {
        return maxActiveDay;
    }

    public void setMaxActiveDay(String maxActiveDay) {
        this.maxActiveDay = maxActiveDay;
    }

    public String getMinInvestAmount() {
        return minInvestAmount;
    }

    public void setMinInvestAmount(String minInvestAmount) {
        this.minInvestAmount = minInvestAmount;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getGroupItemIndex() {
        return groupItemIndex;
    }

    public void setGroupItemIndex(int groupItemIndex) {
        this.groupItemIndex = groupItemIndex;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getAcquireSource() {
        return acquireSource;
    }

    public void setAcquireSource(String acquireSource) {
        this.acquireSource = acquireSource;
    }

    public String getUseRule() {
        return useRule;
    }

    public void setUseRule(String useRule) {
        this.useRule = useRule;
    }

    public String getCouponTypeName() {
        return couponTypeName;
    }

    public void setCouponTypeName(String couponTypeName) {
        this.couponTypeName = couponTypeName;
    }

    public String getMinInvestDeadline() {
        return minInvestDeadline;
    }

    public void setMinInvestDeadline(String minInvestDeadline) {
        this.minInvestDeadline = minInvestDeadline;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getAssignInvestDeadline() {
        return assignInvestDeadline;
    }

    public void setAssignInvestDeadline(String assignInvestDeadline) {
        this.assignInvestDeadline = assignInvestDeadline;
    }

    public String getCouponProfit() {
        return couponProfit;
    }

    public void setCouponProfit(String couponProfit) {
        this.couponProfit = couponProfit;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSupportedProduct() {
        return supportedProduct;
    }

    public void setSupportedProduct(String supportedProduct) {
        this.supportedProduct = supportedProduct;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getSupportedDeadline() {
        return supportedDeadline;
    }

    public void setSupportedDeadline(String supportedDeadline) {
        this.supportedDeadline = supportedDeadline;
    }

    public String getCouponAmountFmt() {
        return couponAmountFmt;
    }

    public void setCouponAmountFmt(String couponAmountFmt) {
        this.couponAmountFmt = couponAmountFmt;
    }

    public String getSupportedPlatform() {
        return supportedPlatform;
    }

    public void setSupportedPlatform(String supportedPlatform) {
        this.supportedPlatform = supportedPlatform;
    }

    public String getUsableStatus() {
        return usableStatus;
    }

    public void setUsableStatus(String usableStatus) {
        this.usableStatus = usableStatus;
    }

    public String getRealActiveDay() {
        return realActiveDay;
    }

    public void setRealActiveDay(String realActiveDay) {
        this.realActiveDay = realActiveDay;
    }
}
