package com.example.administrator.lmw.mine.card.entity;

import java.util.List;

/**
 *  红包实体
 *  @author snubee
 *  @email snubee96@gmail.com
 *  @time 2017/3/24 14:06
 *
**/
public class DataRedBean {
    private int pageIndex;
    private int pageSize;
    private int pageCount;
    private int totalCount;
    private List<RedBean> datas;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<RedBean> getDatas() {
        return datas;
    }

    public void setDatas(List<RedBean> datas) {
        this.datas = datas;
    }
}
