package com.example.administrator.lmw.mine.seting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResponse;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.view.PasswardEditText;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lion on 2016/9/2.
 */
public class MotifyExchargePswVerify extends BaseActivity implements PasswardEditText.OnEditTextListener{
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
    @Override
    protected void initializeData() {

    }

    @Override
    protected int getContentLayout() {
        return R.layout.verify_old_excharge_psw_layout;
    }

    @Override
    protected void initializeView() {
        initBarTitle();
        passwardEditText.setOnEditTextListener(this);
    }

    private void initBarTitle() {
            barTitle.setText("验证原交易密码");
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

    /**
     * @param baseEvent void
     * @auther lion
     */
    private String oldPsw;
    @Override
    public void inputComplete(int state, String password) {
        oldPsw=password;
        verifyOldPsw();
    }
/**
 * 验证原密码
 *
 * */
    private void verifyOldPsw() {
        showLoadingDialog();
        Singlton.getInstance(SetLogic.class).verifyOldTradePsw(this, oldPsw, new OnResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                hideLoadingDialog();
                int code=Integer.valueOf(response.code);
                if (code==0){
                    Bundle bundle = new Bundle();
                    bundle.putInt(PreferenceCongfig.MOTIFY_EXCHARGE_PSW, PreferenceCongfig.motify_excharge_psw);
                    bundle.putString(PreferenceCongfig.MOTIFY_EXCHARGE_OLD_PSW, oldPsw);
                    ActivityTools.switchActivity(MotifyExchargePswVerify.this, SetExchargePsw.class, bundle);
                }else
                    showToast(response.msg);
                passwardEditText.clearEditText();
            }

            @Override
            public void onFail(Throwable e) {
                hideLoadingDialog();
                passwardEditText.clearEditText();
            }
        });

    }


}
