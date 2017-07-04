package com.example.administrator.lmw.mine.card.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class RedBean implements Serializable {

    /**
     * id : 332
     * title : 红包7
     * amount : 77
     * useStatus : 1
     * acquireSource : 666
     * createTime : 2016-10-10 00:16:37
     * useTime :
     */

    private String id;
    private String title;
    private double amount;
    private int useStatus;
    private String acquireSource;
    private String createTime;
    private String useTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(int useStatus) {
        this.useStatus = useStatus;
    }

    public String getAcquireSource() {
        return acquireSource;
    }

    public void setAcquireSource(String acquireSource) {
        this.acquireSource = acquireSource;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }
}
