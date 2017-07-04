package com.example.administrator.lmw.mine.invest.entity;

import java.util.List;

/**
 * 续投设置初始化V3
 * <p>
 * Created by Administrator on 2017/3/14.
 */

public class ContinueSetEntity {

    /**
     * annualRate	            年化	string
     * borrowTitle	            标题	string
     * continueBorrowType	    续投产品类型 (1004车贷宝,1005易优贷,1001定期	string
     * continueBorrowTypeDatas	array<object>
     * key	                    键	string
     * value	                值	string
     * <p>
     * continueDeadLine	        续投产品期限 (30,60,90,180,360,720)	string
     * continueDeadLineDatas	array<object>
     * key	                    键	string
     * value	                值	string
     * <p>
     * continueRepayMode	    续投回款类型(1-等额本息3-一次性还本付息 4-等额本金 ,5按月付息到期还本 )	string
     * continueRepayModeDatas	array<object>
     * key	                    键	string
     * value	                值	string
     * <p>
     * deadLineType	            期限类型(1=天数|2=自然月|3=年)	string
     * deadLineValue	        期限	string
     * increaseRate	            加息	string
     * <p>
     * investId	                投资iD	string
     * mode	                    回款续投方式(-1-不续投，0-本息续投、1-本金续投、2-利息续投)	string
     * modeDatas		        array<object>
     * key	                    键	string
     * value	                值	string
     * <p>
     * repayDate	            下一回款日	string
     * stillSum	                回款本息	string
     * <p>
     * isFlow	                是否浮动利率(1=固定利率|2=浮动利率)	string
     * isFlowDead	            是否浮动期限(1=固定期限|2=浮动期限)	string
     * collectLineMaxValue	    浮动最大期限	string
     * collectLineMinValue	    浮动最小期限	string
     * flowMaxAnnualRate	    浮动最大利率	string
     * flowMinAnnualRate	    浮动最小利率	string
     */

    private String borrowTitle;
    private String annualRate;
    private String increaseRate;
    private String deadLineValue;
    private String deadLineType;
    private String repayDate;
    private String stillSum;
    private String mode;
    private String continueRepayMode;
    private String continueBorrowType;
    private String continueDeadLine;
    private String investId;
    private List<ValueKeyBeans> continueBorrowTypeDatas;
    private List<ValueKeyBeans> continueDeadLineDatas;
    private List<ValueKeyBeans> continueRepayModeDatas;
    private List<ValueKeyBeans> modeDatas;
    private String isFlow;
    private String isFlowDead;
    private String collectLineMaxValue;
    private String collectLineMinValue;
    private String flowMaxAnnualRate;
    private String flowMinAnnualRate;
    private String isOutTime;
    private String actualDays;

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

    public String getIncreaseRate() {
        return increaseRate;
    }

    public void setIncreaseRate(String increaseRate) {
        this.increaseRate = increaseRate;
    }

    public String getDeadLineValue() {
        return deadLineValue;
    }

    public void setDeadLineValue(String deadLineValue) {
        this.deadLineValue = deadLineValue;
    }

    public String getDeadLineType() {
        return deadLineType;
    }

    public void setDeadLineType(String deadLineType) {
        this.deadLineType = deadLineType;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public String getStillSum() {
        return stillSum;
    }

    public void setStillSum(String stillSum) {
        this.stillSum = stillSum;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getContinueRepayMode() {
        return continueRepayMode;
    }

    public void setContinueRepayMode(String continueRepayMode) {
        this.continueRepayMode = continueRepayMode;
    }

    public String getContinueBorrowType() {
        return continueBorrowType;
    }

    public void setContinueBorrowType(String continueBorrowType) {
        this.continueBorrowType = continueBorrowType;
    }

    public String getContinueDeadLine() {
        return continueDeadLine;
    }

    public void setContinueDeadLine(String continueDeadLine) {
        this.continueDeadLine = continueDeadLine;
    }

    public String getInvestId() {
        return investId;
    }

    public void setInvestId(String investId) {
        this.investId = investId;
    }

    public List<ValueKeyBeans> getContinueBorrowTypeDatas() {
        return continueBorrowTypeDatas;
    }

    public void setContinueBorrowTypeDatas(List<ValueKeyBeans> continueBorrowTypeDatas) {
        this.continueBorrowTypeDatas = continueBorrowTypeDatas;
    }

    public List<ValueKeyBeans> getContinueDeadLineDatas() {
        return continueDeadLineDatas;
    }

    public void setContinueDeadLineDatas(List<ValueKeyBeans> continueDeadLineDatas) {
        this.continueDeadLineDatas = continueDeadLineDatas;
    }

    public List<ValueKeyBeans> getContinueRepayModeDatas() {
        return continueRepayModeDatas;
    }

    public void setContinueRepayModeDatas(List<ValueKeyBeans> continueRepayModeDatas) {
        this.continueRepayModeDatas = continueRepayModeDatas;
    }

    public List<ValueKeyBeans> getModeDatas() {
        return modeDatas;
    }

    public void setModeDatas(List<ValueKeyBeans> modeDatas) {
        this.modeDatas = modeDatas;
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

    public String getIsOutTime() {
        return isOutTime;
    }

    public void setIsOutTime(String isOutTime) {
        this.isOutTime = isOutTime;
    }

    public String getActualDays() {
        return actualDays;
    }

    public void setActualDays(String actualDays) {
        this.actualDays = actualDays;
    }
}
