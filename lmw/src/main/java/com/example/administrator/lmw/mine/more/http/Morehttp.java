package com.example.administrator.lmw.mine.more.http;

import android.content.Context;

import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResponse;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.http.CovertHttpSubscriber;
import com.example.administrator.lmw.http.HttpApiInterface;
import com.example.administrator.lmw.http.HttpSubscriber;
import com.example.administrator.lmw.http.HttpUrl;
import com.example.administrator.lmw.http.LmwHttp;
import com.example.administrator.lmw.http.RxManager;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.more.entity.MoreEntity;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.SharedPreference;

import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public class Morehttp {

    /**
     * 更多页面
     */
    public void getMore(Context context, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.COMMON_CONFIG);
//        para.put("source", "android");
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<MoreEntity>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<MoreEntity> moreEntity) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(moreEntity);
                    ALLog.e(moreEntity.getCode());
                    ALLog.e(moreEntity.getMsg());
                }
            }
        });


    }

    /**
     * 意见反馈
     */
    public void getSuggestion(Context context, String adviseContent, String adviseTitle, String adviseType, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.USER_QUESTION_SUGGESTION);
        para.put("adviseContent", adviseContent);
        para.put("adviseTitle", adviseTitle);
        para.put("adviseType", adviseType);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<Objects>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<Objects> baseResponse) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse);
                    ALLog.e(baseResponse.getCode() + "");
                    ALLog.e(baseResponse.getMsg());
                }
            }
        });


    }
}
