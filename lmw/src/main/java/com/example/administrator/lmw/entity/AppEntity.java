package com.example.administrator.lmw.entity;

/**
 * Created by Administrator on 2016/10/14.
 */

public class AppEntity extends BaseResponse {
    private AppInfo data;

    public AppInfo getData() {
        return data;
    }

    public void setData(AppInfo data) {
        this.data = data;
    }
}
