package com.example.administrator.lmw.mine.invest;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.DespositOperate;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResponse;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.finance.utils.BigDecimalUtils;
import com.example.administrator.lmw.finance.utils.OnPayPasswordDialogListener;
import com.example.administrator.lmw.finance.utils.PayPasswordDialog;
import com.example.administrator.lmw.http.LmwHttp;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.invest.entity.ConfirmTransferEntity;
import com.example.administrator.lmw.mine.invest.entity.PurchaseDebtEntity;
import com.example.administrator.lmw.mine.invest.utils.Arith;
import com.example.administrator.lmw.mine.invest.utils.InvestmentHttp;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.mine.seting.DespositOperateFailActivity;
import com.example.administrator.lmw.mine.seting.DespositOperateSuccessActivity;
import com.example.administrator.lmw.register.RegisterLogic;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.StringUtils;
import com.example.administrator.lmw.utils.ThreadManager;

import java.text.DecimalFormat;

import butterknife.InjectView;

/**
 * 确认转让
 * <p>
 * Created by Administrator on 2016/9/21 0021.
 */
public class ConfirmTransferActivity extends BaseActivity {

    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.back)
    TextView back;
    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.back_layout)
    LinearLayout backLayout;
    @InjectView(R.id.right_title)
    TextView rightTitle;
    @InjectView(R.id.right_icon)
    ImageView rightIcon;
    @InjectView(R.id.title_bar_right_layout)
    LinearLayout titleBarRightLayout;
    @InjectView(R.id.original_interest)
    TextView originalInterest;//债权价值=待收本金+应计收益
    @InjectView(R.id.to_be_close_interest)
    TextView toBeCloseInterest;
    @InjectView(R.id.forecase_transfer_interest)
    TextView forecaseTransferInterest;
    @InjectView(R.id.after_estimated_interest)
    TextView afterEstimatedInterest;
    @InjectView(R.id.after_principal)
    TextView afterPrincipal;
    @InjectView(R.id.after_interest)
    TextView afterInterest;
    @InjectView(R.id.transfer_less)
    ImageView transferLess;
    @InjectView(R.id.transfer_et)
    EditText transferEt;
    @InjectView(R.id.transfer_plus)
    ImageView transferPlus;
    @InjectView(R.id.expenditures_interest)
    TextView expendituresInterest;
    @InjectView(R.id.transfer_none)
    TextView transferNone;
    @InjectView(R.id.transfer_annualized)
    TextView transferAnnualized;
    @InjectView(R.id.holding_days)
    TextView holdingDays;
    @InjectView(R.id.surplus_days)
    TextView surplusDays;
    @InjectView(R.id.transfer_checkbox)
    CheckBox transferCheckbox;
    @InjectView(R.id.transfer_btn)
    Button transferBtn;
    @InjectView(R.id.transfer_protocol)
    TextView transferProtocol;
    @InjectView(R.id.trannsfer_description)
    TextView TrannsferDescription;
    @InjectView(R.id.title)
    RelativeLayout Title;
    private String titleId;
    private boolean falg = true;
    private DecimalFormat df = new DecimalFormat("##0.00");
    private BigDecimalUtils bdu = new BigDecimalUtils();
    private PayPasswordDialog payPasswordDialog;
    private Intent intent;
    private String phone;
    private boolean timefalg = true;// 获取验证吗只能点击一次判断

    @Override
    protected void initializeData() {
        intent = getIntent();
        phone = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_ACCOUNT, phone);//获取账号
        bundle();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_confirm_transfer;
    }

    @Override
    protected void initializeView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        titlebar();
        getComfirm("0.0", titleId);

        /**
         * 减
         */
        transferLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = transferEt.getText().toString();
                if (str.equals("")) {
                    showToast("不能小于0.0，请重新输入");
                } else {
                    if (Double.parseDouble(str) <= 0.0) {
                        showToast("不能小于0.0，请重新输入");
                    } else {
                        transferEt.setText(Arith.sub(Double.parseDouble(str), 0.1) + "");
                    }
                }
            }
        });
        /**
         * 加
         */
        transferPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = transferEt.getText().toString();
                if (str.equals("")) {
                     transferEt.setText(Arith.add(0, 0.1) + "");
                } else {
                    if (Double.parseDouble(str) >= 5) {
                        showToast("不能大于5.00，请重新输入");
                    } else {
                        transferEt.setText(Arith.add(Double.parseDouble(str), 0.1) + "");
                    }
                }
            }
        });

        /**
         * 监听EditText
         */
        transferEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String str = charSequence.toString();
                if (str.length() == 1) {
                    if (str.equals(".")) {
                        transferEt.setText("");
                    }
                } else if (str.length() == 2) {
                    if (str.substring(0, 1).equals("0")) {
                        if (str.substring(1, 2).equals("0")) {
                            transferEt.setText("0");
                            transferEt.setSelection(1);
                        } else if (!str.substring(1, 2).equals(".")) {
                            transferEt.setText(str.substring(1, str.length()));
                            transferEt.setSelection(1);
                        }
                    }
                } else {
                    if (str.contains(".")) {
                        if (str.length() - str.indexOf(".") > 3) {
//                            showToast(str.length() + " " + str.indexOf("."));
                            transferEt.setText(str.substring(0, str.indexOf(".") + 3));
                            transferEt.setSelection(str.indexOf(".") + 3);
                        }

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("."))
                    if (s.length() < 1) {
                        transferBtn.setBackground(getResources().getDrawable(R.drawable.select_panic_buying_white_btn));
                        transferLess.setImageResource(R.drawable.btn_zhuanr0_normal);
                        transferPlus.setImageResource(R.drawable.btn_plus);
                        getComfirmEt("0", titleId);
                    } else {
                        if (Double.parseDouble(s.toString()) >= 0.0) {
                            if (Double.parseDouble(s.toString()) <= 5) {
                                if (falg) {
                                    transferBtn.setBackground(getResources().getDrawable(R.drawable.select_panic_buying_btn));
                                } else {
                                    transferBtn.setBackground(getResources().getDrawable(R.drawable.select_panic_buying_white_btn));
                                }
                                transferLess.setImageResource(R.drawable.btn_less);
                                transferPlus.setImageResource(R.drawable.btn_plus);
                                getComfirmEt(s.toString(), titleId);

                            } else {
                                showToast("不能大于5.0，请重新输入");
                                transferLess.setImageResource(R.drawable.btn_less);
                                transferPlus.setImageResource(R.drawable.btn_zhuanr1_normal);
                            }
                            if (Double.parseDouble(s.toString()) <= 0.0) {
                                transferLess.setImageResource(R.drawable.btn_zhuanr0_normal);
                                transferPlus.setImageResource(R.drawable.btn_plus);
                            }
                            if (Double.parseDouble(s.toString()) >= 5) {
                                transferLess.setImageResource(R.drawable.btn_less);
                                transferPlus.setImageResource(R.drawable.btn_zhuanr1_normal);
                            }
                        } else {
                            showToast("不能小于0.0，请重新输入");
                            transferLess.setImageResource(R.drawable.btn_zhuanr0_normal);
                            transferPlus.setImageResource(R.drawable.btn_plus);
                            transferBtn.setBackground(getResources().getDrawable(R.drawable.select_panic_buying_white_btn));
                        }
                    }
            }
        });
        /**
         * CheckBox
         */
        transferCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                falg = isChecked;
                if (isChecked) {
                    if (transferEt.getText().toString() != null) {
                        if (Double.parseDouble(transferEt.getText().toString()) > 0) {
                            transferBtn.setBackground(getResources().getDrawable(R.drawable.select_panic_buying_btn));
                        } else {
                            transferBtn.setBackground(getResources().getDrawable(R.drawable.select_panic_buying_white_btn));
                        }
                    } else {
                        transferBtn.setBackground(getResources().getDrawable(R.drawable.select_panic_buying_white_btn));
                    }
                } else {
                    transferBtn.setBackground(getResources().getDrawable(R.drawable.select_panic_buying_white_btn));
                }
            }
        });

        /**
         * 转让
         */
        transferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String discount = transferEt.getText().toString();
                if (falg) {
                    if (!discount.equals("")) {
                        if (Double.parseDouble(discount) >= 0) {
                            if (Double.parseDouble(discount) <= 5) {
                                transfer();
                            } else {
                                showToast("不能大于5.0，请重新输入");
                            }
                        } else {
                            showToast("不能小于0.0，请重新输入");
                        }
                    } else {
                        showToast("请输入转让折扣");
                    }
                } else {

                }
            }
        });
        /**
         * 协议
         */
        transferProtocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentab = new Intent(mContext, WebViewMore.class);
                intentab.putExtra("url", LmwHttp.baseUrl + "/lmw-product/contract/debt_wechat.html");
                intentab.putExtra("title", "协议");
                startActivity(intentab);
            }
        });
        /**
         * 转让说明
         */
        TrannsferDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                full("转让说明", R.string.transfer_description);
            }
        });
    }

    private void bundle() {
        if (intent.hasExtra("titleId"))
            titleId = intent.getStringExtra("titleId");
    }

    /**
     * 标题初始化
     */
    private void titlebar() {
        barTitle.setText("确认转让");
        barIconBack.setImageResource(R.drawable.nav_back);
        // 返回
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity(ConfirmTransferActivity.this);
            }
        });


    }

    /**
     * 转让说明 弹出框
     *
     * @param title
     * @param content
     */
    private void full(String title, int content) {
        Singlton.getInstance(PopWindowManager.class).showFullDialog(mContext, Title, title, getResources().getString(content));
    }

    /**
     * 债权标发起确认
     */
    private void getDebt(String discount, String investId) {
        Singlton.getInstance(InvestmentHttp.class).getDebtAdd(mContext, discount, investId, new OnResponseListener<BaseResult<ConfirmTransferEntity>>() {
            @Override
            public void onSuccess(BaseResult<ConfirmTransferEntity> confirmTransferEntity) {
                if (TextUtils.equals(confirmTransferEntity.getCode(), "0")) {
                    intent = new Intent(mContext, DespositOperateSuccessActivity.class);
                    intent.putExtra(PreferenceCongfig.INTENT_KEY_DESPONSIT_OPERATE, DespositOperate.RESULT_BORROW_DEBT_ADD);
                    startActivity(intent);
                } else {
                    intent = new Intent(mContext, DespositOperateFailActivity.class);
                    intent.putExtra(PreferenceCongfig.INTENT_KEY_DESPONSIT_OPERATE, DespositOperate.RESULT_BORROW_DEBT_ADD);
                    intent.putExtra(PreferenceCongfig.INTENT_KEY_OPEN_DESPONSITY_FAIL, confirmTransferEntity.getMsg());
                    startActivity(intent);
                }
            }

            @Override

            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 债权初始化计算数据
     *
     * @param discount
     * @param investId
     */
    private void getComfirm(String discount, String investId) {
        Singlton.getInstance(InvestmentHttp.class).getPurchase(mContext, discount, investId, new OnResponseListener<BaseResult<PurchaseDebtEntity>>() {
            @Override
            public void onSuccess(BaseResult<PurchaseDebtEntity> purchaseDebtEntity) {
                if (purchaseDebtEntity == null) return;
                if (purchaseDebtEntity.getData() == null) return;
                if (TextUtils.equals(purchaseDebtEntity.getCode(), "0")) {
                    find(purchaseDebtEntity.getData());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 输入框变化修改不同的数据
     *
     * @param discount
     * @param investId
     */
    private void getComfirmEt(String discount, String investId) {
        Singlton.getInstance(InvestmentHttp.class).getPurchase(mContext, discount, investId, new OnResponseListener<BaseResult<PurchaseDebtEntity>>() {
            @Override
            public void onSuccess(BaseResult<PurchaseDebtEntity> purchaseDebtEntity) {
                if (purchaseDebtEntity == null) return;
                if (purchaseDebtEntity.getData() == null) return;
                if (TextUtils.equals(purchaseDebtEntity.getCode(), "0")) {
                    findEt(purchaseDebtEntity.getData());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * @param entity
     */
    private void findEt(PurchaseDebtEntity entity) {
        // 转让后预估应收本息
        afterEstimatedInterest.setText(df.format(Arith.add(Double.parseDouble(entity.getPrincipalPriceAmt()), Double.parseDouble(entity.getTempInterestPriceAmt()))));
        // 转让后应收本金
        afterPrincipal.setText(entity.getPrincipalPriceAmt());
        // 转让后应收利息
        afterInterest.setText(entity.getTempInterestPriceAmt());
        // 让利
        transferNone.setText(df.format(Arith.add(Double.parseDouble(entity.getPrincipalDiscountAmt()), Double.parseDouble(entity.getTempInterestDiscountAmt()))));
        // 受让人预期年华收益
        transferAnnualized.setText(entity.getTransferRate());
    }

    /**
     * 页面初始化
     *
     * @param entity
     */
    private void find(PurchaseDebtEntity entity) {
        // 债权价值
        originalInterest.setText(df.format(Arith.add(Double.parseDouble(entity.getPrincipalAmt()),//待收本金
                Double.parseDouble(entity.getTempInterestAmt()))));//应计收益
        // 待收本金
        toBeCloseInterest.setText(entity.getPrincipalAmt());
        // 应计转让收益
        forecaseTransferInterest.setText(entity.getTempInterestAmt());
        // 转让后应收本息
        afterEstimatedInterest.setText(df.format(Arith.add(Double.parseDouble(entity.getPrincipalPriceAmt()),
                Double.parseDouble(entity.getTempInterestPriceAmt()))));
        // 转让后应收本金
        afterPrincipal.setText(entity.getPrincipalPriceAmt());
        // 转让后应收利息
        afterInterest.setText(entity.getTempInterestPriceAmt());
        // 转让折扣
        transferEt.setText("0.0");
        transferEt.setSelection(transferEt.getText().toString().length());
        // 让利
        transferNone.setText(df.format(Arith.add(Double.parseDouble(entity.getPrincipalDiscountAmt()),
                Double.parseDouble(entity.getTempInterestDiscountAmt()))));
        // 受让人预期年华收益
        transferAnnualized.setText(entity.getTransferRate());
        // 持有天数
        holdingDays.setText(entity.getHoldDay() + "");
        // 转让之后剩余天数
        surplusDays.setText(entity.getSurplusDay() + "");
    }

    /**
     * 确认转让 短信验证提示框
     */
    private void transfer() {
        String content = "验证吗将发送至" + StringUtils.fuzzyMobile(phone);
        if (payPasswordDialog == null){
            payPasswordDialog = new PayPasswordDialog(ConfirmTransferActivity.this, content, new OnPayPasswordDialogListener() {
                @Override
                public void onClick(int id, String edit_no) {
                    switch (id) {
                        case 0:// 倒计时
                            if (timefalg) {
                                timefalg = false;
                                getVerifyCode(phone);
                            }
                            break;
                        case 1:// 确认
                            if (!TextUtils.isEmpty(edit_no) && edit_no.length() == 6) {
                                checkVerifyCode(edit_no);
                            }
                            break;
                        case 2:// 关闭按钮
                            break;
                    }
                }
            });
        } else {
            payPasswordDialog.show();
        }
    }

    /**
     * 获取手机短信验证吗
     *
     * @param phone
     */
    private void getVerifyCode(String phone) {
        Singlton.getInstance(RegisterLogic.class).getVerifyCode(this, phone, "07", new OnResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                timefalg = true;
                int code = Integer.valueOf(response.getCode());
                if (code == 0) {
                    ThreadManager.getMainHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            payPasswordDialog.start();
                        }
                    });
                } else {
                    showToast(response.msg);
                }
            }

            @Override
            public void onFail(Throwable e) {
                timefalg = true;
            }
        });
    }


    /**
     * 核对手机验证吗
     *
     * @param verifyCode
     */
    private void checkVerifyCode(String verifyCode) {
        Singlton.getInstance(RegisterLogic.class).checkVerifyCode(this, phone, verifyCode,
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        int code = Integer.valueOf(response.code);
                        if (code == 0) {
                            payPasswordDialog.dismiss();
                            getDebt(transferEt.getText().toString(), titleId);
                        } else
                            showToast(response.msg);
                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });

    }
}
