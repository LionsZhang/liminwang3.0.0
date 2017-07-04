package com.example.administrator.lmw.mine.seting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseEvent;
import com.example.administrator.lmw.entity.BaseResponse;
import com.example.administrator.lmw.entity.BindBankEvent;
import com.example.administrator.lmw.entity.BindBankRefreshEvent;
import com.example.administrator.lmw.entity.BindSuccessEvent;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.login.UserInfoUtils;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.login.entity.UserInfoBean;
import com.example.administrator.lmw.register.GesturePasswardActivity;
import com.example.administrator.lmw.register.RegisterLogic;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.CountdownThread;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.InjectView;
import butterknife.OnClick;

public class ResetTradPswVerifyActivity extends BaseActivity implements CountdownThread.OnCountdownListener {
    @InjectView(R.id.title_layout)
    RelativeLayout title_layout;
    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.back)
    TextView back;
    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.back_layout)
    LinearLayout backLayout;
    @InjectView(R.id.iv_verify_icon)
    ImageView ivVerifyIcon;
    @InjectView(R.id.et_verification_code)
    EditText etVerificationCode;
    @InjectView(R.id.btn_get_verification_code)
    Button btnGetVerificationCode;
    @InjectView(R.id.next_bt)
    Button nextBt;
    @InjectView(R.id.sms_hint)
    TextView sms_hint;
    private String account;
    private boolean enableGetVerifyCode = true;
    private String verifyCode;
    private Intent intent;
    private int type;
    private String licence;
    private String bindCardOderNo;
    private String smsType;
    private String phone;
    private UserInfo userInfo;
    private UserInfoBean userInfoBean;

    /**
     * 获取验证码
     * 01:重置手势密码-获取短信验证码
     * 03:交易密码设置-获取短信验证码
     * 04:绑定银行卡-获取短信验证码
     */
    @Override
    protected void initializeData() {
        EventBus.getDefault().register(this);
        userInfo = UserInfoUtils.getInstance().getUserInfo(this);
        if (userInfo != null)
            userInfoBean = userInfo.getData();
        account = SharedPreference.getInstance().getString(this, PreferenceCongfig.APP_ACCOUNT, "");
        initPara();

    }

    private void initPara() {
        intent = getIntent();
        if (intent.hasExtra(PreferenceCongfig.RESET_EXCHARGE_PSW)) {
            type = intent.getIntExtra(PreferenceCongfig.RESET_EXCHARGE_PSW, -1);
            smsType = "03";
        }

        if (intent.hasExtra(PreferenceCongfig.BIND_BANK_CARD_VERIFY))
            type = intent.getIntExtra(PreferenceCongfig.BIND_BANK_CARD_VERIFY, -1);
        if (intent.hasExtra(PreferenceCongfig.RESET_GESTURE_PSW)) {
            type = intent.getIntExtra(PreferenceCongfig.RESET_GESTURE_PSW, -1);
            smsType = "01";
        }
        if (intent.hasExtra(PreferenceCongfig.LICENCE))
            licence = intent.getStringExtra(PreferenceCongfig.LICENCE);
        if (intent.hasExtra(PreferenceCongfig.BIND_PHONE))
            phone = intent.getStringExtra(PreferenceCongfig.BIND_PHONE);
        if (intent.hasExtra(PreferenceCongfig.BIND_CARD_ODER_NO))
            bindCardOderNo = intent.getStringExtra(PreferenceCongfig.BIND_CARD_ODER_NO);

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_reset_trade_verify_phone;
    }

    @Override
    protected void initializeView() {
        nextBt.setClickable(false);
        if (type == PreferenceCongfig.bind_bank_card_verify) {
            sms_hint.setText(String.format(getString(R.string.reset_trade_psw), StringUtils.fuzzyMobile(phone)));
            enableGetVerifyCode = false;
            startTimer();
        } else {
            //  getVerifyCode();
            sms_hint.setText(String.format(getString(R.string.reset_trade_psw), userInfoBean.getMobile()));
        }

        initTitle();
        setTextChangeListener();
    }

    private void initTitle() {
        if (type == PreferenceCongfig.reset_excharge_psw) {
            barTitle.setText("重置交易密码");
            //  btnGetVerificationCode.setVisibility(View.VISIBLE);
        }
        if (type == PreferenceCongfig.bind_bank_card_verify) {
            barTitle.setText("绑定银行卡");
            //  btnGetVerificationCode.setVisibility(View.GONE);
        }

        if (type == PreferenceCongfig.reset_gesture_psw) {
            barTitle.setText("重置手势密码");
            //   btnGetVerificationCode.setVisibility(View.VISIBLE);
        }

        back.setText("返回");
        title_layout.setBackgroundColor(getResources().getColor(R.color.text_blue));
    }

    private void setTextChangeListener() {

        etVerificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                verifyCode = etVerificationCode.getText().toString();
                if (TextUtils.isEmpty(verifyCode)) {
                    nextBt.setClickable(false);
                    nextBt.setBackgroundResource(R.drawable.login_bg_nol);
                } else if (verifyCode.length() == 6) {
                    nextBt.setClickable(true);
                    nextBt.setBackgroundResource(R.drawable.login_bg_sel);
                }
            }
        });


    }

    @OnClick({R.id.back_layout, R.id.btn_get_verification_code,
            R.id.next_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                finishActivity(ResetTradPswVerifyActivity.this);
                break;
            case R.id.btn_get_verification_code:
                if (enableGetVerifyCode) {
                    if (type == PreferenceCongfig.bind_bank_card_verify) {
                        EventBus.getDefault().post(new BindBankEvent());
                        enableGetVerifyCode = false;
                        startTimer();

                    } else {
                        getVerifyCode();
                    }
                }

                break;

            case R.id.next_bt:
                if (TextUtils.isEmpty(verifyCode)) {

                    showToast("请输入验证码");
                    return;
                }

                if (!StringUtils.isNumberOrLetter(verifyCode)) {
                    showToast("验证码必须为六位");
                    return;
                }

                showLoadingDialog();
                if (type == PreferenceCongfig.reset_gesture_psw)
                    checkVerifyCode();//核对重置手势密码短信验证码是否正确
                else if (type == PreferenceCongfig.bind_bank_card_verify)
                    bindBankCard();//核对绑定银行卡
                else
                    verifyAuth(account);//重置交易密码短信验证码是否正确
                break;

        }
    }

    /**
     * 绑定银行卡，重置交易密码短信验证接口,核对验证码是否正确
     */
    private void verifyAuth(String phoneType) {
        Singlton.getInstance(SetLogic.class).resetTradePswVerify(this, phoneType, verifyCode, new OnResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                hideLoadingDialog();
                int code = Integer.valueOf(response.code);
                if (code == 0) {
                    if (type == PreferenceCongfig.reset_excharge_psw)
                        goSetTradPassward();
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
                }

            }

            @Override
            public void onFail(Throwable e) {
                hideLoadingDialog();
            }

        });
    }

    /**
     * 核对手机验证码
     */

    private void checkVerifyCode() {
        Singlton.getInstance(RegisterLogic.class).checkVerifyCode(this, account, verifyCode,
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        hideLoadingDialog();
                        int code = Integer.valueOf(response.code);
                        if (code == 0) {
                            if (type == PreferenceCongfig.reset_gesture_psw) {
                                Bundle bundle = new Bundle();
                                bundle.putInt(PreferenceCongfig.RESET_GESTURE_PSW, PreferenceCongfig.reset_gesture_psw);
                                bundle.putInt(PreferenceCongfig.FROM_POSITION_TO_SET_PSW, PreferenceCongfig.FROM_SET_RESET_GESTURE_PSW);
                                ActivityTools.switchActivity(ResetTradPswVerifyActivity.this, GesturePasswardActivity.class, bundle);
                            }
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
                            hideLoadingDialog();
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        hideLoadingDialog();
                    }
                });

    }

    private void bindBankCard() {//绑定银行卡
        Singlton.getInstance(SetLogic.class).bindBankCardTwo(this, bindCardOderNo, verifyCode, new OnResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                hideLoadingDialog();
                int code = Integer.valueOf(response.code);
                if (code == 0) {
                    showToast("绑定银行卡成功");
                    keepUserInfo();
                    EventBus.getDefault().post(new BindSuccessEvent());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (PreferenceCongfig.IS_NOT_SET_TRADEPASSWD == userInfoBean.getIsSetTradepasswd()) {
                                if (userInfoBean.getUserTag() == PreferenceCongfig.IS_OLD_UN_EFFECTIVE_USER) {
                                    Intent intent = new Intent(mContext, SetTradPswVerifyActivity.class);
//                                    //add by snubee  start 添加新用户绑卡标记
//                                    intent.putExtra(PreferenceCongfig.KEY_NEW_USER_SET_PW, true);
//                                    //add by snubee  end 添加新用户绑卡标记
                                    startActivity(intent);
                                    finishActivity(ResetTradPswVerifyActivity.this);
                                    ActivityManage.create().finishActivity(BindBankActivity.class);
                                } else if (userInfoBean.getUserTag() == PreferenceCongfig.IS_NEW_USER ||
                                        userInfoBean.getUserTag() == PreferenceCongfig.IS_OLD_EFFECTIVE_USER) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(PreferenceCongfig.SET_EXCHARGE_PSW, PreferenceCongfig.set_excharge_psw);
                                    //add by snubee  start 添加新用户绑卡标记
                                    bundle.putBoolean(PreferenceCongfig.KEY_NEW_USER_SET_PW, userInfoBean.getUserTag() == PreferenceCongfig.IS_NEW_USER);
                                    //add by snubee  end 添加新用户绑卡标记
                                    ActivityTools.switchActivity(ResetTradPswVerifyActivity.this, SetExchargePsw.class, bundle);
                                    finishActivity(ResetTradPswVerifyActivity.this);
                                    ActivityManage.create().finishActivity(BindBankActivity.class);
                                }
                            } else {
                                finishActivity(ResetTradPswVerifyActivity.this);
                                ActivityManage.create().finishActivity(BindBankActivity.class);
                            }

                        }
                    }, 500);
                } else if (code == 150006) {
                    showToast(response.msg);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(LoginActivity.class);
                        }
                    }, 1000);
                } else if (code == 1) {
                    showToast(response.msg);
                } else if (code == 2) {
                    Singlton.getInstance(PopWindowManager.class).
                            showOneButtonDialog(mContext, "温馨提示", response.msg, "取消", new OnDialogClickListener() {
                                @Override
                                public void onClick(int id, View v) {
                                    if (id == 2) {

                                    }
                                }
                            });
                } else
                    showToast(response.msg);
            }

            @Override
            public void onFail(Throwable e) {
                hideLoadingDialog();
            }
        });


    }

    private void keepUserInfo() {
        UserInfo userInfo = UserInfoUtils.getInstance().getUserInfo(ResetTradPswVerifyActivity.this);
        if (userInfo != null) {
            UserInfoBean userInfoBean = userInfo.getData();
            userInfoBean.setIsRealnameAuth(PreferenceCongfig.IS_REAL_NAME_AUTH);
            userInfoBean.setIsBindBankCard(PreferenceCongfig.IS_BIND_BANKCARD);
            userInfo.setData(userInfoBean);
            UserInfoUtils.getInstance().setUserInfo(this, userInfo);
        }
    }

    private void goSetTradPassward() {//重置交易密码
        Bundle bundle = new Bundle();
        bundle.putString(PreferenceCongfig.PHONE, account);
        bundle.putString(PreferenceCongfig.VERIFY_CODE, verifyCode);
        bundle.putInt(PreferenceCongfig.RESET_EXCHARGE_PSW, PreferenceCongfig.reset_excharge_psw);
        ActivityTools.switchActivity(this, SetExchargePsw.class, bundle);

    }

    /**
     * 获取验证码
     */
    private void getVerifyCode() {
        Singlton.getInstance(RegisterLogic.class).getVerifyCode(this, account, smsType,
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        int code = Integer.valueOf(response.code);
                        if (code == 0) {
                            showToast(R.string.verficode_tips);
                            enableGetVerifyCode = false;
                            startTimer();

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
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });

    }

    private void startTimer() {
        btnGetVerificationCode.setBackgroundResource(R.drawable.login_bg_nol);
        CountdownThread thread = new CountdownThread();
        thread.setOnCountdownListener(this);
        thread.start(); // 开始倒计时

    }

    @Override
    public void OnCountdown(int count) {
        if (btnGetVerificationCode != null)
            btnGetVerificationCode.setText(count + getString(R.string.countdown_tip));
    }

    @Override
    public void OnCountdownFinish() {
        if (btnGetVerificationCode != null) {
            btnGetVerificationCode.setBackgroundResource(R.drawable.login_bg_sel);
            btnGetVerificationCode.setText(getString(R.string.get_verify_code));
        }
        enableGetVerifyCode = true;
    }


    /**
     * @param baseEvent void
     * @auther lion
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent baseEvent) {
        if (baseEvent != null) {
            ALLog.e("==================================");
            if (baseEvent instanceof BindBankRefreshEvent) {
                BindBankRefreshEvent bindBankRefreshEvent = (BindBankRefreshEvent) baseEvent;
                bindCardOderNo = bindBankRefreshEvent.getOderNo();
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
