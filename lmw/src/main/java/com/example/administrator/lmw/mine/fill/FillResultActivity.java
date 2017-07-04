package com.example.administrator.lmw.mine.fill;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.DesposityResultManager;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.ShowMineFragmentEvent;
import com.example.administrator.lmw.main.MainActivity;
import com.example.administrator.lmw.mine.account.AccountActivity;
import com.example.administrator.lmw.mine.credit.CreditActivity;
import com.example.administrator.lmw.mine.fill.entity.FillResultBean;
import com.example.administrator.lmw.mine.seting.AccountManageActivity;
import com.example.administrator.lmw.mine.seting.BindBankSuccussActivity;
import com.example.administrator.lmw.mine.seting.DespositOperateSuccessActivity;
import com.example.administrator.lmw.mine.seting.SetingActivity;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.ActivityTools;

import org.greenrobot.eventbus.EventBus;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lion on 2016/9/9.
 */
public class FillResultActivity extends BaseActivity {

    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.title_layout)
    RelativeLayout title_layout;
    @InjectView(R.id.invest_bt)
    Button invest_bt;
    @InjectView(R.id.record_bt)
    Button record_bt;
    @InjectView(R.id.fill_num_tv)
    TextView fill_num_tv;
    @InjectView(R.id.fill)
    TextView fill;
    @InjectView(R.id.fill_statu_tv)
    TextView fill_statu_tv;
    private String fillNumber;
    private String fillResultInfo;
    private Intent intent;
    private String fillStatu;
    private FillResultBean fillResultBean;

    @Override
    protected void initializeData() {
        intent = getIntent();
        if (intent.hasExtra(DesposityResultManager.FILL_RESULT)) {
            fillResultBean = (FillResultBean) intent.getSerializableExtra(DesposityResultManager.FILL_RESULT);
        }
        if (fillResultBean != null) {
            fillNumber = fillResultBean.getAmount();
            fillStatu = fillResultBean.getResult();
            fillResultInfo = fillResultBean.getResultMsg();
        }
    /*    if (intent.hasExtra(FillActivity.FILL_NUMBER)) {
            fillNumber = intent.getStringExtra(FillActivity.FILL_NUMBER);
        }
        if (intent.hasExtra(FillConfirmActivity.FILL_STATU)) {
            fillStatu = intent.getIntExtra(FillConfirmActivity.FILL_STATU, -1);
        }
        if (intent.hasExtra(FillConfirmActivity.FILL_STATU)) {
            fillFailInfo = intent.getStringExtra(FillConfirmActivity.FAIL_INFO);
        }*/
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_fill_result_layout;
    }

    @Override
    protected void initializeView() {
        initBarTitle();

    }

    private void initBarTitle() {
        barIconBack.setImageResource(R.drawable.nav_back);
        title_layout.setBackgroundColor(getResources().getColor(R.color.text_blue));
        if (TextUtils.isEmpty(fillStatu))
            return;
        barTitle.setText("充值");
        fill_statu_tv.setText(fillResultInfo);
        if (TextUtils.equals(fillStatu, "1")) {//充值成功
            //  fill_statu_tv.setText("充值成功");
            fill.setText("您已成功充值");
            fill_num_tv.setText(String.format(getString(R.string.credit), fillNumber));
            fill_num_tv.setVisibility(View.VISIBLE);
            invest_bt.setText("去投资");
        } else if (TextUtils.equals(fillStatu, "2")) {//充值失败
            /// fill_statu_tv.setText(String.format(getString(R.string.fill_fail), fillFailInfo));
            fill.setText("您可以继续充值");
            invest_bt.setText("继续充值");
            fill_num_tv.setVisibility(View.GONE);
        } else if (TextUtils.equals(fillStatu, "0")) {//充值处理中
            //  fill_statu_tv.setText(String.format(getString(R.string.fill_fail), fillFailInfo));
            // fill_statu_tv.setText(fillResultInfo);
            fill.setText("充值金额");
            invest_bt.setText("返回我的账户");
            fill_num_tv.setText(String.format(getString(R.string.credit), fillNumber));
        }

    }

    @OnClick({R.id.back_layout, R.id.invest_bt, R.id.record_bt})
    public void onClick(View view) {
        Bundle bundle = null;
        switch (view.getId()) {
            case R.id.back_layout:
                ActivityManage.create().finishActivity();
                ActivityManage.create().finishActivity(AccountManageActivity.class);
                ActivityManage.create().finishActivity(SetingActivity.class);
                ActivityManage.create().finishActivity(FillActivity.class);
                ActivityManage.create().finishActivity(DespositOperateSuccessActivity.class);
                break;
            case R.id.record_bt:
                if (bundle == null)
                    bundle = new Bundle();
                bundle.putInt(PreferenceCongfig.FILL_RESULT_TO_ACCOUNT_DETAIL_FILL, PreferenceCongfig.fill_result_to_account_detail_fill);
                ActivityTools.switchActivity(FillResultActivity.this, AccountActivity.class, bundle);
                break;
            case R.id.invest_bt:
                if (TextUtils.isEmpty(fillStatu))
                    return;
                if (TextUtils.equals(fillStatu, "1")) {//充值成功去投资
                    startActivity(MainActivity.class);

                } else if (TextUtils.equals(fillStatu, "2")) {//充值失败继续充值
                    startActivity(FillActivity.class);
                } else if (TextUtils.equals(fillStatu, "0")) {//充值处理中
                    startActivity(MainActivity.class);
                    EventBus.getDefault().post(new ShowMineFragmentEvent());
                }
                break;
        }
    }

}
