package com.example.administrator.lmw.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.config.DespositAccountManager;
import com.example.administrator.lmw.config.DespositOperate;
import com.example.administrator.lmw.config.DesposityResultManager;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.CommonInfo;
import com.example.administrator.lmw.entity.FreshFloatIconEvent;
import com.example.administrator.lmw.entity.ShowFinanceFragmentEvent;
import com.example.administrator.lmw.entity.ShowMineFragmentEvent;
import com.example.administrator.lmw.finance.activity.DetailProductActivity;
import com.example.administrator.lmw.finance.activity.ImageCarousel;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.login.UserInfoUtils;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.login.entity.UserInfoBean;
import com.example.administrator.lmw.main.MainActivity;
import com.example.administrator.lmw.mine.card.CardActivity;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.mine.seting.DespositOperateSuccessActivity;
import com.example.administrator.lmw.register.PhoneVerifyActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * JS 交互公共类
 * 注：打开activity跳转时需要调用post方法再调整，以防止有的手机崩溃
 * Created by Administrator on 2017/4/10.
 */

public class JSInteraction {

    private CommonInfo commonInfo;
    private Context context;
    private UserInfo userInfo;
    private UserInfoBean userInfoBean;


    private String mBuyType;

    public JSInteraction(Context context, String... args) {
        this.context = context;
        mBuyType = args != null && args.length > 0 ? args[0] : "";
        commonInfo = ConfigManager.getInstance().getCommonInfo(context);
        userInfo = UserInfoUtils.getInstance().getUserInfo(context);
        if (userInfo != null) {
            userInfoBean = userInfo.getData();
        }
    }

