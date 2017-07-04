package com.example.administrator.lmw.finance.entity;

import java.util.List;

/**
 * 理财子列表数据
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/4/25 10:53
 **/
public class DataProductItem {


    private int pageIndex;
    private int pageSize;
    private int pageCount;
    private int totalCount;
    /**
     * borrowTitle : 1
     * borrowId : 1
     * labels :
     * annualRate : 1.00
     * increaseRate : 1.00
     * deadLineType : 1
     * deadLineValue : 1
     * status : 3
     * collectBeginTime :
     * collectEndTime : 2016-08-09 10:58:09
     * cateId : 1001
     * description : 稳赚收益 还可以转让变活期
     * isCanTransfer : 1
     * remaMoney : 1.00
     * hasInvestMoney : 1.00
     * buyTotalAmount : 10000000.00
     * labelStrArr : [""]
     */

    private List<ProductItemDataBean> datas;

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

    public List<ProductItemDataBean> getDatas() {
        return datas;
    }

    public void setDatas(List<ProductItemDataBean> datas) {
        this.datas = datas;
    }
}
