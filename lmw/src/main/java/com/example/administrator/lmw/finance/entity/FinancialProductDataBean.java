package com.example.administrator.lmw.finance.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class FinancialProductDataBean {


    /**
     * borrowTitle : 定期宝A16110101
     * borrowId : BORROW_k2TTsiaVZZucVWQpgrk
     * labels :
     * annualRate : 1.00
     * increaseRate : 0.00
     * deadLineType : 1
     * deadLineValue : 30
     * status : 5
     * collectBeginTime :
     * collectEndTime : 2016-11-12 00:01:25
     * cateId : 1001
     * description : 定期宝分类
     * isCanTransfer : 1
     * remaMoney : 9000.00
     * hasInvestMoney : 1000.00
     * buyTotalAmount : 10000.00
     * labelStrArr : null
     * saleTimeEnd :
     * saleTimeStart : 2016-11-05 00:01:25
     * isRepay : 0
     * nowTime : 2016-11-01 00:21:09
     */

    private String borrowTitle;
    private String borrowId;
    private String labels;
    private String annualRate;
    private String increaseRate;
    private int deadLineType;
    private int deadLineValue;
    private int status;
    private String collectBeginTime;
    private String collectEndTime;
    private String cateId;
    private String description;
    private String isCanTransfer;
    private String remaMoney;
    private String hasInvestMoney;
    private String buyTotalAmount;
    private List<String> labelStrArr;
    private String saleTimeEnd;
    private String saleTimeStart;
    private int isRepay;
    private String nowTime;
    private String startInterestTime;
    private String repayModeStr;

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

    public int getDeadLineValue() {
        return deadLineValue;
    }

    public void setDeadLineValue(int deadLineValue) {
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

    public List<String> getLabelStrArr() {
        return labelStrArr;
    }

    public void setLabelStrArr(List<String> labelStrArr) {
        this.labelStrArr = labelStrArr;
    }

    public String getSaleTimeEnd() {
        return saleTimeEnd;
    }

    public void setSaleTimeEnd(String saleTimeEnd) {
        this.saleTimeEnd = saleTimeEnd;
    }

    public String getSaleTimeStart() {
        return saleTimeStart;
    }

    public void setSaleTimeStart(String saleTimeStart) {
        this.saleTimeStart = saleTimeStart;
    }

    public int getIsRepay() {
        return isRepay;
    }

    public void setIsRepay(int isRepay) {
        this.isRepay = isRepay;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getStartInterestTime() {
        return startInterestTime;
    }

    public void setStartInterestTime(String startInterestTime) {
        this.startInterestTime = startInterestTime;
    }

    public String getRepayModeStr() {
        return repayModeStr;
    }

    public void setRepayModeStr(String repayModeStr) {
        this.repayModeStr = repayModeStr;
    }
}
