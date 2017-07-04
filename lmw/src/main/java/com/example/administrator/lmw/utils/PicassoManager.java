package com.example.administrator.lmw.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.io.File;

/**
 * Created by lion on 2016/8/19.
 */
public class PicassoManager {

    private static PicassoManager instance;

    private PicassoManager() {
    }

    public static PicassoManager getInstance() {
        if (instance == null) {
            synchronized (PicassoManager.class) {
                if (instance == null) {
                    instance = new PicassoManager();
                }
            }
        }

        return instance;
    }
    /**
     * Created by lion on 2016/8/19.
     *@param context
     *@param imageView 显示的目标imageview
     *@param  url 要加载的图片url
     *
     */
    public void displayByFixSize(Context context, ImageView imageView, String url) {
        Picasso.with(context)
                .load(url)
                .resize(50, 50)
                .centerCrop()
                .into(imageView);

    }
    /**
     * Created by lion on 2016/8/19.
     *@param context
     *@param imageView 显示的目标imageview
     *@param  url 要加载的图片url
     *
     */
    public void display(Context context, ImageView imageView, String url) {
        Picasso.with(context)
                .load(url)
                .into(imageView);

    }
    /**
     * Created by lion on 2016/8/19.
     *@param context
     *@param imageView 显示的目标imageview
     *@param  url 要加载的图片url
     *@param  callback 要加载的图片回调
     *
     */
    public void display(Context context, ImageView imageView, String url, Callback  callback) {
        Picasso.with(context)
                .load(url)
                .into(imageView,callback);

    }
    /**
     * Created by lion on 2016/8/19.
     *@param context
     *@param imageView 显示的目标imageview
     *@param drawable 要加载的图片drawable对象id
     *
     */
    public void display(Context context, ImageView imageView, int drawable) {
        Picasso.with(context)
                .load(drawable)
                .into(imageView);

    }
    /**
     * Created by lion on 2016/8/19.
     *@param context
     *@param imageView 显示的目标imageview
     *@param preload 加载前要显示的图片drawable对象id
     *@param loaderror 要加载的图片drawable对象id
     *
     */
    public void display(Context context, ImageView imageView, String url,int preload,int loaderror) {
        Picasso.with(context)
                .load(url)
                .placeholder(preload)
                .error(loaderror)
                .into(imageView);

    }
    /**
     * Created by lion on 2016/8/19.
     *@param context
     *@param imageView 显示的目标imageview
     *@param  file 要加载的图片文件路径
     *@param
     *
     */
    public void display(Context context, ImageView imageView, File file) {
        Picasso.with(context)
                .load(file)
                .into(imageView);

    }


}
