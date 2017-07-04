package com.example.administrator.lmw.entity;

/**
 *  浮动窗口实体 
 *  @author snubee
 *  @email snubee96@gmail.com
 *  @time 2017/4/14 17:33
 *
**/
public class DataDragFloatBean {
    private int disable;//是否显示（1：显示，2：不是显示）
    private String icon;
    private String link;
    private String name;

    public int getDisable() {
        return disable;
    }

    public void setDisable(int disable) {
        this.disable = disable;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
