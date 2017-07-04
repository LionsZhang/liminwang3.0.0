package com.example.administrator.lmw.config;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.entity.DepositoryEntity;
import com.example.administrator.lmw.entity.DespositStatus;
import com.example.administrator.lmw.finance.entity.DebtBuyEntity;
import com.example.administrator.lmw.finance.entity.PurchaseEntity;
import com.example.administrator.lmw.http.CovertHttpSubscriber;
import com.example.administrator.lmw.http.HttpApiInterface;
import com.example.administrator.lmw.http.HttpUrl;
import com.example.administrator.lmw.http.LmwHttp;
import com.example.administrator.lmw.http.RxManager;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.credit.Entity.CreditResultBean;
import com.example.administrator.lmw.mine.fill.entity.FillResultBean;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.ToastUtil;

import java.util.Map;

/**
 * 银行存管相关的网络请求
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/5/18 10:07
 **/
public class DespositLogic {


    /**
     * 存管相关的操作
     *
     * @param context
     * @param desOpetate         操作的类型
     * @param onResponseListener
     */
    public void despositOperate(final Context context, DespositOperate desOpetate, final OnResponseListener<DepositoryEntity> onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(desOpetate.getUrl());

        if (para != null) {
            para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
            RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<DepositoryEntity>>(context, desOpetate.toString()) {

                @Override
                public void onSuccess(BaseResult<DepositoryEntity> baseResponse) {
                    ALLog.e("===============存管baseResponse.getCode()" + baseResponse.getCode());
                    ALLog.e("===============存管baseResponse.getmsg()" + baseResponse.getMsg());
                    if (baseResponse != null && baseResponse.getData() != null && onResponseListener != null) {
                        onResponseListener.onSuccess(baseResponse.getData());
                    }
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    if (onResponseListener != null)
                        onResponseListener.onFail(e);

                }
            });
        }


    }

    /**
     * 获取相关存管操作的结果，得到结果根据操作类型desOpetate进行相关的操作
     *
     * @param context
     * @param desOpetate 操作的类型
     * @param requestNo  请求流水号
     */

    public void getDespositCreditResult(final Context context, final DespositOperate desOpetate, final String requestNo, final OnResponseListener<BaseResult<CreditResultBean>> onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(desOpetate.getUrl());

        if (para != null) {
            para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
            para.put("requestNo", requestNo);
            RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<CreditResultBean>>(context, desOpetate.toString()) {

                @Override
                public void onSuccess(BaseResult<CreditResultBean> result) {
                    if (onResponseListener != null) {
                        onResponseListener.onSuccess(result);

                    }
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    if (onResponseListener != null) {
                        onResponseListener.onFail(e);
                    }
                }
            });
        }

    }

    /**
     * 获取相关存管操作的结果，得到结果根据操作类型desOpetate进行相关的操作
     *
     * @param context
     * @param desOpetate 操作的类型
     * @param requestNo  请求流水号
     */
    public void getDespositFillResult(final Context context, final DespositOperate desOpetate, final String requestNo, final OnResponseListener<BaseResult<FillResultBean>> onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(desOpetate.getUrl());

        if (para != null) {
            para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
            para.put("requestNo", requestNo);
            RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<FillResultBean>>(context, desOpetate.toString()) {

                @Override
                public void onSuccess(BaseResult<FillResultBean> result) {
                    if (onResponseListener != null) {
                        onResponseListener.onSuccess(result);

                    }
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    if (onResponseListener != null) {
                        onResponseListener.onFail(e);
                    }
                }
            });
        }

    }

    /**
     * 获取相关存管操作的结果，得到结果根据操作类型desOpetate进行相关的操作
     *
     * @param context
     * @param desOpetate 操作的类型
     * @param requestNo  请求流水号
     */
    public void getDespositOperateResult(final Context context, final DespositOperate desOpetate, final String requestNo, final OnResponseListener<BaseResult<DespositStatus>> onResponseListener) {

        Map<String, String> para = LmwHttp.getInstance().getBasePara(desOpetate.getUrl());

        if (para != null) {
            para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
            para.put("requestNo", requestNo);
            RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<DespositStatus>>(context, desOpetate.toString()) {

                @Override
                public void onSuccess(BaseResult<DespositStatus> result) {
                    if (onResponseListener != null) {
                        onResponseListener.onSuccess(result);

                    }
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    if (onResponseListener != null) {
                        onResponseListener.onFail(e);
                    }
                }
            });
        }

    }


    /**
     * 开通存管
     *
     * @param context
     * @param onResponseListener
     */
    public void getDepositoryPostPara(final Context context, String despositStatu, final OnResponseListener<DepositoryEntity> onResponseListener) {
        Map<String, String> para = null;
        if (TextUtils.equals(despositStatu, PreferenceCongfig.OPEN_DESPONSITY_NOT)) {//-1
            para = LmwHttp.getInstance().getBasePara(HttpUrl.OPEN_DEPOSITORY_ACCOUNT);
        } else if (TextUtils.equals(despositStatu, PreferenceCongfig.OPEN_DESPONSITY_ACTIVE)) {//0
            para = LmwHttp.getInstance().getBasePara(HttpUrl.ACTIVATE_DEPOSITORY_ACCOUNT);
        }
        if (para != null) {
            para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
//            para.put("redirectUrl", "www.baidu.com");
            RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<DepositoryEntity>>(context, "depository_01") {

                @Override
                public void onSuccess(BaseResult<DepositoryEntity> baseResponse) {
                    ALLog.e("===============存管baseResponse.getCode()" + baseResponse.getCode());
                    ALLog.e("===============存管baseResponse.getmsg()" + baseResponse.getMsg());
                    if (baseResponse != null && baseResponse.getData() != null && onResponseListener != null) {
                        onResponseListener.onSuccess(baseResponse.getData());

                    } else {
                        ToastUtil.showToast(context, baseResponse.getMsg());
                        hideDiglog(context);
                    }


                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    onResponseListener.onFail(e);
                }

            });
        }

    }


    /**
     * 存管充值
     *
     * @param context
     * @param onResponseListener
     */
    public void doFill(final Context context, String bankCode, String amount, String rechargeWay, final OnResponseListener<DepositoryEntity> onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.DEPOSITORY_PAY_RECHARGE);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        para.put("bankCode", bankCode);
