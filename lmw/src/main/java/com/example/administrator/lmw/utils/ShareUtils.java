package com.example.administrator.lmw.utils;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.view.ShareDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * 分享工具类
 * <p>
 * Created by Administrator on 2017/4/14.
 */

public class ShareUtils {

    private Activity context;
    private String imageUrl, shareContent, shareTitle, targetUrl;

    /**
     * @param context      弹窗的activity
     * @param imageUrl     图片链接
     * @param shareContent 分享内容
     * @param shareTitle   分享标题
     * @param targetUrl    分享连接
     */
    public void getShareUtils(Activity context, String imageUrl, String shareContent, String shareTitle, String targetUrl) {
        this.context = context;
        this.imageUrl = imageUrl;
        this.shareContent = shareContent;
        this.shareTitle = shareTitle;
        this.targetUrl = targetUrl;
        shareTools();

    }

    private void shareTools() {
        final UMImage image = new UMImage(context, imageUrl);//资源文件
        new ShareDialog(context, new OnDialogClickListener() {
            @Override
            public void onClick(int id, View v) {
                if (id == 0) {//分享朋友圈
                    new ShareAction(context)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .setCallback(umShareListener)
                            .withTargetUrl(targetUrl)
                            .withMedia(image)
                            .withText(shareContent)
                            .withTitle(shareTitle)
                            .share();
                } else if (id == 1) {//分享微信好友
                    new ShareAction(context)
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .setCallback(umShareListener)
                            .withTargetUrl(targetUrl)
                            .withText(shareContent)
                            .withMedia(image)
                            .withTitle(shareTitle)
                            .share();
                } else if (id == 2) {//分享联系人发短信
                    new ShareAction(context)
                            .setPlatform(SHARE_MEDIA.SMS)
                            .setCallback(umShareListener)
                            .withText(shareTitle + "\n" + shareContent + "\n" + targetUrl)
                            .withTitle(shareTitle)
                            .share();
                } else if (id == 3) {// 分享新浪
                    new ShareAction(context)
                            .setPlatform(SHARE_MEDIA.SINA)
                            .setCallback(umShareListener)
                            .withTargetUrl(targetUrl)
                            .withText(shareTitle + "，" + shareContent)
                            .withTitle(shareTitle)
                            .withMedia(image)
                            .share();
                } else if (id == 4) {// 分享QQ
                    new ShareAction(context)
                            .setPlatform(SHARE_MEDIA.QQ)
                            .setCallback(umShareListener)
                            .withTargetUrl(targetUrl)
                            .withText(shareContent)
                            .withMedia(image)
                            .withTitle(shareTitle)
                            .share();
                }
            }
        });
    }

    private UMShareListener umShareListener = new UMShareListener() {

        @Override
        public void onResult(SHARE_MEDIA platform) {
            com.umeng.socialize.utils.Log.d("plat", "platform" + platform);
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(context, platform + " FDF�", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            if (platform == SHARE_MEDIA.QQ || platform == SHARE_MEDIA.WEIXIN || platform == SHARE_MEDIA.WEIXIN_CIRCLE)
//                if (t.toString().contains("2008"))
//                    Toast.makeText(context, platform + " 没有安装应用", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.i("snubee", "分享失败throw:" + t.getMessage());
                Toast.makeText(context, "分享失败: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(context, "分享取消", Toast.LENGTH_SHORT).show();
        }
    };

// 需要在activity中添加否则无法分享
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }*/

}
