package com.example.administrator.lmw.mine.credit;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.DesposityResultManager;
import com.example.administrator.lmw.main.MainActivity;
import com.example.administrator.lmw.mine.credit.Entity.CreditResultBean;
import com.example.administrator.lmw.mine.fill.FillActivity;
import com.example.administrator.lmw.mine.seting.BindBankSuccussActivity;
import com.example.administrator.lmw.mine.seting.DespositOperateSuccessActivity;
import com.example.administrator.lmw.mine.seting.SetingActivity;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.StringUtils;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lion on 2016/9/9.
 */
public class CreditResultActivity extends BaseActivity {

    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.title_layout)
    RelativeLayout title_layout;
    @InjectView(R.id.credit_num_tv)
    TextView credit_num_tv;
    @InjectView(R.id.credit_time)
    TextView credit_time;
    @InjectView(R.id.to_account_time)
    TextView to_account_time;
    @InjectView(R.id.credit_rate_tv)
    TextView credit_rate_tv;
    @InjectView(R.id.myAccount_bt)
    Button myAccount_bt;
    private String creditNumber;
    private Intent intent;
    private String poundage;//提现手续费率
    private String creditTime;//提现时间
    private String arraveTime;//提现到账时间
    private int state;
    private String creditNo;
    private CreditResultBean creditResultBean;


    @Override
    protected void initializeData() {
        intent = getIntent();
        if (intent.hasExtra(DesposityResultManager.CREDIT_RESULT)) {
            creditResultBean = (CreditResultBean) intent.getSerializableExtra(DesposityResultManager.CREDIT_RESULT);
        }
        if (creditResultBean != null) {
            intPara();
        }
    }

    private void intPara() {
        if (!TextUtils.isEmpty(creditResultBean.getAmount()))
            creditNumber = creditResultBean.getAmount();
        if (!TextUtils.isEmpty(creditResultBean.getPoundage()))
            poundage = creditResultBean.getPoundage();
        if (!TextUtils.isEmpty(creditResultBean.getApplyTime()))
            creditTime = creditResultBean.getApplyTime();
        if (!TextUtils.isEmpty(creditResultBean.getArrivalTime()))
            arraveTime = creditResultBean.getArrivalTime();
        if (!TextUtils.isEmpty(creditResultBean.getWithdrawalOrderNo()))
            creditNo = creditResultBean.getWithdrawalOrderNo();
        state = creditResultBean.getState();

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_credit_result_layout;
    }

    @Override
    protected void initializeView() {
        initBarTitle();
        setView();
    }

    private void setView() {
        credit_num_tv.setText(String.format(getString(R.string.credit), creditNumber));
        credit_time.setText(creditTime);
        to_account_time.setText(arraveTime);
        if (poundage.equals("0"))
            credit_rate_tv.setText("本次提现手续费利民君代付");
        else
            // credit_rate_tv.setText(String.format(getString(R.string.credit_rate), poundage));
            credit_rate_tv.setText(StringUtils.changeColor("本次提现费用为", poundage + "", "元", "f99709"));
    }

    private void initBarTitle() {
        barIconBack.setImageResource(R.drawable.nav_back);
        title_layout.setBackgroundColor(getResources().getColor(R.color.text_blue));
        barTitle.setText("提现申请成功");
    }

    @OnClick({R.id.back_layout, R.id.myAccount_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                ActivityManage.create().finishActivity();
                ActivityManage.create().finishActivity(CreditActivity.class);
                break;
            case R.id.myAccount_bt:
                startActivity(MainActivity.class);
                break;
        }
    }

}
