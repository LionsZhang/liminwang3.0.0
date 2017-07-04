package com.example.administrator.lmw.mine.transfer.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/10 0010.
 */
public class TransferEntity {

    /**
     * msg : 测试内容5k20
     * data : [{"title":55765,"repayAmt":15463,"repayDate":"测试内容fxm1","periods":"测试内容57vi"}]
     * code : 13273
     */

    private List<TransferDataBean> datas;

    public List<TransferDataBean> getDatas() {
        return datas;
    }

    public void setDatas(List<TransferDataBean> datas) {
        this.datas = datas;
    }

}
