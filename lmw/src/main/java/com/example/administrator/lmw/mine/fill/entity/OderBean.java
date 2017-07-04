package com.example.administrator.lmw.mine.fill.entity;

import java.io.Serializable;

/**
 * Created by lion on 2016/9/9.
 */
public class OderBean implements Serializable {

    /**
     * customerId : 测试内容jn9w
     * orderNo : 测试内容0ot6
     */
    private String customerId;
    private String orderNo;
    private String respCode;
    private String respMsg;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
}
