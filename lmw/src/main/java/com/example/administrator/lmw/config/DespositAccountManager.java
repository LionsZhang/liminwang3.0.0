package com.example.administrator.lmw.config;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.entity.BindSuccessEvent;
import com.example.administrator.lmw.entity.CommonInfo;
import com.example.administrator.lmw.entity.DepositoryEntity;
import com.example.administrator.lmw.entity.DepositoryInfo;
import com.example.administrator.lmw.entity.FreshFloatIconEvent;
import com.example.administrator.lmw.finance.activity.CreditBuy;
import com.example.administrator.lmw.finance.activity.DetailActivity;
import com.example.administrator.lmw.finance.activity.DetailProductActivity;
import com.example.administrator.lmw.finance.activity.ProblemBuy;
import com.example.administrator.lmw.finance.utils.BuyType;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.LoginLogic;
import com.example.administrator.lmw.login.UserInfoUtils;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.login.entity.UserInfoBean;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.mine.seting.DesponsityStatueActivity;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ThreadPoolManager;
import com.example.administrator.lmw.view.DialogPlus;
import com.example.administrator.lmw.view.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

/**
 * 存管账户绑卡相关业务
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/5/10 11:39
 **/
public class DespositAccountManager {


    private UserInfoBean mUserInfoBean;
    private CommonInfo commonInfo;

    /**
     * 检测是否绑卡，未绑卡返回false 并且弹出未绑银行卡提示，如果已经绑卡直接返回true，可以进行后续操作
     * <p>
     * 涉及绑卡检测功能的地方有以下：
     * 产品详情-立即购买-投资
     * 我的-充值/提现
     * 我的-我的投资-续投设置
     * 卡券红包-领取红包
     * 我的-账户设置-银行卡管理
     * 我的-账户设置-账户信息-银行卡管理
     * 我的-账户设置-交易密码
     *
     * @param context
     * @returnc 未绑卡返回false 并且弹出未绑银行卡提示，如果已经绑卡直接返回true，
     */
    public boolean isBindCard(final Context context) {
        getLocalUserInfo(context);

        if (mUserInfoBean == null) {//取不到用户信息直接返回false，不再进行后续操作
            return false;
        }

        if (mUserInfoBean.getIsBindBankCard() == PreferenceCongfig.IS_NOT_BIND_BANKCARD) {
            if (mUserInfoBean.getUserTag() == PreferenceCongfig.IS_NEW_USER) {//新用户绑卡
                bindCard(context);
                return false;
            } else if (mUserInfoBean.getUserTag() == PreferenceCongfig.IS_OLD_EFFECTIVE_USER
                    || mUserInfoBean.getUserTag() == PreferenceCongfig.IS_OLD_UN_EFFECTIVE_USER) {//老用户重新绑卡
                bindCard(context);
                return false;
            }
        } else if (mUserInfoBean.getIsBindBankCard() == PreferenceCongfig.IS_REBIND_BANKCARD) {//重新绑卡
            bindCard(context);
            return false;
        }

        return true;
    }

    /**
     * 绑卡提示弹窗
     *
     * @param context
     */
    private void bindCard(final Context context) {
        getLocalUserInfo(context);
        Singlton.getInstance(PopWindowManager.class).showNormolDialog(context,
                context.getResources().getString(R.string.txt_tips),
                context.getResources().getString(R.string.txt_bind_card_tips),
                "", context.getResources().getString(R.string.txt_bind_card),
                new OnDialogClickListener() {
                    @Override
                    public void onClick(int id, View v) {
                        if (id == 1) {
                            despositOperate(context, DespositOperate.BIND_BANKCARD);
                        }
                    }
                });
    }

    /**
     * 开通银行存管账户提示
     */
    public void showOpenDespositTwoBtn(final Context context, final String despositStatu) {

        Singlton.getInstance(PopWindowManager.class).showNormolDialog(context,
                context.getResources().getString(R.string.txt_tips),
                context.getResources().getString(R.string.txt_open_desposit_tips),
                "", context.getResources().getString(R.string.txt_go_open),
                new OnDialogClickListener() {
                    @Override
                    public void onClick(int id, View v) {
                        if (id == 1) {
                            openBankDepository(context, despositStatu);
                        }
                    }
                });


    }

