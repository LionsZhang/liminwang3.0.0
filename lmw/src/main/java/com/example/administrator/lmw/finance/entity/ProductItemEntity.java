package com.example.administrator.lmw.finance.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class ProductItemEntity {


    /**
     * code : 0
     * msg : success
     * data : {"pageIndex":1,"pageSize":10,"pageCount":1,"totalCount":5,"datas":[{"borrowTitle":"1","borrowId":"1","labels":"","annualRate":"1.00","increaseRate":"1.00","deadLineType":1,"deadLineValue":1,"status":3,"collectBeginTime":"","collectEndTime":"2016-08-09 10:58:09","cateId":"1001","description":"稳赚收益 还可以转让变活期","isCanTransfer":"1","remaMoney":"1.00","hasInvestMoney":"1.00","buyTotalAmount":"10000000.00","labelStrArr":[""]},{"borrowTitle":"测试定期宝65465464","borrowId":"BORROW_qufNnuYMZ4msCZi1tbB","labels":"","annualRate":"4.00","increaseRate":"4.00","deadLineType":1,"deadLineValue":4,"status":6,"collectBeginTime":"","collectEndTime":"2016-08-18 02:32:02","cateId":"1001","description":"稳赚收益 还可以转让变活期","isCanTransfer":"1","remaMoney":"999400.00","hasInvestMoney":"600.00","buyTotalAmount":"1000000.00","labelStrArr":[""]},{"borrowTitle":"测试定期宝55","borrowId":"5","labels":"","annualRate":"5.00","increaseRate":"5.00","deadLineType":1,"deadLineValue":5,"status":9,"collectBeginTime":"","collectEndTime":"","cateId":"1001","description":"稳赚收益 还可以转让变活期","isCanTransfer":"1","remaMoney":"5.00","hasInvestMoney":"5.00","buyTotalAmount":"10000000.00","labelStrArr":[""]},{"borrowTitle":"测试标的","borrowId":"3","labels":"测试、车市1","annualRate":"3.00","increaseRate":"3.00","deadLineType":2,"deadLineValue":3,"status":3,"collectBeginTime":"","collectEndTime":"2016-08-31 08:59:28","cateId":"1001","description":"稳赚收益 还可以转让变活期","isCanTransfer":"0","remaMoney":"3.00","hasInvestMoney":"3.00","buyTotalAmount":"10000000.00","labelStrArr":["测试、车市1"]},{"borrowTitle":"定期宝C20160804010","borrowId":"22","labels":"","annualRate":"2.00","increaseRate":"2.00","deadLineType":3,"deadLineValue":90,"status":3,"collectBeginTime":"","collectEndTime":"","cateId":"1001","description":"稳赚收益 还可以转让变活期","isCanTransfer":"1","remaMoney":"","hasInvestMoney":"","buyTotalAmount":"10000000.00","labelStrArr":[""]}]}
     */

    private String code;
    private String msg;
    /**
     * pageIndex : 1
     * pageSize : 10
     * pageCount : 1
     * totalCount : 5
     * datas : [{"borrowTitle":"1","borrowId":"1","labels":"","annualRate":"1.00","increaseRate":"1.00","deadLineType":1,"deadLineValue":1,"status":3,"collectBeginTime":"","collectEndTime":"2016-08-09 10:58:09","cateId":"1001","description":"稳赚收益 还可以转让变活期","isCanTransfer":"1","remaMoney":"1.00","hasInvestMoney":"1.00","buyTotalAmount":"10000000.00","labelStrArr":[""]},{"borrowTitle":"测试定期宝65465464","borrowId":"BORROW_qufNnuYMZ4msCZi1tbB","labels":"","annualRate":"4.00","increaseRate":"4.00","deadLineType":1,"deadLineValue":4,"status":6,"collectBeginTime":"","collectEndTime":"2016-08-18 02:32:02","cateId":"1001","description":"稳赚收益 还可以转让变活期","isCanTransfer":"1","remaMoney":"999400.00","hasInvestMoney":"600.00","buyTotalAmount":"1000000.00","labelStrArr":[""]},{"borrowTitle":"测试定期宝55","borrowId":"5","labels":"","annualRate":"5.00","increaseRate":"5.00","deadLineType":1,"deadLineValue":5,"status":9,"collectBeginTime":"","collectEndTime":"","cateId":"1001","description":"稳赚收益 还可以转让变活期","isCanTransfer":"1","remaMoney":"5.00","hasInvestMoney":"5.00","buyTotalAmount":"10000000.00","labelStrArr":[""]},{"borrowTitle":"测试标的","borrowId":"3","labels":"测试、车市1","annualRate":"3.00","increaseRate":"3.00","deadLineType":2,"deadLineValue":3,"status":3,"collectBeginTime":"","collectEndTime":"2016-08-31 08:59:28","cateId":"1001","description":"稳赚收益 还可以转让变活期","isCanTransfer":"0","remaMoney":"3.00","hasInvestMoney":"3.00","buyTotalAmount":"10000000.00","labelStrArr":["测试、车市1"]},{"borrowTitle":"定期宝C20160804010","borrowId":"22","labels":"","annualRate":"2.00","increaseRate":"2.00","deadLineType":3,"deadLineValue":90,"status":3,"collectBeginTime":"","collectEndTime":"","cateId":"1001","description":"稳赚收益 还可以转让变活期","isCanTransfer":"1","remaMoney":"","hasInvestMoney":"","buyTotalAmount":"10000000.00","labelStrArr":[""]}]
     */

    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int pageIndex;
        private int pageSize;
        private int pageCount;
        private int totalCount;
        /**
         * borrowTitle : 1
         * borrowId : 1
         * labels :
         * annualRate : 1.00
         * increaseRate : 1.00
         * deadLineType : 1
         * deadLineValue : 1
         * status : 3
         * collectBeginTime :
         * collectEndTime : 2016-08-09 10:58:09
         * cateId : 1001
         * description : 稳赚收益 还可以转让变活期
         * isCanTransfer : 1
         * remaMoney : 1.00
         * hasInvestMoney : 1.00
         * buyTotalAmount : 10000000.00
         * labelStrArr : [""]
         */

        private List<ProductItemDataBean> datas;

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<ProductItemDataBean> getDatas() {
            return datas;
        }

        public void setDatas(List<ProductItemDataBean> datas) {
            this.datas = datas;
        }
    }
}
