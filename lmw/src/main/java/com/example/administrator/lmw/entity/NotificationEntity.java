package com.example.administrator.lmw.entity;

import java.io.Serializable;

/**
 * Created by lion on 2016/10/14.
 * <p>
 * {"type":"","id":"","title":"","linkUrl":""}
 * type:// 类型 (0:发布产品定期宝 1:活动 2：公告 3:指定跳转url 4：定期宝子列表 页 5:散标子列表页 6债权标)
 * title 活动或公告标题，
 * linkUrl 活动和公告Url，
 * id 发新标的Id 债权或标的ID
 * "cateId"; // 产品分类id 1001：定期宝 1002散标
 */

public class NotificationEntity implements Serializable {
    private String type;
    private String id;
    private String title;
    private String linkUrl;
    private String cateId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }
}
