package com.example.administrator.lmw.mine.seting.entity;

import java.io.Serializable;

/**
 * Created by lion on 2016/9/5.
 */
public class BindBankBean implements Serializable {


    private String orderNo;
    private String cipher;
    private String rspData;
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCipher() {
        return cipher;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    public String getRspData() {
        return rspData;
    }

    public void setRspData(String rspData) {
        this.rspData = rspData;
    }
}
