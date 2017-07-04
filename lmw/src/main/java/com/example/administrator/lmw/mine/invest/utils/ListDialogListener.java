package com.example.administrator.lmw.mine.invest.utils;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */

public interface ListDialogListener {

    /**
     *
     * @param id        自定义的id
     * @param value     返回的显示拼接字段
     * @param key       返回的对应的key值拼接字段
     * @param falg      返回的选择对应的布尔集合
     */
    void onClick(int id, String value, String key, List<Boolean> falg);
}
