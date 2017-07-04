package com.example.administrator.lmw.login.entity;

/**
 * Created by Administrator on 2016/8/29.
 */
public class LoginBean {
    /**
     * "failCount":1,"leftCount":4
     *
     * */
    public String token;
    public int failCount;
    public int leftCount;
    private String cipher;
    private String rspData;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public int getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(int leftCount) {
        this.leftCount = leftCount;
    }

    public String getCipher() {
        return cipher;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    public String getRspData() {
        return rspData;
    }

    public void setRspData(String rspData) {
        this.rspData = rspData;
    }
}
