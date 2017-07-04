package com.example.administrator.lmw.mine.seting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.example.administrator.lmw.login.LoginLogic;
import com.example.administrator.lmw.login.SetPasswardtActivity;
import com.example.administrator.lmw.login.UserInfoUtils;
import com.example.administrator.lmw.login.entity.LoginBean;
import com.example.administrator.lmw.login.entity.LoginEntity;
import com.example.administrator.lmw.login.entity.MineData;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.register.GesturePasswardActivity;
import com.example.administrator.lmw.register.RegisterLogic;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.StringUtils;

import butterknife.InjectView;
import butterknife.OnClick;

public class VerifyOldLoginPswActivity extends BaseActivity {

    @InjectView(R.id.title_layout)
    RelativeLayout title_layout;
    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.back_layout)
    LinearLayout backLayout;
    @InjectView(R.id.et_phone_number)
    EditText et_phone_number;
    @InjectView(R.id.et_psw)
    EditText etPsw;
    @InjectView(R.id.next_bt)
    Button next_bt;
    @InjectView(R.id.look_pw_iv_down)
    ImageView look_pw_iv_down;
    @InjectView(R.id.look_pw_iv)
    ImageView look_pw_iv;
    private String psw;
    private boolean isLookPsw = false;
    private Intent intent;
    private String phone;
    @Override
    protected void initializeData() {
        intent=getIntent();
        if (intent.hasExtra(PreferenceCongfig.PHONE))
            phone = intent.getStringExtra(PreferenceCongfig.PHONE);
    }


    @Override
    protected int getContentLayout() {
        return R.layout.activity_verify_old_login_password_layout;
    }

    @Override
    protected void initializeView() {
        next_bt.setClickable(false);
        initBarTitle();
        setTextChangeListener();
        barTitle.setText("验证原密码");
        next_bt.setText("下一步");
    }

    private void initBarTitle() {
        barIconBack.setImageResource(R.drawable.nav_back);
        title_layout.setBackgroundColor(getResources().getColor(R.color.text_blue));

    }

    private void setTextChangeListener() {
        et_phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //psw = textNumberListener(s, et_phone_number);
                psw=et_phone_number.getText().toString();
                if (!TextUtils.isEmpty(psw)&&psw.length()>=6 ) {
                    next_bt.setBackgroundResource(R.drawable.login_bg_sel);
                    next_bt.setClickable(true);
                }else {
                    next_bt.setBackgroundResource(R.drawable.login_bg_nol);
                    next_bt.setClickable(false);
                }
            }
        });

    }

    /**
     * 监听字数限制
     * by lionzhang
     */


    private String textNumberListener(Editable s, EditText et) {
        int selectionStart = et.getSelectionStart();
        int selectionEnd = et.getSelectionEnd();
        isShowLimit(s, 16, "请输入您的密码限制在16字符以内");
        if (isLimit) {
            s.delete(selectionStart - 1, selectionEnd);
            et.setText(s);
            et.setSelection(s.length());
        }

        return StringUtils.getStringNoCharacter(et.getText()
                .toString().trim());
    }


    /**
     * 是否限制00
     *
     * @param s
     * @param limit 限制大小
     * @param text  提示内容
     */
    private boolean isLimit, isShowTips;

    private void isShowLimit(Editable s, int limit, String text) {
        isLimit = StringUtils.getWordCountRegex(s.toString()) > limit;
        if (!isShowTips && isLimit) {
            isShowTips = true;
            showToast(text);
        }
    }

    private void setPswVisible() {
        if (!isLookPsw) {
            look_pw_iv.setImageResource(R.drawable.btn_login_pw_off);//不可见
            et_phone_number.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isLookPsw = true;
        } else {
            look_pw_iv.setImageResource(R.drawable.btn_login_pw_on);
            et_phone_number.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isLookPsw = false;
        }
    }
    @OnClick({R.id.back_layout, R.id.next_bt,R.id.look_pw_iv_down,R.id.look_pw_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                finish();
                break;
            case R.id.look_pw_iv:
                setPswVisible();
                break;
            case R.id.next_bt:
           /*     if (StringUtils.getWordCountRegex(psw) < 6) {
                    showToast("6-16位数字、字母、符号至少2种");
                    return;
                }*/
                verifyOldLoginPsw();
                break;
        }
    }

    //验证原密码   还没有接口  9/8
    private void verifyOldLoginPsw() {
        Singlton.getInstance(SetLogic.class).verifyOldLoginPsw(this, psw, new OnResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                int code=Integer.valueOf(response.code);
                if (code == 0) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(PreferenceCongfig.FROM_POSITION_TO_SET_PSW, PreferenceCongfig.FROM_SET_MOTIFY_LOGIN_PSW);
                    bundle.putString(PreferenceCongfig.OLD_LOGIN_PSW, psw);
                    bundle.putString(PreferenceCongfig.PHONE, phone);
                    ActivityTools.switchActivity(VerifyOldLoginPswActivity.this, SetPasswardtActivity.class, bundle);
                } else {
                    showToast(response.msg);
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }



}
