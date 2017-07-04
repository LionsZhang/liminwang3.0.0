package com.example.administrator.lmw.finance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
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
import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.config.DespositAccountManager;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseEvent;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.entity.ChoseCouponEvent;
import com.example.administrator.lmw.entity.CommonInfo;
import com.example.administrator.lmw.finance.entity.DetailFragmentEntity;
import com.example.administrator.lmw.finance.entity.ExpectedReturn;
import com.example.administrator.lmw.finance.entity.MatchVerifyEntity;
import com.example.administrator.lmw.finance.entity.UserMoenyEntity;
import com.example.administrator.lmw.finance.financehttp.FinanceHttp;
import com.example.administrator.lmw.finance.utils.BigDecimalUtils;
import com.example.administrator.lmw.finance.utils.ProblemDisplayUtils;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.login.UserInfoUtils;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.login.entity.UserInfoBean;
import com.example.administrator.lmw.mine.card.CashCouponActivity;
import com.example.administrator.lmw.mine.card.cardhttp.CardHttp;
import com.example.administrator.lmw.mine.card.entity.CashCouponBeanInfo;
import com.example.administrator.lmw.mine.card.entity.DataCashCoupon;
import com.example.administrator.lmw.mine.fill.FillActivity;
import com.example.administrator.lmw.mine.invest.utils.ListDialogListener;
import com.example.administrator.lmw.mine.invest.utils.ListOneDialog;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.mine.seting.BindBankActivity;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.BigDecemalCalculateUtil;
import com.example.administrator.lmw.utils.NumberParserUtil;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.InjectView;

/**
 * 理财购买页面
 * <p>
 * Created by Administrator on 2016/8/31 0031.
 */
public class ProblemBuy extends BaseActivity {

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
    @InjectView(R.id.problem_buy_title_tv)
    TextView problemBuyTitleTv;
    @InjectView(R.id.problem_buy_earnings_tv)
    TextView problemBuyEarningsTv;
    @InjectView(R.id.problem_buy_earnings_tv_x)
    TextView problemBuyEarningsTvX;
    @InjectView(R.id.problem_buy_days_tv)
    TextView problemBuyDaysTv;
    @InjectView(R.id.problem_buy_days_unit_tv)
    TextView problemBuyDaysUnitTv;
    @InjectView(R.id.problem_buy_moneys_tv)
    TextView problemBuyMoneysTv;
    @InjectView(R.id.problem_buy_money_et)
    EditText problemBuyMoneyEt;
    @InjectView(R.id.problem_buy_full_btn)
    Button problemBuyFullBtn;
    @InjectView(R.id.problem_buy_income_tv)
    TextView problemBuyIncomeTv;
    @InjectView(R.id.problem_buy_balance_tv)
    TextView problemBuyBalanceTv;
    @InjectView(R.id.problem_buy_coupons_tv_c)
    TextView problemBuyCouponsTvC;
    @InjectView(R.id.problem_buy_recharge_btn)
    Button problemBuyRechargeBtn;
    @InjectView(R.id.problem_buy_coupons_lin)
    LinearLayout problemBuyCouponsLin;
    @InjectView(R.id.problem_buy_btn)
    Button problemBuyBtn;
    @InjectView(R.id.problem_buy_full_prompt)
    ImageView problemBuyFullPrompt;
    @InjectView(R.id.problem_buy_coupons_iv)
    ImageView problemBuyCouponsIv;
    @InjectView(R.id.problem_buy_income_lin)
    LinearLayout problemBuyIncomeLin;
    @InjectView(R.id.problem_buy_income_yq_lin)
    LinearLayout problemBuyIncomeYqLin;
    @InjectView(R.id.problem_buy_income_tj)
    TextView problemBuyIncomeTj;
    @InjectView(R.id.problem_buy_coupons_explanation_iv)
    TextView problemBuyCouponsExplanationIv;
    @InjectView(R.id.modify_tv)
    TextView modify_tv;
    @InjectView(R.id.problem_buy_jsq)
    ImageView problemBuyJsq;
    @InjectView(R.id.problem_buy_continue_tv)
    TextView problemBuyContinueTv;
    @InjectView(R.id.problem_buy_continue_rel)
    RelativeLayout problemBuyContinueRel;
    @InjectView(R.id.problem_buy_continue_about_iv)
    ImageView problemBuyContinueAboutIv;
    @InjectView(R.id.problem_buy_yields_tv)
    TextView problemBuyYieldsTv;
    @InjectView(R.id.problem_buy_head_tv)
    TextView problemBuyHeadTv;
    @InjectView(R.id.problem_buy_yields_tv_per)
    TextView problemBuyYieldsTvPer;
    @InjectView(R.id.problem_buy_lin)
    LinearLayout problemBuyLin;
    @InjectView(R.id.problem_buy_types_tv)
    TextView problemBuyTypesTv;
    private DetailFragmentEntity entity;// 标的信息
    private UserMoenyEntity moenyEntity;// 用户可用余额
    private String subjectId;
    private DecimalFormat df = new DecimalFormat("##0.00");
    private DecimalFormat dfz = new DecimalFormat("##0");
    private UserInfo userInfo;
    private UserInfoBean userInfoBean;
    private BigDecimalUtils bdu = new BigDecimalUtils();
    private Intent intent;
    private boolean avaiCash = false;// 判断是否有现金券
    private String selectedCouponId;//已选择的现金券Id
    private String avaiCashNum; // 总共有多少张现金券
    private CommonInfo commonInfo;
    private ListOneDialog listOneDialog;
    private String continueFlag = "-1";// 续投标识，-1-不续投、0-本息续投、1-本金续投、2-利息续投
    private boolean buyfalg = true; // 购买按钮点击标识
    private boolean editTextFalg = true;
    private int version = 0;
    private int gainDetection = 0;

