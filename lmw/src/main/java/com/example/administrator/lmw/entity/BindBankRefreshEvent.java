package com.example.administrator.lmw.entity;

/**
 * 事件分发的基类
 *
 * @author lion
 * @Description:TODO
 * @Date 2016-8-19
 */
public class BindBankRefreshEvent extends BaseEvent {
    private String oderNo;

    public BindBankRefreshEvent() {

    }

    public String getOderNo() {
        return oderNo;
    }

    public void setOderNo(String oderNo) {
        this.oderNo = oderNo;
    }
}
