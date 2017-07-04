package com.example.administrator.lmw.mine.seting.entity;

import com.example.administrator.lmw.entity.BaseResponse;

/**
 * Created by lion on 2016/9/5.
 */
public class BankCardEntity extends BaseResponse {

    /**
     * msg :
     * code : 0
     * data : {"bankList":[{"bankId":12071,"bankName":"测试内容9r42"}],"idCarNum":"测试内容4hy7","realName":"测试内容cyg8"}
     */

    private BankCardBean data;


    public BankCardBean getData() {
        return data;
    }

    public void setData(BankCardBean data) {
        this.data = data;
    }

}
