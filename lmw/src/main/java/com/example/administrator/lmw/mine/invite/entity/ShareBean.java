package com.example.administrator.lmw.mine.invite.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/9.
 */

public class ShareBean implements Serializable {


    /**
     * createTime	创建时间(格式:yyyy-MM-dd HH:mm:ss)	string	[示例：2016-10-01 00:00:00]
     * imageUrl	图片URL	string	[示例：https://www.baidu.com/img/bd_logo1.png]
     * publishTime	发布时间(格式:yyyy-MM-dd HH:mm:ss)	string	[示例：2016-10-01 00:00:00]
     * shareContent	内容	string	[示例：【利民网】注册享好礼，预计年化收益8.5%-13.5%，前海股权交易中心挂牌上市（代码：666339）]
     * shareTitle	标题	string	[示例：送你548元现金券，和我一起来利民网赚钱吧!]
     * targetUrl	目标链接URL	string	[示例：http://www.baidu.com]
     */
    private String createTime;
    private String targetUrl;
    private String shareTitle;
    private String shareContent;
    private String publishTime;
    private String imageUrl;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
