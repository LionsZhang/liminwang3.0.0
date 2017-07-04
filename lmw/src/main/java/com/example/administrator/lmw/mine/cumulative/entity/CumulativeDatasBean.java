package com.example.administrator.lmw.mine.cumulative.entity;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class CumulativeDatasBean {


    /**
     * awardAmount	收益金额	string
     * awardName	收益名称	string
     * awardRemark	收益来源	string
     * getTime	    收益获得时间	string
     */

    private String awardAmount;
    private String awardName;
    private String awardRemark;
    private String getTime;

    public String getAwardAmount() {
        return awardAmount;
    }

    public void setAwardAmount(String awardAmount) {
        this.awardAmount = awardAmount;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getAwardRemark() {
        return awardRemark;
    }

    public void setAwardRemark(String awardRemark) {
        this.awardRemark = awardRemark;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }
}