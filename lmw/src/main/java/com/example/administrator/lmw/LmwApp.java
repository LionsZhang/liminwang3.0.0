package com.example.administrator.lmw;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import com.example.administrator.lmw.R;
import com.example.administrator.lmw.config.ConfigLogic;
import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.config.VersionConfig;
import com.example.administrator.lmw.entity.CommonEntity;
import com.example.administrator.lmw.entity.CommonInfo;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.select.BannerHttp;
import com.example.administrator.lmw.select.entity.BannerDataBean;
import com.example.administrator.lmw.select.entity.BannerEntity;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.CrashHandler;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.lzy.okgo.OkGo;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.UUID;

import cn.jpush.android.api.JPushInterface;
import cn.udesk.UdeskSDKManager;
import cn.udesk.config.UdeskConfig;


public class LmwApp extends Application {
    private static LmwApp instance;
    /**
     * 日志打印开关 false 不打印 true 打印
     */
    public static final boolean DEBUG_FLAG = true;
    // sp管理器
    private SharedPreference mSharedPreference;

    // 版本控制
    private VersionConfig mVersionConfig;

    // 全局的布局加载器
    public LayoutInflater mGlobalLayoutInflater;
    //   public static Handler handler;
    //   public static AppInfo appInfo;
    public static BannerDataBean bannerBean;

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Config.DEBUG = false;
        OkGo.init(this);
        initJgPushService();
        initWxShareConfig();
        initQQShareConfig();
        initSinaShareConfig();
        initUdeskConfig();
        UMShareAPI.get(this);
        instance = this;
        initData();
        getCommonInfo();
        getBanner();
    }

    private void initJgPushService() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    private void initUdeskConfig() {
        UdeskSDKManager.getInstance().initApiKey(this, "liminkefu.udesk.cn", "262265516fea1d6380f13d4fd639b6d3");
        UdeskConfig.udeskTitlebarBgResId = R.color.text_blue;
        String uDeskTokenGroop = SharedPreference.getInstance().getString(this, PreferenceCongfig.APP_UDESK_TOKEN_GROOP, "");
        if (TextUtils.isEmpty(uDeskTokenGroop)) {
            uDeskTokenGroop = UUID.randomUUID().toString();
            SharedPreference.getInstance().saveString(this, PreferenceCongfig.APP_UDESK_TOKEN_GROOP, uDeskTokenGroop);
        }
    }

    private void initWxShareConfig() {
        PlatformConfig.setWeixin("wx9cf0d619d501fdf8", "36f84f31cc3745f5e1dfeba31132d046");//微信分享key

    }

    private void initQQShareConfig() {
        PlatformConfig.setQQZone("1105111711", "r0PN9KvrXdjwcJqe");//QQ分享key

    }

    private void initSinaShareConfig() {
        Config.REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
        PlatformConfig.setSinaWeibo("333262896", "d65258bb46ab73200c16053844badbe2");//新浪分享key  "https://api.weibo.com/oauth2/default.html"

    }

    private void getCommonInfo() {
        Singlton.getInstance(ConfigLogic.class).getCommonInfo(this, new OnResponseListener<CommonEntity>() {
            @Override
            public void onSuccess(CommonEntity commonEntity) {
                ALLog.e("获取公共信息1" + "===========================");
                if (commonEntity != null) {
                    ALLog.e("获取公共信息" + commonEntity.code + "");
                    CommonInfo commonInfo = commonEntity.getData();
                    ALLog.e("commonInfo" + commonInfo.getLmw_recharge_min_amount() + "");
                    if (commonInfo != null) {
                        ConfigManager.getInstance().setCommonInfo(instance, commonInfo);
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });

    }

    /**
     * 获取banner条
     */
    private void getBanner() {
        Singlton.getInstance(BannerHttp.class).getBanner(this, 0, new OnResponseListener<BannerEntity>() {
            @Override
            public void onSuccess(BannerEntity response) {
                if (response == null) return;
                if (response.data == null) return;
                if (response.data.datas == null) return;
                if (response.code.equals("0")) {
                    bannerBean = response.data;
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });

    }


    /**
     * 初始化数据
     */
    private void initData() {
        // 版本控制,true是打印日志false不打印日志
        mVersionConfig = new VersionConfig(this, DEBUG_FLAG);
        mSharedPreference = SharedPreference.getInstance();
        // 初始化imageloader
        //      imageLoaderConfig = new ImageLoaderConfig(this);
        // 初始化全局加载器
        mGlobalLayoutInflater = (LayoutInflater) (getBaseContext()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 初始化Crash异常
        initCrashHandler();

    }

    public static LmwApp getInstance() {
        return instance;
    }

    /**
     * 获取sp管理器
     *
     * @return
     */
    public SharedPreference getSharedPreference() {
        return mSharedPreference;
    }


    /**
     * 获取应用配置文件
     *
     * @return
     */
    public VersionConfig getVersionConfig() {
        return mVersionConfig;
    }


    @Override
    public void onTerminate() {

        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        System.gc();
        super.onLowMemory();
    }

    /**
     * 初始化Crash异常
     */
    public void initCrashHandler() {
        // 异常捕获
        CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        crashHandler.init(getApplicationContext());

    }
}
