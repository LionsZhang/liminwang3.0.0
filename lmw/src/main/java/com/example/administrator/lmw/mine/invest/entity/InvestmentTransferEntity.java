package com.example.administrator.lmw.mine.invest.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class InvestmentTransferEntity {

    /**
     * stillPrincipal : 测试内容9c51
     * borrowTitle : 测试内容hu73
     * culInterestStartTime : 测试内容7128
     * stillInterest : 测试内容48p5
     * investTime : 测试内容8kno
     * culInterestEndTime : 测试内容i61v
     * deadLineType : 测试内容57c6
     * annualRate : 测试内容d43y
     * deadLineValue : 测试内容djat
     */

    private int pageCount;
    private int pageSize;
    private int pageIndex;

    private List<InvestmentTransferDatasBean> datas;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
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

    public List<InvestmentTransferDatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<InvestmentTransferDatasBean> datas) {
        this.datas = datas;
    }

}
