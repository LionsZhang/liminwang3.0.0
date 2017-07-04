package com.example.administrator.lmw.select;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.LmwApp;
import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.config.ConfigLogic;
import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.AppEntity;
import com.example.administrator.lmw.entity.AppInfo;
import com.example.administrator.lmw.entity.BaseEvent;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.entity.CommonInfo;
import com.example.administrator.lmw.entity.DataDragFloatBean;
import com.example.administrator.lmw.entity.FreshFloatIconEvent;
import com.example.administrator.lmw.entity.FreshMessageEvent;
import com.example.administrator.lmw.finance.activity.DetailActivity;
import com.example.administrator.lmw.finance.activity.DetailProductActivity;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.LoginLogic;
import com.example.administrator.lmw.login.entity.LoginBean;
import com.example.administrator.lmw.login.entity.LoginEntity;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.select.adapter.SelectFragmentAdapter;
import com.example.administrator.lmw.select.entity.ActivityOrAnoucemnetEntity;
import com.example.administrator.lmw.select.entity.ActivityOrAnoucemnetInfo;
import com.example.administrator.lmw.select.entity.AnnouncementDatasBean;
import com.example.administrator.lmw.select.entity.AnnouncementEntity;
import com.example.administrator.lmw.select.entity.BannerDataBean;
import com.example.administrator.lmw.select.entity.BannerEntity;
import com.example.administrator.lmw.select.entity.DataSelectlist;
import com.example.administrator.lmw.select.entity.SelectlistDataBean;
import com.example.administrator.lmw.select.entity.StatisticsEntity;
import com.example.administrator.lmw.select.entity.utils.MarqueeTextView;
import com.example.administrator.lmw.select.utils.NumberWith;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.APPUtil;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.BigDecemalCalculateUtil;
import com.example.administrator.lmw.utils.DateUtil;
import com.example.administrator.lmw.utils.NumberParserUtil;
import com.example.administrator.lmw.utils.PicassoManager;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ToastUtil;
import com.example.administrator.lmw.utils.UpdateVersionTask;
import com.example.administrator.lmw.view.DragView;
import com.example.administrator.lmw.view.SelectGuideDialog;
import com.example.administrator.lmw.view.ShowActiveDialog;
import com.example.administrator.lmw.view.ShowAnnouncementDialog;
import com.example.administrator.lmw.view.XListView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 首页
 * <p/>
 * Created by Administrator on 2016/8/22.
 */
public class SelectFragment extends BaseFragment implements XListView.IXListViewListener {
    @InjectView(R.id.select_xlistview)
    XListView selectXlistview;
    @InjectView(R.id.content)
    LinearLayout content;
    private View fragment_select_item_head, fragment_select_item_foot;
    private Banner banner;
    private MarqueeTextView announcement_tv;
    private TextView enrollment_tv, security_operations_tv, announcement_notice_tv, cumulative_transactions_Billion_no_tv,
            cumulative_transactions_Million_no_tv, cumulative_transactions_no_tv;
    private SelectFragmentAdapter selectFragmentAdapter;
    //    private ArrayList<SelectlistDataBean> list;
    private Handler handler;
    private ArrayList bannerlist;
    private BannerDataBean bannerBean;
    private List<AnnouncementDatasBean> announcementDatasBeen;
    private CommonInfo commonInfo;
    private LinearLayout announcement_notice_lin, cumulative_transactions_Billion_no_lin, cumulative_transactions_Million_no_lin, cumulative_transactions_no_lin;
    private ImageView announcement_close;
    private List<ActivityOrAnoucemnetInfo> activityOrAnoucemnetBeanLists;
    private boolean isFirstLauch;
    private AppInfo appInfo;
    //    private List<SelectlistDataBean> selectlistDataBeenLists = new ArrayList<SelectlistDataBean>();
    private int forceUpdate = -1;
    private TextView risk_hint;
    private DataDragFloatBean mDragBean = null;//浮动图标实体类
    private DragView mDragView;//可拖拽控件容器
    private boolean disableDragView = false;//是否显示浮动窗口
    private boolean isFirstShowActivityInfo = true;//是否第一次显示活动弹窗控制Fragment切换反复显示
    private boolean isFirstShowAnouncementInfo = true;//是否第一次显示公告弹窗
    private boolean isFreshShow = false;//控制刷新弹窗是渠道弹窗显示
    private int currentIndex = 0;//mainactivity当前页面的索引
    private String phone;
    private String psw;
    private String token;


