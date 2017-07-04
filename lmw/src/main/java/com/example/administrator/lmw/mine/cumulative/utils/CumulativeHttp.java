package com.example.administrator.lmw.mine.cumulative.utils;

import android.content.Context;

import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.http.CovertHttpSubscriber;
import com.example.administrator.lmw.http.HttpUrl;
import com.example.administrator.lmw.http.LmwHttp;
import com.example.administrator.lmw.http.RxManager;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.cumulative.entity.CumulativeEntity;
import com.example.administrator.lmw.mine.cumulative.entity.TotalAssetsEntity;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.SharedPreference;

import java.util.Map;

/**
 * 全部收益模块 网络访问
 * <p/>
 * Created by Administrator on 2016/9/1 0001.
 */
public class CumulativeHttp {

    /**
     * 累计收益
     *
     * @param context
     * @param incomeType
     * @param pageIndex
     * @param onResponseListener
     */
    public void getCumulative(Context context, int incomeType, int pageIndex, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.USER_ACCUMULATED_INCOME_LIST);
        para.put("incomeType", incomeType + "");
        para.put("pageIndex", pageIndex + "");
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<CumulativeEntity>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<CumulativeEntity> cumulativeEntity) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(cumulativeEntity);
                    ALLog.e(cumulativeEntity.getCode() + "");
                    ALLog.e(cumulativeEntity.getMsg());
                }
            }
        });
    }

    /**
     * 总资产
     *
     * @param context
     * @param onResponseListener
     */
    public void getTotalAssets(Context context, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.USER_INVEST_AGGREGATE);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<TotalAssetsEntity>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<TotalAssetsEntity> totalAssetsEntity) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(totalAssetsEntity);
                    ALLog.e(totalAssetsEntity.getCode() + "");
                    ALLog.e(totalAssetsEntity.getMsg());
                }
            }
        });
    }

}
