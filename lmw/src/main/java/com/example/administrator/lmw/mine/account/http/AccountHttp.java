package com.example.administrator.lmw.mine.account.http;

import android.content.Context;

import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.http.CovertHttpSubscriber;
import com.example.administrator.lmw.http.HttpUrl;
import com.example.administrator.lmw.http.LmwHttp;
import com.example.administrator.lmw.http.RxManager;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.account.entity.AccountEntity;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.SharedPreference;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class AccountHttp {

    /**
     * 账户明细
     *
     * @param context
     * @param appFundsType
     * @param pageIndex
     * @param pageSize
     * @param onResponseListener
     */
    public void getAccount(Context context, int appFundsType, int pageIndex, int pageSize, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.APP_FUNDS_PAGE_LIST);
        para.put("appFundsType", appFundsType + "");
        para.put("pageIndex", pageIndex + "");
        para.put("pageSize", pageSize + "");
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<AccountEntity>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<AccountEntity> accountEntity) {
                onResponseListener.onSuccess(accountEntity);
                ALLog.e(accountEntity.getCode() + "");
                ALLog.e(accountEntity.getMsg());
            }
        });

    }
}
