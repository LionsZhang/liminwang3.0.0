package com.example.administrator.lmw.mine.seting;

import android.content.Intent;
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
import com.example.administrator.lmw.entity.BaseResponse;
import com.example.administrator.lmw.entity.SetTradePswEvent;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.login.UserInfoUtils;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.login.entity.UserInfoBean;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.view.PasswardEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lion on 2016/9/2.
 */
public class ConfirmExchargePsw extends BaseActivity implements PasswardEditText.OnEditTextListener {
    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.title_layout)
    RelativeLayout title_layout;
    @InjectView(R.id.right_title)
    TextView right_title;
    @InjectView(R.id.title_bar_right_layout)
    LinearLayout title_bar_right_layout;
    @InjectView(R.id.psw_et)
    PasswardEditText passwardEditText;
    @InjectView(R.id.back_layout)
    LinearLayout backLayout;
    private UserInfo userInfo;
    private UserInfoBean userInfoBean;
    private String lastPsw;
    private String confirmPsw;
    private String oldTradingPsw;
    private int type = -1;
    private String verifyCode;
    private String account;
    private Intent intent;
    private boolean isNewUser;//是否是新用户

    @Override
    protected void initializeData() {
        intent = getIntent();
        userInfo = UserInfoUtils.getInstance().getUserInfo(this);
        if (userInfo != null)
            userInfoBean = userInfo.getData();
        if (intent.hasExtra(PreferenceCongfig.FIRST_INPUT_EXCHARGE_PSW)) {
            lastPsw = intent.getStringExtra(PreferenceCongfig.FIRST_INPUT_EXCHARGE_PSW);
        }
        if (intent.hasExtra(PreferenceCongfig.MOTIFY_EXCHARGE_OLD_PSW))
            oldTradingPsw = intent.getStringExtra(PreferenceCongfig.MOTIFY_EXCHARGE_OLD_PSW);
        if (intent.hasExtra(PreferenceCongfig.RESET_EXCHARGE_PSW))
            type = intent.getIntExtra(PreferenceCongfig.RESET_EXCHARGE_PSW, -1);
        if (intent.hasExtra(PreferenceCongfig.SET_EXCHARGE_PSW))
            type = intent.getIntExtra(PreferenceCongfig.SET_EXCHARGE_PSW, -1);
        if (intent.hasExtra(PreferenceCongfig.MOTIFY_EXCHARGE_PSW))
            type = intent.getIntExtra(PreferenceCongfig.MOTIFY_EXCHARGE_PSW, -1);
        if (intent.hasExtra(PreferenceCongfig.VERIFY_CODE))
            verifyCode = intent.getStringExtra(PreferenceCongfig.VERIFY_CODE);
        if (intent.hasExtra(PreferenceCongfig.PHONE))
            account = intent.getStringExtra(PreferenceCongfig.PHONE);
        if (intent.hasExtra(PreferenceCongfig.KEY_NEW_USER_SET_PW))
            isNewUser = intent.getBooleanExtra(PreferenceCongfig.KEY_NEW_USER_SET_PW, false);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.confrim_excharge_psw_layout;
    }

    @Override
    protected void initializeView() {
        initBarTitle();
        passwardEditText.setOnEditTextListener(this);
    }

    private void initBarTitle() {
        if (type == PreferenceCongfig.reset_excharge_psw) {
            barTitle.setText("重置交易密码");
            backLayout.setVisibility(View.VISIBLE);
        }

        if (type == PreferenceCongfig.set_excharge_psw) {
            barTitle.setText("设置交易密码");
            backLayout.setVisibility(View.GONE);
        }

        if (type == PreferenceCongfig.motify_excharge_psw) {
            barTitle.setText("修改交易密码");
            backLayout.setVisibility(View.VISIBLE);
        }

        barIconBack.setImageResource(R.drawable.nav_back);
        title_layout.setBackgroundColor(getResources().getColor(R.color.text_blue));

    }


    @OnClick({R.id.back_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                finishActivity(this);
                break;
        }
    }


    /**
     * 重置交易密码re
     */
    private void resetTradePsw() {
        Singlton.getInstance(SetLogic.class).resetTradePsw(this, lastPsw, confirmPsw, verifyCode, new OnResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                hideLoadingDialog();
                passwardEditText.clearEditText();
                int code = Integer.valueOf(response.code);
                if (code == 0) {
                    showToast("重置交易密码成功");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            finishActivity(ConfirmExchargePsw.this);
                            ActivityManage.create().finishActivity(SetExchargePsw.class);
                            ActivityManage.create().finishActivity(ResetTradPswVerifyActivity.class);
                        }
                    }, 1000);

                } else if (code == 150006) {
                    showToast(response.msg);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(LoginActivity.class);
                        }
                    }, 1000);
                } else {
                    showToast(response.msg);
                    finishActivity(ConfirmExchargePsw.this);
                }
            }

            @Override
            public void onFail(Throwable e) {
                hideLoadingDialog();

            }
        });
    }

    private void keepUserInfo() {
        UserInfo userInfo = UserInfoUtils.getInstance().getUserInfo(ConfirmExchargePsw.this);
        if (userInfo != null) {
            UserInfoBean userInfoBean = userInfo.getData();
            userInfoBean.setIsSetTradepasswd(PreferenceCongfig.IS_SET_TRADEPASSWD);
            userInfoBean.setIsRealnameAuth(PreferenceCongfig.IS_REAL_NAME_AUTH);
            userInfoBean.setIsBindBankCard(PreferenceCongfig.IS_BIND_BANKCARD);
            userInfo.setData(userInfoBean);
            UserInfoUtils.getInstance().setUserInfo(this, userInfo);
        }


    }

    /**
     * 新用户设置交易密码
     */
    private void saveSetTradePsw() {
        Singlton.getInstance(SetLogic.class).setTradePsw(this, confirmPsw, new OnResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                passwardEditText.clearEditText();
                hideLoadingDialog();
                int code = Integer.valueOf(response.code);
                if (code == 0) {
                    keepUserInfo();
                    showToast("设置交易密码成功");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new SetTradePswEvent());
                            if (isNewUser) {
                                Intent intent = new Intent(mContext, DespositOperateSuccessActivity.class);
                                startActivity(intent);
                            }
                            finishActivity(ConfirmExchargePsw.this);
                            ActivityManage.create().finishActivity(SetExchargePsw.class);

                        }
                    }, 1000);

                } else if (code == 150006) {
                    showToast(response.msg);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(LoginActivity.class);
                        }
                    }, 1000);
                } else {
                    showToast(response.msg);
                    finishActivity(ConfirmExchargePsw.this);
                }

            }

            @Override
            public void onFail(Throwable e) {
                hideLoadingDialog();

            }
        });

    }

    /**
     * 设置无效老用户交易密码
     */
    private void saveOldUserTradePsw() {
        Singlton.getInstance(SetLogic.class).setOldUserTradePsw(this, confirmPsw, verifyCode, new OnResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                passwardEditText.clearEditText();
                hideLoadingDialog();
                int code = Integer.valueOf(response.code);
                if (code == 0) {
                    keepUserInfo();
                    showToast("设置交易密码成功");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new SetTradePswEvent());
                            finishActivity(ConfirmExchargePsw.this);
                            ActivityManage.create().finishActivity(SetExchargePsw.class);
                            ActivityManage.create().finishActivity(SetTradPswVerifyActivity.class);

                        }
                    }, 1000);

                } else if (code == 150006) {
                    showToast(response.msg);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(LoginActivity.class);
                        }
                    }, 1000);
                } else {
                    showToast(response.msg);
                    finishActivity(ConfirmExchargePsw.this);
                    ActivityManage.create().finishActivity(SetExchargePsw.class);
                }

            }

            @Override
            public void onFail(Throwable e) {
                hideLoadingDialog();

            }
        });

    }

    private void saveMotifyTradePsw() {
        Singlton.getInstance(SetLogic.class).motifyTradePsw(this, oldTradingPsw, confirmPsw, new OnResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                passwardEditText.clearEditText();
                hideLoadingDialog();
                int code = Integer.valueOf(response.code);
                if (code == 0) {
                    showToast("修改交易密码成功");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finishActivity(ConfirmExchargePsw.this);
                            ActivityManage.create().finishActivity(SetExchargePsw.class);
                            ActivityManage.create().finishActivity(MotifyExchargePswVerify.class);
                        }
                    }, 1000);

                } else if (code == 150006) {
                    showToast(response.msg);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(LoginActivity.class);
                        }
                    }, 1000);
                } else {
                    showToast(response.msg);
                    finishActivity(ConfirmExchargePsw.this);
                }

            }

            @Override
            public void onFail(Throwable e) {
                hideLoadingDialog();

            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (type == PreferenceCongfig.motify_excharge_psw || type == PreferenceCongfig.reset_excharge_psw)
                finishActivity(ConfirmExchargePsw.this);

            return true;
        }

        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void inputComplete(int state, String password) {

        confirmPsw = password;
        if (password.equals(lastPsw)) {//将交易密码保存服务器
            showLoadingDialog();
            switch (type) {
                case PreferenceCongfig.reset_excharge_psw:
                    resetTradePsw();
                    break;
                case PreferenceCongfig.set_excharge_psw:
                    if (PreferenceCongfig.IS_OLD_UN_EFFECTIVE_USER == userInfoBean.getUserTag()) {
                        saveOldUserTradePsw();
                    }
                    if (PreferenceCongfig.IS_NEW_USER == userInfoBean.getUserTag() || PreferenceCongfig.IS_OLD_EFFECTIVE_USER == userInfoBean.getUserTag()) {
                        saveSetTradePsw();
                    }

                    break;
                case PreferenceCongfig.motify_excharge_psw:
                    saveMotifyTradePsw();
                    break;
            }

        } else {
            showToast("两次输入的交易密码不一致，请重新输入");
            finishActivity(ConfirmExchargePsw.this);
            passwardEditText.clearEditText();
        }
    }

}
