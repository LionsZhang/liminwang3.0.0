package com.example.administrator.lmw.finance.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class InvestmentEntity {

    /**
     * msg : success
     * data : {"datas":[{"mobile":"18688380486","realAmount":"30","investTime":"15%"}]}
     * pageCount : 14210
     * pageSize : 65711
     * pageIndex : 62814
     * code : 0
     * totalCount : 31757
     */

    private String msg;
    private DataBean data;
    private int pageCount;
    private int pageSize;
    private int pageIndex;
    private String code;
    private int totalCount;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public static class DataBean {


        private List<InvestmentDatasBean> datas;

        public List<InvestmentDatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<InvestmentDatasBean> datas) {
            this.datas = datas;
        }
    }
}
