package com.example.administrator.lmw.mine.card.entity;

/**
 * 卡券列表数量实体
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/3/13 11:49
 **/
public class CouponCountBean {

    /**
     * couponTypeName : '现金券'
     * couponType : '0'
     * usableCount : '3'
     */

    private String couponTypeName;
    private String couponType;
    private int usableCount = 0;

    public String getCouponTypeName() {
        return couponTypeName;
    }

    public void setCouponTypeName(String couponTypeName) {
        this.couponTypeName = couponTypeName;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public int getUsableCount() {
        return usableCount;
    }

    public void setUsableCount(int usableCount) {
        this.usableCount = usableCount;
    }
}
