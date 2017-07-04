package com.example.administrator.lmw.utils;

import android.Manifest;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.lmw.entity.AppInfo;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.view.NormalLeftDialog;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;


public class UpdateVersionTask implements Runnable {
    private Context mContext;
    private UpdateManager updateManager;
    private AppInfo appInfo;
    private int forceUpdate;
    private NormalLeftDialog forceUpdateDialog, selectUpdateDialog;

    public UpdateVersionTask(Context context, AppInfo appInfo) {
        this.mContext = context;
        updateManager = new UpdateManager(context);
        this.appInfo = appInfo;
    }

    /**
     * isForceUpdate	是否强制更新	string	0=否、1=是
     */
    @Override
    public void run() {
        if (appInfo == null) return;
        int newVersion = Integer.valueOf(appInfo.getVersion().replace(".", ""));
        forceUpdate = Integer.valueOf(appInfo.getIsForceUpdate());
        int curVersion = Integer.valueOf(APPUtil.getVersion(false).replace(".", ""));
        if (newVersion > curVersion) { //有版本可以更新
            if (forceUpdate == 1) {
                ThreadManager.getMainHandler().post(new Runnable() {
                    public void run() {
                        forceUpdateDialog = new NormalLeftDialog(mContext, "新版本更新提示", appInfo.getUpdateContent(),
                                "立即更新", new OnDialogClickListener() {
                            @Override
                            public void onClick(int id, View v) {
                                if (id == 2) {
                                    handleUpdateBusiniss();
                                }
                            }
                        });
                        forceUpdateDialog.setCancelable(false);
                        forceUpdateDialog.setCanceledOnTouchOutside(false);
                    }
                });
            } else if (forceUpdate == 0) {
                ThreadManager.getMainHandler().post(new Runnable() {
                    public void run() {
                        selectUpdateDialog = new NormalLeftDialog(mContext, "新版本更新提示", appInfo.getUpdateContent(),
                                "稍后提醒", "立即更新", false, new OnDialogClickListener() {
                            @Override
                            public void onClick(int id, View v) {
                                if (id == 0) {//取消
                                    selectUpdateDialog.dismiss();
                                } else if (id == 1) {
                                    handleUpdateBusiniss();
                                }
                            }
                        });
                        selectUpdateDialog.setCancelable(false);
                        selectUpdateDialog.setCanceledOnTouchOutside(false);
                    }
                });
            }
        }
    }

    private void handleUpdateBusiniss() {
        RxPermissions.getInstance(mContext)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            updateManager.update(appInfo.getDownloadUrl());//"https://m.limin.com/download/limin.apk"
                            // appInfo.getDownloadUrl();
                        } else {
                            Toast.makeText(mContext,
                                    "您已拒绝了利民网权限授权，授权同意后才能继续体验",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
