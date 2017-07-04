package com.example.administrator.lmw.entity;

import com.example.administrator.lmw.select.entity.NoticeEntity;

/**
 * Created by Administrator on 2017/4/13.
 */

public class FreshMessageEvent extends BaseEvent {

    private NoticeEntity noticeEntity;

    public NoticeEntity getNoticeEntity() {
        return noticeEntity;
    }

    public void setNoticeEntity(NoticeEntity noticeEntity) {
        this.noticeEntity = noticeEntity;
    }
}
