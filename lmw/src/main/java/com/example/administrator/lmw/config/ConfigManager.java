package com.example.administrator.lmw.config;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.lmw.entity.CommonInfo;
import com.example.administrator.lmw.utils.SharedPreference;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * 保存全局配置信息
 *
 * @create by lion
 * @created at 2016/8/12
 */
public class ConfigManager {
    private SharedPreference sharedPreference;
    private static ConfigManager instance;
    private CommonInfo commonInfo;

    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }


    private ConfigManager() {
        sharedPreference = SharedPreference.getInstance();
    }

    /**
     * 获取到一个 UserInfo
     *
     * @return
     */
    public CommonInfo getCommonInfo(Context context) {
        final String results = sharedPreference.getString(context, PreferenceCongfig.KEY_COMMON_INFO, "");
        if (!TextUtils.isEmpty(results)) {
            try {
                Gson gson = new Gson();
                commonInfo = gson.fromJson(results, CommonInfo.class);
                return commonInfo;
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 保存CommonEntity
     *
     * @param userInfo
     */
    public void setCommonInfo(Context context, CommonInfo userInfo) {
        if (userInfo == null) return;
        Gson gson = new Gson();
        final String json = gson.toJson(userInfo);
        sharedPreference.saveString(context, PreferenceCongfig.KEY_COMMON_INFO, json);

    }

    /**
     * 清除UserInfo
     */
    public void clearCommonInfo(Context context) {
        sharedPreference.saveString(context, PreferenceCongfig.KEY_COMMON_INFO, "");
    }

    public String getPrivateKeyClient(Context context) {
        String privateKey = "";
        getCommonInfo(context);
        if (commonInfo != null)
            privateKey = commonInfo.getPrivateKeyClient();
        return privateKey;

    }

    public String getPublicKeyClient(Context context) {
        String publicKey = "";
        getCommonInfo(context);
        if (commonInfo != null)
            publicKey = commonInfo.getPublicKeyClient();
        return publicKey;
    }


    public boolean getEnableRsa(Context context) {
        String enableRsa = "";
        getCommonInfo(context);
        if (commonInfo != null)
            enableRsa = commonInfo.getEnableRsa();
        if (!TextUtils.isEmpty(enableRsa) && TextUtils.equals(enableRsa, "1"))
            return true;
        else
            return false;

    }

}
