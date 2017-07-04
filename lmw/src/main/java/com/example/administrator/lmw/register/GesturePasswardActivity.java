package com.example.administrator.lmw.register;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.ShowMineFragmentEvent;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.login.SetPasswardtActivity;
import com.example.administrator.lmw.main.MainActivity;
import com.example.administrator.lmw.mine.seting.ResetTradPswVerifyActivity;
import com.example.administrator.lmw.mine.seting.VerifyOldLoginPswActivity;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.SharedPreference;
import com.syd.oden.gesturelock.view.GestureLockViewGroup;
import com.syd.oden.gesturelock.view.listener.GestureEventListener;
import com.syd.oden.gesturelock.view.listener.GesturePasswordSettingListener;
import com.syd.oden.gesturelock.view.listener.GestureUnmatchedExceedListener;

import org.greenrobot.eventbus.EventBus;

import butterknife.InjectView;
import butterknife.OnClick;

public class GesturePasswardActivity extends BaseActivity {
    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.title_layout)
    RelativeLayout title_layout;
    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.back_layout)
    LinearLayout backLayout;
    @InjectView(R.id.tv_state)
    TextView tv_state;
    @InjectView(R.id.gesturelock)
    GestureLockViewGroup mGestureLockViewGroup;
    private boolean isReset = false;
    private Intent intent;
    private int setGestureType = -1;
    private int goPageType = -1;

    @Override
    protected void initializeData() {
        SharedPreference.getInstance().saveBoolean(mContext, PreferenceCongfig.APP_IS_SET_GESTURE_PSW, false);//初始化是否设置手势密码状态
        intent = getIntent();
        if (intent.hasExtra(PreferenceCongfig.RESET_GESTURE_PSW)) {
            setGestureType = intent.getIntExtra(PreferenceCongfig.RESET_GESTURE_PSW, -1);//重置手势密码，删掉之前的手势密码再设置
        }
        if (intent.hasExtra(PreferenceCongfig.SET_GESTURE_PSW)) {
            setGestureType = intent.getIntExtra(PreferenceCongfig.SET_GESTURE_PSW, -1);//之前没有保存手势密码，设置新的手势密码
        }
        if (intent.hasExtra(PreferenceCongfig.MOTIFY_GESTURE_PSW)) {
            setGestureType = intent.getIntExtra(PreferenceCongfig.MOTIFY_GESTURE_PSW, -1);//先验证原手势密码再设置新手势密码
        }
        if (intent.hasExtra(PreferenceCongfig.FROM_POSITION_TO_SET_PSW)) {
            goPageType = intent.getIntExtra(PreferenceCongfig.FROM_POSITION_TO_SET_PSW, -1);
        }
        if (intent.hasExtra(PreferenceCongfig.DEBENTURE_TO_LOGIN))
            goPageType = intent.getIntExtra(PreferenceCongfig.DEBENTURE_TO_LOGIN, 0);
        if (intent.hasExtra(PreferenceCongfig.PURCHASE_TO_LOGIN))
            goPageType = intent.getIntExtra(PreferenceCongfig.PURCHASE_TO_LOGIN, 0);

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_gesture_passwoad;
    }

    @Override
    protected void initializeView() {
        initTitle();
        initGesture();
        switch (setGestureType) {
            case PreferenceCongfig.reset_gesture_psw:
            case PreferenceCongfig.set_gesture_psw:
                tv_state.setText("设置登录利民网的密码(首次)");
                mGestureLockViewGroup.resetView();
                mGestureLockViewGroup.motifyPasswordReset();//初始化到设置手势密码状态
                tv_state.setTextColor(getResources().getColor(R.color.text_blue));
                break;
            case PreferenceCongfig.motify_gesture_psw://先验证原手势密码再设置新手势密码
                isReset = true;
                tv_state.setTextColor(getResources().getColor(R.color.text_blue));
                tv_state.setText("请验证原手势密码");
                mGestureLockViewGroup.resetView();
                break;
        }
    }

    private void initGesture() {
        gestureEventListener();
        gesturePasswordSettingListener();
        gestureRetryLimitListener();
    }

    private void gestureRetryLimitListener() {
        /**
         * 手势密码输入错误超限提示
         */
        mGestureLockViewGroup.setGestureUnmatchedExceedListener(4, new GestureUnmatchedExceedListener() {
            @Override
            public void onUnmatchedExceedBoundary(int mTryTimes) {
                tv_state.setTextColor(Color.RED);

            }

            @Override
            public void onUnmatchedLessFourPoint(int length) {
                ALLog.e(length + "");
                if (length < 4) {
                    tv_state.setTextColor(Color.RED);
                    tv_state.setText("请至少连接四个点");
                }
            }
        });

    }

    private void initTitle() {
        switch (setGestureType) {
            case PreferenceCongfig.reset_gesture_psw:
                barTitle.setText("重置手势密码");
                barIconBack.setVisibility(View.VISIBLE);
                break;
            case PreferenceCongfig.set_gesture_psw:
                barTitle.setText("设置手势密码");
                barIconBack.setVisibility(View.GONE);
                break;
            case PreferenceCongfig.motify_gesture_psw:
                barTitle.setText("修改手势密码");
                barIconBack.setVisibility(View.VISIBLE);
                break;
        }
        barIconBack.setImageResource(R.drawable.nav_back);
        title_layout.setBackgroundColor(getResources().getColor(R.color.text_blue));
    }

    /**
     * 验证手势密码
     */
    private void gestureEventListener() {
        mGestureLockViewGroup.setGestureEventListener(new GestureEventListener() {
            @Override
            public void onGestureEvent(boolean matched) {
                if (!matched) {
                    tv_state.setTextColor(Color.RED);
                    tv_state.setText("手势密码错误");
                    mGestureLockViewGroup.resetView();
                } else {
                    if (isReset) {
                        isReset = false;
                        resetGesturePattern();
                    } else {
                        tv_state.setTextColor(getResources().getColor(R.color.text_blue));
                        tv_state.setText("手势密码正确");
                    }
                }
            }
        });
    }

    /**
     * 设置手势密码
     */
    private void gesturePasswordSettingListener() {
        mGestureLockViewGroup.setGesturePasswordSettingListener(new GesturePasswordSettingListener() {
            @Override
            public boolean onFirstInputComplete(int len) {
                if (len > 3) {
                    tv_state.setTextColor(getResources().getColor(R.color.text_blue));
                    tv_state.setText("设置登录利民网的登录密码(再次)");
                    return true;
                } else {
                    tv_state.setTextColor(Color.RED);
                    tv_state.setText("最少连接4个点，请重新输入!");
                    return false;
                }
            }

            @Override
            public void onSuccess(String gesturePsw) {
                showToast("手势密码设置成功!");
                SharedPreference.getInstance().saveBoolean(mContext, PreferenceCongfig.APP_IS_SET_GESTURE_PSW, true);//保存密码
                tv_state.setTextColor(getResources().getColor(R.color.text_blue));
                tv_state.setText("请验证手势密码");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (goPageType == PreferenceCongfig.FROM_LOGIN_TO_SET_PSW) {
                            startActivity(MainActivity.class);//从登陆界面忘记密码自动登录设置手势密码
                            EventBus.getDefault().post(new ShowMineFragmentEvent());
                        } else if (goPageType == PreferenceCongfig.FROM_REGISTER_TO_SET_PSW) {//注册自动登录设置手势密码
                            if (PreferenceCongfig.RegistType == PreferenceCongfig.FROM_FINANCE_BANNAER_TO_REGISTER) {//从webviewmore banner条注册自动登录
                                PreferenceCongfig.RegistType = -1;
                                finishActivity(GesturePasswardActivity.this);
                                ActivityManage.create().finishActivity(SetPasswardtActivity.class);
                                ActivityManage.create().finishActivity(PhoneVerifyActivity.class);
                            } else {
                                startActivity(MainActivity.class);//从注册自动登录设置手势密码
                                EventBus.getDefault().post(new ShowMineFragmentEvent());
                            }

                        } else if (goPageType == PreferenceCongfig.FROM_SET_MOTIFY_LOGIN_PSW) {//从设置
                            finishActivity(GesturePasswardActivity.this);
                            ActivityManage.create().finishActivity(SetPasswardtActivity.class);
                            ActivityManage.create().finishActivity(VerifyOldLoginPswActivity.class);
                        } else if (goPageType == PreferenceCongfig.FROM_SET_MOTIFY_GESTURE_PSW || goPageType == PreferenceCongfig.FROM_SET_SET_GESTURE_PSW) {
                            finishActivity(GesturePasswardActivity.this);//从设置
                        } else if (goPageType == PreferenceCongfig.FROM_SET_RESET_GESTURE_PSW) {//从设置
                            finishActivity(GesturePasswardActivity.this);
                            ActivityManage.create().finishActivity(ResetTradPswVerifyActivity.class);
                        } else if (goPageType == PreferenceCongfig.purchase_to_login ||
                                goPageType == PreferenceCongfig.debenture_to_login) {//标的购买来登录或者//债权购买来登录
                            finishActivity(GesturePasswardActivity.this);
                            ActivityManage.create().finishActivity(LoginActivity.class);
                        } else if (goPageType == PreferenceCongfig.debenture_to_login) {//债权购买来登录
                            finishActivity(GesturePasswardActivity.this);
                            ActivityManage.create().finishActivity(LoginActivity.class);
                        } else if (PreferenceCongfig.loginType == PreferenceCongfig.FROM_FINANCE_BANNAER_TO_LOGIN) {
                            //从WebviewMOre BANNER条登陆界面登陆账号不一样设置手势密码跳转
                            PreferenceCongfig.loginType = -1;
                            finishActivity(GesturePasswardActivity.this);
                            ActivityManage.create().finishActivity(LoginActivity.class);
                        } else
                            startActivity(MainActivity.class);//从登陆界面登陆账号不一样设置手势密码跳转
                    }
                }, 1000);


            }

            @Override
            public void onFail() {
                tv_state.setTextColor(Color.RED);
                tv_state.setText("与第一手势密码不一致，请重新输入");
            }
        });
    }


    /**
     * 修改手势密码时UI状态
     */
    private void resetGesturePattern() {
        // mGestureLockViewGroup.removePassword();
        mGestureLockViewGroup.motifyPasswordReset();
        //  setGestureWhenNoSet();
        tv_state.setTextColor(getResources().getColor(R.color.text_blue));
        tv_state.setText("设置登录利民网的密码");
        mGestureLockViewGroup.resetView();
    }


    @OnClick({R.id.back_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                finishActivity(this);
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (setGestureType == PreferenceCongfig.motify_gesture_psw || setGestureType == PreferenceCongfig.reset_gesture_psw)
                finishActivity(GesturePasswardActivity.this);

            return true;
        }

        return super.onKeyDown(keyCode, event);

    }
}
