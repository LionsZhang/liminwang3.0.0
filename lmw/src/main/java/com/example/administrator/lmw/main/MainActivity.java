package com.example.administrator.lmw.main;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseEvent;
import com.example.administrator.lmw.entity.CommonInfo;
import com.example.administrator.lmw.entity.FreshFloatIconEvent;
import com.example.administrator.lmw.entity.FreshMessageEvent;
import com.example.administrator.lmw.entity.FreshUseInfoEvent;
import com.example.administrator.lmw.entity.ShowFinanceFragmentEvent;
import com.example.administrator.lmw.entity.ShowMineFragmentEvent;
import com.example.administrator.lmw.finance.FinanceFragment;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.inteface.ViewOnClick;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.login.LoginLogic;
import com.example.administrator.lmw.login.UserInfoUtils;
import com.example.administrator.lmw.login.entity.LoginBean;
import com.example.administrator.lmw.login.entity.LoginEntity;
import com.example.administrator.lmw.login.entity.MineData;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.mine.MineFragment;
import com.example.administrator.lmw.mine.invite.InviteFriendsActivity;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.receiver.NetChangeReceiver;
import com.example.administrator.lmw.select.Activity.MessageCenterActivity;
import com.example.administrator.lmw.select.BannerHttp;
import com.example.administrator.lmw.select.SelectFragment;
import com.example.administrator.lmw.select.entity.NoticeEntity;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.udesk.UdeskConst;
import cn.udesk.UdeskSDKManager;

/**
 * Created by lion on 2016/8/22.
 */
public class MainActivity extends BaseActivity {
    private static final int SELECT_FRAGMENT = 0;
    private static final int FINANCE_FRAGMENT = 1;
    private static final int MINE_FRAGMENT = 2;
    private static final String select_fragment = "SELECT_FRAGMENT";
    private static final String finance_fragment = "FINANCE_FRAGMENT";
    private static final String mine_fragment = "MINE_FRAGMENT";
    private int[] menu_res_sel = {R.drawable.select_icon_sel,
            R.drawable.finance_icon_sel, R.drawable.mine_icon_sel};

    private int[] menu_res_nol = {R.drawable.select_icon_nol, R.drawable.finance_icon_nol,
            R.drawable.mine_icon_nol};
    private int[] titles = {R.string.menu_select, R.string.menu_finance,
            R.string.menu_mine};

