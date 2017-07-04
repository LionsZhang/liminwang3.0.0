package com.example.administrator.lmw.entity;

import java.io.Serializable;

/**
 * Created by lion on 2017/5/11.
 */

public class DepositoryEntity implements Serializable{

    private DepositoryInfo xwBankAbsractRsp;

    public DepositoryInfo getXwBankAbsractRsp() {
        return xwBankAbsractRsp;
    }

    public void setXwBankAbsractRsp(DepositoryInfo xwBankAbsractRsp) {
        this.xwBankAbsractRsp = xwBankAbsractRsp;
    }
}

