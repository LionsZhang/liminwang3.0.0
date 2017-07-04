package com.example.administrator.lmw.inteface;

import com.example.administrator.lmw.login.entity.MineData;

/**
 * Created by lion on 2016/8/25.
 */
public interface OnMineDataChangeListener {


    /**
     * 当用户信息发生改变时候执行该方法
     *
     * @param mineData 我的頁面顯示數據
     */
    void onMineDataChange(MineData mineData);


}
