package com.example.administrator.lmw.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.login.LoginActivity;

import java.util.HashMap;
import java.util.Map;

import cn.udesk.UdeskConst;
import cn.udesk.UdeskSDKManager;

/**
 * 用于切换Activity的工具类
 *
 * @Autour lionzhang by 2016.8.16
 */
public class ActivityTools {

    public static class Screen {
        public int widthPixels;
        public int heightPixels;
    }

    private static Screen screen;

    public static Screen getCurrentScreen(Activity act) {
        Display display = act.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        if (screen == null) {
            screen = new Screen();
        }
        screen.widthPixels = dm.widthPixels;
        screen.heightPixels = dm.heightPixels;
        return screen;
    }


    public static void switchActivity(Context context, Class<?> descClass,
                                      Bundle bundle) {
        Class<?> mClass = context.getClass();
        if (mClass == descClass) {
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setClass(context, descClass);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            ((Activity) context).startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void switchActivity(Context context, Class<?> descClass,
                                      Bundle bundle, int result) {
        Class<?> mClass = context.getClass();
        if (mClass == descClass) {
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setClass(context, descClass);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            ((Activity) context).startActivityForResult(intent, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 跳转到登录页面
     *
     * @param context
     */
    public static void startToLogin(final Context context, String... msg) {
        String msgTxt = "";
        if (msg != null && msg.length > 0) {
            msgTxt = msg[0];
        }
        ToastUtil.showToast(context, msgTxt);
        ThreadManager.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
        }, 1000);
    }

    /**
     * 跳到在线客服页面
     * @param context
     */
    public static void startUdeskGuest(Context context) {
        String appUdeskTokenGroop = SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_UDESK_TOKEN_GROOP, "");
        Map<String, String> info = new HashMap<String, String>();
        info.put(UdeskConst.UdeskUserInfo.USER_SDK_TOKEN, appUdeskTokenGroop);
        UdeskSDKManager.getInstance().setUserInfo(context, appUdeskTokenGroop, info);
        String groupId = PreferenceCongfig.APP_UDESK_GROOP_ID;
        UdeskSDKManager.getInstance().lanuchChatByGroupId(context, groupId);
    }

}
