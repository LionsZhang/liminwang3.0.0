package com.example.administrator.lmw.login;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.lmw.config.DespositAccountManager;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.inteface.OnMineDataChangeListener;
import com.example.administrator.lmw.inteface.OnUserInfoChangeListener;
import com.example.administrator.lmw.login.entity.MineData;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:TODO 用户个人信息管理类, 如果调用页面需要根据useinfo的改变刷新UI的话，在该页面addUseInfoChangeListener（）注册监听
 * 并同时在ondestroy()方法反注册监听removeUseInfoChangeListener
 * @create by lion
 * @created at 2016/8/12
 */
public class UserInfoUtils {
    private SharedPreference sharedPreference;
    private static UserInfoUtils instance;
    private static List<OnUserInfoChangeListener> listeners = new ArrayList<OnUserInfoChangeListener>();
    private static List<OnMineDataChangeListener> onMineDataChangeListeners = new ArrayList<OnMineDataChangeListener>();

    /**
     * 注册一个UserInfo资源监听者
     *
     * @param listener
     */
    public static void addUserInfoChangeListener(OnUserInfoChangeListener listener) {
        listeners.add(listener);

    }

    /**
     * 删除一个UserInfo监听者
     *
     * @param listener
     */
    public static void removeUserInfoChangeListener(OnUserInfoChangeListener listener) {
        listeners.remove(listener);
    }

    /**
     * 注册一个MineData资源监听者
     *
     * @param listener
     */
    public static void addMineDataChangeListener(OnMineDataChangeListener listener) {
        onMineDataChangeListeners.add(listener);
    }


    /**
     * 删除一个MineData监听者
     *
     * @param listener
     */
    public static void removeMineDataChangeChangeListener(OnMineDataChangeListener listener) {
        onMineDataChangeListeners.remove(listener);
    }


    public static UserInfoUtils getInstance() {
        if (instance == null) {
            synchronized (UserInfoUtils.class) {
                if (instance == null) {
                    instance = new UserInfoUtils();
                }
            }
        }
        return instance;
    }


    private UserInfoUtils() {
        sharedPreference = SharedPreference.getInstance();
    }

    /**
     * 获取到一个 UserInfo
     *
     * @return
     */
    public UserInfo getUserInfo(Context context) {
        final String results = sharedPreference.getString(context, PreferenceCongfig.KEY_USE_INFO, "");
        if (!TextUtils.isEmpty(results)) {
            try {
                Gson gson = new Gson();
                return gson.fromJson(results, UserInfo.class);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取到一个 MineData
     *
     * @return
     */
    public MineData getMineData(Context context) {
        final String results = sharedPreference.getString(context, PreferenceCongfig.KEY_MINE_DATA_INFO, "");
        if (!TextUtils.isEmpty(results)) {
            try {
                Gson gson = new Gson();
                return gson.fromJson(results, MineData.class);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 保存最新的UserInfo
     *
     * @param userInfo
     */
    public void setUserInfo(Context context, UserInfo userInfo) {
        if (userInfo == null) return;
        //修改存管相关的本地缓存
        Singlton.getInstance(DespositAccountManager.class).updateLocalUserInfo(userInfo.getData());
        Gson gson = new Gson();
        final String json = gson.toJson(userInfo);
        sharedPreference.saveString(context, PreferenceCongfig.KEY_USE_INFO, json);
        // notifyDataChange(userInfo);
    }

    /**
     * 保存最新的mineData
     *
     * @param mineData
     */
    public void setMineData(Context context, MineData mineData) {
        if (mineData == null) return;
        Gson gson = new Gson();
        final String json = gson.toJson(mineData);
        sharedPreference.saveString(context, PreferenceCongfig.KEY_MINE_DATA_INFO, json);
        //  notifyMineDataChange(mineData);
    }


    public void notifyDataChange(UserInfo userInfo) {
        for (OnUserInfoChangeListener l : listeners) {
            if (l == null) continue;
            l.onUserInfoChange(userInfo);
        }
    }

    public void notifyMineDataChange(MineData mineData) {
        for (OnMineDataChangeListener l : onMineDataChangeListeners) {
            if (l == null) continue;
            l.onMineDataChange(mineData);
        }
    }


    /**
     * 清除UserInfo
     */
    public void clearUserInfo(Context context) {
        sharedPreference.saveString(context, PreferenceCongfig.KEY_USE_INFO, "");
        //notifyDataChange(null);
    }

    /**
     * 清除mineData
     */
    public void clearMineData(Context context) {
        sharedPreference.saveString(context, PreferenceCongfig.KEY_MINE_DATA_INFO, "");
        // notifyMineDataChange(null);
    }

}
