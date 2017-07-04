package com.example.administrator.lmw.mine.seting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.DespositAccountManager;
import com.example.administrator.lmw.config.DespositOperate;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.login.UserInfoUtils;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.login.entity.UserInfoBean;
import com.example.administrator.lmw.mine.seting.entity.SetDataBean;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.view.SettingNextItemNotIconView;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lion on 2016/8/24.
 */
public class UserInfoActivity extends BaseActivity {
    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.title_layout)
    RelativeLayout title_layout;
    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.back_layout)
    LinearLayout backLayout;
    @InjectView(R.id.name_set)
    SettingNextItemNotIconView nameSet;
    @InjectView(R.id.phone_set)
    SettingNextItemNotIconView phone_set;
    @InjectView(R.id.bank_set)
    SettingNextItemNotIconView bank_set;
    private Intent intent;
    private SetDataBean setDataBean;
    private UserInfo userInfo;
    private UserInfoBean userInfoBean;

    @Override
    protected void initializeData() {
        inintPara();
    }

    private void inintPara() {
        intent = getIntent();
        if (intent.hasExtra(SetingActivity.SET_DATA))
            setDataBean = (SetDataBean) intent.getSerializableExtra(SetingActivity.SET_DATA);
        userInfo = UserInfoUtils.getInstance().getUserInfo(this);
        if (userInfo != null)
            userInfoBean = userInfo.getData();
    }


    @Override
    protected int getContentLayout() {
        return R.layout.activity_user_info_layout;
    }


    @Override
    protected void initializeView() {
        initBarTitle();
        if (setDataBean != null)
            setView();
    }

    private void setView() {

        if (!TextUtils.isEmpty(setDataBean.getCustomerSurname()))
            nameSet.setHintTxt(setDataBean.getCustomerSurname());
        if (!TextUtils.isEmpty(setDataBean.getMobile()))
            phone_set.setHintTxt(setDataBean.getMobile());


        bank_set.setOnRightTextOnclick(new SettingNextItemNotIconView.RigthTextViewListener() {

            @Override
            public void onRightTextView(TextView v) {
                if (PreferenceCongfig.IS_BIND_BANKCARD == userInfoBean.getIsBindBankCard()) {//已绑卡
                    if (!TextUtils.isEmpty(setDataBean.getBankName()) && !TextUtils.isEmpty(setDataBean.getBankCardNo()))
                        v.setText(String.format(getString(R.string.set_bind_card),
                                setDataBean.getBankName(), setDataBean.getBankCardNo()));
                } else if (PreferenceCongfig.IS_NOT_BIND_BANKCARD == userInfoBean.getIsBindBankCard()
                        || PreferenceCongfig.IS_REBIND_BANKCARD == userInfoBean.getIsBindBankCard())
                    v.setText("未绑卡");
            }

            @Override
            public void onClick(View v) {
                if (PreferenceCongfig.IS_BIND_BANKCARD == userInfoBean.getIsBindBankCard()) {//已绑卡
                    //跳到已绑卡界面
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(SetingActivity.SET_DATA, setDataBean);
                    ActivityTools.switchActivity(UserInfoActivity.this, BindBankSuccussActivity.class, bundle);
                } else if (PreferenceCongfig.IS_NOT_BIND_BANKCARD == userInfoBean.getIsBindBankCard()
                        || PreferenceCongfig.IS_REBIND_BANKCARD == userInfoBean.getIsBindBankCard()) {//需重新绑卡或者未绑卡//跳到绑卡界面
                    // startActivity(BindBankActivity.class);
                    Singlton.getInstance(DespositAccountManager.class).despositOperate(UserInfoActivity.this, DespositOperate.BIND_BANKCARD);

                }

            }
        });

    }

    private void initBarTitle() {
        barTitle.setText("账户信息");
        barIconBack.setImageResource(R.drawable.nav_back);
        title_layout.setBackgroundColor(getResources().getColor(R.color.text_blue));

    }

    @OnClick({R.id.back_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                finishActivity(this);
                break;
        }
    }

    private void bindBank() {
     /*   Intent intent = new Intent(PreferenceCongfig.ACTION_BIND_BANKCARD);
        intent.setClass(mContext, BindBankActivity.class);
        startActivity(intent);*/
        if (userInfoBean != null)
            Singlton.getInstance(DespositAccountManager.class).
                    openBankDepository(this, userInfoBean.getOpenDpStatus());
    }
}