package com.example.administrator.lmw.mine.cumulative.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.base.BasePagerAdapter;
import com.example.administrator.lmw.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 累计收益
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/3/14 11:21
 **/
public class AccumulatedEarningsFragment extends BaseFragment {

    public static final String INTENT_KEY_INDEX = "intent_key_index";

    @InjectView(R.id.tab_top)
    PagerSlidingTabStrip mTabStrip;
    @InjectView(R.id.vp_pager)
    ViewPager mViewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>(4);
    private int cumindexs = 0;

    @Override
    protected void initializeData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            cumindexs = bundle.getInt(INTENT_KEY_INDEX, 0);
        }
    }


    @Override
    protected int getContentLayout() {
        return R.layout.fragment_accumulated_earnings;
    }

    @Override
    protected void initializeView() {
        initTitles();
        initFragments();
        initViewPaper();
    }

    private void initTitles() {
        mTitles.add("全部");
        mTitles.add("投资收益");
        mTitles.add("活动奖励");
        mTitles.add("邀请奖励");
    }

    /**
     * 初始化fragment
     */
    private void initFragments() {
        TotalCunulativeFragment totalCunulativeFragment = new TotalCunulativeFragment();
        InvestmentCumulativeFragment investmentCumulativeFragment = new InvestmentCumulativeFragment();
        CampaignCumulativeFragment campaignCumulativeFragment = new CampaignCumulativeFragment();
        InvitationCumulativeFragment invitationCumulativeFragment = new InvitationCumulativeFragment();


        mFragments.add(totalCunulativeFragment);
        mFragments.add(investmentCumulativeFragment);
        mFragments.add(campaignCumulativeFragment);
        mFragments.add(invitationCumulativeFragment);

    }

    private void initViewPaper() {
        mViewPager.setAdapter(new BasePagerAdapter(getChildFragmentManager(), mFragments, mTitles));
        mViewPager.setCurrentItem(cumindexs);
        mViewPager.setOffscreenPageLimit(3);
        mTabStrip.setViewPager(mViewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


}
