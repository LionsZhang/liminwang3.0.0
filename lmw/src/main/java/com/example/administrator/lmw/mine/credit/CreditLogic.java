package com.example.administrator.lmw.mine.credit;

import android.content.Context;

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
import com.example.administrator.lmw.mine.credit.Entity.CreditInfo;
import com.example.administrator.lmw.mine.credit.Entity.CreditResult;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ToastUtil;

import java.util.Map;

/**
 * Created by lion on 2016/9/9.
 */
public class CreditLogic {

/**
     * 获取提现信息
     */
    public void getCreditInfo(final Context context,
                                  final OnResponseListener<CreditInfo> onResponseListener) {

        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.CREDIT_INFO);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toSubscrib(httpApiInterface.getCreditInfo(para), new HttpSubscriber<CreditInfo>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(CreditInfo baseResponse) {
                if (baseResponse != null && !Singlton.getInstance(PopWindowManager.class).
                        showStopDialogShow(context,baseResponse.getCode(),baseResponse.msg) && onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse);
                }
            }
        });

    }


    /**
     * 提现
     */
    public void credit(final Context context,String creidtNumber,String tradePsw,String creditRate,
                                  final OnResponseListener<CreditResult> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.CREDIT);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        para.put("amount", creidtNumber);//提现额
        para.put("poundage", creditRate);//费率
        para.put("tradingPassword", tradePsw);//交易密码
        RxManager.toSubscrib(httpApiInterface.credit(para), new HttpSubscriber<CreditResult>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(CreditResult baseResponse) {
                if (baseResponse != null && !Singlton.getInstance(PopWindowManager.class).
                        showStopDialogShow(context,baseResponse.getCode(),baseResponse.msg) && onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse);
                }
            }
        });

    }
    /**
     * 存管提现
     *
     * @param context
     * @param onResponseListener
     */
    public void doCredit(final Context context, final String criditRedirectUrl,String poundage,String amount,final OnResponseListener<DepositoryEntity> onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.DEPOSITORY_PAY_CREDIT);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        para.put("withdrawRedirectUrl", "www.baidu.com");
        para.put("poundage", poundage);
        para.put("amount", amount);
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<DepositoryEntity>>(context, "depository_credit") {

            @Override
            public void onSuccess(BaseResult<DepositoryEntity> baseResponse) {
                ALLog.e("===============存管baseResponse.getCode()" + baseResponse.getCode());
                ALLog.e("===============存管baseResponse.getmsg()" + baseResponse.getMsg());
                if (baseResponse != null && baseResponse.getData() != null && onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse.getData());

                } else
                    ToastUtil.showToast(context, baseResponse.getMsg());
            }
        });


    }



}
