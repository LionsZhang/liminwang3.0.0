package com.example.administrator.lmw.mine.cumulative.fragment;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.inteface.ViewOnClick;
import com.example.administrator.lmw.mine.cumulative.entity.TotalAssetsEntity;
import com.example.administrator.lmw.mine.cumulative.utils.CumulativeHttp;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.Singlton;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 总资产
 * <p>
 * Created by Administrator on 2016/9/12 0012.
 */
public class TotalAssetsFragment extends BaseFragment {

    @InjectView(R.id.total_assets_iv)
    ImageView totalAssetsIv;
    @InjectView(R.id.total_assets_tv)
    TextView totalAssetsTv;
    @InjectView(R.id.total_assets_available_iv)
    ImageView totalAssetsAvailableIv;
    @InjectView(R.id.total_assets_available_tv)
    TextView totalAssetsAvailableTv;
    @InjectView(R.id.total_assets_freeze_iv)
    ImageView totalAssetsFreezeIv;
    @InjectView(R.id.total_assets_freeze_tv)
    TextView totalAssetsFreezeTv;
    @InjectView(R.id.total_assets_finan_iv)
    ImageView totalAssetsFinanIv;
    @InjectView(R.id.total_assets_finan_tv)
    TextView totalAssetsFinanTv;
    @InjectView(R.id.total_assets_claims_iv)
    ImageView totalAssetsClaimsIv;
    @InjectView(R.id.total_assets_claims_tv)
    TextView totalAssetsClaimsTv;
    @InjectView(R.id.total_assets_picchart)
    PieChart totalAssetsPicchart;
    private float quarterly1, quarterly2, quarterly3, quarterly4;
    private DecimalFormat df = new DecimalFormat("##0.00");

    @Override
    protected void initializeData() {

    }

    @Override
    protected void initializeView() {
        PieDatafindview();
        totalassetshttp();

    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_total_assets;
    }

    @OnClick({R.id.total_assets_iv, R.id.total_assets_available_iv, R.id.total_assets_claims_iv, R.id.total_assets_freeze_iv, R.id.total_assets_finan_iv})

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.total_assets_iv:// 总资产
                action("总资产", R.string.total_assets);
                break;
            case R.id.total_assets_available_iv:
                break;
            case R.id.total_assets_freeze_iv:// 冻结金额
                action("冻结金额说明", R.string.total_freeze);
                break;
            case R.id.total_assets_finan_iv:// 理财产品
                action("理财产品", R.string.total_finan);
                break;
            case R.id.total_assets_claims_iv:// 债权产品
                action("债权产品", R.string.total_claims);
                break;
        }
    }

    private void totalassetshttp() {
        Singlton.getInstance(CumulativeHttp.class).getTotalAssets(mContext, new OnResponseListener<BaseResult<TotalAssetsEntity>>() {
            @Override
            public void onSuccess(BaseResult<TotalAssetsEntity> totalAssetsEntity) {
                if (totalAssetsEntity == null) return;
                if (totalAssetsEntity.getData() == null) return;
                if (totalAssetsEntity.getData().getAssetInfo() == null) return;
                if (totalAssetsEntity.getCode().equals("0")) {
                    findview(totalAssetsEntity.getData());
                }
            }

            @Override
            public void onFail(Throwable e) {
            }
        });
    }

    /**
     * 初始化饼图
     */
    private void PieDatafindview() {
        quarterly1 = 0;
        quarterly2 = 0;
        quarterly3 = 0;
        quarterly4 = 0;

        PieData mPieData = getPieData(4, 100);
        showChart(totalAssetsPicchart, mPieData);
    }

    /**
     * 提示框
     */
    private void action(String str, int id) {
        String action = getString(id);
        Singlton.getInstance(PopWindowManager.class).
                showInvestDialog(mContext, str, action, "我知道了", new ViewOnClick() {
                    @Override
                    public void onClick(int msgID, View v, Object... obj) {
                        if (msgID == 2) {

                        }
                    }
                });
    }

    /**
     * 实例化数据
     *
     * @param totalAssetsEntity
     */
    private void findview(TotalAssetsEntity totalAssetsEntity) {
        /**
         * 总资产金额
         */
        totalAssetsTv.setText(df.format(totalAssetsEntity.getAssetInfo().getTotalAssets()));
        /**
         * 可用余额
         */
        totalAssetsAvailableTv.setText(df.format(totalAssetsEntity.getAssetInfo().getUsableAmount()));
        /**
         * 冻结金额
         */
        totalAssetsFreezeTv.setText(df.format(totalAssetsEntity.getAssetInfo().getFrozenAmount()));
        /**
         * 理财产品
         */
        totalAssetsFinanTv.setText(df.format(totalAssetsEntity.getAssetInfo().getFinanceProductAmount()));
        /**
         * 债权 产品
         */
        totalAssetsClaimsTv.setText(df.format(totalAssetsEntity.getAssetInfo().getDebtProductAmount()));

        /**
         * 初始化数据百分比
         */
        quarterly1 = (float) totalAssetsEntity.getAssetInfo().getUsableAmountRatio();
        quarterly2 = (float) totalAssetsEntity.getAssetInfo().getFrozenAmountRatio();
        quarterly3 = (float) totalAssetsEntity.getAssetInfo().getFinanceProductAmountRatio();
        quarterly4 = (float) totalAssetsEntity.getAssetInfo().getDebtProductAmountRatio();

        PieData mPieData = getPieData(4, 100);
        showChart(totalAssetsPicchart, mPieData);


    }

    private void showChart(PieChart pieChart, PieData pieData) {
//        pieChart.setHoleColorTransparent(true);
//        pieChart.setDrawHoleEnabled(true);

        pieChart.setHoleRadius(93f);  //半径
        pieChart.setTransparentCircleRadius(80f); // 半透明圈
        //pieChart.setHoleRadius(0)  //实心圆

        pieChart.setDescription("");
        pieData.setValueTextSize(7f);

        pieData.setDrawValues(false);

        // mChart.setDrawYValues(true);
        pieChart.setDrawCenterText(false);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(270); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(false); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(false);  //显示成百分比
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        pieChart.setTouchEnabled(false);// 是否可触摸

//      mChart.setOnAnimationListener(this);

        pieChart.setCenterText("");  //饼状图中间的文字

        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);  //最右边显示
