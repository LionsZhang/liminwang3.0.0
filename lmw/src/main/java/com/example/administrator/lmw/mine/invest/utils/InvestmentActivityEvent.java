package com.example.administrator.lmw.mine.invest.utils;

import com.example.administrator.lmw.entity.BaseEvent;
import com.example.administrator.lmw.mine.invest.entity.InvestmentTransferEntity;

/**
 * Created by Administrator on 2017/4/5.
 */

public class InvestmentActivityEvent extends BaseEvent {
    private InvestmentTransferEntity investmentTransferEntity;

    public InvestmentTransferEntity getInvestmentTransferEntity() {
        return investmentTransferEntity;
    }

    public void setInvestmentTransferEntity(InvestmentTransferEntity investmentTransferEntity) {
        this.investmentTransferEntity = investmentTransferEntity;
    }
}
