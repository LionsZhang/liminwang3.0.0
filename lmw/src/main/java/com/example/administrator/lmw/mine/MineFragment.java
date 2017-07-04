package com.example.administrator.lmw.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.config.DespositAccountManager;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseEvent;
import com.example.administrator.lmw.entity.BindSuccessEvent;
import com.example.administrator.lmw.entity.CommonInfo;
import com.example.administrator.lmw.entity.FreshFloatIconEvent;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.inteface.OnXScrollOreitationListener;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.login.LoginLogic;
import com.example.administrator.lmw.login.UserInfoUtils;
import com.example.administrator.lmw.login.entity.LoginBean;
import com.example.administrator.lmw.login.entity.LoginEntity;
import com.example.administrator.lmw.login.entity.MineData;
import com.example.administrator.lmw.login.entity.MineDataBean;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.login.entity.UserInfoBean;
import com.example.administrator.lmw.main.WebViewActivity;
import com.example.administrator.lmw.mine.account.AccountActivity;
import com.example.administrator.lmw.mine.card.CardActivity;
import com.example.administrator.lmw.mine.credit.CreditActivity;
import com.example.administrator.lmw.mine.cumulative.CumulativeActivity;
import com.example.administrator.lmw.mine.fill.FillActivity;
import com.example.administrator.lmw.mine.invest.InvestmentActivity;
import com.example.administrator.lmw.mine.invite.InviteFriendsActivity;
import com.example.administrator.lmw.mine.more.MoreActivity;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.mine.seting.SetingActivity;
import com.example.administrator.lmw.mine.transfer.TransferActivity;
import com.example.administrator.lmw.register.PhoneVerifyActivity;
import com.example.administrator.lmw.utils.APPUtil;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.BigDecemalCalculateUtil;
import com.example.administrator.lmw.utils.PicassoManager;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.StringUtils;
import com.example.administrator.lmw.view.CircleImageView;
import com.example.administrator.lmw.view.MineGuideCardDialog;
import com.example.administrator.lmw.view.MineGuideDialog;
import com.example.administrator.lmw.view.SettingNextItemNotIconView;
import com.example.administrator.lmw.view.XScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.InjectView;


