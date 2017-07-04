package com.example.administrator.lmw.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.config.ConfigLogic;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseEvent;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.entity.ListenNetEvent;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.view.NormalLeftDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.download.DownloadInfo;
import com.lzy.okserver.download.DownloadManager;
import com.lzy.okserver.download.DownloadService;
import com.lzy.okserver.listener.DownloadListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

public class UpdateManager {
    private Context context;
    private final static String APK_DIR = "apks";
    private static final String LMW_DIR = "liminw";
    private NormalLeftDialog normalLeftDialog;
    private DownloadManager downloadManager;
    private List<DownloadInfo> allTask;
    private DownloadInfo downloadInfo;
    private volatile boolean isTop = false;

    public UpdateManager(Context context) {
        this.context = context;
        EventBus.getDefault().register(this);
    }

    /**
     * 下载
     */
    private File writeToPath;
    private String downLoadFileName;

    public void update(String downloadURL) {
        if (TextUtils.isEmpty(downloadURL)) {
            return;
        }
        writeToPath = FileUtil.getExternalCacheDir(context, LMW_DIR,
                APK_DIR);
        downLoadFileName = FileUtil.getFileNameByUrl(downloadURL);
        downloadManager = DownloadService.getDownloadManager();
        downloadManager.setTargetFolder(writeToPath.getAbsolutePath());
        File apkFile = new File(writeToPath.getAbsolutePath(), downLoadFileName);
        if (!apkFile.exists()) {//防止用户删除apk，下载数据库仍保留下载数据
            downloadManager.removeAllTask();//移除下载记录
            SharedPreference.getInstance().saveBoolean(context, PreferenceCongfig.APP_DOWN_LOAD_COMPULTE, false);//用户没有下载
        }
        if (!SharedPreference.getInstance().getBoolean(context, PreferenceCongfig.APP_DOWN_LOAD_COMPULTE, false)) {//用户没有下载则下载
            showUpdateDownDialog();
            downloadManager.getThreadPool().setCorePoolSize(1);
            download(downloadURL);
        } else {
//            installAPK(apkFile);//下载了直接安装
            check(apkFile);
//            UpdateUtil.install(context, apkFile, false);
        }

    }

    private void showUpdateDownDialog() {
        ThreadManager.getMainHandler().post(new Runnable() {
            public void run() {
                normalLeftDialog = new NormalLeftDialog(context, "新版本更新提示", "正在下载最新版本:",
                        "取消下载", "暂停下载", true, new OnDialogClickListener() {
                    @Override
                    public void onClick(int id, View v) {
                        if (id == 0) {//取消下载
                            normalLeftDialog.dismiss();
                            downloadManager.stopAllTask();
                        } else if (id == 1) {//暂停下载或者继续下载
                            if (downloadInfo == null) return;
                            switch (downloadInfo.getState()) {
                                case DownloadManager.PAUSE:
                                case DownloadManager.NONE:
                                case DownloadManager.ERROR:
                                    downloadManager.addTask(downloadInfo.getUrl(), downloadInfo.getRequest(), downloadInfo.getListener());
                                    normalLeftDialog.setRightBtnTxt("暂停下载");
                                    isTop = true;
                                    break;
                                case DownloadManager.DOWNLOADING:
                                    downloadManager.pauseTask(downloadInfo.getUrl());
                                    normalLeftDialog.setRightBtnTxt("继续下载");
                                    isTop = false;
                                    break;
                                case DownloadManager.FINISH:

                                    break;
                            }

                        }
                    }
                });
                normalLeftDialog.setCancelable(false);
                normalLeftDialog.setCanceledOnTouchOutside(false);
            }
        });

    }

