package com.example.administrator.lmw.select;

import android.content.Context;

import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResponse;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.http.CovertHttpSubscriber;
import com.example.administrator.lmw.http.HttpApiInterface;
import com.example.administrator.lmw.http.HttpSubscriber;
import com.example.administrator.lmw.http.HttpUrl;
import com.example.administrator.lmw.http.LmwHttp;
import com.example.administrator.lmw.http.RxManager;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.select.entity.ActivityOrAnoucemnetEntity;
import com.example.administrator.lmw.select.entity.AnnouncementEntity;
import com.example.administrator.lmw.select.entity.BannerEntity;
import com.example.administrator.lmw.select.entity.DataSelectlist;
import com.example.administrator.lmw.select.entity.NoticeEntity;
import com.example.administrator.lmw.select.entity.StatisticsEntity;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;

import java.util.Map;


/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class BannerHttp {

    /**
     * 获取banner条
     */
    public void getBanner(Context context, int showPlace, final OnResponseListener onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.BANNER_LIST);
        para.put("showPlace", "7");// (1:PC首页,3:微信首页,7:APP首页)
        RxManager.toSubscrib(httpApiInterface.getBanner(para), new HttpSubscriber<BannerEntity>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(BannerEntity baseResponse) {
                onResponseListener.onSuccess(baseResponse);

            }
        });


    }

    /**
     * 获取首页公告
     */
    public void getAnnouncement(final Context context, int pageIndex, int pageSize, final OnResponseListener onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.HOMEPAGE_NITICE);
        para.put("pageIndex", pageIndex + "");
        para.put("pageSize", pageSize + "");
        RxManager.toSubscrib(httpApiInterface.getAnnouncement(para), new HttpSubscriber<AnnouncementEntity>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(AnnouncementEntity announcementEntity) {
                if (announcementEntity != null && !Singlton.getInstance(PopWindowManager.class).
                        showStopDialogShow(context, announcementEntity.getCode() + "", announcementEntity.getMsg()) && onResponseListener != null) {
                    onResponseListener.onSuccess(announcementEntity);
                }
            }
        });
    }

    /**
     * 获取首页数据统计
     */
    public void getStatistics(final Context context, final OnResponseListener onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.HOMEPAGE_GETINDEXCOUNT);
//        RxManager.toSubscrib(httpApiInterface.getStatistics(para), new HttpSubscriber<StatisticsEntity>(context) {
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//                onResponseListener.onFail(e);
//            }
//
//            @Override
//            public void onNext(StatisticsEntity statisticsEntity) {
//                ALLog.e("statisticsEntity+CODE" + statisticsEntity.getCode() + "MSG" + statisticsEntity.msg);
//                if (statisticsEntity != null && !Singlton.getInstance(PopWindowManager.class).
//                        showStopDialogShow(context, statisticsEntity.getCode(), statisticsEntity.msg) && onResponseListener != null) {
//                    onResponseListener.onSuccess(statisticsEntity);
//                }
//            }
//        });
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<StatisticsEntity>(context) {

            @Override
            public void onSuccess(StatisticsEntity statisticsEntity) {
                ALLog.e("statisticsEntity+CODE" + statisticsEntity.getCode() + "MSG" + statisticsEntity.msg);
                if (statisticsEntity != null && !Singlton.getInstance(PopWindowManager.class).
                        showStopDialogShow(context, statisticsEntity.getCode(), statisticsEntity.msg) && onResponseListener != null) {
                    onResponseListener.onSuccess(statisticsEntity);
                }
            }
        });
    }


    /**
     * 获取首页列表
     */
    public void getRecommend(final Context context, final OnResponseListener onResponseListener) {
//        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
//        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.APP_BORROW_RECOMMEND);
//        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
//        RxManager.toSubscrib(httpApiInterface.getRecommend(para), new HttpSubscriber<SelectlistEnitity>(context) {
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//                onResponseListener.onFail(e);
//
//            }
//
//            @Override
//            public void onNext(SelectlistEnitity selectlistEnitity) {
//                if (selectlistEnitity != null && !Singlton.getInstance(PopWindowManager.class).
//                        showStopDialogShow(context, selectlistEnitity.getCode() + "", selectlistEnitity.msg) && onResponseListener != null) {
//                    onResponseListener.onSuccess(selectlistEnitity);
//                }
//            }
//        });
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.APP_BORROW_RECOMMEND);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toPostSubscrib(para, new CovertHttpSubscriber<BaseResult<DataSelectlist>>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (onResponseListener != null) {
                    onResponseListener.onFail(e);
                }

            }

            @Override
            public void onSuccess(BaseResult<DataSelectlist> result) {
                if (onResponseListener != null) {
                    onResponseListener.onSuccess(result);
                }
            }
        });


    }

    /**
     * 获取消息列表
     */
    public void getNotice(final Context context, int pageIndex, int pageSize, final OnResponseListener onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.MESSAGE_MY_LIST);
        para.put("pageIndex", pageIndex + "");
        para.put("pageSize", pageSize + "");
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toSubscrib(httpApiInterface.getNotice(para), new HttpSubscriber<NoticeEntity>(context) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(NoticeEntity noticeEntity) {
                if (noticeEntity != null && !Singlton.getInstance(PopWindowManager.class).
                        showStopDialogShow(context, noticeEntity.getCode() + "", noticeEntity.getMsg()) && onResponseListener != null) {
                    onResponseListener.onSuccess(noticeEntity);
                }
            }
        });

    }

    /**
     * 获取读取消息
     */
    public void getMessage(final Context context, int readAll, String statusList, String token, final OnResponseListener onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.MESSAGE_UPDATE_STATUS);
        para.put("readAll", readAll + "");
        para.put("statusList", statusList + "`1");
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toSubscrib(httpApiInterface.getMessage(para), new HttpSubscriber<BaseResponse>(context) {
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
     * 获取读取消息
     */
    public void getAnouncementOrActivityPopinfo(final Context context, final OnResponseListener<ActivityOrAnoucemnetEntity> onResponseListener) {
        HttpApiInterface httpApiInterface = LmwHttp.getInstance().createHttpInterface();
        Map<String, String> para = LmwHttp.getInstance().getBasePara(HttpUrl.GET_ANOUNCEMENT_OR_ACTIVITY_POP_INFO);
        para.put("token", SharedPreference.getInstance().getString(context, PreferenceCongfig.APP_TOKEN, ""));
        RxManager.toSubscrib(httpApiInterface.getAnouncementOrActivityPopinfo(para), new HttpSubscriber<ActivityOrAnoucemnetEntity>(context) {
            @Override
            public void onError(Throwable e) {

                super.onError(e);
                onResponseListener.onFail(e);
            }

            @Override
            public void onNext(ActivityOrAnoucemnetEntity baseResponse) {
                if (baseResponse != null && !Singlton.getInstance(PopWindowManager.class).
                        showStopDialogShow(context, baseResponse.getCode(), baseResponse.msg) && onResponseListener != null) {
                    onResponseListener.onSuccess(baseResponse);
                }
            }
        });

    }


}
