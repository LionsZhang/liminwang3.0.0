package com.example.administrator.lmw.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.NotificationEntity;
import com.example.administrator.lmw.finance.activity.DetailActivity;
import com.example.administrator.lmw.finance.activity.DetailProductActivity;
import com.example.administrator.lmw.finance.activity.ProductItem;
import com.example.administrator.lmw.login.GesturePswLoginActivity;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.SharedPreference;
import com.google.gson.Gson;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class PushMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        ALLog.e("intent.getAction()" + intent.getAction());
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            SharedPreference.getInstance().saveString(context, PreferenceCongfig.JPUSH_REGESTER_ID, regId);//保存RegisterId
            ALLog.e("接收Registration Id : " + regId);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            ALLog.e("接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            ALLog.e("接收到推送下来的通知");

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            try {
                int type = -1;
                ALLog.e("用户点击打开了通知");
                String jasonString = bundle.getString(JPushInterface.EXTRA_EXTRA);//通知附带参数
                Gson gson = new Gson();
                NotificationEntity notificationEntity = gson.fromJson(jasonString, NotificationEntity.class);
                ALLog.e("id:" + notificationEntity.getId() + "type:" + notificationEntity.getType() + "linkUrl:"
                        + notificationEntity.getLinkUrl() + "title:" + notificationEntity.getTitle() + "cateId" + notificationEntity.getCateId());
                boolean isCurrentActivityForGestureLogin = SharedPreference.getInstance().getBoolean(context,
                        PreferenceCongfig.IS_SHOW_CURRENT_ACTIVITY_FOR_GESTURE_LOGIN, false);
                if (!TextUtils.isEmpty(notificationEntity.getType())) {
                    type = Integer.valueOf(notificationEntity.getType());
                }
//type:// 类型 (0:发布产品 1:活动 2：公告 3:指定跳转url 4：定期宝子列表 页 5:散标子列表页 )
                Intent i = null;
                switch (type) {
                    case 0://发新标（定期宝、散标）跳到详情列表
                        if (isCurrentActivityForGestureLogin) {
                            i = new Intent(context, GesturePswLoginActivity.class);
                            i.putExtra(PreferenceCongfig.NOTICE_CONTENT, notificationEntity);//通知内容
                        } else {
                            i = new Intent(context, DetailProductActivity.class);
                            i.putExtra("subjectId", notificationEntity.getId());// 标的Id
                            i.putExtra("type", "0");//0标1债权
                            ActivityManage.create().finishActivity(DetailProductActivity.class);
                        }
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(i);
                        break;
                    case 1://活动页
                    case 2://公告页
                    case 3://指定页
                        if (isCurrentActivityForGestureLogin) {
                            i = new Intent(context, GesturePswLoginActivity.class);
                            i.putExtra(PreferenceCongfig.NOTICE_CONTENT, notificationEntity);//通知内容
                        } else {
                            i = new Intent(context, WebViewMore.class);
                            i.putExtra("title", notificationEntity.getTitle());
                            i.putExtra("url", notificationEntity.getLinkUrl());
                            ActivityManage.create().finishActivity(WebViewMore.class);
                        }

                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(i);
                        break;
                    case 4://定期宝子列表
                    case 5://散标子列表页
                        if (isCurrentActivityForGestureLogin) {
                            i = new Intent(context, GesturePswLoginActivity.class);
                            i.putExtra(PreferenceCongfig.NOTICE_CONTENT, notificationEntity);//通知内容
                        } else {
                            i = new Intent(context, ProductItem.class);
                            i.putExtra("type", notificationEntity.getCateId());//类型ID
                            ActivityManage.create().finishActivity(ProductItem.class);
                        }
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(i);
                        break;
                    case 6://发新标（债券标）跳到详情列表
                        if (isCurrentActivityForGestureLogin) {
                            i = new Intent(context, GesturePswLoginActivity.class);
                            i.putExtra(PreferenceCongfig.NOTICE_CONTENT, notificationEntity);//通知内容
                        } else {
                            i = new Intent(context, DetailActivity.class);
                            i.putExtra("subjectId", notificationEntity.getId());// 债权的Id
                            i.putExtra("type", "1");//0标1债权
                            ActivityManage.create().finishActivity(DetailActivity.class);
                        }

                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(i);
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            ALLog.e("用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            ALLog.e(intent.getAction() + " connected state change to " + connected);
        } else {
            ALLog.e("Unhandled intent - " + intent.getAction());
        }
    }
}
