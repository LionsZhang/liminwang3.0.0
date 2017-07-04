package com.example.administrator.lmw.mine.seting;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.DespositAccountManager;
import com.example.administrator.lmw.entity.ShowFinanceFragmentEvent;
import com.example.administrator.lmw.login.UserInfoUtils;
import com.example.administrator.lmw.login.entity.MineData;
import com.example.administrator.lmw.login.entity.MineDataBean;
import com.example.administrator.lmw.main.MainActivity;
import com.example.administrator.lmw.mine.fill.FillActivity;
import com.example.administrator.lmw.mine.seting.entity.SetDataBean;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lion on 2016/9/2.
 */
public class AccountManageActivity extends BaseActivity {
    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.back_layout)
    LinearLayout back_layout;
    @InjectView(R.id.title_layout)
    RelativeLayout title_layout;
    @InjectView(R.id.account_id)
    TextView account_id;
    @InjectView(R.id.account_name)
    TextView account_name;
    @InjectView(R.id.account_money)
    TextView account_money;
    @InjectView(R.id.invest_tv)
    TextView invest_tv;
    @InjectView(R.id.fill_tv)
    TextView fill_tv;
    private Intent intent;
    private SetDataBean setDataBean;
    private double accountMoney;//存管账户余额
    private String accountId;//存管账户ID
    private String name;//存管账户ID
    private MineData mineData;
    private MineDataBean mineDataBean;
    private DecimalFormat df = new DecimalFormat("##0.00");
    @Override
    protected void initializeData() {

        initPara();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_bank_manage_account_layout;
    }

    @Override
    protected void initializeView() {
        initBarTitle();
        setView();
    }

    private void initPara() {
        mineData = UserInfoUtils.getInstance().getMineData(mContext);
        if (mineData != null)
            mineDataBean = mineData.getMineDataBean();
        if (mineDataBean != null)
            accountMoney = mineDataBean.getUsableAmount();
        intent = getIntent();
        if (intent.hasExtra(SetingActivity.SET_DATA))
            setDataBean = (SetDataBean) intent.getSerializableExtra(SetingActivity.SET_DATA);
        if (setDataBean != null) {
            if (!TextUtils.isEmpty(setDataBean.getCustomerSurname()))
                name = setDataBean.getCustomerName();
            if (!TextUtils.isEmpty(setDataBean.getDepositAccount()))
                accountId = setDataBean.getDepositAccount();

        }

    }

    private void setView() {

        account_id.setText(String.format(getString(R.string.txt_desposit_account_name), accountId));
        //  account_money.setText(String.format(getString(R.string.txt_desposit_account_money), accountMoney+""));
        // account_id.setText(StringUtils.changeTextColor("账户名:", accountId, "999999", "666666"));
        account_money.setText(StringUtils.changeTextColor("账户余额:", "￥" + df.format(accountMoney), "999999", "f97709"));
        account_name.setText(StringUtils.changeTextColor("账户名:", name, "999999", "666666"));
    }

    private void initBarTitle() {
        barIconBack.setImageResource(R.drawable.nav_back);
        title_layout.setBackgroundColor(getResources().getColor(R.color.text_blue));
        barTitle.setText("新网银行存管账户");
    }

    @OnClick({R.id.back_layout, R.id.invest_tv, R.id.fill_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                finishActivity(this);
                break;
            case R.id.invest_tv:
                startActivity(MainActivity.class);
                EventBus.getDefault().post(new ShowFinanceFragmentEvent());
                break;
            case R.id.fill_tv:
                if (Singlton.getInstance(DespositAccountManager.class).isBindCard(mContext)) {
                    startActivity(FillActivity.class);
                }

                break;

        }
    }

}
