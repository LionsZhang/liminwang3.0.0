package com.example.administrator.lmw.mine.fill.entity;

import com.example.administrator.lmw.entity.BaseResponse;

/**
 * Created by lion on 2016/9/9.
 */
public class FillEntity extends BaseResponse {
    private FillStatuBean data;

    public FillStatuBean getData() {
        return data;
    }

    public void setData(FillStatuBean data) {
        this.data = data;
    }
}