//        mLegend.setForm(Legend.LegendForm.CIRCLE);  //设置比例图的形状，默认是方形
        mLegend.setEnabled(false); // 是否显示表格
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }


    /**
     * @param count 分成几部分
     * @param range
     */
    @TargetApi(Build.VERSION_CODES.M)
    private PieData getPieData(int count, float range) {

        List<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容

        xValues.add("");
        xValues.add("");
        xValues.add("");
        xValues.add("");
//        xValues.add(df.format(mon1*100) + "%");
//        xValues.add(df.format(mon2*100) + "%");
//        xValues.add(df.format(mon3*100) + "%");
//        xValues.add(df.format(mon4*100) + "%");//饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4
        List<PieEntry> yValues = new ArrayList<PieEntry>();  //yVals用来表示封装每个饼块的实际数据

        // 饼图数据
//        float quarterly1 = 14;
//        float quarterly2 = 14;
//        float quarterly3 = 34;
//        float quarterly4 = 38;

//        float quarterly1 = (float) mon1 * 100;
//        float quarterly2 = (float) mon2 * 100;
//        float quarterly3 = (float) mon3 * 100;
//        float quarterly4 = (float) mon4 * 100;

        yValues.add(new PieEntry(quarterly1, 0));
        yValues.add(new PieEntry(quarterly2, 1));
        yValues.add(new PieEntry(quarterly3, 2));
        yValues.add(new PieEntry(quarterly4, 3));

        //y轴的集合
//        PieDataSet pieDataSet = new PieDataSet(yValues, "Quarterly Revenue 2014"/*显示在比例图上*/);
        PieDataSet pieDataSet = new PieDataSet(yValues, "");
        pieDataSet.setSliceSpace(1f); //设置个饼状图之间的距离

        ArrayList<Integer> c = new ArrayList<Integer>();

        // 饼图颜色
        c.add(Color.rgb(98, 158, 255)); // 蓝色
        c.add(Color.rgb(255, 63, 64)); // 红色
        c.add(Color.rgb(0, 211, 152)); // 绿色
        c.add(Color.rgb(254, 214, 0)); // 黄色

        pieDataSet.setColors(c);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(pieDataSet);


        return pieData;
    }

}
