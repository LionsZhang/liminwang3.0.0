package com.example.administrator.lmw.mine.card;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BasePagerAdapter;
import com.example.administrator.lmw.base.BaseTitleActivity;
import com.example.administrator.lmw.mine.card.cardhttp.CouponType;
import com.example.administrator.lmw.mine.card.cardhttp.QueryStatus;
import com.example.administrator.lmw.mine.card.fragment.CashCouponFragment;
import com.example.administrator.lmw.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;


/**
 * 已使用和过期的优惠券的列表页面
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/2/14 17:48
 **/
public class CardHistoryListActivity extends BaseTitleActivity {

    public static final String INTENT_KEY_TITLE = "intent_key_title";//页面的标题

    private ArrayList<Fragment> fragmentList;

    @InjectView(R.id.tab_top)
    PagerSlidingTabStrip mTabStrip;
    @InjectView(R.id.viewpager_vp)
    ViewPager viewpagerVp;

    private String mTitle;//标题
    private CouponType mCouponType = CouponType.CASH_COUPON;//卡券类型(0现金券;1:红包;2抵扣券;3加息券;4理财金)

    @Override
    protected void initializeData() {
        super.initializeData();
        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_KEY_TITLE)){
            mTitle = intent.getStringExtra(INTENT_KEY_TITLE);
            if(!TextUtils.isEmpty(mTitle)){
                mTitle=mTitle.substring(2);
            }
        }

        if (intent.hasExtra(CashCouponFragment.CARD_COUPON_TYPE))
            mCouponType = (CouponType) intent.getSerializableExtra(CashCouponFragment.CARD_COUPON_TYPE);
    }

    @Override
    protected void initializeView() {
        initTabViewPager();
    }

    @Override
    protected void setTitleContentView() {
        initView(mTitle, R.layout.activity_card);
    }

    /*
     * 初始化tab导航ViewPager
     */
    public void initTabViewPager() {
        fragmentList = new ArrayList<Fragment>(2);

        Bundle bundle = new Bundle();
        bundle.putSerializable(CashCouponFragment.CARD_COUPON_STATUS, QueryStatus.USED);
        bundle.putSerializable(CashCouponFragment.CARD_COUPON_TYPE, mCouponType);
        CashCouponFragment usedCashCoupon = (CashCouponFragment) CashCouponFragment.newInstance(bundle, CashCouponFragment.class);
        fragmentList.add(usedCashCoupon);

        Bundle args = new Bundle();
        args.putSerializable(CashCouponFragment.CARD_COUPON_STATUS, QueryStatus.EXPIRED);
        args.putSerializable(CashCouponFragment.CARD_COUPON_TYPE, mCouponType);
        CashCouponFragment stableCashCoupon = (CashCouponFragment) CashCouponFragment.newInstance(args, CashCouponFragment.class);
        fragmentList.add(stableCashCoupon);

        List<String> titles = new ArrayList<>(2);
        titles.add(getResources().getString(R.string.txt_used));
        titles.add(getResources().getString(R.string.txt_stable));


        //给ViewPager设置适配器
        viewpagerVp.setAdapter(new BasePagerAdapter(getSupportFragmentManager(), fragmentList, titles));
        viewpagerVp.setCurrentItem(0);//设置当前显示标签页为第一页
        mTabStrip.setViewPager(viewpagerVp);
    }


}
