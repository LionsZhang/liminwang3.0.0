package com.example.administrator.lmw.mine.seting.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lion on 2016/9/5.
 */
public class BankCardBean implements Serializable{
    private String idCarNum;//银行卡号
    private String realName;//用户名字
    /**
     * bankId : 12071
     * bankName : 测试内容9r42
     */

    private List<BankCardInfo> datas;

    public String getIdCarNum() {
        return idCarNum;
    }

    public void setIdCarNum(String idCarNum) {
        this.idCarNum = idCarNum;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public List<BankCardInfo> getBankList() {
        return datas;
    }

    public void setBankList(List<BankCardInfo> bankList) {
        this.datas = bankList;
    }
}
