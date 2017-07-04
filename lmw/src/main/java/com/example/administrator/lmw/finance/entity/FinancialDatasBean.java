package com.example.administrator.lmw.finance.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class FinancialDatasBean {

    private String cate_id;//标的类型
    private String cate_name;
    private String identifier;
    private String description;
    private int appType;//标子列表页展示类型
    private String isSearch;
    private List<ProductItemDataBean> datas;

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public List<ProductItemDataBean> getDatas() {
        return datas;
    }

    public void setDatas(List<ProductItemDataBean> datas) {
        this.datas = datas;
    }

    public String getIsSearch() {
        return isSearch;
    }

    public void setIsSearch(String isSearch) {
        this.isSearch = isSearch;
    }
}
