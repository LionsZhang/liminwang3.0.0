package com.example.administrator.lmw.select.entity;

/**
 * Created by lion on 2016/12/9.
 * 活动或者公告信息实体
 */

public class ActivityOrAnoucemnetInfo  {

    private String imgUrl;
    private String linkUrl;
    private int showRate;
    private int type;
    private String postTitle;
    private long effectBeginTime;
    private long effectEndTime;
    private long publishTime;
    private long id;//活动或公告id

    public String getImageUrl() {
        return imgUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imgUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public int getShwoRate() {
        return showRate;
    }

    public void setShwoRate(int shwoRate) {
        this.showRate = shwoRate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return postTitle;
    }

    public void setTitle(String title) {
        this.postTitle = title;
    }

    public long getEffectBeginTime() {
        return effectBeginTime;
    }

    public void setEffectBeginTime(long effectBeginTime) {
        this.effectBeginTime = effectBeginTime;
    }

    public long getEffectEndTime() {
        return effectEndTime;
    }

    public void setEffectEndTime(long effectEndTime) {
        this.effectEndTime = effectEndTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }





}
