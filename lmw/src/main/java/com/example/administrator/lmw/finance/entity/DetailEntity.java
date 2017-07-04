package com.example.administrator.lmw.finance.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 债权详情
 * <p/>
 * Created by Administrator on 2016/9/6 0006.
 */
public class DetailEntity implements Serializable {

    /**
     * code : 0
     * msg : success
     * data : {"nowTime":"2016-09-06 15:11:59","baseInfoDesc":["适用利民网备用金计划"],"collectDesc":null,"labelStrArr":null,"productInfoList":[{"type":"startInvestAmount","typeName":"起投金额","value":"10.00元,以10000.00元为单位递增"},{"type":"repaymentWay","typeName":"本息方式","value":"先息后本还款"},{"type":"sourceRate","typeName":"标的利率","value":"原标的年利率1.00%,转让人让利1.00%"},{"type":"startBuyTime","typeName":"转让开始","value":"2016-08-19 11:57:02"},{"type":"endBuyTime","typeName":"转让完成","value":"满标即止"}],"borrowTitle":"测试债权4","debtId":"DEBT_hx5bPtnBk9ERtOfhlUQ","deadLineType":"1","deadLineValue":"71","paymentMode":null,"buyTotalAmount":null,"increaseRate":"+1.00","transferRate":"45.34","minTenderedMoney":"10.00","increaseTenderedMoney":"10000.00","maxTenderedMoney":"1000000.00","status":"1","isCollect":null,"collectBeginTime":null,"collectEndTime":null,"collectRate":null,"labelStr":null,"commonProblemUrl":"","securityUrl":"","descriptionUrl":"","hasInvestMoney":null,"borrowType":"","investPeople":"","remaMoney":"","collectFinishTime":"","saleTimeStart":null}
     */

    private String code;
    private String msg;
    /**
     * nowTime : 2016-09-06 15:11:59
     * baseInfoDesc : ["适用利民网备用金计划"]
     * collectDesc : null
     * labelStrArr : null
     * productInfoList : [{"type":"startInvestAmount","typeName":"起投金额","value":"10.00元,以10000.00元为单位递增"},{"type":"repaymentWay","typeName":"本息方式","value":"先息后本还款"},{"type":"sourceRate","typeName":"标的利率","value":"原标的年利率1.00%,转让人让利1.00%"},{"type":"startBuyTime","typeName":"转让开始","value":"2016-08-19 11:57:02"},{"type":"endBuyTime","typeName":"转让完成","value":"满标即止"}]
     * borrowTitle : 测试债权4
     * debtId : DEBT_hx5bPtnBk9ERtOfhlUQ
     * deadLineType : 1
     * deadLineValue : 71
     * paymentMode : null
     * buyTotalAmount : null
     * increaseRate : +1.00
     * transferRate : 45.34
     * minTenderedMoney : 10.00
     * increaseTenderedMoney : 10000.00
     * maxTenderedMoney : 1000000.00
     * status : 1
     * isCollect : null
     * collectBeginTime : null
     * collectEndTime : null
     * collectRate : null
     * labelStr : null
     * commonProblemUrl :
     * securityUrl :
     * descriptionUrl :
     * hasInvestMoney : null
     * borrowType :
     * investPeople :
     * remaMoney :
     * collectFinishTime :
     * saleTimeStart : null
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

    public static class DataBean implements Serializable {


        /**
         * debtPrincipalAmt : 22222.00
         * sourceRate : 10.00
         * singleMaxTenderedMoney : 977778.00
         * discount : 1.00
         * showDetailFlag : 0
         * transferAmount : 22647.92
         * hasDebtInvestAmt : 22222.00
         * nowTime : 2016-10-24 15:13:53
         * contractUrls : [{"type":"contractUrl","typeName":"测试协议","value":"www.baidu.com"}]
         * baseInfoDesc : []
         * collectDesc : null
         * labelStrArr : null
         * productInfoList : [{"type":"startDate","typeName":"起息日","value":"2016-10-23"},{"type":"endDate","typeName":"到期日","value":"2017-08-10"},{"type":"repayMode","typeName":"收益方式","value":"一次还本付息"},{"type":"sourceRate","typeName":"原年化收益","value":"10.00%"},{"type":"discount","typeName":"折让比例","value":"1.00%"}]
         * productInfoListDown : [{"type":"transferAmount","typeName":"转让总额","value":"22647.92万元"},{"type":"investAmt","typeName":"投资金额","value":"100.00元起投,1.00元递增"},{"type":"transferTime","typeName":"转让开始","value":"2016-10-19"},{"type":"transferEndDate","typeName":"转让结束","value":"2016-10-22"}]
         * borrowTitle : 定期宝D16101901
         * debtTitle : Z161019002
         * debtId : DEBT_aaK0va3kTeWhw71wnmq
         * deadLineType : 天
         * holdDay : 69
         * deadLineValue : 291
         * paymentMode : null
         * buyTotalAmount : null
         * increaseRate : +0.00
         * transferRate : 11.16
         * minTenderedMoney : 100.00
         * increaseTenderedMoney : 1.00
         * maxTenderedMoney : 1000000.00
         * status : 1
         * isCollect : null
         * collectBeginTime : null
         * collectEndTime : null
         * collectRate : null
         * labelStr : null
         * commonProblemUrl :
         * securityUrl :
         * descriptionUrl :
         * hasInvestMoney : null
         * borrowType : 1
         * investPeople : 1
         * remaMoney : 0.00
         * collectFinishTime : 2016-10-22 15:19:15
         * saleTimeStart : null
         * transferEndDate : 2016-10-22 18:44:36
         * contractUrl : /contract/debt.html
         */

