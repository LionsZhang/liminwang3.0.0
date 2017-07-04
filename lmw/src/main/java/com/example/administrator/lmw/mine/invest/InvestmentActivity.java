package com.example.administrator.lmw.mine.invest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.DespositAccountManager;
import com.example.administrator.lmw.entity.BaseEvent;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.entity.ShowFinanceFragmentEvent;
import com.example.administrator.lmw.entity.ShowMineFragmentEvent;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.main.MainActivity;
import com.example.administrator.lmw.mine.invest.activity.TransferRewardActivity;
import com.example.administrator.lmw.mine.invest.adapter.InvestmentTransferAdapter;
import com.example.administrator.lmw.mine.invest.entity.InvestmentCountEntity;
import com.example.administrator.lmw.mine.invest.entity.InvestmentTransferDatasBean;
import com.example.administrator.lmw.mine.invest.entity.InvestmentTransferEntity;
import com.example.administrator.lmw.mine.invest.utils.IdCardDialog;
import com.example.administrator.lmw.mine.invest.utils.InvestmentActivityEvent;
import com.example.administrator.lmw.mine.invest.utils.InvestmentHttp;
import com.example.administrator.lmw.mine.invest.utils.OnIdCardDialogListener;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.utils.DateUtil;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.view.XListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 我的投资
 * <p>
 * Created by Administrator on 2016/8/24 0024.
 */
