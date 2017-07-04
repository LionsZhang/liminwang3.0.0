package com.example.administrator.lmw.finance.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.finance.utils.CustViewPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public class MoreFragment extends BaseFragment implements View.OnClickListener {
    @InjectView(R.id.details_tv)
    TextView detailsTv;
    @InjectView(R.id.details_blue_iv)
    ImageView detailsBlueIv;
    @InjectView(R.id.financial_details_lin)
    LinearLayout financialDetailsLin;
    @InjectView(R.id.problem_tv)
    TextView problemTv;
    @InjectView(R.id.problem_blue_iv)
    ImageView problemBlueIv;
    @InjectView(R.id.financial_problem_lin)
    LinearLayout financialProblemLin;
    @InjectView(R.id.record_tv)
    TextView recordTv;
    @InjectView(R.id.record_blue_iv)
    ImageView recordBlueIv;
    @InjectView(R.id.financial_record_lin)
    LinearLayout financialRecordLin;
    @InjectView(R.id.viewpager)
    CustViewPage viewpager;
    private List<Fragment> mFragments = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void initializeData() {

    }

    @Override
    protected void initializeView() {
        findview();

        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }
        };
        viewpager.setAdapter(mAdapter);
        viewpager.setOffscreenPageLimit(2);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private int currentIndex;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // TODO: 2016/8/23 0023   需要补全数据
            @Override
            public void onPageSelected(int position) {
                resetImage();
                switch (position) {
                    case 0:
                        detailsTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue));
                        break;
                    case 1:
                        problemTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue));
                        break;
                    case 2:
                        recordTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue));
                        break;
                }
                currentIndex = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_finance_more;
    }

    // TODO: 2016/8/23 0023   需要补全数据
    private void resetImage() {
        detailsTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
        problemTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
        recordTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.financial_details_lin:// 产品详情
                resetImage();
                detailsTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue));
                viewpager.setCurrentItem(0);
                break;
            case R.id.financial_problem_lin:// 常见问题
                resetImage();
                problemTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue));
                viewpager.setCurrentItem(1);
                break;
            case R.id.financial_record_lin:// 投资记录
                resetImage();
                recordTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue));
                viewpager.setCurrentItem(2);
                break;
        }
    }

    /**
     * 实例化
     */

    private void findview() {

        ProductDetail productDetail = new ProductDetail();
        CommonProblem commonProblem = new CommonProblem();
        InvestmentRecord investmentRecord = new InvestmentRecord();
        financialDetailsLin.setOnClickListener(this);
        financialProblemLin.setOnClickListener(this);
        financialRecordLin.setOnClickListener(this);

        mFragments.add(productDetail);
        mFragments.add(commonProblem);
        mFragments.add(investmentRecord);

        detailsTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue));
    }
}
