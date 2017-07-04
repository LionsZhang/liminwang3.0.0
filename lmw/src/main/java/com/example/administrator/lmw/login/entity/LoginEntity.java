package com.example.administrator.lmw.login.entity;

import com.example.administrator.lmw.entity.BaseResponse;

/**
 * Created by lion on 2016/8/29.
 */
public class LoginEntity extends BaseResponse {
    private LoginBean data;
    public LoginBean getData() {
        return data;
    }

    public void setData(LoginBean data) {
        this.data = data;
    }
}
