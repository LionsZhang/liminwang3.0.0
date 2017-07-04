package com.example.administrator.lmw.mine.transfer.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class TransferCountEntity {


    /**
     * data : [{"emptyIdentifier":1,"repayCount":80700,"repayAmt":18121,"isDebtRepay":36847}]
     * msg : 测试内容p4iv
     * code : 68256
     */

    private List<TransferCountDataBean> datas;

    public List<TransferCountDataBean> getDatas() {
        return datas;
    }

    public void setDatas(List<TransferCountDataBean> datas) {
        this.datas = datas;
    }
}
