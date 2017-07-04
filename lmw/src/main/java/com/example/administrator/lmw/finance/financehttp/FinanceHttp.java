package com.example.administrator.lmw.finance.financehttp;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.finance.entity.CashCouponsEntity;
import com.example.administrator.lmw.finance.entity.CreditEntity;
import com.example.administrator.lmw.finance.entity.DataCateExtend;
import com.example.administrator.lmw.finance.entity.DataFinancial;
import com.example.administrator.lmw.finance.entity.DataFinancialCategory;
import com.example.administrator.lmw.finance.entity.DataProductItem;
import com.example.administrator.lmw.finance.entity.DetailEntity;
import com.example.administrator.lmw.finance.entity.DetailFragmentEntity;
import com.example.administrator.lmw.finance.entity.ExpectedReturn;
import com.example.administrator.lmw.finance.entity.InvestmentEntity;
import com.example.administrator.lmw.finance.entity.MatchVerifyEntity;
import com.example.administrator.lmw.finance.entity.UserMoenyEntity;
import com.example.administrator.lmw.http.CovertHttpSubscriber;
import com.example.administrator.lmw.http.HttpApiInterface;
import com.example.administrator.lmw.http.HttpSubscriber;
import com.example.administrator.lmw.http.HttpUrl;
import com.example.administrator.lmw.http.LmwHttp;
import com.example.administrator.lmw.http.RxManager;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.invite.entity.ShareBean;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ToastUtil;

import java.util.Map;

/**
 * 理财模块 网络访问类
 * <p/>
 * Created by Administrator on 2016/8/30 0030.
 */
public class FinanceHttp {

    public static final String KEY_REMOTE_CODE = "150006";// 异地登陆code
    public static final String KEY_STOP_CODE = "1000";// 停服code
    public static final String KEY_ZOER_CODE = "0";// 返回成功code

