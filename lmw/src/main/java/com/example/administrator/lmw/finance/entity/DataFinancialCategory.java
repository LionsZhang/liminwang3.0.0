package com.example.administrator.lmw.finance.entity;

import java.util.List;

/**
 * 理财产品的分类
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/4/25 10:34
 **/
public class DataFinancialCategory {


    private List<DataFinancialCategoryBean> datas;

    public List<DataFinancialCategoryBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DataFinancialCategoryBean> datas) {
        this.datas = datas;
    }

}
