package com.example.administrator.lmw.mine.credit.Entity;

import java.io.Serializable;

/**
 * Created by lion on 2016/9/13.
 */
public class CreditResultBean implements Serializable{

    private String code;
    private String msg;
    /**  amount	提现金额	string
     applyTime	提现申请时间	string
     arrivalTime	预期到账时间	string
     poundage	提现手续费	string
     state	状态(1 审核中 2 已提现 3 取消 4转账中 5 失败)	number
     withdrawalOrderNo	提现流水号*/
        private String withdrawalOrderNo;
        private String applyTime;
        private String arrivalTime;
        private String amount;
        private String poundage;
        private int state;

        public String getWithdrawalOrderNo() {
            return withdrawalOrderNo;
        }

        public void setWithdrawalOrderNo(String withdrawalOrderNo) {
            this.withdrawalOrderNo = withdrawalOrderNo;
        }

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public String getArrivalTime() {
            return arrivalTime;
        }

        public void setArrivalTime(String arrivalTime) {
            this.arrivalTime = arrivalTime;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPoundage() {
            return poundage;
        }

        public void setPoundage(String poundage) {
            this.poundage = poundage;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }









