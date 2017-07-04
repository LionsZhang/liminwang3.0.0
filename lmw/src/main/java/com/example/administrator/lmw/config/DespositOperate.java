package com.example.administrator.lmw.config;

import com.example.administrator.lmw.http.HttpUrl;

/**
 * 存管相关的操作
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/5/18 10:33
 **/
public enum DespositOperate {
    /*****************存管相关操作**********************/
    PHONE(HttpUrl.UPDATE_RESERVED_MOBILE),//修改预留手机
    TRANSACTION_PW(HttpUrl.UPDATE_TRANSACTION_PASSWORD),//修改交易密码
    BIND_BANKCARD(HttpUrl.BIND_BANKCARD),//绑定银行卡
    UNBIND_BANKCARD(HttpUrl.UNBIND_BANKCARD),//解绑银行卡

    /*****************存管相关操作结果**********************/
    RESULT_RECHARGE(HttpUrl.PAY_RECHARGE_RESULT),//	充值	获取充值结果	pay.recharge.result
    RESULT_WITHDRAW(HttpUrl.PAY_WITHDRAW_RESULT),//	提现	获取提现结果	pay.withdraw.result
    RESULT_PERSONAL_REGISTER_EXPAND(HttpUrl.RESULT_OPEN_DEPOSITORY_ACCOUNT),//	开通存管账户	获取开通存管账户结果	user.open.depository.account.result
    RESULT_ACTIVATE_STOCKED_USER(HttpUrl.RESULT_ACTIVATE_DEPOSITORY_ACCOUNT),//	激活存管账户	获取激活存管账户结果	user.activate.depository.account.result
    RESULT_PERSONAL_BIND_BANKCARD_EXPAND(HttpUrl.RESULT_BIND_BANKCARD),//	绑定银行卡	获取绑定银行卡结果	user.bind.bankcard.result
    RESULT_UNBIND_BANKCARD(HttpUrl.RESULT_UNBIND_BANKCARD),//解绑银行卡 获取解绑银行卡结果 user.unbind.bankcard.result
    RESULT_MODIFY_MOBILE_EXPAND(HttpUrl.RESULT_UPDATE_RESERVED_MOBILE),// 修改预留手机 获取修改预留手机结果 user.update.reserved.mobile.result
    RESUET_PASSWORD(HttpUrl.RESULT_UPDATE_TRANSACTION_PASSWORD),// 重置交易密码  获取重置交易密码结果 user.update.transaction.password.result
    RESULT_USER_PRE_TRANSACTION(HttpUrl.RESULT_PURCHASE_XW), //购买标的 获取投标支付结果 borrow.purchase.xw.result
    RESULT_USET_DEBT_TRANSACTION(HttpUrl.XW_QUERY_BUY_DEBT_RESULT),//购买债权 获取投标支付结果 xw.query.buy.debt.result
    RESULT_BORROW_DEBT_ADD("xw.borrow.debt.add"),//转让债权_存管  xw.borrow.debt.add

    ;


    private String url;

    private DespositOperate(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

}