    // 加注释不然17版本以上代码无效
    @JavascriptInterface
    public void forward(final String pageId) {
        ThreadManager.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                ALLog.e("pageId" + pageId);
                if (pageId.equals("app.home")) {// 首页
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                } else if (pageId.equals("app.login")) {// 登录页
                    PreferenceCongfig.loginType = PreferenceCongfig.FROM_FINANCE_BANNAER_TO_LOGIN;
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                } else if (pageId.equals("app.reg")) { // 注册页
                    PreferenceCongfig.RegistType = PreferenceCongfig.FROM_FINANCE_BANNAER_TO_REGISTER;
                    Intent intent = new Intent(context, PhoneVerifyActivity.class);
                    context.startActivity(intent);
                } else if (pageId.equals("app.borrow")) { // 投资大厅
                    Intent intent = new Intent(context, MainActivity.class);
                    FreshFloatIconEvent mFreshFloatIconEvent = new FreshFloatIconEvent(FreshFloatIconEvent.FRESH_NOT);
                    mFreshFloatIconEvent.setIndex(1);
                    context.startActivity(intent);
                    EventBus.getDefault().post(mFreshFloatIconEvent);
                    EventBus.getDefault().post(new ShowFinanceFragmentEvent());
                } else if (pageId.equals("app.user")) { // 我的（个人中心）
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    EventBus.getDefault().post(new ShowMineFragmentEvent());
                } else if (pageId.equals("app.bank.bind")) {// 银行卡
                  /*  Intent intent = new Intent(PreferenceCongfig.ACTION_BIND_BANKCARD);
                    intent.setClass(context, BindBankActivity.class);
                    context.startActivity(intent);*/
                    if (Singlton.getInstance(DespositAccountManager.class).isOpenDesposit(context)) {
                        if (PreferenceCongfig.IS_NOT_BIND_BANKCARD == userInfoBean.getIsBindBankCard()
                                || PreferenceCongfig.IS_REBIND_BANKCARD == userInfoBean.getIsBindBankCard()) {//需重新绑卡或者未绑卡//跳到绑卡界面
                            // startActivity(BindBankActivity.class);
                            Singlton.getInstance(DespositAccountManager.class).despositOperate(context, DespositOperate.BIND_BANKCARD);
                        }
                    }
                } else if (pageId.equals("app.user.bonus")) {// 红包 卡卷
                    Intent intent = new Intent(context, CardActivity.class);
                    context.startActivity(intent);
                } else if (pageId.equals("app.security")) {
                    Intent intent = new Intent(context, WebViewMore.class);
                    if (commonInfo != null) {
                        intent.putExtra("url", commonInfo.getLmw_product_safe() + "?t=" + System.currentTimeMillis());
                    } else {
                        intent.putExtra("url", "");
                    }
                    intent.putExtra("title", "安全保障");
                    context.startActivity(intent);
                } else if (pageId.equals("app.sign")) {//   关掉web界面
                    Toast.makeText(context, context.getString(R.string.toast_authorize), Toast.LENGTH_LONG).show();
                    if (userInfo != null && userInfoBean != null) {// 修改授权状态
                        userInfoBean.setAuthorize("1");
                        userInfo.setData(userInfoBean);
                        UserInfoUtils.getInstance().setUserInfo(context, userInfo);
                    }
                    ActivityManage.create().finishActivity(WebViewMore.class);
                } else if (pageId.equals("app.risk")) { // 风险评估跳过测试
                    ActivityManage.create().finishActivity(WebViewMore.class);
                    ActivityManage.create().finishActivity(DespositOperateSuccessActivity.class);

                }
            }
        });
    }

    /**
     * 跳详情
     *
     * @param borrowId
     */
    @JavascriptInterface
    public void toBorrow(final String borrowId) {
        ThreadManager.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(borrowId)) {
                    Intent intent = new Intent(context, DetailProductActivity.class);
                    intent.putExtra("subjectId", borrowId);// 标的标识
                    intent.putExtra("type", "0");// 是否是债权
                    context.startActivity(intent);
                }
            }
        });

    }

    @JavascriptInterface
    public void showContract(final String contract) {
        ThreadManager.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(contract)) {
                    Intent intent = new Intent(context, WebViewMore.class);
                    intent.putExtra("url", contract);
                    intent.putExtra("title", "合同");
                    context.startActivity(intent);
                }
            }
        });
    }

    @JavascriptInterface
    public void showBanners(final String params) {
        ThreadManager.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                String[] str = null;
                if (params != null) {
                    str = params.split("\\:\\:");
                    Intent intent = new Intent(context, ImageCarousel.class);
                    intent.putExtra("selectNum", Integer.parseInt(str[0].toString()));// 标的标识
                    intent.putExtra("images", str[1].split("\\|\\|"));// 是否是债权
                    context.startActivity(intent);
                }
            }
        });
    }

    @JavascriptInterface
    public void openUrl(final String[] title) {
        ThreadManager.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (title.length == 2) {
                    Intent intent = new Intent(context, WebViewMore.class);
                    intent.putExtra("url", title[0]);
                    intent.putExtra("title", title[1]);
                    context.startActivity(intent);
                }
            }
        });
    }

    /**
     * 标标有奖界面接口
     */
    @JavascriptInterface
    public void showConnection() {
        ThreadManager.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                ALLog.e("showConnection===");
                if (commonInfo != null && !TextUtils.isEmpty(commonInfo.getLmw_has_prize_title()) && !TextUtils.isEmpty(commonInfo.getLmw_has_prize_url())) {
                    Intent intent = new Intent(context, WebViewMore.class);
                    intent.putExtra("url", commonInfo.getLmw_has_prize_url());
                    intent.putExtra("title", commonInfo.getLmw_has_prize_title());
                    context.startActivity(intent);
                }
            }
        });
    }

    @JavascriptInterface
    public void shareContent(final String[] share) {
        Log.d("shareContent--", share.toString());
        if (share.length == 4) {
            ThreadManager.getMainHandler().post(new Runnable() {
                @Override
                public void run() {
                    Singlton.getInstance(ShareUtils.class).getShareUtils((Activity) context, share[0], share[1], share[2], share[3]);
                }
            });
        }
    }

    // 转让条件弹出框
    @JavascriptInterface
    public void transBox() {
        ThreadManager.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                Singlton.getInstance(PopWindowManager.class).
                        showLeftOnebtnDialog(context, "转让条件", StringUtils.getTransferInstruction().toString(), "我知道了",
                                new OnDialogClickListener() {
                                    @Override
                                    public void onClick(int id, View v) {
                                        if (id == 2) {

                                        }
                                    }
                                });
            }
        });
    }

    @JavascriptInterface
    public String getToken() {
        return SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, "");
    }

    @JavascriptInterface
    public void callBackForDesposity(final String[] desposityPara) {
        ALLog.e("snubee=====================回调native" + desposityPara.toString());
        ThreadManager.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (desposityPara != null && desposityPara.length == 2) {
                    String routeType = desposityPara[0];
                    String oderNo = desposityPara[1];
                    Singlton.getInstance(DesposityResultManager.class).init(context, routeType, oderNo, mBuyType);
                }
            }
        });

    }
}
