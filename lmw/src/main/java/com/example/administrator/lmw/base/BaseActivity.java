package com.example.administrator.lmw.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.http.RxActionManagerImp;
import com.example.administrator.lmw.http.RxManager;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.login.GesturePswLoginActivity;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.APPUtil;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.StatusBarUtil;
import com.example.administrator.lmw.view.NormalDialog;
import com.example.administrator.lmw.view.ProgressDialog;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;


/**
 * @Description:TODO 通用基础activity
 * @create by lion
 * @created at 2016/8/18
 */
public abstract class BaseActivity extends FragmentActivity {

    private static final String mPageName = "activity";
    protected ProgressDialog loadingDialog;
    protected Context mContext;
    protected LayoutInflater inflater;
    private int targetSdkVersion;
    protected Bundle saveInsstanceState;

    //private   ScreenStatusReceiver screenStatusReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        this.saveInsstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        initializeData();
        ActivityManage.create().addActivity(this);
        if (loadingDialog == null)
            loadingDialog = new ProgressDialog(this);
        inflater = LayoutInflater.from(mContext);
        int resLayoutId = getContentLayout();
        if (resLayoutId != -1)
            setContentView(getContentLayout());
        setStatusBar();
        ButterKnife.inject(this);
        initializeView();
// 屏幕状态广播
/*
        // 屏幕状态广播初始化
        screenStatusReceiver = new ScreenStatusReceiver();
        IntentFilter screenStatusIF = new IntentFilter();
        screenStatusIF.addAction(Intent.ACTION_SCREEN_ON);
        // 注册
        registerReceiver(screenStatusReceiver, screenStatusIF);*/
    }

    /**
     * 子类Activity重写，获取数据
     */
    protected abstract void initializeData();

    /**
     * 子类Activity重写，初始化布局View
     */
    protected abstract int getContentLayout();


    /**
     * 子类Activity重写，更新View
     */
    protected abstract void initializeView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManage.create().finishActivity(this);
        ButterKnife.reset(this);
        //页面销毁取消并移除根据当前页面context为tag标记发出去的网络请求
        RxActionManagerImp.create().cancelSubscriber(mContext);
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.text_blue), 0);
    }

    /**
     * 用于获取状态栏的高度。 使用Resource对象获取（推荐这种方式）
     *
     * @return 返回状态栏高度的像素值。
     */
    protected static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 显示对话框加载进度条
     */
    public void showLoadingDialog() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (loadingDialog != null && !loadingDialog.isShowing()) {
                    loadingDialog.show();
                }
            }
        });
    }

    /**
     * 隐藏对话框加载进度条
     */
    public void hideLoadingDialog() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (loadingDialog != null && loadingDialog.isShowing())
                    loadingDialog.dismiss();

            }
        });

    }

    /**
     * 描述：Toast提示文本.
     *
     * @param text 文本
     */
    public void showToast(final String text) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 描述：Toast提示文本.
     *
     * @param mesageId 文本id
     */
    public void showToast(final int mesageId) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, getString(mesageId), Toast.LENGTH_SHORT).show();
            }

        });

    }


    /**
     * 默认启动一个activity
     *
     * @param clazz
     */
    protected void startActivity(Class<?> clazz) {
        Intent startIntent = new Intent(this, clazz);
        this.startActivity(startIntent);
    }

    /**
     * 默认启动一个activity
     */
    protected void finishActivity(Activity mActivity) {
        ActivityManage.create().finishActivity(mActivity);
    }


    private boolean isRunningForeground;


    @Override
    protected void onStop() {
        super.onStop();
        ALLog.e("STOP");
        SharedPreference.getInstance().saveBoolean(mContext, PreferenceCongfig.APP_IS_FORWARD, APPUtil.isAppIsInBackground(mContext));
        if (APPUtil.isAppIsInBackground(mContext))
            saveStartTime();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ALLog.e("onRestart");
        isRunningForeground = SharedPreference.getInstance().getBoolean(mContext, PreferenceCongfig.APP_IS_FORWARD, false);
        boolean isSetGesturePsw = SharedPreference.getInstance().getBoolean(mContext, PreferenceCongfig.APP_IS_SET_GESTURE_PSW, false);
        if (isRunningForeground && PreferenceCongfig.isLogin && isSetGesturePsw) {
            long endtime = System.currentTimeMillis();
            ALLog.e(endtime + "");
            ALLog.e("startime" + getStartTime());
            if (endtime - getStartTime() >= 60 * 1000) {
                startActivity(GesturePswLoginActivity.class);
                //  SharedPreference.getInstance().saveBoolean(mContext, PreferenceCongfig.IS_SHOW_CURRENT_ACTIVITY_FOR_GESTURE_LOGIN, true);
            }

        }
    }

    public void saveStartTime() {
        SharedPreference.getInstance().saveLong(mContext, PreferenceCongfig.APP_TO_FORWARD_START, System.currentTimeMillis());

    }

    public long getStartTime() {
        return SharedPreference.getInstance().getLong(mContext, PreferenceCongfig.APP_TO_FORWARD_START, System.currentTimeMillis());
    }
