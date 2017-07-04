/**
 * 
 */
package com.example.administrator.lmw.config;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;

import com.example.administrator.lmw.utils.ALLog;

import java.io.File;
import java.io.IOException;

/**
 * 应用程序配置文件。服务器配置信息，调试信息
 * 
 * @author alonso lion
 * 
 */
public class VersionConfig {

	// 图片保存路径
	public static String IMAGE_PATH = "images";
	// 语音保存路径


	public static String AUDIO_PATH = "audio";
	// 视频保存路径
	public static String VIDIO_PATH = "video";

	// 更新安装包
	public static String UPDATE_APK_PATH = "apks";
	// 启动界面
	public static String SPLASH_PATH = "splash";

	public static final String LMW_DIR = "liminw";


	
	/**
	 * 
	 */
	public VersionConfig(Context context, boolean isDebug) {
		// TODO Auto-generated constructor stub

		checkDir(context);
		// 设置日志输出功能
		ALLog.setDebug(isDebug);
	}

	private void checkDir(Context context) {
		IMAGE_PATH = getExternalCacheDir(context, LMW_DIR, IMAGE_PATH);
		AUDIO_PATH = getExternalCacheDir(context, LMW_DIR, AUDIO_PATH);
		UPDATE_APK_PATH = getExternalCacheDir(context, LMW_DIR,
				UPDATE_APK_PATH);
		SPLASH_PATH = getExternalCacheDir(context, LMW_DIR, SPLASH_PATH);
		VIDIO_PATH=getExternalCacheDir(context, LMW_DIR, VIDIO_PATH);
	}

	private String getExternalCacheDir(Context context, String path, String dir) {
		File dataDir = new File(Environment.getExternalStorageDirectory(), path);
		File appCacheDir = new File(dataDir, dir);
		if (!appCacheDir.exists()) {
			if (!appCacheDir.mkdirs()) {
				ALLog.w("Unable to create external cache directory");
				return null;
			}
			try {
				new File(appCacheDir, ".nomedia").createNewFile();
			} catch (IOException e) {
				ALLog.i("Can't create \".nomedia\" file in application external cache directory");
			}
		}
		return appCacheDir.getAbsolutePath();
	}


}
