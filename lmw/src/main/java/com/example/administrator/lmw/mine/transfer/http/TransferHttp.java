package com.example.administrator.lmw.mine.transfer.http;

import android.content.Context;

import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.http.CovertHttpSubscriber;
import com.example.administrator.lmw.http.HttpApiInterface;
import com.example.administrator.lmw.http.HttpSubscriber;
import com.example.administrator.lmw.http.HttpUrl;
import com.example.administrator.lmw.http.LmwHttp;
import com.example.administrator.lmw.http.RxManager;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.transfer.entity.TransferCountEntity;
import com.example.administrator.lmw.mine.transfer.entity.TransferEntity;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.SharedPreference;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/10 0010.
 */
public class TransferHttp {
    /**
     * 预回款列表统计
     *
     * @param context
     * @param token
     * @param onResponseListener
     */
    public void getRepayment(Context context, String token, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.APP_USER_REPAYMENT_COUNT);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toGetSubscrib(para, new CovertHttpSubscriber<BaseResult<TransferCountEntity>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<TransferCountEntity> transferCountEntity) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(transferCountEntity);
                    ALLog.e(transferCountEntity.getCode() + "");
                    ALLog.e(transferCountEntity.getMsg());
                }
            }
        });

    }

    /**
     * 预回款列表
     *
     * @param context
     * @param isDebt             0：是债权 1：不是债权
     * @param token
     * @param onResponseListener
     */
    public void getTransfer(Context context, int pageIndex, int pageSize, String isDebt, String token, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.APP_USER_REPAYMENT_LIST);
        para.put("pageIndex", pageIndex + "");
        para.put("pageSize", pageSize + "");
        para.put("isDebt", isDebt);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toGetSubscrib(para, new CovertHttpSubscriber<BaseResult<TransferEntity>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<TransferEntity> transferCountEntity) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(transferCountEntity);
                    ALLog.e(transferCountEntity.getCode() + "");
                    ALLog.e(transferCountEntity.getMsg());
                }
            }
        });

    }
}