    @InjectView(R.id.fragmentContent)
    RelativeLayout fragmentContent;
    @InjectView(R.id.select_layout)
    FrameLayout tab1;
    @InjectView(R.id.finance_layout)
    FrameLayout tab2;
    @InjectView(R.id.mine_layout)
    FrameLayout tab3;
    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.right_icon)
    ImageView rightIcon;
    @InjectView(R.id.back_layout)
    LinearLayout backLayout;
    @InjectView(R.id.title_bar_right_layout)
    LinearLayout titleBarRightLayout;
    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.title_layout)
    RelativeLayout title_layout;
    private FrameLayout[] bts = new FrameLayout[3];
    private FragmentManager fManager;
    private FragmentTransaction transaction;
    private int current;
    private int lastIndex = -1;
    private Fragment slectFragment, financeFragment, mineFragment;
    private CommonInfo commonInfo;
    private Map<String, String> info;
    private String appUdeskTokenGroop;
    private FreshFloatIconEvent mFreshFloatIconEvent;//刷新弹窗的事件实体
    private IntentFilter intentFilter;
    private NetChangeReceiver netChangeReceiver;
    private Intent intent = null;
    private String phone;
    private String psw;
    private String token;

    @OnClick({R.id.select_layout, R.id.finance_layout, R.id.mine_layout,
    })
    public void clickMenu(View v) {
        switch (v.getId()) {
            case R.id.select_layout:
                intTabs(SELECT_FRAGMENT);
                show(current);
                break;
            case R.id.finance_layout:
                intTabs(FINANCE_FRAGMENT);
                show(current);
                break;
            case R.id.mine_layout:
                intTabs(MINE_FRAGMENT);
                show(current);
                break;
        }
    }


    @Override
    protected void initializeData() {
        EventBus.getDefault().register(this);
        intent = getIntent();
        appUdeskTokenGroop = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_UDESK_TOKEN_GROOP, "");
        commonInfo = ConfigManager.getInstance().getCommonInfo(mContext);
        mFreshFloatIconEvent = new FreshFloatIconEvent(FreshFloatIconEvent.FRESH_NOT);
        intentFilter = new IntentFilter();
        //为过滤器添加处理规则
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netChangeReceiver = new NetChangeReceiver();
        //注册广播接收器
        registerReceiver(netChangeReceiver, intentFilter);
        phone = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_ACCOUNT, "");//账号
        psw = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_PSW, "");//密码

    }


    @Override
    protected int getContentLayout() {
        return R.layout.main_activity;
    }

    @Override
    protected void initializeView() {
        bts[0] = tab1;
        bts[1] = tab2;
        bts[2] = tab3;
        fManager = getSupportFragmentManager();
        intTabs(SELECT_FRAGMENT);
        show(current);
        setTitleClickEvent();
        getMessage();
    }

    private void setTitleClickEvent() {
        // 消息按钮点击
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MessageCenterActivity.class);
            }
        });
        // 更多
        titleBarRightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singlton.getInstance(PopWindowManager.class).showPopWindow(mContext, titleBarRightLayout, new ViewOnClick() {
                    @Override
                    public void onClick(int msgID, View v, Object... obj) {
                        switch (msgID) {
                            case 0:
                                if (!PreferenceCongfig.isLogin)
                                    startActivity(LoginActivity.class);
                                else
                                    startActivity(InviteFriendsActivity.class);
                                break;
                            case 1:
                                startUdeskGuest();
                                break;
                            case 2:
                                Intent intentc = new Intent(mContext, WebViewMore.class);
                                Bundle bundlec = new Bundle();
                                if (commonInfo != null) {
                                    bundlec.putString("url", commonInfo.getLmw_product_faq());
                                } else {
                                    bundlec.putString("url", "");
                                }
                                bundlec.putString("title", "常见问题");
                                intentc.putExtras(bundlec);
                                startActivity(intentc);
                                break;
                            case 3:
                                //跳到理财计算器
                                Bundle bundle = new Bundle();
                                bundle.putInt(WebViewActivity.URL_TYPE, WebViewActivity.URL_FINANCE_CALCULATE);
                                if (commonInfo != null && !TextUtils.isEmpty(commonInfo.income_calculator))
                                    bundle.putString(WebViewActivity.URL, commonInfo.income_calculator);
                                ActivityTools.switchActivity(mContext, WebViewActivity.class, bundle);
                                break;
                        }
                    }
                });
            }
        });
    }


    private void intTabs(int index) {
        if (index == lastIndex)
            return;

        for (int i = 0; i < bts.length; i++) {
            Button button = (Button) bts[i].getChildAt(0);
            if (i == index) {
                button.setTextColor(getResources().getColor(R.color.text_blue));
                button.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
                        .getDrawable(menu_res_sel[index]), null, null);
            } else {
                button.setTextColor(getResources().getColor(R.color.select_list_earnings));
                button.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
                        .getDrawable(menu_res_nol[i]), null, null);
            }

        }
        initTitle(index);
        current = index;
    }

    private void initTitle(int index) {
        if (index == SELECT_FRAGMENT) {//首页
            rightIcon.setImageResource(R.drawable.ic_main_nav_more);
            barTitle.setText("首页");
        } else if (index == FINANCE_FRAGMENT) {//理财
            barTitle.setText(R.string.menu_finance);
            rightIcon.setImageResource(R.drawable.ic_main_nav_more);
        } else if (index == MINE_FRAGMENT) {//我的
            barTitle.setText("我的");
            rightIcon.setImageResource(R.drawable.ic_main_nav_more);
            title_layout.setBackgroundColor(getResources().getColor(R.color.text_blue));
        }

    }

    private void show(int index) {
        transaction = fManager.beginTransaction();
        if (index == lastIndex)
            return;
        addAllFragment();
        hideAllFragment();

        switch (index) {
            case SELECT_FRAGMENT:
                // slectFragment = fManager.findFragmentByTag(select_fragment);
                if (slectFragment == null) {
                    slectFragment = new SelectFragment();
                    transaction.add(R.id.fragmentContent, slectFragment, select_fragment);
                    // transaction.commitAllowingStateLoss();
                } else
                    transaction.show(slectFragment);
                break;
            case FINANCE_FRAGMENT:
                //  financeFragment = fManager.findFragmentByTag(finance_fragment);
                if (financeFragment == null) {
                    financeFragment = new FinanceFragment();
                    transaction.add(R.id.fragmentContent, financeFragment, finance_fragment);
                    // transaction.commitAllowingStateLoss();
                } else
                    transaction.show(financeFragment);
                break;
            case MINE_FRAGMENT:
                //   mineFragment = fManager.findFragmentByTag(mine_fragment);
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.fragmentContent, mineFragment, mine_fragment);
                    // transaction.commitAllowingStateLoss();
                } else {
                    transaction.show(mineFragment);
                    mineFragment.setUserVisibleHint(true);
                }

                break;
        }

        //post点击事件
        mFreshFloatIconEvent.setIndex(index);
        EventBus.getDefault().post(mFreshFloatIconEvent);
        if (index == 2)
            EventBus.getDefault().post(new FreshFloatIconEvent(FreshFloatIconEvent.FRESH_USER_INFO));

        lastIndex = index;
        transaction.commitAllowingStateLoss();

    }

    private void hideAllFragment() {
        if (slectFragment != null)
            transaction.hide(slectFragment);
        if (financeFragment != null)
            transaction.hide(financeFragment);
        if (mineFragment != null)
            transaction.hide(mineFragment);
    }

    private void addAllFragment() {
        if (slectFragment == null) {
            slectFragment = new SelectFragment();
            transaction.add(R.id.fragmentContent, slectFragment, select_fragment);
        }

        if (financeFragment == null) {
            financeFragment = new FinanceFragment();
            transaction.add(R.id.fragmentContent, financeFragment, finance_fragment);
        }

        if (mineFragment == null) {
            mineFragment = new MineFragment();
            transaction.add(R.id.fragmentContent, mineFragment, mine_fragment);
        }

    }

    private boolean bExit = false;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            bExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (bExit) {
                ActivityManage.create().finishAllActivity();
                android.os.Process.killProcess(android.os.Process.myPid());
                return true;
            }
            bExit = true;
            mHandler.sendEmptyMessageDelayed(0x100, 2000);
            showToast(getString(R.string.exit_app_Prompt));

            return true;
        }

        return super.onKeyDown(keyCode, event);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(netChangeReceiver);
    }


    /**
     * @param baseEvent void
     * @auther lion
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent baseEvent) {
        if (baseEvent != null) {
            if (baseEvent instanceof ShowFinanceFragmentEvent) {
                intTabs(FINANCE_FRAGMENT);
                show(current);
            } else if (baseEvent instanceof ShowMineFragmentEvent) {
                intTabs(MINE_FRAGMENT);
                show(current);
            } else if (baseEvent instanceof FreshMessageEvent) {
                getMessage();
            } else if (baseEvent instanceof FreshUseInfoEvent) {
                FreshUseInfoEvent freshUseInfoEvent = (FreshUseInfoEvent) baseEvent;
                if (TextUtils.equals(freshUseInfoEvent.getFreshType(), FreshUseInfoEvent.IS_GESTUREPSW_LOGIN)) {
                    phone = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_ACCOUNT, "");//账号
                    psw = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_PSW, "");//密码
                    login();
                } else if (TextUtils.equals(freshUseInfoEvent.getFreshType(), FreshUseInfoEvent.IS_FRESH_USER_INFO)) {
                    token=SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, "");
                    getUserInfo();
                }

            }

        }
    }

    private void startUdeskGuest() {
        if (info == null)
            info = new HashMap<String, String>();
        info.put(UdeskConst.UdeskUserInfo.USER_SDK_TOKEN, appUdeskTokenGroop);
        UdeskSDKManager.getInstance().setUserInfo(mContext, appUdeskTokenGroop, info);
        String groupId = PreferenceCongfig.APP_UDESK_GROOP_ID;
        UdeskSDKManager.getInstance().lanuchChatByGroupId(mContext, groupId);
    }

    public void getMessage() {
        Singlton.getInstance(BannerHttp.class).getNotice(mContext, 0, 1, new OnResponseListener<NoticeEntity>() {
            @Override
            public void onSuccess(NoticeEntity noticeEntity) {
                barIconBack.setImageResource(R.drawable.ic_main_nav_mes);
                if (noticeEntity == null) return;
                if (noticeEntity.getData() == null) return;
                if (noticeEntity.getData().getDatas() == null) return;
                if (noticeEntity.getCode().equals("0")) {
                    if (noticeEntity.getData().getUnreadMessageNum() > 0) {
                        barIconBack.setImageResource(R.drawable.ic_main_has_mes);
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {
                barIconBack.setImageResource(R.drawable.ic_main_nav_mes);
            }
        });
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
                        PreferenceCongfig.isCancelLogin=false;
                        freshPage();
                        LoginBean loginBean = response.getData();
                        token = loginBean.token;
                        SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_TOKEN, token);//保存token
                        SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_ACCOUNT, phone);//保存账号
                        SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_PSW, psw);//保存密码
                        getUserInfo();

                    }
                } else {
                    if (PreferenceCongfig.IS_APP_AUTO_LOGIN) {
                        SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_TOKEN, "");//保存token
                        SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_ACCOUNT, "");//保存账号
                        SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_PSW, "");//保存密码
                        PreferenceCongfig.isLogin = false;
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {
                //  hideLoadingDialog();

            }
        });
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
                //  hideLoadingDialog();
            }
        });
    }

    private void getMineDataInfo() {
        Singlton.getInstance(LoginLogic.class).getMineDataInfo(this, token, new OnResponseListener<MineData>() {
            @Override
            public void onSuccess(MineData response) {
                //   hideLoadingDialog();
                if (response != null) {
                    UserInfoUtils.getInstance().setMineData(mContext, response);


                }
            }

            @Override
            public void onFail(Throwable e) {
                //hideLoadingDialog();

            }
        });
    }

    private void freshPage() {
        EventBus.getDefault().post(new FreshFloatIconEvent(FreshFloatIconEvent.FRESH_ICON));
        EventBus.getDefault().post(new FreshMessageEvent());

    }

}
