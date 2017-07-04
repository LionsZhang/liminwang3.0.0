package com.example.administrator.lmw.mine.card.entity;

import java.util.List;

/**
 * 卡券列表实体data
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/4/26 16:36
 **/
public class DataCashEntity {

    /**
     * pageIndex : 70050
     * datas : [{"useRegion":"测试内容1jws","endTime":"测试内容4348","beginTime":"测试内容0286","amount":53085,"useStatus":31653,"useCondition":"测试内容ysr7"}]
     * totalCount : 21635
     * pageSize : 28010
     * pageCount : 40632
     */
    private int pageIndex;
    private int totalCount;
    private int pageSize;
    private int pageCount;

    private List<CashDatasBean> datas;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

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

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<CashDatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<CashDatasBean> datas) {
        this.datas = datas;
    }
}
