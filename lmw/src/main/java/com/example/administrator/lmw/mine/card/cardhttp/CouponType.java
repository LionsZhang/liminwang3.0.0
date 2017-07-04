package com.example.administrator.lmw.mine.card.cardhttp;


/**
 * 卡券类型
 * 卡券类型(0现金券;1:红包;2抵扣券;3加息券;4理财金)
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/3/13 11:33
 **/
public enum CouponType {
    CASH_COUPON("0"),
    RED_PACKET("1"),
    VOUCHER("2"),
    RAISE_COUPON("3"),
    FINANCIAL_GOLD("4");

    private String couponType;

    private CouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponType() {
        return this.couponType;
    }
}
