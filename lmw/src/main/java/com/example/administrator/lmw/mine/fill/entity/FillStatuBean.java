package com.example.administrator.lmw.mine.fill.entity;

import com.example.administrator.lmw.entity.BaseResponse;

/**
 * Created by lion on 2016/9/9.
 */
public class FillStatuBean extends BaseResponse {


    private String respCode;
    private String respMsg;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
}
