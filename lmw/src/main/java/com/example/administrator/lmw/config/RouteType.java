package com.example.administrator.lmw.config;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 存管操作的路由类型
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/6/23 11:40
 **/
@StringDef
@Retention(RetentionPolicy.SOURCE)
public @interface RouteType {
    String recharge = "RECHARGE";//充值
    String withdraw = "WITHDRAW";//提现
    String personal_register_expand = "PERSONAL_REGISTER_EXPAND";//开通存管账号
    String activate_stocked_user = "ACTIVATE_STOCKED_USER";//激活存管账号
    String personal_bind_bankcard_expand = "PERSONAL_BIND_BANKCARD_EXPAND";//绑定银行卡
    String unbind_bankcard = "UNBIND_BANKCARD";//解绑银行卡
    String modify_mobile_expand = "MODIFY_MOBILE_EXPAND";//修改预留手机
    String reset_password = "RESET_PASSWORD";//重置交易密码
    String user_pre_transaction = "USER_PRE_TRANSACTION";//购买标的
}
