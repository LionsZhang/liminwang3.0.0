package com.example.administrator.lmw.mine.card.entity;

/**
 * 卡券实体类
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/2/20 17:43
 **/
public class CashDatasBean {


    private boolean isOpen;//是否展开
    /**
     * supportedProduct : '定期宝、车贷宝'
     * minInvestAmount : '1000.00'
     * effectiveDate : '2017.02.25 - 2017.02.28'
     * assignInvestDeadline : '30,60'
     * minInvestDeadline : '30'
     * useTime : '2016-08-01 00:00:00'
     * couponName : '188投资'
     * supportedPlatform : 'app端、pc网页端、微信端'
     * useStatus : '1'
     * couponId : '168521'
     * couponAmountFmt : '10'
     * endTime : '2016-09-01 00:00:00'
     * couponTypeName : '现金券'
     * supportedDeadline : '90天及以上'
     * beginTime : '2016-08-01 00:00:00'
     * couponAmount : '10.00'
     * couponType : '0'
     * maxActiveDay : '30'
     * acquireSource : '公司周年庆'
     * useRule : '年化投资额不少于1000元'
     */


    private String supportedProduct;
    private String minInvestAmount;
    private String effectiveDate;
    private String assignInvestDeadline;
    private String minInvestDeadline;
    private String useDate;
    private String couponName;
    private String supportedPlatform;
    private int useStatus;
    private String couponId;
    private String couponAmountFmt;
    private String endTime;
    private String couponTypeName;
    private String supportedDeadline;
    private String beginTime;
    private String couponAmount;
    private int couponType;
    private String maxActiveDay;
    private String acquireSource;
    private String useRule;
    private String investPlatform;//投资平台
    private String investProduct;//投资产品
    private String useTime;//使用时间(格式:yyyy-MM-dd HH:mm:ss)
    private String endDate;//结束日期
    private String beginDate;//开始日期

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }


    public String getSupportedProduct() {
        return supportedProduct;
    }

    public void setSupportedProduct(String supportedProduct) {
        this.supportedProduct = supportedProduct;
    }

    public String getMinInvestAmount() {
        return minInvestAmount;
    }

    public void setMinInvestAmount(String minInvestAmount) {
        this.minInvestAmount = minInvestAmount;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getAssignInvestDeadline() {
        return assignInvestDeadline;
    }

    public void setAssignInvestDeadline(String assignInvestDeadline) {
        this.assignInvestDeadline = assignInvestDeadline;
    }

    public String getMinInvestDeadline() {
        return minInvestDeadline;
    }

    public void setMinInvestDeadline(String minInvestDeadline) {
        this.minInvestDeadline = minInvestDeadline;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getSupportedPlatform() {
        return supportedPlatform;
    }

    public void setSupportedPlatform(String supportedPlatform) {
        this.supportedPlatform = supportedPlatform;
    }

    public int getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(int useStatus) {
        this.useStatus = useStatus;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponAmountFmt() {
        return couponAmountFmt;
    }

    public void setCouponAmountFmt(String couponAmountFmt) {
        this.couponAmountFmt = couponAmountFmt;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCouponTypeName() {
        return couponTypeName;
    }

    public void setCouponTypeName(String couponTypeName) {
        this.couponTypeName = couponTypeName;
    }

    public String getSupportedDeadline() {
        return supportedDeadline;
    }

    public void setSupportedDeadline(String supportedDeadline) {
        this.supportedDeadline = supportedDeadline;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public String getMaxActiveDay() {
        return maxActiveDay;
    }

    public void setMaxActiveDay(String maxActiveDay) {
        this.maxActiveDay = maxActiveDay;
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

    public String getUseDate() {
        return useDate;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }

    public String getInvestPlatform() {
        return investPlatform;
    }

    public void setInvestPlatform(String investPlatform) {
        this.investPlatform = investPlatform;
    }

    public String getInvestProduct() {
        return investProduct;
    }

    public void setInvestProduct(String investProduct) {
        this.investProduct = investProduct;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }
}
