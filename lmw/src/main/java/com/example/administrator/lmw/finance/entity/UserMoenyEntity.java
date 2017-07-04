package com.example.administrator.lmw.finance.entity;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class UserMoenyEntity {

    /**
     * code : 68408
     * data : {"mobile":"测试内容0ff1","customerName":"测试内容y1gy","balance":84317}
     * msg : 测试内容siv0
     */

    private String code;
    /**
     * mobile : 测试内容0ff1
     * customerName : 测试内容y1gy
     * balance : 84317
     */

    private DataBean data;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public static class DataBean {
        /**
         * 用户手机号码
         */
        private String mobile;
        /**
         * 用户昵称
         */
        private String customerName;
        /**
         * 用户余额
         */
        private double balance;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }
    }
}