public class InvestmentActivity extends BaseActivity implements XListView.IXListViewListener {


    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.back)
    TextView back;
    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.back_layout)
    LinearLayout backLayout;
    @InjectView(R.id.right_title)
    TextView rightTitle;
    @InjectView(R.id.right_icon)
    ImageView rightIcon;
    @InjectView(R.id.title_bar_right_layout)
    LinearLayout titleBarRightLayout;
    @InjectView(R.id.invest_project_lin)
    LinearLayout investProjectLin;
    @InjectView(R.id.invest_project_tv)
    TextView investProjectTv;
    @InjectView(R.id.invest_project_iv)
    ImageView investProjectIv;
    @InjectView(R.id.invest_ment_tv)
    TextView investMentTv;
    @InjectView(R.id.invest_ment_iv)
    ImageView investMentIv;
    @InjectView(R.id.invest_ment_lin)
    LinearLayout investMentLin;
    @InjectView(R.id.invest_transfer_tv)
    TextView investTransferTv;
    @InjectView(R.id.invest_transfer_iv)
    ImageView investTransferIv;
    @InjectView(R.id.invest_transfer_lin)
    LinearLayout investTransferLin;
    @InjectView(R.id.invest_section_tv)
    TextView investSectionTv;
    @InjectView(R.id.invest_section_iv)
    ImageView investSectionIv;
    @InjectView(R.id.invest_section_lin)
    LinearLayout investSectionLin;
    @InjectView(R.id.invest_listview)
    XListView listview;
    @InjectView(R.id.transfer_no)
    TextView transferNo;
    @InjectView(R.id.transfer_launch)
    TextView transferLaunch;
    @InjectView(R.id.transfer_reward)
    TextView transferReward;
    @InjectView(R.id.transfer_lin)
    LinearLayout transferLin;
    @InjectView(R.id.invest_lin)
    LinearLayout investLin;
    @InjectView(R.id.transfer_bottom)
    LinearLayout transferBottom;
    @InjectView(R.id.invest_btn)
    Button investBtn;
    private PopupWindow popupWindow;
    private View view;
    public static int screen_width = 0;
    public static int screen_height = 0;
    View showPupWindow = null; // 选择区域的view

    TranslateAnimation animation;// 出现的动画效果
    private boolean[] tabStateArr = new boolean[4];// 标记tab的选中状态，方便设置
    private LinearLayout invest_one_lin, invest_two_lin, invest_three_lin, invest_four_lin;
    private ImageView invest_one_iv, invest_two_iv, invest_three_iv, invest_four_iv;
    private TextView invest_one_tv, invest_two_tv, invest_three_tv, invest_four_tv;
    private int subValue = 0;
    private int proType = 0;
    private int subType = 1;
    private Handler handler;
    private int pageIndex = 1;
    private int pageSize = 10;
    private int pageCount = 0;
    private InvestmentTransferAdapter adapter;
    private boolean falg = false;
    private int debtCount = 0;
    private List<InvestmentTransferDatasBean> datasBeen;
    private String titleId;
    private int i = -1;

    @Override
    protected void initializeData() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent baseEvent) {
        if (baseEvent != null) {
            if (baseEvent instanceof InvestmentActivityEvent) {
                if (listview != null) {
                    listview.setRefreshTime(DateUtil.getRefreshTime());
                    pageIndex = 1;
                    if (subType == 2 && subValue == 1) {
                        getInvestmentCount(proType, subType, subValue);
                    }
                    getInvestmenthttp(pageIndex, pageSize, proType, subType, subValue);

                    listview.stopRefresh();
                }
            }

        }
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_investment;
    }

    @Override
    protected void initializeView() {
        transferLin.setVisibility(View.GONE);
        http();
        popuwindow();
        xlist();
        titlebar();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (subType == 2 && subValue == 1) {
                    if (falg) {
                        if (i == (position - 1)) {
                            adapter.InvestmentNo(-1);
                            adapter.notifyDataSetChanged();
                            titleId = "";
                            i = -1;
                        } else {
                            adapter.InvestmentNo(position - 1);
                            adapter.notifyDataSetChanged();
                            titleId = datasBeen.get(position - 1).getInvestId();
                            i = (position - 1);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        subValue = 0;
//        proType = 0;
//        subType = 1;
//        pageIndex = 1;
//        pageSize = 10;
//        pageCount = 0;
//        falg = false;
//        debtCount = 0;
//        i = -1;
//        transferLin.setVisibility(View.GONE);
//        http();
    }

    private void http() {
        titleId = "";
        if (subType == 1) {// 判断是否显示 待结卡券奖励  可转让 按钮
            transferBottom.setVisibility(View.VISIBLE);
            transferLin.setVisibility(View.GONE);
            transferReward.setVisibility(View.VISIBLE);
        } else if (subValue == 1 && subType == 2) {
            transferBottom.setVisibility(View.VISIBLE);
            transferLin.setVisibility(View.VISIBLE);
            transferReward.setVisibility(View.GONE);
        } else {
            transferBottom.setVisibility(View.GONE);
        }
        getInvestmenthttp(pageIndex, pageSize, proType, subType, subValue);
    }

    @OnClick({R.id.invest_project_lin, R.id.invest_ment_lin, R.id.invest_transfer_lin, R.id.invest_section_lin, R.id.transfer_launch, R.id.transfer_no, R.id.invest_btn, R.id.transfer_reward})

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.invest_project_lin:
                investProjectTv.setTextColor(ContextCompat.getColor(mContext, R.color.bule_press));
                investMentTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
                investTransferTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
                investSectionTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
                findpopwindow("全部类型", "理财产品", "债权产品", "", 3, 0);
                break;
            case R.id.invest_ment_lin:
                investProjectTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
                investMentTv.setTextColor(ContextCompat.getColor(mContext, R.color.bule_press));
                investTransferTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
                investSectionTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
                findpopwindow("全部类型", "投资中", "计息中", "", 3, 1);
                break;
            case R.id.invest_transfer_lin:
                investProjectTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
                investMentTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
                investTransferTv.setTextColor(ContextCompat.getColor(mContext, R.color.bule_press));
                investSectionTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
                findpopwindow("全部类型", "可转让", "转让中", "转让过", 4, 2);
                break;
            case R.id.invest_section_lin:
                investProjectTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
                investMentTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
                investTransferTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
                investSectionTv.setTextColor(ContextCompat.getColor(mContext, R.color.bule_press));
                findpopwindow("全部类型", "投资回款", "转让回款", "流标回款", 4, 3);
                break;
            case R.id.transfer_launch:
                if (falg && Singlton.getInstance(DespositAccountManager.class).isOpenDesposit(mContext)) {//判断是否开通存管
                    if (titleId.equals("")) {
                        showToast("请选择需要转让的标");
                    } else {
                        Intent intent = new Intent(mContext, ConfirmTransferActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("titleId", titleId);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                } else {
                    falg = true;
                    transferLaunch.setText("转让");
                    transferNo.setText("取消");
                    adapter.InvestmentSelect(true);
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.transfer_no:
                //如果选择，取消选中状态,重置参数
                if (falg) {
                    if (i != -1) {
                        adapter.InvestmentNo(-1);
                        i = -1;
                    }
                }
                falg = false;
                transferLaunch.setText("发起转让");
                transferNo.setText("可转让(个)：" + debtCount);
//                getInvestmentCount(proType, subType, subValue, SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, ""));
                adapter.InvestmentSelect(false);
                adapter.notifyDataSetChanged();
                break;
            case R.id.invest_btn:
                // startActivity(MainActivity.class);
                finishActivity(InvestmentActivity.this);
                EventBus.getDefault().post(new ShowFinanceFragmentEvent());
                break;
            case R.id.transfer_reward:// 待结卡券奖励
                startActivity(TransferRewardActivity.class);
                break;
        }
    }

    /**
     * 标题初始化
     */
    private void titlebar() {
        barTitle.setText("我的投资");
        barIconBack.setImageResource(R.drawable.nav_back);
        // 返回
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.class);
                EventBus.getDefault().post(new ShowMineFragmentEvent());
            }
        });
    }

    /**
     * 重写返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(MainActivity.class);
            EventBus.getDefault().post(new ShowMineFragmentEvent());
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     *
     */
    private void popuwindow() {
        int[] location = new int[2];
        investProjectLin.getLocationOnScreen(location);
        animation = new TranslateAnimation(0, 0, -700, location[1]);
        animation.setDuration(500);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screen_width = dm.widthPixels;
        screen_height = dm.heightPixels * 2;
    }

    /**
     * @param view
     */
    private void showPopWindow(View view) {

        /* 第一个参数弹出显示view 后两个是窗口大小 */
        popupWindow = new PopupWindow(view, screen_width, screen_height);
        /* 设置背景显示 */
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.dotted_line));
        /* 设置触摸外面时消失 */
        // mPopupWindow.setOutsideTouchable(true);

        popupWindow.update();
        popupWindow.setTouchable(true);
        /* 设置点击menu以外其他地方以及返回键退出 */
        popupWindow.setFocusable(true);

        /**
         * 1.解决再次点击MENU键无反应问题 2.sub_view是PopupWindow的子View
         */
        view.setFocusableInTouchMode(true);
    }

    /**
     * 初始化 pop
     *
     * @param investone
     * @param investtwo
     * @param investthree
     * @param investfour
     * @param investno
     * @param pictureno
     */
    private void findpopwindow(String investone, String investtwo, String investthree, String investfour, int investno, final int pictureno) {
        if (popupWindow == null) {
            showPupWindow = LayoutInflater.from(mContext).inflate(
                    R.layout.dialog_investment_four, null);
            invest_one_lin = (LinearLayout) showPupWindow.findViewById(R.id.invest_one_lin);
            invest_one_iv = (ImageView) showPupWindow.findViewById(R.id.invest_one_iv);
            invest_one_tv = (TextView) showPupWindow.findViewById(R.id.invest_one_tv);
            invest_two_lin = (LinearLayout) showPupWindow.findViewById(R.id.invest_two_lin);
            invest_two_iv = (ImageView) showPupWindow.findViewById(R.id.invest_two_iv);
            invest_two_tv = (TextView) showPupWindow.findViewById(R.id.invest_two_tv);
            invest_three_lin = (LinearLayout) showPupWindow.findViewById(R.id.invest_three_lin);
            invest_three_iv = (ImageView) showPupWindow.findViewById(R.id.invest_three_iv);
            invest_three_tv = (TextView) showPupWindow.findViewById(R.id.invest_three_tv);
            invest_four_lin = (LinearLayout) showPupWindow.findViewById(R.id.invest_four_lin);
            invest_four_iv = (ImageView) showPupWindow.findViewById(R.id.invest_four_iv);
            invest_four_tv = (TextView) showPupWindow.findViewById(R.id.invest_four_tv);
            showPopWindow(showPupWindow);
        }
        /**
         * 初始化文字
         */
        if (!TextUtils.isEmpty(investone))
            invest_one_tv.setText(investone);
        if (!TextUtils.isEmpty(investtwo))
            invest_two_tv.setText(investtwo);
        if (!TextUtils.isEmpty(investthree))
            invest_three_tv.setText(investthree);
        if (!TextUtils.isEmpty(investfour))
            invest_four_tv.setText(investfour);
        /**
         * 初始化 item数目
         */
        if (investno == 3) {
            invest_four_lin.setVisibility(View.GONE);
        } else if (investno == 4) {
            invest_four_lin.setVisibility(View.VISIBLE);
        }

        if (pictureno == 0) {
            popfindview(proType);

        } else if (pictureno == subType) {
            popfindview(subValue);
        } else {
            popfindview(-1);
        }
        /**
         * 点击事件
         */
        // 第一个
        invest_one_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                pageIndex = 1;
                if (pictureno == 0) {
                    proType = 0;
                    subType = 1;
                    subValue = 0;
                } else {
                    subValue = 0;
                    subType = pictureno;
                }
                http();
            }
        });
        // 第二个
        invest_two_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                pageIndex = 1;
                if (pictureno == 0) {
                    proType = 1;
                    subType = 1;
                    subValue = 0;
                } else {
                    subValue = 1;
                    subType = pictureno;
                    if (pictureno == 2) {
                        getInvestmentCount(proType, subType, subValue);
                    }
                }
                http();
            }
        });
        // 第三个
        invest_three_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                pageIndex = 1;
                if (pictureno == 0) {
                    proType = 2;
                    subType = 1;
                    subValue = 0;
                } else {
                    subValue = 2;
                    subType = pictureno;
                }
                http();
            }
        });
        // 第四个
        invest_four_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                pageIndex = 1;
                if (pictureno == 0) {
                    proType = 3;
                    subType = 1;
                    subValue = 0;
                } else {
                    subValue = 3;
                    subType = pictureno;
                }
                http();
            }
        });

        showPupWindow.setAnimation(animation);
        showPupWindow.startAnimation(animation);
        popupWindow.showAsDropDown(investProjectLin, -5, 10);
    }

    /**
     * 初始化选择框
     */
    private void popfindview(int id) {

        switch (id) {
            case 0:
                invest_one_iv.setVisibility(View.VISIBLE);
                invest_two_iv.setVisibility(View.INVISIBLE);
                invest_three_iv.setVisibility(View.INVISIBLE);
                invest_four_iv.setVisibility(View.INVISIBLE);
                invest_one_tv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                invest_two_tv.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
                invest_three_tv.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
                invest_four_tv.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
                break;
            case 1:
                invest_one_iv.setVisibility(View.INVISIBLE);
                invest_two_iv.setVisibility(View.VISIBLE);
                invest_three_iv.setVisibility(View.INVISIBLE);
                invest_four_iv.setVisibility(View.INVISIBLE);
                invest_one_tv.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
                invest_two_tv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                invest_three_tv.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
                invest_four_tv.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
                break;
            case 2:
                invest_one_iv.setVisibility(View.INVISIBLE);
                invest_two_iv.setVisibility(View.INVISIBLE);
                invest_three_iv.setVisibility(View.VISIBLE);
                invest_four_iv.setVisibility(View.INVISIBLE);
                invest_one_tv.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
                invest_two_tv.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
                invest_three_tv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                invest_four_tv.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
                break;
            case 3:
                invest_one_iv.setVisibility(View.INVISIBLE);
                invest_two_iv.setVisibility(View.INVISIBLE);
                invest_three_iv.setVisibility(View.INVISIBLE);
                invest_four_iv.setVisibility(View.VISIBLE);
                invest_one_tv.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
                invest_two_tv.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
                invest_three_tv.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
                invest_four_tv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                break;
            default:
                invest_one_iv.setVisibility(View.INVISIBLE);
                invest_two_iv.setVisibility(View.INVISIBLE);
                invest_three_iv.setVisibility(View.INVISIBLE);
                invest_four_iv.setVisibility(View.INVISIBLE);
                invest_one_tv.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
                invest_two_tv.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
                invest_three_tv.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
                invest_four_tv.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
                break;
        }
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listview != null) {
                    listview.setRefreshTime(DateUtil.getRefreshTime());
                    pageIndex = 1;
                    if (subType == 2 && subValue == 1) {
                        getInvestmentCount(proType, subType, subValue);
                    }
                    getInvestmenthttp(pageIndex, pageSize, proType, subType, subValue);

                    listview.stopRefresh();
                }
            }
        }, 1000);

    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listview != null) {
                    pageIndex++;
                    if (pageIndex > pageCount) {
                        showToast("没有更多数据");
                    } else {
                        getInvestmenthttp(pageIndex, pageSize, proType, subType, subValue);
                    }
                    listview.stopLoadMore();
                }
            }
        }, 1000);

    }

    private void xlist() {
        if (listview != null) {
            handler = new Handler();
            listview.setPullRefreshEnable(true);
            listview.setPullLoadEnable(true);
            listview.setXListViewListener(this);
            listview.setRefreshTime(DateUtil.getRefreshTime());
            listview.setAdapter(adapter);
        }
    }


    /**
     * @param pageIndex
     * @param pageSize
     * @param proType
     * @param subType
     * @param subValue
     */
    private void getInvestmenthttp(final int pageIndex, int pageSize, int proType, final int subType, final int subValue) {
        Singlton.getInstance(InvestmentHttp.class).getInvestmentTransfer(mContext, pageIndex, pageSize, proType, subType, subValue, new OnResponseListener<BaseResult<InvestmentTransferEntity>>() {
            @Override
            public void onSuccess(BaseResult<InvestmentTransferEntity> entity) {
                if (listview == null) return;
                if (entity == null) return;
                if (entity.getData() == null) return;
                if (entity.getData().getDatas() == null) return;
                if (TextUtils.equals(entity.getCode(), "0")) {
                    if (pageIndex == 1) {
                        datasBeen = new ArrayList<InvestmentTransferDatasBean>();
                        if (entity.getData().getDatas().size() < 1) {
                            investLin.setVisibility(View.VISIBLE);
                            transferLin.setVisibility(View.GONE);
                            listview.setVisibility(View.GONE);
                        } else {
                            investLin.setVisibility(View.GONE);
                            listview.setVisibility(View.VISIBLE);
                            if (subType == 2 && subValue == 1) {
                                transferLin.setVisibility(View.VISIBLE);
                            } else {
                                transferLin.setVisibility(View.GONE);
                            }

                        }
                        if (entity.getData().getDatas().size() < 10) {
                            listview.setPullLoadEnable(false);
                        } else {
                            listview.setPullLoadEnable(true);
                        }
                        datasBeen.addAll(entity.getData().getDatas());
                        adapter = new InvestmentTransferAdapter(mContext, entity.getData().getDatas(), R.layout.invest_financial_item_layout, listener, subType);
                        listview.setAdapter(adapter);
                    } else {
                        if (entity.getData().getDatas().size() > 0) {
                            datasBeen.addAll(entity.getData().getDatas());
                            adapter.addInvestmentTransfer(entity.getData().getDatas());
                            adapter.notifyDataSetChanged();
                        } else {
                            listview.setPullLoadEnable(false);
                        }
                    }
                    pageCount = entity.getData().getPageCount();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (listview != null) {
                    List<InvestmentTransferDatasBean> investmentTransferDatas = new ArrayList<InvestmentTransferDatasBean>();
                    adapter = new InvestmentTransferAdapter(mContext, investmentTransferDatas, R.layout.invest_financial_item_layout, listener, subType);
                    listview.setAdapter(adapter);
                }
            }
        });

    }

    /**
     * @param proType
     * @param subType
     * @param subValue
     */
    private void getInvestmentCount(int proType, int subType, int subValue) {
        Singlton.getInstance(InvestmentHttp.class).getInvestmentCount(mContext, proType, subType, subValue, new OnResponseListener<BaseResult<InvestmentCountEntity>>() {
            @Override
            public void onSuccess(BaseResult<InvestmentCountEntity> investmentCountEntity) {
                if (investmentCountEntity == null) return;
                if (investmentCountEntity.getData() == null) return;
                if (TextUtils.equals(investmentCountEntity.getCode(), "0")) {
                    falg = false;
                    transferLaunch.setText("发起转让");
                    debtCount = investmentCountEntity.getData().getDebtCount();
                    transferNo.setText("可转让(个)：" + investmentCountEntity.getData().getDebtCount());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 协议监听
     */
    private InvestmentTransferAdapter.InvestmentListener listener = new InvestmentTransferAdapter.InvestmentListener() {
        @Override
        public void onclick(int position, String url) {
            idhttp(url);

        }
    };

    /**
     *
     */
    private void idhttp(final String url) {
        new IdCardDialog(this, "为确保账号信息安全，请进行身份验证", "取消", "确认", new OnIdCardDialogListener() {
            @Override
            public void onClick(int id, String psw) {
                switch (id) {
                    case 0:// 取消
                        break;
                    case 1:// 确认
                        if (psw.length() == 6) {
                            getIdCard(psw, url);
                        } else {
                            showToast("请输入身份证后6位");
                        }
                        break;
                    case 2:
                        break;
                }
            }
        });
    }

    /**
     * 校验身份证接口
     *
     * @param idNoSuffix
     */
    private void getIdCard(String idNoSuffix, final String url) {
        Singlton.getInstance(InvestmentHttp.class).getIdCard(mContext, idNoSuffix, new OnResponseListener<BaseResult<String>>() {
            @Override
            public void onSuccess(BaseResult<String> baseResponse) {
                if (baseResponse == null) return;
                if (TextUtils.equals(baseResponse.getCode(), "0")) {
                    Intent intent = new Intent(mContext, WebViewMore.class);
                    intent.putExtra("url", url);
                    intent.putExtra("title", "协议");
                    startActivity(intent);
                } else {
                    showToast(baseResponse.getMsg());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }
}