    /**
     * 获取债权列表
     *
     * @param context
     * @param orderType
     * @param pageIndex
     * @param pageSize
     * @param sortType
     * @param onResponseListener
     */
    public void getCreditTrannsfer(final Context context, int orderType, int pageIndex, int pageSize, int sortType, final OnResponseListener onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.BORROW_PAGEDEBT_LIST);
        para.put("isApp", 1 + "");
        para.put("orderType", orderType + "");
        para.put("pageIndex", pageIndex + "");
        para.put("pageSize", pageSize + "");
        para.put("sortType", sortType + "");
        RxManager.toSubscrib(httpApiInterface.getCreditTransfer(para), new HttpSubscriber<CreditEntity>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(CreditEntity creditEntity) {
                if (creditEntity != null && TextUtils.equals(creditEntity.getCode(), KEY_REMOTE_CODE)) {
                    ActivityTools.startToLogin(context);
                } else if (creditEntity != null && TextUtils.equals(creditEntity.getCode(), KEY_STOP_CODE)) {
                    showDialog(context, creditEntity.getCode(), creditEntity.getMsg());
                } else if (TextUtils.equals(creditEntity.getCode(), KEY_ZOER_CODE) && onResponseListener != null) {
                    onResponseListener.onSuccess(creditEntity);
                } else {
                    ToastUtil.showToast(context, creditEntity.getMsg());
                }
                ALLog.e(creditEntity.getCode() + "");
                ALLog.e(creditEntity.getMsg());
            }
        });
    }


    /**
     * 获取投资记录
     * +
     *
     * @param context
     * @param borrowId           标的id
     * @param isDebt             是否为债权
     * @param pageIndex          页码
     * @param pageSize           每页数据条说
     * @param token
     * @param onResponseListener
     */
    public void getInvestmentRecord(final Context context, String borrowId, int isDebt, int pageIndex, int pageSize, String token, final OnResponseListener onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.BORROW_INVEST_LIST);
        para.put("borrowId", borrowId);
        para.put("isDebt", isDebt + "");
        para.put("pageIndex", pageIndex + "");
        para.put("pageSize", pageSize + "");
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toSubscrib(httpApiInterface.getInvestmentRecord(para), new HttpSubscriber<InvestmentEntity>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(InvestmentEntity investmentEntity) {
                if (investmentEntity != null && TextUtils.equals(investmentEntity.getCode(), KEY_REMOTE_CODE)) {
                    ActivityTools.startToLogin(context);
                } else if (investmentEntity != null && TextUtils.equals(investmentEntity.getCode(), KEY_STOP_CODE)) {
                    showDialog(context, investmentEntity.getCode(), investmentEntity.getMsg());
                } else if (TextUtils.equals(investmentEntity.getCode(), KEY_ZOER_CODE) && onResponseListener != null) {
                    onResponseListener.onSuccess(investmentEntity);
                } else {
                    ToastUtil.showToast(context, investmentEntity.getMsg());
                }
                ALLog.e(investmentEntity.getCode() + "");
                ALLog.e(investmentEntity.getMsg());
            }
        });
    }

    /**
     * 获取理财产品列表
     *
     * @param context
     * @param onResponseListener
     */
    public void getFinancialProduct(final Context context, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.APP_BORROW_PRODUCT_LIST);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));

        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<DataFinancial>>(context) {
            @Override
            public void onSuccess(BaseResult<DataFinancial> dataFinancialBaseResult) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(dataFinancialBaseResult);
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

    /**
     * 获取债权详情
     *
     * @param context
     * @param debtId             标的id
     * @param token
     * @param onResponseListener
     */
    public void getDetailFragment(final Context context, String debtId, String token, final OnResponseListener onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.BORROW_DEBT_DETAIL);
        para.put("debtId", debtId);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toSubscrib(httpApiInterface.getDetail(para), new HttpSubscriber<DetailEntity>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(DetailEntity detailEntity) {
                if (detailEntity != null && TextUtils.equals(detailEntity.getCode(), KEY_REMOTE_CODE)) {
                    ActivityTools.startToLogin(context);
                } else if (detailEntity != null && TextUtils.equals(detailEntity.getCode(), KEY_STOP_CODE)) {
                    showDialog(context, detailEntity.getCode(), detailEntity.getMsg());
                } else if (TextUtils.equals(detailEntity.getCode(), KEY_ZOER_CODE) && onResponseListener != null) {
                    onResponseListener.onSuccess(detailEntity);
                } else {
                    ToastUtil.showToast(context, detailEntity.getMsg());
                }
                ALLog.e(detailEntity.getCode() + "");
                ALLog.e(detailEntity.getMsg());
            }
        });
    }

    /**
     * 获取标的详情
     *
     * @param context
     * @param borrowId           标的id
     * @param token
     * @param onResponseListener
     */
    public void getDetailProductFragment(final Context context, String borrowId, String token, final OnResponseListener onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.BORROW_DETAIL);
        para.put("borrowId", borrowId);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toSubscrib(httpApiInterface.getDetailFragment(para), new HttpSubscriber<DetailFragmentEntity>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(DetailFragmentEntity detailFragmentEntity) {
                if (detailFragmentEntity != null && TextUtils.equals(detailFragmentEntity.getCode(), KEY_REMOTE_CODE)) {
                    ActivityTools.startToLogin(context);
                } else if (detailFragmentEntity != null && TextUtils.equals(detailFragmentEntity.getCode(), KEY_STOP_CODE)) {
                    showDialog(context, detailFragmentEntity.getCode(), detailFragmentEntity.getMsg());
                } else if (TextUtils.equals(detailFragmentEntity.getCode(), KEY_ZOER_CODE) && onResponseListener != null) {
                    onResponseListener.onSuccess(detailFragmentEntity);
                } else {
                    ToastUtil.showToast(context, detailFragmentEntity.getMsg());
                }
                ALLog.e(detailFragmentEntity.getCode() + "");
                ALLog.e(detailFragmentEntity.getMsg());
            }
        });
    }

    /**
     * 获取用户余额
     *
     * @param context
     * @param token
     * @param onResponseListener
     */
    public void getUserMoney(final Context context, String token, final OnResponseListener onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.USER_GET_BALANCE);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toSubscrib(httpApiInterface.getUserMoney(para), new HttpSubscriber<UserMoenyEntity>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(UserMoenyEntity userMoenyEntity) {
                if (userMoenyEntity != null && TextUtils.equals(userMoenyEntity.getCode(), KEY_REMOTE_CODE)) {
                    ActivityTools.startToLogin(context);
                } else if (userMoenyEntity != null && TextUtils.equals(userMoenyEntity.getCode(), KEY_STOP_CODE)) {
                    showDialog(context, userMoenyEntity.getCode(), userMoenyEntity.getMsg());
                } else if (TextUtils.equals(userMoenyEntity.getCode(), KEY_ZOER_CODE) && onResponseListener != null) {
                    onResponseListener.onSuccess(userMoenyEntity);
                } else {
                    ToastUtil.showToast(context, userMoenyEntity.getMsg());
                }
                ALLog.e(userMoenyEntity.getCode() + "");
                ALLog.e(userMoenyEntity.getMsg());
            }
        });
    }

    /**
     * 查询现金券
     *
     * @param context
     * @param busId              标的id
     * @param busType            标的类型
     * @param investAmount       投资金额
     * @param token
     * @param onResponseListener
     */
    public void getCashMatch(final Context context, String busId, String busType, String investAmount, String token, final OnResponseListener onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.BORROW_MATCH_INFO);
        para.put("busId", busId);
        para.put("busType", busType);
        para.put("investAmount", investAmount);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toSubscrib(httpApiInterface.getCashMatch(para), new HttpSubscriber<CashCouponsEntity>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(CashCouponsEntity cashCouponsEntity) {
                if (cashCouponsEntity != null && TextUtils.equals(cashCouponsEntity.getCode(), KEY_REMOTE_CODE)) {
                    ActivityTools.startToLogin(context);
                } else if (cashCouponsEntity != null && TextUtils.equals(cashCouponsEntity.getCode(), KEY_STOP_CODE)) {
                    showDialog(context, cashCouponsEntity.getCode(), cashCouponsEntity.getMsg());
                } else if (TextUtils.equals(cashCouponsEntity.getCode(), KEY_ZOER_CODE) && onResponseListener != null) {
                    onResponseListener.onSuccess(cashCouponsEntity);
                } else {
                    ToastUtil.showToast(context, cashCouponsEntity.getMsg());
                }
                ALLog.e(cashCouponsEntity.getCode() + "");
                ALLog.e(cashCouponsEntity.getMsg());
            }
        });
    }

    /**
     * 购买标的
     *
     * @param context
     * @param borrowId           标的id
     * @param continueFlag       续投标识，-1-不续投、0-本息续投、1-本金续投、2-利息续投
     * @param couponId           代金券id
     * @param investAmount       支付金额
     * @param payPassword        交易密码
     * @param token
     * @param onResponseListener
     */