//        para.put("rechargeRedirectUrl", "www.baidu.com");
        para.put("rechargeWay", rechargeWay);
        para.put("amount", amount);
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<DepositoryEntity>>(context, "depository_01") {

                    @Override
                    public void onSuccess(BaseResult<DepositoryEntity> baseResponse) {
                        ALLog.e("===============存管baseResponse.getCode()" + baseResponse.getCode());
                        ALLog.e("===============存管baseResponse.getmsg()" + baseResponse.getMsg());
                        if (baseResponse != null && baseResponse.getData() != null && onResponseListener != null) {
                            onResponseListener.onSuccess(baseResponse.getData());

                        } else {
                            ToastUtil.showToast(context, baseResponse.getMsg());
                            hideDiglog(context);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        onResponseListener.onFail(e);
                    }
                }
        );

    }

    /**
     * 存管提现
     *
     * @param context
     * @param onResponseListener
     */
    public void doCredit(final Context context, String poundage, String amount, final OnResponseListener<DepositoryEntity> onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.DEPOSITORY_PAY_CREDIT);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
//        para.put("withdrawRedirectUrl", "www.baidu.com");
        para.put("poundage", poundage);
        para.put("amount", amount);
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<DepositoryEntity>>(context, "depository_credit") {

            @Override
            public void onSuccess(BaseResult<DepositoryEntity> baseResponse) {
                ALLog.e("===============存管baseResponse.getCode()" + baseResponse.getCode());
                ALLog.e("===============存管baseResponse.getmsg()" + baseResponse.getMsg());
                if (baseResponse != null && baseResponse.getData() != null && onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse.getData());

                } else {
                    hideDiglog(context);
                    ToastUtil.showToast(context, baseResponse.getMsg());

                }

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (onResponseListener != null)
                    onResponseListener.onFail(e);

            }
        });


    }

    /**
     * 查询存管状态
     *
     * @param context
     * @param onResponseListener
     */
    public void queryDespositStatus(final Context context, final OnResponseListener<DespositStatus> onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.QUERY_OPEN_DEPOSITORY_STATUS);

        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));

        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<DespositStatus>>(context) {

            @Override
            public void onSuccess(BaseResult<DespositStatus> baseResponse) {
                if (baseResponse != null && baseResponse.getData() != null && onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse.getData());

                } else {
                    hideDiglog(context);
                    ToastUtil.showToast(context, baseResponse.getMsg());
                }

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (onResponseListener != null)
                    onResponseListener.onFail(e);

            }
        });


    }


    /**
     * 存管 标的购买接口
     *
     * @param context
     * @param borrowId           标的id
     * @param continueFlag       续投标识，-1-不续投、0-本息续投、1-本金续投、2-利息续投
     * @param couponId           代金券id
     * @param investAmount       支付金额
     * @param onResponseListener
     */
    public void getPurchasexw(final Context context, String borrowId, String continueFlag, String couponId, String investAmount, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.BORROW_PURCHASE_XW);
        para.put("borrowId", borrowId);
        para.put("continueFlag", continueFlag);
        para.put("couponId", couponId);
        para.put("investAmount", investAmount);
