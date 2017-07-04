package com.example.administrator.lmw.mine.card.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lion on 2017/2/20.
 */

public class CashCouponUseScope implements Serializable {
    /**
     * supportedCateList	支持的类目	array<string>	定期宝180天及以上项目可用
     * supportedPlatform	支持的平台	string	app端、pc网页端、微信端
     */
    private List<String> supportedCateList;
    private String supportedPlatform;

    public List<String> getSupportedCateList() {
        return supportedCateList;
    }

    public void setSupportedCateList(List<String> supportedCateList) {
        this.supportedCateList = supportedCateList;
    }

    public String getSupportedPlatform() {
        return supportedPlatform;
    }

    public void setSupportedPlatform(String supportedPlatform) {
        this.supportedPlatform = supportedPlatform;
    }
}