//    public void getPurchase(final Context context, String borrowId, String continueFlag, String couponId, String investAmount, String payPassword, String token, final OnResponseListener onResponseListener) {
////        try {
//            Map<String, String> para;
//            HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
////            final boolean isEncrypt = ConfigManager.getInstance().getEnableRsa(context);
////            if (isEncrypt) {
////                para = LmwHttp.getInstance().getBasePara(HttpUrl.BORROW_PURCHASE_RSA);
////                String cipherStr = AESCoder.getRandom(16);
////                StringBuilder singStringBuilder = new StringBuilder();
////                String requestStr = singStringBuilder.append("borrowId=")
////                        .append(borrowId)
////                        .append("&")
////                        .append("continueFlag=")
////                        .append(continueFlag)
////                        .append("&")
////                        .append("couponId=")
////                        .append(couponId)
////                        .append("&")
////                        .append("investAmount=")
////                        .append(investAmount)
////                        .append("&")
////                        .append("payPassword=")
////                        .append(payPassword)
////                        .append("&")
////                        .append("token=")
////                        .append(SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""))
////                        .toString();
////
////                String aes = AESCoder.encryptToBase64(requestStr, cipherStr);
////                String rsa = RSACoder.encryptByPublicKey(
////                        ConfigManager.getInstance().getPublicKeyClient(context), cipherStr);
////                ALLog.e("AES加密reqData=" + aes);
////                ALLog.e("RSA加密cipherStr=" + rsa);
////                para.put("cipher", rsa);
////                para.put("reqData", aes);
////            } else {
//            para = LmwHttp.getInstance().getBasePara(HttpUrl.BORROW_PURCHASE_XW);
//            para.put("borrowId", borrowId);
//            para.put("continueFlag", continueFlag);
//            para.put("couponId", couponId);
//            para.put("investAmount", investAmount);
//            para.put("payPassword", payPassword);
//            para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
//
////            }
//
//            RxManager.toSubscrib(httpApiInterface.getPurchase(para), new HttpSubscriber<PurchaseEntity>(context) {
//                @Override
//                public void onError(Throwable e) {
//                    super.onError(e);
//                    if (onResponseListener != null)
//                        onResponseListener.onFail(e);
//                }
//
//                @Override
//                public void onNext(PurchaseEntity purchaseEntity) {
//                    if (purchaseEntity != null && TextUtils.equals(purchaseEntity.getCode(), KEY_REMOTE_CODE)) {
//                        ActivityTools.startToLogin(context);
//                    } else if (purchaseEntity != null && TextUtils.equals(purchaseEntity.getCode(), KEY_STOP_CODE)) {
//                        showDialog(context, purchaseEntity.getCode(), purchaseEntity.getMsg());
//                    } else if (purchaseEntity != null && TextUtils.equals(purchaseEntity.getCode(), KEY_ZOER_CODE) && onResponseListener != null) {
////                        if (purchaseEntity.getData() != null && isEncrypt) {
////                            try {
////                                String aesKey = RSACoder.decryptByPrivateKey(
////                                        ConfigManager.getInstance().getPrivateKeyClient(context), purchaseEntity.getData().getCipher());
////                                String resData = AESCoder.decryptFromBase64(purchaseEntity.getData().getRspData(), aesKey);
////                                ALLog.e("resData" + resData);
////                                ALLog.e("aesKey" + aesKey);
////                                Gson gson = new Gson();
////                                PurchaseEntity.DataBean dataBean = gson.fromJson(resData, PurchaseEntity.DataBean.class);
////                                purchaseEntity.setData(dataBean);
////                            } catch (Exception e) {
////                                e.printStackTrace();
////                            }
////                        }
//                        onResponseListener.onSuccess(purchaseEntity);
//
//                    } else {
//                        ToastUtil.showToast(context, purchaseEntity.getMsg());
//                    }
//                    ALLog.e(purchaseEntity.getCode() + "");
//                    ALLog.e(purchaseEntity.getMsg());
//                }
//            });
////        } catch (Exception e) {
////
////        }
//
//    }

    /**
     * 购买债权
     *
     * @param context
     * @param debtId
     * @param investAmount
     * @param tradingPasswd
     * @param token
     * @param onResponseListener
     */