    private void download(String downloadURL) {
        GetRequest request = OkGo.get(downloadURL);
        downloadManager.addTask(downLoadFileName, downloadURL, request, new DownloadListener() {
            @Override
            public void onProgress(DownloadInfo downloadInfo) {
                float progress = BigDecemalCalculateUtil.divide(downloadInfo.getTotalLength(), downloadInfo.getDownloadLength(), 1);
                if (normalLeftDialog != null) {
                    //下载完成时先设置进度条为99.99%等待检测通过改为100%并隐藏弹框和进行安装
//                    progress = progress == 100 ? 99.9f : progress;
                    normalLeftDialog.setDownLoadProgress(progress);
                }
                //normalLeftDialog.setDownLoadProgress((Math.round(downloadInfo.getProgress() * 10000) * 1.0f / 100));
            }

            @Override
            public void onFinish(DownloadInfo downloadInfo) {
                if (normalLeftDialog != null) {
                    normalLeftDialog.dismiss();
                }
                SharedPreference.getInstance().saveBoolean(context, PreferenceCongfig.APP_DOWN_LOAD_COMPULTE, true);//保存用户下载完成
//                installAPK(new File(downloadInfo.getTargetPath()));
                //TODO 开始和服务器校验文件md5，通过就安装否则删除文件重新下载
                check(new File(downloadInfo.getTargetPath()));
            }

            @Override
            public void onError(DownloadInfo downloadInfo, String errorMsg, Exception e) {
//                //TODO 下载错误删除缓存文件和数据库记录
//                if (downloadInfo != null && downloadManager != null){
//                    SharedPreference.getInstance().saveBoolean(context, PreferenceCongfig.APP_DOWN_LOAD_COMPULTE, false);
//                    downloadManager.removeTask(downloadInfo.getTaskKey(), true);
//               }
            }
        });
        downloadManager.startAllTask();
        allTask = downloadManager.getAllTask();
        downloadInfo = allTask.get(0);
    }


    private void check(final File apkFile) {
        String md5 = UpdateUtil.md5(apkFile);
        ALLog.i("md5  " + md5);

        Singlton.getInstance(ConfigLogic.class).checkApkFile(context, md5, new OnResponseListener<BaseResult<String>>() {
            @Override
            public void onSuccess(BaseResult<String> result) {
                if (result != null && TextUtils.equals(result.getCode(), "0")) {//校验通过
                    UpdateUtil.install(context, apkFile, false);
                    EventBus.getDefault().unregister(this);
                } else {//删除文件重新下载
                    if (downloadManager != null && downloadInfo != null) {
                        ToastUtil.showToast(context, result.getMsg());
                        SharedPreference.getInstance().saveBoolean(context, PreferenceCongfig.APP_DOWN_LOAD_COMPULTE, false);
                        downloadManager.removeTask(downloadInfo.getTaskKey(), true);
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });

    }

    /**
     * 安装APK
     */
    public void installAPK(File file) {

        Uri uri = null;
        String type;
        if (file != null && file.exists()) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                uri = Uri.fromFile(file);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            } else {
                uri = FileProvider.getUriForFile(context, "cn.udesk.provider", file);//通过FileProvider创建一个content类型的Uri
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            }
            if (Build.VERSION.SDK_INT < 23) {
                type = "application/vnd.android.package-archive";
            } else {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(FileUtil.getFileExtension(file));
            }
            intent.setDataAndType(uri, type);
            ((Activity) context).startActivity(intent);

            ALLog.e("安装apk");
        } else {
            Toast.makeText(context, context.getString(R.string.install_fail), Toast.LENGTH_LONG).show();
        }
        EventBus.getDefault().unregister(this);
    }

    /**
     * @param baseEvent void
     * @auther lion
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent baseEvent) {
        ALLog.e("============update_envent");
        if (baseEvent != null) {
            if (baseEvent instanceof ListenNetEvent) {
                if (downloadManager != null && downloadInfo != null && normalLeftDialog != null && !isTop) {
                    downloadManager.addTask(downloadInfo.getUrl(), downloadInfo.getRequest(), downloadInfo.getListener());
                    normalLeftDialog.setRightBtnTxt("暂停下载");
                }
            }
        }
    }
}
