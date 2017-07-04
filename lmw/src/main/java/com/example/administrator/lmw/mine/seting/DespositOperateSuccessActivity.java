package com.example.administrator.lmw.mine.seting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.lmw.Activity.ActivityLogic;
import com.example.administrator.lmw.Activity.ActivityType;
import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseTitleActivity;
import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.config.DespositAccountManager;
import com.example.administrator.lmw.config.DespositOperate;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.CommonInfo;
import com.example.administrator.lmw.entity.ShowMineFragmentEvent;
import com.example.administrator.lmw.main.MainActivity;
import com.example.administrator.lmw.mine.credit.CreditActivity;
import com.example.administrator.lmw.mine.fill.FillActivity;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.StringUtils;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 存管相关操作成功页面
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/4/12 14:14
 **/
public class DespositOperateSuccessActivity extends BaseTitleActivity {


    @InjectView(R.id.tv_bind_suc)
    TextView mTvBindSuc;
    @InjectView(R.id.tv_go_finace)
    TextView mTvGoFinace;
    @InjectView(R.id.tv_credit_hint)
    TextView mTvCreditHint;
    @InjectView(R.id.tv_risk_camuate)
    TextView tv_risk_camuate;
    private CommonInfo commonInfo;

    private DespositOperate mDespositOperate;//存管操作类型

    @Override
    protected void initializeData() {
        super.initializeData();
        commonInfo = ConfigManager.getInstance().getCommonInfo(mContext);
        Intent intent = getIntent();
        if (intent.hasExtra(PreferenceCongfig.INTENT_KEY_DESPONSIT_OPERATE)) {
            mDespositOperate = (DespositOperate) intent.getSerializableExtra(PreferenceCongfig.INTENT_KEY_DESPONSIT_OPERATE);
        }

    }

