package com.example.administrator.lmw.mine.credit.Entity;

import com.example.administrator.lmw.entity.BaseResponse;

/**
 * Created by lion on 2016/9/13.
 */
public class CreditInfo extends BaseResponse{
    private CreditBean data;

    public CreditBean getData() {
        return data;
    }

    public void setData(CreditBean data) {
        this.data = data;
    }


}
