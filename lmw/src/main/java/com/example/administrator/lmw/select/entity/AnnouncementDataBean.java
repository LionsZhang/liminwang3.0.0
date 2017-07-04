package com.example.administrator.lmw.select.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class AnnouncementDataBean implements Serializable {

    private List<AnnouncementDatasBean> datas;
    /**
     * pageIndex : 1
     * pageSize : 10
     * pageCount : 2
     * totalCount : 12
     */

    private int pageIndex;
    private int pageSize;
    private int pageCount;
    private int totalCount;

    public List<AnnouncementDatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<AnnouncementDatasBean> datas) {
        this.datas = datas;
    }

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
}
