package com.example.administrator.lmw.mine.seting;
/**
 * 设置交易密码验证界面 只针对老用户
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
import com.example.administrator.lmw.entity.BaseResponse;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.login.UserInfoUtils;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.login.entity.UserInfoBean;
import com.example.administrator.lmw.register.RegisterLogic;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.CountdownThread;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.StringUtils;

import butterknife.InjectView;
import butterknife.OnClick;

public class SetTradPswVerifyActivity extends BaseActivity implements CountdownThread.OnCountdownListener {
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
    private UserInfo userInfo;
    private UserInfoBean userInfoBean;
    private boolean isBindCard = false;//是否是绑卡页面进来的

    @Override
    protected void initializeData() {
        userInfo = UserInfoUtils.getInstance().getUserInfo(this);
        if (userInfo != null)
            userInfoBean = userInfo.getData();
        account = SharedPreference.getInstance().getString(this, PreferenceCongfig.APP_ACCOUNT, "");

        Intent intent = getIntent();
        if (intent.hasExtra(PreferenceCongfig.KEY_NEW_USER_SET_PW))
            isBindCard = intent.getBooleanExtra(PreferenceCongfig.KEY_NEW_USER_SET_PW, false);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_reset_trade_verify_phone;
    }

    @Override
    protected void initializeView() {
        //   getVerifyCode();
        nextBt.setClickable(false);
        sms_hint.setText(String.format(getString(R.string.reset_trade_psw), userInfoBean.getMobile()));
        initTitle();
        setTextChangeListener();
    }

    private void initTitle() {
        barTitle.setText("设置交易密码");
        back.setVisibility(View.GONE);
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

    @OnClick({R.id.btn_get_verification_code,
            R.id.next_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_verification_code:
                if (enableGetVerifyCode)
                    getVerifyCode();
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
                checkVerifyCode();//核对设置交易密码短信验证码是否正确

                break;

        }
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
                            Bundle bundle = new Bundle();
                            bundle.putInt(PreferenceCongfig.SET_EXCHARGE_PSW, PreferenceCongfig.set_excharge_psw);
                            bundle.putString(PreferenceCongfig.PHONE, account);
                            bundle.putString(PreferenceCongfig.VERIFY_CODE, verifyCode);
                            //add by snubee 首次绑卡
                            if (isBindCard)
                                bundle.putBoolean(PreferenceCongfig.KEY_NEW_USER_SET_PW, true);
                            ActivityTools.switchActivity(SetTradPswVerifyActivity.this, SetExchargePsw.class, bundle);

                            // finishActivity(SetTradPswVerifyActivity.this);
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


    /**
     * 获取验证码
     * 06 老用户首次设置交易密码获取短信验证码类型
     */
    private void getVerifyCode() {
        Singlton.getInstance(RegisterLogic.class).getVerifyCode(this, account, "06",
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        int code = Integer.valueOf(response.code);
                        if (code == 0) {
                            showToast(R.string.verficode_tips);
                            startTimer();
                        } else if (code == 150006) {
                            showToast(getString(R.string.re_login));
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
        enableGetVerifyCode = false;
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            return true;
        }

        return super.onKeyDown(keyCode, event);

    }

}
