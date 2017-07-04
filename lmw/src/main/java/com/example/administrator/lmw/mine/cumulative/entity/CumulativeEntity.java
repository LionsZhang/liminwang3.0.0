package com.example.administrator.lmw.mine.cumulative.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class CumulativeEntity {

    private int pageIndex;
    private int pageSize;
    private int pageCount;
    private int totalCount;
    private List<CumulativeDatasBean> datas;

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

    public List<CumulativeDatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<CumulativeDatasBean> datas) {
        this.datas = datas;
    }
}
