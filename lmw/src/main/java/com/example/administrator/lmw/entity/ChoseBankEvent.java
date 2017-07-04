package com.example.administrator.lmw.entity;

import com.example.administrator.lmw.mine.seting.entity.BankCardInfo;

/**
 * Created by lion on 2016/8/30.
 */
public class ChoseBankEvent extends BaseEvent{
    private BankCardInfo bankCardInfo;

    public BankCardInfo getBankCardInfo() {
        return bankCardInfo;
    }

    public void setBankCardInfo(BankCardInfo bankCardInfo) {
        this.bankCardInfo = bankCardInfo;
    }
}
