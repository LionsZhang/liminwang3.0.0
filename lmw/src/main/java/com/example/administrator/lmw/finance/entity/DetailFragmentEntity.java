package com.example.administrator.lmw.finance.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class DetailFragmentEntity implements Serializable {


    /**
     * code : 0
     * msg : success
     * data : {"nowTime":"2016-09-20 11:18:24","contractUrls":[],"baseInfoDesc":["适用利民网备用金计划","持有60天后可转让"],"collectDesc":"募集时间截止后如未满标，募集期收益及本金将一并汇入您的利民账户","labelStrArr":["可转让"," 加息"],"productInfoList":[{"type":"startDate","typeName":"起息日","value":"2016-09-21"},{"type":"endDate","typeName":"到期日","value":"2017-03-19"},{"type":"repayMode","typeName":"收益方式","value":"一次性还清"},{"type":"collectProfit","typeName":"募集期收益","value":"1.5000%,募集结束次日发放"},{"type":"startBuyTime","typeName":"认购开始时间","value":"2016-09-20 10:34:55"},{"type":"collectFinish","typeName":"募集完成","value":"满标即止"},{"type":"repayMode","typeName":"收益方式","value":"一次性还清"}],"borrowTitle":"定期宝C16092002","borrowId":"BORROW_J8XGOQbFuwbDxlu0m2P","deadLineType":"天","deadLineValue":"180","paymentMode":null,"buyTotalAmount":"1500000.00","increaseRate":"+1.00","annualRate":"12.00","minTenderedMoney":"100.00","increaseTenderedMoney":"200.00","maxTenderedMoney":"1500000.00","status":"5","isCollect":"1","collectEndTime":"2016-10-05 02:37:18","collectRate":"1.50","labels":"可转让, 加息","commonProblemUrl":"","securityUrl":"","descriptionUrl":"","hasInvestMoney":"","borrowType":"","investPeople":"0","remaMoney":"1500000.00","collectFinishTime":"","saleTimeStart":"2016-09-20 02:37:18"}
     */

    private String code;
    private String msg;
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
         * annualRate	            年化率	string
         * avaiCashNum	            可使用现金卷张数	string
         * baseInfoDesc	            基础信息描述	array<string>
         * borrowId	                基础信息描述	string
         * borrowTitle	            标的标题	string
         * borrowType	            标的类型,1，定期宝，2信贷通，3，车贷宝,4,体验金，5，定期宝子项目,6,赎楼贷，7新手专享标	string
         * buyTotalAmount	        借款总额	string
         * buyedNovice	            是否已经购买的新手标，0-没有、1-买过	string
         * canUseCoupon	            募集截止时间可以为nul格式yyyy-MM-ddHH:mm:ss	string
         * cateId	                类型id	string
         * collectEndTime	        募集截止时间可以为nul格式yyyy-MM-ddHH:mm:ss	string
         * collectFinishTime	    满标时间	string
         * collectRate	            募集期收益率	string
         * commonProblemUrl	        常见问题url	string
         * continueFlag	            续投标识,0-不支持续投、1-支持续投
         * contractUrl	            购买协议url	string
         * contractUrls	            购买协议集合	array<object>
         * deadLineType	            借款期限类型(1=天数|2=自然月|3=年)	string
         * deadLineValue	        借款期限值	string
         * descriptionUrl	        标的详情url	string
         * hasInvestMoney	        已投金额	string
         * increaseRate	            加息年利率	string
         * increaseTenderedMoney	递增投标金额	string
         * investPeople	            投资人数	string
         * isCanTransfer    	    是否可以转让标识，0-可以转让、1-不可以转让	string
         * isCollect	            是否需要募集开始及截止时间(1=不需要|2=需要)	string
         * labelStrArr	            标签多个数据用,隔开	array<string>
         * labels	                标签多个数据用,隔开	string
         * maxTenderedMoney	        最大投标金额	string
         * minTenderedMoney	        最小投资金额	string
         * nowTime	                现在时间	string
         * productInfoList	        产品基本信息集合	array<object>
         * remaMoney	            剩余金额	string
         * saleTimeStart	        销售开始时间	string
         * securityUrl	            安全协议地址	string
         * showDetailFlag	        展示状态 0-前端页面写死、1-后台返回	string
         * singleBuyedMoney	        个人已经认购额度	string
         * singleMaxTenderedMoney	个人剩余认购上限认购上线	string
         * status	                2-预售3-(在售)正在招标中4-（售罄）已满标5-募集中6-募集失败7-募集成功9-已经结束
         * isFlow	                是否浮动利率(1=固定利率|2=浮动利率)	string
         * isFlowDead	            是否浮动期限(1=固定期限|2=浮动期限)	string
         * collectFinishTime	    满标时间
         * collectLineMaxValue	    最大期限	string
         * collectLineMinValue	    最小期限	string
         * flowMaxAnnualRate	    浮动最大利率	string
         * flowMinAnnualRate	    浮动最小利率	string
         */
        private String nowTime;
        private String collectDesc;
        private String borrowTitle;
        private String borrowId;
        private String deadLineType;
        private String deadLineValue;
        private Object paymentMode;
        private String buyTotalAmount;
        private String increaseRate;
        private String annualRate;
        private String minTenderedMoney;
        private String increaseTenderedMoney;
        private String maxTenderedMoney;
        private String status;
        private String isCollect;
        private String cateId;
        private String collectEndTime;
        private String collectRate;
        private String labels;
        private String commonProblemUrl;
        private String securityUrl;
        private String descriptionUrl;
        private String hasInvestMoney;
        private String borrowType;
        private String investPeople;
        private String remaMoney;
        private String collectFinshTime;
        private String saleTimeStart;
        private String contractUrl;
        private String singleMaxTenderedMoney;
        private String buyedNovice;
        private String saleTimeEnd;
        private String startInterestTime;
        private String avaiCashNum;
        private String isFlow;
        private String isFlowDead;
        private String continueFlag;
        private String collectFinishTime;
        private String collectLineMaxValue;
        private String collectLineMinValue;
        private String flowMaxAnnualRate;
        private String flowMinAnnualRate;
        private double isRepay;
        private List<String> contractUrls;
        private List<String> baseInfoDesc;
        private List<String> labelStrArr;
        /**
         * type : startDate
         * typeName : 起息日
         * value : 2016-09-21
         */

        private List<SharedBean> productInfoList;

        private List<SharedBean> productInfoListDown;
        private List<SharedBean> continueTypeList;

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

        public String getBorrowTitle() {
            return borrowTitle;
        }

        public void setBorrowTitle(String borrowTitle) {
            this.borrowTitle = borrowTitle;
        }

        public String getBorrowId() {
            return borrowId;
        }

        public void setBorrowId(String borrowId) {
            this.borrowId = borrowId;
        }

        public String getDeadLineType() {
            return deadLineType;
        }

        public void setDeadLineType(String deadLineType) {
            this.deadLineType = deadLineType;
        }

        public String getDeadLineValue() {
            return deadLineValue;
        }

        public void setDeadLineValue(String deadLineValue) {
            this.deadLineValue = deadLineValue;
        }

        public Object getPaymentMode() {
            return paymentMode;
        }

        public void setPaymentMode(Object paymentMode) {
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

        public String getAnnualRate() {
            return annualRate;
        }

        public void setAnnualRate(String annualRate) {
            this.annualRate = annualRate;
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

        public String getLabels() {
            return labels;
        }

        public void setLabels(String labels) {
            this.labels = labels;
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

        public String getContractUrl() {
            return contractUrl;
        }

        public void setContractUrl(String contractUrl) {
            this.contractUrl = contractUrl;
        }

        public String getSingleMaxTenderedMoney() {
            return singleMaxTenderedMoney;
        }

        public void setSingleMaxTenderedMoney(String singleMaxTenderedMoney) {
            this.singleMaxTenderedMoney = singleMaxTenderedMoney;
        }

        public String getBuyedNovice() {
            return buyedNovice;
        }

        public void setBuyedNovice(String buyedNovice) {
            this.buyedNovice = buyedNovice;
        }

        public String getSaleTimeEnd() {
            return saleTimeEnd;
        }

        public void setSaleTimeEnd(String saleTimeEnd) {
            this.saleTimeEnd = saleTimeEnd;
        }

        public String getStartInterestTime() {
            return startInterestTime;
        }

        public void setStartInterestTime(String startInterestTime) {
            this.startInterestTime = startInterestTime;
        }

        public String getAvaiCashNum() {
            return avaiCashNum;
        }

        public void setAvaiCashNum(String avaiCashNum) {
            this.avaiCashNum = avaiCashNum;
        }

        public String getContinueFlag() {
            return continueFlag;
        }

        public void setContinueFlag(String continueFlag) {
            this.continueFlag = continueFlag;
        }

        public double getIsRepay() {
            return isRepay;
        }

        public void setIsRepay(double isRepay) {
            this.isRepay = isRepay;
        }

        public List<String> getContractUrls() {
            return contractUrls;
        }

        public void setContractUrls(List<String> contractUrls) {
            this.contractUrls = contractUrls;
        }

        public List<String> getBaseInfoDesc() {
            return baseInfoDesc;
        }

        public void setBaseInfoDesc(List<String> baseInfoDesc) {
            this.baseInfoDesc = baseInfoDesc;
        }

        public List<String> getLabelStrArr() {
            return labelStrArr;
        }

        public void setLabelStrArr(List<String> labelStrArr) {
            this.labelStrArr = labelStrArr;
        }

        public List<SharedBean> getProductInfoList() {
            return productInfoList;
        }

        public void setProductInfoList(List<SharedBean> productInfoList) {
            this.productInfoList = productInfoList;
        }

        public List<SharedBean> getProductInfoListDown() {
            return productInfoListDown;
        }

        public void setProductInfoListDown(List<SharedBean> productInfoListDown) {
            this.productInfoListDown = productInfoListDown;
        }

        public List<SharedBean> getContinueTypeList() {
            return continueTypeList;
        }

        public void setContinueTypeList(List<SharedBean> continueTypeList) {
            this.continueTypeList = continueTypeList;
        }

        public String getCateId() {
            return cateId;
        }

        public void setCateId(String cateId) {
            this.cateId = cateId;
        }

        public String getCollectFinishTime() {
            return collectFinishTime;
        }

        public void setCollectFinishTime(String collectFinishTime) {
            this.collectFinishTime = collectFinishTime;
        }

        public String getCollectLineMaxValue() {
            return collectLineMaxValue;
        }

        public void setCollectLineMaxValue(String collectLineMaxValue) {
            this.collectLineMaxValue = collectLineMaxValue;
        }

        public String getCollectLineMinValue() {
            return collectLineMinValue;
        }

        public void setCollectLineMinValue(String collectLineMinValue) {
            this.collectLineMinValue = collectLineMinValue;
        }

        public String getFlowMaxAnnualRate() {
            return flowMaxAnnualRate;
        }

        public void setFlowMaxAnnualRate(String flowMaxAnnualRate) {
            this.flowMaxAnnualRate = flowMaxAnnualRate;
        }

        public String getFlowMinAnnualRate() {
            return flowMinAnnualRate;
        }

        public void setFlowMinAnnualRate(String flowMinAnnualRate) {
            this.flowMinAnnualRate = flowMinAnnualRate;
        }

        public String getIsFlow() {
            return isFlow;
        }

        public void setIsFlow(String isFlow) {
            this.isFlow = isFlow;
        }

        public String getIsFlowDead() {
            return isFlowDead;
        }

        public void setIsFlowDead(String isFlowDead) {
            this.isFlowDead = isFlowDead;
        }
    }
}