        private String debtPrincipalAmt;
        private String sourceRate;
        private String singleMaxTenderedMoney;
        private String discount;
        private String showDetailFlag;
        private String transferAmount;
        private String hasDebtInvestAmt;
        private String nowTime;
        private String collectDesc;
        private String labelStrArr;
        private String borrowTitle;
        private String debtTitle;
        private String debtId;
        private String deadLineType;
        private String holdDay;
        private String deadLineValue;
        private String paymentMode;
        private String buyTotalAmount;
        private String increaseRate;
        private String transferRate;
        private String minTenderedMoney;
        private String increaseTenderedMoney;
        private String maxTenderedMoney;
        private String status;
        private String isCollect;
        private String collectBeginTime;
        private String collectEndTime;
        private String collectRate;
        private String labelStr;
        private String commonProblemUrl;
        private String securityUrl;
        private String descriptionUrl;
        private String hasInvestMoney;
        private String borrowType;
        private String investPeople;
        private String remaMoney;
        private String collectFinshTime;
        private String saleTimeStart;
        private String transferEndDate;
        private String contractUrl;
        private String repayMode;
        private String lastInvestTime;
        /**
         * type : contractUrl
         * typeName : 测试协议
         * value : www.baidu.com
         */

        private List<ContractUrlsBean> contractUrls;
        private List<String> baseInfoDesc;
        /**
         * type : startDate
         * typeName : 起息日
         * value : 2016-10-23
         */

        private List<ContractUrlsBean> productInfoList;
        /**
         * type : transferAmount
         * typeName : 转让总额
         * value : 22647.92万元
         */

        private List<ContractUrlsBean> productInfoListDown;

        public String getDeadLineValue() {
            return deadLineValue;
        }

        public void setDeadLineValue(String deadLineValue) {
            this.deadLineValue = deadLineValue;
        }

        public String getDebtPrincipalAmt() {
            return debtPrincipalAmt;
        }

        public void setDebtPrincipalAmt(String debtPrincipalAmt) {
            this.debtPrincipalAmt = debtPrincipalAmt;
        }

        public String getSourceRate() {
            return sourceRate;
        }

        public void setSourceRate(String sourceRate) {
            this.sourceRate = sourceRate;
        }

        public String getSingleMaxTenderedMoney() {
            return singleMaxTenderedMoney;
        }

