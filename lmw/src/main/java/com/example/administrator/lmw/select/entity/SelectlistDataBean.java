package com.example.administrator.lmw.select.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class SelectlistDataBean {

    /**
     * appType	APP对应的子页面链接类型 1:定期宝2:散标	string	APP对应的子页面链接类型 1:定期宝2:散标
     * cateId	分类id	string	分类id
     * cateName	分类名称	string	分类名称
     * description	分类描述	string	分类描述
     * identifier	编号	string	编号
     */

    public String appType;
    public String cateId;
    public String cateName;
    public String description;
    public String identifier;
    public List<SelectlistDataBeanInfo> datas;

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<SelectlistDataBeanInfo> getDatas() {
        return datas;
    }

    public void setDatas(List<SelectlistDataBeanInfo> datas) {
        this.datas = datas;
    }
}