    @Override
    protected void setTitleContentView() {
        initView("", R.layout.activity_desposit_operate_success);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        initUI();
        setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManage.create().finishActivity();
                ActivityManage.create().finishActivity(BindBankSuccussActivity.class);
                ActivityManage.create().finishActivity(AccountManageActivity.class);
                ActivityManage.create().finishActivity(SetingActivity.class);
            }
        });
    }

    private void initUI() {
        if (mDespositOperate == DespositOperate.RESULT_RECHARGE) {//充值结果  --->失败或成功

        } else if (mDespositOperate == DespositOperate.RESULT_WITHDRAW) {//提现结果  --->失败或成功

        } else if (mDespositOperate == DespositOperate.RESULT_PERSONAL_REGISTER_EXPAND) {//开通存管账户结果  --->失败或成功
            openDespositSuccess();
        } else if (mDespositOperate == DespositOperate.RESULT_ACTIVATE_STOCKED_USER) {//激活存活账户结果  --->失败或成功
            openDespositSuccess();
        } else if (mDespositOperate == DespositOperate.RESULT_PERSONAL_BIND_BANKCARD_EXPAND) {//绑定银行卡结果结果  --->失败或成功
            setTitleName(R.string.text_bind_suc);
            mTvBindSuc.setText(R.string.text_bind_bankcard_suc);
            mTvGoFinace.setText(R.string.txt_go_back_home);
            hideRiskCamuateTxt();
        } else if (mDespositOperate == DespositOperate.RESULT_UNBIND_BANKCARD) {//解绑银行卡结果  --->失败或成功
            setTitleName(R.string.txt_unbind_suc);
            mTvBindSuc.setText(R.string.txt_unbind_card_suc);
            mTvGoFinace.setText(R.string.txt_rebind_card);
            hideRiskCamuateTxt();
        } else if (mDespositOperate == DespositOperate.RESULT_MODIFY_MOBILE_EXPAND) {//修改预留手机号结果  --->失败或成功
            setTitleName(R.string.txt_motify_suc);
            mTvBindSuc.setText("银行预留手机号修改成功");
            mTvGoFinace.setText(R.string.txt_go_back_home);
            hideRiskCamuateTxt();
        } else if (mDespositOperate == DespositOperate.RESUET_PASSWORD) {//重置交易密码结果  --->失败或成功
            setTitleName(R.string.txt_motify_suc);
            mTvBindSuc.setText("交易密码修改成功");
            mTvGoFinace.setText(R.string.txt_go_back_home);
            hideRiskCamuateTxt();
        } else if (mDespositOperate == DespositOperate.RESULT_USER_PRE_TRANSACTION) {//购买标的结果  --->失败或成功
            setTitleName(R.string.txt_commit_suc);
            mTvBindSuc.setText(R.string.txt_commit_suc);
            mTvGoFinace.setText(R.string.txt_go_back_home);
            hideRiskCamuateTxt();
        } else if (mDespositOperate == DespositOperate.RESULT_BORROW_DEBT_ADD) {// 转让债权_存管
            setTitleName(R.string.txt_commit_suc);
            mTvBindSuc.setText(R.string.txt_commit_suc);
            mTvGoFinace.setText(R.string.txt_go_back_home);
            mTvCreditHint.setVisibility(View.VISIBLE);
            hideRiskCamuateTxt();
        }
    }

    /**
     * 开通存管成功
     */
    private void openDespositSuccess() {
        setTitleName(R.string.txt_open_suc);
        mTvBindSuc.setText("存管账户开通成功");
        mTvGoFinace.setText(R.string.txt_go_back_home);
        showRiskCamuateTxt();
        //开通存管成功，请求活动接口
        Singlton.getInstance(ActivityLogic.class).showActivityInfo(mContext, ActivityType.BING_CARD, "");
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @OnClick({R.id.tv_go_finace, R.id.tv_risk_camuate})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_go_finace:
                if (mDespositOperate == DespositOperate.RESULT_RECHARGE) {//充值结果  --->失败或成功

                } else if (mDespositOperate == DespositOperate.RESULT_WITHDRAW) {//提现结果  --->失败或成功

                } else if (mDespositOperate == DespositOperate.RESULT_PERSONAL_REGISTER_EXPAND) {//开通存管账户结果  --->失败或成功
                    goBackHome();
                } else if (mDespositOperate == DespositOperate.RESULT_ACTIVATE_STOCKED_USER) {//激活存活账户结果  --->失败或成功
                    goBackHome();
                } else if (mDespositOperate == DespositOperate.RESULT_PERSONAL_BIND_BANKCARD_EXPAND) {//绑定银行卡结果结果  --->失败或成功
                    goBackHome();
                } else if (mDespositOperate == DespositOperate.RESULT_UNBIND_BANKCARD) {//解绑银行卡结果  --->失败或成功
                    //TODO 重新绑卡
                    Singlton.getInstance(DespositAccountManager.class).despositOperate(mContext, DespositOperate.BIND_BANKCARD);
                } else if (mDespositOperate == DespositOperate.RESULT_MODIFY_MOBILE_EXPAND) {//修改预留手机号结果  --->失败或成功
                    goBackHome();
                } else if (mDespositOperate == DespositOperate.RESUET_PASSWORD) {//重置交易密码结果  --->失败或成功
                    goBackHome();
                } else if (mDespositOperate == DespositOperate.RESULT_USER_PRE_TRANSACTION) {//购买标的结果  --->失败或成功
                    goBackHome();
                } else if (mDespositOperate == DespositOperate.RESULT_BORROW_DEBT_ADD) { // 转让债权_存管
                    goBackHome();
                }
                break;
            case R.id.tv_risk_camuate:
                Bundle bundle = new Bundle();
                if (commonInfo != null && !TextUtils.isEmpty(commonInfo.getRiskCamuluateUrl())) {
                    bundle.putString(WebViewMore.INTENT_KEY_URL, commonInfo.getRiskCamuluateUrl());
                    bundle.putString(WebViewMore.INTENT_KEY_TITLE, "风险评估");
                }
                ActivityTools.switchActivity(mContext, WebViewMore.class, bundle);

                break;
        }

    }


    /**
     * 返回我的账户
     */
    private void goBackHome() {
        startActivity(MainActivity.class);
        EventBus.getDefault().post(new ShowMineFragmentEvent());
        //  ActivityManage.create().finishOthersActivity(MainActivity.class);
        // ActivityManage.create().finishActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(mContext).onActivityResult(requestCode, resultCode, data);
    }

    private void showRiskCamuateTxt() {
        tv_risk_camuate.setText(StringUtils.getRiskHintDefault());
        tv_risk_camuate.setVisibility(View.VISIBLE);
    }

    private void hideRiskCamuateTxt() {
        tv_risk_camuate.setVisibility(View.GONE);
    }

}
