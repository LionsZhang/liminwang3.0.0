package com.example.administrator.lmw.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

import com.example.administrator.lmw.R;

/**
 * Created by lion on 2016/10/22.
 */

public class AnimationUtil {
    private static AnimationUtil instance;

    private AnimationUtil() {
    }

    public static AnimationUtil getInstance() {
        if (instance == null) {
            synchronized (AnimationUtil.class) {
                if (instance == null) {
                    instance = new AnimationUtil();
                }
            }
        }

        return instance;
    }
    private boolean isUpCompute=true;
    private boolean isDownCompute=true;
    public void startTitleBarHideAnimal(Context mContext,final View titleView,final View contentView) {
        if (titleView==null||mContext==null||contentView==null)
            return;
        if (titleView.getVisibility()==View.VISIBLE&&isDownCompute&&isUpCompute){
            isUpCompute=false;
          Animation outAnim = AnimationUtils.loadAnimation(mContext, R.anim.title_fade_out_center);
            titleView.startAnimation(outAnim);
            ALLog.e("titleView.getHeight()"+titleView.getHeight());
            ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(contentView, View.TRANSLATION_Y,-titleView.getHeight());
          translationAnimator.setDuration(1000);
           translationAnimator.start();
            translationAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    titleView.setVisibility(View.INVISIBLE);
                    isUpCompute=true;
                    ALLog.e("==========up");
                }
            });
            translationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    contentView.setTranslationY((Float) animation.getAnimatedValue());
                    ALLog.e("==========up111");
                }
            });
        }



    }
    public void startTitleBarShowAnimal(Context mContext,final View titleView,final View contentView) {
        if (titleView==null||mContext==null||contentView==null)
            return;
        if (titleView.getVisibility()==View.INVISIBLE&&isDownCompute&&isUpCompute){
            isDownCompute=false;
           Animation inAnim = AnimationUtils.loadAnimation(mContext, R.anim.title_fade_in_center);
            titleView.startAnimation(inAnim);
            ALLog.e("titleView.getHeight()" + titleView.getHeight());
        final     ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(contentView, View.TRANSLATION_Y,0);
            translationAnimator.setDuration(1000);
            translationAnimator.start();
            translationAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    titleView.setVisibility(View.VISIBLE);
                    isDownCompute=true;
                    ALLog.e("==========down");
                }
            });
            translationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    contentView.setTranslationY((Float) animation.getAnimatedValue());
                    ALLog.e("==========down11");
                }
            });
        }
    }
}
