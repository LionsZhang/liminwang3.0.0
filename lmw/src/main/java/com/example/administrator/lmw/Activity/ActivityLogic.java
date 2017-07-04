package com.example.administrator.lmw.Activity;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.example.administrator.lmw.Activity.entity.ActivityBean;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.http.CovertHttpSubscriber;
import com.example.administrator.lmw.http.HttpUrl;
import com.example.administrator.lmw.http.LmwHttp;
import com.example.administrator.lmw.http.RxManager;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.ShareUtils;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.view.RedPacketShareDialog;

import java.util.Map;

/**
 * 活动业务逻辑
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/4/21 11:08
 **/
public class ActivityLogic {
    /**
     * 获取活动弹窗
     *
     * @param context
     * @param activityType       活动标记
     * @param relationId         关联id 投资记录id或者用户id
     * @param onResponseListener
     */
    public void getDataActivity(final Context context, ActivityType activityType, String relationId, final OnResponseListener<BaseResult<ActivityBean>> onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.ACTIVITY_PROMPT);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        if (!TextUtils.isEmpty(relationId))
            para.put("investId", relationId);
        para.put("type", activityType.getActivityType());
        RxManager.toGetActivitySubscrib(para, new CovertHttpSubscriber<BaseResult<ActivityBean>>(context) {

            @Override
            public void onSuccess(BaseResult<ActivityBean> baseResponse) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse);
                }
            }
        });

    }

    /**
     * 显示活动信息
     *
     * @param context
     * @param activityType
     * @param relationId
     */
    public void showActivityInfo(final Context context, ActivityType activityType, String relationId) {
        getDataActivity(context, activityType, relationId, new OnResponseListener<BaseResult<ActivityBean>>() {
            @Override
            public void onSuccess(BaseResult<ActivityBean> result) {
                if (result != null && result.getData() != null && result.getData().isShow()) {
                    //显示活动弹窗
                    showDialog(context, result.getData());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 显示活动弹窗
     *
     * @param activityBean
     */
    private void showDialog(final Context context, final ActivityBean activityBean) {
        if (activityBean.getActivityTpye() != 2)
            //活动弹窗
            Singlton.getInstance(PopWindowManager.class).showActivityDialog(context, activityBean.getActivityTitle(), activityBean.getContent(), activityBean.getActivityAppUrl(), false);
        else
            //红包弹窗
            new RedPacketShareDialog(context, activityBean.getContent(), new OnDialogClickListener() {
                @Override
                public void onClick(int id, View v) {
                    if (id == RedPacketShareDialog.RIGHT_ONCLICK) {
                        //TODO 弹起分享弹窗
                        Singlton.getInstance(ShareUtils.class).getShareUtils((Activity) context, activityBean.getActivitySharePicUrl(), activityBean.getShareContent(),
                                activityBean.getActivityTitle(), activityBean.getActivityAppUrl());
                    }
                }
            }, false);
    }

}