/**
 * Created by lion on 2016/8/22.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener, XScrollView.IXScrollViewListener, OnXScrollOreitationListener {

    private TextView finance_number_tv;//总资产
    private TextView finance_number_point_tv;
    private LinearLayout personEditLayout;
    private TextView phone_tv;
    private TextView name_tv, regist_hint_tv;
    private TextView ac_income_tv, ac_income_point_tv;//累计收益
    private TextView credit_num_tv;//提现
    private CircleImageView head_iv;
    private ImageView finance_iv;
    private MineData mineData;
    private MineDataBean mineDataBean;
    private boolean isLookFinance;
    private UserInfo userInfo;
    private UserInfoBean userInfoBean;
    private CommonInfo commonInfo;
    private RelativeLayout user_rl, fill_rl, credit_rl;
    @InjectView(R.id.xScroll_view)
    XScrollView xScroll_view;
    @InjectView(R.id.content)
    RelativeLayout contentView;
    TextView version_tv;
    private DecimalFormat df = new DecimalFormat("##0.00");
    private MineGuideDialog mineGuideDialog;
    private boolean isViewInitialized;
    private ImageView vip_iv;
    private SettingNextItemNotIconView can_use_money_tv, invest_tv, receivable_queries_tv,
            card_tv, invite_tv, lm_data_tv, finance_caculate_tv, more_tv;
    private LinearLayout person_edit_layout;
    private RelativeLayout ac_income_rl;
    private RelativeLayout finance_number_rl;
    private TextView credit_hint_tv;
    private ImageView arrow_right_iv;
    private ImageView delect_risk_icon;
    private RelativeLayout risk_tip_rl;
    private TextView risk_cacumulate;
    private TextView tv_xw_open;
    private RelativeLayout desposit_txt_rl;
    private String phone;
    private String psw;
    private String token;

    @Override
    protected int getContentLayout() {

        return R.layout.fragment_mine_layout;
    }

    @Override
    protected void initializeData() {
        EventBus.getDefault().register(this);
        commonInfo = ConfigManager.getInstance().getCommonInfo(mContext);
        isLookFinance = SharedPreference.getInstance().getBoolean(mContext, PreferenceCongfig.MINE_EYE_STATU, false);
        phone = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_ACCOUNT, "");//账号
        psw = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_PSW, "");//密码
    }

    private void getMineDataInfo() {
        Singlton.getInstance(LoginLogic.class).getMineDataInfo(mContext, null, new OnResponseListener<MineData>() {
            @Override
            public void onSuccess(MineData response) {
                if (response != null) {
                    int code = Integer.valueOf(response.code);
                    if (code == 0) {
                        mineDataBean = response.getMineDataBean();
                        UserInfoUtils.getInstance().setMineData(mContext, response);
                        if (mineDataBean != null) {
                            setView();
                            if (!isLookFinance)
                                showData();
                            else
                                hideData();
                        }
                        onLoad();
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
            }
        });
    }

    private View content;

    @Override
    protected void initializeView() {
        content = inflater.inflate(R.layout.mine_scrollview_layout, null);
        findId();
        setListener();
        xScroll_view.setView(content);
        xScroll_view.setOnXScrollOreitationListener(this);
        setXscrollView();
        initView();
        version_tv.setText(String.format(getString(R.string.current_version), APPUtil.getVersion(true)));

    }


    private void setXscrollView() {
        if (PreferenceCongfig.isLogin)
            xScroll_view.setPullRefreshEnable(true);
        else
            xScroll_view.setPullRefreshEnable(false);
        xScroll_view.setPullLoadEnable(false);
        xScroll_view.setAutoLoadEnable(false);
        xScroll_view.setIXScrollViewListener(this);
        xScroll_view.setRefreshTime(getTime());

    }

    private void setListener() {

        finance_number_tv.setOnClickListener(this);
        phone_tv.setOnClickListener(this);
        name_tv.setOnClickListener(this);
        ac_income_tv.setOnClickListener(this);
        credit_num_tv.setOnClickListener(this);
        user_rl.setOnClickListener(this);
        fill_rl.setOnClickListener(this);
        credit_rl.setOnClickListener(this);
        person_edit_layout.setOnClickListener(this);
        finance_iv.setOnClickListener(this);
        ac_income_rl.setOnClickListener(this);
        finance_number_rl.setOnClickListener(this);
        delect_risk_icon.setOnClickListener(this);
        risk_cacumulate.setOnClickListener(this);
    }

    private void findId() {
        phone_tv = (TextView) content.findViewById(R.id.phone_tv);
        name_tv = (TextView) content.findViewById(R.id.name_tv);
        vip_iv = (ImageView) content.findViewById(R.id.vip_iv);
        arrow_right_iv = (ImageView) content.findViewById(R.id.arrow_right_iv);
        user_rl = (RelativeLayout) content.findViewById(R.id.user_rl);
        ac_income_tv = (TextView) content.findViewById(R.id.ac_income_tv);
        ac_income_point_tv = (TextView) content.findViewById(R.id.ac_income_point_tv);
        finance_number_tv = (TextView) content.findViewById(R.id.finance_number_tv);
        finance_number_point_tv = (TextView) content.findViewById(R.id.finance_number_point_tv);
        finance_iv = (ImageView) content.findViewById(R.id.finance_iv);
        credit_num_tv = (TextView) content.findViewById(R.id.credit_num_tv);
        credit_rl = (RelativeLayout) content.findViewById(R.id.credit_rl);
        fill_rl = (RelativeLayout) content.findViewById(R.id.fill_rl);
        can_use_money_tv = (SettingNextItemNotIconView) content.findViewById(R.id.can_use_money_tv);
        invest_tv = (SettingNextItemNotIconView) content.findViewById(R.id.invest_tv);
        receivable_queries_tv = (SettingNextItemNotIconView) content.findViewById(R.id.receivable_queries_tv);
        card_tv = (SettingNextItemNotIconView) content.findViewById(R.id.card_tv);
        invite_tv = (SettingNextItemNotIconView) content.findViewById(R.id.invite_tv);
        lm_data_tv = (SettingNextItemNotIconView) content.findViewById(R.id.lm_data_tv);
        finance_caculate_tv = (SettingNextItemNotIconView) content.findViewById(R.id.finance_caculate_tv);
        more_tv = (SettingNextItemNotIconView) content.findViewById(R.id.more_tv);
        version_tv = (TextView) content.findViewById(R.id.version_tv);
        regist_hint_tv = (TextView) content.findViewById(R.id.regist_hint_tv);
        credit_hint_tv = (TextView) content.findViewById(R.id.credit_hint_tv);
        person_edit_layout = (LinearLayout) content.findViewById(R.id.person_edit_layout);
        ac_income_rl = (RelativeLayout) content.findViewById(R.id.ac_income_rl);
        finance_number_rl = (RelativeLayout) content.findViewById(R.id.finance_number_rl);
        delect_risk_icon = (ImageView) content.findViewById(R.id.delect_risk_icon);
        risk_tip_rl = (RelativeLayout) content.findViewById(R.id.risk_tip_rl);
        risk_cacumulate = (TextView) content.findViewById(R.id.risk_cacumulate);
        desposit_txt_rl = (RelativeLayout) content.findViewById(R.id.desposit_txt_rl);
        tv_xw_open = (TextView) content.findViewById(R.id.tv_xw_open);
    }

    private void initView() {
        phone_tv.setText(getString(R.string.mine_login));
        name_tv.setText(getString(R.string.mine_register));
        finance_number_tv.setText(getString(R.string.mine_not_login));//总资产
        credit_num_tv.setText(getString(R.string.mine_not_login));//提现
        regist_hint_tv.setText(getString(R.string.mine_finance_not_login));
        regist_hint_tv.setVisibility(View.VISIBLE);
        ac_income_tv.setText(getString(R.string.mine_not_login));//累计收益
        ac_income_point_tv.setVisibility(View.INVISIBLE);
        finance_number_point_tv.setVisibility(View.INVISIBLE);
        finance_iv.setVisibility(View.GONE);
        vip_iv.setVisibility(View.GONE);
        credit_hint_tv.setVisibility(View.INVISIBLE);
        arrow_right_iv.setVisibility(View.INVISIBLE);
        setMineItem();

    }

    private void setMineItem() {
        initCanUseMoneyNumber();//可用余额
        initInvestNumber();//我的投资数据
        initReceivableQueries();//回款查询
        initCard();//卡券红包
        initInviteInfo();//邀请有礼
        initLmData();//利民数据
        initFinanceCaculate();//理财计算
        initMoreInfo();//更多
    }

    private void initMoreInfo() {

        more_tv.setOnRightTextOnclick(new SettingNextItemNotIconView.RigthTextViewListener() {

            @Override
            public void onRightTextView(TextView v) {
                v.setText("关于利民网");
            }

            @Override
            public void onClick(View v) {
                startActivity(MoreActivity.class);
            }
        });

    }

    private void initFinanceCaculate() {
        finance_caculate_tv.setOnRightTextOnclick(new SettingNextItemNotIconView.RigthTextViewListener() {

            @Override
            public void onRightTextView(TextView v) {
                v.setText("投资好帮手");
            }

            @Override
            public void onClick(View v) {
                //跳到理财计算器
                Bundle bundle = new Bundle();
                bundle.putInt(WebViewActivity.URL_TYPE, WebViewActivity.URL_FINANCE_CALCULATE);
                if (commonInfo != null && !TextUtils.isEmpty(commonInfo.income_calculator))//mineDataBean.getFinancialCalculatorUrl()
                    bundle.putString(WebViewActivity.URL, commonInfo.income_calculator);
                ActivityTools.switchActivity(mContext, WebViewActivity.class, bundle);

            }
        });
    }

    private void initLmData() {
        lm_data_tv.setOnRightTextOnclick(new SettingNextItemNotIconView.RigthTextViewListener() {

            @Override
            public void onRightTextView(TextView v) {
                v.setText("运营数据披露");
            }

            @Override
            public void onClick(View v) {
                //跳到利民数据
                Bundle bundle = new Bundle();
                bundle.putInt(WebViewActivity.URL_TYPE, WebViewActivity.URL_LMW_DATA);
                if (commonInfo != null && !TextUtils.isEmpty(commonInfo.liminDataUrl))
                    bundle.putString(WebViewActivity.URL, commonInfo.liminDataUrl);
                ActivityTools.switchActivity(mContext, WebViewActivity.class, bundle);

            }
        });
    }

    private void initInviteInfo() {
        invite_tv.setOnRightTextOnclick(new SettingNextItemNotIconView.RigthTextViewListener() {

            @Override
            public void onRightTextView(TextView v) {
                v.setText("获取佣金");
            }

            @Override
            public void onClick(View v) {
                if (!PreferenceCongfig.isLogin)
                    startActivity(LoginActivity.class);
                else
                    startActivity(InviteFriendsActivity.class);
            }
        });
    }

    private void initCard() {
        card_tv.setOnRightTextOnclick(new SettingNextItemNotIconView.RigthTextViewListener() {

            @Override
            public void onRightTextView(TextView v) {
                if (!PreferenceCongfig.isLogin) {
                    v.setText(getString(R.string.mine_not_login));
                    v.setTextColor(getResources().getColor(R.color.text_gray));
                } else if (mineDataBean != null && !TextUtils.isEmpty(mineDataBean.getCouponNum() + "")) {
                    v.setText(String.format(getString(R.string.mine_couponNum), mineDataBean.getCouponNum() + ""));//卡券红包数量
                    v.setTextColor(getResources().getColor(R.color.text_balck));
                }

            }

            @Override
            public void onClick(View v) {
                if (!PreferenceCongfig.isLogin)
                    startActivity(LoginActivity.class);
                else
                    startActivity(CardActivity.class);

            }
        });
    }

    private void initReceivableQueries() {
        receivable_queries_tv.setOnRightTextOnclick(new SettingNextItemNotIconView.RigthTextViewListener() {

            @Override
            public void onRightTextView(TextView v) {
                if (!PreferenceCongfig.isLogin)
                    v.setText(getString(R.string.mine_not_login));
                else if (mineDataBean != null && !TextUtils.isEmpty(mineDataBean.getRepayAmount() + "")
                        && !TextUtils.isEmpty(mineDataBean.getRepayNum() + ""))
                    v.setText(String.format(getString(R.string.repay_count),
                            df.format(mineDataBean.getRepayAmount()) + "", mineDataBean.getRepayNum() + ""));//回款查询
            }

            @Override
            public void onClick(View v) {
                if (!PreferenceCongfig.isLogin)
                    startActivity(LoginActivity.class);
                else
                    startActivity(TransferActivity.class);

            }
        });
    }

    private void initInvestNumber() {
        invest_tv.setOnRightTextOnclick(new SettingNextItemNotIconView.RigthTextViewListener() {

            @Override
            public void onRightTextView(TextView v) {
                if (!PreferenceCongfig.isLogin)
                    v.setText(getString(R.string.mine_not_login));
                else if (mineDataBean != null && !TextUtils.isEmpty(mineDataBean.getInvestAmount() + ""))
                    v.setText(String.format(getString(R.string.mine_credit_num), df.format(mineDataBean.getInvestAmount())));//我的投资
            }

            @Override
            public void onClick(View v) {
                if (!PreferenceCongfig.isLogin)
                    startActivity(LoginActivity.class);
                else
                    startActivity(InvestmentActivity.class);

            }
        });

    }

    private void initCanUseMoneyNumber() {
        can_use_money_tv.setOnRightTextOnclick(new SettingNextItemNotIconView.RigthTextViewListener() {

            @Override
            public void onRightTextView(TextView v) {
                if (!PreferenceCongfig.isLogin)
                    v.setText(getString(R.string.mine_not_login));
                else if (mineDataBean != null && !TextUtils.isEmpty(mineDataBean.getUsableAmount() + ""))
                    v.setText(String.format(getString(R.string.mine_credit_num), df.format(mineDataBean.getUsableAmount())));//可用余额
            }

            @Override
            public void onClick(View v) {
                if (!PreferenceCongfig.isLogin)
                    startActivity(LoginActivity.class);
                else
                    startActivity(AccountActivity.class);

            }
        });

    }

    private void setFinanceVisible() {
        if (!isLookFinance) {
            hideData();
            isLookFinance = true;
        } else {
            showData();
            isLookFinance = false;
        }
    }

    private void showData() {
        finance_iv.setImageResource(R.drawable.my_eyes_01);
        if (mineDataBean != null) {
            setFinanceAndIncomeData();
            can_use_money_tv.setHintTxt(String.format(getString(R.string.mine_credit_num), df.format(mineDataBean.getUsableAmount())));//可用余额
            invest_tv.setHintTxt(String.format(getString(R.string.mine_credit_num), df.format(mineDataBean.getInvestAmount())));//我的投资
            receivable_queries_tv.setHintTxt(String.format(getString(R.string.repay_count),
                    df.format(mineDataBean.getRepayAmount()) + "", mineDataBean.getRepayNum() + "")); //回款查询
            card_tv.setHintTxt(String.format(getString(R.string.mine_couponNum), mineDataBean.getCouponNum() + ""));//卡券红包

        }
        // finance_number_tv.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        // ac_income_tv.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        finance_number_point_tv.setVisibility(View.VISIBLE);
        ac_income_point_tv.setVisibility(View.VISIBLE);
        credit_hint_tv.setVisibility(View.VISIBLE);
        card_tv.setHintTextColor(getResources().getColor(R.color.text_balck));
        SharedPreference.getInstance().saveBoolean(mContext, PreferenceCongfig.MINE_EYE_STATU, false);

    }

    private void hideData() {
        finance_iv.setImageResource(R.drawable.my_eyes_02);//不可见
        finance_number_tv.setText(getString(R.string.mine_not_login));
        //  finance_number_tv.setTransformationMethod(PasswordTransformationMethod.getInstance());
        ac_income_tv.setText(getString(R.string.mine_not_login));
        //  ac_income_tv.setTransformationMethod(PasswordTransformationMethod.getInstance());
        finance_number_point_tv.setVisibility(View.INVISIBLE);
        ac_income_point_tv.setVisibility(View.INVISIBLE);
        credit_num_tv.setText(getString(R.string.mine_not_login));//提现数量
        can_use_money_tv.setHintTxt(getString(R.string.mine_not_login));//可用余额
        invest_tv.setHintTxt(getString(R.string.mine_not_login));//我的投资
        receivable_queries_tv.setHintTxt(getString(R.string.mine_not_login)); //回款查询
        // card_tv.setHintTxt(getString(R.string.mine_not_login));//卡券红包
        //  card_tv.setHintTextColor(getResources().getColor(R.color.text_gray));
        credit_hint_tv.setVisibility(View.INVISIBLE);
        SharedPreference.getInstance().saveBoolean(mContext, PreferenceCongfig.MINE_EYE_STATU, true);
    }

    /**
     * 设置累计收益和提现数量数据
     */
    private void setFinanceAndIncomeData() {
        if (mineDataBean != null) {
            finance_number_tv.setText(BigDecemalCalculateUtil.subNumberString(df.format(mineDataBean.getTotalAssets()), 0));//总资产
            finance_number_point_tv.setText(BigDecemalCalculateUtil.subNumberString(df.format(mineDataBean.getTotalAssets()), 1));//总资产
            ac_income_tv.setText(BigDecemalCalculateUtil.subNumberString(df.format(mineDataBean.getAccuIncome()), 0));//累计收益
            ac_income_point_tv.setText(BigDecemalCalculateUtil.subNumberString(df.format(mineDataBean.getAccuIncome()), 1));//累计收益
            credit_num_tv.setText(String.format(getString(R.string.mine_credit_num), df.format(mineDataBean.getWithdrawingAmount()) + ""));//提现数量
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        isViewInitialized = true;
        if (PreferenceCongfig.isRegister) {
        /*    Singlton.getInstance(PopWindowManager.class).
                    showRegistSuccussDialog(mContext, "注册成功", "快去实名认证绑卡使用吧", "先逛逛", "认证激活",
                            new OnDialogClickListener() {
                                @Override
                                public void onClick(int id, View v) {
                                    if (id == 1) {
                                        bindBank();
                                    }
                                }
                            });*/
            bindBank();
            PreferenceCongfig.isRegister = false;
        }
        if (PreferenceCongfig.isLogin) {
            getUserInfo();
        } else {
            setXscrollView();
            initView();
        }

    }

    /**
     * 风险提示显示逻辑
     */
    private void initRiskView() {
//        risk_cacumulate.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_risk_camuate)));
        //根据产品要求隐藏该提示
//        if (TextUtils.equals(userInfoBean.getIsRiskCamuluate(), PreferenceCongfig.RISK_CACULATE_ALREADY)) {//没有评估
//            if (userInfoBean.getIsRealnameAuth() == PreferenceCongfig.IS_REAL_NAME_AUTH) {
//                showRiskHink();
//            }
//        } else {
//            hideRiskHint();
//        }
    }

    /**
     * 存管提示显示逻辑
     */
    private void initDespositView() {
        if (TextUtils.equals(userInfoBean.getOpenDpStatus(), PreferenceCongfig.OPEN_DESPONSITY_PASS))
//            desposit_txt_rl.setVisibility(View.VISIBLE);
            tv_xw_open.setText(R.string.txt_xw_open);
        else
//            desposit_txt_rl.setVisibility(View.INVISIBLE);
            tv_xw_open.setText(R.string.txt_xw_unopen);
    }

    private void hideRiskHint() {
        risk_tip_rl.setVisibility(View.GONE);
    }

    private void showRiskHink() {
        risk_tip_rl.setVisibility(View.VISIBLE);
    }

    private void getUserCacheInfo() {
        userInfo = UserInfoUtils.getInstance().getUserInfo(mContext);
        if (userInfo != null)
            userInfoBean = userInfo.getData();
        mineData = UserInfoUtils.getInstance().getMineData(mContext);
        if (mineData != null)
            mineDataBean = mineData.getMineDataBean();

    }

    private void setView() {
        if (!TextUtils.isEmpty(mineDataBean.getMobile()))
            phone_tv.setText(mineDataBean.getMobile());
        if (!TextUtils.isEmpty(mineDataBean.getCustomerName()) && (userInfoBean.getIsRealnameAuth() == PreferenceCongfig.IS_REAL_NAME_AUTH))
            name_tv.setText(mineDataBean.getCustomerName());
        else
            name_tv.setText("未认证");
        regist_hint_tv.setVisibility(View.INVISIBLE);
        finance_iv.setVisibility(View.VISIBLE);
        credit_hint_tv.setVisibility(View.VISIBLE);
        arrow_right_iv.setVisibility(View.VISIBLE);
        showVipImg();
    }

    private void showVipImg() {
        if (!TextUtils.isEmpty(mineDataBean.getMemberLevelImg())) {
            PicassoManager.getInstance().display(mContext, vip_iv, mineDataBean.getMemberLevelImg());
            vip_iv.setVisibility(View.VISIBLE);
        } else
            vip_iv.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.person_edit_layout:
                if (!PreferenceCongfig.isLogin) {
                    startActivity(LoginActivity.class);
                }
              showRealAutoDialog();

                break;
            case R.id.user_rl:
                if (!PreferenceCongfig.isLogin)
                    startActivity(LoginActivity.class);
                else
                    startActivity(SetingActivity.class);
                break;
            case R.id.fill_rl://充值
                if (!PreferenceCongfig.isLogin)
                    startActivity(LoginActivity.class);
                else if (Singlton.getInstance(DespositAccountManager.class).isOpenDesposit(mContext))
                    processFillLogic("fill");
                break;
            case R.id.credit_rl://提现
                if (!PreferenceCongfig.isLogin)
                    startActivity(LoginActivity.class);
                else if (Singlton.getInstance(DespositAccountManager.class).isOpenDesposit(mContext))
                    processFillLogic("credit");
                break;
            case R.id.ac_income_rl://累计收益
                if (!PreferenceCongfig.isLogin)
                    startActivity(LoginActivity.class);
                else
                    toCumulative(1);
                break;
            case R.id.phone_tv://手机号
                if (!PreferenceCongfig.isLogin)
                    startActivity(LoginActivity.class);
                else
                    showRealAutoDialog();
                break;
            case R.id.name_tv://名字进入注册流程
                if (!PreferenceCongfig.isLogin)
                    startActivity(PhoneVerifyActivity.class);
                else
                    showRealAutoDialog();
                break;
            case R.id.finance_iv:
                setFinanceVisible();
                break;
            case R.id.finance_number_rl://总资产
                if (!PreferenceCongfig.isLogin)
                    startActivity(LoginActivity.class);
                else
                    toCumulative(0);
                break;
            case R.id.delect_risk_icon://删除风险评测提示
                hideRiskHint();
                break;
            case R.id.risk_cacumulate://跳转到风险评估页面
                Bundle bundle = new Bundle();
                if (commonInfo != null && !TextUtils.isEmpty(commonInfo.getRiskCamuluateUrl())) {
                    bundle.putString(WebViewMore.INTENT_KEY_URL, commonInfo.getRiskCamuluateUrl());
                    bundle.putString(WebViewMore.INTENT_KEY_TITLE, "风险评估");
                }
                ActivityTools.switchActivity(mContext, WebViewMore.class, bundle);
                break;

        }
    }

    private void showRealAutoDialog() {
        if (PreferenceCongfig.isLogin) {
            if (userInfoBean == null) return;
            if (userInfoBean.getIsRealnameAuth() == PreferenceCongfig.IS_NOT_REAL_NAME_AUTH)
                Singlton.getInstance(PopWindowManager.class).showRealAutuDialog(mContext, "实名认证 服务更到位", null, "立即认证",
                        new OnDialogClickListener() {
                            @Override
                            public void onClick(int id, View v) {
                                if (id == 2) {//进入银行卡绑卡未绑卡流程
                                    bindBank();
                                }
                            }
                        });
            else {
                startActivity(SetingActivity.class);
            }
        }
    }

    /**
     * 处理去充值提现页面前的逻辑
     */
    private void processFillLogic(String busnissType) {
        if (Singlton.getInstance(DespositAccountManager.class).isBindCard(mContext)) {
            if (busnissType.equals("fill")) {
                startActivity(FillActivity.class);
            } else if (busnissType.equals("credit")) {
                startActivity(CreditActivity.class);
            }
        }

    }

    private void bindBank() {
       // Singlton.getInstance(DespositAccountManager.class).despositOperate(mContext, DespositOperate.BIND_BANKCARD);
        Singlton.getInstance(DespositAccountManager.class).isOpenDesposit(mContext);
    }


    private void toCumulative(int indexs) {
        Intent intent = new Intent(mContext, CumulativeActivity.class);
        intent.putExtra(CumulativeActivity.INTENT_KEY_INDEX, indexs);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        if (PreferenceCongfig.isLogin)
            getMineDataInfo();
    }

    @Override
    public void onLoadMore() {

    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

    private void onLoad() {
        xScroll_view.stopRefresh();
        xScroll_view.setRefreshTime(getTime());
    }

    /**
     * @param baseEvent void
     * @auther lion
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent baseEvent) {
        if (baseEvent != null) {
            if (baseEvent instanceof BindSuccessEvent) {
                getMineDataInfo();
            }
            if (baseEvent != null && baseEvent instanceof FreshFloatIconEvent) {//切换到我的页面时触发
                FreshFloatIconEvent event = (FreshFloatIconEvent) baseEvent;
                String freshType = event.getFreshEventType();
                if (TextUtils.equals(freshType, FreshFloatIconEvent.FRESH_USER_INFO)) {
                    if (!PreferenceCongfig.isLogin && PreferenceCongfig.IS_APP_AUTO_LOGIN && !PreferenceCongfig.isCancelLogin)
                        login();
                    else if (PreferenceCongfig.isCancelLogin) {
                        setXscrollView();
                        initView();
                    } else if (PreferenceCongfig.isLogin)
                        getUserInfo();

                }


            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private void showGuideDialog() {
        if (SharedPreference.getInstance().getBoolean(mContext, PreferenceCongfig.APP_ISFIRST_LAUCH_SHOW_GUGIDE_DIALOG, true)) {
            mineGuideDialog = new MineGuideDialog(mContext);
            mineGuideDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    new MineGuideCardDialog(mContext);
                    SharedPreference.getInstance().saveBoolean(mContext, PreferenceCongfig.APP_ISFIRST_LAUCH_SHOW_GUGIDE_DIALOG, false);
                }
            });
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isViewInitialized) {
            if (!isInit) {
                isInit = true;
                showGuideDialog();
            }
        }
    }

    boolean isInit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isInit = false;
    }

    @Override
    public void onXScrollingDown() {
        // AnimationUtil.getInstance().startTitleBarShowAnimal(mContext,title_layout,contentView);
    }

    @Override
    public void onXScrollingUp() {
        // AnimationUtil.getInstance().startTitleBarHideAnimal(mContext,title_layout,contentView);
    }

    private void setLoginView() {
        getUserCacheInfo();
        if (userInfoBean != null) {
            initRiskView();
            initDespositView();
        }
        if (mineDataBean != null)
            setView();
        if (!isLookFinance)
            showData();
        else
            hideData();
        setXscrollView();
    }


    private void login() {
        // showLoadingDialog();
        Singlton.getInstance(LoginLogic.class).login(mContext, phone, psw, new OnResponseListener<LoginEntity>() {
            @Override
            public void onSuccess(LoginEntity response) {
                int code = Integer.valueOf(response.code);
                if (code == 0) {//登录成功
                    if (response.getData() != null) {
                        PreferenceCongfig.isLogin = true;
                        // freshPage();
                        LoginBean loginBean = response.getData();
                        token = loginBean.token;
                        SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_TOKEN, token);//保存token
                        SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_ACCOUNT, phone);//保存账号
                        SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_PSW, psw);//保存密码
                        getUserInfo();

                    }
                } else {
                    SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_TOKEN, "");//保存token
                    SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_ACCOUNT, "");//保存账号
                    SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_PSW, "");//保存密码
                    PreferenceCongfig.isLogin = false;

                }
            }

            @Override
            public void onFail(Throwable e) {
                //  hideLoadingDialog();

            }
        });
    }


    private void getUserInfo() {
        token = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, "");//保存token
        Singlton.getInstance(LoginLogic.class).getUserInfo(mContext, token, new OnResponseListener<UserInfo>() {
            @Override
            public void onSuccess(UserInfo response) {
                int code = Integer.valueOf(response.code);
                if (code == 0) {
                    if (response != null) {
                        UserInfoUtils.getInstance().setUserInfo(mContext, response);
                    }
                    getMineDataInfoAuditLogin();
                }

            }

            @Override
            public void onFail(Throwable e) {
                //  hideLoadingDialog();
            }
        });
    }

    private void getMineDataInfoAuditLogin() {
        Singlton.getInstance(LoginLogic.class).getMineDataInfo(mContext, token, new OnResponseListener<MineData>() {
            @Override
            public void onSuccess(MineData response) {
                //   hideLoadingDialog();
                if (response != null) {
                    UserInfoUtils.getInstance().setMineData(mContext, response);
                    setLoginView();

                }
            }

            @Override
            public void onFail(Throwable e) {
                //hideLoadingDialog();

            }
        });

    }


}
