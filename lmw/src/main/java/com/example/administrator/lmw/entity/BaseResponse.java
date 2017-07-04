package com.example.administrator.lmw.entity;

import java.io.Serializable;

/**
 * 事件分发的基类
 *
 * @author lion
 * @Description:TODO
 * @Date 2016-8-19
 */
public class BaseResponse implements Serializable {
    public String code;
    public String msg;

    public BaseResponse() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
