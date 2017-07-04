package com.example.administrator.lmw.mine.seting;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResponse;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.entity.DepositoryEntity;
import com.example.administrator.lmw.http.CovertHttpSubscriber;
import com.example.administrator.lmw.http.HttpApiInterface;
import com.example.administrator.lmw.http.HttpSubscriber;
import com.example.administrator.lmw.http.HttpUrl;
import com.example.administrator.lmw.http.LmwHttp;
import com.example.administrator.lmw.http.RxManager;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.seting.entity.BankCardEntity;
import com.example.administrator.lmw.mine.seting.entity.BindBankBean;
import com.example.administrator.lmw.mine.seting.entity.BindBankEntity;
import com.example.administrator.lmw.mine.seting.entity.SetData;
import com.example.administrator.lmw.utils.AESCoder;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.RSACoder;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by lion on 2016/8/25.
 */
public class SetLogic {
    /**
     * 获取设置界面数据
     */
    public void getSetData(final Context context, String account, final OnResponseListener<SetData> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.GET_SET_DATA);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toSubscrib(httpApiInterface.getSetData(para), new HttpSubscriber<SetData>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(SetData baseResponse) {
                if (baseResponse != null && !Singlton.getInstance(PopWindowManager.class).
                        showStopDialogShow(context, baseResponse.getCode(), baseResponse.msg) && onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse);
                }
            }
        });

    }

    /**
     * 获取银行卡列表信息
     */
    public void getBankList(final Context context, final OnResponseListener<BankCardEntity> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.GET_BANK_LIST);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toSubscrib(httpApiInterface.getBankInfoList(para), new HttpSubscriber<BankCardEntity>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(BankCardEntity baseResponse) {
                if (baseResponse != null && !Singlton.getInstance(PopWindowManager.class).
                        showStopDialogShow(context, baseResponse.getCode(), baseResponse.msg) && onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse);
                }
            }
        });

    }

    /**
     * 获取银行卡列表信息
     */
    public void bindBankCard(final Context context, String bankCardNo, String bankCode, String bankMobile,
                             String customerSurname, String idNo, final OnResponseListener<BindBankEntity> onResponseListener) {
        try {
            Map<String, String> para;
            HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
            final boolean isEncrypt = ConfigManager.getInstance().getEnableRsa(context);
            if (isEncrypt) {
                para = LmwHttp.getInstance().getBasePara(HttpUrl.BIND_BANK_RSA);
                String cipherStr = AESCoder.getRandom(16);
                StringBuilder singStringBuilder = new StringBuilder();
                String requestStr = singStringBuilder.append("token=")
                        .append(SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""))
                        .append("&")
                        .append("bankCardNo=")
                        .append(bankCardNo)
                        .append("&")
                        .append("bankCode=")
                        .append(bankCode)
                        .append("&")
                        .append("bankMobile=")
                        .append(bankMobile)
                        .append("&")
                        .append("customerSurname=")
                        .append(customerSurname)
                        .append("&")
                        .append("idNo=")
                        .append(idNo)
                        .toString();

                String aes = AESCoder.encryptToBase64(requestStr, cipherStr);
                String rsa = RSACoder.encryptByPublicKey(
                        ConfigManager.getInstance().getPublicKeyClient(context), cipherStr);
                ALLog.e("AES加密reqData=" + aes);
                ALLog.e("RSA加密cipherStr=" + rsa);
                para.put("cipher", rsa);
                para.put("reqData", aes);
            } else {
                para = LmwHttp.getInstance().getBasePara(HttpUrl.BIND_BANK);
                para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
                para.put("bankCardNo", bankCardNo);
                para.put("bankCode", bankCode);
                para.put("bankMobile", bankMobile);
                para.put("customerSurname", customerSurname);
                para.put("idNo", idNo);
            }


            RxManager.toSubscrib(httpApiInterface.bindBankCard(para), new HttpSubscriber<BindBankEntity>(context) {
                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    onResponseListener.onFail(e);
                }

                @Override
                public void onNext(BindBankEntity baseResponse) {
                    if (baseResponse != null && !Singlton.getInstance(PopWindowManager.class).
                            showStopDialogShow(context, baseResponse.getCode(), baseResponse.msg) && onResponseListener != null) {
                        try {
                            BindBankBean bindBankBean = baseResponse.getData();
                            if (bindBankBean != null && isEncrypt) {
                                String aesKey = RSACoder.decryptByPrivateKey(
                                        ConfigManager.getInstance().getPrivateKeyClient(context), bindBankBean.getCipher());
                                String resData = AESCoder.decryptFromBase64(bindBankBean.getRspData(), aesKey);
                                ALLog.e("resData" + resData);
                                ALLog.e("aesKey" + aesKey);
                                Gson gson = new Gson();
                                BindBankBean bindBankBeanRsp = gson.fromJson(resData, BindBankBean.class);
                                baseResponse.setData(bindBankBeanRsp);
                                ALLog.e("bindBankBeanRsp.getOrderNo()" + bindBankBeanRsp.getOrderNo());
                            }
                            onResponseListener.onSuccess(baseResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑定银行卡
     */
    public void bindBankCardTwo(final Context context, String orderNo, String smsCode, final OnResponseListener<BaseResponse> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.BIND_BANK_TWO);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        para.put("orderNo", orderNo);
        para.put("smsCode", smsCode);
        RxManager.toSubscrib(httpApiInterface.bindBankCardTwo(para), new HttpSubscriber<BaseResponse>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(BaseResponse baseResponse) {
                if (baseResponse != null && !Singlton.getInstance(PopWindowManager.class).
                        showStopDialogShow(context, baseResponse.getCode(), baseResponse.msg) && onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse);
                }
            }
        });

    }

    /**
     * 设置交易密码
     */
    public void setTradePsw(final Context context, String reTradingPasswd,
                            final OnResponseListener<BaseResponse> onResponseListener) {
        try {
            Map<String, String> para;
            HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
            final boolean isEncrypt = ConfigManager.getInstance().getEnableRsa(context);
            if (isEncrypt) {
                para = LmwHttp.getInstance().getBasePara(HttpUrl.SET_TRADE_PSW_RSA);
                String cipherStr = AESCoder.getRandom(16);
                StringBuilder singStringBuilder = new StringBuilder();
                String requestStr = singStringBuilder.append("token=")
                        .append(SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""))
                        .append("&")
                        .append("tradingPasswd=")
                        .append(reTradingPasswd)
                        .toString();

                String aes = AESCoder.encryptToBase64(requestStr, cipherStr);
                String rsa = RSACoder.encryptByPublicKey(
                        ConfigManager.getInstance().getPublicKeyClient(context), cipherStr);
                ALLog.e("AES加密reqData=" + aes);
                ALLog.e("RSA加密cipherStr=" + rsa);
                para.put("cipher", rsa);
                para.put("reqData", aes);
            } else {
                para = LmwHttp.getInstance().getBasePara(HttpUrl.SET_TRADE_PSW);
                para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
                para.put("tradingPasswd", reTradingPasswd);
            }
            RxManager.toSubscrib(httpApiInterface.setTradePsw(para), new HttpSubscriber<BaseResponse>(context) {
                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    onResponseListener.onFail(e);
                }

                @Override
                public void onNext(BaseResponse baseResponse) {
                    if (baseResponse != null && !Singlton.getInstance(PopWindowManager.class).
                            showStopDialogShow(context, baseResponse.getCode(), baseResponse.msg) && onResponseListener != null) {
                        onResponseListener.onSuccess(baseResponse);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置无效老用户交易密码
     */
    public void setOldUserTradePsw(final Context context, String reTradingPasswd, String smsCode,
                                   final OnResponseListener<BaseResponse> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.SET_OLD_USER_TRADE_PSW);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        para.put("smsCode", smsCode);
        para.put("tradingPasswd", reTradingPasswd);
        RxManager.toSubscrib(httpApiInterface.setTradePsw(para), new HttpSubscriber<BaseResponse>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(BaseResponse baseResponse) {
                if (baseResponse != null && !Singlton.getInstance(PopWindowManager.class).
                        showStopDialogShow(context, baseResponse.getCode(), baseResponse.msg) && onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse);
                }
            }
        });

    }

    /**
     * 修改交易密码
     */
    public void motifyTradePsw(final Context context, String oldTradingPasswd, String newTradingPasswd,
                               final OnResponseListener<BaseResponse> onResponseListener) {
        try {
            Map<String, String> para;
            HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
            final boolean isEncrypt = ConfigManager.getInstance().getEnableRsa(context);
            if (isEncrypt) {
                para = LmwHttp.getInstance().getBasePara(HttpUrl.MOTIFY_TRADE_PSW_RSA);
                String cipherStr = AESCoder.getRandom(16);
                StringBuilder singStringBuilder = new StringBuilder();
                String requestStr = singStringBuilder.append("token=")
                        .append(SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""))
                        .append("&")
                        .append("newTradingPasswd=")
                        .append(newTradingPasswd)
                        .append("&")
                        .append("oldTradingPasswd=")
                        .append(oldTradingPasswd)
                        .toString();
                String aes = AESCoder.encryptToBase64(requestStr, cipherStr);
                String rsa = RSACoder.encryptByPublicKey(
                        ConfigManager.getInstance().getPublicKeyClient(context), cipherStr);
                ALLog.e("AES加密reqData=" + aes);
                ALLog.e("RSA加密cipherStr=" + rsa);
                para.put("cipher", rsa);
                para.put("reqData", aes);
            } else {
                para = LmwHttp.getInstance().getBasePara(HttpUrl.MOTIFY_TRADE_PSW);
                para.put("newTradingPasswd", newTradingPasswd);
                para.put("oldTradingPasswd", oldTradingPasswd);
                para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
            }

            RxManager.toSubscrib(httpApiInterface.motifyTradePsw(para), new HttpSubscriber<BaseResponse>(context) {
                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    onResponseListener.onFail(e);
                }

                @Override
                public void onNext(BaseResponse baseResponse) {
                    if (baseResponse != null && !Singlton.getInstance(PopWindowManager.class).
                            showStopDialogShow(context, baseResponse.getCode(), baseResponse.msg) && onResponseListener != null) {
                        onResponseListener.onSuccess(baseResponse);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重置交易密码验证
     */
    public void resetTradePswVerify(final Context context, String mobile, String smsCode,
                                    final OnResponseListener<BaseResponse> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.RESET_TRADE_PSW_VERIFY);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        // para.put("credentialsCode",credentialsCode);
        para.put("mobile", mobile);
        para.put("smsCode", smsCode);
        RxManager.toSubscrib(httpApiInterface.resetTradePswVerify(para), new HttpSubscriber<BaseResponse>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(BaseResponse baseResponse) {
                if (baseResponse != null && !Singlton.getInstance(PopWindowManager.class).
                        showStopDialogShow(context, baseResponse.getCode(), baseResponse.msg) && onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse);
                }
            }
        });

    }

    /**
     * 重置交易密码
     */
    public void resetTradePsw(final Context context, String tradingPasswd, String reTradingPasswd,
                              String vrifyCode, final OnResponseListener<BaseResponse> onResponseListener) {
        try {
            HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
            final boolean isEncrypt = ConfigManager.getInstance().getEnableRsa(context);
            Map<String, String> para;
            if (isEncrypt) {
                para = LmwHttp.getInstance().getBasePara(HttpUrl.RESET_TRADE_PSW_RSA);
                String cipherStr = AESCoder.getRandom(16);
                StringBuilder singStringBuilder = new StringBuilder();
                String requestStr = singStringBuilder.append("token=")
                        .append(SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""))
                        .append("&")
                        .append("tradingPasswd=")
                        .append(tradingPasswd)
                        .append("&")
                        .append("reTradingPasswd=")
                        .append(reTradingPasswd)
                        .append("&")
                        .append("smsCode=")
                        .append(vrifyCode)
                        .toString();
                String aes = AESCoder.encryptToBase64(requestStr, cipherStr);
                String rsa = RSACoder.encryptByPublicKey(
                        ConfigManager.getInstance().getPublicKeyClient(context), cipherStr);
                ALLog.e("AES加密reqData=" + aes);
                ALLog.e("RSA加密cipherStr=" + rsa);
                para.put("cipher", rsa);
                para.put("reqData", aes);
            } else {
                para = LmwHttp.getInstance().getBasePara(HttpUrl.RESET_TRADE_PSW);
                para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
                para.put("tradingPasswd", tradingPasswd);
                para.put("reTradingPasswd", reTradingPasswd);
                para.put("smsCode", vrifyCode);
            }


            RxManager.toSubscrib(httpApiInterface.resetTradePsw(para), new HttpSubscriber<BaseResponse>(context) {
                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    onResponseListener.onFail(e);
                }

                @Override
                public void onNext(BaseResponse baseResponse) {
                    if (baseResponse != null && !Singlton.getInstance(PopWindowManager.class).
                            showStopDialogShow(context, baseResponse.getCode(), baseResponse.msg) && onResponseListener != null) {
                        onResponseListener.onSuccess(baseResponse);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 验证旧的交易密码
     */
    public void verifyOldTradePsw(final Context context, String oldTradePasswd,
                                  final OnResponseListener<BaseResponse> onResponseListener) {
        try {
            Map<String, String> para;
            HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
            final boolean isEncrypt = ConfigManager.getInstance().getEnableRsa(context);
            if (isEncrypt) {
                para = LmwHttp.getInstance().getBasePara(HttpUrl.VERIFY_OLD_TRADE_PSW_RSA);
                String cipherStr = AESCoder.getRandom(16);
                StringBuilder singStringBuilder = new StringBuilder();
                String requestStr = singStringBuilder.append("token=")
                        .append(SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""))
                        .append("&")
                        .append("tradingPasswd=")
                        .append(oldTradePasswd)
                        .toString();
                String aes = AESCoder.encryptToBase64(requestStr, cipherStr);
                String rsa = RSACoder.encryptByPublicKey(
                        ConfigManager.getInstance().getPublicKeyClient(context), cipherStr);
                ALLog.e("AES加密reqData=" + aes);
                ALLog.e("RSA加密cipherStr=" + rsa);
                para.put("cipher", rsa);
                para.put("reqData", aes);
            } else {
                para = LmwHttp.getInstance().getBasePara(HttpUrl.VERIFY_OLD_TRADE_PSW);
                para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
                para.put("tradingPasswd", oldTradePasswd);
            }

            RxManager.toSubscrib(httpApiInterface.verifyOldTradePsw(para), new HttpSubscriber<BaseResponse>(context) {
                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    onResponseListener.onFail(e);
                }

                @Override
                public void onNext(BaseResponse baseResponse) {
                    if (baseResponse != null && !Singlton.getInstance(PopWindowManager.class).
                            showStopDialogShow(context, baseResponse.getCode(), baseResponse.msg) && onResponseListener != null) {
                        onResponseListener.onSuccess(baseResponse);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 验证旧的登录密码
     */
    public void verifyOldLoginPsw(final Context context, String oldLoginPasswd,
                                  final OnResponseListener<BaseResponse> onResponseListener) {
        try {

            HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
            Map<String, String> para;
            final boolean isEncrypt = ConfigManager.getInstance().getEnableRsa(context);
            if (isEncrypt) {
                para = LmwHttp.getInstance().getBasePara(HttpUrl.VERIFY_OLD_LOGIN_PSW_RSA);
                String cipherStr = AESCoder.getRandom(16);
                StringBuilder singStringBuilder = new StringBuilder();
                String requestStr = singStringBuilder.append("token=")
                        .append(SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""))
                        .append("&")
                        .append("passwd=")
                        .append(oldLoginPasswd)
                        .toString();
                String aes = AESCoder.encryptToBase64(requestStr, cipherStr);
                String rsa = RSACoder.encryptByPublicKey(
                        ConfigManager.getInstance().getPublicKeyClient(context), cipherStr);
                ALLog.e("AES加密reqData=" + aes);
                ALLog.e("RSA加密cipherStr=" + rsa);
                para.put("cipher", rsa);
                para.put("reqData", aes);
            } else {
                para = LmwHttp.getInstance().getBasePara(HttpUrl.VERIFY_OLD_LOGIN_PSW);
                para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
                para.put("passwd", oldLoginPasswd);
            }


            RxManager.toSubscrib(httpApiInterface.verifyOldLoginPsw(para), new HttpSubscriber<BaseResponse>(context) {
                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    onResponseListener.onFail(e);
                }

                @Override
                public void onNext(BaseResponse baseResponse) {
                    if (baseResponse != null && !Singlton.getInstance(PopWindowManager.class).
                            showStopDialogShow(context, baseResponse.getCode(), baseResponse.msg) && onResponseListener != null) {
                        onResponseListener.onSuccess(baseResponse);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改登录密码
     */
    public void motifyLoginPsw(final Context context, String oldLoginPasswd, String newPsw,
                               final OnResponseListener<BaseResponse> onResponseListener) {

        try {
            Map<String, String> para;
            HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
            final boolean isEncrypt = ConfigManager.getInstance().getEnableRsa(context);
            if (isEncrypt) {
                para = LmwHttp.getInstance().getBasePara(HttpUrl.MOTITY_LOGIN_PASSWD_RSA);
                String cipherStr = AESCoder.getRandom(16);
                StringBuilder singStringBuilder = new StringBuilder();
                String requestStr = singStringBuilder.append("token=")
                        .append(SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""))
                        .append("&")
                        .append("oldPasswd=")
                        .append(oldLoginPasswd)
                        .append("&")
                        .append("newPasswd=")
                        .append(newPsw)
                        .toString();
                String aes = AESCoder.encryptToBase64(requestStr, cipherStr);
                String rsa = RSACoder.encryptByPublicKey(
                        ConfigManager.getInstance().getPublicKeyClient(context), cipherStr);
                ALLog.e("AES加密reqData=" + aes);
                ALLog.e("RSA加密cipherStr=" + rsa);
                para.put("cipher", rsa);
                para.put("reqData", aes);
            } else {
                para = LmwHttp.getInstance().getBasePara(HttpUrl.MOTITY_LOGIN_PASSWD);
                para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
                para.put("oldPasswd", oldLoginPasswd);
                para.put("newPasswd", newPsw);
            }

            RxManager.toSubscrib(httpApiInterface.motifyLoginPsw(para), new HttpSubscriber<BaseResponse>(context) {
                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    onResponseListener.onFail(e);
                }

                @Override
                public void onNext(BaseResponse baseResponse) {
                    if (baseResponse != null && !Singlton.getInstance(PopWindowManager.class).
                            showStopDialogShow(context, baseResponse.getCode(), baseResponse.msg) && onResponseListener != null) {
                        onResponseListener.onSuccess(baseResponse);
                    }
                }
            });
        } catch (Exception e) {

        }
    }

    /**
     * 验证身份证
     */
    public void verifyLicence(final Context context, String licence,
                              final OnResponseListener<BaseResult> onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.VERIFY_LICENCE);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        para.put("idNoSuffix", licence);

        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<String>>(context, "verifyLicence_01") {

            @Override
            public void onSuccess(BaseResult baseResponse) {
                if (baseResponse != null && onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse);
                }
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }
        });

    }

    /**
     * 退出登录
     */
    public void logout(final Context context,
                       final OnResponseListener<BaseResponse> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.USER_LOGOUT);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toSubscrib(httpApiInterface.logout(para), new HttpSubscriber<BaseResponse>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(BaseResponse baseResponse) {
                if (baseResponse != null && !Singlton.getInstance(PopWindowManager.class).
                        showStopDialogShow(context, baseResponse.getCode(), baseResponse.msg) && onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse);
                }
            }
        });

    }

    /**
     * 解绑
     *
     * @param context
     * @param onResponseListener
     */
    public void doUnbundle(final Context context, final String criditRedirectUrl, final OnResponseListener<DepositoryEntity> onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.UNBIND_BANKCARD);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        para.put("withdrawRedirectUrl", "www.baidu.com");
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<DepositoryEntity>>(context, "doUnbundle") {

                    @Override
                    public void onSuccess(BaseResult<DepositoryEntity> baseResponse) {
                        ALLog.e("===============存管baseResponse.getCode()" + baseResponse.getCode());
                        ALLog.e("===============存管baseResponse.getmsg()" + baseResponse.getMsg());
                        if (baseResponse != null && onResponseListener != null&&TextUtils.equals("0",baseResponse.getCode())) {
                            onResponseListener.onSuccess(baseResponse.getData());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (onResponseListener != null)
                            onResponseListener.onFail(e);

                    }
                }


        );


    }


}
