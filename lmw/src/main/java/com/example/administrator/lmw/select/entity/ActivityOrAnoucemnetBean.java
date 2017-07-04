package com.example.administrator.lmw.select.entity;

import java.util.List;

/**
 * Created by lion on 2016/12/9.
 * 活动或者公告信息实体
 * <p>
 * imageUrl	图片地址	string	活动弹窗的图片地址
 * linkUrl	链接地址	string	h5页面地址
 * shwoRate	显示频率	number	显示频率(0 有效期内每天仅第一次启动显示 1 有效期内每次启动都显示 2 有效期内第一次启动显示)
 * type	1 代表活动 2 代表公告	number
 * <p>
 * effectBeginTime		string	开始时间
 * effectEndTime		string	结束时间
 */

public class ActivityOrAnoucemnetBean {

    private List<ActivityOrAnoucemnetInfo> datas;
    private long currentTime;

    public List<ActivityOrAnoucemnetInfo> getDatas() {
        return datas;
    }

    public void setDatas(List<ActivityOrAnoucemnetInfo> data) {
        this.datas = data;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

}
