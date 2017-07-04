package com.example.administrator.lmw.config;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.entity.BindSuccessEvent;
import com.example.administrator.lmw.entity.DespositStatus;
import com.example.administrator.lmw.entity.FreshFloatIconEvent;
import com.example.administrator.lmw.finance.activity.BuySuccee;
import com.example.administrator.lmw.finance.entity.DebtBuyEntity;
import com.example.administrator.lmw.finance.entity.PurchaseEntity;
import com.example.administrator.lmw.finance.utils.BuyType;
import com.example.administrator.lmw.http.ResponseStatue;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.UserInfoUtils;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.login.entity.UserInfoBean;
import com.example.administrator.lmw.mine.credit.CreditResultActivity;
import com.example.administrator.lmw.mine.credit.Entity.CreditResultBean;
import com.example.administrator.lmw.mine.fill.FillResultActivity;
import com.example.administrator.lmw.mine.fill.entity.FillResultBean;
import com.example.administrator.lmw.mine.seting.DesponsityStatueActivity;
import com.example.administrator.lmw.mine.seting.DespositOperateFailActivity;
import com.example.administrator.lmw.mine.seting.DespositOperateSuccessActivity;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.Singlton;

import org.greenrobot.eventbus.EventBus;


/**
 * 存管操作结果处理业务
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/5/25 10:34
 **/
public class DesposityResultManager {
    /**
     * RECHARGE
     * WITHDRAW
     * PERSONAL_REGISTER_EXPAND
     * ACTIVATE_STOCKED_USER
     * PERSONAL_BIND_BANKCARD_EXPAND
     * UNBIND_BANKCARD
     * MODIFY_MOBILE_EXPAND
     * RESET_PASSWORD
     * USER_PRE_TRANSACTION
     */
    private Context mContext;
    private String routeType;
    private String oderNo;
//    public static final String ROUTE_TYPE = "route_type";
//    public static final String REQUEST_NO = "request_no";
    public static final String FILL_RESULT = "fill_result";
    public static final String CREDIT_RESULT = "credit_result";
//    public static final String BUY_TYPE = "buy_type";
//    private final String recharge = "RECHARGE";
//    private final String withdraw = "WITHDRAW";
//    private final String personal_register_expand = "PERSONAL_REGISTER_EXPAND";//开通存管账号
//    private final String activate_stocked_user = "ACTIVATE_STOCKED_USER";//激活存管账号
//    private final String personal_bind_bankcard_expand = "PERSONAL_BIND_BANKCARD_EXPAND";//绑定银行卡
//    private final String unbind_bankcard = "UNBIND_BANKCARD";//解绑银行卡
//    private final String modify_mobile_expand = "MODIFY_MOBILE_EXPAND";//修改预留手机
//    private final String reset_password = "RESET_PASSWORD";//重置交易密码
//    private final String user_pre_transaction = "USER_PRE_TRANSACTION";//购买标的

    private DespositOperate mDespositOperate;
    private String mBuyType;//购买类型。0标的，1债权
    private UserInfo userInfo;
    private UserInfoBean userInfoBean;
    private DespositStatus despositStatus;

    public void init(Context context, String routeType, String oderNo, String buyType) {
        this.mContext = context;
        this.mBuyType = buyType;
        this.routeType = routeType;
        this.oderNo = oderNo;
        getUserCacheInfo();
        check();
    }

    private void getUserCacheInfo() {
        userInfo = UserInfoUtils.getInstance().getUserInfo(mContext);
        if (userInfo != null)
            userInfoBean = userInfo.getData();

    }