    @Override
    protected void initializeData() {
        commonInfo = ConfigManager.getInstance().getCommonInfo(mContext);
        EventBus.getDefault().register(this);
        intent = getIntent();
        intents();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_problem_buy;
    }

    @Override
    protected void initializeView() {
        Titlebar();
        edittextclick();
        click();
    }

    private void initCashCouponShow() {
        problemBuyCouponsTvC.setText("无匹配优惠券");
        problemBuyCouponsTvC.setTextColor(ContextCompat.getColor(mContext, R.color.text_balck));
        problemBuyCouponsIv.setVisibility(View.VISIBLE);
        problemBuyCouponsExplanationIv.setVisibility(View.VISIBLE);
        modify_tv.setVisibility(View.GONE);
        problemBuyCouponsExplanationIv.setText("共" + avaiCashNum + "张 已选择0张");
        selectedCouponId = "";
        couponAmount = "";
    }

    /**
     * 调取网络
     */
    private void httpmore() {
        if (entity == null || entity.getData() == null) {
            producthttp(subjectId, SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, ""));
        } else {
            // 赋值实例化
            Singlton.getInstance(ProblemDisplayUtils.class).showProductFindView(entity, problemBuyContinueRel, problemBuyTitleTv,
                    problemBuyMoneysTv, problemBuyMoneyEt);
            // 利率 期限 赋值
            Singlton.getInstance(ProblemDisplayUtils.class).showAnnualRate(entity, problemBuyLin, problemBuyEarningsTv,
                    problemBuyEarningsTvX, problemBuyYieldsTv, problemBuyYieldsTvPer, problemBuyHeadTv, problemBuyDaysTv, problemBuyDaysUnitTv);
            // 可用现金券
            initShowCashCouponState();
        }
        if (!TextUtils.isEmpty(SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, ""))) {
            problemBuyIncomeYqLin.setVisibility(View.GONE);
            problemBuyIncomeLin.setVisibility(View.VISIBLE);
        } else {
            problemBuyIncomeYqLin.setVisibility(View.GONE);
            problemBuyIncomeLin.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取个人信息
     */
    private void httpUser() {
        if (!TextUtils.isEmpty(SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, "")))
            userhttp(SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, ""));
    }

    /**
     * 做页面返回加载的操作
     */
    @Override
    protected void onResume() {
        super.onResume();
        judgment();
        httpUser();
        httpmore();
    }

    /**
     * 获取标的id
     */
    private void intents() {
        if (intent.hasExtra("subjectId")) {
            subjectId = intent.getStringExtra("subjectId");
        }
        if (intent.hasExtra("entity")) {
            entity = (DetailFragmentEntity) intent.getSerializableExtra("entity");
        }
    }

    /**
     * 标题初始化
     */
    private void Titlebar() {
        barTitle.setText("投资");
        barIconBack.setImageResource(R.drawable.nav_back);
        rightIcon.setVisibility(View.GONE);
        rightTitle.setText("协议");
        //返回键
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity(ProblemBuy.this);
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
        problemBuyMoneyEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = charSequence.toString();
                if (str.length() == 2) {
                    if (str.substring(0, 1).equals("0")) {
                        if (str.substring(1, 2).equals("0")) {
                            problemBuyMoneyEt.setText("0");
                            problemBuyMoneyEt.setSelection(1);
                        } else {
                            problemBuyMoneyEt.setText(str.substring(1, str.length()));
                            problemBuyMoneyEt.setSelection(1);
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
                            editjudgment(problemBuyMoneyEt.getText().toString(), version);
                        }
                    }, 500);
                }
            }
        });

    }

    /**
     * 监听editable 逻辑判断
     *
     * @param editable
     */
    private void editjudgment(String editable, int version) {
        problemBuyIncomeTv.setText("");
        if (entity != null && entity.getData() != null) {
            if (editable.length() < 1) {
                problemBuyIncomeTv.setText("--/--");
                problemBuyIncomeYqLin.setVisibility(View.GONE);
                problemBuyIncomeLin.setVisibility(View.VISIBLE);
                problemBuyIncomeTj.setText("填写投资金额");
            } else {
                ExpectedReturn(subjectId, editable, "0", version);
                if (TextUtils.isEmpty(editable)) {
                    initShowCashCouponState();
                }
                if (TextUtils.isEmpty(entity.getData().getMinTenderedMoney()))
                    return;
                if (TextUtils.isEmpty(entity.getData().getRemaMoney())) return;
                if ((Double.parseDouble(entity.getData().getMinTenderedMoney()) * 2) > Double.parseDouble(entity.getData().getRemaMoney())) {
                    if (Double.parseDouble(editable) < Double.parseDouble(entity.getData().getRemaMoney())) {
                        problemBuyIncomeYqLin.setVisibility(View.GONE);
                        problemBuyIncomeLin.setVisibility(View.VISIBLE);
                        problemBuyIncomeTj.setText(entity.getData().getRemaMoney() + "元需一同购买");
                    } else if (Double.parseDouble(editable) == Double.parseDouble(entity.getData().getRemaMoney())) {
                        problemBuyIncomeYqLin.setVisibility(View.VISIBLE);
                        problemBuyIncomeLin.setVisibility(View.GONE);
                        if (!TextUtils.isEmpty(avaiCashNum) && Double.parseDouble(avaiCashNum) != 0)
                            cashmatchhttp(subjectId, "0", editable, false);
                    } else {
                        problemBuyIncomeYqLin.setVisibility(View.GONE);
                        problemBuyIncomeLin.setVisibility(View.VISIBLE);
                        problemBuyIncomeTj.setText(entity.getData().getRemaMoney() + "元需一同购买");
                    }
                } else {
                    if (Double.parseDouble(editable) >= Double.parseDouble(entity.getData().getMinTenderedMoney())
                            && Double.parseDouble(editable) <= Double.parseDouble(entity.getData().getRemaMoney())) {
                        problemBuyIncomeYqLin.setVisibility(View.VISIBLE);
                        problemBuyIncomeLin.setVisibility(View.GONE);
                        if (!TextUtils.isEmpty(avaiCashNum) && Double.parseDouble(avaiCashNum) != 0)
                            cashmatchhttp(subjectId, "0", editable, false);
                    } else {
                        problemBuyIncomeYqLin.setVisibility(View.GONE);
                        problemBuyIncomeLin.setVisibility(View.VISIBLE);
                        problemBuyIncomeTj.setText("请输入一个介于" + dfz.format(Double.parseDouble(entity.getData().getMinTenderedMoney())) + "和" + bdu.bigDecimalzoer(Double.parseDouble(entity.getData().getRemaMoney())) + "之间的值");
                    }
                }

                if (Double.parseDouble(editable.toString()) < Double.parseDouble(entity.getData().getMinTenderedMoney())) {
                    if (!TextUtils.isEmpty(avaiCashNum) && Double.parseDouble(avaiCashNum) != 0) {
                        problemBuyCouponsTvC.setText("无匹配优惠券");
                        problemBuyCouponsTvC.setTextColor(ContextCompat.getColor(mContext, R.color.text_balck));
                        problemBuyCouponsIv.setVisibility(View.VISIBLE);
                        problemBuyCouponsExplanationIv.setVisibility(View.VISIBLE);
                        modify_tv.setVisibility(View.GONE);
                        problemBuyCouponsExplanationIv.setText("共" + avaiCashNum + "张 已选择0张现金劵");
                        selectedCouponId = "";
                    }
                }
            }
        }
    }

    /**
     * 点击购买 逻辑判断
     *
     * @return
     */

    private boolean isEmpty() {
        if (userInfoBean != null && entity != null && moenyEntity != null && PreferenceCongfig.isLogin) {// 判断
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
        String money = problemBuyMoneyEt.getText().toString();
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
        String money = problemBuyMoneyEt.getText().toString();
        if (Double.parseDouble(money) <= moenyEntity.getData().getBalance()) {// 输入金额是否小于个人余额
            if (Double.parseDouble(money) <= Double.parseDouble(entity.getData().getRemaMoney())) { // 数据金额是否小于可投金额
                if (Double.parseDouble(money) <= Double.parseDouble(entity.getData().getSingleMaxTenderedMoney())) {// 数据金额是否小于可投金额
                    return true;
                } else {
                    buyMoney("您输入的购买金额超过了您的可购份额，当前剩余可购份额为" + entity.getData().getSingleMaxTenderedMoney() + "元，请重新输入");
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

    private boolean singleMaxTenderedMoney() {
        if (Double.parseDouble(entity.getData().getSingleMaxTenderedMoney()) > 0) {
            if (entity.getData().getStatus().equals("3") || entity.getData().getStatus().equals("5")) {
                return true;
            } else {
                ToastUtil.showToast(mContext, "该标不可投资");
                return false;
            }
        } else {
            buyMoney("您输入的购买金额超过了您的可购份额，当前剩余可购份额为" + entity.getData().getSingleMaxTenderedMoney() + "元");
            return false;
        }
    }

    private void borrowType() {
        if (entity.getData().getBorrowType().equals("7") && entity.getData().getBuyedNovice().equals("1")) {
            showToast("仅能购买一次，你已购买一次");
            buyfalg = true;
        } else {
            buyMoneys();
        }
    }

    private void buyMoneys() {
        String money = problemBuyMoneyEt.getText().toString();
        if (Double.parseDouble(entity.getData().getRemaMoney()) < (Double.parseDouble(entity.getData().getMinTenderedMoney()) * 2)) {
            if (Double.parseDouble(entity.getData().getRemaMoney()) == Double.parseDouble(money)) {
//                paypasswordDialog(money);
//                getMatchVerify(money);
                // 是否续投过,0-没有、1-续投过
                if (TextUtils.equals(userInfoBean.getContinuedFlag(), "0") && !TextUtils.equals(entity.getData().getBorrowType(), "7")) {
                    showContinuousVote();
                } else {
                    getMatchVerify(money);
                }
            } else {
                buyfalg = true;
                buyMoney("购买后剩余额度小于" + entity.getData().getMinTenderedMoney() + "，需一同购买");
                problemBuyMoneyEt.setText(entity.getData().getRemaMoney());
            }
        } else {
            if (Double.parseDouble(money) <= Double.parseDouble(entity.getData().getRemaMoney())) {
                if ((Double.parseDouble(entity.getData().getRemaMoney()) - Double.parseDouble(money)) < Double.parseDouble(entity.getData().getMinTenderedMoney()) && Double.parseDouble(entity.getData().getRemaMoney()) != Double.parseDouble(money)) {
                    buyfalg = true;
                    buyMoney("购买后剩余额度小于" + entity.getData().getMinTenderedMoney() + "，需一同购买");
                    problemBuyMoneyEt.setText(entity.getData().getRemaMoney());
                } else {
                    if (((Double.parseDouble(money) - Double.parseDouble(entity.getData().getMinTenderedMoney())) % Double.parseDouble(entity.getData().getIncreaseTenderedMoney())) == 0) {
//                        paypasswordDialog(money);
//                        getMatchVerify(money);
                        // 是否续投过,0-没有、1-续投过
                        if (TextUtils.equals(userInfoBean.getContinuedFlag(), "0") && !TextUtils.equals(entity.getData().getBorrowType(), "7")) {
                            showContinuousVote();
                        } else {
                            getMatchVerify(money);
                        }
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
    }

    /**
     * 整合 逻辑判断
     *
     * @return
     */
    private boolean Integration() {
        if (isEmpty()) {
            if (bindBankCard(true)) {
                if (minTenderedMoney()) {
                    return true;
                } else {
                    buyfalg = true;
                    return false;
                }
            } else {
                buyfalg = true;
                return false;
            }
        } else {
            buyfalg = true;
            return false;
        }
    }

    private void buy() {
        if (balance()) {
            if (singleMaxTenderedMoney()) {
                borrowType();
            } else {
                buyfalg = true;
            }
        } else {
            buyfalg = true;
        }
    }

    /**
     * 续投提示
     */
    private void showContinuousVote() {
        if (commonInfo != null) {
            final String money = problemBuyMoneyEt.getText().toString();
            String tipsUrl = commonInfo.getContinue_invest_tips_url();
            Singlton.getInstance(PopWindowManager.class).showContinuousDialog(mContext, "续投提示", "", "", "我知道了", tipsUrl, new OnDialogClickListener() {
                @Override
                public void onClick(int id, View v) {
                    if (id == 2) {
                        getContinuedFlag("1");
                        getMatchVerify(money);
                        keepUseInfo();
                    } else {
                        buyfalg = true;
                    }
                }
            });
        } else {
            buyfalg = true;
        }
    }

    /**
     * 点击
     */
    private void click() {
        /**
         * 投资
         */
        problemBuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buyfalg) {
                    buyfalg = false;
                    if (Integration())
                        buy();
                }
            }
        });
        /**
         * 全投
         */
        problemBuyFullBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity == null) return;
                if (entity.getData() == null) return;
                if (moenyEntity == null) return;
                if (moenyEntity.getData() == null) return;
                if (entity.getData().getBuyTotalAmount() == null) return;
                if (entity.getData().getRemaMoney() == null) return;
                if (entity.getData().getSingleMaxTenderedMoney() == null) return;
                if (entity.getData().getStatus() == null) return;
                if (isEmpty())
                    if (bindBankCard(false)) {
                        if (TextUtils.equals(moenyEntity.getCode(), "0")) {
                            if (TextUtils.equals(entity.getData().getStatus(), "3") || TextUtils.equals(entity.getData().getStatus(), "5")) {
                                if (TextUtils.equals(entity.getData().getSingleMaxTenderedMoney(), entity.getData().getBuyTotalAmount())) {
                                    if (Double.parseDouble(entity.getData().getRemaMoney()) > moenyEntity.getData().getBalance()) {
                                        problemBuyMoneyEt.setText(bdu.bigDecimalzoer(moenyEntity.getData().getBalance()));
                                        problemBuyMoneyEt.setSelection(bdu.bigDecimalzoer(moenyEntity.getData().getBalance()).length());
                                    } else {
                                        problemBuyMoneyEt.setText(entity.getData().getRemaMoney());
                                        problemBuyMoneyEt.setSelection(entity.getData().getRemaMoney().length());
                                    }
                                } else {
                                    if (Double.parseDouble(entity.getData().getSingleMaxTenderedMoney()) > moenyEntity.getData().getBalance()) {
                                        if (Double.parseDouble(entity.getData().getRemaMoney()) > moenyEntity.getData().getBalance()) {
                                            problemBuyMoneyEt.setText(bdu.bigDecimalzoer(moenyEntity.getData().getBalance()));
                                            problemBuyMoneyEt.setSelection(bdu.bigDecimalzoer(moenyEntity.getData().getBalance()).length());
                                        } else {
                                            problemBuyMoneyEt.setText(entity.getData().getRemaMoney());
                                            problemBuyMoneyEt.setSelection(entity.getData().getRemaMoney().length());
                                        }
                                    } else {
                                        if (Double.parseDouble(entity.getData().getSingleMaxTenderedMoney()) > Double.parseDouble(entity.getData().getRemaMoney())) {
                                            problemBuyMoneyEt.setText(entity.getData().getRemaMoney());
                                            problemBuyMoneyEt.setSelection(entity.getData().getRemaMoney().length());
                                        } else {
                                            problemBuyMoneyEt.setText(bdu.bigDecimalzoer(Double.parseDouble(entity.getData().getSingleMaxTenderedMoney())));
                                            problemBuyMoneyEt.setSelection(bdu.bigDecimalzoer(Double.parseDouble(entity.getData().getSingleMaxTenderedMoney())).length());
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
            }
        });

        /**
         * 充值
         */
        problemBuyRechargeBtn.setOnClickListener(new View.OnClickListener() {
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
         * 获取现金券
         */
        problemBuyCouponsLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceCongfig.isLogin) {
                    if (avaiCash) { // 判断是否有现金券
                        //跳到选中现金券页面
                        Bundle bundle = new Bundle();
                        bundle.putString(CashCouponActivity.BUS_ID, subjectId);//业务id，标的Id或者债权Id
                        bundle.putString(CashCouponActivity.BUS_TYPE, "0");//业务类型，0-标的、1-债权
                        bundle.putString(CashCouponActivity.SELECTED_COUPON_ID, selectedCouponId);//已经选择的现金券Id
                        bundle.putString(CashCouponActivity.INVEST_AMOUNT, problemBuyMoneyEt.getText().toString());//投资金额
                        ActivityTools.switchActivity(ProblemBuy.this, CashCouponActivity.class, bundle);
                    }
                } else {
//                    buyLogin();
                }
            }
        });

        // 协议
        titleBarRightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (entity != null && entity.getData() != null) {
                    Intent intent = new Intent(mContext, WebViewMore.class);
                    intent.putExtra("url", entity.getData().getContractUrl());
                    intent.putExtra("title", "投资协议");
                    startActivity(intent);
                    problemBuyMoneyEt.setText("");
                }
            }
        });
        // 全投提示
        problemBuyFullPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity != null && entity.getData() != null && entity.getData().getBorrowType() != null) {
                    if (entity.getData().getBorrowType().equals("7")) {
                        showLeftDialog("提示", "全投=账户可用余额/该标剩余认购上限额度三者中最小值的整数", "确认");
                    } else {
                        showLeftDialog("提示", "全投=账户可用余额/标的剩余额度/该标剩余认购上限额度三者中最小值的整数", "确认");
                    }
                }
            }
        });

        /**
         * 回款续投方式提示
         */
        problemBuyContinueAboutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commonInfo != null) {
                    String center = commonInfo.getContinue_invest_way_explain();
                    if (!TextUtils.isEmpty(center))
                        showLeftDialog("续投方式", Html.fromHtml(center).toString(), "我知道了");
                }
            }
        });

        /**
         * 回款续投方式选择 续投标识,0-不支持续投、1-支持续投
         */
        problemBuyContinueTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity != null && entity.getData() != null && !TextUtils.isEmpty(entity.getData().getContinueFlag()) && entity.getData().getContinueFlag().equals("1")) {
                    List<String> listValue = new ArrayList<>();
                    List<String> listKey = new ArrayList<>();
                    List<Boolean> listFalg = new ArrayList<>();
                    for (int i = 0; i < entity.getData().getContinueTypeList().size(); i++) {
                        listValue.add(entity.getData().getContinueTypeList().get(i).getValue());
                        listKey.add(entity.getData().getContinueTypeList().get(i).getType());
                        if (continueFlag.equals(entity.getData().getContinueTypeList().get(i).getType())) {
                            listFalg.add(true);
                        } else {
                            listFalg.add(false);
                        }
                    }
                    listOneDialog = new ListOneDialog(ProblemBuy.this, listValue, listKey, listFalg, "续投方式选择", new ListDialogListener() {
                        @Override
                        public void onClick(int id, String value, String key, List<Boolean> falg) {
                            if (id == 1) {
                                problemBuyContinueTv.setText(value);
                                continueFlag = key;
                            }
                        }
                    });
                }
            }
        });
        /**
         * 收益计算器
         */
        problemBuyJsq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commonInfo != null) {// income_calculator
                    Intent intent = new Intent(mContext, WebViewMore.class);
                    intent.putExtra("url", commonInfo.income_calculator);
                    intent.putExtra("title", "理财计算器");
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 单个按钮左对齐弹出框
     *
     * @param titleStr
     * @param centerStr
     * @param btnStr
     */
    private void showLeftDialog(String titleStr, String centerStr, String btnStr) {
        Singlton.getInstance(PopWindowManager.class).showLeftOnebtnDialog(mContext, titleStr, centerStr, btnStr, new OnDialogClickListener() {
            @Override
            public void onClick(int id, View v) {

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
                                    entity = new DetailFragmentEntity();
                                    problemBuyMoneyEt.setText("");
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(PreferenceCongfig.PURCHASE_TO_LOGIN, PreferenceCongfig.purchase_to_login);
                                    ActivityTools.switchActivity(mContext, LoginActivity.class, bundle);
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
                                    entity = new DetailFragmentEntity();
                                    problemBuyMoneyEt.setText("");
                                    startActivity(FillActivity.class);
                                }
                            }
                        });
    }

    /**
     * 跳转银行卡
     */
    private void bindBank() {
        entity = new DetailFragmentEntity();
        problemBuyMoneyEt.setText("");
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
                if (userMoenyEntity.getData() == null) return;
                if (userMoenyEntity.getCode().equals("0")) {
                    problemBuyBalanceTv.setText(df.format(userMoenyEntity.getData().getBalance()));
                } else {
                    problemBuyBalanceTv.setText("--");
                }
                moenyEntity = userMoenyEntity;
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 获取标的信息
     *
     * @param borrowId
     * @param token
     */
    private void producthttp(String borrowId, String token) {
        Singlton.getInstance(FinanceHttp.class).getDetailProductFragment(mContext, borrowId, token, new OnResponseListener<DetailFragmentEntity>() {
            @Override
            public void onSuccess(DetailFragmentEntity detailFragmentEntity) {
                if (detailFragmentEntity == null) return;
                if (detailFragmentEntity.getData() == null) return;
                if (TextUtils.equals(detailFragmentEntity.getCode(), "0")) {
                    entity = detailFragmentEntity;
                    // 赋值实例化
                    Singlton.getInstance(ProblemDisplayUtils.class).showProductFindView(entity, problemBuyContinueRel, problemBuyTitleTv,
                            problemBuyMoneysTv, problemBuyMoneyEt);
                    // 利率 期限 赋值
                    Singlton.getInstance(ProblemDisplayUtils.class).showAnnualRate(entity, problemBuyLin, problemBuyEarningsTv,
                            problemBuyEarningsTvX, problemBuyYieldsTv, problemBuyYieldsTvPer, problemBuyHeadTv, problemBuyDaysTv, problemBuyDaysUnitTv);
                    // 可用现金券
                    initShowCashCouponState();
                }
            }

            @Override
            public void onFail(Throwable e) {
            }
        });
    }

    private void initShowCashCouponState() {
        if (!TextUtils.isEmpty(entity.getData().getAvaiCashNum())) {
            if (Double.parseDouble(entity.getData().getAvaiCashNum()) <= 0) {
                if (!selectCashCouponTag) {
                    problemBuyCouponsTvC.setText("无可用优惠券");
                    problemBuyCouponsTvC.setTextColor(ContextCompat.getColor(mContext, R.color.text_gray));
                    problemBuyCouponsIv.setVisibility(View.INVISIBLE);
                    modify_tv.setVisibility(View.GONE);
                    problemBuyCouponsExplanationIv.setVisibility(View.INVISIBLE);
                    selectedCouponId = "";
                }
                avaiCash = false;
            } else if (!selectCashCouponTag) {
                problemBuyCouponsTvC.setText(entity.getData().getAvaiCashNum() + "张可用");
                problemBuyCouponsTvC.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue));
                problemBuyCouponsIv.setVisibility(View.VISIBLE);
                problemBuyCouponsExplanationIv.setVisibility(View.INVISIBLE);
                modify_tv.setVisibility(View.GONE);
                avaiCash = true;
            }
            avaiCashNum = entity.getData().getAvaiCashNum();
        } else {
            problemBuyCouponsTvC.setText("无可用优惠券");
            problemBuyCouponsTvC.setTextColor(ContextCompat.getColor(mContext, R.color.text_gray));
            problemBuyCouponsIv.setVisibility(View.INVISIBLE);
            modify_tv.setVisibility(View.GONE);
            problemBuyCouponsExplanationIv.setVisibility(View.INVISIBLE);
            selectedCouponId = "";
        }
    }

    /**
     * 获取现金券
     *
     * @param busId
     * @param busType
     * @param investAmount
     * @param casefalg      现金券接口调用位置判断 true 校验不成功重新获取现金券 false 普通情况获取现金券
     */
    private int mCanUseCouponNum;//现金券数量
    private String couponAmount;//现金券金额
    private String usableStatus;//最优现金券使用状态
    private String couponType;//券类型(0现金券;2抵扣券;3加息券;4理财金)
    private String couponProfit;//卡券收益(仅对加息券、理财金有效)
    private String maxActiveDay;//理财天数或加息天数

    private void cashmatchhttp(String busId, String busType, final String investAmount, final boolean casefalg) {
        final String nonceStr = "request_cash_coupon_" + UUID.randomUUID();//现金券接口请求Id唯一标示
        Singlton.getInstance(CardHttp.class).getcashCouponList(mContext, busId, busType,
                investAmount, nonceStr, new OnResponseListener<BaseResult<DataCashCoupon>>() {
                    @Override
                    public void onSuccess(BaseResult<DataCashCoupon> response) {
                        hideLoadingDialog();
                        if (response == null) return;
                        int code = NumberParserUtil.parserInt(response.getCode(), -1);
                        if (code == 0) {
                            if (response.getData() != null) {
                                String returnedNonceStr = response.getData().getNonceStr();
                                if (!TextUtils.isEmpty(returnedNonceStr) && returnedNonceStr.equals(nonceStr)) {
                                    DataCashCoupon cashCouponBean = response.getData();
                                    mCanUseCouponNum = cashCouponBean.getNum();
                                    if (cashCouponBean.getDatas() != null) {
                                        CashCouponBeanInfo cashCouponBeanInfo = cashCouponBean.getDatas().get(0);
                                        if (cashCouponBeanInfo != null) {
                                            couponAmount = cashCouponBeanInfo.getCouponAmount();
                                            usableStatus = cashCouponBeanInfo.getUsableStatus();
                                            selectedCouponId = cashCouponBeanInfo.getCouponId();
                                            couponType = cashCouponBeanInfo.getCouponType();
                                            couponProfit = cashCouponBeanInfo.getCouponProfit();
                                            maxActiveDay = cashCouponBeanInfo.getRealActiveDay();
                                        }
                                    }
                                    if (mCanUseCouponNum <= 0)
                                        initCashCouponShow();
                                    else
                                        showCashCouponInfo(investAmount);
                                    // 现金券不为空 且调用接口入口是校验位置时
                                    if (!TextUtils.isEmpty(selectedCouponId) && casefalg) {
//                                        paypasswordDialog(investAmount);
                                        buyfalg = true;
                                        problemBuyMoneyEt.setText("");
                                        purchasehttp(subjectId, continueFlag, selectedCouponId, investAmount);
                                    }
                                }
                            }
                        } else {
                            //onLoad();
                            showToast(response.getMsg());
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        hideLoadingDialog();
                    }
                });
    }

    private void showCashCouponInfo(String investAmount) {
        if (problemBuyCouponsTvC == null) return;
        if (problemBuyCouponsIv == null) return;
        if (modify_tv == null) return;
        if (problemBuyCouponsExplanationIv == null) return;
        if (!TextUtils.isEmpty(usableStatus) && usableStatus.equals("1")) {
            switch (Integer.valueOf(couponType)) {//券类型(0现金券;2抵扣券;3加息券;4理财金)
                case 0:
                    if (!TextUtils.isEmpty(couponAmount) && !TextUtils.isEmpty(selectedCouponId)) {
                        problemBuyCouponsTvC.setText(couponAmount);
                        modify_tv.setText("元现金券");
                        problemBuyCouponsExplanationIv.setText("共" + avaiCashNum + "张 已选择1张现金劵");
                    }
                    break;
                case 2:
                    if (!TextUtils.isEmpty(couponAmount) && !TextUtils.isEmpty(selectedCouponId)) {
                        problemBuyCouponsTvC.setText(couponAmount);
                        modify_tv.setText("元抵扣券");
                        problemBuyCouponsExplanationIv.setText("实际支付：" + BigDecemalCalculateUtil.subtractToString(investAmount, couponAmount, 2) + "元");
                    }
                    break;
                case 3:
                    if (!TextUtils.isEmpty(couponAmount) && !TextUtils.isEmpty(selectedCouponId)) {
                        problemBuyCouponsTvC.setText(couponAmount);
                        modify_tv.setText("%加息券");
                        problemBuyCouponsExplanationIv.setText("加息" + maxActiveDay + "天，预计加息收益" + couponProfit + "元");
                    }
                    break;
                case 4:
                    if (!TextUtils.isEmpty(couponAmount) && !TextUtils.isEmpty(selectedCouponId)) {
                        problemBuyCouponsTvC.setText(couponAmount);
                        modify_tv.setText("元理财金");
                        problemBuyCouponsExplanationIv.setText("预计理财金收益" + couponProfit + "元");
                    }
                    break;
            }
            if (!TextUtils.isEmpty(couponAmount) && !TextUtils.isEmpty(selectedCouponId)) {
                problemBuyCouponsTvC.setTextColor(ContextCompat.getColor(mContext, R.color.select_list_detail));
                problemBuyCouponsIv.setVisibility(View.VISIBLE);
                problemBuyCouponsExplanationIv.setVisibility(View.VISIBLE);
                modify_tv.setVisibility(View.VISIBLE);
                selectedCouponId = selectedCouponId;
            }
        } else {
            initCashCouponShow();
        }
    }

    /**
     * 投资
     *
     * @param busId        标的id
     * @param continueFlag 续投标识，-1-不续投、0-本息续投、1-本金续投、2-利息续投
     * @param couponId     代金券id
     * @param investAmount 支付金额
     */

    private void purchasehttp(String busId, final String continueFlag, final String couponId, final String investAmount) {
        Singlton.getInstance(DespositAccountManager.class).dpurchasehttp(mContext, busId, continueFlag, couponId, investAmount);
    }

    /**
     * 保持用户是否续投到本地信息
     */
    private void keepUseInfo() {
        userInfoBean.setContinuedFlag("1");
        userInfo.setData(userInfoBean);
        UserInfoUtils.getInstance().setUserInfo(mContext, userInfo);
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
                if (problemBuyIncomeTv == null) return;
                if (expectedReturn == null) return;
                if (expectedReturn.getData() == null) return;
                if (expectedReturn.getCode().equals("0")) {
                    if (version >= gainDetection) {
                        gainDetection = version;
                        if (TextUtils.equals(expectedReturn.getData().getIsFlow(), "2")) {
                            problemBuyIncomeTv.setText(expectedReturn.getData().getMinExpectProfit() + "~" + expectedReturn.getData().getMaxExpectProfit());
                        } else {
                            problemBuyIncomeTv.setText(expectedReturn.getData().getExpectProfit().trim());
                        }
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (problemBuyIncomeTv != null) {
                    problemBuyIncomeTv.setText("");
                }
            }
        });
    }

    /**
     * 查询卡券校验V3
     * <p>
     * subjectId       标的Id
     * investAmount    金额
     * selectedCouponId       卡券ID
     */
    private void getMatchVerify(final String investAmount) {
        Singlton.getInstance(FinanceHttp.class).getMatchVerify(this, subjectId, selectedCouponId, investAmount, new OnResponseListener<MatchVerifyEntity>() {
            @Override
            public void onSuccess(MatchVerifyEntity matchVerifyEntity) {
                if (matchVerifyEntity == null) return;
                if (matchVerifyEntity.getData() == null) return;
                if (matchVerifyEntity.getCode().equals("0")) {
                    // 验证码(0:通过;1:非最优;2:不匹配;3:未使用)
                    if (matchVerifyEntity.getData().getVerifyCode().equals("0") || matchVerifyEntity.getData().getVerifyCode().equals("1")) {
//                        paypasswordDialog(investAmount);
                        buyfalg = true;
                        problemBuyMoneyEt.setText("");
                        purchasehttp(subjectId, continueFlag, selectedCouponId, investAmount);
                    } else {
                        // 重新获取现金券
                        cashmatchhttp(subjectId, "0", investAmount, true);
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {
                problemBuyIncomeTv.setText("");
            }
        });
    }

    /**
     * @param baseEvent void
     * @auther lion
     */
    private boolean selectCashCouponTag = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent baseEvent) {
        if (baseEvent != null) {
            if (baseEvent instanceof ChoseCouponEvent) {
                ChoseCouponEvent choseCouponEvent = (ChoseCouponEvent) baseEvent;
                CashCouponBeanInfo cashCouponBeanInfo = choseCouponEvent.getCashCouponBeanInfo();
                //具体业务
                if (!TextUtils.isEmpty(cashCouponBeanInfo.getCouponId()))
                    selectedCouponId = cashCouponBeanInfo.getCouponId();// 现金券Id
                if (!TextUtils.isEmpty(cashCouponBeanInfo.getCouponAmount()))
                    couponAmount = cashCouponBeanInfo.getCouponAmount();// 现金券金额
                if (!TextUtils.isEmpty(cashCouponBeanInfo.getUsableStatus()))
                    usableStatus = cashCouponBeanInfo.getUsableStatus();
                if (!TextUtils.isEmpty(cashCouponBeanInfo.getCouponType()))
                    couponType = cashCouponBeanInfo.getCouponType();
                if (!TextUtils.isEmpty(cashCouponBeanInfo.getCouponProfit()))
                    couponProfit = cashCouponBeanInfo.getCouponProfit();
                if (!TextUtils.isEmpty(cashCouponBeanInfo.getRealActiveDay()))
                    maxActiveDay = cashCouponBeanInfo.getRealActiveDay();
                if (!TextUtils.isEmpty(cashCouponBeanInfo.getCouponId()) && !TextUtils.isEmpty(cashCouponBeanInfo.getCouponAmount())) {
                    switch (Integer.valueOf(couponType)) {//券类型(0现金券;2抵扣券;3加息券;4理财金)
                        case 0:
                            if (!TextUtils.isEmpty(couponAmount) && !TextUtils.isEmpty(selectedCouponId)) {
                                problemBuyCouponsTvC.setText(couponAmount);
                                modify_tv.setText("元现金券");
                                problemBuyCouponsExplanationIv.setText("共" + avaiCashNum + "张 已选择1张现金劵");
                            }
                            break;
                        case 2:
                            if (!TextUtils.isEmpty(couponAmount) && !TextUtils.isEmpty(selectedCouponId)) {
                                problemBuyCouponsTvC.setText(couponAmount);
                                modify_tv.setText("元抵扣券");
                                problemBuyCouponsExplanationIv.setText("实际支付：" +
                                        BigDecemalCalculateUtil.subtractToString(problemBuyMoneyEt.getText().toString(), couponAmount, 2)
                                        + "元");
                            }
                            break;
                        case 3:
                            if (!TextUtils.isEmpty(couponAmount) && !TextUtils.isEmpty(selectedCouponId)) {
                                problemBuyCouponsTvC.setText(couponAmount);
                                modify_tv.setText("%加息券");
                                problemBuyCouponsExplanationIv.setText("加息" + maxActiveDay + "天，预计加息收益" + couponProfit + "元");
                            }
                            break;
                        case 4:
                            if (!TextUtils.isEmpty(couponAmount) && !TextUtils.isEmpty(selectedCouponId)) {
                                problemBuyCouponsTvC.setText(couponAmount);
                                modify_tv.setText("元理财金");
                                problemBuyCouponsExplanationIv.setText("预计理财金收益" + couponProfit + "元");
                            }
                            break;
                    }
                }
                problemBuyCouponsTvC.setTextColor(ContextCompat.getColor(mContext, R.color.select_list_detail));
                problemBuyCouponsIv.setVisibility(View.VISIBLE);
                problemBuyCouponsExplanationIv.setVisibility(View.VISIBLE);
                modify_tv.setVisibility(View.VISIBLE);
                selectCashCouponTag = true;
            }
        }
    }

    /**
     * 是否续投提示弹框
     *
     * @param isContinuedFlag
     */
    private void getContinuedFlag(String isContinuedFlag) {
        Singlton.getInstance(FinanceHttp.class).getContinuedFlag(mContext, isContinuedFlag, new OnResponseListener<BaseResult<String>>() {
            @Override
            public void onSuccess(BaseResult<String> result) {
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }
}
