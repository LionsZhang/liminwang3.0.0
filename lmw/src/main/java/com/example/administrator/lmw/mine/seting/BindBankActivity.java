package com.example.administrator.lmw.mine.seting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseEvent;
import com.example.administrator.lmw.entity.BindBankEvent;
import com.example.administrator.lmw.entity.BindBankRefreshEvent;
import com.example.administrator.lmw.entity.BindSuccessEvent;
import com.example.administrator.lmw.entity.ChoseBankEvent;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.login.UserInfoUtils;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.login.entity.UserInfoBean;
import com.example.administrator.lmw.mine.fill.FillActivity;
import com.example.administrator.lmw.mine.seting.entity.BankCardInfo;
import com.example.administrator.lmw.mine.seting.entity.BindBankEntity;
import com.example.administrator.lmw.mine.seting.entity.SetData;
import com.example.administrator.lmw.mine.seting.entity.SetDataBean;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lion on 2016/9/2.
 */
public class BindBankActivity extends BaseActivity {
    @InjectView(R.id.bank_name)
    TextView bank_name;//银行名字
    @InjectView(R.id.bank_hint)
    TextView bank_hint;//银行限额
    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.title_layout)
    RelativeLayout title_layout;
    @InjectView(R.id.bank_rl)
    RelativeLayout bank_rl;
    @InjectView(R.id.province_rl)
    RelativeLayout province_rl;
    @InjectView(R.id.province_tv)
    TextView province_tv;
    @InjectView(R.id.name_et)
    EditText name_et;
    @InjectView(R.id.license_tv)
    EditText license_tv;
    @InjectView(R.id.license_rl)
    RelativeLayout license_rl;
    @InjectView(R.id.bank_number_et)
    EditText bank_number_et;
    @InjectView(R.id.phone_et)
    EditText phone_et;
    @InjectView(R.id.next_bt)
    Button next_bt;
    @InjectView(R.id.name_icon)
    ImageView name_icon;
    @InjectView(R.id.phone_icon)
    ImageView phone_icon;
    @InjectView(R.id.instruction_btn)
    Button instruction_btn;
    private Intent intent;
    private String mActionType;
    private String phone;
    private String name, license, bankNumber, bankCardName, limitCredit, province;
    private BankCardInfo bankCardInfo;
    private int bankId = -1;
    private String bankCode;
    private String authName;
    private UserInfo userInfo;
    private UserInfoBean userInfoBean;
    private String token;
    private SetDataBean setDataBean;
    private String authLicense;

    @Override
    protected void initializeData() {
        EventBus.getDefault().register(this);
        token = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, "");
        intent = getIntent();
        if (!TextUtils.isEmpty(intent.getAction()))
            mActionType = intent.getAction();
        userInfo = UserInfoUtils.getInstance().getUserInfo(mContext);
        if (userInfo != null)
            userInfoBean = userInfo.getData();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_bind_card_layout;
    }

    @Override
    protected void initializeView() {
        if (userInfoBean.getUserTag() == PreferenceCongfig.IS_OLD_EFFECTIVE_USER
                || userInfoBean.getIsBindBankCard() == PreferenceCongfig.IS_REBIND_BANKCARD
                ) {//已经绑过卡，解绑之后再绑，显示实名的名字和身份证
            showLoadingDialog();
            requestSetData();
            license_rl.setVisibility(View.GONE);
        } else
            license_rl.setVisibility(View.VISIBLE);
        initBarTitle();
       // next_bt.setClickable(false);
        setTextChangeListener();

    }

    private void setTextChangeListener() {
        if (userInfoBean.getUserTag() == PreferenceCongfig.IS_OLD_EFFECTIVE_USER ||
                userInfoBean.getIsBindBankCard() == PreferenceCongfig.IS_REBIND_BANKCARD) {
            name_et.setFocusableInTouchMode(false);
            name_et.clearFocus();
            license_tv.setFocusableInTouchMode(false);
            license_tv.clearFocus();
        } else {
            name_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {

                    name = name_et.getText().toString();


                }
            });
            license_tv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    license = license_tv.getText().toString();
                }
            });
        }

        bank_number_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bankNumber = bank_number_et.getText().toString();

            }
        });
        phone_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                phone = phone_et.getText().toString();
         /*       if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(bankNumber) && !TextUtils.isEmpty(name) &&
                        !TextUtils.isEmpty(bank_name.getText().toString()) && !TextUtils.isEmpty(license)) {
                    next_bt.setClickable(true);
                    next_bt.setBackgroundColor(getResources().getColor(R.color.text_blue));
                    bankCardName = bank_name.getText().toString();
                } else {
                    next_bt.setClickable(false);
                    next_bt.setBackgroundColor(getResources().getColor(R.color.gray));
                }*/
            }
        });
    }

    private void initBarTitle() {
        barIconBack.setImageResource(R.drawable.nav_back);
        title_layout.setBackgroundColor(getResources().getColor(R.color.text_blue));
        if (PreferenceCongfig.ACTION_BIND_BANKCARD.equals(mActionType)) {
            barTitle.setText("绑定银行卡");
            //   license_rl.setVisibility(View.VISIBLE);
        }

        if (PreferenceCongfig.ACTION_MOTIFY_BIND_BANKCARD.equals(mActionType)) {
            barTitle.setText("修改银行卡");
            //   license_rl.setVisibility(View.GONE);
        }
        bank_hint.setVisibility(View.GONE);
    }

    @OnClick({R.id.back_layout, R.id.next_bt, R.id.province_rl, R.id.bank_rl, R.id.name_icon, R.id.phone_icon, R.id.instruction_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                finishActivity(this);
                break;
            case R.id.instruction_btn:
                Singlton.getInstance(PopWindowManager.class).
                        showInstructionDetailPopupWindow(this, title_layout, "温馨提示", StringUtils.getBindCardInstruction());
                break;
            case R.id.province_rl:

                break;
            case R.id.bank_rl:
                Bundle bundle = new Bundle();
                bundle.putInt(PreferenceCongfig.ChoseBankCard, PreferenceCongfig.ChoseBankCard_chose_card);
                ActivityTools.switchActivity(BindBankActivity.this, ChooseBankCardActivity.class, bundle);
                // startActivity(ChooseBankCardActivity.class);
                break;
            case R.id.name_icon:
                Singlton.getInstance(PopWindowManager.class).
                        showOneButtonDialog(mContext, "温馨提示", "为保障账户安全，只能绑定本人银行卡。", "我知道了",
                                new OnDialogClickListener() {
                                    @Override
                                    public void onClick(int id, View v) {

                                    }
                                });

                break;
            case R.id.phone_icon:
                Singlton.getInstance(PopWindowManager.class).
                        showOneButtonDialog(mContext, "温馨提示", "请填写办理该银行卡时预留的手机号、没有预留或手机号已停用请联系银行客服更新处理。", "我知道了",
                                new OnDialogClickListener() {
                                    @Override
                                    public void onClick(int id, View v) {

                                    }
                                });

                break;
            case R.id.next_bt:
                // limitCredit = bank_hint.getText().toString();
                //  province = province_tv.getText().toString();
                bankCardName = bank_name.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    showToast("请输入姓名");
                    return;
                }
                if (!StringUtils.isTwoToTenChinese(name)) {
                    showToast("请输入正确的持卡人姓名");
                    return;
                }
                if (PreferenceCongfig.ACTION_BIND_BANKCARD.equals(mActionType)) {
                    if (TextUtils.isEmpty(license)) {
                        showToast("请输入持卡人身份证号");
                        return;
                    }
                }
                if (PreferenceCongfig.ACTION_BIND_BANKCARD.equals(mActionType)) {
                    if (license.length()!=15&&license.length()!=18) {
                        showToast("请输入正确的持卡人身份证号");
                        return;
                    }
                }

                if (TextUtils.isEmpty(bankCardName)) {
                    showToast("请选择开户银行");
                    return;
                }

        /*        if (TextUtils.isEmpty(province)) {
                    showToast("请选择持卡人开户银行省市");
                    return;
                }*/

                if (TextUtils.isEmpty(bankNumber)||!StringUtils.isBankCard(bankNumber)) {
                    showToast("请输入正确的银行卡号(15-19位)");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    showToast("请输入银行预留手机号码");
                    return;
                }
                if (phone.contains(" ")||!StringUtils.isPhone(phone)) {
                    showToast("银行预留手机号格式错误");
                    return;
                }
                showLoadingDialog();
                bindBankCard();
                break;
        }
    }

    /**
     * 绑定银行卡
     */
    private String orderNo;

    private void bindBankCard() {
        Singlton.getInstance(SetLogic.class).bindBankCard(this, bankNumber, bankCode, phone, name, license, new OnResponseListener<BindBankEntity>() {
            @Override
            public void onSuccess(BindBankEntity response) {
                hideLoadingDialog();
                int code = Integer.valueOf(response.code);
                if (code == 0) {
                    if (response.getData() != null)
                        orderNo = response.getData().getOrderNo();
                    //添加跳到验证短信页面
                    Bundle bundle = new Bundle();
                    bundle.putInt(PreferenceCongfig.BIND_BANK_CARD_VERIFY, PreferenceCongfig.bind_bank_card_verify);
                    bundle.putString(PreferenceCongfig.LICENCE, license);
                    bundle.putString(PreferenceCongfig.BIND_PHONE, phone);
                    bundle.putString(PreferenceCongfig.BIND_CARD_ODER_NO, orderNo);
                    ActivityTools.switchActivity(BindBankActivity.this, ResetTradPswVerifyActivity.class, bundle);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    /**
     * @param baseEvent void
     * @auther lion
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent baseEvent) {
        if (baseEvent != null) {
            if (baseEvent instanceof ChoseBankEvent) {
                ChoseBankEvent choseBankEvent = (ChoseBankEvent) baseEvent;
                bankCardInfo = choseBankEvent.getBankCardInfo();
                bankCardName = bankCardInfo.getBankName();
                bankId = bankCardInfo.getBankId();
                bankCode = bankCardInfo.getBankCode();
                bank_name.setText(bankCardName);
                bank_hint.setVisibility(View.VISIBLE);
                if (bankCardInfo.getDailyQuotaAmount() == -1)
                    bank_hint.setText(String.format(getString(R.string.bind_card_limit_credit_not_limit),
                            StringUtils.mathLeftMove4(bankCardInfo.getEachQuotaAmout() + "")));
                else
                    bank_hint.setText(String.format(getString(R.string.bind_card_limit_credit),
                            StringUtils.mathLeftMove4(bankCardInfo.getEachQuotaAmout() + ""),
                            StringUtils.mathLeftMove4(bankCardInfo.getDailyQuotaAmount() + "")));
            }
            if (baseEvent instanceof BindBankEvent) {
                Singlton.getInstance(SetLogic.class).bindBankCard(this, bankNumber, bankCode, phone, name, license, new OnResponseListener<BindBankEntity>() {
                    @Override
                    public void onSuccess(BindBankEntity response) {
                        int code = Integer.valueOf(response.code);
                        if (code == 0) {
                            if (response.getData() != null)
                                orderNo = response.getData().getOrderNo();
                            BindBankRefreshEvent bindBankRefreshEvent = new BindBankRefreshEvent();
                            bindBankRefreshEvent.setOderNo(orderNo);
                            EventBus.getDefault().post(bindBankRefreshEvent);
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

        }
    }

    /**
     * 请求用户数据获取银行卡号,身份证号
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
                        if (setDataBean != null) {
                            if (userInfoBean.getIsBindBankCard() == PreferenceCongfig.IS_REBIND_BANKCARD) {
                                setUserNameAndLicenseInfo();
                            } else if (userInfoBean.getIsBindBankCard() != PreferenceCongfig.IS_REBIND_BANKCARD && userInfoBean.getUserTag() == PreferenceCongfig.IS_OLD_EFFECTIVE_USER) {
                                setUserNameAndLicenseInfo();
                                setUserBindedCardInfo();
                            }
                        }
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

    /**
     * 有效老用户设置已经绑定的银行卡信息
     */
    private void setUserBindedCardInfo() {
        bankCode = setDataBean.getBankCode();//老用户绑卡银行code
        bankCardName = setDataBean.getBankName();//老用户绑卡银行名称
        bank_name.setText(bankCardName);//老用户绑卡银行名称
        bankNumber = setDataBean.getBankCardNoWhole();//老用户绑定的银行卡卡号
        bank_number_et.setText(bankNumber);//老用户绑定的银行卡卡号
        bank_hint.setVisibility(View.VISIBLE);
        phone_et.requestFocus();
        if (!TextUtils.isEmpty(setDataBean.getDailyQuotaAmount())){
            if (Double.valueOf(setDataBean.getDailyQuotaAmount()) == -1)
                bank_hint.setText(String.format(getString(R.string.bind_card_limit_credit_not_limit),
                        StringUtils.mathLeftMove4(setDataBean.getEachQuotaAmout() + "")));
            else
                bank_hint.setText(String.format(getString(R.string.bind_card_limit_credit),
                        StringUtils.mathLeftMove4(setDataBean.getEachQuotaAmout()),
                        StringUtils.mathLeftMove4(setDataBean.getDailyQuotaAmount())));
        }
    }

    /**
     * 用户已绑卡再解绑再重新绑卡或者是有效老用户
     * 设置名字和身份证信息
     */
    private void setUserNameAndLicenseInfo() {
        authName = setDataBean.getCustomerName();//缩略名字
        authLicense = setDataBean.getIdNo();//缩略身份证
        name = setDataBean.getCustomerSurnameWhole();//真实名字
        license = setDataBean.getIdNoWhole();//真实身份证
        name_et.setText(authName);
        name_et.setSelection(authName.length());
        license_tv.setText(authLicense);
        license_tv.setSelection(authLicense.length());
        bank_hint.setVisibility(View.GONE);
    }
}