        public void setSingleMaxTenderedMoney(String singleMaxTenderedMoney) {
            this.singleMaxTenderedMoney = singleMaxTenderedMoney;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getShowDetailFlag() {
            return showDetailFlag;
        }

        public void setShowDetailFlag(String showDetailFlag) {
            this.showDetailFlag = showDetailFlag;
        }

        public String getTransferAmount() {
            return transferAmount;
        }

        public void setTransferAmount(String transferAmount) {
            this.transferAmount = transferAmount;
        }

        public String getHasDebtInvestAmt() {
            return hasDebtInvestAmt;
        }

        public void setHasDebtInvestAmt(String hasDebtInvestAmt) {
            this.hasDebtInvestAmt = hasDebtInvestAmt;
        }

        public String getNowTime() {
            return nowTime;
        }

        public void setNowTime(String nowTime) {
            this.nowTime = nowTime;
        }

        public String getCollectDesc() {
            return collectDesc;
        }

        public void setCollectDesc(String collectDesc) {
            this.collectDesc = collectDesc;
        }

        public String getLabelStrArr() {
            return labelStrArr;
        }

        public void setLabelStrArr(String labelStrArr) {
            this.labelStrArr = labelStrArr;
        }

        public String getBorrowTitle() {
            return borrowTitle;
        }

        public void setBorrowTitle(String borrowTitle) {
            this.borrowTitle = borrowTitle;
        }

        public String getDebtTitle() {
            return debtTitle;
        }

        public void setDebtTitle(String debtTitle) {
            this.debtTitle = debtTitle;
        }

        public String getDebtId() {
            return debtId;
        }

        public void setDebtId(String debtId) {
            this.debtId = debtId;
        }

        public String getDeadLineType() {
            return deadLineType;
        }

        public void setDeadLineType(String deadLineType) {
            this.deadLineType = deadLineType;
        }

        public String getHoldDay() {
            return holdDay;
        }

        public void setHoldDay(String holdDay) {
            this.holdDay = holdDay;
        }

        public String getPaymentMode() {
            return paymentMode;
        }

        public void setPaymentMode(String paymentMode) {
            this.paymentMode = paymentMode;
        }

        public String getBuyTotalAmount() {
            return buyTotalAmount;
        }

        public void setBuyTotalAmount(String buyTotalAmount) {
            this.buyTotalAmount = buyTotalAmount;
        }

        public String getIncreaseRate() {
            return increaseRate;
        }

        public void setIncreaseRate(String increaseRate) {
            this.increaseRate = increaseRate;
        }

        public String getTransferRate() {
            return transferRate;
        }

        public void setTransferRate(String transferRate) {
            this.transferRate = transferRate;
        }

        public String getMinTenderedMoney() {
            return minTenderedMoney;
        }

        public void setMinTenderedMoney(String minTenderedMoney) {
            this.minTenderedMoney = minTenderedMoney;
        }

        public String getIncreaseTenderedMoney() {
            return increaseTenderedMoney;
        }

        public void setIncreaseTenderedMoney(String increaseTenderedMoney) {
            this.increaseTenderedMoney = increaseTenderedMoney;
        }

        public String getMaxTenderedMoney() {
            return maxTenderedMoney;
        }

        public void setMaxTenderedMoney(String maxTenderedMoney) {
            this.maxTenderedMoney = maxTenderedMoney;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(String isCollect) {
            this.isCollect = isCollect;
        }

        public String getCollectBeginTime() {
            return collectBeginTime;
        }

        public void setCollectBeginTime(String collectBeginTime) {
            this.collectBeginTime = collectBeginTime;
        }

        public String getCollectEndTime() {
            return collectEndTime;
        }

        public void setCollectEndTime(String collectEndTime) {
            this.collectEndTime = collectEndTime;
        }

        public String getCollectRate() {
            return collectRate;
        }

        public void setCollectRate(String collectRate) {
            this.collectRate = collectRate;
        }

        public String getLabelStr() {
            return labelStr;
        }

        public void setLabelStr(String labelStr) {
            this.labelStr = labelStr;
        }

        public String getCommonProblemUrl() {
            return commonProblemUrl;
        }

        public void setCommonProblemUrl(String commonProblemUrl) {
            this.commonProblemUrl = commonProblemUrl;
        }

        public String getSecurityUrl() {
            return securityUrl;
        }

        public void setSecurityUrl(String securityUrl) {
            this.securityUrl = securityUrl;
        }

        public String getDescriptionUrl() {
            return descriptionUrl;
        }

        public void setDescriptionUrl(String descriptionUrl) {
            this.descriptionUrl = descriptionUrl;
        }

        public String getHasInvestMoney() {
            return hasInvestMoney;
        }

        public void setHasInvestMoney(String hasInvestMoney) {
            this.hasInvestMoney = hasInvestMoney;
        }

        public String getBorrowType() {
            return borrowType;
        }

        public void setBorrowType(String borrowType) {
            this.borrowType = borrowType;
        }

        public String getInvestPeople() {
            return investPeople;
        }

        public void setInvestPeople(String investPeople) {
            this.investPeople = investPeople;
        }

        public String getRemaMoney() {
            return remaMoney;
        }

        public void setRemaMoney(String remaMoney) {
            this.remaMoney = remaMoney;
        }

        public String getCollectFinshTime() {
            return collectFinshTime;
        }

        public void setCollectFinshTime(String collectFinshTime) {
            this.collectFinshTime = collectFinshTime;
        }

        public String getSaleTimeStart() {
            return saleTimeStart;
        }

        public void setSaleTimeStart(String saleTimeStart) {
            this.saleTimeStart = saleTimeStart;
        }

        public String getTransferEndDate() {
            return transferEndDate;
        }

        public void setTransferEndDate(String transferEndDate) {
            this.transferEndDate = transferEndDate;
        }

        public String getContractUrl() {
            return contractUrl;
        }

        public void setContractUrl(String contractUrl) {
            this.contractUrl = contractUrl;
        }

        public String getRepayMode() {
            return repayMode;
        }

        public void setRepayMode(String repayMode) {
            this.repayMode = repayMode;
        }

        public String getLastInvestTime() {
            return lastInvestTime;
        }

        public void setLastInvestTime(String lastInvestTime) {
            this.lastInvestTime = lastInvestTime;
        }

        public List<ContractUrlsBean> getContractUrls() {
            return contractUrls;
        }

        public void setContractUrls(List<ContractUrlsBean> contractUrls) {
            this.contractUrls = contractUrls;
        }

        public List<String> getBaseInfoDesc() {
            return baseInfoDesc;
        }

        public void setBaseInfoDesc(List<String> baseInfoDesc) {
            this.baseInfoDesc = baseInfoDesc;
        }

        public List<ContractUrlsBean> getProductInfoList() {
            return productInfoList;
        }

        public void setProductInfoList(List<ContractUrlsBean> productInfoList) {
            this.productInfoList = productInfoList;
        }

        public List<ContractUrlsBean> getProductInfoListDown() {
            return productInfoListDown;
        }

        public void setProductInfoListDown(List<ContractUrlsBean> productInfoListDown) {
            this.productInfoListDown = productInfoListDown;
        }

        public static class ContractUrlsBean implements Serializable {
            private String type;
            private String typeName;
            private String value;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

    }
}
