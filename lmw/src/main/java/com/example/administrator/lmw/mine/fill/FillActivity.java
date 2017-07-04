package com.example.administrator.lmw.mine.fill;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.config.DespositAccountManager;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseEvent;
import com.example.administrator.lmw.entity.BaseResponse;
import com.example.administrator.lmw.entity.CommonInfo;
import com.example.administrator.lmw.entity.FillSmsEvent;
import com.example.administrator.lmw.inteface.OnInputDialogListener;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.main.WebViewActivity;
import com.example.administrator.lmw.mine.fill.entity.UserBankEntity;
import com.example.administrator.lmw.mine.fill.entity.UserBankInfo;
import com.example.administrator.lmw.mine.seting.BindBankSuccussActivity;
import com.example.administrator.lmw.mine.seting.ChooseBankCardActivity;
import com.example.administrator.lmw.mine.seting.ResetTradPswVerifyActivity;
import com.example.administrator.lmw.mine.seting.SetLogic;
import com.example.administrator.lmw.mine.seting.SetingActivity;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.StringUtils;
import com.example.administrator.lmw.view.InputTradePswDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lion on 2016/9/9.
 */
public class FillActivity extends BaseActivity {

    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.title_layout)
    RelativeLayout title_layout;
    @InjectView(R.id.next_bt)
    Button next_bt;
    @InjectView(R.id.fill_num_et)
    EditText fill_num_et;
    @InjectView(R.id.bank_name)
    TextView bank_name;
    @InjectView(R.id.fill_limit_tv)
    TextView fill_limit_tv;
    @InjectView(R.id.fill_limit_instruction)
    TextView fill_limit_instruction;
    @InjectView(R.id.fill_limit_detail)
    TextView fill_limit_detail;
    @InjectView(R.id.credit_manage_detail)
    TextView credit_manage_detail;
    @InjectView(R.id.instruction_btn)
    Button instruction_btn;
    private String fillNumber;
    private Intent intent;
    private UserBankInfo userBankInfo;
    private String daylyLimit;
    private String singleLimit;
    private double daylyLimitNumber;
    private double singleLimitNumber;
    private double fill;
    private String trade;
    public final static String FILL_NUMBER = "fill_number";
    public final static String DAYLY_LIMIT = "dayly_Limit";
    public final static String SINGLE_LIMIT = "singleLimit";
    public final static String BIND_BANK_NAME = "bind_bank_name";
    public final static String BIND_BANK_CODE = "bind_bank_code";
    public final static String BIND_BANK_NO = "bind_bank_no";
    private CommonInfo commonInfo;
    private String fillManageUrl;
    private String limitUrl;
    private String lmw_recharge_min_amount;

    @Override
    protected void initializeData() {
        EventBus.getDefault().register(this);
        commonInfo = ConfigManager.getInstance().getCommonInfo(this);
        if (commonInfo != null) {
            fillManageUrl = commonInfo.getLmy_cash_management_agreement();
            lmw_recharge_min_amount = commonInfo.getLmw_recharge_min_amount();
            // limitUrl = commonInfo.getLmw_recharge_limit();
        }
    }

    private void getUserBindCardInfo() {

        Singlton.getInstance(FillLogic.class).getBindBankCardInfo(this, new OnResponseListener<UserBankEntity>() {
            @Override
            public void onSuccess(UserBankEntity response) {
                hideLoadingDialog();
                if (response != null) {
                    int code = Integer.valueOf(response.code);
                    if (code == 0) {
                        if (response.getData() != null) {
                            userBankInfo = response.getData();
                            daylyLimit = userBankInfo.getDailyLimit();//单日限额
                            singleLimit = userBankInfo.getSingleLimit();//单次限额
                            daylyLimitNumber = Double.valueOf(daylyLimit);
                            singleLimitNumber = Double.valueOf(singleLimit);
                            if (daylyLimitNumber == -1)
                                fill_limit_tv.setText(String.format(getString(R.string.bind_card_limit_credit_not_limit),
                                        StringUtils.mathLeftMove4(singleLimit)));
                            else
                                fill_limit_tv.setText(String.format(getString(R.string.bind_card_limit_credit),
                                        StringUtils.mathLeftMove4(singleLimit), StringUtils.mathLeftMove4(daylyLimit)));
                            bank_name.setText(String.format(getString(R.string.fill_bind_card), userBankInfo.getBindBankName(), userBankInfo.getBindBankNo()));
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

    private InputTradePswDialog inputTradePswDialog;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_fill_layout;
    }

    @Override
    protected void initializeView() {
        showLoadingDialog();
        initBarTitle();
        getUserBindCardInfo();
        next_bt.setClickable(false);
        setTextChangeListener();
        //showInputPswDialog();

    }

    private void showInputPswDialog() {
        inputTradePswDialog = new InputTradePswDialog(this, InputTradePswDialog.input_fill, fillNumber, new OnInputDialogListener() {
            @Override
            public void onClick(int id, String tradePsw) {
                if (id == 1) {

                } else if (id == 0) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(PreferenceCongfig.RESET_EXCHARGE_PSW, PreferenceCongfig.reset_excharge_psw);
                    bundle.putInt(PreferenceCongfig.FILL_RESET_EXCHARGE_PSW, PreferenceCongfig.fill_reset_excharge_psw);
                    // bundle.putString(PreferenceCongfig.LICENCE, license);
                    ActivityTools.switchActivity(FillActivity.this, ResetTradPswVerifyActivity.class, bundle);
                } else if (id == 2) {
                    trade = tradePsw;
                    verifyOldPsw();
                }
            }
        });

    }

    /**
     * 验证原密码
     */
    private void verifyOldPsw() {
        showLoadingDialog();
        Singlton.getInstance(SetLogic.class).verifyOldTradePsw(this, trade, new OnResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                hideLoadingDialog();
                int code = Integer.valueOf(response.code);
                if (code == 0) {
                    inputTradePswDialog.dismiss();
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
                hideLoadingDialog();
            }
        });
    }

    private void setTextChangeListener() {
        fill_num_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                //  fillNumber = StringUtils.getStringNoCharacter(fill_num_et.getText().toString().trim());
                fillNumber = Number2pointListener(s, fill_num_et);
                if (!TextUtils.isEmpty(fillNumber) && !StringUtils.isNotnumberPointStart(fillNumber) && !StringUtils.isNotnumberPointEnd(fillNumber)) {
                    next_bt.setClickable(true);
                    next_bt.setBackgroundColor(getResources().getColor(R.color.text_blue));

                } else {
                    next_bt.setClickable(false);
                    next_bt.setBackgroundColor(getResources().getColor(R.color.gray));
                }

            }
        });

    }


    private void initBarTitle() {
        barIconBack.setImageResource(R.drawable.nav_back);
        title_layout.setBackgroundColor(getResources().getColor(R.color.text_blue));
        barTitle.setText("充值");
    }

    @OnClick({R.id.back_layout, R.id.next_bt, R.id.fill_limit_detail, R.id.credit_manage_detail, R.id.instruction_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                finishActivity(this);
                break;
            case R.id.instruction_btn:
                Singlton.getInstance(PopWindowManager.class).showInstructionDetailPopupWindow(this, title_layout, "充值说明", getString(R.string.fill_text));
                break;
            case R.id.fill_limit_detail:
                //   startFillLimitPage();

                Bundle bundle = new Bundle();
                bundle.putInt(PreferenceCongfig.ChoseBankCard, PreferenceCongfig.ChoseBankCard_limit_detail);
                ActivityTools.switchActivity(FillActivity.this, ChooseBankCardActivity.class, bundle);

                //   startActivity(ChooseBankCardActivity.class);
                break;
            case R.id.credit_manage_detail:
                startFillManagePage();
                break;
            case R.id.next_bt:
                if (TextUtils.isEmpty(fillNumber)) {
                    showToast("请输入充值金额");
                    return;
                }

                if (StringUtils.isNotnumberPointStart(fillNumber) || StringUtils.isNotnumberPointEnd(fillNumber)) {
                    showToast("输入的金额格式不正确");
                    return;
                }
                fill = Double.valueOf(fillNumber);
          /*      if (fill < 100) {
                    showToast("充值金额最小为一百元");
                    return;
                }*/
                if (!TextUtils.isEmpty(lmw_recharge_min_amount) && fill < Double.valueOf(lmw_recharge_min_amount)) {
                    showToast(String.format(getString(R.string.fill_min_amount), lmw_recharge_min_amount));
                    return;
                }

                if (fill > singleLimitNumber) {
                    showToast("您已超过单笔充值金额，请重新输入金额");
                    return;
                }
                if (daylyLimitNumber != -1 && fill > daylyLimitNumber) {
                    showToast("您已超过此卡单日输出金额，建议更换银行卡充值");
                    return;
                }
          /*      if (TextUtils.isEmpty(trade)) {
                    showInputPswDialog();
                    return;
                }*/
                showLoadingDialog();
                fill();
                break;
        }
    }

    private void startFillManagePage() {
        Bundle bundle = new Bundle();
        bundle.putInt(WebViewActivity.URL_TYPE, WebViewActivity.URL_FILL_MANAGE_INSTURCTION);
        bundle.putString(WebViewActivity.URL, fillManageUrl);
        ActivityTools.switchActivity(this, WebViewActivity.class, bundle);
    }

    private void startFillLimitPage() {
        Bundle bundle = new Bundle();
        bundle.putInt(WebViewActivity.URL_TYPE, WebViewActivity.URL_FILL_LIMIT_INSTURCTION);
        bundle.putString(WebViewActivity.URL, limitUrl);
        ActivityTools.switchActivity(this, WebViewActivity.class, bundle);
    }

    private void fill() {
        Singlton.getInstance(DespositAccountManager.class).desposityFill(this, userBankInfo.getBindBankCode(), fillNumber, "2");

    }

    /**
     * @param baseEvent void
     * @auther lion
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent baseEvent) {
        if (baseEvent != null) {
            if (baseEvent instanceof FillSmsEvent) {
                fill();
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    /**
     * 监听数字两位小数限制
     * by lionzhang
     */


    private String Number2pointListener(Editable s, EditText et) {
        int selectionStart = et.getSelectionStart();
        int selectionEnd = et.getSelectionEnd();

        if (StringUtils.isNumberHas2point(s.toString())) {
            s.delete(selectionStart - 1, selectionEnd);
            et.setText(s);
            et.setSelection(s.length());
        }

        return et.getText().toString();

    }
}
