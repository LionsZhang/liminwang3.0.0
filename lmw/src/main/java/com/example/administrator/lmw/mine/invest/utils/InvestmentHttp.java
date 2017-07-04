package com.example.administrator.lmw.mine.invest.utils;

import android.content.Context;

import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.http.CovertHttpSubscriber;
import com.example.administrator.lmw.http.HttpApiInterface;
import com.example.administrator.lmw.http.HttpUrl;
import com.example.administrator.lmw.http.LmwHttp;
import com.example.administrator.lmw.http.RxManager;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.invest.entity.BackPlanEntity;
import com.example.administrator.lmw.mine.invest.entity.ConfirmTransferEntity;
import com.example.administrator.lmw.mine.invest.entity.ContinueSetEntity;
import com.example.administrator.lmw.mine.invest.entity.DataTranferReward;
import com.example.administrator.lmw.mine.invest.entity.InvestmentCountEntity;
import com.example.administrator.lmw.mine.invest.entity.InvestmentTransferEntity;
import com.example.administrator.lmw.mine.invest.entity.PurchaseDebtEntity;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.SharedPreference;

import java.util.Map;
import java.util.Objects;



/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class InvestmentHttp {

    /**
     * 获取我的投资
     *
     * @param context
     * @param pageIndex
     * @param pageSize
     * @param proType
     * @param subType
     * @param subValue
     * @param onResponseListener
     */
    public void getInvestmentTransfer(Context context, int pageIndex, int pageSize, int proType, int subType, int subValue, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.APP_USER_PAGE_INVEST_LIST);
        para.put("pageIndex", pageIndex + "");
        para.put("pageSize", pageSize + "");
        para.put("proType", proType + "");
        para.put("subType", subType + "");
        para.put("subValue", subValue + "");
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<InvestmentTransferEntity>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<InvestmentTransferEntity> investmentTransferEntity) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(investmentTransferEntity);
                    ALLog.e(investmentTransferEntity.getCode() + "");
                    ALLog.e(investmentTransferEntity.getMsg());
                }
            }
        });
    }

    /**
     * 获取我的投资
     *
     * @param context
     * @param proType
     * @param subType
     * @param subValue
     * @param onResponseListener
     */
    public void getInvestmentCount(Context context, int proType, int subType, int subValue, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.APP_USER_DEBT_COUNT);
        para.put("proType", proType + "");
        para.put("subType", subType + "");
        para.put("subValue", subValue + "");
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<InvestmentCountEntity>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<InvestmentCountEntity> investmentTransferEntity) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(investmentTransferEntity);
                    ALLog.e(investmentTransferEntity.getCode() + "");
                    ALLog.e(investmentTransferEntity.getMsg());
                }
            }
        });
    }

    /**
     * 转让债权初始化
     *
     * @param context
     * @param discount
     * @param investId
     * @param onResponseListener
     */
    public void getPurchase(Context context, String discount, String investId, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.BORROW_DEBT_PURCHASE);
        para.put("discount", discount);
        para.put("investId", investId + "");
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<PurchaseDebtEntity>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<PurchaseDebtEntity> purchaseDebtEntity) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(purchaseDebtEntity);
                    ALLog.e(purchaseDebtEntity.getCode() + "");
                    ALLog.e(purchaseDebtEntity.getMsg());
                }
            }
        });
    }

    /**
     * 转让债权
     *
     * @param context
     * @param discount
     * @param investId

     * @param onResponseListener
     */


    public void getDebtAdd(final Context context, String discount, String investId, final OnResponseListener onResponseListener) {
//        try {
        Map<String, String> para;
//        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        final boolean isEncrypt = ConfigManager.getInstance().getEnableRsa(context);
//            if (isEncrypt) {
//                para = LmwHttp.getInstance().getBasePara(HttpUrl.BORROW_DEBT_ADD_RSA);
//                String cipherStr = AESCoder.getRandom(16);
//                StringBuilder singStringBuilder = new StringBuilder();
//                String requestStr = singStringBuilder.append("discount=")
//                        .append(discount)
//                        .append("&")
//                        .append("investId=")
//                        .append(investId)
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
        para = LmwHttp.getInstance().getBasePara(HttpUrl.BORROW_DEBT_ADD_XW);
        para.put("discount", discount);
        para.put("investId", investId + "");
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
//            }
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<ConfirmTransferEntity>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);

            }

            @Override
            public void onSuccess(BaseResult<ConfirmTransferEntity> confirmTransferEntity) {
//                    if (confirmTransferEntity.getData() != null && isEncrypt) {
//                        try {
//                            String aesKey = RSACoder.decryptByPrivateKey(
//                                    ConfigManager.getInstance().getPrivateKeyClient(context), confirmTransferEntity.getData().getCipher());
//                            String resData = AESCoder.decryptFromBase64(confirmTransferEntity.getData().getRspData(), aesKey);
//                            ALLog.e("resData" + resData);
//                            ALLog.e("aesKey" + aesKey);
//                            Gson gson = new Gson();
//                            ConfirmTransferEntity.DataBean dataBean = gson.fromJson(resData, ConfirmTransferEntity.DataBean.class);
//                            confirmTransferEntity.setData(dataBean);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
                if (onResponseListener != null && confirmTransferEntity != null)
                    onResponseListener.onSuccess(confirmTransferEntity);

            }
        });



