package com.example.administrator.lmw.finance.activity;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.Activity.ActivityLogic;
import com.example.administrator.lmw.Activity.ActivityType;
import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseTitleActivity;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.ShowFinanceFragmentEvent;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.main.MainActivity;
import com.example.administrator.lmw.mine.invest.InvestmentActivity;
import com.example.administrator.lmw.utils.Singlton;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 购买成功
 * <p/>
 * Created by Administrator on 2016/9/5 0005.
 */
public class BuySuccee extends BaseTitleActivity {

    public static final String INTENT_KEY_INVESTID = "intent_key_investid";//投资记录id

    @InjectView(R.id.buy_image1)
    RelativeLayout buyImage1;
    @InjectView(R.id.buy_image2)
    ImageView buyImage2;
    @InjectView(R.id.buy_image3)
    ImageView buyImage3;
    @InjectView(R.id.buy_image4)
    ImageView buyImage4;
    @InjectView(R.id.buy_image5)
    ImageView buyImage5;
    @InjectView(R.id.buy_image6)
    ImageView buyImage6;
    @InjectView(R.id.buy_image7)
    ImageView buyImage7;
    @InjectView(R.id.buy_datas_tv)
    TextView buyDatasTv;
    @InjectView(R.id.buy_continue)
    Button buyContinue;
    @InjectView(R.id.buy_see)
    Button buySee;
    @InjectView(R.id.buy_lin5)
    LinearLayout buyLin5;
    @InjectView(R.id.buy_interest_data)
    TextView buyInterestData;
    @InjectView(R.id.buy_payment_data)
    TextView buyPaymentData;
    @InjectView(R.id.buy_succee_tv)
    TextView buySucceeTv;
    private String interestData, paymentData, canTransfer, buyedSucSpanDesc;
    //    private String str = "<span color='#444444'>购买成功，金额<font color='#f97909'>1000.0</font>元，使用<font color='#f97909'>100.00</font>元现金券</span>";

    private String mInvestId;//投资记录id

    @Override
    protected void initializeData() {
        initParams();
        //查看是否有投资成功活动
        if (!TextUtils.isEmpty(mInvestId))
            Singlton.getInstance(ActivityLogic.class).showActivityInfo(mContext, ActivityType.INVEST, mInvestId);
    }

    /**
     * 获取数据
     */
    private void initParams() {
        Intent intent = getIntent();
        if (intent.hasExtra("interestData"))
            interestData = intent.getStringExtra("interestData");
        if (intent.hasExtra("paymentData"))
            paymentData = intent.getStringExtra("paymentData");
        if (intent.hasExtra("canTransfer"))
            canTransfer = intent.getStringExtra("canTransfer");
        if (intent.hasExtra("buyedSucSpanDesc"))
            buyedSucSpanDesc = intent.getStringExtra("buyedSucSpanDesc");
        if (intent.hasExtra(INTENT_KEY_INVESTID))
            mInvestId = intent.getStringExtra(INTENT_KEY_INVESTID);
    }


    @Override
    protected void initializeView() {
        setText();
    }

    @Override
    protected void setTitleContentView() {
        initView(R.string.buy_success, R.layout.activity_buy_succee);
        setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.class);
                EventBus.getDefault().post(new ShowFinanceFragmentEvent());
            }
        });
    }


    @OnClick({R.id.buy_continue, R.id.buy_see})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buy_continue:// 投资理财
                startActivity(MainActivity.class);
                EventBus.getDefault().post(new ShowFinanceFragmentEvent());
                break;
            case R.id.buy_see:// 账户明细
                if (!PreferenceCongfig.isLogin)
                    startActivity(LoginActivity.class);
                else
//                    startActivity(AccountActivity.class);// 账户明细
                    startActivity(InvestmentActivity.class);// 我的投资
                break;
        }
    }

    private void setText() {
        /**
         * 花费金额
         */
        if (!TextUtils.isEmpty(buyedSucSpanDesc))
            buySucceeTv.setText(Html.fromHtml(buyedSucSpanDesc));

        if (canTransfer.equals("0")) {//canTransfer;//0-可以转让、1-不可以转让
            buyImage5.setVisibility(View.VISIBLE);
            buyImage6.setVisibility(View.VISIBLE);
            buyLin5.setVisibility(View.VISIBLE);
        } else {
            buyImage5.setVisibility(View.GONE);
            buyImage6.setVisibility(View.GONE);
            buyLin5.setVisibility(View.GONE);
        }

        /**
         * 时间
         */
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        buyDatasTv.setText(formatter.format(curDate));
        /**
         * 起息日
         */
        buyInterestData.setText(interestData);
        /**
         * 回款日
         */
        buyPaymentData.setText(paymentData);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(mContext).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 重写返回方法
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(MainActivity.class);
            EventBus.getDefault().post(new ShowFinanceFragmentEvent());
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
