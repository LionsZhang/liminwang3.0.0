package com.example.administrator.lmw.finance.entity;

import java.util.List;

/**
 * 债权列表
 * <p/>
 * Created by Administrator on 2016/8/30 0030.
 */
public class CreditEntity {

    /**
     * code : 0
     * msg : success
     * data : {"pageIndex":1,"pageSize":10,"pageCount":15,"totalCount":143,"datas":[{"borrowType":"1","deadLineValue":"142","deadLineType":"天","debtId":"DEBT_O624xKAtIGQEo4CDkf3","debtTitle":"","hasDebtInvestAmt":"","labels":"","labelStrArr":[""],"cornerlabels":"","cornerlabelStrArr":[""],"repayMode":"按月等额本息还款","status":"0","transferAmount":"50000.00","transferEndDate":"2016-04-26 09:32:57","transferRate":"25.25","productType":"1"},{"borrowType":"1","deadLineValue":"142","deadLineType":"天","debtId":"DEBT_x6SjMy0iKia3OBy8gqW","debtTitle":"","hasDebtInvestAmt":"","labels":"","labelStrArr":[""],"cornerlabels":"","cornerlabelStrArr":[""],"repayMode":"按月等额本息还款","status":"0","transferAmount":"50000.00","transferEndDate":"2016-04-26 09:31:51","transferRate":"25.25","productType":"1"},{"borrowType":"1","deadLineValue":"142","deadLineType":"天","debtId":"DEBT_v9AtUoHnYtbR4PDA0GR","debtTitle":"","hasDebtInvestAmt":"","labels":"","labelStrArr":[""],"cornerlabels":"","cornerlabelStrArr":[""],"repayMode":"按月等额本息还款","status":"0","transferAmount":"50000.00","transferEndDate":"2016-04-26 09:30:46","transferRate":"25.25","productType":"1"},{"borrowType":"1","deadLineValue":"142","deadLineType":"天","debtId":"DEBT_RI9tKpJJmc4kxrDl0yZ","debtTitle":"","hasDebtInvestAmt":"","labels":"","labelStrArr":[""],"cornerlabels":"","cornerlabelStrArr":[""],"repayMode":"按月等额本息还款","status":"0","transferAmount":"50000.00","transferEndDate":"2016-04-26 09:11:59","transferRate":"25.25","productType":"1"},{"borrowType":"1","deadLineValue":"142","deadLineType":"天","debtId":"DEBT_3oUMn2QGkNfrEE9LTib","debtTitle":"","hasDebtInvestAmt":"","labels":"","labelStrArr":[""],"cornerlabels":"","cornerlabelStrArr":[""],"repayMode":"按月等额本息还款","status":"0","transferAmount":"50000.00","transferEndDate":"2016-04-26 09:06:44","transferRate":"25.25","productType":"1"},{"borrowType":"1","deadLineValue":"142","deadLineType":"天","debtId":"DEBT_hA8Yyh42MbktqEdTtFv","debtTitle":"","hasDebtInvestAmt":"","labels":"","labelStrArr":[""],"cornerlabels":"","cornerlabelStrArr":[""],"repayMode":"按月等额本息还款","status":"0","transferAmount":"60000.00","transferEndDate":"2016-04-26 08:51:16","transferRate":"25.25","productType":"1"},{"borrowType":"1","deadLineValue":"76","deadLineType":"天","debtId":"DEBT_jGwlvwvkyiVMY2UgFpm","debtTitle":"","hasDebtInvestAmt":"","labels":"","labelStrArr":[""],"cornerlabels":"","cornerlabelStrArr":[""],"repayMode":"按月等额本息还款","status":"3","transferAmount":"10000.00","transferEndDate":"2016-04-15 06:01:26","transferRate":"14.84","productType":"1"},{"borrowType":"1","deadLineValue":"142","deadLineType":"天","debtId":"DEBT_4l0dKpr2cMerDCI8IRS","debtTitle":"","hasDebtInvestAmt":"","labels":"","labelStrArr":[""],"cornerlabels":"","cornerlabelStrArr":[""],"repayMode":"按月等额本息还款","status":"0","transferAmount":"60000.00","transferEndDate":"2016-04-26 08:46:42","transferRate":"25.25","productType":"1"},{"borrowType":"1","deadLineValue":"77","deadLineType":"天","debtId":"DEBT_5Ye1qWl39HzTiwONZox","debtTitle":"","hasDebtInvestAmt":"","labels":"","labelStrArr":[""],"cornerlabels":"","cornerlabelStrArr":[""],"repayMode":"按月等额本息还款","status":"0","transferAmount":"10000.00","transferEndDate":"2016-04-14 16:00:00","transferRate":"14.78","productType":"1"},{"borrowType":"1","deadLineValue":"142","deadLineType":"天","debtId":"DEBT_BQRhv3lYO3PACUOhgKu","debtTitle":"","hasDebtInvestAmt":"","labels":"","labelStrArr":[""],"cornerlabels":"","cornerlabelStrArr":[""],"repayMode":"按月等额本息还款","status":"0","transferAmount":"60000.00","transferEndDate":"2016-04-26 08:46:12","transferRate":"25.25","productType":"1"}]}
     */

