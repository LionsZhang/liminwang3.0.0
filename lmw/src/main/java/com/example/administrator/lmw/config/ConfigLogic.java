package com.example.administrator.lmw.config;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.lmw.entity.AppEntity;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.entity.CommonEntity;
import com.example.administrator.lmw.entity.DataDragFloatBean;
import com.example.administrator.lmw.entity.DepositoryEntity;
import com.example.administrator.lmw.http.CovertHttpSubscriber;
import com.example.administrator.lmw.http.HttpApiInterface;
import com.example.administrator.lmw.http.HttpSubscriber;
import com.example.administrator.lmw.http.HttpUrl;
import com.example.administrator.lmw.http.LmwHttp;
import com.example.administrator.lmw.http.RxManager;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.ToastUtil;

import java.util.Map;

/**
 * Created by lion on 2016/9/18.
 */
public class ConfigLogic {
    public void getCommonInfo(Context context, final OnResponseListener<CommonEntity> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.COMMON_CONFIG);
//        para.put("source", "android");
        RxManager.toSubscrib(httpApiInterface.getCommonInfo(para), new HttpSubscriber<CommonEntity>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(CommonEntity baseResponse) {
                onResponseListener.onSuccess(baseResponse);
            }
        });

    }

    public void getVersionInfo(Context context, final OnResponseListener<AppEntity> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.GET_APP_VERSION_INFO);
        para.put("platformModel", "0");//android
        RxManager.toSubscrib(httpApiInterface.getVersionInfo(para), new HttpSubscriber<AppEntity>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(AppEntity baseResponse) {
                onResponseListener.onSuccess(baseResponse);
            }
        });

    }


    /**
     * 获取浮动图标
     *
     * @param context
     * @param onResponseListener
     */
    public void getDragData(final Context context, final OnResponseListener<DataDragFloatBean> onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.FLOATING_LCON);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toGetSubscrib(para, new CovertHttpSubscriber<BaseResult<DataDragFloatBean>>(context, "snubee") {

            @Override
            public void onSuccess(BaseResult<DataDragFloatBean> baseResponse) {
                if (baseResponse != null && baseResponse.getData() != null && onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse.getData());
                }
            }
        });

    }

    /**
     * 检验安装包md5
     *
     * @param context
     * @param onResponseListener
     */
    public void checkApkFile(final Context context, String md5, final OnResponseListener<BaseResult<String>> onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.COMMON_MD5_VALIDATE);
        para.put("md5", md5);
        RxManager.toGetSubscrib(para, new CovertHttpSubscriber<BaseResult<String>>(context) {

            @Override
            public void onSuccess(BaseResult<String> baseResponse) {
                if (baseResponse != null && onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse);
                }
            }
        });

    }


}
