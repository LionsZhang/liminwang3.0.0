package com.example.administrator.lmw.entity;

import java.io.Serializable;

/**
 * 简单的响应
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2016/12/8 14:22
 **/
public class SimpleResult implements Serializable {

    private static final long serialVersionUID = -1477609349345966116L;

    public String code;
    public String msg;

    public BaseResult toBaseResult() {
        BaseResult baseResponse = new BaseResult();
        baseResponse.setCode(code);
        baseResponse.setMsg(msg);
        return baseResponse;
    }
}