    private String code;
    private String msg;
    /**
     * pageIndex : 1
     * pageSize : 10
     * pageCount : 15
     * totalCount : 143
     * datas : [{"borrowType":"1","deadLineValue":"142","deadLineType":"天","debtId":"DEBT_O624xKAtIGQEo4CDkf3","debtTitle":"","hasDebtInvestAmt":"","labels":"","labelStrArr":[""],"cornerlabels":"","cornerlabelStrArr":[""],"repayMode":"按月等额本息还款","status":"0","transferAmount":"50000.00","transferEndDate":"2016-04-26 09:32:57","transferRate":"25.25","productType":"1"},{"borrowType":"1","deadLineValue":"142","deadLineType":"天","debtId":"DEBT_x6SjMy0iKia3OBy8gqW","debtTitle":"","hasDebtInvestAmt":"","labels":"","labelStrArr":[""],"cornerlabels":"","cornerlabelStrArr":[""],"repayMode":"按月等额本息还款","status":"0","transferAmount":"50000.00","transferEndDate":"2016-04-26 09:31:51","transferRate":"25.25","productType":"1"},{"borrowType":"1","deadLineValue":"142","deadLineType":"天","debtId":"DEBT_v9AtUoHnYtbR4PDA0GR","debtTitle":"","hasDebtInvestAmt":"","labels":"","labelStrArr":[""],"cornerlabels":"","cornerlabelStrArr":[""],"repayMode":"按月等额本息还款","status":"0","transferAmount":"50000.00","transferEndDate":"2016-04-26 09:30:46","transferRate":"25.25","productType":"1"},{"borrowType":"1","deadLineValue":"142","deadLineType":"天","debtId":"DEBT_RI9tKpJJmc4kxrDl0yZ","debtTitle":"","hasDebtInvestAmt":"","labels":"","labelStrArr":[""],"cornerlabels":"","cornerlabelStrArr":[""],"repayMode":"按月等额本息还款","status":"0","transferAmount":"50000.00","transferEndDate":"2016-04-26 09:11:59","transferRate":"25.25","productType":"1"},{"borrowType":"1","deadLineValue":"142","deadLineType":"天","debtId":"DEBT_3oUMn2QGkNfrEE9LTib","debtTitle":"","hasDebtInvestAmt":"","labels":"","labelStrArr":[""],"cornerlabels":"","cornerlabelStrArr":[""],"repayMode":"按月等额本息还款","status":"0","transferAmount":"50000.00","transferEndDate":"2016-04-26 09:06:44","transferRate":"25.25","productType":"1"},{"borrowType":"1","deadLineValue":"142","deadLineType":"天","debtId":"DEBT_hA8Yyh42MbktqEdTtFv","debtTitle":"","hasDebtInvestAmt":"","labels":"","labelStrArr":[""],"cornerlabels":"","cornerlabelStrArr":[""],"repayMode":"按月等额本息还款","status":"0","transferAmount":"60000.00","transferEndDate":"2016-04-26 08:51:16","transferRate":"25.25","productType":"1"},{"borrowType":"1","deadLineValue":"76","deadLineType":"天","debtId":"DEBT_jGwlvwvkyiVMY2UgFpm","debtTitle":"","hasDebtInvestAmt":"","labels":"","labelStrArr":[""],"cornerlabels":"","cornerlabelStrArr":[""],"repayMode":"按月等额本息还款","status":"3","transferAmount":"10000.00","transferEndDate":"2016-04-15 06:01:26","transferRate":"14.84","productType":"1"},{"borrowType":"1","deadLineValue":"142","deadLineType":"天","debtId":"DEBT_4l0dKpr2cMerDCI8IRS","debtTitle":"","hasDebtInvestAmt":"","labels":"","labelStrArr":[""],"cornerlabels":"","cornerlabelStrArr":[""],"repayMode":"按月等额本息还款","status":"0","transferAmount":"60000.00","transferEndDate":"2016-04-26 08:46:42","transferRate":"25.25","productType":"1"},{"borrowType":"1","deadLineValue":"77","deadLineType":"天","debtId":"DEBT_5Ye1qWl39HzTiwONZox","debtTitle":"","hasDebtInvestAmt":"","labels":"","labelStrArr":[""],"cornerlabels":"","cornerlabelStrArr":[""],"repayMode":"按月等额本息还款","status":"0","transferAmount":"10000.00","transferEndDate":"2016-04-14 16:00:00","transferRate":"14.78","productType":"1"},{"borrowType":"1","deadLineValue":"142","deadLineType":"天","debtId":"DEBT_BQRhv3lYO3PACUOhgKu","debtTitle":"","hasDebtInvestAmt":"","labels":"","labelStrArr":[""],"cornerlabels":"","cornerlabelStrArr":[""],"repayMode":"按月等额本息还款","status":"0","transferAmount":"60000.00","transferEndDate":"2016-04-26 08:46:12","transferRate":"25.25","productType":"1"}]
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
        private List<CreditDatasBean> datas;

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

        public List<CreditDatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<CreditDatasBean> datas) {
            this.datas = datas;
        }
    }
}
