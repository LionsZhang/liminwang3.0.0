package com.example.administrator.lmw.mine.seting;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseTitleActivity;
import com.example.administrator.lmw.config.DespositOperate;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.Singlton;
import com.umeng.socialize.UMShareAPI;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 存管相关操作失败页面
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/5/25 9:42
 **/
public class DespositOperateFailActivity extends BaseTitleActivity {

    @InjectView(R.id.tv_fail)
    TextView tv_fail;
    @InjectView(R.id.tv_fail_msg)
    TextView tv_fail_msg;
    @InjectView(R.id.tv_guest)
    TextView tv_guest;
    @InjectView(R.id.tv_cantact_guest)
    TextView tv_cantact_guest;
    @InjectView(R.id.tv_know)
    TextView tv_know;

    private String failMsg = "";
    private DespositOperate mDespositOperate;//存管操作类型

    @Override
    protected void initializeData() {
        super.initializeData();
        Intent intent = getIntent();
        if (intent.hasExtra(PreferenceCongfig.INTENT_KEY_DESPONSIT_OPERATE)) {
            mDespositOperate = (DespositOperate) intent.getSerializableExtra(PreferenceCongfig.INTENT_KEY_DESPONSIT_OPERATE);
        }
        if (intent.hasExtra(PreferenceCongfig.INTENT_KEY_OPEN_DESPONSITY_FAIL)) {
            failMsg = intent.getStringExtra(PreferenceCongfig.INTENT_KEY_OPEN_DESPONSITY_FAIL);
        }
    }

    @Override
    protected void setTitleContentView() {
        initView("", R.layout.activity_desposit_operate_fail);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        initUI();
    }

    private void initUI() {
        tv_fail_msg.setText(String.format(getString(R.string.txt_fail_msg), failMsg));
        tv_guest.setText(Html.fromHtml(mContext.getResources().getString(R.string.txt_cantact_guest)));

        if (mDespositOperate == DespositOperate.RESULT_RECHARGE) {//充值结果  --->失败或成功
            setTitleName(R.string.txt_fill_fail);
            tv_fail.setText(R.string.txt_fill_fail);
        } else if (mDespositOperate == DespositOperate.RESULT_WITHDRAW) {//提现结果  --->失败或成功
            setTitleName(R.string.txt_commit_fail);
            tv_fail.setText(R.string.txt_commit_fail);
        } else if (mDespositOperate == DespositOperate.RESULT_PERSONAL_REGISTER_EXPAND) {//开通存管账户结果  --->失败或成功
            setTitleName(R.string.txt_open_fail);
            tv_fail.setText("存管账户开通失败");
        } else if (mDespositOperate == DespositOperate.RESULT_ACTIVATE_STOCKED_USER) {//激活存活账户结果  --->失败或成功
            setTitleName(R.string.txt_open_fail);
            tv_fail.setText("存管账户开通失败");
        } else if (mDespositOperate == DespositOperate.RESULT_PERSONAL_BIND_BANKCARD_EXPAND) {//绑定银行卡结果结果  --->失败或成功
            setTitleName(R.string.text_bind_fail);
            tv_fail.setText(R.string.text_bind_fail);
        } else if (mDespositOperate == DespositOperate.RESULT_UNBIND_BANKCARD) {//解绑银行卡结果  --->失败或成功
            setTitleName(R.string.txt_unbind_fail);
            tv_fail.setText(R.string.txt_unbind_fail);
        } else if (mDespositOperate == DespositOperate.RESULT_MODIFY_MOBILE_EXPAND) {//修改预留手机号结果  --->失败或成功
            setTitleName(R.string.txt_motify_fail);
            tv_fail.setText("银行预留手机号修改失败");
        } else if (mDespositOperate == DespositOperate.RESUET_PASSWORD) {//重置交易密码结果  --->失败或成功
            setTitleName(R.string.txt_motify_fail);
            tv_fail.setText("交易密码修改失败");
        } else if (mDespositOperate == DespositOperate.RESULT_USER_PRE_TRANSACTION) {//购买标的结果  --->失败或成功
            setTitleName(R.string.txt_invest_fail);
            tv_fail.setText(R.string.txt_invest_fail);
        } else if (mDespositOperate == DespositOperate.RESULT_BORROW_DEBT_ADD) {// 转让债权_存管
            setTitleName(R.string.txt_invest_fail);
            tv_fail.setText(R.string.txt_invest_fail);
            tv_know.setText("暂不转让");
        }
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @OnClick({R.id.tv_know, R.id.tv_cantact_guest, R.id.tv_guest})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_know://我知道了
                ActivityManage.create().finishActivity();
                break;
            case R.id.tv_cantact_guest://在线客服
                ActivityTools.startUdeskGuest(mContext);
                break;
            case R.id.tv_guest://客服热线
                Singlton.getInstance(PopWindowManager.class).showCall(mContext);
                break;
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(mContext).onActivityResult(requestCode, resultCode, data);
    }

}
