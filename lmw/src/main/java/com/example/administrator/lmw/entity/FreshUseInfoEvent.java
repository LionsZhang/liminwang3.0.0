package com.example.administrator.lmw.entity;

/**
 * Created by Administrator on 2017/4/13.
 */

public class FreshUseInfoEvent extends BaseEvent {
    public static String IS_GESTUREPSW_LOGIN = "isGesturePswLogin";
    public static String IS_FRESH_USER_INFO = "isFreshUserInfo";
    private String freshType;

    public FreshUseInfoEvent(String freshType) {
        this.freshType = freshType;
    }

    public String getFreshType() {
        return freshType;
    }

    public void setFreshType(String freshType) {
        this.freshType = freshType;
    }


}
