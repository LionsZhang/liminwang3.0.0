package com.example.administrator.lmw.mine.fill.entity;

import java.io.Serializable;

/**
 * Created by lion on 2016/9/13.
 */
public class FillResultBean implements Serializable {

    private String code;
    private String msg;
    /**
     * amount	充值金额(单位：元)	string	@mock=测试内容8000
     * customerId		string	@mock=测试内容6fl6
     * rechargeNo	充值订单号	string	@mock=测试内容xs15
     * rechargeTime	充值时间	string	@mock=测试内容w8ly
     * rechargeType	充值类型	string	@mock=测试内容61mh
     * result	充值结果（0，正在处理中，1，已成功,2:失败）	string	@mock=测试内容57h8
     * resultMsg	结果描述	string	@mock=测试内容m2ey
     * sucTime	成功时间	string	@mock=测试内容e5e4
     */
    private String customerId;
    private String rechargeNo;
    private String rechargeTime;
    private String amount;
    private String rechargeType;
    private String result;
    private String resultMsg;
    private String sucTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRechargeNo() {
        return rechargeNo;
    }

    public void setRechargeNo(String rechargeNo) {
        this.rechargeNo = rechargeNo;
    }

    public String getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(String rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getSucTime() {
        return sucTime;
    }

    public void setSucTime(String sucTime) {
        this.sucTime = sucTime;
    }
}









