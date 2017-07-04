package com.example.administrator.lmw.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResponse;
import com.example.administrator.lmw.entity.FreshFloatIconEvent;
import com.example.administrator.lmw.entity.FreshMessageEvent;
import com.example.administrator.lmw.entity.FreshUseInfoEvent;
import com.example.administrator.lmw.entity.NotificationEntity;
import com.example.administrator.lmw.finance.activity.DetailActivity;
import com.example.administrator.lmw.finance.activity.DetailProductActivity;
import com.example.administrator.lmw.finance.activity.ProductItem;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.entity.LoginBean;
import com.example.administrator.lmw.login.entity.LoginEntity;
import com.example.administrator.lmw.login.entity.MineData;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.login.entity.UserInfoBean;
import com.example.administrator.lmw.main.MainActivity;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.mine.seting.SetLogic;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.syd.oden.gesturelock.view.GestureLockViewGroup;
import com.syd.oden.gesturelock.view.listener.GestureEventListener;
import com.syd.oden.gesturelock.view.listener.GestureUnmatchedExceedListener;

import org.greenrobot.eventbus.EventBus;

import butterknife.InjectView;
import butterknife.OnClick;

public class GesturePswLoginActivity extends BaseActivity {
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.login)
    TextView login;
    @InjectView(R.id.fg_psw_tv)
    TextView fg_psw_tv;
    @InjectView(R.id.tv_state)
    TextView tv_state;
    @InjectView(R.id.gesturelock)
    GestureLockViewGroup mGestureLockViewGroup;
    private Intent intent = null;
    private Intent newIntent = null;
    private NotificationEntity notificationEntity;
    private UserInfoBean userInfoBean;
    private UserInfo userInfo;
    private String phone;
    private String psw;
    private String token;

    @Override
    protected void initializeData() {
        SharedPreference.getInstance().saveBoolean(mContext, PreferenceCongfig.IS_SHOW_CURRENT_ACTIVITY_FOR_GESTURE_LOGIN, true);
        phone = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_ACCOUNT, "");//账号
        psw = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_PSW, "");//密码
        intent = getIntent();
        userInfo = UserInfoUtils.getInstance().getUserInfo(mContext);
        if (userInfo != null)
            userInfoBean = userInfo.getData();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_gesture_passwoad_login;
    }

    @Override
    protected void initializeView() {
        initGesture();
        if (userInfoBean != null)
            setView();
    }

    private void setView() {
        if (!TextUtils.isEmpty(userInfoBean.getCustomerRespectName()))
            tv_name.setText(String.format(getString(R.string.name_formate), userInfoBean.getCustomerRespectName().subSequence(0, 1)));
        else if (!TextUtils.isEmpty(userInfoBean.getMobile()))
            tv_name.setText(userInfoBean.getMobile());
    }

    private void initGesture() {
        gestureEventListener();
        gestureRetryLimitListener();
    }

    /**
     * 验证手势密码
     */
    private void gestureEventListener() {
        mGestureLockViewGroup.setGestureEventListener(new GestureEventListener() {
            @Override
            public void onGestureEvent(boolean matched) {
                if (!matched) {
                    tv_state.setTextColor(Color.RED);
                    tv_state.setText("手势密码错误");
                    mGestureLockViewGroup.resetView();
                } else {
                    tv_state.setTextColor(getResources().getColor(R.color.text_blue));
                    tv_state.setText("手势密码正确");
                    mGestureLockViewGroup.resetView();
                    SharedPreference.getInstance().saveBoolean(mContext, PreferenceCongfig.IS_SHOW_CURRENT_ACTIVITY_FOR_GESTURE_LOGIN, false);
                    if (newIntent != null && notificationEntity != null) {
                        handleNoticeEvent();
                    } else if (intent != null && intent.hasExtra(PreferenceCongfig.APP_AUTO_LOGIN)) {
                        Bundle bundle = new Bundle();
                        bundle.putString(PreferenceCongfig.APP_AUTO_LOGIN, "auto_login");
                        ActivityTools.switchActivity(GesturePswLoginActivity.this,MainActivity.class , bundle);
                     //   startActivity(MainActivity.class);
                    } else {// 手势密码解锁
                    //  login();
                        EventBus.getDefault().post(new FreshUseInfoEvent(FreshUseInfoEvent.IS_GESTUREPSW_LOGIN));
                        finishActivity(GesturePswLoginActivity.this);
                    }

                }
            }
        });
    }

    private void handleNoticeEvent() {
        int type = -1;
        if (!TextUtils.isEmpty(notificationEntity.getType())) {
            type = Integer.valueOf(notificationEntity.getType());
        }
//type:// 类型 (0:发布产品 1:活动 2：公告 3:指定跳转url 4：定期宝子列表 页 5:散标子列表页 )
        Intent i = null;
        if (intent != null && intent.hasExtra(PreferenceCongfig.APP_AUTO_LOGIN))
            startActivity(MainActivity.class);
        switch (type) {
            case 0://发新标（定期宝、散标）跳到详情列表
                i = new Intent(this, DetailProductActivity.class);
                i.putExtra("subjectId", notificationEntity.getId());// 标的Id
                i.putExtra("type", "0");//0标1债权
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                ActivityManage.create().finishActivity(DetailProductActivity.class);
                finishActivity(this);
                break;
            case 1://活动页
            case 2://公告页
            case 3://指定页

                i = new Intent(this, WebViewMore.class);
                i.putExtra("title", notificationEntity.getTitle());
                i.putExtra("url", notificationEntity.getLinkUrl());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                ActivityManage.create().finishActivity(WebViewMore.class);
                finishActivity(this);
                break;
            case 4://定期宝子列表
            case 5://散标子列表页
                i = new Intent(this, ProductItem.class);
                i.putExtra("type", notificationEntity.getCateId());//类型ID
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                ActivityManage.create().finishActivity(ProductItem.class);
                finishActivity(this);
                break;
            case 6://发新标（债券标）跳到详情列表
                i = new Intent(this, DetailActivity.class);
                i.putExtra("subjectId", notificationEntity.getId());// 债权的Id
                i.putExtra("type", "1");//0标1债权
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                ActivityManage.create().finishActivity(DetailActivity.class);
                finishActivity(this);
                break;

        }

    }



    /**
     * 手势密码输入错误超限提示
     */
    private void gestureRetryLimitListener() {
        mGestureLockViewGroup.setGestureUnmatchedExceedListener(4, new GestureUnmatchedExceedListener() {
            @Override
            public void onUnmatchedExceedBoundary(int mTryTimes) {
                tv_state.setTextColor(Color.RED);
                switch (mTryTimes) {
                    case 1:
                        tv_state.setText("最后的机会咯，4次失败后将跳转至登录页面重新登录");
                        break;
                    case 2:
                    case 3:
                        tv_state.setText(String.format(getString(R.string.gesture_cw), mTryTimes + ""));
                        break;
                    case 0:
                        Singlton.getInstance(SetLogic.class).logout(mContext, new OnResponseListener<BaseResponse>() {
                            @Override
                            public void onSuccess(BaseResponse response) {
                                int code = Integer.valueOf(response.code);
                                if (code == 0) {
                                    UserInfoUtils.getInstance().clearMineData(GesturePswLoginActivity.this);
                                    UserInfoUtils.getInstance().clearUserInfo(GesturePswLoginActivity.this);
                                    SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_TOKEN, "");
                                    SharedPreference.getInstance().saveString(mContext, PreferenceCongfig.APP_PSW, "");
                                    PreferenceCongfig.isLogin = false;
                                    ActivityManage.create().finishAllActivity();
                                    startActivity(LoginActivity.class);
                                }
                            }

                            @Override
                            public void onFail(Throwable e) {

                            }
                        });
                        break;
                }
            }

            @Override
            public void onUnmatchedLessFourPoint(int length) {
                if (length < 4) {
                    tv_state.setTextColor(Color.RED);
                    tv_state.setText("请至少连接四个点");
                }

            }
        });
    }

    @OnClick({R.id.login, R.id.fg_psw_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fg_psw_tv:
               /* Bundle bundle = new Bundle();
                bundle.putInt(PreferenceCongfig.RESET_GESTURE_PSW, PreferenceCongfig.reset_gesture_psw);
                ActivityTools.switchActivity(GesturePswLoginActivity.this, ResetTradPswVerifyActivity.class, bundle);*/
                startActivity(LoginActivity.class);
                break;
            case R.id.login:
                startActivity(LoginActivity.class);
                break;


        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            return true;
        }

        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        newIntent = intent;
        ALLog.e("=======================newIntent");
        notificationEntity = (NotificationEntity) intent.getSerializableExtra(PreferenceCongfig.NOTICE_CONTENT);
    }
}
