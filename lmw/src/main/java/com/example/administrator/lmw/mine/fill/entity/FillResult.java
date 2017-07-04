package com.example.administrator.lmw.mine.fill.entity;

import com.example.administrator.lmw.entity.BaseResponse;

/**
 * Created by lion on 2016/9/13.
 */
public class FillResult extends BaseResponse {
    private FillResultBean data;

    public FillResultBean getData() {
        return data;
    }

    public void setData(FillResultBean data) {
        this.data = data;
    }

}