    private void check() {
        if (TextUtils.equals(routeType, RouteType.recharge)) {
            mDespositOperate = DespositOperate.RESULT_RECHARGE;
            getFillResult(mDespositOperate, oderNo);
        } else if (TextUtils.equals(routeType, RouteType.withdraw)) {
            mDespositOperate = DespositOperate.RESULT_WITHDRAW;
            getCreditResult(mDespositOperate, oderNo);
        } else if (TextUtils.equals(routeType, RouteType.personal_register_expand)) {
            mDespositOperate = DespositOperate.RESULT_PERSONAL_REGISTER_EXPAND;
            getResult(mDespositOperate, oderNo);
        } else if (TextUtils.equals(routeType, RouteType.activate_stocked_user)) {
            mDespositOperate = DespositOperate.RESULT_ACTIVATE_STOCKED_USER;
            getResult(mDespositOperate, oderNo);
        } else if (TextUtils.equals(routeType, RouteType.personal_bind_bankcard_expand)) {
            mDespositOperate = DespositOperate.RESULT_PERSONAL_BIND_BANKCARD_EXPAND;
            getResult(mDespositOperate, oderNo);
        } else if (TextUtils.equals(routeType, RouteType.unbind_bankcard)) {
            mDespositOperate = DespositOperate.RESULT_UNBIND_BANKCARD;
            getResult(mDespositOperate, oderNo);
        } else if (TextUtils.equals(routeType, RouteType.modify_mobile_expand)) {
            mDespositOperate = DespositOperate.RESULT_MODIFY_MOBILE_EXPAND;
            getResult(mDespositOperate, oderNo);
        } else if (TextUtils.equals(routeType, RouteType.reset_password)) {
            mDespositOperate = DespositOperate.RESUET_PASSWORD;
            getResult(mDespositOperate, oderNo);
        } else if (TextUtils.equals(routeType, RouteType.user_pre_transaction)) {
            if (TextUtils.equals(mBuyType, BuyType.PROBLEM)) {
                mDespositOperate = DespositOperate.RESULT_USER_PRE_TRANSACTION;
                getPurchasexwResult(mDespositOperate, oderNo);
            } else if (TextUtils.equals(mBuyType, BuyType.CREDIT)) {
                mDespositOperate = DespositOperate.RESULT_USET_DEBT_TRANSACTION;
                getDebtBuyXWResult(mDespositOperate, oderNo);
            }
        }

    }

