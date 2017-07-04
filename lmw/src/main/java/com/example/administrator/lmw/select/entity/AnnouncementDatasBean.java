package com.example.administrator.lmw.select.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class AnnouncementDatasBean implements Serializable {

    private String linkUrl;
    private String createTime;
    private String noticeId;
    private String postTitle;

    public AnnouncementDatasBean() {
        super();
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }
}
