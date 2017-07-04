package com.example.administrator.lmw.finance.entity;

/**
 * 理财产品的分类的实体
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/4/25 10:34
 **/
public class DataFinancialCategoryBean {

    /**
     * count : 2
     * isSellOut : false
     * cateId : 1004
     * cateName : 车贷宝
     * isSearch	是否显示搜索框	string	0不显示，1显示
     */

    private int count; // 类型下面可投标数
    private boolean isSellOut; // 是否售罄
    private String cateId; // 类型编号
    private String cateName;// 子栏目名称
    private String isSearch;
    private String imgUrl;//图标icon

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSellOut() {
        return isSellOut;
    }

    public void setSellOut(boolean sellOut) {
        isSellOut = sellOut;
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

    public String getIsSearch() {
        return isSearch;
    }

    public void setIsSearch(String isSearch) {
        this.isSearch = isSearch;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
