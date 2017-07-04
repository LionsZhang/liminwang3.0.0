package com.example.administrator.lmw.finance.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class ProductItemDataBean {

    private String cate_id;//标的类型
    private String cate_name;
    private String identifier;
    private String groudDescription;//外层头部的描述
    private int appType;//标子列表页展示类型
    private String isSearch;
    private boolean isGroupItem = false;//是否带头部第一个item

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getGroudDescription() {
        return groudDescription;
    }

    public void setGroudDescription(String groudDescription) {
        this.groudDescription = groudDescription;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public boolean isGroupItem() {
        return isGroupItem;
    }

    public void setGroupItem(boolean groupItem) {
        isGroupItem = groupItem;
    }

    public String getIsSearch() {
        return isSearch;
    }

    public void setIsSearch(String isSearch) {
        this.isSearch = isSearch;
    }

    /**
     * annualRate	    年利率	number
     * borrowId	        标的标识	string
     * borrowTitle	    标的标题	string
     * buyTotalAmount	项目总金额	number
     * cateId	        分类id	string
     * collectEndTime	募集结束时间	string	格式yyyy-MM-ddHH:mm:ss
     * deadLineType	    借款期限时间类型	string	1=天数|2=自然月|3=年
     * deadLineValue	借款期限	number
     * description	    分类描述	string
     * hasInvestMoney	已投金额	number
     * increaseRate	    加息利率	number
     * isCanTransfer	是否能转让	string	0可以 1 不可以
     * labelStrArr	    标签集合	array<string>	测试1，测试2
     * nowTime	        系统当前时间	string	格式yyyy-MM-ddHH:mm:ss
     * remaMoney	    剩余金额	number
     * repayStatus	    满标之后的状态	number	11-投资中 12-计息中 13-还款中 14-已还款
     * saleTimeEnd	    售卖结束时间	string	格式yyyy-MM-ddHH:mm:ss 没有是返回""
     * status	        标的状态	number	-1-产品删除(0-(初始)待审核 1-审核失败 2-预售 3-(在售)正在招标中 4-（售罄）已满标 5-募集中 6-募集失败 7-募集成功 8-下架 9-已经结束 10-定时发售
     * buyTotalAmountNumber	借款总额格式化后数量	string
     * buyTotalAmountUnit	借款总额格式化后单位
     * remaMoneyNumber	剩余金额格式化数量	string
     * remaMoneyUnit	剩余金额格式化 单位	string
     * isFlow	        是否浮动利率(1=固定利率|2=浮动利率)	string
     * isFlowDead	    是否浮动期限(1=固定期限|2=浮动期限)	string
     * collectLineMaxValue	浮动最大期限	string
     * collectLineMinValue	浮动最小期限	string
     * flowMaxAnnualRate	浮动最大利率	string
     * flowMinAnnualRate	浮动最小利率	string
     * mobileAddInfo	    月增利详情页面	string
     */

    private String borrowTitle;
    private String borrowId;
    private String labels;
    private String annualRate;
    private String increaseRate;
    private int deadLineType;
    private String deadLineValue;
    private int status;
    private String collectBeginTime;
    private String collectEndTime;
    private String cateId;
    private String description;
    private String isCanTransfer;
    private String remaMoney;
    private String hasInvestMoney;
    private String buyTotalAmount;
    private String nowTime;
    private String saleTimeEnd;
    private String startInterestTime;
    private String saleTimeStart;
    private String repayModeStr;
    private String repayStatus;
    private String buyTotalAmountNumber;
    private String buyTotalAmountUnit;
    private String remaMoneyNumber;
    private String remaMoneyUnit;
    private List<String> labelStrArr;
    private long millisInFuture;
    private String isFlow;
    private String isFlowDead;
    private String collectLineMaxValue;
    private String collectLineMinValue;
    private String flowMaxAnnualRate;
    private String flowMinAnnualRate;
    private String mobileAddInfo;

    public String getBorrowTitle() {
        return borrowTitle;
    }

    public void setBorrowTitle(String borrowTitle) {
        this.borrowTitle = borrowTitle;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getAnnualRate() {
        return annualRate;
    }

    public void setAnnualRate(String annualRate) {
        this.annualRate = annualRate;
    }

    public String getIncreaseRate() {
        return increaseRate;
    }

    public void setIncreaseRate(String increaseRate) {
        this.increaseRate = increaseRate;
    }

    public int getDeadLineType() {
        return deadLineType;
    }

    public void setDeadLineType(int deadLineType) {
        this.deadLineType = deadLineType;
    }

    public String getDeadLineValue() {
        return deadLineValue;
    }

    public void setDeadLineValue(String deadLineValue) {
        this.deadLineValue = deadLineValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCollectBeginTime() {
        return collectBeginTime;
    }

    public void setCollectBeginTime(String collectBeginTime) {
        this.collectBeginTime = collectBeginTime;
    }

    public String getCollectEndTime() {
        return collectEndTime;
    }

    public void setCollectEndTime(String collectEndTime) {
        this.collectEndTime = collectEndTime;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsCanTransfer() {
        return isCanTransfer;
    }

    public void setIsCanTransfer(String isCanTransfer) {
        this.isCanTransfer = isCanTransfer;
    }

    public String getRemaMoney() {
        return remaMoney;
    }

    public void setRemaMoney(String remaMoney) {
        this.remaMoney = remaMoney;
    }

    public String getHasInvestMoney() {
        return hasInvestMoney;
    }

    public void setHasInvestMoney(String hasInvestMoney) {
        this.hasInvestMoney = hasInvestMoney;
    }

    public String getBuyTotalAmount() {
        return buyTotalAmount;
    }

    public void setBuyTotalAmount(String buyTotalAmount) {
        this.buyTotalAmount = buyTotalAmount;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getSaleTimeEnd() {
        return saleTimeEnd;
    }

    public void setSaleTimeEnd(String saleTimeEnd) {
        this.saleTimeEnd = saleTimeEnd;
    }

    public String getStartInterestTime() {
        return startInterestTime;
    }

    public void setStartInterestTime(String startInterestTime) {
        this.startInterestTime = startInterestTime;
    }

    public String getSaleTimeStart() {
        return saleTimeStart;
    }

    public void setSaleTimeStart(String saleTimeStart) {
        this.saleTimeStart = saleTimeStart;
    }

    public String getRepayModeStr() {
        return repayModeStr;
    }

    public void setRepayModeStr(String repayModeStr) {
        this.repayModeStr = repayModeStr;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }

    public String getBuyTotalAmountNumber() {
        return buyTotalAmountNumber;
    }

    public void setBuyTotalAmountNumber(String buyTotalAmountNumber) {
        this.buyTotalAmountNumber = buyTotalAmountNumber;
    }

    public String getBuyTotalAmountUnit() {
        return buyTotalAmountUnit;
    }

    public void setBuyTotalAmountUnit(String buyTotalAmountUnit) {
        this.buyTotalAmountUnit = buyTotalAmountUnit;
    }

    public String getRemaMoneyNumber() {
        return remaMoneyNumber;
    }

    public void setRemaMoneyNumber(String remaMoneyNumber) {
        this.remaMoneyNumber = remaMoneyNumber;
    }

    public String getRemaMoneyUnit() {
        return remaMoneyUnit;
    }

    public void setRemaMoneyUnit(String remaMoneyUnit) {
        this.remaMoneyUnit = remaMoneyUnit;
    }

    public List<String> getLabelStrArr() {
        return labelStrArr;
    }

    public void setLabelStrArr(List<String> labelStrArr) {
        this.labelStrArr = labelStrArr;
    }

    public long getMillisInFuture() {
        return millisInFuture;
    }

    public void setMillisInFuture(long millisInFuture) {
        this.millisInFuture = millisInFuture;
    }

    public String getIsFlow() {
        return isFlow;
    }

    public void setIsFlow(String isFlow) {
        this.isFlow = isFlow;
    }

    public String getIsFlowDead() {
        return isFlowDead;
    }

    public void setIsFlowDead(String isFlowDead) {
        this.isFlowDead = isFlowDead;
    }

    public String getCollectLineMaxValue() {
        return collectLineMaxValue;
    }

    public void setCollectLineMaxValue(String collectLineMaxValue) {
        this.collectLineMaxValue = collectLineMaxValue;
    }

    public String getCollectLineMinValue() {
        return collectLineMinValue;
    }

    public void setCollectLineMinValue(String collectLineMinValue) {
        this.collectLineMinValue = collectLineMinValue;
    }

    public String getFlowMaxAnnualRate() {
        return flowMaxAnnualRate;
    }

    public void setFlowMaxAnnualRate(String flowMaxAnnualRate) {
        this.flowMaxAnnualRate = flowMaxAnnualRate;
    }

    public String getFlowMinAnnualRate() {
        return flowMinAnnualRate;
    }

    public void setFlowMinAnnualRate(String flowMinAnnualRate) {
        this.flowMinAnnualRate = flowMinAnnualRate;
    }

    public String getMobileAddInfo() {
        return mobileAddInfo;
    }

    public void setMobileAddInfo(String mobileAddInfo) {
        this.mobileAddInfo = mobileAddInfo;
    }
}
