package com.example.administrator.lmw.mine.invite.http;

import android.content.Context;

import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.http.CovertHttpSubscriber;
import com.example.administrator.lmw.http.HttpUrl;
import com.example.administrator.lmw.http.LmwHttp;
import com.example.administrator.lmw.http.RxManager;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.invite.entity.FriendListEntity;
import com.example.administrator.lmw.mine.invite.entity.FriendMoneyEntity;
import com.example.administrator.lmw.mine.invite.entity.ShareBean;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.SharedPreference;

import java.util.Map;

/**
 * 邀请好友http访问类
 * <p/>
 * Created by Administrator on 2016/9/14 0014.
 */
public class Friendshttp {

    /**
     * 获取好友列表
     */
    public void getFriend(Context context, int level, int pageIndex, int pageSize, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.RECOMMEND_FRIEND_PAGE_LIST);
        para.put("level", level + "");
        para.put("pageIndex", pageIndex + "");
        para.put("pageSize", pageSize + "");
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<FriendListEntity>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<FriendListEntity> result) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(result);
                }
            }
        });

    }

    /**
     * 收益汇总
     */
    public void getFriendMoney(Context context, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.RECOMMEND_INCOME_AGGREGATE);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<FriendMoneyEntity>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<FriendMoneyEntity> friendListEntity) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(friendListEntity);
                    ALLog.e(friendListEntity.getCode() + "");
                    ALLog.e(friendListEntity.getMsg());
                }
            }
        });

    }

    /**
     * 获取分享内容
     */
    public void getShareContent(Context context, String shareType, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.getShareContent);
        para.put("shareType", shareType);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<ShareBean>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<ShareBean> friendListEntity) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(friendListEntity);
                }
            }
        });

    }
}
