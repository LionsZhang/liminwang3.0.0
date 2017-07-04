package com.example.administrator.lmw.mine.credit.Entity;

import com.example.administrator.lmw.entity.BaseResponse;

/**
 * Created by lion on 2016/9/13.
 */
public class CreditResult extends BaseResponse{
    private CreditResultBean data;

    public CreditResultBean getData() {
        return data;
    }

    public void setData(CreditResultBean data) {
        this.data = data;
    }

}


