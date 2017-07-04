package com.example.administrator.lmw.finance.entity;

/**
 * Created by Administrator on 2017/2/14.
 */

public class ExpectedReturn {

    /**
     * msg : 测试内容edm6
     * code : 73888
     * data : {"expectProfit":65405}
     */

    private String msg;
    private String code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * expectProfit	    预期收益	string
         * is_flow	        是否是浮动收益（1：固定收益，2：浮动收益）
         * maxExpectProfit	最大收益	string
         * minExpectProfit	最小收益	string
         * realAmount	    需支付金额	string
         */

        private String expectProfit;
        private String realAmount;
        private String isFlow;
        private String minExpectProfit;
        private String maxExpectProfit;

        public String getExpectProfit() {
            return expectProfit;
        }

        public void setExpectProfit(String expectProfit) {
            this.expectProfit = expectProfit;
        }

        public String getRealAmount() {
            return realAmount;
        }

        public void setRealAmount(String realAmount) {
            this.realAmount = realAmount;
        }

        public String getIsFlow() {
            return isFlow;
        }

        public void setIsFlow(String isFlow) {
            this.isFlow = isFlow;
        }

        public String getMinExpectProfit() {
            return minExpectProfit;
        }

        public void setMinExpectProfit(String minExpectProfit) {
            this.minExpectProfit = minExpectProfit;
        }

        public String getMaxExpectProfit() {
            return maxExpectProfit;
        }

        public void setMaxExpectProfit(String maxExpectProfit) {
            this.maxExpectProfit = maxExpectProfit;
        }
    }
}
