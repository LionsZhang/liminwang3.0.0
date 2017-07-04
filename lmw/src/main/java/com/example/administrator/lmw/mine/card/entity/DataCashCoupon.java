package com.example.administrator.lmw.mine.card.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 选择优惠券实体data
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/4/27 17:34
 **/
public class DataCashCoupon implements Serializable {
    private int num;
    private List<CashCouponBeanInfo> datas;
    private String nonceStr;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<CashCouponBeanInfo> getDatas() {
        return datas;
    }

    public void setDatas(List<CashCouponBeanInfo> datas) {
        this.datas = datas;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }
}
