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
import com.example.administrator.lmw.entity.FreshMessageEvent;
import com.example.administrator.lmw.http.HttpUrl;
import com.example.administrator.lmw.http.LmwHttp;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.entity.LoginBean;
import com.example.administrator.lmw.login.entity.LoginEntity;
import com.example.administrator.lmw.login.entity.MineData;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.main.MainActivity;
import com.example.administrator.lmw.register.GesturePasswardActivity;
import com.example.administrator.lmw.register.PhoneVerifyActivity;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.MD5;
import com.example.administrator.lmw.utils.NetUtil;
import com.example.administrator.lmw.utils.PicassoManager;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Set;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class LoginActivity extends BaseActivity {


    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @InjectView(R.id.et_psw)
    EditText etPsw;
    @InjectView(R.id.look_pw_iv)
    ImageView look_pw_iv;
    @InjectView(R.id.fg_psw_tv)
    TextView fgPswTv;
    @InjectView(R.id.regist)
    TextView regist;
    @InjectView(R.id.back_layout)
    LinearLayout backLayout;
    @InjectView(R.id.ll_psw)
    RelativeLayout llPsw;
    @InjectView(R.id.next_bt)
    Button nextBt;
    @InjectView(R.id.back_layout)
    LinearLayout back_layout;
    @InjectView(R.id.et_picture_verify)
    EditText et_picture_verify;
    @InjectView(R.id.picture_verify)
    ImageView picture_verify;
    @InjectView(R.id.ll_verification_picture)
    RelativeLayout ll_verification_picture;
    @InjectView(R.id.v_line_verify)
    View v_line_verify;
    @InjectView(R.id.delete_phone_iv)
    ImageView delete_phone_iv;
    private String phone, psw;
    private boolean isLookPsw = false;
    private String lastAccount;
    private String pictureVerifyCode;
    private Intent intent;
    private boolean isSetGesturePsw;

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                case 0:
                    ALLog.e("Set tag and alias success"+alias);
                    break;

                case 6002:
                    ALLog.e("Failed to set alias and tags due to timeout. Try again after 60s.");
                    if (NetUtil.isConnected(mContext)) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setJpushAlis();
                            }
                        },1000 * 60);
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
        lastAccount = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_ACCOUNT, "");//保存账号
        isSetGesturePsw = SharedPreference.getInstance().getBoolean(mContext, PreferenceCongfig.APP_IS_SET_GESTURE_PSW, false);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initializeView() {
        nextBt.setClickable(false);
        ll_verification_picture.setVisibility(View.GONE);
        v_line_verify.setVisibility(View.GONE);
        initBarTitle();
        setPswVisible();
        setTextChangeListener();
    }

    private void setPswVisible() {
        if (!isLookPsw) {
            look_pw_iv.setImageResource(R.drawable.btn_login_pw_off);//不可见
            etPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isLookPsw = true;
        } else {
            look_pw_iv.setImageResource(R.drawable.btn_login_pw_on);
            etPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isLookPsw = false;
        }
    }

    private void initBarTitle() {
        barIconBack.setImageResource(R.drawable.btn_login_close);

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

                phone = etPhoneNumber.getText().toString();
                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(psw) && phone.length() == 11) {
                    nextBt.setClickable(true);
                    nextBt.setBackgroundResource(R.drawable.login_bg_sel);
                } else {
                    nextBt.setClickable(false);
                    nextBt.setBackgroundResource(R.drawable.login_bg_nol);
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
                psw = etPsw.getText().toString();
                // psw = textNumberListener(s, etPsw);
                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(psw) && phone.length() == 11) {
                    nextBt.setClickable(true);
                    nextBt.setBackgroundResource(R.drawable.login_bg_sel);
                } else {
                    nextBt.setClickable(false);
                    nextBt.setBackgroundResource(R.drawable.login_bg_nol);
                }
            }
        });
        et_picture_verify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                pictureVerifyCode = et_picture_verify.getText().toString();

            }
        });
    }

    @OnClick({R.id.ll_psw, R.id.next_bt, R.id.back_layout, R.id.regist, R.id.look_pw_iv, R.id.fg_psw_tv, R.id.picture_verify, R.id.delete_phone_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_psw:
                break;
            case R.id.delete_phone_iv:
                etPhoneNumber.getText().clear();
                phone = "";
                break;
            case R.id.back_layout:
                // startActivity(MainActivity.class);
                finishActivity(LoginActivity.this);
                break;
            case R.id.look_pw_iv:
                setPswVisible();
                break;
            case R.id.fg_psw_tv:
                startActivity(FindPasswardVerifyActivity.class);
                break;
            case R.id.regist:
                startActivity(PhoneVerifyActivity.class);
                break;
            case R.id.picture_verify:
                showPictureVerifyCodeUi();
                break;
            case R.id.next_bt:
                if (TextUtils.isEmpty(phone)) {
                    showToast("请输入手机号码");
                    return;
                }
                if (phone.contains(" ")||!StringUtils.isPhone(phone)) {
                    showToast("手机号码格式错误");
                    return;
                }
            /*    if (StringUtils.getWordCountRegex(psw) < 6) {
                    showToast("密码必须设置六位以上");
                    return;
                }*/
                if (et_picture_verify.isShown() && TextUtils.isEmpty(pictureVerifyCode)) {
                    showToast("请输入图形验证码");
                    return;
                }
                if (!TextUtils.isEmpty(pictureVerifyCode))
                    checkPictureVerifyCode();
                else {
                    login();
                }
                break;
        }
    }

    private void checkPictureVerifyCode() {
        showLoadingDialog();
        Singlton.getInstance(LoginLogic.class).checkPictureVerifyCode(this, pictureVerifyCode, noncestr, new OnResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                hideLoadingDialog();
                int code = Integer.valueOf(response.code);
                if (code == 0) {
                    login();
                } else {
                    showPictureVerifyCodeUi();
                    showToast(response.msg);
                }

            }

            @Override
            public void onFail(Throwable e) {
                hideLoadingDialog();
            }
        });


    }

    private String token;

    private void login() {
        showLoadingDialog();
        Singlton.getInstance(LoginLogic.class).login(mContext, phone, psw, new OnResponseListener<LoginEntity>() {
            @Override
            public void onSuccess(LoginEntity response) {
                int code = Integer.valueOf(response.code);
                if (code == 0) {//登录成功
                    setJpushAlis();//调用JPush API设置Alias
                    if (response.getData() != null) {
                        LoginBean loginBean = response.getData();
                        token = loginBean.token;
                        SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_TOKEN, token);//保存token
                        SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_ACCOUNT, phone);//保存账号
                        SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_PSW, psw);//保存密码
                        PreferenceCongfig.isCancelLogin=false;
                        getUserInfo();
                    }
                    EventBus.getDefault().post(new FreshFloatIconEvent(FreshFloatIconEvent.FRESH_ICON_AND_POP));
                    EventBus.getDefault().post(new FreshMessageEvent());
                } else if (code == 2) {
                    hideLoadingDialog();
                    showToast(response.msg);
                    if (response.getData() != null) {
                        LoginBean loginBean = response.getData();
                        if (loginBean.failCount == 2) {
                            showPictureVerifyCodeUi();
                        }
                    }
                } else {
                    hideLoadingDialog();
                    showToast(response.msg);
                }


            }

            @Override
            public void onFail(Throwable e) {
                hideLoadingDialog();
            }
        });
    }

    private void setJpushAlis() {
         //调用JPush API设置Alias
        JPushInterface.setAliasAndTags(mContext, phone, null, mAliasCallback);
    }

    private String noncestr;

    private void showPictureVerifyCodeUi() {
        noncestr = MD5.getMD5(System.currentTimeMillis() + "");
        String url = LmwHttp.getInstance().getPictureVerifyCodeUrl(HttpUrl.GET_PICTURE_VERIFY, noncestr);
        if (!TextUtils.isEmpty(url))
            PicassoManager.getInstance().display(this, picture_verify, url);
        ll_verification_picture.setVisibility(View.VISIBLE);
        v_line_verify.setVisibility(View.VISIBLE);
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

    private void getMineDataInfo() {
        Singlton.getInstance(LoginLogic.class).getMineDataInfo(this, token, new OnResponseListener<MineData>() {
            @Override
            public void onSuccess(MineData response) {
                hideLoadingDialog();
                PreferenceCongfig.isLogin = true;
                if (response != null) {
                    UserInfoUtils.getInstance().setMineData(mContext, response);
                    if (!lastAccount.equals(phone) || !isSetGesturePsw) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(PreferenceCongfig.SET_GESTURE_PSW, PreferenceCongfig.set_gesture_psw);
                        if (intent.hasExtra(PreferenceCongfig.DEBENTURE_TO_LOGIN))
                            bundle.putInt(PreferenceCongfig.DEBENTURE_TO_LOGIN, PreferenceCongfig.debenture_to_login);
                        if (intent.hasExtra(PreferenceCongfig.PURCHASE_TO_LOGIN))
                            bundle.putInt(PreferenceCongfig.PURCHASE_TO_LOGIN, PreferenceCongfig.purchase_to_login);
                        ActivityTools.switchActivity(LoginActivity.this, GesturePasswardActivity.class, bundle);
                    } else if (fromType == PreferenceCongfig.purchase_to_login || fromType == PreferenceCongfig.debenture_to_login) {
                        finishActivity(LoginActivity.this);

                    } else if (PreferenceCongfig.loginType == PreferenceCongfig.FROM_FINANCE_BANNAER_TO_LOGIN) {
                        PreferenceCongfig.loginType = -1;
                        finishActivity(LoginActivity.this);
                    } else
                        startActivity(MainActivity.class);
                }
            }

            @Override
            public void onFail(Throwable e) {
                hideLoadingDialog();
            }
        });
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

    private int fromType = -1;

    @Override
    protected void onResume() {
        super.onResume();
        intent = getIntent();
        if (intent.hasExtra(PreferenceCongfig.DEBENTURE_TO_LOGIN))
            fromType = intent.getIntExtra(PreferenceCongfig.DEBENTURE_TO_LOGIN, 0);
        if (intent.hasExtra(PreferenceCongfig.PURCHASE_TO_LOGIN))
            fromType = intent.getIntExtra(PreferenceCongfig.PURCHASE_TO_LOGIN, 0);
    }


}
