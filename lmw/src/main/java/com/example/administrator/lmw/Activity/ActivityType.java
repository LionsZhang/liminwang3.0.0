package com.example.administrator.lmw.Activity;

/**
 * 活动标记枚举
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/4/21 10:48
 **/
public enum ActivityType {
    BING_CARD("1"),//绑卡
    INVEST("2"),//投资
    AUTHED("3");//实名认证

    private String activityType;

    private ActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityType() {
        return this.activityType;
    }
}