    /**
     * 未满十八岁提示
     *
     * @param context
     */
    public void showLimitAccount(final Context context) {
        Singlton.getInstance(PopWindowManager.class).showNormolDialog(context, Gravity.LEFT | Gravity.CENTER_VERTICAL,
                context.getResources().getString(R.string.txt_warm_prompt),
                context.getResources().getString(R.string.txt_limit_account_tips),
                context.getResources().getString(R.string.txt_i_know),
                context.getResources().getString(R.string.txt_call_center),
                new OnDialogClickListener() {
                    @Override
                    public void onClick(int id, View v) {
                        if (id == 1) {
                            ActivityTools.startUdeskGuest(context);
                        }
                    }
                });
    }


    /**
     * 开通银行存管账户弹窗
     *
     * @param context
     * @param args    agrs[0]=false 点击屏幕不消失弹窗,默认是点击屏幕消失
     */
    public void openDespositDialog(final Context context, final String despositStatu, boolean... args) {

        final boolean cancelable = args != null && args.length > 0 ? args[0] : true;

        View view = View.inflate(context, R.layout.dialog_open_desposit, null);
        ViewHolder holder = new ViewHolder(view);
        final DialogPlus dialog = new DialogPlus.Builder(context)
                .setContentHolder(holder).setCancelable(cancelable)
                .setBackgroundColorResourceId(R.drawable.transparent)
                .setMargins(100, 20, 100, 20)
                .setGravity(DialogPlus.Gravity.CENTER).create();

        dialog.show();

//        //需开通银行存管
//        view.findViewById(R.id.tv_tip1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "需开通银行存管", Toast.LENGTH_SHORT).show();
//                openBankDepository(context, callBackUrl, despositStatu);
//            }
//        });
        //了解详情
        view.findViewById(R.id.tv_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "了解详情---调到h5页面", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        //开通存管
        view.findViewById(R.id.right_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBankDepository(context, despositStatu);
                dialog.dismiss();
            }
        });
        //取消
        view.findViewById(R.id.left_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    /**
     * 是否开通存管 false 还没开通，需要提示弹窗,true已开通或者显示开通状态，不需要弹窗
     *
     * @param mContext
     * @return
     */
    public boolean isOpenDesposit(final Context mContext) {
        getLocalUserInfo(mContext);
        if (mUserInfoBean != null) {
            String despositStatu = mUserInfoBean.getOpenDpStatus();
            if (!TextUtils.isEmpty(despositStatu)) {
                if (TextUtils.equals(despositStatu, PreferenceCongfig.OPEN_DESPONSITY_NOT)
                        || TextUtils.equals(despositStatu, PreferenceCongfig.OPEN_DESPONSITY_ACTIVE)) {
                    openDespositDialog(mContext, despositStatu);
                    return false;
                } else if (TextUtils.equals(despositStatu, PreferenceCongfig.OPEN_DESPONSITY_NO_PERMISSION)) {//未满十八岁
                    showLimitAccount(mContext);
                    return false;
                } else if (TextUtils.equals(despositStatu, PreferenceCongfig.OPEN_DESPONSITY_AUDIT)
                        || TextUtils.equals(despositStatu, PreferenceCongfig.OPEN_DESPONSITY_RETURN)
                        || TextUtils.equals(despositStatu, PreferenceCongfig.OPEN_DESPONSITY_REJECT)) {
                    Intent startIntent = new Intent(mContext, DesponsityStatueActivity.class);
                    startIntent.putExtra(PreferenceCongfig.INTENT_KEY_OPEN_DESPONSITY_STATE, despositStatu);
                    mContext.startActivity(startIntent);
                    return false;
                } else if (TextUtils.equals(despositStatu, PreferenceCongfig.OPEN_DESPONSITY_PASS)) {//已开通存管
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * 是否电子签证
     *
     * @param context
     * @return
     */
    public boolean isEsignatureAuthorize(Context context) {
        getLocalUserInfo(context);
        getLocalCommonInfo(context);
        if (mUserInfoBean != null && TextUtils.equals(mUserInfoBean.getUseFdd(), "1") && TextUtils.equals(mUserInfoBean.getAuthorize(),
                PreferenceCongfig.IS_NOT_ESIGNATURE_AUTHORIZE)) {//授权
            //跳到电子授权页
            Intent intent = new Intent(context, WebViewMore.class);
            intent.putExtra(WebViewMore.INTENT_KEY_TITLE, "电子签名授权");
            if (commonInfo != null && !TextUtils.isEmpty(commonInfo.getEsignature_authorize_url())) {
                intent.putExtra(WebViewMore.INTENT_KEY_URL, commonInfo.getEsignature_authorize_url());
            }
            context.startActivity(intent);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 用户的账号状态是否ok，开通银行存管账户和绑定银行卡都完成返回true
     * <p>
     * 判断是否开通银行存管账户-->再判断是否绑定银行卡
     * <p>
     * 涉及绑卡检测功能的地方有以下：
     * 产品详情-立即购买
     * 我的-充值/提现
     * 我的-我的投资-续投设置
     * 卡券红包-领取红包
     * 我的-账户设置-银行卡管理
     * 我的-账户设置-账户信息-银行卡管理
     * 我的-账户设置-交易密码
     *
     * @param context
     * @param flag    true 表示购买时验证是否电子授权
     *                false 表示 不是购买时调用 不必进行电子授权验证
     * @return true 用户已开通银行账户存管和绑定了银行卡，可以进行后续操作
     * false 账户状态不正常，可能不绑卡或者没有开通银行存管，会进行对应的弹窗显示
     */
    public boolean isCheckOpenDespositAndBindCard(final Context context, boolean flag) {
        if (isOpenDesposit(context)) {//判断是否开通存管
            //判断是否绑卡，  未绑卡-->去绑卡
            //未绑卡返回false 并且弹出未绑银行卡提示，如果已经绑卡直接返回true，
            if (isBindCard(context)) {//已绑卡判断是否电子签证授权
                if (flag)
                    return true;
                else
                    return isEsignatureAuthorize(context);
            }
        }
        return false;
    }


    /**
     * 获取本地的用户信息
     *
     * @param context
     */
    private void getLocalUserInfo(Context context) {
        if (mUserInfoBean == null) {
            UserInfo userInfo = UserInfoUtils.getInstance().getUserInfo(context);
            mUserInfoBean = userInfo != null ? userInfo.getData() : null;
        }
    }

    /**
     * 获取本地的通用配置
     *
     * @param context
     */
    private void getLocalCommonInfo(Context context) {
        if (commonInfo == null) {
            commonInfo = ConfigManager.getInstance().getCommonInfo(context);
        }
    }

    /**
     * 开通存管
     *
     * @param mActivity
     */
    public void openBankDepository(final Context mActivity, String despositStatu) {
        showDiglog(mActivity);
        Singlton.getInstance(DespositLogic.class).getDepositoryPostPara(mActivity, despositStatu, new OnResponseListener<DepositoryEntity>() {
            @Override
            public void onSuccess(DepositoryEntity result) {
                hideDiglog(mActivity);
                try {
                    if (result != null && result.getXwBankAbsractRsp() != null) {
                        DepositoryInfo depositoryInfo = result.getXwBankAbsractRsp();
                        StringBuilder stringBuilder = new StringBuilder();
                        String url = depositoryInfo.getUrl();
                        String postPara = null;
                        postPara = stringBuilder.append(URLEncoder.encode("keySerial", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getKeySerial(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("platformNo", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getPlatformNo(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("reqData", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getReqData(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("serviceName", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getServiceName(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("sign", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getSign(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("userDevice", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getUserDevice(), "UTF-8"))
                                .toString();
                        Bundle bundle = new Bundle();
                        bundle.putString(WebViewMore.INTENT_KEY_URL, url);
                        bundle.putString(WebViewMore.INTENT_KEY_TITLE, "开通存管账户");
                        bundle.putString(WebViewMore.INTENT_KEY_POST_PARAMETER, postPara);
                        ActivityTools.switchActivity(mActivity, WebViewMore.class, bundle);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Throwable e) {
                hideDiglog(mActivity);

            }
        });
    }


    /**
     * 存管相关的操作
     *
     * @param context
     * @param desOpetate 操作的类型
     */
    public void despositOperate(final Context context, DespositOperate desOpetate) {

        String title = null;
        if (desOpetate == DespositOperate.BIND_BANKCARD) {//绑定银行卡
            title = "绑定银行卡";
        } else if (desOpetate == DespositOperate.UNBIND_BANKCARD) {//解绑银行卡
            title = "解绑银行卡";
        } else if (desOpetate == DespositOperate.PHONE) {//修改预留手机号
            title = "修改预留手机号";
        } else if (desOpetate == DespositOperate.TRANSACTION_PW) {//修改交易密码
            title = "修改交易密码";
        }

        final String finalTitle = title;
        Singlton.getInstance(DespositLogic.class).despositOperate(context, desOpetate, new OnResponseListener<DepositoryEntity>() {
            @Override
            public void onSuccess(DepositoryEntity result) {
                hideDiglog(context);
                try {
                    if (result != null && result.getXwBankAbsractRsp() != null) {
                        DepositoryInfo depositoryInfo = result.getXwBankAbsractRsp();
                        StringBuilder stringBuilder = new StringBuilder();
                        String url = depositoryInfo.getUrl();
                        String postPara = null;
                        postPara = stringBuilder.append(URLEncoder.encode("keySerial", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getKeySerial(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("platformNo", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getPlatformNo(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("reqData", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getReqData(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("serviceName", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getServiceName(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("sign", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getSign(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("userDevice", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getUserDevice(), "UTF-8"))
                                .toString();
                        Bundle bundle = new Bundle();
                        bundle.putString(WebViewMore.INTENT_KEY_URL, url);
                        bundle.putString(WebViewMore.INTENT_KEY_TITLE, finalTitle);
                        bundle.putString(WebViewMore.INTENT_KEY_POST_PARAMETER, postPara);
                        ActivityTools.switchActivity(context, WebViewMore.class, bundle);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Throwable e) {
                hideDiglog(context);
            }


        });

    }

    public void desposityFill(final Context context, String bankCode, String amount,
                              String rechargeWay) {
        Singlton.getInstance(DespositLogic.class).doFill(context, bankCode, amount, rechargeWay, new OnResponseListener<DepositoryEntity>() {
            @Override
            public void onSuccess(DepositoryEntity result) {
                hideDiglog(context);
                try {
                    if (result != null && result.getXwBankAbsractRsp() != null) {
                        DepositoryInfo depositoryInfo = result.getXwBankAbsractRsp();
                        StringBuilder stringBuilder = new StringBuilder();
                        String url = depositoryInfo.getUrl();
                        String postPara = null;
                        postPara = stringBuilder.append(URLEncoder.encode("keySerial", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getKeySerial(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("platformNo", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getPlatformNo(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("reqData", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getReqData(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("serviceName", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getServiceName(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("sign", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getSign(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("userDevice", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getUserDevice(), "UTF-8"))
                                .toString();
                        Bundle bundle = new Bundle();
                        bundle.putString(WebViewMore.INTENT_KEY_URL, url);
                        bundle.putString(WebViewMore.INTENT_KEY_TITLE, "充值");
                        bundle.putString(WebViewMore.INTENT_KEY_POST_PARAMETER, postPara);
                        ActivityTools.switchActivity(context, WebViewMore.class, bundle);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Throwable e) {
                hideDiglog(context);
            }
        });

    }


    /**
     * 购买标的
     *
     * @param context
     * @param busId
     * @param continueFlag
     * @param couponId
     * @param investAmount
     */
    public void dpurchasehttp(final Context context, String busId, final String continueFlag, final String couponId, final String investAmount) {
        ALLog.e("snubee-----------------" + BuyType.PROBLEM);

        Singlton.getInstance(DespositLogic.class).getPurchasexw(context, busId, continueFlag, couponId, investAmount, new OnResponseListener<DepositoryEntity>() {
            @Override
            public void onSuccess(DepositoryEntity result) {
                hideDiglog(context);
                EventBus.getDefault().post(new BindSuccessEvent());
                EventBus.getDefault().post(new FreshFloatIconEvent(FreshFloatIconEvent.FRESH_ICON));
                ActivityManage.create().finishActivity(ProblemBuy.class);
                ActivityManage.create().finishActivity(DetailProductActivity.class);
                try {
                    if (result != null && result.getXwBankAbsractRsp() != null) {
                        DepositoryInfo depositoryInfo = result.getXwBankAbsractRsp();
                        StringBuilder stringBuilder = new StringBuilder();
                        String url = depositoryInfo.getUrl();
                        String postPara = null;
                        postPara = stringBuilder.append(URLEncoder.encode("keySerial", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getKeySerial(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("platformNo", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getPlatformNo(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("reqData", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getReqData(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("serviceName", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getServiceName(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("sign", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getSign(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("userDevice", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getUserDevice(), "UTF-8"))
                                .toString();
                        Bundle bundle = new Bundle();
                        bundle.putString(WebViewMore.INTENT_KEY_URL, url);
                        bundle.putString(WebViewMore.INTENT_KEY_TITLE, "投资");
                        bundle.putString(WebViewMore.INTENT_KEY_POST_PARAMETER, postPara);
                        bundle.putString(WebViewMore.INTENT_KEY_EXTRA_TYPE, BuyType.PROBLEM);
                        ActivityTools.switchActivity(context, WebViewMore.class, bundle);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Throwable e) {
                hideDiglog(context);
            }
        });
    }

    /**
     * 购买债权
     *
     * @param context
     * @param debtId
     * @param investAmount
     *           /**
    keySerial	证书序号	string
    platformNo	平台编号	string
    reqData	业务数据报文	string
    serviceName	接口名称	string
    sign	对 reqData 参数的签名	string
    url	网关URL	string
    userDevice	用户终端设备类型	string
     */

    public void getDebtBuyXWhttp(final Context context, String debtId, String investAmount) {
        Singlton.getInstance(DespositLogic.class).getDebtBuyXW(context, debtId, investAmount, new OnResponseListener<DepositoryEntity>() {
            @Override
            public void onSuccess(DepositoryEntity result) {
                hideDiglog(context);
                EventBus.getDefault().post(new BindSuccessEvent());
                EventBus.getDefault().post(new FreshFloatIconEvent(FreshFloatIconEvent.FRESH_ICON));
                ActivityManage.create().finishActivity(CreditBuy.class);
                ActivityManage.create().finishActivity(DetailActivity.class);
                try {
                    if (result != null && result.getXwBankAbsractRsp() != null) {
                        DepositoryInfo depositoryInfo = result.getXwBankAbsractRsp();
                        StringBuilder stringBuilder = new StringBuilder();
                        String url = depositoryInfo.getUrl();
                        String postPara = null;
                        postPara = stringBuilder.append(URLEncoder.encode("keySerial", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getKeySerial(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("platformNo", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getPlatformNo(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("reqData", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getReqData(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("serviceName", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getServiceName(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("sign", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getSign(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("userDevice", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getUserDevice(), "UTF-8"))
                                .toString();
                        Bundle bundle = new Bundle();
                        bundle.putString(WebViewMore.INTENT_KEY_URL, url);
                        bundle.putString(WebViewMore.INTENT_KEY_TITLE, "投资");
                        bundle.putString(WebViewMore.INTENT_KEY_POST_PARAMETER, postPara);
                        bundle.putString(WebViewMore.INTENT_KEY_EXTRA_TYPE, BuyType.CREDIT);
                        ActivityTools.switchActivity(context, WebViewMore.class, bundle);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Throwable e) {
                hideDiglog(context);
            }
        });
    }


    public void depositoryCredit(final Context context, String poundage, String amount) {

        Singlton.getInstance(DespositLogic.class).doCredit(context, poundage, amount, new OnResponseListener<DepositoryEntity>() {
            @Override
            public void onSuccess(DepositoryEntity result) {
                hideDiglog(context);
                try {
                    if (result != null && result.getXwBankAbsractRsp() != null) {
                        DepositoryInfo depositoryInfo = result.getXwBankAbsractRsp();
                        StringBuilder stringBuilder = new StringBuilder();
                        String url = depositoryInfo.getUrl();
                        String postPara = null;
                        postPara = stringBuilder.append(URLEncoder.encode("keySerial", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getKeySerial(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("platformNo", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getPlatformNo(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("reqData", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getReqData(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("serviceName", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getServiceName(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("sign", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getSign(), "UTF-8"))
                                .append("&")
                                .append(URLEncoder.encode("userDevice", "UTF-8"))
                                .append("=")
                                .append(URLEncoder.encode(depositoryInfo.getUserDevice(), "UTF-8"))
                                .toString();
                        Bundle bundle = new Bundle();
                        bundle.putString(WebViewMore.INTENT_KEY_URL, url);
                        bundle.putString(WebViewMore.INTENT_KEY_TITLE, "提现");
                        bundle.putString(WebViewMore.INTENT_KEY_POST_PARAMETER, postPara);
                        ActivityTools.switchActivity(context, WebViewMore.class, bundle);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Throwable e) {
                hideDiglog(context);
            }
        });

    }


    private void hideDiglog(Context mActivity) {
        if (mActivity instanceof Activity) {
            BaseActivity baseActivity = (BaseActivity) mActivity;
            baseActivity.hideLoadingDialog();
        }
    }

    private void showDiglog(Context mActivity) {
        if (mActivity instanceof Activity) {
            BaseActivity baseActivity = (BaseActivity) mActivity;
            baseActivity.showLoadingDialog();
        }
    }

    /**
     * 退出登录时手动回收释放资源
     */
    public void onDestroy() {
        if (mUserInfoBean != null)
            mUserInfoBean = null;
        if (commonInfo != null)
            commonInfo = null;
    }

    /**
     * 开通存管或者解绑或者绑定银行卡时延迟500毫秒联网请求更新本地数据
     *
     * @param context
     */
    public void updateLocalUserInfo(final Context context) {
        final String token = SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, "");
        if (TextUtils.isEmpty(token)) return;

        //延迟500毫秒执行防止后台数据库更新不及时
        ThreadPoolManager.getInstance(DespositAccountManager.class.getName()).schedule(new Runnable() {
            @Override
            public void run() {

                Singlton.getInstance(LoginLogic.class).getUserInfo(context, token, new OnResponseListener<UserInfo>() {
                    @Override
                    public void onSuccess(UserInfo response) {
                        if (TextUtils.equals("0", response.code)) {
                            if (response != null) {
                                //更新缓存的用户信息bean防止再次调用判断没及时更新
                                mUserInfoBean = response.getData();
                                UserInfoUtils.getInstance().setUserInfo(context, response);
                                //移除异步线程
                                ThreadPoolManager.getInstance(DespositAccountManager.class.getName()).cancelTaskThreads(DespositAccountManager.class.getName(), true);
                            }
                        }

                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });
            }
        }, 500, TimeUnit.MILLISECONDS);
    }

    /**
     * 更新本地缓存，避免引起再次点击本地状态没有更新
     *
     * @param userInfoBean
     */
    public void updateLocalUserInfo(UserInfoBean userInfoBean) {
        if (mUserInfoBean != null && userInfoBean != null)
            mUserInfoBean = userInfoBean;
    }

}