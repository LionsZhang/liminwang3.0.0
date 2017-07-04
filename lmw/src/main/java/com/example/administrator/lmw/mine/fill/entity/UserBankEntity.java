package com.example.administrator.lmw.mine.fill.entity;

import com.example.administrator.lmw.entity.BaseResponse;

import java.util.List;

/**
 * Created by lion on 2016/9/9.
 */
public class UserBankEntity extends BaseResponse {

    /**
     * bindBankName : 测试内容e188
     * bindBankNo : 测试内容07b0
     * customerName : 测试内容0319
     * bankList : [{"bankCode":"测试内容od6q","bankName":"测试内容oo36"}]
     * balance : 测试内容196l
     * dailyLimit : 测试内容c4xm
     * bindBankCode : 测试内容261n
     * singleLimit : 测试内容0y5z
     */

    private UserBankInfo data;

    public UserBankInfo getData() {
        return data;
    }

    public void setData(UserBankInfo data) {
        this.data = data;
    }

}
