package com.example.administrator.lmw.mine.invite.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class FriendListEntity {

    private int totalCount;
    private int pageSize;
    private int pageIndex;
    private int pageCount;
    /**
     * createTime : 测试内容c3n6
     * name : 测试内容6jk5
     */

    private List<FriendListDatasBean> datas;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<FriendListDatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<FriendListDatasBean> datas) {
        this.datas = datas;
    }

}
