package com.example.administrator.lmw.mine.fill;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResponse;
import com.example.administrator.lmw.entity.BindSuccessEvent;
import com.example.administrator.lmw.entity.FillSmsEvent;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.inteface.OnInputDialogListener;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.login.UserInfoUtils;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.login.entity.UserInfoBean;
import com.example.administrator.lmw.mine.fill.entity.FillEntity;
import com.example.administrator.lmw.mine.fill.entity.OderEntity;
import com.example.administrator.lmw.mine.fill.entity.UserBankEntity;
import com.example.administrator.lmw.mine.fill.entity.UserBankInfo;
import com.example.administrator.lmw.mine.seting.ResetTradPswVerifyActivity;
import com.example.administrator.lmw.mine.seting.SetExchargePsw;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.CountdownThread;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.StringUtils;
import com.example.administrator.lmw.view.InputTradePswDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lion on 2016/9/9.
 */
public class FillConfirmActivity extends BaseActivity implements CountdownThread.OnCountdownListener {

    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.title_layout)
    RelativeLayout title_layout;
    @InjectView(R.id.next_bt)
    Button next_bt;
    @InjectView(R.id.bank_iv)
    ImageView bank_iv;
    @InjectView(R.id.bank_tv)
    TextView bank_tv;
    @InjectView(R.id.limit_num_tv)
    TextView limit_num_tv;
    @InjectView(R.id.fill_num_tv)
    TextView fill_num_tv;
    @InjectView(R.id.et_verification_code)
    EditText etVerificationCode;
    @InjectView(R.id.btn_get_verification_code)
    Button btnGetVerificationCode;
    private String fillNumber;
    private Intent intent;
    private UserInfo userInfo;
    private UserInfoBean userInfoBean;
    private String daylyLimit;
    private String singleLimit;
    private String fillOderNo;
    private String tradePsw;
    private String bindBankName;
    private String bindBankNo;
    private String bankCode;
    private String verifyCode;
    public static final String FILL_STATU = "FILL_STATU";
    private boolean enableGetVerifyCode = true;
    public static final  String FAIL_INFO="FAIL_INFO";
    @Override

    protected void initializeData() {
        userInfo = UserInfoUtils.getInstance().getUserInfo(this);
        if (userInfo != null)
            userInfoBean = userInfo.getData();
        intent = getIntent();
        if (intent.hasExtra(PreferenceCongfig.FILL_ODER_NO)) {
            fillOderNo = intent.getStringExtra(PreferenceCongfig.FILL_ODER_NO);
        }
        if (intent.hasExtra(PreferenceCongfig.APP_TRADE_PSW)) {
            tradePsw = intent.getStringExtra(PreferenceCongfig.APP_TRADE_PSW);
        }
        if (intent.hasExtra(FillActivity.FILL_NUMBER)) {
            fillNumber = intent.getStringExtra(FillActivity.FILL_NUMBER);
        }
        if (intent.hasExtra(FillActivity.DAYLY_LIMIT)) {
            daylyLimit = intent.getStringExtra(FillActivity.DAYLY_LIMIT);

        }
        if (intent.hasExtra(FillActivity.SINGLE_LIMIT)) {
            singleLimit = intent.getStringExtra(FillActivity.SINGLE_LIMIT);
        }
        if (intent.hasExtra(FillActivity.BIND_BANK_NAME)) {
            bindBankName = intent.getStringExtra(FillActivity.BIND_BANK_NAME);
        }
        if (intent.hasExtra(FillActivity.BIND_BANK_CODE)) {
            bankCode = intent.getStringExtra(FillActivity.BIND_BANK_CODE);
        }
        if (intent.hasExtra(FillActivity.BIND_BANK_NO)) {
            bindBankNo = intent.getStringExtra(FillActivity.BIND_BANK_NO);
        }
    }


    @Override
    protected int getContentLayout() {
        return R.layout.activity_fill_confirm_layout;
    }

    @Override
    protected void initializeView() {
        next_bt.setClickable(false);
        initBarTitle();
        startTimer();
        setTextChangeListener();

        if (Double.valueOf(daylyLimit)== -1)
            limit_num_tv.setText(String.format(getString(R.string.bind_card_limit_credit_not_limit),
                    StringUtils.mathLeftMove4(singleLimit)));
        else    limit_num_tv.setText(String.format(getString(R.string.bind_card_limit_credit),
                StringUtils.mathLeftMove4(singleLimit), StringUtils.mathLeftMove4(daylyLimit)));
        bank_tv.setText(String.format(getString(R.string.fill_bind_card), bindBankName, bindBankNo));
        fill_num_tv.setText(String.format(getString(R.string.credit),fillNumber));

        if (bankCode.equals("ICBC"))//ICBC中国工商银行
            bank_iv.setBackgroundResource(R.drawable.ic_banklogo_gs);
        else if (bankCode.equals("ABC"))//ABC中国农业银行
            bank_iv.setBackgroundResource(R.drawable.ic_banklogo_ny);
        else if  (bankCode.equals("BOC"))//BOC中国银行
            bank_iv.setBackgroundResource(R.drawable.ic_banklogo_zg);
        else if (bankCode.equals("CCB"))//中国建设银行
            bank_iv.setBackgroundResource(R.drawable.ic_banklogo_js);
        else if (bankCode.equals("PAB"))//平安银行
            bank_iv.setBackgroundResource(R.drawable.ic_banklogo_pa);
        else if (bankCode.equals("GDB"))//广发银行
            bank_iv.setBackgroundResource(R.drawable.ic_banklogo_gf);
        else if  (bankCode.equals("CITIC"))//中信银行
            bank_iv.setBackgroundResource(R.drawable.ic_banklogo_zx);
        else if  (bankCode.equals("CIB"))//兴业银行
            bank_iv.setBackgroundResource(R.drawable.ic_banklogo_xy);
        else if  (bankCode.equals("XHB"))//华夏银行
            bank_iv.setBackgroundResource(R.drawable.ic_banklogo_hx);
        else if (bankCode.equals("BCOM"))//交通银行
            bank_iv.setBackgroundResource(R.drawable.ic_banklogo_jt);
        else if  (bankCode.equals("CMB"))//招商银行
            bank_iv.setBackgroundResource(R.drawable.ic_banklogo_zs);
        else if  (bankCode.equals("SPDB"))//SPDB上海浦东发展银行
            bank_iv.setBackgroundResource(R.drawable.ic_banklogo_pf);
        else if  (bankCode.equals("SHB"))//SHB上海银行
            bank_iv.setBackgroundResource(R.drawable.ic_banklogo_shyh);
        else if  (bankCode.equals("PSBC"))//PSBC邮政储蓄银行
            bank_iv.setBackgroundResource(R.drawable.ic_banklogo_yz);
        else if  (bankCode.equals("CMBC"))//CMBC民生银行
            bank_iv.setBackgroundResource(R.drawable.ic_banklogo_ms);
        else if  (bankCode.equals("CEB"))//CEB光大银行
            bank_iv.setBackgroundResource(R.drawable.ic_banklogo_gd);




    }

    private void initBarTitle() {
        barIconBack.setImageResource(R.drawable.nav_back);
        title_layout.setBackgroundColor(getResources().getColor(R.color.text_blue));
        barTitle.setText("充值确认");
    }



    @OnClick({R.id.back_layout, R.id.next_bt,R.id.btn_get_verification_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                finishActivity(this);
                break;
            case R.id.btn_get_verification_code:
                if (enableGetVerifyCode){
                    EventBus.getDefault().post(new FillSmsEvent());
                    enableGetVerifyCode=false;
                    startTimer();
                }

                break;
            case R.id.next_bt:
                showLoadingDialog();
                pay();
                break;
        }
    }

    private void setTextChangeListener() {

        etVerificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                verifyCode = etVerificationCode.getText().toString();
                if (TextUtils.isEmpty(verifyCode)||verifyCode.length()!=6) {
                    next_bt.setClickable(false);
                    next_bt.setBackgroundResource(R.drawable.login_bg_nol);
                } else {
                    next_bt.setClickable(true);
                    next_bt.setBackgroundResource(R.drawable.login_bg_sel);
                }
            }
        });


    }

    private void pay() {
        Singlton.getInstance(FillLogic.class).fillPay(this, fillOderNo, tradePsw, verifyCode, new OnResponseListener<FillEntity>() {
            @Override
            public void onSuccess(FillEntity response) {
                hideLoadingDialog();
                int code = Integer.valueOf(response.code);
                if (code == 0) {//充值成功
                    EventBus.getDefault().post(new BindSuccessEvent());
                    Bundle bundle = new Bundle();
                    bundle.putString(FillActivity.FILL_NUMBER, fillNumber);
                    bundle.putInt(FILL_STATU, 0);
                    ActivityTools.switchActivity(FillConfirmActivity.this, FillResultActivity.class, bundle);
                    ActivityManage.create().finishActivity(FillActivity.class);

                } else if (code == 150006) {
                    showToast(response.msg);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(LoginActivity.class);
                        }
                    }, 1000);
                }
                else//充值失败
                {
                    Bundle bundle = new Bundle();
                    bundle.putString(FillActivity.FILL_NUMBER, fillNumber);
                    bundle.putInt(FILL_STATU, 1);
                    bundle.putString(FAIL_INFO, response.getData().getRespMsg());
                    ActivityTools.switchActivity(FillConfirmActivity.this, FillResultActivity.class, bundle);
                   finishActivity(FillConfirmActivity.this);
                }

            }

            @Override
            public void onFail(Throwable e) {
                hideLoadingDialog();
            }
        });

    }
    private void startTimer() {
        enableGetVerifyCode = false;
        btnGetVerificationCode.setBackgroundResource(R.drawable.login_bg_nol);
        CountdownThread thread = new CountdownThread();
        thread.setOnCountdownListener(this);
        thread.start(); // 开始倒计时

    }
    @Override
    public void OnCountdown(int count) {
        if (btnGetVerificationCode != null)
            btnGetVerificationCode.setText(count + getString(R.string.countdown_tip));
    }

    @Override
    public void OnCountdownFinish() {
        if (btnGetVerificationCode != null){
            btnGetVerificationCode.setBackgroundResource(R.drawable.login_bg_sel);
            btnGetVerificationCode.setText(getString(R.string.get_verify_code));
            enableGetVerifyCode = true;
        }


    }
}
