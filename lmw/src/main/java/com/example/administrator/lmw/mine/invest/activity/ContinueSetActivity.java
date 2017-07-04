package com.example.administrator.lmw.mine.invest.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseTitleActivity;
import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.entity.CommonInfo;
import com.example.administrator.lmw.finance.utils.TextViewUtils;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.invest.entity.ContinueSetEntity;
import com.example.administrator.lmw.mine.invest.entity.ValueKeyBeans;
import com.example.administrator.lmw.mine.invest.utils.InvestmentActivityEvent;
import com.example.administrator.lmw.mine.invest.utils.InvestmentHttp;
import com.example.administrator.lmw.mine.invest.utils.ListDialog;
import com.example.administrator.lmw.mine.invest.utils.ListDialogListener;
import com.example.administrator.lmw.mine.invest.utils.ListOneDialog;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.NumberParserUtil;
import com.example.administrator.lmw.utils.Singlton;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;


/**
 * 我的投资 续投设置
 * <p>
 * Created by Administrator on 2017/3/13
 */
public class ContinueSetActivity extends BaseTitleActivity {

    @InjectView(R.id.continue_title_tv)
    TextView continueTitleTv;
    @InjectView(R.id.continue_rate_tv)
    TextView continueRateTv;
    @InjectView(R.id.continue_deadline_tv)
    TextView continueDeadlineTv;
    @InjectView(R.id.continue_dates_tv)
    TextView continueDatesTv;
    @InjectView(R.id.continue_interest_tv)
    TextView continueInterestTv;
    @InjectView(R.id.continue_plan_tv)
    TextView continuePlanTv;
    @InjectView(R.id.continue_way_tv)
    TextView continueWayTv;
    @InjectView(R.id.continue_back_way_tv)
    TextView continueBackWayTv;
    @InjectView(R.id.continue_back_way_rel)
    RelativeLayout continueBackWayRel;
    @InjectView(R.id.continue_types_tv)
    TextView continueTypesTv;
    @InjectView(R.id.continue_types_rel)
    RelativeLayout continueTypesRel;
    @InjectView(R.id.continue_term_tv)
    TextView continueTermTv;
    @InjectView(R.id.continue_term_rel)
    RelativeLayout continueTermRel;
    @InjectView(R.id.continue_repayment_tv)
    TextView continueRepaymentTv;
    @InjectView(R.id.continue_repayment_rel)
    RelativeLayout continueRepaymentRel;
    @InjectView(R.id.continue_btn)
    Button continueBtn;
    @InjectView(R.id.continue_about_tv)
    TextView continueAboutTv;
    @InjectView(R.id.activity_continue_set)
    LinearLayout activityContinueSet;
    private String investId;// 标的id
    private Intent intent;
    private ListOneDialog listOneDialog;
    private ListDialog listDialog;
    private String backWay, types, term, repayment;
    private CommonInfo commonInfo;
    private List<String> listbackwayvalue;
    private List<String> listbackwaykey;
    private List<Boolean> listbackwyafalg;
    private List<String> listtypevalue;
    private List<String> listtypekey;
    private List<Boolean> listtypefalg;
    private List<String> listTermvalue;
    private List<String> listTermkey;
    private List<Boolean> listTermfalg;
    private List<String> listRepayvalue;
    private List<String> listRepaykey;
    private List<Boolean> listRepayfalg;

    @Override
    protected void initializeData() {
        commonInfo = ConfigManager.getInstance().getCommonInfo(mContext);
        intent = getIntent();
        intents();
    }

