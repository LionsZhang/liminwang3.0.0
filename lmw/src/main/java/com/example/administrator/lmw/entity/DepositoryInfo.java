package com.example.administrator.lmw.entity;

import java.io.Serializable;

/**
 * Created by lion on 2017/5/11.
 */

public class DepositoryInfo implements Serializable {

    /**
     * keySerial	证书序号	string	@mock=测试内容ck8c
     * platformNo	平台编号	string	@mock=测试内容i713
     * reqData	业务数据报文	string	@mock=测试内容6630
     * serviceName	接口名称	string	@mock=测试内容84a5
     * sign	对 reqData 参数的签名	string	@mock=测试内容4gl0
     * url	网关URL	string	@mock=测试内容6h90
     * userDeivce	用户终端设备类型	string	@mock=测试内容p1rf
     */

    private String keySerial;
    private String platformNo;
    private String reqData;
    private String serviceName;
    private String sign;
    private String url;
    private String userDevice;

    public String getKeySerial() {
        return keySerial;
    }

    public void setKeySerial(String keySerial) {
        this.keySerial = keySerial;
    }

    public String getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(String platformNo) {
        this.platformNo = platformNo;
    }

    public String getReqData() {
        return reqData;
    }

    public void setReqData(String reqData) {
        this.reqData = reqData;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserDevice() {
        return userDevice;
    }

    public void setUserDevice(String userDevice) {
        this.userDevice = userDevice;
    }
}
