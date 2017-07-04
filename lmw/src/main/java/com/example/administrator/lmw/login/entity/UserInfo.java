package com.example.administrator.lmw.login.entity;

import com.example.administrator.lmw.entity.BaseResponse;

/**
 * Created by lion on 2016/8/25.
 */
public class UserInfo extends BaseResponse {



    private UserInfoBean data;

    public  UserInfoBean getData() {
        return data;
    }

    public void setData( UserInfoBean data) {
        this.data = data;
    }

}
