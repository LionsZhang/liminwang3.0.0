package com.example.administrator.lmw.finance.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/16.
 */

public class SharedBean implements Serializable {
    private String type;
    private String typeName;
    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
