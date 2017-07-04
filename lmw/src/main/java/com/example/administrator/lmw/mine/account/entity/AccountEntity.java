package com.example.administrator.lmw.mine.account.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class AccountEntity {

    private int pageIndex;
    private int pageSize;
    private int pageCount;
    private int totalCount;
    private List<AccountDatasBean> datas;

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

    public List<AccountDatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<AccountDatasBean> datas) {
        this.datas = datas;
    }
}
