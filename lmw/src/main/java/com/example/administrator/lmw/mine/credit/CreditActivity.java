package com.example.administrator.lmw.mine.credit;

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
import com.example.administrator.lmw.entity.BaseResponse;
import com.example.administrator.lmw.entity.CommonInfo;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.inteface.OnInputDialogListener;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.mine.credit.Entity.CreditBean;
import com.example.administrator.lmw.mine.credit.Entity.CreditInfo;
import com.example.administrator.lmw.mine.fill.FillActivity;
import com.example.administrator.lmw.mine.seting.BindBankSuccussActivity;
import com.example.administrator.lmw.mine.seting.DespositOperateSuccessActivity;
import com.example.administrator.lmw.mine.seting.ResetTradPswVerifyActivity;
import com.example.administrator.lmw.mine.seting.SetLogic;
import com.example.administrator.lmw.mine.seting.SetingActivity;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.StringUtils;
import com.example.administrator.lmw.view.InputTradePswDialog;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lion on 2016/9/9.
 */
public class CreditActivity extends BaseActivity {

    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.title_layout)
    RelativeLayout title_layout;
    @InjectView(R.id.next_bt)
    Button next_bt;
    @InjectView(R.id.credit_num_et)
    EditText credit_num_et;
    @InjectView(R.id.canCash)
    TextView canCash;
    @InjectView(R.id.balance_tv)
    TextView balance_tv;
    @InjectView(R.id.credit_bank)
    TextView credit_bank;
    @InjectView(R.id.credit_rate_tv)
    TextView credit_rate_tv;
    @InjectView(R.id.toAccountTime_tv)
    TextView toAccountTime_tv;
    private String creditNum;
    private Intent intent;
    private String trade;
    private CreditBean creditBean;
    private String freeWithdrawalsCount;//免费提现次数
    private String mobileDesc;//手机号描述
    private String bankName;//银行名称
    private int bankCodeDesc;//银行卡号描述
    private String poundage;//提现手续费率
    private String balance;//余额
    public final static String CREDIT_RESULT = "credit_result";
    private InputTradePswDialog inputTradePswDialog;
    private String withDrawAmount;//可提现金额
    private CommonInfo commonInfo;
    private String freeTime;//免费提现次数
    private String maxCashPerTime;//每次最高提现限制
    private String creditConsume;//提现手续费

    @Override
    protected void initializeData() {
        commonInfo = ConfigManager.getInstance().getCommonInfo(this);
        if (commonInfo != null) {
            freeTime = commonInfo.free_withdrawals_count;
            creditConsume = commonInfo.withdrawal_fee;
        }
    }

    private void getCreditInfo() {
        showLoadingDialog();
        Singlton.getInstance(CreditLogic.class).getCreditInfo(this, new OnResponseListener<CreditInfo>() {
            @Override
            public void onSuccess(CreditInfo response) {
                hideLoadingDialog();
                if (response != null) {
                    int code = Integer.valueOf(response.code);
                    if (code == 0) {
                        creditBean = response.getData();
                        if (creditBean != null) {
                            freeWithdrawalsCount = creditBean.getFreeWithdrawalsCount();//免费提现次数
                            mobileDesc = creditBean.getMobileDesc();//手机号描述
                            bankName = creditBean.getBankName();//银行名称
                            bankCodeDesc = creditBean.getBankCodeDesc();//银行卡号描述
                            poundage = creditBean.getPoundage();//提现手续费率
                            balance = creditBean.getBalance();//余额
                            withDrawAmount = creditBean.getWithDrawAmount();//可提现金额
                            maxCashPerTime = creditBean.getBankWithdrawalsLimit();//单笔提现限额
                        }
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

    private void setView() {
        if (!TextUtils.isEmpty(withDrawAmount))
            canCash.setText(String.format(getString(R.string.credit_number), withDrawAmount));
        if (!TextUtils.isEmpty(bankName) && !TextUtils.isEmpty(bankCodeDesc + ""))
            credit_bank.setText(String.format(getString(R.string.fill_bind_card), bankName, bankCodeDesc + ""));
        if (!TextUtils.equals(freeWithdrawalsCount, "0"))
            credit_rate_tv.setText(StringUtils.changeColor("本月还可免费提现", freeWithdrawalsCount + "", "次", "f99709"));
        else if (!TextUtils.isEmpty(poundage))
            credit_rate_tv.setText(StringUtils.changeColor("本次提现费用为", poundage, "元", "f99709"));
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_credit_layout;
    }

    @Override
    protected void initializeView() {
        getCreditInfo();
        initBarTitle();
        next_bt.setClickable(false);
        setTextChangeListener();

    }

    private void showInputPswDialog() {
        inputTradePswDialog = new InputTradePswDialog(this, InputTradePswDialog.input_credit, creditNum, new OnInputDialogListener() {
            @Override
            public void onClick(int id, String tradePsw) {
                if (id == 1) {

                } else if (id == 0) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(PreferenceCongfig.RESET_EXCHARGE_PSW, PreferenceCongfig.reset_excharge_psw);
                    bundle.putInt(PreferenceCongfig.CREDIT_RESET_EXCHARGE_PSW, PreferenceCongfig.credit_reset_excharge_psw);
                    // bundle.putString(PreferenceCongfig.LICENCE, license);
                    ActivityTools.switchActivity(CreditActivity.this, ResetTradPswVerifyActivity.class, bundle);
                } else if (id == 2) {
                    trade = tradePsw;
                    verifyOldPsw();
                }
            }
        });


    }

    private void setTextChangeListener() {
        credit_num_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                // creditNum = StringUtils.getStringNoCharacter(credit_num_et.getText().toString().trim());
                creditNum = Number2pointListener(s, credit_num_et);
                if (!TextUtils.isEmpty(creditNum) && !StringUtils.isNotnumberPointStart(creditNum) && !StringUtils.isNotnumberPointEnd(creditNum)) {
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
        barTitle.setText("提现");
    }

    @OnClick({R.id.back_layout, R.id.next_bt, R.id.balance_tv, R.id.canCashImg, R.id.instruction_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                ActivityManage.create().finishActivity();
                break;
            case R.id.instruction_btn:
                Singlton.getInstance(PopWindowManager.class).showInstructionDetailPopupWindow(this, title_layout, "提现说明", String.format(getString(R.string.credit_text), freeTime, creditConsume));
                break;
            case R.id.canCashImg:
                Singlton.getInstance(PopWindowManager.class).
                        showOneButtonDialog(mContext, "可提现金额", getString(R.string.credit_fail), "我知道了",
                                new OnDialogClickListener() {
                                    @Override
                                    public void onClick(int id, View v) {
                                        if (id == 2) {

                                        }
                                    }
                                });

                break;
            case R.id.balance_tv:
                if (!TextUtils.isEmpty(withDrawAmount)) {
                    credit_num_et.setText(withDrawAmount);
                    credit_num_et.setSelection(withDrawAmount.length());
                    creditNum = withDrawAmount;
                }

                break;
            case R.id.next_bt:
                if (TextUtils.isEmpty(creditNum)) {
                    showToast("请输入提现金额");
                    return;
                }

                if (StringUtils.isNotnumberPointStart(creditNum) || StringUtils.isNotnumberPointEnd(creditNum)) {
                    showToast("输入的金额格式不正确");
                    return;
                }
                if (Double.valueOf(creditNum) < 100) {
                    showToast("单笔提现不能少于100元");
                    return;
                }
                if (Double.valueOf(creditNum) > Double.valueOf(balance)) {
                    showToast("您输入的提现金额不能大于全部余额");
                    return;
                }
                if (Double.valueOf(creditNum) > Double.valueOf(withDrawAmount)) {
                    showToast("您输入的提现金额不能大于可提现余额");

                    return;
                }
                if (!TextUtils.isEmpty(maxCashPerTime) && Double.valueOf(creditNum) > Double.valueOf(maxCashPerTime)) {
                    showToast(String.format(getString(R.string.max_credit_limit), maxCashPerTime));

                    return;
                }
                if (Double.valueOf(creditNum) > Double.valueOf(withDrawAmount) - Double.valueOf(poundage)) {
                    showToast("提现之后可用余额不足以支付手续费");

                    return;
                }

                //   showInputPswDialog();
                credit();
                break;
        }
    }

    private void credit() {
        Singlton.getInstance(DespositAccountManager.class).depositoryCredit(this, poundage, creditNum);

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
                    credit();
                    inputTradePswDialog.dismiss();
                } else if (code == 150006) {
                    showToast(response.msg);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(LoginActivity.class);
                        }
                    }, 1000);
                } else {
                    inputTradePswDialog.clearTradePsw();
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