/*    */

    /**
     * 屏幕状态广播
     *
     * @author yuegy
     *//*
    class ScreenStatusReceiver extends BroadcastReceiver

    {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 屏幕唤醒
            ALLog.e("SCREEN_ON");
            if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
                if ( PreferenceCongfig.isLogin){
                    startActivity(GesturePswLoginActivity.class);
                }
            }
        }
    }*/
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart(mPageName);

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd(mPageName);
    }


    /****************
     * Android M Permission (Android 6.0权限控制代码封装)
     *****************************************************/
    private int permissionRequestCode = 88;
    private PermissionCallback permissionRunnable;

    public interface PermissionCallback {
        void hasPermission();

        void noPermission();
    }


    /**
     * Android M运行时权限请求封装
     *
     * @param permissionDes 权限描述
     * @param runnable      请求权限回调
     * @param permissions   请求的权限（数组类型），直接从Manifest中读取相应的值，比如Manifest.permission.WRITE_CONTACTS
     */
    public void performCodeWithPermission(String permissionDes, PermissionCallback runnable,
                                          String... permissions) {
        if (permissions == null || permissions.length == 0)
            return;
        this.permissionRunnable = runnable;
        if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || checkPermissionGranted
                (permissions)) {
            if (permissionRunnable != null) {
                permissionRunnable.hasPermission();
                permissionRunnable = null;
            }
        } else {
            //permission has not been granted.
            requestPermission(permissionDes, permissionRequestCode, permissions);
        }

    }

    private boolean checkPermissionGranted(String[] permissions) {
        boolean flag = true;
        for (String p : permissions) {
            // if (ActivityCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED)
            if (selfPermissionGranted(p)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private void requestPermission(String permissionDes, final int requestCode, final String[]
            permissions) {
        if (shouldShowRequestPermissionRationale(permissions)) {
            //如果用户之前拒绝过此权限，再提示一次准备授权相关权限
            Singlton.getInstance(PopWindowManager.class).showTwoButtonDialog(mContext, "", permissionDes, "残忍拒绝", "继续体验",
                    new OnDialogClickListener() {
                        @Override
                        public void onClick(int id, View v) {
                            if (NormalDialog.RIGHT_ONCLICK == id) {
                                // 关闭弹窗
                                ALLog.e("用户之前拒绝过此权限===================请求授权");
                                ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
                            } else {
                                if (permissionRunnable != null) {
                                    permissionRunnable.noPermission();
                                    permissionRunnable = null;
                                }
                            }
                        }
                    });

        } else {
            ALLog.e("===================请求授权");
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);


        }
    }

    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        boolean flag = false;
        for (String p : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, p)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        ALLog.e("requestCode" + requestCode);
        if (requestCode == permissionRequestCode) {
            if (verifyPermissions(grantResults)) {
                if (permissionRunnable != null) {
                    permissionRunnable.hasPermission();
                    permissionRunnable = null;
                }
            } else {
                //                showToast("暂无权限执行相关操作！");

                ALLog.e("暂无权限执行相关操作或用户拒绝权限！");
                if (permissionRunnable != null) {
                    permissionRunnable.noPermission();
                    permissionRunnable = null;
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    public boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.

        ALLog.e("grantResults" + java.util.Arrays.toString(grantResults));
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    public boolean selfPermissionGranted(String permission) {
        // For Android < Android M, self permissions are always granted.
        boolean result = true;
        //  获取target sdk的方法如下：

        try {
            final PackageInfo info = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (targetSdkVersion >= Build.VERSION_CODES.M) {
                    // targetSdkVersion >= Android M, we can
                    // use Context#checkSelfPermission
                    result = mContext.checkSelfPermission(permission)
                            == PackageManager.PERMISSION_GRANTED;
                } else {
                    // targetSdkVersion < Android M, we have to use PermissionChecker
                    result = PermissionChecker.checkSelfPermission(mContext, permission)
                            == PermissionChecker.PERMISSION_GRANTED;
                }
            }


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        return result;
    }


    /********************** END Android M Permission ****************************************/


}