//        para.put("payPassword", payPassword);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));

        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<DepositoryEntity>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (onResponseListener != null)
                    onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<DepositoryEntity> purchaseEntity) {
                if (purchaseEntity != null && purchaseEntity.getData() != null && onResponseListener != null
                        && TextUtils.equals(purchaseEntity.getCode(), "0")) {
                    onResponseListener.onSuccess(purchaseEntity.getData());
                } else {
                    ToastUtil.showToast(context, purchaseEntity.getMsg());
                }
                ALLog.e(purchaseEntity.getCode() + "");
                ALLog.e(purchaseEntity.getMsg());
            }
        });
    }

    /**
     * 标得购买 返回结果
     *
     * @param context
     * @param desOpetate
     * @param requestNo
     * @param onResponseListener
     */
    public void getPurchasexwResult(final Context context, final DespositOperate desOpetate, final String requestNo, final OnResponseListener<BaseResult<PurchaseEntity>> onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(desOpetate.getUrl());
        if (para != null) {
            para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
            para.put("requestNo", requestNo);
            RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<PurchaseEntity>>(context, desOpetate.toString()) {

                @Override
                public void onSuccess(BaseResult<PurchaseEntity> result) {
                    if (onResponseListener != null) {
                        onResponseListener.onSuccess(result);

                    }
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    if (onResponseListener != null) {
                        onResponseListener.onFail(e);
                    }
                }
            });
        }
    }

    /**
     * 存管 债权 购买接口
     *
     * @param context
     * @param debtId             债权ID	string	[是否必输：是]
     * @param investAmount       投资金额	number
     * @param onResponseListener
     */
    public void getDebtBuyXW(final Context context, String debtId, String investAmount, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.BORROW_DEBT_BUY_XW);
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        para.put("debtId", debtId);
        para.put("investAmount", investAmount);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<DepositoryEntity>>(context) {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (onResponseListener != null)
                    onResponseListener.onFail(e);

            }

            @Override
            public void onSuccess(BaseResult<DepositoryEntity> result) {
                if (result != null && result.getData() != null && onResponseListener != null
                        && TextUtils.equals(result.getCode(), "0")) {
                    onResponseListener.onSuccess(result.getData());
                } else {
                    ToastUtil.showToast(context, result.getMsg());
                }
            }
        });
    }


    /**
     * 债权购买 返回结果
     *
     * @param context
     * @param desOpetate
     * @param requestNo
     * @param onResponseListener
     */
    public void getDebtBuyXWResult(final Context context, final DespositOperate desOpetate, final String requestNo, final OnResponseListener<BaseResult<DebtBuyEntity>> onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(desOpetate.getUrl());
        if (para != null) {
            para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
            para.put("requestNo", requestNo);
            RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<DebtBuyEntity>>(context, desOpetate.toString()) {

                @Override
                public void onSuccess(BaseResult<DebtBuyEntity> result) {
                    if (onResponseListener != null) {
                        onResponseListener.onSuccess(result);

                    }
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    if (onResponseListener != null) {
                        onResponseListener.onFail(e);
                    }
                }
            });
        }
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
}