    @Override
    protected void initializeData() {
        commonInfo = ConfigManager.getInstance().getCommonInfo(mContext);
        isFirstLauch = SharedPreference.getInstance().getBoolean(mContext, PreferenceCongfig.APP_FIRST_SELECT, true);
        activityOrAnoucemnetBeanLists = new ArrayList<ActivityOrAnoucemnetInfo>();
        phone = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_ACCOUNT, "");//账号
        psw = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_PSW, "");//密码
        bannerBean = LmwApp.bannerBean;
        EventBus.getDefault().register(this);
    }

    private void login() {
        // showLoadingDialog();
        Singlton.getInstance(LoginLogic.class).login(mContext, phone, psw, new OnResponseListener<LoginEntity>() {
            @Override
            public void onSuccess(LoginEntity response) {
                int code = Integer.valueOf(response.code);
                if (code == 0) {//登录成功
                    if (response.getData() != null) {
                        PreferenceCongfig.isLogin = true;
                        PreferenceCongfig.isCancelLogin = false;
                        getFloatingIcon(new FreshFloatIconEvent(FreshFloatIconEvent.FRESH_NOT));
                        EventBus.getDefault().post(new FreshMessageEvent());
                        LoginBean loginBean = response.getData();
                        token = loginBean.token;
                        SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_TOKEN, token);//保存token
                        SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_ACCOUNT, phone);//保存账号
                        SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_PSW, psw);//保存密码
                        //  EventBus.getDefault().post(new FreshUseInfoEvent(FreshUseInfoEvent.IS_FRESH_USER_INFO));
                    }
                } else {
                    SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_TOKEN, "");//保存token
                    SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_ACCOUNT, "");//保存账号
                    SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_PSW, "");//保存密码
                    PreferenceCongfig.isLogin = false;

                }
            }

            @Override
            public void onFail(Throwable e) {
                //  hideLoadingDialog();

            }
        });
    }

    @Override
    protected void initializeView() {
        xlists();
        findview();
        setBanner();
        getBanner();
        getAnnouncement();
        getStatistics();
        createDragView();//创建可拖拽窗体
        getRecommend();
        if (isFirstLauch) {
            checkIsShowTipsDialog();
        } else {
            if (PreferenceCongfig.IS_APP_AUTO_LOGIN)
                login();
            else
                getFloatingIcon(new FreshFloatIconEvent(FreshFloatIconEvent.FRESH_NOT));
        }


        /**
         * listview点击事件
         */
        selectXlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                SelectlistDataBean bean = (SelectlistDataBean) adapterView.getItemAtPosition(i);
                if (bean != null && bean.getDatas().get(0) != null) {
                    if (TextUtils.equals("2000", bean.getCateId())) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, DetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("subjectId", bean.datas.get(0).borrowId);// 标的标识
                        bundle.putString("type", "1");// 是否是债权
                        intent.putExtra("mobileAddInfo", bean.datas.get(0).getMobileAddInfo());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(mContext, DetailProductActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("subjectId", bean.datas.get(0).borrowId);// 标的标识
                        bundle.putString("type", "0");// 是否是债权
                        intent.putExtra("mobileAddInfo", bean.datas.get(0).getMobileAddInfo());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            }
        });


        /**
         * banner图点击事件
         */
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void OnBannerClick(View view, int position) {
                if (bannerBean != null) {
                    Intent intent = new Intent(getActivity(), WebViewMore.class);
                    intent.putExtra("url", bannerBean.datas.get(position - 1).bannerUrl);
                    intent.putExtra("title", bannerBean.datas.get(position - 1).bannerTitle);
                    startActivity(intent);
                }
            }
        });
        // 点击公告
        announcement_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (announcementDatasBeen != null && announcementDatasBeen.size() > 0) {
                    Intent intent = new Intent(mContext, WebViewMore.class);
                    intent.putExtra("url", announcementDatasBeen.get(0).getLinkUrl());
                    intent.putExtra("title", announcementDatasBeen.get(0).getPostTitle());
                    startActivity(intent);
                }
            }
        });
        // 隐藏条幅弹框
        announcement_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                announcement_notice_lin.setVisibility(View.GONE);
            }
        });
    }

    private void getAppVerisionInfo() {
        Singlton.getInstance(ConfigLogic.class).getVersionInfo(mContext, new OnResponseListener<AppEntity>() {
            @Override
            public void onSuccess(AppEntity appEntity) {
                if (appEntity != null) {

                    appInfo = appEntity.getData();
                    if (checkAppUpdate())
                        new Thread(new UpdateVersionTask(mContext, appEntity.getData())).start();
                    else {
                        getAnouncementOrActivityPopinfo();
                    }

                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });

    }

    private boolean checkAppUpdate() {
        int newVersion = Integer.valueOf(appInfo.getVersion().replace(".", ""));
        int curVersion = Integer.valueOf(APPUtil.getVersion(false).replace(".", ""));
        return newVersion > curVersion;//有更新
        //  forceUpdate = Integer.valueOf(appInfo.getIsForceUpdate());
    }

    /**
     * 创建可拖拽窗体控件˝
     */
    private void createDragView() {
        mDragView = DragView.getInstance(mContext);
        mDragView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDragBean != null) {
                    Intent intent = new Intent(mContext, WebViewMore.class);
                    intent.putExtra(WebViewMore.INTENT_KEY_TITLE, mDragBean.getName());
                    intent.putExtra(WebViewMore.INTENT_KEY_URL, mDragBean.getLink());
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 获取浮动图标
     *
     * @param
     * @param freshFloatIconEvent 刷新浮标和弹窗类型
     */


    private void getFloatingIcon(final FreshFloatIconEvent freshFloatIconEvent) {

        Singlton.getInstance(ConfigLogic.class).getDragData(mContext, new OnResponseListener<DataDragFloatBean>() {
            @Override
            public void onSuccess(DataDragFloatBean result) {
                if (result != null) {
                    mDragBean = result;
                    disableDragView = mDragBean.getDisable() == 1;
                    if (mDragView != null) {
                        if (!TextUtils.isEmpty(mDragBean.getIcon()))
                            PicassoManager.getInstance().display(mContext, mDragView.getImageView(), mDragBean.getIcon());
                        isShowDragView(currentIndex);
                    }
                }

                if (TextUtils.equals(freshFloatIconEvent.getFreshEventType(), FreshFloatIconEvent.FRESH_ICON_AND_POP)) {
                    isFreshShow = true;
                    getAnouncementOrActivityPopinfo();
                } else if (TextUtils.equals(freshFloatIconEvent.getFreshEventType(), FreshFloatIconEvent.FRESH_NOT)) {
                    getAppVerisionInfo();
                }

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    private void isShowDragView(int index) {
        if (mDragView == null) {
            return;
        }

        if (disableDragView && index == 0) {
            mDragView.showAtLocation(new int[]{846, 825});
        } else
            mDragView.hide();
    }


    /**
     * 获取公告或者活动弹窗信息
     */
    private long currentTime;

    private void getAnouncementOrActivityPopinfo() {
        Singlton.getInstance(BannerHttp.class).getAnouncementOrActivityPopinfo(getActivity(), new OnResponseListener<ActivityOrAnoucemnetEntity>() {
            @Override
            public void onSuccess(ActivityOrAnoucemnetEntity activityOrAnoucemnetEntity) {
                if (activityOrAnoucemnetEntity == null) {
                    return;
                }
                if (activityOrAnoucemnetEntity.getData() == null) {
                    return;
                }
                if (activityOrAnoucemnetEntity.getData().getDatas().size() == 0) {
                    return;
                }

                int code = Integer.valueOf(activityOrAnoucemnetEntity.code);
                if (code == 0) {
                    if (activityOrAnoucemnetBeanLists != null)
                        activityOrAnoucemnetBeanLists.clear();
                    activityOrAnoucemnetBeanLists.addAll(activityOrAnoucemnetEntity.getData().getDatas());
                    currentTime = activityOrAnoucemnetEntity.getData().getCurrentTime();
                    showActivityOrAnoucementInfo();
                } else
                    showToast(activityOrAnoucemnetEntity.msg);

            }

            @Override
            public void onFail(Throwable e) {
                ALLog.e(e.getMessage());
            }
        });


    }

    /**
     * 显示弹窗信息int index isFirstShowActivityInfo &&
     */
    private void showActivityOrAnoucementInfo() {
        if (activityOrAnoucemnetBeanLists != null && activityOrAnoucemnetBeanLists.size() > 0) {
            Iterator<ActivityOrAnoucemnetInfo> iterator = activityOrAnoucemnetBeanLists.iterator();
            if (iterator.hasNext()) {
                ActivityOrAnoucemnetInfo activityOrAnoucemnetInfo = iterator.next();
                jumdgeShowDialogType(activityOrAnoucemnetInfo);

            }
        }
    }

    /**
     * 判断显示弹窗类型
     */
    private void jumdgeShowDialogType(ActivityOrAnoucemnetInfo activityOrAnoucemnetInfo) {
        if (activityOrAnoucemnetInfo.getType() == 1) { //显示活动弹窗
            initDialogShowConfig(activityOrAnoucemnetInfo);
            if (activityOrAnoucemnetInfo.getShwoRate() == 1)//每次都显示
                showActiveDialog(activityOrAnoucemnetInfo);
            else if (activityOrAnoucemnetInfo.getShwoRate() == 2) {//只显示一次
                boolean isShowActiveDialog = SharedPreference.getInstance().getBoolean(mContext, PreferenceCongfig.IS_SHOW_ACTIVE_DIALOG, true);
                if (isShowActiveDialog) {
                    showActiveDialog(activityOrAnoucemnetInfo);
                    SharedPreference.getInstance().saveBoolean(mContext, PreferenceCongfig.IS_SHOW_ACTIVE_DIALOG, false);
                    SharedPreference.getInstance().saveLong(mContext, PreferenceCongfig.ACTIVE_DIALOG_ID, activityOrAnoucemnetInfo.getId());
                }
            } else if (activityOrAnoucemnetInfo.getShwoRate() == 0) {//每天仅第一次显示
                showDialog(activityOrAnoucemnetInfo);

            }
        } else if (activityOrAnoucemnetInfo.getType() == 2) {//显示公告弹窗
            initDialogShowConfig(activityOrAnoucemnetInfo);
            if (activityOrAnoucemnetInfo.getShwoRate() == 1) {//每次都显示
                showAnouncementDialog(activityOrAnoucemnetInfo);
            } else if (activityOrAnoucemnetInfo.getShwoRate() == 2) {//只显示一次
                boolean isShowAnoucementDialog = SharedPreference.getInstance().getBoolean(mContext, PreferenceCongfig.IS_SHOW_ANOUCEMENT_DIALOG, true);
                if (isShowAnoucementDialog) {
                    showAnouncementDialog(activityOrAnoucemnetInfo);
                    SharedPreference.getInstance().saveBoolean(mContext, PreferenceCongfig.IS_SHOW_ANOUCEMENT_DIALOG, false);
                    SharedPreference.getInstance().saveLong(mContext, PreferenceCongfig.ANOUCEMENT_DIALOG_ID, activityOrAnoucemnetInfo.getId());
                }
            } else if (activityOrAnoucemnetInfo.getShwoRate() == 0) {//每天仅第一次显示
                showDialog(activityOrAnoucemnetInfo);
            }
        } else if (activityOrAnoucemnetInfo.getType() == 3) {//显示渠道弹窗
            showActiveDialog(activityOrAnoucemnetInfo);
        }
    }

    /**
     * 有新的活动或公告弹窗先初始化显示配置
     */
    private void initDialogShowConfig(ActivityOrAnoucemnetInfo activityOrAnoucemnetInfo) {
        if (activityOrAnoucemnetInfo.getType() == 1) {//显示活动弹窗
            long activeDialogId = SharedPreference.getInstance().getLong(mContext, PreferenceCongfig.ACTIVE_DIALOG_ID, -1);
            if (activeDialogId != activityOrAnoucemnetInfo.getId()) {
                SharedPreference.getInstance().saveBoolean(mContext, PreferenceCongfig.IS_SHOW_ACTIVE_DIALOG, true);
            }

        } else if (activityOrAnoucemnetInfo.getType() == 2) {//显示公告弹窗
            long anouncementDialogId = SharedPreference.getInstance().getLong(mContext, PreferenceCongfig.ANOUCEMENT_DIALOG_ID, -1);
            if (anouncementDialogId != activityOrAnoucemnetInfo.getId()) {
                SharedPreference.getInstance().saveBoolean(mContext, PreferenceCongfig.IS_SHOW_ANOUCEMENT_DIALOG, true);
            }
        }
    }

    private void showDialog(ActivityOrAnoucemnetInfo activityOrAnoucemnetInfo) {
        long curYmd = DateUtil.formateYMDDate(currentTime);
        ALLog.e("curYmd================" + curYmd);
        if (activityOrAnoucemnetInfo.getType() == 1) {//显示活动弹窗
            long activeDialogId = SharedPreference.getInstance().getLong(mContext, PreferenceCongfig.ACTIVE_DIALOG_ID, -1);
            if (activeDialogId != activityOrAnoucemnetInfo.getId()) {//id不相等表示有新的活动弹窗显示
                showActiveDialog(activityOrAnoucemnetInfo);
                SharedPreference.getInstance().
                        saveLong(mContext, PreferenceCongfig.LAST_SHOW_ACTIVE_DIALOG_DATE, curYmd);
                SharedPreference.getInstance().saveLong(mContext, PreferenceCongfig.ACTIVE_DIALOG_ID, activityOrAnoucemnetInfo.getId());
            } else {//id相等表示显示与上次相同的活动弹窗一天显示一次，通过保存的日期判断
                long lastShowActiveDialogTime = SharedPreference.getInstance().
                        getLong(mContext, PreferenceCongfig.LAST_SHOW_ACTIVE_DIALOG_DATE, 0);
                if (curYmd - lastShowActiveDialogTime >= 1) {
                    showActiveDialog(activityOrAnoucemnetInfo);
                    SharedPreference.getInstance().
                            saveLong(mContext, PreferenceCongfig.LAST_SHOW_ACTIVE_DIALOG_DATE, curYmd);
                }
            }
        } else if (activityOrAnoucemnetInfo.getType() == 2) {//显示公告弹窗
            long anouncementDialogId = SharedPreference.getInstance().getLong(mContext, PreferenceCongfig.ANOUCEMENT_DIALOG_ID, -1);
            if (anouncementDialogId != activityOrAnoucemnetInfo.getId()) {//id不相等表示有新的公告弹窗显示
                showAnouncementDialog(activityOrAnoucemnetInfo);
                SharedPreference.getInstance().
                        saveLong(mContext, PreferenceCongfig.LAST_SHOW_ANOUCEMENT_DIALOG_DATE, curYmd);
                SharedPreference.getInstance().saveLong(mContext, PreferenceCongfig.ANOUCEMENT_DIALOG_ID, activityOrAnoucemnetInfo.getId());
            } else {//id相等表示显示与上次相同的公告弹窗一天显示一次，通过保存的日期判断
                long lastShowAnoucemnetDialogTime = SharedPreference.getInstance().
                        getLong(mContext, PreferenceCongfig.LAST_SHOW_ANOUCEMENT_DIALOG_DATE, 0);
                if (curYmd - lastShowAnoucemnetDialogTime >= 1) {
                    showAnouncementDialog(activityOrAnoucemnetInfo);
                    SharedPreference.getInstance().
                            saveLong(mContext, PreferenceCongfig.LAST_SHOW_ANOUCEMENT_DIALOG_DATE, curYmd);
                }
            }
        }
    }

    private void showActiveDialog(final ActivityOrAnoucemnetInfo activityOrAnoucemnetInfo) {
        ShowActiveDialog showActiveDialog = new ShowActiveDialog(mContext, activityOrAnoucemnetInfo.getImageUrl(), new OnDialogClickListener() {
            @Override
            public void onClick(int id, View v) {
                if (id == 1) {
                    //点击图片跳转到WebView加载活动页面
                    Bundle bundle = new Bundle();
                    bundle.putString("title", activityOrAnoucemnetInfo.getTitle());
                    bundle.putString("url", activityOrAnoucemnetInfo.getLinkUrl());
                    ActivityTools.switchActivity(getActivity(), WebViewMore.class, bundle);
                }
            }
        });

        if (isFreshShow) {
            if (activityOrAnoucemnetInfo.getType() == 3 && currentIndex == 0) {//渠道弹窗
                showActiveDialog.show();
                isFreshShow = false;
                isFirstShowActivityInfo = false;
            } else
                showActiveDialog.hide();
        } else {
            if (currentIndex == 0 && isFirstShowActivityInfo) {
                showActiveDialog.show();
                isFirstShowActivityInfo = false;
            } else {
                showActiveDialog.hide();
            }
        }
    }

    /**
     * 显示公告弹窗
     */
    private void showAnouncementDialog(final ActivityOrAnoucemnetInfo activityOrAnoucemnetInfo) {
        ShowAnnouncementDialog showAnnouncementDialog = new ShowAnnouncementDialog(mContext, activityOrAnoucemnetInfo.getLinkUrl(), new OnDialogClickListener() {
            @Override
            public void onClick(int id, View v) {
                if (id == 1) {
                    //点击图片跳转到WebView加载活动页面
                    Bundle bundle = new Bundle();
                    bundle.putString("title", activityOrAnoucemnetInfo.getTitle());
                    bundle.putString("url", activityOrAnoucemnetInfo.getLinkUrl());
                    ActivityTools.switchActivity(getActivity(), WebViewMore.class, bundle);
                }
            }
        });
        if (isFreshShow) {
            showAnnouncementDialog.hide();
            isFreshShow = false;
            isFirstShowAnouncementInfo = false;
        } else {
            if (currentIndex == 0 && isFirstShowAnouncementInfo) {
                showAnnouncementDialog.show();
                isFirstShowAnouncementInfo = false;
            } else {
                showAnnouncementDialog.hide();
            }
        }
    }


    @Override
    protected int getContentLayout() {
        return R.layout.fragment_select_layout;
    }


    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (selectXlistview != null) {
                    selectXlistview.setRefreshTime(DateUtil.getRefreshTime());
                    getBannerData();
                    getAnnouncement();
                    getStatistics();
                    getRecommend();
                    selectXlistview.stopRefresh();
                }
            }
        }, 1000);
    }


    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (selectXlistview != null) {
                    selectXlistview.stopLoadMore();
                }
            }
        }, 1000);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /**
     * listview 实例化
     */
    private void findview() {
        fragment_select_item_head = LayoutInflater.from(mContext).inflate(R.layout.fragment_select_item_head, null);
        fragment_select_item_foot = LayoutInflater.from(mContext).inflate(R.layout.fragment_select_item_foot, null);
        announcement_tv = (MarqueeTextView) fragment_select_item_head.findViewById(R.id.announcement_tv);// 公告
        announcement_notice_lin = (LinearLayout) fragment_select_item_head.findViewById(R.id.announcement_notice_lin);
        announcement_notice_tv = (TextView) fragment_select_item_head.findViewById(R.id.announcement_notice_tv);
        announcement_close = (ImageView) fragment_select_item_head.findViewById(R.id.announcement_close);
        cumulative_transactions_Billion_no_tv = (TextView) fragment_select_item_foot.findViewById(R.id.cumulative_transactions_Billion_no_tv);// 累计交易额 亿
        cumulative_transactions_Million_no_tv = (TextView) fragment_select_item_foot.findViewById(R.id.cumulative_transactions_Million_no_tv);// 累计交易额 万
        cumulative_transactions_no_tv = (TextView) fragment_select_item_foot.findViewById(R.id.cumulative_transactions_no_tv);// 累计交易额 元
        cumulative_transactions_Billion_no_lin = (LinearLayout) fragment_select_item_foot.findViewById(R.id.cumulative_transactions_Billion_no_lin);// Lin 亿
        cumulative_transactions_Million_no_lin = (LinearLayout) fragment_select_item_foot.findViewById(R.id.cumulative_transactions_Million_no_lin);// Lin 万
        cumulative_transactions_no_lin = (LinearLayout) fragment_select_item_foot.findViewById(R.id.cumulative_transactions_no_lin);// Lin 元
        enrollment_tv = (TextView) fragment_select_item_foot.findViewById(R.id.enrollment_tv);// 注册人数
        security_operations_tv = (TextView) fragment_select_item_foot.findViewById(R.id.security_operations_tv);// 安全运营
        risk_hint = (TextView) fragment_select_item_foot.findViewById(R.id.risk_hint);// 风险提示
        if (commonInfo != null && !TextUtils.isEmpty(commonInfo.lmw_product_prompt))
            risk_hint.setText(commonInfo.lmw_product_prompt);
        selectXlistview.addHeaderView(fragment_select_item_head);
        selectXlistview.addFooterView(fragment_select_item_foot);
        selectFragmentAdapter = new SelectFragmentAdapter(mContext);
        selectXlistview.setAdapter(selectFragmentAdapter);
        selectFragmentAdapter.setOnTimeOverListener(new SelectFragmentAdapter.OnTimeOverListener() {
            @Override
            public void isFinish() {
                getRecommend();
            }
        });
    }


    /**
     * banner 实例化
     */

    private void setBanner() {
        banner = (Banner) fragment_select_item_head.findViewById(R.id.banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR); // banner样式
        banner.setDelayTime(5000); // banner轮播时间
    }

    /**
     * Xlistview 实例化
     */
    private void xlists() {
        if (selectXlistview != null) {
            handler = new Handler();
            selectXlistview.setPullRefreshEnable(true);// 刷新
            selectXlistview.setPullLoadEnable(false);// 加载
            selectXlistview.setXListViewListener(this);
            selectXlistview.setRefreshTime(DateUtil.getRefreshTime());
        }
    }


    /**
     * 获取banner条
     */
    private void getBanner() {
        if (bannerBean != null) {
            setBannerData();
        } else {
            getBannerData();
        }
    }

    private void setBannerData() {
        bannerlist = new ArrayList<String>();
        if (bannerBean.datas.size() > 0) {
            for (int i = 0; i < bannerBean.datas.size(); i++) {
                bannerlist.add(bannerBean.datas.get(i).bannerImage);
            }
            banner.setImages(bannerlist);
        }
    }

    /**
     * 获取banner条
     */
    private void getBannerData() {
        Singlton.getInstance(BannerHttp.class).getBanner(mContext, 0, new OnResponseListener<BannerEntity>() {
            @Override
            public void onSuccess(BannerEntity response) {
                if (response == null) return;
                if (response.data == null) return;
                if (response.data.datas == null) return;
                if (response.code.equals("0")) {
                    bannerBean = response.data;
                    if (bannerBean != null)
                        setBannerData();
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });

    }

    /**
     * 获取首页公告
     */
    private void getAnnouncement() {
        Singlton.getInstance(BannerHttp.class).getAnnouncement(mContext, 1, 1, new OnResponseListener<AnnouncementEntity>() {
            @Override
            public void onSuccess(AnnouncementEntity announcementEntity) {
                if (announcementEntity == null) return;
                if (announcementEntity.getCode().equals("0")) {
                    announcementDatasBeen = new ArrayList<AnnouncementDatasBean>();
                    if (announcementEntity.getData().getDatas().size() == 0) {
                    } else {
                        announcement_tv.setText(announcementEntity.getData().getDatas().get(0).getPostTitle());
                        announcementDatasBeen.addAll(announcementEntity.getData().getDatas());
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });

    }

    /**
     * 获取底部信息
     */
    private void getStatistics() {
        Singlton.getInstance(BannerHttp.class).getStatistics(mContext, new OnResponseListener<StatisticsEntity>() {
            @Override
            public void onSuccess(StatisticsEntity statisticsEntity) {
                if (statisticsEntity == null) return;
                if (statisticsEntity.code.equals("0")) {
                    if (statisticsEntity.data.datas.size() > 0) {
                        if (NumberParserUtil.parserDouble(statisticsEntity.data.datas.get(1).count, 0) >= 100000000) {
                            cumulative_transactions_Billion_no_lin.setVisibility(View.VISIBLE);
                            cumulative_transactions_Million_no_lin.setVisibility(View.VISIBLE);
                            cumulative_transactions_no_lin.setVisibility(View.VISIBLE);
                            cumulative_transactions_Billion_no_tv.setText(BigDecemalCalculateUtil.formatValue(statisticsEntity.data.datas.get(1).count, 8, 0));
                            cumulative_transactions_Million_no_tv.setText(BigDecemalCalculateUtil.formatMillionValue(statisticsEntity.data.datas.get(1).count));
                            cumulative_transactions_no_tv.setText(BigDecemalCalculateUtil.formatThousandValue(statisticsEntity.data.datas.get(1).count));
                        } else if (NumberParserUtil.parserDouble(statisticsEntity.data.datas.get(1).count) >= 10000) {
                            cumulative_transactions_Billion_no_lin.setVisibility(View.GONE);
                            cumulative_transactions_Million_no_lin.setVisibility(View.VISIBLE);
                            cumulative_transactions_no_lin.setVisibility(View.VISIBLE);
                            cumulative_transactions_Million_no_tv.setText(BigDecemalCalculateUtil.formatMillionValue(statisticsEntity.data.datas.get(1).count));
                            cumulative_transactions_no_tv.setText(BigDecemalCalculateUtil.formatThousandValue(statisticsEntity.data.datas.get(1).count));
                        } else {
                            cumulative_transactions_Billion_no_lin.setVisibility(View.GONE);
                            cumulative_transactions_Million_no_lin.setVisibility(View.GONE);
                            cumulative_transactions_no_lin.setVisibility(View.VISIBLE);
                            cumulative_transactions_no_tv.setText(BigDecemalCalculateUtil.formatThousandValue(statisticsEntity.data.datas.get(1).count));
                        }
                        enrollment_tv.setText(
                                NumberWith.dealWith(statisticsEntity.data.datas.get(0).count) + statisticsEntity.data.datas.get(0).units);
                        security_operations_tv.setText(
                                statisticsEntity.data.datas.get(2).count + statisticsEntity.data.datas.get(2).units);
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 获取首页列表
     */
    private void getRecommend() {
        Singlton.getInstance(BannerHttp.class).getRecommend(mContext, new OnResponseListener<BaseResult<DataSelectlist>>() {
            @Override
            public void onSuccess(BaseResult<DataSelectlist> result) {
                if (TextUtils.equals("0", result.getCode()) && result.getData() != null && result.getData().getDatas() != null) {
                    selectFragmentAdapter.clearCache();//清楚缓存
                    selectFragmentAdapter.resetDatas(result.getData().getDatas());
                    fragment_select_item_foot.setVisibility(View.VISIBLE);
                } else {
                    ToastUtil.showToast(mContext, result.getMsg());
                    fragment_select_item_foot.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (selectXlistview != null) {
                    fragment_select_item_foot.setVisibility(View.GONE);
                }
            }
        });
    }


    /**
     * 第一次 进入 提示
     *
     * @param context
     */
    private SelectGuideDialog selectGuideDialog;

    private void checkIsShowTipsDialog() {
        selectGuideDialog = new SelectGuideDialog(mContext);
        selectGuideDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                SharedPreference.getInstance().saveBoolean(mContext, PreferenceCongfig.APP_FIRST_SELECT, false);
                getFloatingIcon(new FreshFloatIconEvent(FreshFloatIconEvent.FRESH_NOT));
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent baseEvent) {
        if (baseEvent != null && baseEvent instanceof FreshFloatIconEvent) {
            FreshFloatIconEvent event = (FreshFloatIconEvent) baseEvent;
            String freshType = event.getFreshEventType();
            if (TextUtils.equals(freshType, FreshFloatIconEvent.FRESH_NOT)) {
                currentIndex = event.getIndex();
                isShowDragView(event.getIndex());
                if (event.getIndex() == 0)//当前页面是首页判断是否弹窗
                    showActivityOrAnoucementInfo();
            } else if (!TextUtils.equals(freshType, FreshFloatIconEvent.FRESH_USER_INFO)) {
                getFloatingIcon(event);
            }


        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
