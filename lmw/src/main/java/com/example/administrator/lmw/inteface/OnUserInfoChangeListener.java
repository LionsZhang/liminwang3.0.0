package com.example.administrator.lmw.inteface;

import com.example.administrator.lmw.login.entity.UserInfo;

/**
 * Created by lion on 2016/8/25.
 */
public interface OnUserInfoChangeListener {


    /**
     * 当用户信息发生改变时候执行该方法
     *
     * @param userInfo 用户信息
     */
    void onUserInfoChange(UserInfo userInfo);


}
