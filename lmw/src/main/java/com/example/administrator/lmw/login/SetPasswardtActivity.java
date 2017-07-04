package com.example.administrator.lmw.login;

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
import com.example.administrator.lmw.entity.FreshFloatIconEvent;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.entity.LoginBean;
import com.example.administrator.lmw.login.entity.LoginEntity;
import com.example.administrator.lmw.login.entity.MineData;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.mine.seting.SetLogic;
import com.example.administrator.lmw.register.GesturePasswardActivity;
import com.example.administrator.lmw.register.RegisterLogic;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.NetUtil;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Set;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class SetPasswardtActivity extends BaseActivity {

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
    private String psw, confirmPsw, verifyCode, phone, reconmender;
    private int fromType;
    private boolean isLookPsw = false;
    private boolean isLookPswdown = false;
    private String oldLoginPsw;
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                case 0:
                    ALLog.e("Set tag and alias success" + alias);
                    break;

                case 6002:
                    ALLog.e("Failed to set alias and tags due to timeout. Try again after 60s.");
                    if (NetUtil.isConnected(mContext)) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setJpushAlis();
                            }
                        }, 1000 * 60);
                        //  mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        ALLog.e("No network");
                    }
                    break;

                default:
                    ALLog.e("Failed with errorCode = " + code);
            }

        }

    };

    @Override
    protected void initializeData() {
        initPara();
    }

    private void initPara() {
        Intent intent = getIntent();
        if (intent.hasExtra(PreferenceCongfig.VERIFY_CODE))
            verifyCode = intent.getStringExtra(PreferenceCongfig.VERIFY_CODE);
        if (intent.hasExtra(PreferenceCongfig.PHONE))
            phone = intent.getStringExtra(PreferenceCongfig.PHONE);
        if (intent.hasExtra(PreferenceCongfig.RECONMENDER))
            reconmender = intent.getStringExtra(PreferenceCongfig.RECONMENDER);//注册
        if (intent.hasExtra(PreferenceCongfig.OLD_LOGIN_PSW))
            oldLoginPsw = intent.getStringExtra(PreferenceCongfig.OLD_LOGIN_PSW);//修改密码的旧密码
        if (intent.hasExtra(PreferenceCongfig.FROM_POSITION_TO_SET_PSW))
            fromType = intent.getIntExtra(PreferenceCongfig.FROM_POSITION_TO_SET_PSW, -1);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_find_password_set_layout;
    }

    @Override
    protected void initializeView() {
        next_bt.setClickable(false);
        initBarTitle();
        setTextChangeListener();
        switch (fromType) {
            case PreferenceCongfig.FROM_LOGIN_TO_SET_PSW:
                barTitle.setText("新密码");
                next_bt.setText("完成");
                break;
            case PreferenceCongfig.FROM_REGISTER_TO_SET_PSW:
                barTitle.setText("设置密码");
                next_bt.setText("下一步");
                break;
            case PreferenceCongfig.FROM_SET_MOTIFY_LOGIN_PSW:
                barTitle.setText("修改密码");
                next_bt.setText("完成");
                break;
        }
    }

    private void initBarTitle() {
        barIconBack.setImageResource(R.drawable.nav_back);
        title_layout.setBackgroundColor(getResources().getColor(R.color.text_blue));

    }

    private void setTextChangeListener() {
        et_phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ALLog.e("s" + s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                psw = textNumberListener(s, et_phone_number);
                if (!TextUtils.isEmpty(psw) && !TextUtils.isEmpty(confirmPsw) && psw.length() >= 6 && confirmPsw.length() >= 6) {
                    next_bt.setBackgroundResource(R.drawable.login_bg_sel);
                    next_bt.setClickable(true);
                } else {
                    next_bt.setBackgroundResource(R.drawable.login_bg_nol);
                    next_bt.setClickable(false);
                }
            }
        });
        etPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                confirmPsw = textNumberListener(s, etPsw);
                if (!TextUtils.isEmpty(psw) && !TextUtils.isEmpty(confirmPsw) && psw.length() >= 6 && confirmPsw.length() >= 6) {
                    next_bt.setBackgroundResource(R.drawable.login_bg_sel);
                    next_bt.setClickable(true);
                } else {
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

        return et.getText()
                .toString();
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

    private void setPswdownVisible() {
        if (!isLookPswdown) {
            look_pw_iv_down.setImageResource(R.drawable.btn_login_pw_off);//不可见
            etPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isLookPswdown = true;
        } else {
            look_pw_iv_down.setImageResource(R.drawable.btn_login_pw_on);
            etPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isLookPswdown = false;
        }
    }

    @OnClick({R.id.back_layout, R.id.next_bt, R.id.look_pw_iv_down, R.id.look_pw_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                finishActivity(SetPasswardtActivity.this);
                break;
            case R.id.look_pw_iv_down:
                setPswdownVisible();
                break;
            case R.id.look_pw_iv:
                setPswVisible();
                break;
            case R.id.next_bt:
                if (!StringUtils.isNumberAndLetter(psw) || StringUtils.getWordCountRegex(psw) < 6 || psw.contains(" ") || StringUtils.isContainChinese(psw)) {
                    showToast("由6-16位数字、字母、符号中的至少2种组成，不含空格");
                    return;
                }

                if (!psw.equals(confirmPsw)) {
                    showToast("两次输入的密码不一致，请再次确认");
                    return;
                }
                switch (fromType) {
                    case PreferenceCongfig.FROM_LOGIN_TO_SET_PSW:
                        resetLoginPsw();
                        break;
                    case PreferenceCongfig.FROM_SET_MOTIFY_LOGIN_PSW:
                        motifyLoginPsw();
                        break;
                    case PreferenceCongfig.FROM_REGISTER_TO_SET_PSW:
                        register();
                        break;
                }

                break;
        }
    }

    private void motifyLoginPsw() {

        Singlton.getInstance(SetLogic.class).motifyLoginPsw(this, oldLoginPsw, confirmPsw, new OnResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                int code = Integer.valueOf(response.code);
                if (code == 0) {
                    showToast("修改密码成功");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            login();
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
                } else
                    showToast(response.msg);
            }

            @Override
            public void onFail(Throwable e) {

            }
        });


    }

    //重置密码
    private void resetLoginPsw() {
        Singlton.getInstance(LoginLogic.class).resetLoginPsw(this, psw, confirmPsw, verifyCode, phone, new OnResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                int code = Integer.valueOf(response.code);
                if (code == 0) {
                    showToast("设置新密码成功");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            login();
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
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    //注册
    private void register() {
        Singlton.getInstance(RegisterLogic.class).register(this, phone, confirmPsw, verifyCode, reconmender,
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        int code = Integer.valueOf(response.code);
                        if (code == 0) {
                            PreferenceCongfig.isRegister = true;
                            login();
                        } else
                            showToast(response.msg);
                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });
    }

    private String token;

    private void login() {
        Singlton.getInstance(LoginLogic.class).login(mContext, phone, confirmPsw, new OnResponseListener<LoginEntity>() {
            @Override
            public void onSuccess(LoginEntity response) {
                int code = Integer.valueOf(response.code);
                if (code == 0) {//登录成功
                    setJpushAlis();
                    EventBus.getDefault().post(new FreshFloatIconEvent(FreshFloatIconEvent.FRESH_ICON_AND_POP));
                    if (response.getData() != null) {
                        LoginBean loginBean = response.getData();
                        token = loginBean.token;
                        SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_TOKEN, token);
                        SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_ACCOUNT, phone);//保存账号
                        SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_PSW, confirmPsw);//保存密码
                        PreferenceCongfig.isCancelLogin=false;
                        getUserInfo();
                    }

                } else
                    showToast(response.msg);
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    private void setJpushAlis() {
        //调用JPush API设置Alias
        JPushInterface.setAliasAndTags(mContext, phone, null, mAliasCallback);
    }

    private void getUserInfo() {
        Singlton.getInstance(LoginLogic.class).getUserInfo(mContext, token, new OnResponseListener<UserInfo>() {
            @Override
            public void onSuccess(UserInfo response) {
                int code = Integer.valueOf(response.code);
                if (code == 0) {
                    if (response != null) {
                        UserInfoUtils.getInstance().setUserInfo(mContext, response);
                    }
                    getMineDataInfo();
                }

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    private void getMineDataInfo() {
        Singlton.getInstance(LoginLogic.class).getMineDataInfo(this, token, new OnResponseListener<MineData>() {
            @Override
            public void onSuccess(MineData response) {
                PreferenceCongfig.isLogin = true;
                if (response != null) {
                    UserInfoUtils.getInstance().setMineData(mContext, response);
                    Bundle bundle = new Bundle();
                    bundle.putInt(PreferenceCongfig.SET_GESTURE_PSW, PreferenceCongfig.set_gesture_psw);
                    switch (fromType) {
                        case PreferenceCongfig.FROM_LOGIN_TO_SET_PSW:
                            bundle.putInt(PreferenceCongfig.FROM_POSITION_TO_SET_PSW, PreferenceCongfig.FROM_LOGIN_TO_SET_PSW);
                            ActivityTools.switchActivity(SetPasswardtActivity.this, GesturePasswardActivity.class, bundle);
                            break;
                        case PreferenceCongfig.FROM_REGISTER_TO_SET_PSW:
                            bundle.putInt(PreferenceCongfig.FROM_POSITION_TO_SET_PSW, PreferenceCongfig.FROM_REGISTER_TO_SET_PSW);
                            ActivityTools.switchActivity(SetPasswardtActivity.this, GesturePasswardActivity.class, bundle);
                            break;
                        case PreferenceCongfig.FROM_SET_MOTIFY_LOGIN_PSW:
                            bundle.putInt(PreferenceCongfig.FROM_POSITION_TO_SET_PSW, PreferenceCongfig.FROM_SET_MOTIFY_LOGIN_PSW);
                            ActivityTools.switchActivity(SetPasswardtActivity.this, GesturePasswardActivity.class, bundle);
                            break;
                    }

                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

}