//        } catch (Exception e) {
//
//        }
    }

    /**
     * 校验身份证后六位
     *
     * @param context
     * @param idNoSuffix
     * @param onResponseListener
     */
    public void getIdCard(Context context, String idNoSuffix, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.USER_VALID_IDNO);
        para.put("idNoSuffix", idNoSuffix);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<String>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<String> baseResponse) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse);
                    ALLog.e(baseResponse.getCode() + "");
                    ALLog.e(baseResponse.getMsg());
                }
            }
        });
    }

    /**
     * 我的投资 还款计划
     *
     * @param context
     * @param investId           标的id
     * @param onResponseListener
     */
    public void getBackPlan(Context context, String investId, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.V3_APP_REPAYMENT_PLAN);
        para.put("investId", investId);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<BackPlanEntity>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<BackPlanEntity> baseResponse) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse);
                    ALLog.e(baseResponse.getCode() + "");
                    ALLog.e(baseResponse.getMsg());
                }
            }
        });
    }

    /**
     * 我的投资 续投设置 初始化
     *
     * @param context
     * @param investId           标的id
     * @param onResponseListener
     */
    public void getContinueInitialization(Context context, String investId, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.V3_CONTINUED_INVESTMENT_INIT);
        para.put("investId", investId);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<ContinueSetEntity>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<ContinueSetEntity> baseResponse) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse);
                    ALLog.e(baseResponse.getCode() + "");
                    ALLog.e(baseResponse.getMsg());
                }
            }
        });
    }

    /**
     * 续投设置v3  变量名	含义	类型	备注
     *
     * @param context
     * @param continueBorrowType 续投产品类型（字符串，多个用,号隔开）	string
     * @param continueDeadLine   续投产品期限（字符串，多个用,号隔开）	string
     * @param continueRepayMode  回款方式（字符串，多个用,号隔开）	string
     * @param investId           投资ID	string
     * @param mode               回款续投方式	string
     * @param onResponseListener
     */
    public void getContinueInvestment(Context context, String continueBorrowType, String continueDeadLine, String continueRepayMode, String investId, String mode, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.V3_CONTINUED_INVESTMENT_SET);
        para.put("continueBorrowType", continueBorrowType);
        para.put("continueDeadLine", continueDeadLine);
        para.put("continueRepayMode", continueRepayMode);
        para.put("investId", investId);
        para.put("mode", mode);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<String>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<String> baseResponse) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse);
                    ALLog.e(baseResponse.getCode() + "");
                    ALLog.e(baseResponse.getMsg());
                }
            }
        });
    }


    /**
     * 待结奖励V3
     *
     * @param context
     * @param pageIndex
     * @param pageSize
     * @param onResponseListener
     */
    public void getTranferReward(final Context context, int pageIndex, int pageSize, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.V3_PLAT_CUSTOMER_COUPON_LIST);
        para.put("pageIndex", String.valueOf(pageIndex));
        para.put("pageSize", String.valueOf(pageSize));
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<DataTranferReward>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (onResponseListener != null)
                    onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<DataTranferReward> baseResponse) {
               if (onResponseListener != null){
                   onResponseListener.onSuccess(baseResponse);
               }
            }
        });
    }


}