    /**
     * 获取结果
     */
    private void getResult(final DespositOperate despositOperate, String requestNo) {
        Singlton.getInstance(DespositLogic.class).getDespositOperateResult(mContext, despositOperate, requestNo, new OnResponseListener<BaseResult<DespositStatus>>() {
            @Override
            public void onSuccess(BaseResult<DespositStatus> result) {
                if (result != null) {
                    if (despositOperate == DespositOperate.RESULT_PERSONAL_REGISTER_EXPAND || despositOperate == DespositOperate.RESULT_ACTIVATE_STOCKED_USER) {//开通存管或者激活存管
                        despositStatus = result.getData();
                    }
                    toShowByResult(despositOperate, result);
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });

    }

    /**
     * 标得购买获取返回结果
     *
     * @param despositOperate
     * @param requestNo
     */
    private void getPurchasexwResult(final DespositOperate despositOperate, String requestNo) {
        Singlton.getInstance(DespositLogic.class).getPurchasexwResult(mContext, despositOperate, requestNo, new OnResponseListener<BaseResult<PurchaseEntity>>() {
            @Override
            public void onSuccess(BaseResult<PurchaseEntity> result) {
//                Intent intent = null;
                if (result != null && TextUtils.equals(result.getCode(), "0")) {
                    Intent intent = new Intent(mContext, BuySuccee.class);
                    if (result.getData() != null) {
                        intent.putExtra("buyedSucSpanDesc", result.getData().getBuyedSucSpanDesc());
                        intent.putExtra("interestData", "募集完成的次日");
                        intent.putExtra("paymentData", result.getData().getEndTime());// 还款日
                        intent.putExtra("canTransfer", result.getData().getCanTransfer());//canTransfer;//0-可以转让、1-不可以转让
                        intent.putExtra(BuySuccee.INTENT_KEY_INVESTID, result.getData().getInvestId());
                    }
                    mContext.startActivity(intent);
                    EventBus.getDefault().post(new BindSuccessEvent());
                    EventBus.getDefault().post(new FreshFloatIconEvent(FreshFloatIconEvent.FRESH_ICON));
                    //关闭webview页面
                    ActivityManage.create().finishActivity();
                } else {
//                    intent = new Intent(mContext, DespositOperateFailActivity.class);
//                    intent.putExtra(PreferenceCongfig.INTENT_KEY_DESPONSIT_OPERATE, despositOperate);
//                    intent.putExtra(PreferenceCongfig.INTENT_KEY_OPEN_DESPONSITY_FAIL, result.getMsg());
//                    mContext.startActivity(intent);
                    toShowByResult(despositOperate, result);
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });

    }

    /**
     * 债权购买返回结果
     *
     * @param despositOperate
     * @param requestNo
     */
    private void getDebtBuyXWResult(final DespositOperate despositOperate, String requestNo) {
        Singlton.getInstance(DespositLogic.class).getDebtBuyXWResult(mContext, despositOperate, requestNo, new OnResponseListener<BaseResult<DebtBuyEntity>>() {
            @Override
            public void onSuccess(BaseResult<DebtBuyEntity> result) {
//                Intent intent = null;
                if (result != null && TextUtils.equals(result.getCode(), "0")) {
                    Intent intent = new Intent(mContext, BuySuccee.class);
                    if (result.getData() != null) {
                        intent.putExtra("buyedSucSpanDesc", result.getData().getBuyedSucSpanDesc());
                        intent.putExtra("interestData", "转让期结束次日");
                        intent.putExtra("paymentData", result.getData().getRepayDate().substring(0, 10));// 还款日
                        intent.putExtra("canTransfer", "1");//canTransfer;//0-可以转让、1-不可以转让
                    }
                    mContext.startActivity(intent);
                    ActivityManage.create().finishActivity();
                    EventBus.getDefault().post(new BindSuccessEvent());
                } else {
//                    intent = new Intent(mContext, DespositOperateFailActivity.class);
//                    intent.putExtra(PreferenceCongfig.INTENT_KEY_DESPONSIT_OPERATE, despositOperate);
//                    intent.putExtra(PreferenceCongfig.INTENT_KEY_OPEN_DESPONSITY_FAIL, result.getMsg());
//                    mContext.startActivity(intent);
                    toShowByResult(despositOperate, result);
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 获取充值结果
     */
    private void getFillResult(final DespositOperate despositOperate, String requestNo) {
        Singlton.getInstance(DespositLogic.class).getDespositFillResult(mContext, despositOperate, requestNo,
                new OnResponseListener<BaseResult<FillResultBean>>() {
                    @Override
                    public void onSuccess(BaseResult<FillResultBean> result) {//充值成功
                        if (result != null && TextUtils.equals(ResponseStatue.STATE_SUCCESS, result.getCode())) {
                            Intent intent = new Intent(mContext, FillResultActivity.class);
                            intent.putExtra(FILL_RESULT, result.getData());
                            mContext.startActivity(intent);
                            ActivityManage.create().finishActivity();
                            EventBus.getDefault().post(new BindSuccessEvent());
                        } /*else {//充值失败
                            toShowByResult(despositOperate, result);
                        }*/
                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });

    }

    /**
     * 获取提现结果
     */
    private void getCreditResult(final DespositOperate despositOperate, String requestNo) {
        Singlton.getInstance(DespositLogic.class).getDespositCreditResult(mContext, despositOperate, requestNo,
                new OnResponseListener<BaseResult<CreditResultBean>>() {
                    @Override
                    public void onSuccess(BaseResult<CreditResultBean> result) {
                        if (result != null && TextUtils.equals(ResponseStatue.STATE_SUCCESS, result.getCode())) {
                            Intent intent = new Intent(mContext, CreditResultActivity.class);
                            intent.putExtra(CREDIT_RESULT, result.getData());
                            mContext.startActivity(intent);
                            ActivityManage.create().finishActivity();
                            EventBus.getDefault().post(new BindSuccessEvent());
                        } else {
                            toShowByResult(despositOperate, result);
                        }

                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });

    }


    /**
     * 根据操作类型和结果，跳转到相应的结果类型页面
     */
    private void toShowByResult(DespositOperate desOpetate, BaseResult result) {
        Intent intent = null;
        if (result != null) {
            if (TextUtils.equals(ResponseStatue.STATE_SUCCESS, result.getCode())) {//成功
                if (desOpetate == DespositOperate.RESULT_PERSONAL_REGISTER_EXPAND || desOpetate == DespositOperate.RESULT_ACTIVATE_STOCKED_USER) {//开通存管或者激活存管
                    showOpenDespositResult(desOpetate);
                } else {
                    intent = new Intent(mContext, DespositOperateSuccessActivity.class);
                    intent.putExtra(PreferenceCongfig.INTENT_KEY_DESPONSIT_OPERATE, desOpetate);
                    mContext.startActivity(intent);
                }
                freshCacheData(desOpetate);
            } else {//失败
                intent = new Intent(mContext, DespositOperateFailActivity.class);
                intent.putExtra(PreferenceCongfig.INTENT_KEY_DESPONSIT_OPERATE, desOpetate);
                intent.putExtra(PreferenceCongfig.INTENT_KEY_OPEN_DESPONSITY_FAIL, result.getMsg());
                mContext.startActivity(intent);
            }
        }
        //关闭新网页面
        ActivityManage.create().finishActivity();
    }

    /**
     * 显示开通存管的结果
     *
     * @param desOpetate
     */
    private void showOpenDespositResult(DespositOperate desOpetate) {
        if (despositStatus != null && TextUtils.equals(despositStatus.getOpenDpStatus(), PreferenceCongfig.OPEN_DESPONSITY_PASS)) {//开通存管成功
            Intent intent = new Intent(mContext, DespositOperateSuccessActivity.class);
            intent.putExtra(PreferenceCongfig.INTENT_KEY_DESPONSIT_OPERATE, desOpetate);
            mContext.startActivity(intent);
        } else {//存管状态
            Intent startIntent = new Intent(mContext, DesponsityStatueActivity.class);
            startIntent.putExtra(PreferenceCongfig.INTENT_KEY_OPEN_DESPONSITY_STATE, despositStatus.getOpenDpStatus());
            mContext.startActivity(startIntent);
        }
    }

    /**
     * 刷新本地缓存
     *
     * @param despositOperate
     */
    private void freshCacheData(DespositOperate despositOperate) {
        EventBus.getDefault().post(new BindSuccessEvent());
        boolean isChangeUserInfo = false;
        if (despositOperate == DespositOperate.RESULT_PERSONAL_BIND_BANKCARD_EXPAND) {//绑卡
            userInfoBean.setIsBindBankCard(PreferenceCongfig.IS_BIND_BANKCARD);
            userInfo.setData(userInfoBean);
            UserInfoUtils.getInstance().setUserInfo(mContext, userInfo);
            isChangeUserInfo = true;
        } else if (despositOperate == DespositOperate.RESULT_PERSONAL_REGISTER_EXPAND
                || despositOperate == DespositOperate.RESULT_ACTIVATE_STOCKED_USER) {//开通存管激活存管
            userInfoBean.setOpenDpStatus(PreferenceCongfig.OPEN_DESPONSITY_PASS);
            userInfoBean.setIsRealnameAuth(PreferenceCongfig.IS_REAL_NAME_AUTH);
            userInfoBean.setIsRiskCamuluate(PreferenceCongfig.RISK_CACULATE_ALREADY);
            userInfoBean.setIsBindBankCard(PreferenceCongfig.IS_BIND_BANKCARD);
            if (despositStatus != null)
                userInfoBean.setOpenDpStatus(despositStatus.getOpenDpStatus());
            userInfo.setData(userInfoBean);
            UserInfoUtils.getInstance().setUserInfo(mContext, userInfo);
            isChangeUserInfo = true;
        } else if (despositOperate == DespositOperate.RESULT_UNBIND_BANKCARD) {//解绑
            userInfoBean.setIsBindBankCard(PreferenceCongfig.IS_REBIND_BANKCARD);
            userInfo.setData(userInfoBean);
            UserInfoUtils.getInstance().setUserInfo(mContext, userInfo);
            isChangeUserInfo = true;
        }

        if (isChangeUserInfo) {
            Singlton.getInstance(DespositAccountManager.class).updateLocalUserInfo(userInfoBean);
        }
    }

}


