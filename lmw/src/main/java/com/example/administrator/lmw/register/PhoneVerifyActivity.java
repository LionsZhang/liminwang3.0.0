package com.example.administrator.lmw.register;

import android.os.Bundle;
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
import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResponse;
import com.example.administrator.lmw.entity.CommonInfo;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.login.SetPasswardtActivity;
import com.example.administrator.lmw.main.WebViewActivity;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.CountdownThread;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.StringUtils;
import com.example.administrator.lmw.utils.ValidateUtil;

import butterknife.InjectView;
import butterknife.OnClick;

public class PhoneVerifyActivity extends BaseActivity implements CountdownThread.OnCountdownListener {
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
    @InjectView(R.id.mobile_icon_iv)
    ImageView mobileIconIv;
    @InjectView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @InjectView(R.id.iv_verify_icon)
    ImageView ivVerifyIcon;
    @InjectView(R.id.et_verification_code)
    EditText etVerificationCode;
    @InjectView(R.id.btn_get_verification_code)
    Button btnGetVerificationCode;
    @InjectView(R.id.no_get_verify)
    TextView noGetVerify;
    @InjectView(R.id.push_mobile)
    TextView pushMobile;
    @InjectView(R.id.push_mobile_et)
    EditText pushMobileEt;
    @InjectView(R.id.next_bt)
    Button nextBt;
    @InjectView(R.id.login)
    TextView login;
    @InjectView(R.id.prototal_icon)
    ImageView prototal_icon;
    @InjectView(R.id.prototal_tv)
    TextView prototal_tv;
    @InjectView(R.id.prototal)
    TextView prototal;
    @InjectView(R.id.risk_tv)
    TextView risk_tv;
    @InjectView(R.id.chose)
    TextView chose;
    @InjectView(R.id.delete_phone_iv)
    ImageView delete_phone_iv;
    private String account;
    private boolean enableGetVerifyCode = true;
    private String verifyCode;
    private String reconmender;
    private boolean isAgreenProtocol;
    private CommonInfo commonInfo;
    private String userProtal;
    private String riskProtal;

    @Override
    protected void initializeData() {

        commonInfo = ConfigManager.getInstance().getCommonInfo(this);
        if (commonInfo != null) {
            userProtal = commonInfo.getLmw_product_pro();
            riskProtal=commonInfo.getLmw_risk_warning();
        }
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_verify_phone;
    }

    @Override
    protected void initializeView() {
        btnGetVerificationCode.setClickable(false);
        nextBt.setClickable(false);
        initTitle();
        setTextChangeListener();
        initRegistProtocol();
    }

    private void initRegistProtocol() {
        prototal_icon.setSelected(true);
        isAgreenProtocol = true;
    }

    private void initTitle() {
        barTitle.setText("手机验证");
        back.setText("返回");
        title_layout.setBackgroundColor(getResources().getColor(R.color.text_blue));
    }

