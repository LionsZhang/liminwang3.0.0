package com.example.administrator.lmw.finance.entity;

/**
 * Created by Administrator on 2016/12/26.
 */

public class DataCateExtend {


    /**
     * cateId : 1001
     * port : 12
     * imageUrl : 12
     * linkHref : 12
     * isDisable : 0
     */

    private String cateId;
    private String port;
    private String imageUrl;
    private String linkHref;
    private String isDisable;
    private String cateName;

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkHref() {
        return linkHref;
    }

    public void setLinkHref(String linkHref) {
        this.linkHref = linkHref;
    }

    public String getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(String isDisable) {
        this.isDisable = isDisable;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }
}