    @Override
    protected void initializeView() {
        initializationhttp(investId);
        // 确认点击事件
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOnClickHttp();
            }
        });
        // 关于续投
        continueAboutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewMore.class);
                if (commonInfo != null)
                    intent.putExtra("url", commonInfo.getLmw_con_invest_url());
                else
                    intent.putExtra("url", "");
                intent.putExtra("title", "关于续投");
                startActivity(intent);
            }
        });
    }

    /**
     * 获取传参
     */
    private void intents() {
        if (intent.hasExtra("investId"))
            investId = intent.getStringExtra("investId");
    }


    @Override
    protected void setTitleContentView() {
        initView("续投设置", R.layout.activity_continue_set);
    }


    /**
     * 续投设置
     * <p>
     * types        续投产品类型（字符串，多个用,号隔开）	string
     * term         续投产品期限（字符串，多个用,号隔开）	string
     * repayment    回款方式（字符串，多个用,号隔开）	string
     * investId     投资ID	string
     * backWay      回款续投方式	string
     */
    private void btnOnClickHttp() {
        Singlton.getInstance(InvestmentHttp.class).getContinueInvestment(mContext, types, term, repayment, investId, backWay, new OnResponseListener<BaseResult<String>>() {
            @Override
            public void onSuccess(BaseResult<String> baseResponse) {
                if (baseResponse == null) return;
                if (TextUtils.equals(baseResponse.getCode(), "0")) {
                    showToast(baseResponse.getMsg());
                    ActivityManage.create().finishActivity(ContinueSetActivity.class);
                    EventBus.getDefault().post(new InvestmentActivityEvent());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    private void initializationhttp(String investId) {
        Singlton.getInstance(InvestmentHttp.class).getContinueInitialization(mContext, investId, new OnResponseListener<BaseResult<ContinueSetEntity>>() {
            @Override
            public void onSuccess(BaseResult<ContinueSetEntity> continueSetEntity) {
                if (continueSetEntity == null) return;
                if (continueSetEntity.getData() == null) return;
                if (TextUtils.equals(continueSetEntity.getCode(), "0")) {
                    // 点击事件
                    onClick(continueSetEntity.getData());
                    getDisplay(continueSetEntity.getData());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 数据显示加载
     *
     * @param continueSetEntity
     */
    private void getDisplay(ContinueSetEntity continueSetEntity) {
        // 标题

        continueTitleTv.setText(continueSetEntity.getBorrowTitle());
        // 利率 1=固定利率 2=浮动利率
        continueRateTv.setText(showRate(continueSetEntity));
        // 日期 期限类型(1=天数|2=自然月|3=年)
        switch (NumberParserUtil.parserInt(continueSetEntity.getDeadLineType(), 0)) {
            case 1:
                continueDeadlineTv.setText(getDeadLineType(continueSetEntity) + "天");
                break;
            case 2:
                continueDeadlineTv.setText(getDeadLineType(continueSetEntity) + "个月");
                break;
            case 3:
                continueDeadlineTv.setText(getDeadLineType(continueSetEntity) + "年");
                break;
        }
        // 下一回款日
        continueDatesTv.setText(continueSetEntity.getRepayDate());
        // 回款本息
        continueInterestTv.setText(continueSetEntity.getStillSum());
        // 回款续投方式(-1-不续投，0-本息续投、1-本金续投、2-利息续投)

        if (!TextViewUtils.isEmpty(continueSetEntity.getMode())) {
            backWay = continueSetEntity.getMode();
            if (continueSetEntity.getMode().equals("-1")) {
                continueTypesRel.setVisibility(View.GONE);
                continueTermRel.setVisibility(View.GONE);
                continueRepaymentRel.setVisibility(View.GONE);
            } else {
                continueTypesRel.setVisibility(View.VISIBLE);
                continueTermRel.setVisibility(View.VISIBLE);
                continueRepaymentRel.setVisibility(View.VISIBLE);
            }
            for (int i = 0; i < continueSetEntity.getModeDatas().size(); i++) {
                if (continueSetEntity.getMode().equals(continueSetEntity.getModeDatas().get(i).getKey())) {
                    continuePlanTv.setText(continueSetEntity.getModeDatas().get(i).getValue());
                    continueBackWayTv.setText(continueSetEntity.getModeDatas().get(i).getValue());
                }
            }
        } else {
            continuePlanTv.setText("");
            continueBackWayTv.setText("");
            backWay = "";
        }
        // 续投产品类型 (1004车贷宝,1005易优贷,1001定期

        if (!TextViewUtils.isEmpty(continueSetEntity.getContinueBorrowType())) {
            types = continueSetEntity.getContinueBorrowType();
            display(continueSetEntity.getContinueBorrowType(), continueSetEntity.getContinueBorrowTypeDatas(),
                    continueTypesTv);
        } else {
            continueTypesTv.setText("");
            types = "";
        }
        //续投产品期限 (30,60,90,180,360,720)

        if (!TextViewUtils.isEmpty(continueSetEntity.getContinueDeadLine())) {
            term = continueSetEntity.getContinueDeadLine();
            display(continueSetEntity.getContinueDeadLine(), continueSetEntity.getContinueDeadLineDatas(),
                    continueTermTv);
        } else {
            continueTermTv.setText("");
            term = "";
        }
        // 续投回款类型(1-等额本息3-一次性还本付息 4-等额本金 ,5按月付息到期还本 )

        if (!TextViewUtils.isEmpty(continueSetEntity.getContinueRepayMode())) {
            repayment = continueSetEntity.getContinueRepayMode();
            display(continueSetEntity.getContinueRepayMode(), continueSetEntity.getContinueRepayModeDatas(),

                    continueRepaymentTv);
        } else {
            continueRepaymentTv.setText("");
            repayment = "";
        }
    }


    /**
     * 利率显示
     *
     * @param continueSetEntity
     * @return
     */
    private String showRate(ContinueSetEntity continueSetEntity) {
        if (TextUtils.equals(continueSetEntity.getIsFlow(), "2")) {
            if (NumberParserUtil.parserDouble(continueSetEntity.getIncreaseRate(), 0) > 0) {
                return "(" + continueSetEntity.getFlowMinAnnualRate() + "%~" + continueSetEntity.getFlowMaxAnnualRate() +
                        "%)+" + continueSetEntity.getIncreaseRate() + "%";

            } else {
                return continueSetEntity.getFlowMinAnnualRate() + "%~" + continueSetEntity.getFlowMaxAnnualRate() + "%";
            }
        } else {
            if (NumberParserUtil.parserDouble(continueSetEntity.getIncreaseRate(), 0) > 0)
                return continueSetEntity.getAnnualRate() + "%+" + continueSetEntity.getIncreaseRate() + "%";
            else
                return continueSetEntity.getAnnualRate() + "%";
        }

    }


    /**
     * 日期是否是月增利
     *
     * @param continueSetEntity
     * @return
     */
    private String getDeadLineType(ContinueSetEntity continueSetEntity) {
        if (TextUtils.equals(continueSetEntity.getIsFlowDead(), "2")) {
            if (TextUtils.equals(continueSetEntity.getIsOutTime(), "1")) {// 为1时请取实际天数，0取浮动天数
                return continueSetEntity.getActualDays();
            } else {
                return continueSetEntity.getCollectLineMinValue() + "~" + continueSetEntity.getCollectLineMaxValue();
            }
        } else {
            return continueSetEntity.getDeadLineValue();
        }
    }

    /**
     * 显示工具
     *
     * @param str      传入参数
     * @param beans    传入数组
     * @param textview 显示控件
     */
    private void display(String str, List<ValueKeyBeans> beans, TextView textview) {
        String[] all = str.split(",");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < all.length; i++) {
            for (int j = 0; j < beans.size(); j++) {
                if (all[i].equals(beans.get(j).getKey())) {
                    sb.append(beans.get(j).getValue() + ",");

                }
            }
        }
        textview.setText(sb.toString().substring(0, sb.toString().length() - 1));
    }

    /**
     * 点击事件
     *
     * @param entity
     */
    private void onClick(final ContinueSetEntity entity) {
        // 回款续投方式
        continueBackWayRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backWay = "";
                if (listbackwayvalue == null && listbackwaykey == null && listbackwyafalg == null) {
                    listbackwayvalue = new ArrayList<>();
                    listbackwaykey = new ArrayList<>();
                    listbackwyafalg = new ArrayList<>();
                    for (int i = 0; i < entity.getModeDatas().size(); i++) {
                        listbackwayvalue.add(entity.getModeDatas().get(i).getValue());
                        listbackwaykey.add(entity.getModeDatas().get(i).getKey());
                        if (entity.getMode().equals(entity.getModeDatas().get(i).getKey())) {
                            listbackwyafalg.add(true);
                        } else {
                            listbackwyafalg.add(false);
                        }
                    }
                }
                listOneDialog = new ListOneDialog(ContinueSetActivity.this, listbackwayvalue, listbackwaykey, listbackwyafalg, "续投方式选择", new ListDialogListener() {
                    @Override
                    public void onClick(int id, String value, String key, List<Boolean> falg) {
                        if (id == 1) {
                            if (key.equals("-1")) {
                                continueTypesRel.setVisibility(View.GONE);
                                continueTermRel.setVisibility(View.GONE);
                                continueRepaymentRel.setVisibility(View.GONE);
                            } else {
                                continueTypesRel.setVisibility(View.VISIBLE);
                                continueTermRel.setVisibility(View.VISIBLE);
                                continueRepaymentRel.setVisibility(View.VISIBLE);
                            }
                            continuePlanTv.setText(value);
                            continueBackWayTv.setText(value);
                            backWay = key;
                            listbackwyafalg = new ArrayList<>();
                            listbackwyafalg.addAll(falg);
                        }
                    }
                });
            }
        });
        // 续投产品类型 (1004车贷宝,1005易优贷,1001定期
        continueTypesRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                types = "";
                if (listtypevalue == null && listtypekey == null && listtypefalg == null) {
                    listtypevalue = new ArrayList<>();
                    listtypekey = new ArrayList<>();
                    listtypefalg = new ArrayList<>();
                    String[] alltype = entity.getContinueBorrowType().split(",");
                    for (int i = 0; i < entity.getContinueBorrowTypeDatas().size(); i++) {
                        listtypevalue.add(entity.getContinueBorrowTypeDatas().get(i).getValue());
                        listtypekey.add(entity.getContinueBorrowTypeDatas().get(i).getKey());
                        listtypefalg.add(false);
                    }
                    for (int i = 0; i < alltype.length; i++) {
                        for (int j = 0; j < listtypekey.size(); j++) {
                            if (alltype[i].equals(listtypekey.get(j))) {
                                listtypefalg.set(j, true);
                            }
                        }
                    }
                }
                listDialog = new ListDialog(mContext, listtypevalue, listtypekey, listtypefalg, "续投产品类型", new ListDialogListener() {
                    @Override
                    public void onClick(int id, String value, String key, List<Boolean> falg) {
                        if (id == 1) {
                            continueTypesTv.setText(value);
                            types = key;
                            listtypefalg = new ArrayList<>();
                            listtypefalg.addAll(falg);
                        }
                    }
                });
            }
        });
        // 续投产品期限 (30,60,90,180,360,720)
        continueTermRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                term = "";
                if (listTermvalue == null && listTermkey == null && listTermfalg == null) {
                    listTermvalue = new ArrayList<>();
                    listTermkey = new ArrayList<>();
                    listTermfalg = new ArrayList<>();
                    String[] allTerm = entity.getContinueDeadLine().split(",");
                    for (int i = 0; i < entity.getContinueDeadLineDatas().size(); i++) {
                        listTermvalue.add(entity.getContinueDeadLineDatas().get(i).getValue());
                        listTermkey.add(entity.getContinueDeadLineDatas().get(i).getKey());
                        listTermfalg.add(false);
                    }
                    for (int i = 0; i < listTermkey.size(); i++) {
                        for (int j = 0; j < allTerm.length; j++) {
                            if (listTermkey.get(i).equals(allTerm[j])) {
                                listTermfalg.set(i, true);
                            }
                        }
                    }
                }
                listDialog = new ListDialog(mContext, listTermvalue, listTermkey, listTermfalg, "续投期限", new ListDialogListener() {
                    @Override
                    public void onClick(int id, String value, String key, List<Boolean> falg) {
                        if (id == 1) {
                            continueTermTv.setText(value);
                            term = key;
                            listTermfalg = new ArrayList<>();
                            listTermfalg.addAll(falg);
                        }
                    }
                });

            }
        });
        //续投回款类型(1-等额本息3-一次性还本付息 4-等额本金 ,5按月付息到期还本 )
        continueRepaymentRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repayment = "";
                if (listRepayvalue == null && listRepaykey == null && listRepayfalg == null) {
                    listRepayvalue = new ArrayList<>();
                    listRepaykey = new ArrayList<>();
                    listRepayfalg = new ArrayList<>();
                    String[] allRepay = entity.getContinueRepayMode().split(",");
                    for (int i = 0; i < entity.getContinueRepayModeDatas().size(); i++) {
                        listRepayvalue.add(entity.getContinueRepayModeDatas().get(i).getValue());
                        listRepaykey.add(entity.getContinueRepayModeDatas().get(i).getKey());
                        listRepayfalg.add(false);
                    }
                    for (int i = 0; i < listRepaykey.size(); i++) {
                        for (int j = 0; j < allRepay.length; j++) {
                            if (listRepaykey.get(i).equals(allRepay[j])) {
                                listRepayfalg.set(i, true);
                            }
                        }
                    }
                }
                listDialog = new ListDialog(mContext, listRepayvalue, listRepaykey, listRepayfalg, "回款方式选择", new ListDialogListener() {
                    @Override
                    public void onClick(int id, String value, String key, List<Boolean> falg) {
                        if (id == 1) {
                            continueRepaymentTv.setText(value);
                            repayment = key;
                            listRepayfalg = new ArrayList<>();
                            listRepayfalg.addAll(falg);
                        }
                    }
                });
            }
        });
    }
}
