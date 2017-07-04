package com.example.administrator.lmw.login.entity;

import com.example.administrator.lmw.entity.BaseResponse;

/**
 * Created by lion on 2016/8/30.
 */
public class MineData extends BaseResponse{
    private MineDataBean data;

    public MineDataBean getMineDataBean() {
        return data;
    }

    public void setMineDataBean(MineDataBean mineDataBean) {
        this.data = mineDataBean;
    }
}