//    public void getDebtBuy(final Context context, String debtId, String investAmount, String tradingPasswd, String token, final OnResponseListener onResponseListener) {
//        try {
//            Map<String, String> para;
//            HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
//            final boolean isEncrypt = ConfigManager.getInstance().getEnableRsa(context);
//            if (isEncrypt) {
//                para = LmwHttp.getInstance().getBasePara(HttpUrl.BORROW_DEBT_BUY_RSA);
//                String cipherStr = AESCoder.getRandom(16);
//                StringBuilder singStringBuilder = new StringBuilder();
//                String requestStr = singStringBuilder.append("debtId=")
//                        .append(debtId)
//                        .append("&")
//                        .append("investAmount=")
//                        .append(investAmount)
//                        .append("&")
//                        .append("tradingPasswd=")
//                        .append(tradingPasswd)
//                        .append("&")
//                        .append("token=")
//                        .append(SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""))
//                        .toString();
//
//                String aes = AESCoder.encryptToBase64(requestStr, cipherStr);
//                String rsa = RSACoder.encryptByPublicKey(
//                        ConfigManager.getInstance().getPublicKeyClient(context), cipherStr);
//                ALLog.e("AES加密reqData=" + aes);
//                ALLog.e("RSA加密cipherStr=" + rsa);
//                para.put("cipher", rsa);
//                para.put("reqData", aes);
//            } else {
//                para = LmwHttp.getInstance().getBasePara(HttpUrl.BORROW_DEBT_BUY);
//                para.put("debtId", debtId);
//                para.put("investAmount", investAmount);
//                para.put("tradingPasswd", tradingPasswd);
//                para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
//            }
//            RxManager.toSubscrib(httpApiInterface.getDebtBuy(para), new HttpSubscriber<DebtBuyEntity>(context) {
//                @Override
//                public void onError(Throwable e) {
//                    super.onError(e);
//                    onResponseListener.onFail(e);
//                }
//
//                @Override
//                public void onNext(DebtBuyEntity debtBuyEntity) {
//                    if (debtBuyEntity != null && TextUtils.equals(debtBuyEntity.getCode(), KEY_REMOTE_CODE)) {
//                        ActivityTools.startToLogin(context);
//                    } else if (debtBuyEntity != null && TextUtils.equals(debtBuyEntity.getCode(), KEY_STOP_CODE)) {
//                        showDialog(context, debtBuyEntity.getCode(), debtBuyEntity.getMsg());
//                    } else if (TextUtils.equals(debtBuyEntity.getCode(), KEY_ZOER_CODE) && onResponseListener != null) {
//                        if (debtBuyEntity.getData() != null && isEncrypt) {
//                            try {
//                                String aesKey = RSACoder.decryptByPrivateKey(
//                                        ConfigManager.getInstance().getPrivateKeyClient(context), debtBuyEntity.getData().getCipher());
//                                String resData = AESCoder.decryptFromBase64(debtBuyEntity.getData().getRspData(), aesKey);
//                                ALLog.e("resData" + resData);
//                                ALLog.e("aesKey" + aesKey);
//                                Gson gson = new Gson();
//                                DebtBuyEntity.DataBean dataBean = gson.fromJson(resData, DebtBuyEntity.DataBean.class);
//                                debtBuyEntity.setData(dataBean);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        onResponseListener.onSuccess(debtBuyEntity);
//                    } else {
//                        ToastUtil.showToast(context, debtBuyEntity.getMsg());
//                    }
//                    ALLog.e(debtBuyEntity.getCode() + "");
//                    ALLog.e(debtBuyEntity.getMsg());
//                }
//            });
//        } catch (Exception e) {
//
//        }
//    }

    /**
     * 理财产品子列表
     *
     * @param context
     * @param cateId
     * @param orderType          排序条件1：年利率2：投资时间
     * @param pageIndex          页码
     * @param pageSize           数量
     * @param sortType           排序类型 1：升序 2：降序
     * @param onResponseListener
     */
    public void getBorrowProduct(final Context context, String cateId, int orderType, int pageIndex, int pageSize, int sortType, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.APP_BORROW_PRODUCT_PAGE);
        para.put("cateId", cateId);
        para.put("orderType", orderType + "");
        para.put("pageIndex", pageIndex + "");
        para.put("pageSize", pageSize + "");
        para.put("sortType", sortType + "");
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));

        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<DataProductItem>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (onResponseListener != null) {
                    onResponseListener.onFail(e);
                }
            }

            @Override
            public void onSuccess(BaseResult<DataProductItem> dataProductItemBaseResult) {
                if (TextUtils.equals(dataProductItemBaseResult.getCode(), KEY_ZOER_CODE) && onResponseListener != null) {
                    onResponseListener.onSuccess(dataProductItemBaseResult);
                } else {
                    ToastUtil.showToast(context, dataProductItemBaseResult.getMsg());
                }

            }
        });
    }

    /**
     * 获取分享内容
     *
     * @param context
     * @param borrowId
     * @param shareType
     * @param onResponseListener
     */
    public void getShareContent(final Context context, String borrowId, String shareType, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.getShareContent);
        para.put("borrowId", borrowId);
        para.put("shareType", shareType);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<ShareBean>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<ShareBean> friendListEntity) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(friendListEntity);
                }
            }
        });
    }

    /**
     * 获取理财子列表头部图片
     *
     * @param context
     * @param cateId
     * @param onResponseListener
     */
    public void getHeadImage(final Context context, String cateId, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.GET_BORROW_CATE_EXTEND);
        para.put("cateId", cateId);

        RxManager.toGetSubscrib(para, new CovertHttpSubscriber<BaseResult<DataCateExtend>>(context) {
            @Override
            public void onSuccess(BaseResult<DataCateExtend> dataCateExtendBaseResult) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(dataCateExtendBaseResult);
                }
            }

        });
    }

    /**
     * @param context
     * @param borrowId           标的id
     * @param investAmount       投资金额
     * @param typeValue          标的类型 1：转让标 0：其他标
     * @param onResponseListener
     */
    public void getExpectedReturn(final Context context, String borrowId, String investAmount, String typeValue, final OnResponseListener onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.BORROW_EXPECT_PROFIT);
        para.put("borrowId", borrowId);
        para.put("investAmount", investAmount);
        para.put("typeValue", typeValue);
        RxManager.toSubscrib(httpApiInterface.getExpectedReturn(para), new HttpSubscriber<ExpectedReturn>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(ExpectedReturn expectedReturn) {
                if (expectedReturn != null && TextUtils.equals(expectedReturn.getCode(), KEY_REMOTE_CODE)) {
                    ActivityTools.startToLogin(context);
                } else if (expectedReturn != null && TextUtils.equals(expectedReturn.getCode(), KEY_STOP_CODE)) {
                    showDialog(context, expectedReturn.getCode(), expectedReturn.getMsg());
                } else if (TextUtils.equals(expectedReturn.getCode(), KEY_ZOER_CODE) && onResponseListener != null) {
                    onResponseListener.onSuccess(expectedReturn);
                } else {
                    ToastUtil.showToast(context, expectedReturn.getMsg());
                }
            }
        });
    }

    /**
     * 获取子列表在售数量
     *
     * @param context
     * @param onResponseListener
     */
    public void getHeadCategory(final Context context, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.BORROW_CATEGORY_LIST);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toGetSubscrib(para, new CovertHttpSubscriber<BaseResult<DataFinancialCategory>>(context) {
            @Override
            public void onSuccess(BaseResult<DataFinancialCategory> dataFinancialCategoryBaseResult) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(dataFinancialCategoryBaseResult);
                }
            }
        });
    }

    /**
     * 查询卡券校验V3
     *
     * @param busId        标的Id    string	[是否必输：是]，[示例：1]
     * @param couponId     卡券ID	number	[是否必输：否]，[示例：1]
     * @param investAmount 投资金额    number	[是否必输：是]，[示例：5000]
     */
    public void getMatchVerify(final Context context, String busId, String couponId, String investAmount, final OnResponseListener onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.V3_BORROW_MATCH_VERIFY);
        para.put("busId", busId);
        para.put("couponId", couponId);
        para.put("investAmount", investAmount);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toSubscrib(httpApiInterface.getMatchVerify(para), new HttpSubscriber<MatchVerifyEntity>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(MatchVerifyEntity matchVerifyEntity) {
                if (matchVerifyEntity != null && TextUtils.equals(matchVerifyEntity.getCode(), KEY_REMOTE_CODE)) {
                    ActivityTools.startToLogin(context);
                } else if (matchVerifyEntity != null && TextUtils.equals(matchVerifyEntity.getCode(), KEY_STOP_CODE)) {
                    showDialog(context, matchVerifyEntity.getCode(), matchVerifyEntity.getMsg());
                } else if (TextUtils.equals(matchVerifyEntity.getCode(), KEY_ZOER_CODE) && onResponseListener != null) {
                    onResponseListener.onSuccess(matchVerifyEntity);
                } else {
                    ToastUtil.showToast(context, matchVerifyEntity.getMsg());
                }
            }
        });
    }

    /**
     * 是否续投提示弹框
     *
     * @param context
     * @param isContinuedFlag    是否弹过框	string	0：没有，1：弹过
     * @param onResponseListener
     */
    public void getContinuedFlag(final Context context, String isContinuedFlag, final OnResponseListener<BaseResult<String>> onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.IS_CONTINUED_FLAG);
        para.put("isContinuedFlag", isContinuedFlag);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<String>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (onResponseListener != null) {
                    onResponseListener.onFail(e);
                }
            }

            @Override
            public void onSuccess(BaseResult<String> result) {
                if (result != null && onResponseListener != null) {
                    onResponseListener.onSuccess(result);
                }
            }
        });

    }

    /**
     * 停服公告
     *
     * @param context
     * @param code
     * @param msg
     */
    public void showDialog(Context context, String code, String msg) {
        Singlton.getInstance(PopWindowManager.class).showStopDialogShow(context, code, msg);
    }
}
