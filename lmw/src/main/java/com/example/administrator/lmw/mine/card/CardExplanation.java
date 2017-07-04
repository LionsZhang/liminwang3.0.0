package com.example.administrator.lmw.mine.card;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;

import butterknife.InjectView;

/**
 * 红包使用说明
 * <p/>
 * Created by Administrator on 2016/9/9 0009.
 */
public class CardExplanation extends BaseActivity {
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

    @Override
    protected void initializeData() {

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_card_explanation;
    }

    @Override
    protected void initializeView() {
        titlebar();

    }

    /**
     * 标题初始化
     */
    private void titlebar() {
        barTitle.setText("使用说明");
        barIconBack.setImageResource(R.drawable.nav_back);
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity(CardExplanation.this);
            }
        });

    }

}
