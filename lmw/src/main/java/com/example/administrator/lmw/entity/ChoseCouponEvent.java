package com.example.administrator.lmw.entity;

import com.example.administrator.lmw.mine.card.entity.CashCouponBeanInfo;

/**
 * Created by lion on 2016/8/30.
 */
public class ChoseCouponEvent extends BaseEvent{
    private CashCouponBeanInfo cashCouponBeanInfo;

    public CashCouponBeanInfo getCashCouponBeanInfo() {
        return cashCouponBeanInfo;
    }

    public void setCashCouponBeanInfo(CashCouponBeanInfo cashCouponBeanInfo) {
        this.cashCouponBeanInfo = cashCouponBeanInfo;
    }
}
