package com.example.administrator.lmw.inteface;

import android.view.View;

/**
 * @Description:TODO 控件点击监听
 * @create by lion
 * @created at 2016/1/28
 */
public interface ViewOnClick {
    /**
     * 点击事件回调监听
     *
     * @param msgID     事件id
     * @param v         控件
     * @param obj       回调的数据
     */
    void onClick(int msgID, View v, Object... obj);

}
