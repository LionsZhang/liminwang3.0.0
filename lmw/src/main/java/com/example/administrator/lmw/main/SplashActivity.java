package com.example.administrator.lmw.main;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.login.GesturePswLoginActivity;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.ThreadPoolManager;
import com.umeng.analytics.MobclickAgent;

import java.util.concurrent.TimeUnit;


/**
 * @author lion
 *         created at 2016/8/19 15:00
 * @desc 应用启动页
 **/
public class SplashActivity extends BaseActivity {
    private final static String TAG = SplashActivity.class.getName();
    private final static int WAIT_DURATION = 500; //进入主程序等待的时间
    private String phone;
    private String psw;
    private String token;
    private boolean isSetGesturePsw;

    @Override
    protected void initializeData() {
        MobclickAgent.openActivityDurationTrack(false);
        phone = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_ACCOUNT, "");//账号
        psw = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_PSW, "");//密码
        token = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, "");//TOKEN
        isSetGesturePsw = SharedPreference.getInstance().getBoolean(mContext, PreferenceCongfig.APP_IS_SET_GESTURE_PSW, false);
    }


    @Override
    protected int getContentLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initializeView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            initAppData();
        else
            checkPermissionForMe();

    }

    private void initAppData() {
        final boolean isFirstLauch = SharedPreference.getInstance().getBoolean(this, PreferenceCongfig.APP_ISFIRST_LAUCH, true);
        ThreadPoolManager.getInstance(TAG).schedule(new Runnable() {
            @Override
            public void run() {
                if (isFirstLauch) {
                    SharedPreference.getInstance().saveBoolean(SplashActivity.this, PreferenceCongfig.APP_ISFIRST_LAUCH, false);
                    startActivity(GuideActivity.class);
                    finish();
                } else if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(psw)) {
                    if (isSetGesturePsw) {
                        Bundle bundle = new Bundle();
                        bundle.putString(PreferenceCongfig.APP_AUTO_LOGIN, "auto_login");
                        PreferenceCongfig.IS_APP_AUTO_LOGIN = true;
                        ActivityTools.switchActivity(SplashActivity.this, GesturePswLoginActivity.class, bundle);
                        finish();
                    } else {
                        startActivity(LoginActivity.class);
                        finishActivity(SplashActivity.this);
                    }

                } else {
                    startActivity(MainActivity.class);
                    finish();
                }
            }
        }, WAIT_DURATION, TimeUnit.MILLISECONDS);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ThreadPoolManager.getInstance(TAG).cancelTaskThreads(TAG, true);
    }


    private void checkPermissionForMe() {
        performCodeWithPermission(getString(R.string.permission_tips), new PermissionCallback() {
                    @Override
                    public void hasPermission() {
                        initAppData();
                    }

                    @Override
                    public void noPermission() {
                        initAppData();
                    }
                }, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.GET_ACCOUNTS, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                , Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_LOGS,
                Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.WRITE_APN_SETTINGS
                , Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.CHANGE_NETWORK_STATE

        );//增加的权限必须要能授权才有效

    }
}
