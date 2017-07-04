package com.example.administrator.lmw.mine.seting;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.view.PasswardEditText;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lion on 2016/9/2.
 */
public class SetExchargePsw extends BaseActivity implements PasswardEditText.OnEditTextListener {

    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.title_layout)
    RelativeLayout title_layout;
    @InjectView(R.id.right_title)
    TextView right_title;
    @InjectView(R.id.title_bar_right_layout)
    LinearLayout title_bar_right_layout;
    @InjectView(R.id.psw_et)
    PasswardEditText passwardEditText;
    @InjectView(R.id.back_layout)
    LinearLayout backLayout;
    private Intent intent;
    private int type = -1;
    private String verifyCode;
    private String account;
    private String oldTradingPsw;
    private boolean isNewUser = false;//是否是新用户


    @Override
    protected void initializeData() {
        intent = getIntent();

        if (intent.hasExtra(PreferenceCongfig.RESET_EXCHARGE_PSW))
            type = intent.getIntExtra(PreferenceCongfig.RESET_EXCHARGE_PSW, -1);
        if (intent.hasExtra(PreferenceCongfig.SET_EXCHARGE_PSW))
            type = intent.getIntExtra(PreferenceCongfig.SET_EXCHARGE_PSW, -1);
        if (intent.hasExtra(PreferenceCongfig.MOTIFY_EXCHARGE_PSW))
            type = intent.getIntExtra(PreferenceCongfig.MOTIFY_EXCHARGE_PSW, -1);
        if (intent.hasExtra(PreferenceCongfig.VERIFY_CODE))
            verifyCode = intent.getStringExtra(PreferenceCongfig.VERIFY_CODE);
        if (intent.hasExtra(PreferenceCongfig.PHONE))
            account = intent.getStringExtra(PreferenceCongfig.PHONE);
        if (intent.hasExtra(PreferenceCongfig.MOTIFY_EXCHARGE_OLD_PSW))
            oldTradingPsw = intent.getStringExtra(PreferenceCongfig.MOTIFY_EXCHARGE_OLD_PSW);
        if (intent.hasExtra(PreferenceCongfig.KEY_NEW_USER_SET_PW))
            isNewUser = intent.getBooleanExtra(PreferenceCongfig.KEY_NEW_USER_SET_PW, false);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.set_excharge_psw_layout;
    }

    @Override
    protected void initializeView() {
        initBarTitle();
        passwardEditText.setOnEditTextListener(this);

    }

    private void initBarTitle() {
        if (type == PreferenceCongfig.reset_excharge_psw) {
            barTitle.setText("重置交易密码");
            backLayout.setVisibility(View.VISIBLE);
        }

        if (type == PreferenceCongfig.set_excharge_psw) {
            barTitle.setText("设置交易密码");
            backLayout.setVisibility(View.GONE);
        }

        if (type == PreferenceCongfig.motify_excharge_psw) {
            barTitle.setText("修改交易密码");
            backLayout.setVisibility(View.VISIBLE);
        }

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (type == PreferenceCongfig.motify_excharge_psw || type == PreferenceCongfig.reset_excharge_psw)
                finishActivity(SetExchargePsw.this);

            return true;
        }

        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void inputComplete(int state, String password) {
        Bundle bundle = new Bundle();
        switch (type) {
            case PreferenceCongfig.reset_excharge_psw:
                bundle.putInt(PreferenceCongfig.RESET_EXCHARGE_PSW, PreferenceCongfig.reset_excharge_psw);
                bundle.putString(PreferenceCongfig.PHONE, account);
                bundle.putString(PreferenceCongfig.VERIFY_CODE, verifyCode);
                break;
            case PreferenceCongfig.set_excharge_psw:
                bundle.putString(PreferenceCongfig.PHONE, account);
                bundle.putString(PreferenceCongfig.VERIFY_CODE, verifyCode);
                bundle.putInt(PreferenceCongfig.SET_EXCHARGE_PSW, PreferenceCongfig.set_excharge_psw);
                bundle.putBoolean(PreferenceCongfig.KEY_NEW_USER_SET_PW, isNewUser);
                break;
            case PreferenceCongfig.motify_excharge_psw:
                bundle.putInt(PreferenceCongfig.MOTIFY_EXCHARGE_PSW, PreferenceCongfig.motify_excharge_psw);
                bundle.putString(PreferenceCongfig.MOTIFY_EXCHARGE_OLD_PSW, oldTradingPsw);
                break;
        }
        bundle.putString(PreferenceCongfig.FIRST_INPUT_EXCHARGE_PSW, password);
        ActivityTools.switchActivity(this, ConfirmExchargePsw.class, bundle);
        passwardEditText.clearEditText();
    }
}
