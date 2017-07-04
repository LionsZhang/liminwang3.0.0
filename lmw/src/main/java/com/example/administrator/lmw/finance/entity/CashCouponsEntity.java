package com.example.administrator.lmw.finance.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class CashCouponsEntity {

    public DataBean data;
    /**
     * data : {"datas":[{"couponAmount":"100","useStatus":1,"couponCode":"22222hkc"},{"couponAmount":"100","useStatus":1,"couponCode":"1111jhkc"}]}
     * pageCount : 68540
     * msg : 成功
     * code : 0
     */

    private int pageCount;
    private String msg;
    private String code;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * couponAmount : 100
         * useStatus : 1
         * couponCode : 22222hkc
         */

        private List<CashCouponsDatasBean> datas;

        public List<CashCouponsDatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<CashCouponsDatasBean> datas) {
            this.datas = datas;
        }
    }
}
