package com.example.administrator.lmw.entity;

/**
 * Created by Administrator on 2017/4/13.
 */

public class FreshFloatIconEvent extends BaseEvent {

    private int index;
    private String freshEventType;
    public static String FRESH_ICON = "fresh_icon";
    public static String FRESH_POP = "fresh_pop";
    public static String FRESH_ICON_AND_POP = "fresh_icon_and_pop";
    public static String FRESH_NOT = "fresh_not";
    public static String FRESH_USER_INFO = "fresh_user_info";


    public FreshFloatIconEvent(String freshEventType) {
        this.freshEventType = freshEventType;
    }

    public String getFreshEventType() {
        return freshEventType;
    }

    public void setFreshEventType(String freshEventType) {
        this.freshEventType = freshEventType;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
