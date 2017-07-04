package com.example.administrator.lmw.mine.account.entity;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class AccountDatasBean {
    /**
     * createTime	交易时间	string
     * fundTypeName	类型名称	string
     * handleAmount	交易金额	string
     * remark	    交易备注	string
     * usableAmount	可用余额	string
     */

    private String usableAmount;
    private String fundTypeName;
    private String remark;
    private String handleAmount;
    private String createTime;

    public String getUsableAmount() {
        return usableAmount;
    }

    public void setUsableAmount(String usableAmount) {
        this.usableAmount = usableAmount;
    }

    public String getFundTypeName() {
        return fundTypeName;
    }

    public void setFundTypeName(String fundTypeName) {
        this.fundTypeName = fundTypeName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHandleAmount() {
        return handleAmount;
    }

    public void setHandleAmount(String handleAmount) {
        this.handleAmount = handleAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
