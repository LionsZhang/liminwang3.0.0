package com.example.administrator.lmw.finance.activity;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.DespositAccountManager;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.finance.entity.DetailEntity;
import com.example.administrator.lmw.finance.entity.ExpectedReturn;
import com.example.administrator.lmw.finance.entity.UserMoenyEntity;
import com.example.administrator.lmw.finance.financehttp.FinanceHttp;
import com.example.administrator.lmw.finance.utils.BigDecimalUtils;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.login.UserInfoUtils;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.login.entity.UserInfoBean;
import com.example.administrator.lmw.mine.fill.FillActivity;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.mine.seting.BindBankActivity;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ToastUtil;

import java.text.DecimalFormat;

import butterknife.InjectView;

/**
 * 债权购买页面
 * <p/>
 * Created by Administrator on 2016/8/31 0031.
 */
public class CreditBuy extends BaseActivity {

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
    @InjectView(R.id.credit_buy_title_tv)
    TextView creditBuyTitleTv;
    @InjectView(R.id.credit_buy_earnings_tv)
    TextView creditBuyEarningsTv;
    @InjectView(R.id.credit_buy_earnings_tv_x)
    TextView creditBuyEarningsTvX;
    @InjectView(R.id.credit_buy_days_tv)
    TextView creditBuyDaysTv;
    @InjectView(R.id.credit_buy_moneys_tv)
    TextView creditBuyMoneysTv;
    @InjectView(R.id.credit_buy_money_et)
    EditText creditBuyMoneyEt;
    @InjectView(R.id.credit_buy_full_btn)
    Button creditBuyFullBtn;
    @InjectView(R.id.credit_buy_actual_tv)
    TextView creditBuyActualTv;
    @InjectView(R.id.credit_buy_income_tv)
    TextView creditBuyIncomeTv;
    @InjectView(R.id.credit_buy_balance_tv)
    TextView creditBuyBalanceTv;
    @InjectView(R.id.credit_buy_days_unit_tv)
    TextView creditBuyDaysUnitTv;
    @InjectView(R.id.credit_buy_recharge_btn)
    Button creditBuyRechargeBtn;
    @InjectView(R.id.credit_buy_btn)
    Button creditBuyBtn;
    @InjectView(R.id.credit_buy_prompt)
    ImageView creditBuyPrompt;
    @InjectView(R.id.credit_buy_full_prompt)
    ImageView creditBuyFullPrompt;
    private String subjectId, discount;
    private DetailEntity entity;// 标的信息
    private UserMoenyEntity moenyEntity;// 用户可用余额
    private UserInfo userInfo;
    private UserInfoBean userInfoBean;
    private DecimalFormat df = new DecimalFormat("##0.00");
    private DecimalFormat dfz = new DecimalFormat("##0");
    private BigDecimalUtils bdu = new BigDecimalUtils();
    private Intent intent;
    private boolean buyfalg = true;//购买按钮判断
    private boolean editTextFalg = true;
    private int version = 0;
    private int detection = 0;

    @Override
    protected void initializeData() {
        intent = getIntent();
        intents();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_credit_buy;
    }

    @Override
    protected void initializeView() {
        titlebar();
        edittextclick();
        click();
    }

