package com.example.administrator.lmw.mine.seting;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.config.DespositAccountManager;
import com.example.administrator.lmw.config.DespositOperate;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseEvent;
import com.example.administrator.lmw.entity.BaseResponse;
import com.example.administrator.lmw.entity.BindSuccessEvent;
import com.example.administrator.lmw.entity.CommonInfo;
import com.example.administrator.lmw.entity.FreshFloatIconEvent;
import com.example.administrator.lmw.entity.SetTradePswEvent;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.inteface.ViewOnClick;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.login.UserInfoUtils;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.login.entity.UserInfoBean;
import com.example.administrator.lmw.main.MainActivity;
import com.example.administrator.lmw.main.WebViewActivity;
import com.example.administrator.lmw.mine.invite.InviteFriendsActivity;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.mine.seting.entity.SetData;
import com.example.administrator.lmw.mine.seting.entity.SetDataBean;
import com.example.administrator.lmw.register.GesturePasswardActivity;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.NetUtil;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.view.SettingNextItemNotIconView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Set;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by lion on 2016/8/24.
 */
public class SetingActivity extends BaseActivity {
    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.title_layout)
    RelativeLayout title_layout;
    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.back_layout)
    LinearLayout backLayout;
    @InjectView(R.id.name_set_info)
    SettingNextItemNotIconView nameSet;
    @InjectView(R.id.card_set)
    SettingNextItemNotIconView cardSet;
    @InjectView(R.id.login_pw)
    SettingNextItemNotIconView loginPw;
    @InjectView(R.id.charge_set)
    SettingNextItemNotIconView chargeSet;
    @InjectView(R.id.hand_pw)
    SettingNextItemNotIconView handPw;
    @InjectView(R.id.vip_center)
    SettingNextItemNotIconView vip_center;
    @InjectView(R.id.qr_code)
    SettingNextItemNotIconView qr_code;
    @InjectView(R.id.tv_logout)
    TextView tvLogout;
    @InjectView(R.id.esignature_authorize)
    SettingNextItemNotIconView esignature_authorize;
    @InjectView(R.id.bank_manage_item)
    SettingNextItemNotIconView bank_manage_item;
    @InjectView(R.id.risk_tip)
    SettingNextItemNotIconView risk_tip;
    private UserInfo userInfo;
    private UserInfoBean userInfoBean;
    private String token;
    private String phone;
    private SetDataBean setDataBean;
    private String license;
    public final static String SET_DATA = "set_data";
    private boolean isSetGesturePsw;
    private CommonInfo commonInfo;
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
                                clearJpushAlis();
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
        EventBus.getDefault().register(this);
        commonInfo = ConfigManager.getInstance().getCommonInfo(mContext);
        token = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, "");
        isSetGesturePsw = SharedPreference.getInstance().getBoolean(mContext, PreferenceCongfig.APP_IS_SET_GESTURE_PSW, false);

    }

    private void inintPara() {
        userInfo = UserInfoUtils.getInstance().getUserInfo(this);
        if (userInfo != null)
            userInfoBean = userInfo.getData();
    }

    /**
     * 请求用户数据获取银行卡号
     */
    private void requestSetData() {
        Singlton.getInstance(SetLogic.class).getSetData(this, token, new OnResponseListener<SetData>() {
            @Override
            public void onSuccess(SetData response) {
                hideLoadingDialog();
                if (response != null) {
                    int code = Integer.valueOf(response.code);
                    if (code == 0) {
                        setDataBean = response.getData();
                        license = setDataBean.getIdNo();
                        phone = setDataBean.getMobileWhole();
                        setView();
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

            }

            @Override
            public void onFail(Throwable e) {
                hideLoadingDialog();
            }

        });
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_setting;
    }


    @Override
    protected void initializeView() {
        showLoadingDialog();
        initBarTitle();
        initQrCode();
    }

    @Override
    protected void onResume() {
        super.onResume();
        inintPara();
        requestSetData();

    }

    private void initRiskCamuluate() {
        risk_tip.setOnRightTextOnclick(new SettingNextItemNotIconView.RigthTextViewListener() {

            @Override
            public void onRightTextView(TextView v) {

                if (!TextUtils.isEmpty(setDataBean.getRiskCamuluateGrade()))
                    v.setText(setDataBean.getRiskCamuluateGrade());

            }

            @Override
            public void onClick(View v) {
                if (Singlton.getInstance(DespositAccountManager.class).isOpenDesposit(mContext)) {
                //    if (!TextUtils.equals(userInfoBean.getIsRiskCamuluate(), PreferenceCongfig.RISK_CACULATE_ALREADY))
                        goToRiskCamuluate();
                }


            }
        });


    }

    private void goToRiskCamuluate() {
        Bundle bundle = new Bundle();
        if (commonInfo != null && !TextUtils.isEmpty(commonInfo.getRiskCamuluateUrl())) {
            bundle.putString(WebViewMore.INTENT_KEY_URL, commonInfo.getRiskCamuluateUrl());
            bundle.putString(WebViewMore.INTENT_KEY_TITLE, "风险评估");
        }
        ActivityTools.switchActivity(mContext, WebViewMore.class, bundle);
    }

    private void initQrCode() {
        qr_code.setOnRightTextOnclick(new SettingNextItemNotIconView.RigthTextViewListener() {

            @Override
            public void onRightTextView(TextView v) {


            }

            @Override
            public void onClick(View v) {
                //跳到邀请好友
                startActivity(InviteFriendsActivity.class);
            }
        });


    }

    private void initVipCenter() {
        vip_center.setOnRightTextOnclick(new SettingNextItemNotIconView.RigthTextViewListener() {

            @Override
            public void onRightTextView(TextView v) {
                if (setDataBean != null && !TextUtils.isEmpty(setDataBean.getMemberLevelName()))
                    v.setText(setDataBean.getMemberLevelName());

            }

            @Override
            public void onClick(View v) {
                //跳到会员中心
                Bundle bundle = new Bundle();
                if (setDataBean != null && !TextUtils.isEmpty(setDataBean.getMemberCenterUrl())) {
                    bundle.putString(WebViewMore.INTENT_KEY_URL, setDataBean.getMemberCenterUrl());
                    bundle.putString(WebViewMore.INTENT_KEY_TITLE, "会员中心");
                }
                ActivityTools.switchActivity(SetingActivity.this, WebViewMore.class, bundle);

            }
        });

    }

    private void setView() {
        setUserName();
        setBankCard();
        setLoginPw();
        setChargePw();
        setHandPw();
        initVipCenter();
        setEsignatureAuthorize();
        initRiskCamuluate();
        setBankManageAccount();

        if (setDataBean != null && TextUtils.equals(setDataBean.getUseFdd(), "1")) {
            esignature_authorize.setVisibility(View.VISIBLE);
        } else {
            esignature_authorize.setVisibility(View.GONE);
        }

    }

    private void setBankManageAccount() {
        bank_manage_item.setOnRightTextOnclick(new SettingNextItemNotIconView.RigthTextViewListener() {
            @Override
            public void onRightTextView(TextView v) {
                if (TextUtils.isEmpty(userInfoBean.getOpenDpStatus()))
                    return;
                if (TextUtils.equals(userInfoBean.getOpenDpStatus(), PreferenceCongfig.OPEN_DESPONSITY_PASS)) {
                    if (!TextUtils.isEmpty(setDataBean.getDepositAccount()))
                        v.setText(setDataBean.getDepositAccount());
                } else {
                    if (!TextUtils.isEmpty(userInfoBean.getOpenDpStatusDesc()))
                        v.setText(userInfoBean.getOpenDpStatusDesc());
                }

            }

            @Override
            public void onClick(View v) {
                if (Singlton.getInstance(DespositAccountManager.class).isOpenDesposit(mContext)) {
                    //跳到存管界面
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(SET_DATA, setDataBean);
                    ActivityTools.switchActivity(SetingActivity.this, AccountManageActivity.class, bundle);
                }
            }

        });
    }


    private void setEsignatureAuthorize() {
        esignature_authorize.setOnRightTextOnclick(
                new SettingNextItemNotIconView.RigthTextViewListener() {
                    @Override
                    public void onRightTextView(TextView v) {

                        if (TextUtils.equals(userInfoBean.getAuthorize(), PreferenceCongfig.IS_ESIGNATURE_AUTHORIZE)) {
                            v.setText("查看授权合同");
                            v.setTextColor(getResources().getColor(R.color.text_gray));

                        } else if (TextUtils.equals(userInfoBean.getAuthorize(), PreferenceCongfig.IS_NOT_ESIGNATURE_AUTHORIZE)) {

                            v.setText("立即授权");
                            v.setTextColor(getResources().getColor(R.color.text_balck));
                        }
                    }

                    @Override
                    public void onClick(View v) {
                        if (TextUtils.equals(userInfoBean.getAuthorize(), PreferenceCongfig.IS_ESIGNATURE_AUTHORIZE)) {
                            Bundle bundle = new Bundle();
                            bundle.putInt(WebViewActivity.URL_TYPE, WebViewActivity.URL_ESIGNATURE_AUTH);
                            bundle.putString(WebViewActivity.URL, setDataBean.getAuthViewUrl());
                            ActivityTools.switchActivity(SetingActivity.this, WebViewActivity.class, bundle);//查看授权合同
                        } else if (TextUtils.equals(userInfoBean.getAuthorize(), PreferenceCongfig.IS_NOT_ESIGNATURE_AUTHORIZE)
                                && Singlton.getInstance(DespositAccountManager.class).isOpenDesposit(mContext)) {//授权
                            //  startActivity(EsignatureActivity.class);
                            //跳到电子授权页
                            Bundle bundle = new Bundle();
                            if (commonInfo != null && !TextUtils.isEmpty(commonInfo.getEsignature_authorize_url())) {
                                bundle.putString(WebViewMore.INTENT_KEY_URL, commonInfo.getEsignature_authorize_url());
                            }
                            bundle.putString(WebViewMore.INTENT_KEY_TITLE, "电子签名授权");
                            ActivityTools.switchActivity(SetingActivity.this, WebViewMore.class, bundle);

                        }
                    }
                }
        );
    }

    private void setHandPw() {
        handPw.setOnRightTextOnclick(
                new SettingNextItemNotIconView.RigthTextViewListener() {
                    @Override
                    public void onRightTextView(TextView v) {
                        if (isSetGesturePsw)
                            v.setText("修改");
                        else
                            v.setText("未设置");
                    }

                    @Override
                    public void onClick(View v) {
                        if (isSetGesturePsw) {
                            Singlton.getInstance(PopWindowManager.class).
                                    showTradePswDialog(mContext, "我忘记手势设置了", "我记得原手势设置", "取消", new ViewOnClick() {
                                                @Override
                                                public void onClick(int msgID, View v, Object... obj) {
                                                    if (msgID == 0) {//重置手势密码
                                                        Bundle bundle = new Bundle();
                                                        bundle.putInt(PreferenceCongfig.RESET_GESTURE_PSW, PreferenceCongfig.reset_gesture_psw);
                                                        bundle.putString(PreferenceCongfig.LICENCE, license);
                                                        ActivityTools.switchActivity(SetingActivity.this, ResetTradPswVerifyActivity.class, bundle);


                                                    } else if (msgID == 1) {//修改手势密码
                                                        Bundle bundle = new Bundle();
                                                        bundle.putInt(PreferenceCongfig.MOTIFY_GESTURE_PSW, PreferenceCongfig.motify_gesture_psw);
                                                        bundle.putInt(PreferenceCongfig.FROM_POSITION_TO_SET_PSW, PreferenceCongfig.FROM_SET_MOTIFY_GESTURE_PSW);
                                                        ActivityTools.switchActivity(SetingActivity.this, GesturePasswardActivity.class, bundle);
                                                    }
                                                }
                                            }
                                    );
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putInt(PreferenceCongfig.SET_GESTURE_PSW, PreferenceCongfig.set_gesture_psw);
                            bundle.putInt(PreferenceCongfig.FROM_POSITION_TO_SET_PSW, PreferenceCongfig.FROM_SET_SET_GESTURE_PSW);
                            ActivityTools.switchActivity(SetingActivity.this, GesturePasswardActivity.class, bundle);
                        }
                    }
                }
        );
    }

    private void setChargePw() {
        chargeSet.setOnRightTextOnclick(
                new SettingNextItemNotIconView.RigthTextViewListener() {

                    @Override
                    public void onRightTextView(TextView v) {
                        if (PreferenceCongfig.IS_SET_TRADEPASSWD == userInfoBean.getIsSetTradepasswd())
                            v.setText("修改");
                        else
                            v.setText("未设置");
                    }

                    @Override
                    public void onClick(View v) {
                        if (Singlton.getInstance(DespositAccountManager.class).isOpenDesposit(mContext))
                            Singlton.getInstance(DespositAccountManager.class).despositOperate(mContext, DespositOperate.TRANSACTION_PW);
                    }

                }

        );


    }

    private void setLoginPw() {
        loginPw.setOnRightTextOnclick(new SettingNextItemNotIconView.RigthTextViewListener() {//修改登录密码

            @Override
            public void onRightTextView(TextView v) {
                v.setText("修改");
            }

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(PreferenceCongfig.PHONE, phone);
                ActivityTools.switchActivity(SetingActivity.this, VerifyOldLoginPswActivity.class, bundle);//修改密码

            }
        });


    }

    private void setBankCard() {
        cardSet.setOnRightTextOnclick(new SettingNextItemNotIconView.RigthTextViewListener() {

            @Override
            public void onRightTextView(TextView v) {
                if (PreferenceCongfig.IS_BIND_BANKCARD == userInfoBean.getIsBindBankCard()) {//已绑卡
                    if (!TextUtils.isEmpty(setDataBean.getBankName()) && !TextUtils.isEmpty(setDataBean.getBankCardNo()))
                        v.setText(String.format(getString(R.string.set_bind_card),
                                setDataBean.getBankName(), setDataBean.getBankCardNo()));
                } else if (PreferenceCongfig.IS_NOT_BIND_BANKCARD == userInfoBean.getIsBindBankCard())
                    v.setText("未绑卡");

            }

            @Override
            public void onClick(View v) {
                if (Singlton.getInstance(DespositAccountManager.class).isOpenDesposit(mContext)) {
                    if (PreferenceCongfig.IS_BIND_BANKCARD == userInfoBean.getIsBindBankCard()) {//已绑卡
                        //跳到已绑卡界面
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(SET_DATA, setDataBean);
                        ActivityTools.switchActivity(SetingActivity.this, BindBankSuccussActivity.class, bundle);

                    } else if (PreferenceCongfig.IS_NOT_BIND_BANKCARD == userInfoBean.getIsBindBankCard()
                            || PreferenceCongfig.IS_REBIND_BANKCARD == userInfoBean.getIsBindBankCard()) {//需重新绑卡或者未绑卡//跳到绑卡界面
                        // startActivity(BindBankActivity.class);
                        Singlton.getInstance(DespositAccountManager.class).despositOperate(mContext, DespositOperate.BIND_BANKCARD);

                    }


                }
            }
        });


    }

    private void setUserName() {
        nameSet.setOnRightTextOnclick(new SettingNextItemNotIconView.RigthTextViewListener() {

            @Override
            public void onRightTextView(TextView v) {
                if (setDataBean != null && !TextUtils.isEmpty(setDataBean.getMobile()))
                    nameSet.setTitle(setDataBean.getMobile());
                if (PreferenceCongfig.IS_REAL_NAME_AUTH == userInfoBean.getIsRealnameAuth()) {
                    if (setDataBean != null && !TextUtils.isEmpty(setDataBean.getCustomerSurname())) {
                        nameSet.setTitleRightTextColor(R.color.text_black_pre);
                        nameSet.setTitleRightText(setDataBean.getCustomerSurname());
                        nameSet.setTitleTextColor(getResources().getColor(R.color.text_black_pre));
                    }
                } else {
                    nameSet.setTitleRightText("未认证");
                    nameSet.setTitleRightTextColor(R.color.text_black_pre);
                    nameSet.setTitleTextColor(getResources().getColor(R.color.text_gray));
                }
                nameSet.showLeftDivider(true);

            }

            @Override
            public void onClick(View v) {

                if (Singlton.getInstance(DespositAccountManager.class).isOpenDesposit(mContext)) {
                    //跳到用户资料界面
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(SET_DATA, setDataBean);
                    ActivityTools.switchActivity(SetingActivity.this, UserInfoActivity.class, bundle);
                }
            }
        });


    }

    private void initBarTitle() {
        barTitle.setText("账户设置");
        barIconBack.setImageResource(R.drawable.nav_back);
        title_layout.setBackgroundColor(getResources().getColor(R.color.text_blue));

    }


    @OnClick({R.id.back_layout, R.id.tv_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                startActivity(MainActivity.class);
                break;
            case R.id.tv_logout:
                Singlton.getInstance(PopWindowManager.class).
                        showTwoButtonDialog(mContext, null, "您确定要退出当前账号吗", "取消", "确定", new OnDialogClickListener() {
                            @Override
                            public void onClick(int id, View v) {
                                if (id == 1) {
                                    showLoadingDialog();
                                    Singlton.getInstance(SetLogic.class).logout(mContext, new OnResponseListener<BaseResponse>() {
                                        @Override
                                        public void onSuccess(BaseResponse response) {
                                            hideLoadingDialog();
                                            int code = Integer.valueOf(response.code);
                                            if (code == 0) {
                                                clearJpushAlis();
                                                finishActivity(SetingActivity.this);
                                                startActivity(LoginActivity.class);
                                                Singlton.getInstance(DespositAccountManager.class).onDestroy();
                                                UserInfoUtils.getInstance().clearMineData(SetingActivity.this);
                                                UserInfoUtils.getInstance().clearUserInfo(SetingActivity.this);
                                                SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_TOKEN, "");
                                                SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_PSW, "");
                                                PreferenceCongfig.isLogin = false;
                                                PreferenceCongfig.isCancelLogin = true;
                                                EventBus.getDefault().post(new FreshFloatIconEvent(FreshFloatIconEvent.FRESH_ICON));
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

                            }
                        });
                break;
        }
    }

    private void clearJpushAlis() {
        //调用JPush API设置Alias
        JPushInterface.setAliasAndTags(mContext, "", null, mAliasCallback);

    }

    /**
     * @param baseEvent void
     * @auther lion
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent baseEvent) {
        if (baseEvent != null) {
            if (baseEvent instanceof BindSuccessEvent || baseEvent instanceof SetTradePswEvent) {
                inintPara();
                requestSetData();
           /*     Singlton.getInstance(PopWindowManager.class).showOneButtonDialog(mContext, "温馨提示", getString(R.string.txt_risk_camuate_dialog), "我要评测", new OnDialogClickListener() {
                    @Override
                    public void onClick(int id, View v) {
                        //  goToRiskCamuluate();
                    }
                });*/
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
