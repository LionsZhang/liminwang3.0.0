package com.example.administrator.lmw.mine.seting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.config.DespositAccountManager;
import com.example.administrator.lmw.config.DespositOperate;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.entity.CommonInfo;
import com.example.administrator.lmw.inteface.OnInputDialogListener;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.UserInfoUtils;
import com.example.administrator.lmw.login.entity.MineData;
import com.example.administrator.lmw.login.entity.MineDataBean;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.mine.seting.entity.SetDataBean;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.PicassoManager;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.StringUtils;
import com.example.administrator.lmw.view.InputTradePswDialog;
import com.example.administrator.lmw.view.SettingNextItemNotIconView;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lion on 2016/9/2.
 */
public class BindBankSuccussActivity extends BaseActivity {
    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.back_layout)
    LinearLayout back_layout;
    @InjectView(R.id.title_layout)
    RelativeLayout title_layout;
    @InjectView(R.id.name_tv)
    TextView name_tv;
    @InjectView(R.id.bank_icon)
    ImageView bank_icon;
    @InjectView(R.id.card_name)
    TextView card_name;
    @InjectView(R.id.account)
    TextView account;
    @InjectView(R.id.right_title)
    TextView right_title;
    @InjectView(R.id.title_bar_right_layout)
    LinearLayout title_bar_right_layout;
    @InjectView(R.id.change_bank_tv)
    TextView change_bank_tv;
    @InjectView(R.id.bank_phone)
    SettingNextItemNotIconView bank_phone;
    @InjectView(R.id.single_payment_limit)
    SettingNextItemNotIconView single_payment_limit;
    @InjectView(R.id.dayly_payment_limit)
    SettingNextItemNotIconView dayly_payment_limit;
    @InjectView(R.id.add_bank_img)
    ImageView add_bank_img;
    @InjectView(R.id.clear_bind_card_btn)
    Button clear_bind_card_btn;

    private Intent intent;
    private String bankNo;
    private String idNo;
    private String bankName;
    private SetDataBean setDataBean;
    private String realName;
    private String bankCode;
    private InputTradePswDialog inputTradePswDialog;
    private String dailyQuotaAmount;//单日限额
    private String eachQuotaAmout;//单笔限额
    private String bankPhone;//银行预留手机号
    private MineData mineData;
    private MineDataBean mineDataBean;
    private double usableAmount;
    private String bankImg;
    private CommonInfo commonInfo;

    @Override
    protected void initializeData() {
        mineData = UserInfoUtils.getInstance().getMineData(mContext);
        commonInfo = ConfigManager.getInstance().getCommonInfo(mContext);
        if (mineData != null)
            mineDataBean = mineData.getMineDataBean();
        if (mineDataBean != null)
            usableAmount = mineDataBean.getUsableAmount();
        intent = getIntent();
        if (intent.hasExtra(SetingActivity.SET_DATA))

            setDataBean = (SetDataBean) intent.getSerializableExtra(SetingActivity.SET_DATA);
        if (setDataBean != null) {
            bankNo = setDataBean.getBankCardNo();
            idNo = setDataBean.getIdNo();
            bankName = setDataBean.getBankName();
            realName = setDataBean.getCustomerSurname();
            bankCode = setDataBean.getBankCode();
            dailyQuotaAmount = setDataBean.getDailyQuotaAmount();
            eachQuotaAmout = setDataBean.getEachQuotaAmout();
            bankPhone = setDataBean.getBankMobile();
            bankImg = setDataBean.getBankImg();
        }

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_bind_card_success_layout;
    }

    @Override
    protected void initializeView() {
        initBarTitle();
        setView();
        setPhoneItem();
        setDailyLimitItem();
        setSingleLimitItem();
        if (setDataBean != null)
            showUnbundleView();

    }

    private void showUnbundleView() {
        if (!TextUtils.isEmpty(setDataBean.getIsCanUnbundle()) && TextUtils.equals(setDataBean.getIsCanUnbundle(), "1")) {//可解绑
            clear_bind_card_btn.setVisibility(View.VISIBLE);
        } else
            clear_bind_card_btn.setVisibility(View.GONE);

    }

    private void setSingleLimitItem() {
        single_payment_limit.setOnRightTextOnclick(new SettingNextItemNotIconView.RigthTextViewListener() {

            @Override
            public void onRightTextView(TextView v) {
                if (!TextUtils.isEmpty(eachQuotaAmout)) {
                    if (TextUtils.equals(eachQuotaAmout, "-1"))
                        v.setText("单笔无限额");
                    else
                        v.setText(StringUtils.mathLeftMove4(eachQuotaAmout) + "万");
                }

            }

            @Override
            public void onClick(View v) {

            }
        });

    }

    private void setDailyLimitItem() {
        dayly_payment_limit.setOnRightTextOnclick(new SettingNextItemNotIconView.RigthTextViewListener() {

            @Override
            public void onRightTextView(TextView v) {
                if (!TextUtils.isEmpty(dailyQuotaAmount)) {
                    if (TextUtils.equals(dailyQuotaAmount, "-1"))
                        v.setText("单日无限额");
                    else
                        v.setText(StringUtils.mathLeftMove4(dailyQuotaAmount) + "万");
                }

            }

            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setPhoneItem() {
        bank_phone.setOnRightTextOnclick(new SettingNextItemNotIconView.RigthTextViewListener() {

            @Override
            public void onRightTextView(TextView v) {
                if (!TextUtils.isEmpty(bankPhone)) {
                    v.setText(String.format(getString(R.string.txt_motify_phone), bankPhone));
                }
            }

            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(bankPhone)){
                    Singlton.getInstance(DespositAccountManager.class).despositOperate(mContext, DespositOperate.PHONE);
                }


            }
        });

    }

    private void setView() {
        if (!TextUtils.isEmpty(bankNo)) {
            if (bankNo.contains("*")){
                String no=  bankNo.replaceAll("\\*", "");
                account.setText(no);//卡号后四位
            }else {
                account.setText(bankNo);
            }
        }
        if (!TextUtils.isEmpty(realName) && !TextUtils.isEmpty(idNo))
            name_tv.setText(String.format(getString(R.string.bind_bank_card_success), realName, idNo));
        if (!TextUtils.isEmpty(bankName))
            card_name.setText(bankName);
        if (TextUtils.isEmpty(bankCode))
            return;
        if (!TextUtils.isEmpty(bankImg))
            PicassoManager.getInstance().display(mContext, bank_icon, bankImg);

    }

    private void initBarTitle() {
        barIconBack.setImageResource(R.drawable.nav_back);
        title_layout.setBackgroundColor(getResources().getColor(R.color.text_blue));
        barTitle.setText("银行卡");
//        right_title.setText("支持银行");

    }


    @OnClick({R.id.back_layout, R.id.instruction_btn, R.id.clear_bind_card_btn, R.id.add_bank_img, R.id.change_bank_tv, R.id.title_bar_right_layout})

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                finishActivity(this);
                break;
            case R.id.title_bar_right_layout:
                //跳到支持银行页面
                Bundle bundle = new Bundle();
                bundle.putInt(PreferenceCongfig.ChoseBankCard, PreferenceCongfig.ChoseBankCard_support_card);
                ActivityTools.switchActivity(BindBankSuccussActivity.this, ChooseBankCardActivity.class, bundle);
                break;
            case R.id.clear_bind_card_btn:
                //跳到解除绑定银行卡
         /*       if (usableAmount > 0) {
                    Singlton.getInstance(PopWindowManager.class).
                            showOneButtonDialog(mContext, "提示", "只有当余额为0时,才能进行银行卡解绑操作哦", "我知道了", new OnDialogClickListener() {
                                @Override
                                public void onClick(int id, View v) {
                                    if (id == 2) {

                                    }

                                }
                            });
                } else {
                    Singlton.getInstance(PopWindowManager.class).
                            showTwoButtonDialog(mContext, "提示", "确定要申请解绑银行卡吗？", "取消", "确定", new OnDialogClickListener() {
                                @Override
                                public void onClick(int id, View v) {
                                    if (id == 1) {
                                        showInputLicenceDialog();
                                    }

                                }
                            });
                }*/
//                Singlton.getInstance(PopWindowManager.class).
//                        showTwoButtonDialog(mContext, "提示", "确定要申请解绑银行卡吗？", "取消", "确定", new OnDialogClickListener() {
//                            @Override
//                            public void onClick(int id, View v) {
//                                if (id == 1) {
//                                    showInputLicenceDialog();
//                                }
//
//                            }
//                        });
                showInputLicenceDialog();
                break;
            case R.id.add_bank_img:
                //跳到添加绑定银行卡
                break;
            case R.id.change_bank_tv:
                //跳到更换绑定银行卡
                Bundle bd = new Bundle();
                if (commonInfo != null)
                    bd.putString(WebViewMore.INTENT_KEY_URL, commonInfo.getChange_bank_card_url());
                bd.putString(WebViewMore.INTENT_KEY_TITLE, "换卡须知");
                ActivityTools.switchActivity(BindBankSuccussActivity.this, WebViewMore.class, bd);
                break;
            case R.id.instruction_btn:
                Singlton.getInstance(PopWindowManager.class).
                        showInstructionDetailPopupWindow(this, title_layout, "温馨提示", StringUtils.getBindCardInstruction());
                break;

        }
    }

    private void showInputLicenceDialog() {
        inputTradePswDialog = new InputTradePswDialog(this, InputTradePswDialog.input_licence, null, new OnInputDialogListener() {
            @Override
            public void onClick(int id, String license) {
                if (id == 2) {//输入完成
                    verifyLicense(license);
                }
            }
        });


    }

    /**
     * 验证原密码
     */
    private void verifyLicense(String licence) {
        showLoadingDialog();
        Singlton.getInstance(SetLogic.class).verifyLicence(this, licence, new OnResponseListener<BaseResult>() {
            @Override
            public void onSuccess(BaseResult response) {
                if (TextUtils.equals("0", response.getCode())) {
                    doUnbundle();
                    inputTradePswDialog.dismiss();
                } else {
                    inputTradePswDialog.clearTradePsw();
                    hideLoadingDialog();
                }

            }


            @Override
            public void onFail(Throwable e) {
                hideLoadingDialog();
            }
        });
    }

    private void doUnbundle() {
        Singlton.getInstance(DespositAccountManager.class).despositOperate(this, DespositOperate.UNBIND_BANKCARD);

    }

}
