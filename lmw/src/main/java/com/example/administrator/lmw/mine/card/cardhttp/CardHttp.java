package com.example.administrator.lmw.mine.card.cardhttp;

import android.content.Context;

import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.http.CovertHttpSubscriber;
import com.example.administrator.lmw.http.HttpUrl;
import com.example.administrator.lmw.http.LmwHttp;
import com.example.administrator.lmw.http.RxManager;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.card.entity.CouponInstructeBean;
import com.example.administrator.lmw.mine.card.entity.DataCashCoupon;
import com.example.administrator.lmw.mine.card.entity.DataCashEntity;
import com.example.administrator.lmw.mine.card.entity.DataCouponCount;
import com.example.administrator.lmw.mine.card.entity.DataRedBean;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.SharedPreference;

import java.util.Map;

/**
 * 卡券网络请求业务逻辑
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/4/26 10:47
 **/
public class CardHttp {

    /**
     * 获取卡券列表数量
     *
     * @param context
     * @param token
     * @param onResponseListener
     */
    public void getCouponCount(final Context context, String token, final OnResponseListener<DataCouponCount> onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.COUPON_CASH_GIFT_COUN);
        para.put("token", token);
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<DataCouponCount>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (onResponseListener != null) {
                    onResponseListener.onFail(e);
                }
            }

            @Override
            public void onSuccess(BaseResult<DataCouponCount> result) {
                if (result != null && onResponseListener != null) {
                    onResponseListener.onSuccess(result.getData());
                }
            }
        });

    }

    /**
     * 获取红包列表
     *
     * @param context
     * @param pageIndex
     * @param pageSize
     * @param token
     * @param onResponseListener
     */
    public void getCardRed(final Context context, int pageIndex, int pageSize, QueryStatus queryStatus, String token, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.COUPON_RED_PACKET_LIST);
        para.put("pageIndex", pageIndex + "");
        para.put("pageSize", pageSize + "");
        para.put("queryStatus", queryStatus.getStatus());
        para.put("token", token);
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<DataRedBean>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (onResponseListener != null) {
                    onResponseListener.onFail(e);
                }
            }

            @Override
            public void onSuccess(BaseResult<DataRedBean> result) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(result);
                }
            }
        });

    }

    /**
     * 获取卡券
     *
     * @param couponType         卡券类型
     * @param context
     * @param pageIndex
     * @param pageSize
     * @param queryStatus        卡券状态
     * @param token
     * @param onResponseListener
     */
    public void getCash(final Context context, CouponType couponType, int pageIndex, int pageSize, QueryStatus queryStatus, String token, final OnResponseListener onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.COUPON_CASH_GIFT_LIST);
        para.put("couponType", couponType.getCouponType());
        para.put("pageIndex", pageIndex + "");
        para.put("pageSize", pageSize + "");
        para.put("queryStatus", queryStatus.getStatus());
        para.put("token", token);
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<DataCashEntity>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (onResponseListener != null)
                    onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<DataCashEntity> cashEntity) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(cashEntity);
                }
            }
        });

    }

    /**
     * 拆红包
     *
     * @param context
     * @param id
     * @param token
     * @param onResponseListener
     */
    public void getRed(final Context context, String id, String token, final OnResponseListener<BaseResult<Void>> onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.COUPON_OPEN_RED_PACKET);
        para.put("id", id);
        para.put("token", token);
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<Void>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (onResponseListener != null)
                    onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<Void> result) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(result);
                }
            }
        });

    }

    /**
     * 选择优惠券列表
     *
     * @param context
     * @param onResponseListener
     */
    public void getcashCouponList(final Context context, String busId, String busType, String investAmount, String nonceStr,
                                  final OnResponseListener<BaseResult<DataCashCoupon>> onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.QUERY_CASH_COUPON_LIST);
        para.put("busId", busId);
        para.put("busType", busType);
        para.put("investAmount", investAmount);
        para.put("nonceStr", nonceStr);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<DataCashCoupon>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (onResponseListener != null)
                    onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<DataCashCoupon> result) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(result);
                }

            }
        });

    }

    /**
     * 卡券使用说明
     *
     * @param context
     * @param onResponseListener
     */
    public void getCouponInstruction(final Context context, CouponType couponType, final OnResponseListener<String> onResponseListener) {
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.COUPON_INSTRUCTION);
        para.put("couponType", couponType.getCouponType());

        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<CouponInstructeBean>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (onResponseListener != null)
                    onResponseListener.onFail(e);
            }

            @Override
            public void onSuccess(BaseResult<CouponInstructeBean> result) {
                if (result != null && result.getData() != null && onResponseListener != null) {
                    ALLog.i("snubee" + "使用说明  " + result.getData().getInstructionUrl());
                    onResponseListener.onSuccess(result.getData().getInstructionUrl());
                }
            }
        });

    }

}
