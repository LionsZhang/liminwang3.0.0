package com.example.administrator.lmw.login;

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
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResponse;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.register.RegisterLogic;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.CountdownThread;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.StringUtils;
import com.example.administrator.lmw.utils.ValidateUtil;

import butterknife.InjectView;
import butterknife.OnClick;

public class FindPasswardVerifyActivity extends BaseActivity implements CountdownThread.OnCountdownListener {


    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.title_layout)
    RelativeLayout title_layout;
    @InjectView(R.id.back)
    TextView back;
    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.back_layout)
    LinearLayout backLayout;
    @InjectView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @InjectView(R.id.delete_phone_iv)
    ImageView deletePhoneIv;
    @InjectView(R.id.iv_verify_icon)
    ImageView ivVerifyIcon;
    @InjectView(R.id.et_verification_code)
    EditText etVerificationCode;
    @InjectView(R.id.btn_get_verification_code)
    Button btnGetVerificationCode;
    @InjectView(R.id.next_bt)
    Button nextBt;
    @InjectView(R.id.no_get_verify)
    TextView noGetVerify;
    private String phone, verifyCode;
    private boolean enableGetVerifyCode = true;

    @Override
    protected void initializeData() {

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_find_password_verify_layout;
    }

    @Override
    protected void initializeView() {
        nextBt.setClickable(false);
        btnGetVerificationCode.setClickable(false);
        initBarTitle();
        setTextChangeListener();
    }

    private void initBarTitle() {
        barTitle.setText("验证手机号");
        back.setVisibility(View.GONE);
        barIconBack.setImageResource(R.drawable.nav_back);
        title_layout.setBackgroundColor(getResources().getColor(R.color.text_blue));

    }

    private void setTextChangeListener() {
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
      /*          if (TextUtils.isEmpty(s.toString().trim())) {
                    btnGetVerificationCode.setClickable(false);
                } else {
                    btnGetVerificationCode.setClickable(true);
                }*/
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                phone = etPhoneNumber.getText().toString();
                if (!TextUtils.isEmpty(phone)&&phone.length()==11){
                    btnGetVerificationCode.setClickable(true);
                    btnGetVerificationCode.setBackgroundResource(R.drawable.login_bg_sel);
                }else {
                    btnGetVerificationCode.setClickable(false);
                    btnGetVerificationCode.setBackgroundResource(R.drawable.login_bg_nol);
                }


            }
        });
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
                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(verifyCode)&&verifyCode.length()==6) {
                    nextBt.setClickable(true);
                    nextBt.setBackgroundResource(R.drawable.login_bg_sel);
                }else {
                    nextBt.setClickable(false);
                    nextBt.setBackgroundResource(R.drawable.login_bg_nol);
                }
            }
        });
    }

    @OnClick({R.id.back_layout, R.id.delete_phone_iv, R.id.btn_get_verification_code, R.id.next_bt, R.id.no_get_verify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                finish();
                break;
            case R.id.delete_phone_iv:
                etPhoneNumber.setText("");
                phone = "";
                break;
            case R.id.btn_get_verification_code:
                if (phone.contains(" ")||!StringUtils.isPhone(phone)) {
                    showToast("手机号码格式错误");
                    return;
                }
                checkPhone();//检验手机是否有效
                break;
            case R.id.next_bt://调到设置密码界面
                if (TextUtils.isEmpty(phone)) {
                    showToast("请输入手机号码");
                    return;
                }

                if (phone.contains(" ")||!StringUtils.isPhone(phone)) {
                    showToast("手机号码格式错误");
                    return;
                }
                if (TextUtils.isEmpty(verifyCode)) {
                    showToast("请输入验证码");
                    return;
                }

                if (!StringUtils.isNumberOrLetter(verifyCode)) {
                    showToast("验证码必须为六位");
                    return;
                }

                checkVerifyCode();
                break;
            case R.id.no_get_verify://无法接收验证码

                break;
        }
    }
    /**
     * 校验手机号是否注册
     */
    private void checkPhone() {
        Singlton.getInstance(RegisterLogic.class).checkPhone(this, phone,
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        int code=Integer.valueOf(response.code);
                        if (code == 1) {//手机号已注册
                            if (enableGetVerifyCode)
                                getVerifyCode();
                        } else {
                            showToast(response.msg);
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });


    }
    /**
     * 核对手机验证码
     */
    private void checkVerifyCode() {
        Singlton.getInstance(RegisterLogic.class).checkVerifyCode(this, phone, verifyCode,
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        int code=Integer.valueOf(response.code);
                        if (code == 0) {
                            Bundle bundle = new Bundle();
                            bundle.putString(PreferenceCongfig.VERIFY_CODE, verifyCode);
                            bundle.putString(PreferenceCongfig.PHONE, phone);
                            bundle.putInt(PreferenceCongfig.FROM_POSITION_TO_SET_PSW, PreferenceCongfig.FROM_LOGIN_TO_SET_PSW);
                            ActivityTools.switchActivity(FindPasswardVerifyActivity.this, SetPasswardtActivity.class, bundle);
                        } else
                            showToast(response.msg);
                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });

    }

    private void getVerifyCode() {
        Singlton.getInstance(RegisterLogic.class).getVerifyCode(this, phone, "02",new OnResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                int code=Integer.valueOf(response.code);
                if (code == 0) {
                    showToast(R.string.verficode_tips);
                    enableGetVerifyCode = false;
                    btnGetVerificationCode.setBackgroundResource(R.drawable.login_bg_nol);
                    CountdownThread thread = new CountdownThread();
                    thread.setOnCountdownListener(FindPasswardVerifyActivity.this);
                    thread.start(); // 开始倒计时
                } else {
                    showToast(response.msg);
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    @Override
    public void OnCountdown(int count) {
        if (btnGetVerificationCode!=null)
        btnGetVerificationCode.setText(count + getString(R.string.countdown_tip));
    }

    @Override
    public void OnCountdownFinish() {
        if (btnGetVerificationCode!=null){
            btnGetVerificationCode.setText(getString(R.string.get_verify_code));
            btnGetVerificationCode.setBackgroundResource(R.drawable.login_bg_sel);
        }

        enableGetVerifyCode = true;
    }
}
