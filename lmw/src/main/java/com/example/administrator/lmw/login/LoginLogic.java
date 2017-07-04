package com.example.administrator.lmw.login;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResponse;
import com.example.administrator.lmw.http.CovertHttpSubscriber;
import com.example.administrator.lmw.http.HttpApiInterface;
import com.example.administrator.lmw.http.HttpSubscriber;
import com.example.administrator.lmw.http.HttpUrl;
import com.example.administrator.lmw.http.LmwHttp;
import com.example.administrator.lmw.http.RxManager;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.entity.LoginBean;
import com.example.administrator.lmw.login.entity.LoginEntity;
import com.example.administrator.lmw.login.entity.MineData;
import com.example.administrator.lmw.login.entity.TokenLoginEntity;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.utils.AESCoder;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.APPUtil;
import com.example.administrator.lmw.utils.DeviceInfoUtils;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.RSACoder;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.google.gson.Gson;

import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lion on 2016/8/25.
 */
public class LoginLogic {
    /**
     * 登录
     */
    public void login(final Context context, String account, String psw, final OnResponseListener<LoginEntity> onResponseListener) {

        try {
            Map<String, String> para;
            final boolean isEncrypt = ConfigManager.getInstance().getEnableRsa(context);
            String JpushRegId = SharedPreference.getInstance().getString(context, PreferenceCongfig.JPUSH_REGESTER_ID, "");
            if (TextUtils.isEmpty(JpushRegId))
                JpushRegId = JPushInterface.getRegistrationID(context);
            HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
            if (isEncrypt) {
                para = LmwHttp.getInstance().getBasePara(HttpUrl.LOGIN_RSA);
                String cipherStr = AESCoder.getRandom(16);
                StringBuilder singStringBuilder = new StringBuilder();
                String requestStr = singStringBuilder.append("mobile=")
                        .append(account)
                        .append("&")
                        .append("passwd=")
                        .append(psw)
                        .append("&")
                        .append("appStore=")
                        .append(APPUtil.getChannel(context))
                        .append("&")
                        .append("jpushRegId=")
                        .append(JpushRegId)
                        .append("&")
                        .append("deviceSn=")
                        .append(DeviceInfoUtils.getDeviceUUID(context))
                        .append("&")
                        .append("noncestr=")
                        .append("21232f297a57a5a743894a0e4a801fc3")
                        .append("&")
                        .append("validCode=")
                        .append("8888")
                        .toString();

                String aes = AESCoder.encryptToBase64(requestStr, cipherStr);
                String rsa = RSACoder.encryptByPublicKey(
                        ConfigManager.getInstance().getPublicKeyClient(context), cipherStr);
                ALLog.e("AES加密reqData=" + aes);
                ALLog.e("RSA加密cipherStr=" + rsa);
                para.put("cipher", rsa);
                para.put("reqData", aes);
            } else {
                para = LmwHttp.getInstance().getBasePara(HttpUrl.LOGIN);
                para.put("mobile", account);
                para.put("passwd", psw);
                para.put("appStore", APPUtil.getChannel(context));
                para.put("jpushRegId", JpushRegId);
                para.put("deviceSn", DeviceInfoUtils.getDeviceUUID(context));
                para.put("noncestr", "21232f297a57a5a743894a0e4a801fc3");
                para.put("validCode", "8888");
            }
            RxManager.toPostSubscrib(/*httpApiInterface.login(*/para, new CovertHttpSubscriber<LoginEntity>(context) {
                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    onResponseListener.onFail(e);
                }

                @Override
                public void onSuccess(LoginEntity baseResponse) {
                    ALLog.e("baseResponse+code" + baseResponse.getCode() + ",msg+" + baseResponse.msg);
                    if (baseResponse != null && !Singlton.getInstance(PopWindowManager.class).
                            showStopDialogShow(context, baseResponse.getCode(), baseResponse.msg) && onResponseListener != null) {
                        try {
                            LoginBean loginBean = baseResponse.getData();
                            if (loginBean != null && isEncrypt) {
                                String aesKey = RSACoder.decryptByPrivateKey(
                                        ConfigManager.getInstance().getPrivateKeyClient(context), loginBean.getCipher());
                                String resData = AESCoder.decryptFromBase64(loginBean.getRspData(), aesKey);
                                ALLog.e("resData" + resData);
                                ALLog.e("aesKey" + aesKey);
                                Gson gson = new Gson();
                                LoginBean loginBeanRsp = gson.fromJson(resData, LoginBean.class);
                                baseResponse.setData(loginBeanRsp);
                                ALLog.e("loginBeanRsp.getToken()" + loginBeanRsp.getToken());
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
            ALLog.e(e.getMessage() + "==========================");
        }


    }

    /**
     * 登录
     */
    public void getUserInfo(final Context context, String token, final OnResponseListener<UserInfo> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.USER_INFO);
        para.put("token", token);
        RxManager.toSubscrib(httpApiInterface.getUserInfo(para), new HttpSubscriber<UserInfo>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(UserInfo response) {
                if (response != null && !Singlton.getInstance(PopWindowManager.class).
                        showStopDialogShow(context, response.getCode(), response.msg) && onResponseListener != null) {
                    onResponseListener.onSuccess(response);
                }
            }
        });

    }

    /**
     * 獲取我的頁面數據
     */
    public void getMineDataInfo(final Context context, String token, final OnResponseListener<MineData> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.MINE_DATA_INFO);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toSubscrib(httpApiInterface.getMineDataInfo(para), new HttpSubscriber<MineData>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(MineData response) {
                if (response != null && !Singlton.getInstance(PopWindowManager.class).
                        showStopDialogShow(context, response.getCode(), response.msg) && onResponseListener != null) {
                    onResponseListener.onSuccess(response);
                }
            }
        });

    }

    /**
     * 重置密码
     */
    public void resetLoginPsw(final Context context, String psw, String confirmPsw, String verifyCode, String phone, final OnResponseListener<BaseResponse> onResponseListener) {
        try {
            Map<String, String> para;
            HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
            final boolean isEncrypt = ConfigManager.getInstance().getEnableRsa(context);
            if (isEncrypt) {
                para = LmwHttp.getInstance().getBasePara(HttpUrl.RESET_PASSWARD_RSA);
                String cipherStr = AESCoder.getRandom(16);
                StringBuilder singStringBuilder = new StringBuilder();
                String requestStr = singStringBuilder.append("passwd=")
                        .append(psw)
                        .append("&")
                        .append("confirmPasswd=")
                        .append(confirmPsw)
                        .append("&")
                        .append("smsCode=")
                        .append(verifyCode)
                        .append("&")
                        .append("mobile=")
                        .append(phone)
                        .toString();

                String aes = AESCoder.encryptToBase64(requestStr, cipherStr);
                String rsa = RSACoder.encryptByPublicKey(
                        ConfigManager.getInstance().getPublicKeyClient(context), cipherStr);
                ALLog.e("AES加密reqData=" + aes);
                ALLog.e("RSA加密cipherStr=" + rsa);
                para.put("cipher", rsa);
                para.put("reqData", aes);
            } else {
                para = LmwHttp.getInstance().getBasePara(HttpUrl.RESET_PASSWARD);
                para.put("passwd", psw);
                para.put("confirmPasswd", confirmPsw);
                para.put("smsCode", verifyCode);
                para.put("mobile", phone);
            }


            RxManager.toSubscrib(httpApiInterface.resetLoginPsw(para), new HttpSubscriber<BaseResponse>(context) {
                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    onResponseListener.onFail(e);
                }

                @Override
                public void onNext(BaseResponse response) {
                    if (response != null && !Singlton.getInstance(PopWindowManager.class).
                            showStopDialogShow(context, response.getCode(), response.msg) && onResponseListener != null) {
                        onResponseListener.onSuccess(response);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 校验图形验证码
     */
    public void checkPictureVerifyCode(final Context context, String verifyCode, String noncestr, final OnResponseListener<BaseResponse> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.CHECK_PICTURE_VERIFY);
        para.put("code", verifyCode);
        para.put("noncestr", noncestr);
        RxManager.toSubscrib(httpApiInterface.checkPictureVerifyCode(para), new HttpSubscriber<BaseResponse>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(BaseResponse response) {
                if (response != null && !Singlton.getInstance(PopWindowManager.class).
                        showStopDialogShow(context, response.getCode(), response.msg) && onResponseListener != null) {
                    onResponseListener.onSuccess(response);
                }
            }
        });
    }

    /**
     * 校验token
     */
    public void checkToken(final Context context, final OnResponseListener<TokenLoginEntity> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.CHECK_TOKEN);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toSubscrib(httpApiInterface.checkToken(para), new HttpSubscriber<TokenLoginEntity>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(TokenLoginEntity response) {
                if (response != null && !Singlton.getInstance(PopWindowManager.class).
                        showStopDialogShow(context, response.getCode(), response.msg) && onResponseListener != null) {
                    onResponseListener.onSuccess(response);
                }
            }
        });

    }

}
