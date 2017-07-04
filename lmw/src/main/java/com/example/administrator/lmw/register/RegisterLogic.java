package com.example.administrator.lmw.register;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResponse;
import com.example.administrator.lmw.http.HttpApiInterface;
import com.example.administrator.lmw.http.HttpSubscriber;
import com.example.administrator.lmw.http.HttpUrl;
import com.example.administrator.lmw.http.LmwHttp;
import com.example.administrator.lmw.http.RxManager;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.utils.AESCoder;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.APPUtil;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.RSACoder;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;

import java.util.Map;

/**
 * Created by lion on 2016/8/25.
 */
public class RegisterLogic {
    /**
     * 获取验证码
     * 获得手机短信 00:注册-获取短信验证码;
     * 01:重置手势密码-获取短信验证码
     * 02:找回登录密码-获取短信验证码
     * 03:交易密码设置-获取短信验证码
     * 04:绑定银行卡-获取短信验证码
     */
    public void getVerifyCode(final Context context, String account, String smsType, final OnResponseListener<BaseResponse> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.SMS_VERIFY_CODE);
        para.put("mobile", account);
        para.put("smsType", smsType);
        RxManager.toSubscrib(httpApiInterface.getVerifyCode(para), new HttpSubscriber<BaseResponse>(context) {
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
     * 检验手机号是否注册
     */
    public void checkPhone(final Context context, String phone, final OnResponseListener<BaseResponse> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.CHECK_PHONE);
        para.put("mobile", phone);
        RxManager.toSubscrib(httpApiInterface.checkPhone(para), new HttpSubscriber<BaseResponse>(context) {
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
     * 检验验证码是否有效
     */
    public void checkVerifyCode(final Context context, String phone, String vrifyCode, final OnResponseListener<BaseResponse> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.CHECK_VERIFY_CODE);
        para.put("mobile", phone);
        para.put("smsCode", vrifyCode);
        RxManager.toSubscrib(httpApiInterface.checkVerifyCode(para), new HttpSubscriber<BaseResponse>(context) {
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
     * 设置密码
     * <p>
     * isAgree	是否同意网站协议(0-同意，1-不同意)	number	[是否必输：是]，[长度：1]，[示例：1]
     * mobile	用户手机号	string	[是否必输：是]，[长度：11]，[示例：15012345678]
     * noncestr	随机字符串	string	[是否必输：是]，[长度：32]，[示例：21232f297a57a5a743894a0e4a801fc3]，[注意：与本次"获得网站验证码"发送的noncestr一致]
     * passwd	用户登录密码	string	[是否必输：是]，[长度：6-12]，[示例：abc123]
     * platfrom	平台名称(pc，android，ios)	string	[是否必输：是]，[示例：pc]
     * recommonder	推荐人	string	[是否必输：否]，[默认值：空]，[示例：123456]
     * smsCode	手机短信验证码	string	[是否必输：是]，[长度：4]，[示例：1234]
     * validCode	网站验证码	string	[是否必输：是]，[长度：4]，[示例：1x2y]
     */
    public void register(final Context context, String phone, String passward, String verifyCode, String recommonder, final OnResponseListener<BaseResponse> onResponseListener) {

        try {
            final boolean isEncrypt = ConfigManager.getInstance().getEnableRsa(context);
            Map<String, String> para;
            HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
            if (isEncrypt) {
                para = LmwHttp.getInstance().getBasePara(HttpUrl.REGISTER_RSA);
                String cipherStr = AESCoder.getRandom(16);
                StringBuilder singStringBuilder = new StringBuilder();
                singStringBuilder.append("mobile=")
                        .append(phone)
                        .append("&")
                        .append("passwd=")
                        .append(passward)
                        .append("&")
                        .append("smsCode=")
                        .append(verifyCode)
                        .append("&")
                        .append("isAgree=")
                        .append("0")
                        .append("&")
                        .append("platfrom=")
                        .append("android")
                        .append("&")
                        .append("noncestr=")
                        .append("21232f297a57a5a743894a0e4a801fc3")
                        .append("&")
                        .append("validCode=")
                        .append("8888")
                        .append("&");
                if (!TextUtils.isEmpty(recommonder)) {
                    singStringBuilder.append("recommonder=")
                            .append(recommonder)
                            .append("&");
                }
                String requestStr = singStringBuilder.append("appStore=")
                        .append(APPUtil.getChannel(context))
                        .append("&")
                        .append("origin=")
                        .append(APPUtil.getChannel(context))
                        .toString();
                String aes = AESCoder.encryptToBase64(requestStr, cipherStr);
                String rsa = RSACoder.encryptByPublicKey(
                        ConfigManager.getInstance().getPublicKeyClient(context), cipherStr);
                String aes_d = AESCoder.decryptFromBase64(aes, cipherStr);
                String rsa_d = RSACoder.decryptByPrivateKey(RSACoder.privateKey, rsa);
                para.put("cipher", rsa);
                para.put("reqData", aes);
                ALLog.e("AES加密reqData=" + aes);
                ALLog.e("RSA加密cipherStr=" + rsa);
                ALLog.e("AES解密=" + aes_d);
                ALLog.e("RSA私钥解密=" + rsa_d);
            } else {
                para = LmwHttp.getInstance().getBasePara(HttpUrl.REGISTER);
                para.put("mobile", phone);
                para.put("smsCode", verifyCode);
                para.put("passwd", passward);
                para.put("isAgree", 0 + "");
                para.put("noncestr", "21232f297a57a5a743894a0e4a801fc3");
                para.put("platfrom", "android");
                if (!TextUtils.isEmpty(recommonder))
                    para.put("recommonder", recommonder);
                para.put("validCode", "8888");
                para.put("appStore", APPUtil.getChannel(context));
                para.put("origin", APPUtil.getChannel(context));
            }


            RxManager.toSubscrib(httpApiInterface.regist(para), new HttpSubscriber<BaseResponse>(context) {
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

    public void setGesturePsw(Context context, String gesturePsw, String confirmGesturePsw, final OnResponseListener<BaseResponse> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.SET_GESTURE_PSW);
        para.put("gesturePasswd", gesturePsw);
        para.put("reGesturePasswd", confirmGesturePsw);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toSubscrib(httpApiInterface.setGestruePsw(para), new HttpSubscriber<BaseResponse>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(BaseResponse Response) {
                onResponseListener.onSuccess(Response);

            }
        });

    }


}
