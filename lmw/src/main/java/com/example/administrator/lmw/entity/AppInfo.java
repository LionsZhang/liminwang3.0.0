package com.example.administrator.lmw.entity;

import java.io.Serializable;

/**
 * Created by lion on 2016/10/14.
 */

public class AppInfo implements Serializable {
    /**
     * downloadUrl	下载地址	string
     * isForceUpdate	是否强制更新	string	0=否、1=是
     * platformModel	应用平台	string	0=android、1=ios
     * updateContent	更新内容	string
     * version	版本号	string
     *
     *,"isDesignatedUpgrade":1,"designatedVersion":""
     * ":{"id":"2","version":"2.0.0","updateContent":
     * "测试2","platformModel":"0",
     * "downloadUrl":"222","isForceUpdate":"0","updateTime":null,"createTime":null}}
     */
    private String downloadUrl;
    private String isForceUpdate;
    private String platformModel;
    private String updateContent;
    private String version;
    private int isDesignatedUpgrade;
    private String designatedVersion;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getIsForceUpdate() {
        return isForceUpdate;
    }

    public void setIsForceUpdate(String isForceUpdate) {
        this.isForceUpdate = isForceUpdate;
    }

    public String getPlatformModel() {
        return platformModel;
    }

    public void setPlatformModel(String platformModel) {
        this.platformModel = platformModel;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getIsDesignatedUpgrade() {
        return isDesignatedUpgrade;
    }

    public void setIsDesignatedUpgrade(int isDesignatedUpgrade) {
        this.isDesignatedUpgrade = isDesignatedUpgrade;
    }

    public String getDesignatedVersion() {
        return designatedVersion;
    }

    public void setDesignatedVersion(String designatedVersion) {
        this.designatedVersion = designatedVersion;
    }
}
