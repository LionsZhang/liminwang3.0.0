package com.example.administrator.lmw.mine.fill;

import android.content.Context;

import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.entity.DepositoryEntity;
import com.example.administrator.lmw.http.CovertHttpSubscriber;
import com.example.administrator.lmw.http.HttpApiInterface;
import com.example.administrator.lmw.http.HttpSubscriber;
import com.example.administrator.lmw.http.HttpUrl;
import com.example.administrator.lmw.http.LmwHttp;
import com.example.administrator.lmw.http.RxManager;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.fill.entity.FillEntity;
import com.example.administrator.lmw.mine.fill.entity.OderEntity;
import com.example.administrator.lmw.mine.fill.entity.UserBankEntity;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ToastUtil;

import java.util.Map;

/**
 * Created by lion on 2016/9/9.
 */
public class FillLogic {

    /**
     * 充值获取绑卡信息
     */
    public void getBindBankCardInfo(final Context context,
                                    final OnResponseListener<UserBankEntity> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.FILL_GET_BIND_BANK_CARD_INFO);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toSubscrib(httpApiInterface.getUserBankCardInfo(para), new HttpSubscriber<UserBankEntity>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(UserBankEntity baseResponse) {
                if (baseResponse != null && !Singlton.getInstance(PopWindowManager.class).
                        showStopDialogShow(context, baseResponse.getCode(), baseResponse.msg) && onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse);
                }
            }
        });

    }

    /**
     * 充值获取订单
     */
    public void fillGetOder(final Context context, String fillNumber,
                            final OnResponseListener<OderEntity> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.FILL_GET_ODER);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        para.put("amount", fillNumber);
        RxManager.toSubscrib(httpApiInterface.fillGetOder(para), new HttpSubscriber<OderEntity>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(OderEntity baseResponse) {
                if (baseResponse != null && !Singlton.getInstance(PopWindowManager.class).
                        showStopDialogShow(context, baseResponse.getCode(), baseResponse.msg) && onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse);
                }
            }
        });

    }

    /**
     * 充值支付
     */
    public void fillPay(final Context context, String orderNumber, String tradePsw,
                        String checkCode, final OnResponseListener<FillEntity> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.FILL_PAY);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        para.put("orderNo", orderNumber);
        // para.put("tradingPassword", tradePsw);
        para.put("checkCode", checkCode);
        RxManager.toSubscrib(httpApiInterface.fillPay(para), new HttpSubscriber<FillEntity>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(FillEntity baseResponse) {
                if (baseResponse != null && !Singlton.getInstance(PopWindowManager.class).
                        showStopDialogShow(context, baseResponse.getCode(), baseResponse.msg) && onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse);
                }
            }
        });

    }




}
