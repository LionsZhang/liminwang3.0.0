package com.example.administrator.lmw.select.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class AnnouncementEntity implements Serializable {

    /**
     * code : 18543
     * msg : 测试内容76yr
     * data : {"datas":[{"linkUrl":"测试内容nt86","createTime":"测试内容w7xd","noticeId":"测试内容p82d","postTitle":"测试内容qd3e"}]}
     */

    private String code;
    private String msg;
    private AnnouncementDataBean data;

    public AnnouncementEntity() {
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

    public AnnouncementDataBean getData() {
        return data;
    }

    public void setData(AnnouncementDataBean data) {
        this.data = data;
    }
}
