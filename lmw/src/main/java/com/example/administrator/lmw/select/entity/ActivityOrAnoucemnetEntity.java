package com.example.administrator.lmw.select.entity;

import com.example.administrator.lmw.entity.BaseResponse;

import java.util.List;

/**
 * Created by lion on 2016/12/9.
 * 活动或者公告信息实体
 */

public class ActivityOrAnoucemnetEntity extends BaseResponse {
    private ActivityOrAnoucemnetBean data;

    public ActivityOrAnoucemnetBean getData() {
        return data;
    }

    public void setData(ActivityOrAnoucemnetBean data) {
        this.data = data;
    }
}
