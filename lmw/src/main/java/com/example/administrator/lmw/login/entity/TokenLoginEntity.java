package com.example.administrator.lmw.login.entity;

import com.example.administrator.lmw.entity.BaseResponse;

/**
 * Created by lion on 2016/8/29.
 */
public class TokenLoginEntity extends BaseResponse {

    private TokenLoginBean data;

    public TokenLoginBean getData() {
        return data;
    }

    public void setData(TokenLoginBean data) {
        this.data = data;
    }
}
