package com.example.administrator.lmw.entity;

/**
 *  开通存管状态 
 *  @author snubee
 *  @email snubee96@gmail.com
 *  @time 2017/5/24 15:23
 *
**/
public class DespositStatus {

    /**
     * openDpStatus : 2
     * reason : 证件已过期
     * prompt : 存管账户开通失败，被银行退回，需要银行存管账户开通成功后才能进行后续操作
     */

    private String openDpStatus;
    private String reason;
    private String prompt;

    public String getOpenDpStatus() {
        return openDpStatus;
    }

    public void setOpenDpStatus(String openDpStatus) {
        this.openDpStatus = openDpStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