    private void setTextChangeListener() {
        etPhoneNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                account =etPhoneNumber.getText()
                        .toString();
                if (TextUtils.isEmpty(account)||account.length()<11) {
                    btnGetVerificationCode.setClickable(false);
                    btnGetVerificationCode.setBackgroundResource(R.drawable.login_bg_nol);

                } else {
                    btnGetVerificationCode.setClickable(true);
                    btnGetVerificationCode.setBackgroundResource(R.drawable.login_bg_sel);
                }
            }
        });
        etVerificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    nextBt.setClickable(false);
                    nextBt.setBackgroundResource(R.drawable.login_bg_nol);
                } else {
                    nextBt.setClickable(true);
                    nextBt.setBackgroundResource(R.drawable.login_bg_sel);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                verifyCode = etVerificationCode.getText().toString();
            }
        });


    }

    private boolean isShow = false;

    @OnClick({R.id.back_layout, R.id.btn_get_verification_code,
            R.id.push_mobile, R.id.next_bt, R.id.login, R.id.prototal_tv, R.id.risk_tv, R.id.prototal_icon, R.id.delete_phone_iv,R.id.prototal,R.id.chose})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                finishActivity(PhoneVerifyActivity.this);
                break;
            case R.id.push_mobile:
            case R.id.chose:
                if (!isShow) {
                    pushMobileEt.setVisibility(View.VISIBLE);
                    isShow = true;
                } else {
                    pushMobileEt.setVisibility(View.GONE);
                    isShow = false;
                }

                break;
            case R.id.btn_get_verification_code:
                if (TextUtils.isEmpty(account)) {
                    showToast("请输入手机号码");
                    return;
                }

                if (account.contains(" ")||!StringUtils.isPhone(account)) {
                    showToast("手机号码格式错误");
                    return;
                }
                checkPhone();
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
                if (pushMobileEt.isShown()) {
                    reconmender = pushMobileEt.getText().toString();
                    if (!TextUtils.isEmpty(reconmender) && reconmender.contains(" ")) {
                        showToast("推荐人手机号码格式错误");
                        return;
                    }
                    if (reconmender.equals(account)){
                        showToast("推荐人手机号码不能与注册手机号相同");
                        return;
                    }


                }
                if (!isAgreenProtocol) {
                    showToast("请选择同意注册协议");
                    return;
                }
                startRegister();//跳到注册界面
                break;
            case R.id.login:
                startActivity(LoginActivity.class);
                break;
            case R.id.prototal_tv:
            case R.id.prototal:
                startUserPrototalPage();
                break;
            case R.id.prototal_icon:
                if (isAgreenProtocol) {
                    prototal_icon.setSelected(false);
                    isAgreenProtocol = false;
                    showToast("请选择同意注册协议");
                } else {
                    prototal_icon.setSelected(true);
                    isAgreenProtocol = true;
                }
                break;
            case R.id.risk_tv:
                startRistHintPage();
                break;
            case R.id.delete_phone_iv:
                etPhoneNumber.getText().clear();
                account = "";
                break;

        }
    }

    private void startRistHintPage() {
        Bundle bundle = new Bundle();
        bundle.putInt(WebViewActivity.URL_TYPE, WebViewActivity.URL_RISK_HINT);
        bundle.putString(WebViewActivity.URL, riskProtal);
        ActivityTools.switchActivity(this, WebViewActivity.class, bundle);

    }

    private void startUserPrototalPage() {
        Bundle bundle = new Bundle();
        bundle.putInt(WebViewActivity.URL_TYPE, WebViewActivity.URL_USER_DEAL);
        bundle.putString(WebViewActivity.URL, userProtal);
        ActivityTools.switchActivity(this, WebViewActivity.class, bundle);


    }

    private void startRegister() {
        showLoadingDialog();
        checkVerifyCode();
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
                            goSetPassward();//注册设置密码
                        } else
                            showToast(response.msg);
                    }

                    @Override
                    public void onFail(Throwable e) {
                        hideLoadingDialog();
                    }
                });

    }

    private void goSetPassward() {
        Bundle bundle = new Bundle();
        bundle.putString(PreferenceCongfig.PHONE, account);
        bundle.putString(PreferenceCongfig.VERIFY_CODE, verifyCode);
        bundle.putString(PreferenceCongfig.RECONMENDER, reconmender);
        bundle.putInt(PreferenceCongfig.FROM_POSITION_TO_SET_PSW, PreferenceCongfig.FROM_REGISTER_TO_SET_PSW);
        ActivityTools.switchActivity(this, SetPasswardtActivity.class, bundle);
    }

    /**
     * 校验手机号是否注册
     */
    private void checkPhone() {
        Singlton.getInstance(RegisterLogic.class).checkPhone(this, account,
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        int code = Integer.valueOf(response.code);
                        if (code == 0) {//手机号未注册
                            if (enableGetVerifyCode)
                                getVerifyCode();
                        } else if (code == 1) {//手机号已注册
                            Singlton.getInstance(PopWindowManager.class).
                                    showOneButtonDialog(mContext, "温馨提示", "该手机号已注册", "去登录", new OnDialogClickListener() {
                                        @Override
                                        public void onClick(int id, View v) {
                                            if (id == 2) {
                                                startActivity(LoginActivity.class);
                                            }
                                        }
                                    });

                        } else
                            showToast(response.msg);
                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });


    }

    /**
     * 获取验证码
     */
    private void getVerifyCode() {
        Singlton.getInstance(RegisterLogic.class).getVerifyCode(this, account, "00",
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        hideLoadingDialog();
                        int code = Integer.valueOf(response.code);
                        if (code == 0) {
                            showToast(R.string.verficode_tips);
                            enableGetVerifyCode = false;
                            btnGetVerificationCode.setBackgroundResource(R.drawable.login_bg_nol);
                            CountdownThread thread = new CountdownThread();
                            thread.setOnCountdownListener(PhoneVerifyActivity.this);
                            thread.start(); // 开始倒计时
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

    @Override
    public void OnCountdown(int count) {
        if (btnGetVerificationCode != null)
            btnGetVerificationCode.setText(count + getString(R.string.countdown_tip));
    }

    @Override
    public void OnCountdownFinish() {
        if (btnGetVerificationCode != null){
            btnGetVerificationCode.setText(getString(R.string.get_verify_code));
            btnGetVerificationCode.setBackgroundResource(R.drawable.login_bg_sel);
        }

        enableGetVerifyCode = true;
    }
}
