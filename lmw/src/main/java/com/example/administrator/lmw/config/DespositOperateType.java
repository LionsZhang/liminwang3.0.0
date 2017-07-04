package com.example.administrator.lmw.config;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 存管操作的入口
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/6/26 11:40
 **/
@IntDef
@Retention(RetentionPolicy.SOURCE)
public @interface DespositOperateType {
    int DEFAULT = -1;//默认无操作
    /********************返回我的根目录页面触发  开通存管相关 start***************************/
    int mine_open_withdraw = 0;//我的--提现
    int mine_open_fill = 1;//我的--充值
    int mine_open_reinvestment = 2;//我的-我的投资-续投设置
    int mine_open_card_red = 3;//卡券红包-领取红包
    int mine_open_setting = 4;//账户设置-未开通存管(未实名）（所有需要判断）
    int mine_open_investment = 5;//发起转让
    /********************返回我的根目录页面触发 开通存管相关  end***************************/

    /********************返回我的根目录页面触发  绑卡相关 start***************************/
    int mine_bindcard_withdraw = 6;//我的--提现
    int mine_bindcard_fill = 7;//我的--充值
    int mine_xw_fill = 8;//新网存管账户-充值
    int mine_bindcard_card_red = 9;//我的-账户设置-账户信息_银行卡
    int mine_bindcard_setting = 10;//我的-账户设置-银行卡绑定
    /********************返回我的根目录页面触发 绑卡相关  end***************************/


}
