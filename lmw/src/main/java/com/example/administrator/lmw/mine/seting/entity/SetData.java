package com.example.administrator.lmw.mine.seting.entity;

import com.example.administrator.lmw.entity.BaseResponse;

/**
 * Created by lion on 2016/9/2.
 */
public class SetData extends BaseResponse {



    private SetDataBean data;



    public SetDataBean getData() {
        return data;
    }

    public void setData(SetDataBean data) {
        this.data = data;
    }

}