    /**
     * 调取网络
     */
    private void httpmore() {
        if (SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, "").equals("")) {

        } else {
            userhttp(SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, ""));
        }
        if (entity == null || entity.getData() == null) {
            detailhttp(subjectId, SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, ""));
        } else {
            detailFindView(entity);
        }
    }

    /**
     * 做页面返回加载的操作
     */
    @Override
    protected void onResume() {
        super.onResume();
        judgment();
        httpmore();
        creditBuyMoneyEt.setText("");
    }

    /**
     * 获取标的id
     */
    private void intents() {
        if (intent.hasExtra("subjectId")) {
            subjectId = intent.getStringExtra("subjectId");
        }
        if (intent.hasExtra("entity")) {
            entity = (DetailEntity) intent.getSerializableExtra("entity");

        }
    }

    /**
     * 标题初始化
     */
    private void titlebar() {
        barTitle.setText("投资");
        barIconBack.setImageResource(R.drawable.nav_back);
        rightIcon.setVisibility(View.GONE);
        rightTitle.setText("协议");
        //返回键
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity(CreditBuy.this);
            }
        });
    }

    /**
     * 判断用户是否登录 是否绑卡 是否实名认证 是否有交易密码
     */
    private void judgment() {
        userInfo = UserInfoUtils.getInstance().getUserInfo(this);
        if (userInfo != null) {
            userInfoBean = userInfo.getData();
        }
        if (userInfoBean != null && PreferenceCongfig.isLogin) {
//            bindBankCard();
        } else {
            buyLogin();
        }
    }

    /**
     * 输入框监听
     */
    private void edittextclick() {
        creditBuyMoneyEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = charSequence.toString();
                if (str.length() == 1) {
                    if (str.equals(".")) {
                        creditBuyMoneyEt.setText("");
                    }
                } else if (str.length() == 2) {
                    if (str.substring(0, 1).equals("0")) {
                        if (str.substring(1, 2).equals("0")) {
                            creditBuyMoneyEt.setText("0");
                            creditBuyMoneyEt.setSelection(1);
                        } else if (!str.substring(1, 2).equals(".")) {
                            creditBuyMoneyEt.setText(str.substring(1, str.length()));
                            creditBuyMoneyEt.setSelection(1);
                        }
                    }
                } else {
                    if (str.contains(".")) {
                        if (str.length() - str.indexOf(".") > 3) {
//                            showToast(str.length() + " " + str.indexOf("."));
                            creditBuyMoneyEt.setText(str.substring(0, str.indexOf(".") + 3));
                            creditBuyMoneyEt.setSelection(str.indexOf(".") + 3);
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(final Editable editable) {

                if (editTextFalg) {
                    editTextFalg = false;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            version++;
                            editTextFalg = true;
                            afterTextChangedSleep(creditBuyMoneyEt.getText().toString(), version);
                        }
                    }, 500);
                }
            }
        });
    }

    private void afterTextChangedSleep(String editable, int version) {
        creditBuyActualTv.setText("");// 实际应付
        creditBuyIncomeTv.setText("");// 预期收益
        if (TextUtils.isEmpty(editable)) {
            creditBuyBtn.setBackground(getResources().getDrawable(R.drawable.select_panic_buying_btn));
            creditBuyActualTv.setText("0.00");
            creditBuyIncomeTv.setText("0.00");
        } else {
            if (!editable.toString().equals("."))
                if (entity != null && entity.getData() != null) {
                    if (PreferenceCongfig.isLogin) {
                        ExpectedReturn(subjectId, editable.toString(), "1", version);
                        if (!TextUtils.isEmpty(entity.getData().getMinTenderedMoney()) && !TextUtils.isEmpty(entity.getData().getMaxTenderedMoney()) && !TextUtils.isEmpty(entity.getData().getRemaMoney()))
                            if (Double.parseDouble(editable.toString()) >= Double.parseDouble(entity.getData().getMinTenderedMoney())
                                    && Double.parseDouble(editable.toString()) <= Double.parseDouble(entity.getData().getMaxTenderedMoney())
                                    && Double.parseDouble(editable.toString()) <= Double.parseDouble(entity.getData().getRemaMoney())) {
                                creditBuyBtn.setBackground(getResources().getDrawable(R.drawable.select_panic_buying_btn));
                            } else {
                                creditBuyBtn.setBackground(getResources().getDrawable(R.drawable.select_panic_buying_btn));
                            }
                    } else {
                        ExpectedReturn(subjectId, editable.toString(), "1", version);
                        if (Double.parseDouble(editable.toString()) >= 100) {
                            creditBuyBtn.setBackground(getResources().getDrawable(R.drawable.select_panic_buying_btn));
                        } else {
                            creditBuyBtn.setBackground(getResources().getDrawable(R.drawable.select_panic_buying_btn));
                        }
                    }
                } else {
                    if (editable.length() < 1) {
                        creditBuyBtn.setBackground(getResources().getDrawable(R.drawable.select_panic_buying_btn));
                        creditBuyActualTv.setText("0.00");
                        creditBuyIncomeTv.setText("0.00");
                    } else {
                        if (Double.parseDouble(editable.toString()) >= 100) {
                            creditBuyBtn.setBackground(getResources().getDrawable(R.drawable.select_panic_buying_btn));
                        } else {
                            creditBuyBtn.setBackground(getResources().getDrawable(R.drawable.select_panic_buying_btn));
                        }
                    }
                }
        }
    }


    private boolean isEmpty() {
        if (userInfoBean != null && moenyEntity != null && entity != null && PreferenceCongfig.isLogin) {// 判断
            return true;
        } else {
            buyLogin();
            return false;
        }
    }

    private boolean bindBankCard(boolean flag) {
        return Singlton.getInstance(DespositAccountManager.class).isCheckOpenDespositAndBindCard(mContext, flag);
    }

    private boolean minTenderedMoney() {
        String money = creditBuyMoneyEt.getText().toString();
        if (!TextUtils.isEmpty(money)) {// 判断输入金额是否合法
            if (Double.parseDouble(money) >= Double.parseDouble(entity.getData().getMinTenderedMoney())) {// 数据大于等于起投金额
                return true;
            } else if (Double.parseDouble(entity.getData().getRemaMoney()) < Double.parseDouble(entity.getData().getMinTenderedMoney())) {// 剩余可投金额 小于起投金额
                return true;
            } else {
                buyMoney("投资金额最低为" + entity.getData().getMinTenderedMoney() + "元,按" + entity.getData().getIncreaseTenderedMoney() + "元的整数倍数递增");
                return false;
            }
        } else {
            buyMoney("投资金额最低为" + entity.getData().getMinTenderedMoney() + "元,按" + entity.getData().getIncreaseTenderedMoney() + "元的整数倍数递增");
            return false;
        }
    }

    private boolean balance() {
        String money = creditBuyMoneyEt.getText().toString();
        if (Double.parseDouble(money) <= moenyEntity.getData().getBalance()) {// 输入金额是否小于个人余额
            if (Double.parseDouble(money) <= Double.parseDouble(entity.getData().getRemaMoney())) { // 数据金额是否小于可投金额
                if (Double.parseDouble(money) <= Double.parseDouble(entity.getData().getMaxTenderedMoney())) {// 数据金额是否小于最大投资金额
                    return true;
                } else {
                    buyMoney("购买金额不可超出最大投资金额" + entity.getData().getMaxTenderedMoney() + "元请重新输入!");
                    return false;
                }
            } else {
                buyMoney("购买金额不可超出" + entity.getData().getRemaMoney() + "元请重新输入!");
                return false;
            }
        } else {
            buyRegter("您的余额不足");
            return false;
        }
    }

    private void status() {
        String money = creditBuyMoneyEt.getText().toString();
        if (entity.getData().getStatus().equals("0")) {
            if (Double.parseDouble(entity.getData().getRemaMoney()) < (Double.parseDouble(entity.getData().getMinTenderedMoney()) * 2)) {// 可投金额是否小于起投金额的两倍
                if (Double.parseDouble(entity.getData().getRemaMoney()) == Double.parseDouble(money)) {
                    purchasehttp(subjectId, money);
                } else {
                    buyfalg = true;
                    buyMoney("购买后剩余额度小于" + entity.getData().getMinTenderedMoney() + "，需一同购买");
                    creditBuyMoneyEt.setText(entity.getData().getRemaMoney());
                }
            } else {
                buyfalg = true;
                buyMoneys();
            }
        } else {
            buyfalg = true;
            ToastUtil.showToast(mContext, "该标不可投资");
        }
    }

    private void buyMoneys() {// 购买
        String money = creditBuyMoneyEt.getText().toString();
        if (Double.parseDouble(money) <= Double.parseDouble(entity.getData().getRemaMoney())) {
            if ((Double.parseDouble(entity.getData().getRemaMoney()) - Double.parseDouble(money)) < Double.parseDouble(entity.getData().getMinTenderedMoney()) && Double.parseDouble(entity.getData().getRemaMoney()) != Double.parseDouble(money)) {
                buyfalg = true;
                buyMoney("购买后剩余额度小于" + entity.getData().getMinTenderedMoney() + "，需一同购买");
                creditBuyMoneyEt.setText(entity.getData().getRemaMoney());
            } else {
                if (((Double.parseDouble(money) - Double.parseDouble(entity.getData().getMinTenderedMoney())) % Double.parseDouble(entity.getData().getIncreaseTenderedMoney())) == 0) {
                    purchasehttp(subjectId, money);
                } else if (Double.parseDouble(money) == Double.parseDouble(entity.getData().getRemaMoney())) {
                    purchasehttp(subjectId, money);
                } else {
                    buyfalg = true;
                    buyMoney("投资金额最低为" + entity.getData().getMinTenderedMoney() + "元,按" + entity.getData().getIncreaseTenderedMoney() + "元的整数倍数递增");
                }
            }
        } else {
            buyfalg = true;
            buyMoney("购买金额不可超出" + entity.getData().getRemaMoney() + "元请重新输入!");
        }
    }

    /**
     * 点击
     */

    private void click() {
        /**
         * 投资
         */
        creditBuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buyfalg) {
                    buyfalg = false;
                    if (isEmpty()) {
                        if (bindBankCard(true)) {
                            if (minTenderedMoney()) {
                                if (balance()) {
                                    status();
                                } else {
                                    buyfalg = true;
                                }
                            } else {
                                buyfalg = true;
                            }
                        } else {
                            buyfalg = true;
                        }
                    } else {
                        buyfalg = true;
                    }

                }
            }
        });
        /**
         * 全投
         */
        creditBuyFullBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity == null) return;
                if (entity.getData() == null) return;
                if (moenyEntity == null) return;
                if (moenyEntity.getData() == null) return;
                if (entity.getData().getTransferAmount() == null) return;
                if (entity.getData().getRemaMoney() == null) return;
                if (entity.getData().getMaxTenderedMoney() == null) return;
                if (isEmpty())
                    if (bindBankCard(false))
                        if (moenyEntity.getCode().equals("0")) {
                            if (entity.getData().getStatus().equals("0")) {
                                if (entity.getData().getMaxTenderedMoney().equals(entity.getData().getTransferAmount())) {
                                    if (Double.parseDouble(entity.getData().getRemaMoney()) > moenyEntity.getData().getBalance()) {
                                        creditBuyMoneyEt.setText(bdu.bigDecimalzoer(moenyEntity.getData().getBalance()));
                                        creditBuyMoneyEt.setSelection(bdu.bigDecimalzoer(moenyEntity.getData().getBalance()).length());
                                    } else {
                                        creditBuyMoneyEt.setText(entity.getData().getRemaMoney());
                                        creditBuyMoneyEt.setSelection(entity.getData().getRemaMoney().length());
                                    }
                                } else {
                                    if (Double.parseDouble(entity.getData().getMaxTenderedMoney()) > moenyEntity.getData().getBalance()) {
                                        if (Double.parseDouble(entity.getData().getRemaMoney()) > moenyEntity.getData().getBalance()) {
                                            creditBuyMoneyEt.setText(bdu.bigDecimalzoer(moenyEntity.getData().getBalance()));
                                            creditBuyMoneyEt.setSelection(bdu.bigDecimalzoer(moenyEntity.getData().getBalance()).length());
                                        } else {
                                            creditBuyMoneyEt.setText(entity.getData().getRemaMoney());
                                            creditBuyMoneyEt.setSelection(entity.getData().getRemaMoney().length());
                                        }
                                    } else {
                                        if (Double.parseDouble(entity.getData().getMaxTenderedMoney()) > Double.parseDouble(entity.getData().getRemaMoney())) {
                                            creditBuyMoneyEt.setText(entity.getData().getRemaMoney());
                                            creditBuyMoneyEt.setSelection(entity.getData().getRemaMoney().length());
                                        } else {
                                            creditBuyMoneyEt.setText(bdu.bigDecimaldouble(Double.parseDouble(entity.getData().getMaxTenderedMoney())));
                                            creditBuyMoneyEt.setSelection(bdu.bigDecimaldouble(Double.parseDouble(entity.getData().getMaxTenderedMoney())).length());
                                        }
                                    }
                                }
                            } else {
                                showToast("该标不可投");
                            }
                        } else {
                            buyLogin();
                        }
            }
        });

        /**
         * 充值
         */
        creditBuyRechargeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInfoBean != null && PreferenceCongfig.isLogin) {
                    if (bindBankCard(false)) {
                        buyRegter("您是否确认去充值");
                    }
                } else {
                    buyLogin();
                }
            }
        });

        /**
         * 提示
         */
        creditBuyPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singlton.getInstance(PopWindowManager.class).
                        showOneButtonDialog(mContext, "提示", "包含认购债权份额及至债转结束日未付利息，到期后由借款人偿还", "确认",
                                new OnDialogClickListener() {
                                    @Override
                                    public void onClick(int id, View v) {
                                        if (id == 2) {

                                        }
                                    }
                                });
            }
        });

        creditBuyFullPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singlton.getInstance(PopWindowManager.class).
                        showOneButtonDialog(mContext, "提示", "全投=账户可用余额/剩余额度二者中最小值的整数", "确认",
                                new OnDialogClickListener() {
                                    @Override
                                    public void onClick(int id, View v) {
                                        if (id == 2) {

                                        }
                                    }
                                });
            }
        });


        /**
         * 协议
         */
        titleBarRightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (entity != null) {
                    Intent intent = new Intent(mContext, WebViewMore.class);
                    intent.putExtra("url", entity.getData().getContractUrl());
                    intent.putExtra("title", "债权转让协议");
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 提示登录弹出框
     */

    private void buyLogin() {
        Singlton.getInstance(PopWindowManager.class).
                showTwoButtonDialog(mContext, "提示", "您还没有登录", "取消", "登录",
                        new OnDialogClickListener() {
                            @Override
                            public void onClick(int id, View v) {
                                if (id == 1) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(PreferenceCongfig.DEBENTURE_TO_LOGIN, PreferenceCongfig.debenture_to_login);
                                    ActivityTools.switchActivity(mContext, LoginActivity.class, bundle);
                                }
                            }
                        });
    }

    /**
     * 提示充值提示框
     *
     * @param str
     */
    private void buyRegter(String str) {
        Singlton.getInstance(PopWindowManager.class).
                showTwoButtonDialog(mContext, "提示", str, "取消", "充值",
                        new OnDialogClickListener() {
                            @Override
                            public void onClick(int id, View v) {
                                if (id == 1) {
                                    startActivity(FillActivity.class);
                                }
                            }
                        });
    }

    /**
     * 购买金额上线提醒
     *
     * @param str
     */
    private void buyMoney(String str) {
        Singlton.getInstance(PopWindowManager.class).
                showOneButtonDialog(mContext, "提示", str, "确定",
                        new OnDialogClickListener() {
                            @Override
                            public void onClick(int id, View v) {
                                if (id == 2) {
                                }
                            }
                        });
    }

    /**
     * 跳转到绑卡界面 实名
     */
    private void bindBank() {
        Intent intent = new Intent(PreferenceCongfig.ACTION_BIND_BANKCARD);
        intent.setClass(mContext, BindBankActivity.class);
        startActivity(intent);
    }

    /**
     * 获取用户余额
     *
     * @param token
     */
    private void userhttp(String token) {
        Singlton.getInstance(FinanceHttp.class).getUserMoney(mContext, token, new OnResponseListener<UserMoenyEntity>() {
            @Override
            public void onSuccess(UserMoenyEntity userMoenyEntity) {
                if (userMoenyEntity == null) return;
                if (userMoenyEntity.getCode().equals("0") && userMoenyEntity.getData() != null) {
                    creditBuyBalanceTv.setText(df.format(userMoenyEntity.getData().getBalance()));
                } else {
//                  ToastUtil.showToast(mContext, userMoenyEntity.msg);
                    creditBuyBalanceTv.setText("--");
                }
                moenyEntity = userMoenyEntity;
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 访问网络
     *
     * @param debtId id
     * @param token
     */
    private void detailhttp(String debtId, String token) {
        Singlton.getInstance(FinanceHttp.class).getDetailFragment(mContext, debtId, token, new OnResponseListener<DetailEntity>() {
            @Override
            public void onSuccess(DetailEntity detailEntity) {
                if (detailEntity == null) return;
                if (detailEntity.getData() == null) return;
                if (detailEntity.getCode().equals("0")) {
                    entity = detailEntity;
                    // 赋值
                    detailFindView(detailEntity);
                } else {
                    ToastUtil.showToast(mContext, detailEntity.getMsg());
                }
            }

            @Override
            public void onFail(Throwable e) {
            }
        });
    }

    /**
     * 赋值
     *
     * @param entity
     */
    private void detailFindView(DetailEntity entity) {
        if (!TextUtils.isEmpty(entity.getData().getDebtTitle()))
            creditBuyTitleTv.setText(entity.getData().getDebtTitle());// 标题
        if (!TextUtils.isEmpty(entity.getData().getTransferRate())) {
            int rale = entity.getData().getTransferRate().indexOf(".");
            creditBuyEarningsTv.setText(entity.getData().getTransferRate().substring(0, rale));// 利率
            creditBuyEarningsTvX.setText(entity.getData().getTransferRate().substring(rale, entity.getData().getTransferRate().length()));
        }
        if (!TextUtils.isEmpty(entity.getData().getDeadLineValue()))
            creditBuyDaysTv.setText(entity.getData().getDeadLineValue());// 投资期限时间
        if (!TextUtils.isEmpty(entity.getData().getDeadLineType()))
            creditBuyDaysUnitTv.setText(entity.getData().getDeadLineType());// 投资期限单位
        if (!TextUtils.isEmpty(entity.getData().getRemaMoney()))
            creditBuyMoneysTv.setText(entity.getData().getRemaMoney());// 剩余金额
        if (!TextUtils.isEmpty(entity.getData().getRemaMoney()))
            if (Double.parseDouble(entity.getData().getRemaMoney()) > 0 && Double.parseDouble(entity.getData().getRemaMoney()) < (Double.parseDouble(entity.getData().getMinTenderedMoney()) * 2)) {
                creditBuyMoneyEt.setHint(entity.getData().getRemaMoney() + "元需一同购买");
            } else {
                if (!TextUtils.isEmpty(entity.getData().getMinTenderedMoney()) && !TextUtils.isEmpty(entity.getData().getIncreaseTenderedMoney()))
                    creditBuyMoneyEt.setHint(dfz.format(Double.parseDouble(entity.getData().getMinTenderedMoney())) + "起投,"
                            + dfz.format(Double.parseDouble(entity.getData().getIncreaseTenderedMoney())) + "元递增");
            }
        for (int i = 0; i < entity.getData().getProductInfoList().size(); i++) {
            if (entity.getData().getProductInfoList().get(i).getType().equals("discount")) {
                discount = entity.getData().getProductInfoList().get(i).getValue().replace("%", "");
            }
        }
    }

    /**
     * 投资
     *
     * @param debtId
     * @param investAmount
     */

    private void purchasehttp(String debtId, String investAmount) {
        buyfalg = true;
        Singlton.getInstance(DespositAccountManager.class).getDebtBuyXWhttp(mContext, debtId, investAmount);
    }

    /**
     * 预期收益
     *
     * @param borrowId     标的id
     * @param investAmount 投资金额
     * @param typeValue    标的类型 1：转让标 0：其他标
     */

    private void ExpectedReturn(String borrowId, String investAmount, String typeValue, final int version) {
        Singlton.getInstance(FinanceHttp.class).getExpectedReturn(this, borrowId, investAmount, typeValue, new OnResponseListener<ExpectedReturn>() {

            @Override
            public void onSuccess(ExpectedReturn expectedReturn) {
                if (creditBuyActualTv == null) return;
                if (creditBuyIncomeTv == null) return;
                if (expectedReturn == null) return;
                if (expectedReturn.getData() == null) return;
                if (expectedReturn.getCode().equals("0")) {
                    if (version >= detection) {// 判断是否是最后一个请求
                        detection = version;
                        creditBuyActualTv.setText(expectedReturn.getData().getRealAmount().trim());// 实际应付
                        creditBuyIncomeTv.setText(expectedReturn.getData().getExpectProfit().trim());// 预期收益
                    }
                } else if (expectedReturn.getCode().equals("1000")) {
                    Singlton.getInstance(PopWindowManager.class).showStopDialogShow(mContext, expectedReturn.getCode(), expectedReturn.getMsg());
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (creditBuyActualTv != null && creditBuyIncomeTv != null) {
                    creditBuyActualTv.setText("");// 实际应付
                    creditBuyIncomeTv.setText("");// 预期收益
                }
            }
        });
    }
}
