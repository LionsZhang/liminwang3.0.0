package com.example.administrator.lmw.select.entity;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class SelectlistDataBeanInfo {
    /**
     * annualRate	年化	string
     * borrowId	标的ID	string
     * borrowTitle	标的名称	string
     * buyTotalAmount	借款总金额	string
     * cateId	类型 1001 定期宝,1002散标,1003新手专享标,1004车贷宝，1005优易贷	string
     * deadLineType	筹标类型 1=天数|2=自然月|3=年	string
     * deadLineValue	筹标期限	string
     * increaseRate	加息	string
     * labels	理财师标签多个数据用,隔开	string
     * minTenderedMoney	最小投标金额	string
     * nowTime	系统当前时间	string
     * remaMoney	剩余投标金额	string
     * repayModeStr	还款方式	string
     * repayStatus	还款状态 string 9准备抢购 10-立即抢购11-已满标 12-计息中 13-还款中 14-已	string
     * saleTimeStart	开始售卖时间	string
     * status	0 标的状态   -2作废 -1-产品删除(0-(初始)待审核 1-审核失败 2-自动队列(待预售)3-(在售)正在招标中 4-（售罄）已满标  5-募集中
     * 6-募集失败 7-募集成功 8-下架(停售) 9-已经结束 10-定时发售（预售） )
     * <p>
     * 预售中（进度为0时）、已满标、计息中、已还完（进度为100%时）
     * <p>
     * remaMoneyNumber	剩余金额 数量	string
     * remaMoneyUnit	剩余金额单位	string
     * buyTotalAmountNumber	总金额 数字	string
     * buyTotalAmountUnit	总金额 单位
     * flowMaxAnnualRate	浮动最大利率	string
     * flowMinAnnualRate	浮动最小利率	string
     * isFlow	            是否浮动利率(1=固定利率|2=浮动利率)	string
     * isFlowDead	        是否浮动期限(1=固定期限|2=浮动期限)	string
     * collectLineMaxValue	募集产品最大期限天数	string
     * collectLineMinValue	募集产品最小期限天数	string
     */
    public String borrowId;//标的ID
    public String borrowTitle;//标的名称
    public String annualRate;//年化
    public String deadLineType;//筹标类型 1=天数|2=自然月|3=年
    public String deadLineValue;//筹标期限
    public String status;//标的状态(0 (初始)待审核 1 审核失败 2 预售 3 (在售)正在招标中 4 （售罄）已满标  5 募集中 6-募集失败 7募集成功
    // 8 下架 9-已经结束、10，预售
    public String cateId;//类型 1001 定期宝,1002散标,1003新手专享标,2000债权
    public String repayStatus;//文字的状态 10-立即抢购11-已满标 12-计息中 13-还款中 14-已还款
    public String increaseRate;//加息
    public String saleTimeStart;//开始售卖时间
    public String nowTime;//当前时间
    public String minTenderedMoney;//起投金额
    public String labels;//首页标签文字用逗号隔开
    public String remaMoney;//	剩余投标金额
    public String repayModeStr;    //还款方式
    public String buyTotalAmount;    //借款总金额
    public String remaMoneyNumber;//剩余金额
    public String remaMoneyUnit;//剩余金额单位
    public String buyTotalAmountNumber;//总金额 数字
    public String buyTotalAmountUnit;//总金额 单位
    private String flowMaxAnnualRate;// 浮动最大利率
    private String flowMinAnnualRate;// 浮动最小利率
    private String isFlow;// 是否浮动利率(1=固定利率|2=浮动利率)
    private String isFlowDead; // 是否浮动期限(1=固定期限|2=浮动期限)
    private String collectLineMaxValue;// 募集产品最大期限天数
    private String collectLineMinValue;// 募集产品最小期限天数
    private String mobileAddInfo;// 标得详情 URL

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getBorrowTitle() {
        return borrowTitle;
    }

    public void setBorrowTitle(String borrowTitle) {
        this.borrowTitle = borrowTitle;
    }

    public String getAnnualRate() {
        return annualRate;
    }

    public void setAnnualRate(String annualRate) {
        this.annualRate = annualRate;
    }

    public String getDeadLineType() {
        return deadLineType;
    }

    public void setDeadLineType(String deadLineType) {
        this.deadLineType = deadLineType;
    }

    public String getDeadLineValue() {
        return deadLineValue;
    }

    public void setDeadLineValue(String deadLineValue) {
        this.deadLineValue = deadLineValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }

    public String getIncreaseRate() {
        return increaseRate;
    }

    public void setIncreaseRate(String increaseRate) {
        this.increaseRate = increaseRate;
    }

    public String getSaleTimeStart() {
        return saleTimeStart;
    }

    public void setSaleTimeStart(String saleTimeStart) {
        this.saleTimeStart = saleTimeStart;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getMinTenderedMoney() {
        return minTenderedMoney;
    }

    public void setMinTenderedMoney(String minTenderedMoney) {
        this.minTenderedMoney = minTenderedMoney;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getRemaMoney() {
        return remaMoney;
    }

    public void setRemaMoney(String remaMoney) {
        this.remaMoney = remaMoney;
    }

    public String getRepayModeStr() {
        return repayModeStr;
    }

    public void setRepayModeStr(String repayModeStr) {
        this.repayModeStr = repayModeStr;
    }

    public String getBuyTotalAmount() {
        return buyTotalAmount;
    }

    public void setBuyTotalAmount(String buyTotalAmount) {
        this.buyTotalAmount = buyTotalAmount;
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

    public String getMobileAddInfo() {
        return mobileAddInfo;
    }

    public void setMobileAddInfo(String mobileAddInfo) {
        this.mobileAddInfo = mobileAddInfo;
    }
}
