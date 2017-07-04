package com.example.administrator.lmw.mine.more.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.more.http.Morehttp;
import com.example.administrator.lmw.utils.Singlton;

import butterknife.InjectView;

/**
 * 意见反馈
 * <p>
 * Created by Administrator on 2016/9/18 0018.
 */
public class FeedbackActivity extends BaseActivity {

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
    @InjectView(R.id.feedback_et)
    EditText feedbackEt;
    @InjectView(R.id.feedback_tv)
    TextView feedbackTv;
    @InjectView(R.id.feedback_share)
    Button feedbackShare;

    @Override
    protected void initializeData() {

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initializeView() {
        titlebar();
        feedbackEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    feedbackTv.setText("0/500");
                } else {
                    feedbackTv.setText(s.length() + "/500");
                }
            }
        });
        // 提交
        feedbackShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = feedbackEt.getText().toString();
                if (str.length() > 0) {
                    getSuggestion(str, "意见反馈", "2");
                }
            }
        });

    }

    /**
     * 标题初始化
     */
    private void titlebar() {
        barIconBack.setImageResource(R.drawable.nav_back);
        barTitle.setText("意见反馈");
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity(FeedbackActivity.this);
            }
        });
    }

    private void getSuggestion(String adviseContent, String adviseTitle, String adviseType) {
        Singlton.getInstance(Morehttp.class).getSuggestion(mContext, adviseContent, adviseTitle, adviseType, new OnResponseListener<BaseResult<Object>>() {
            @Override
            public void onSuccess(BaseResult<Object> baseResponse) {
                int code = Integer.valueOf(baseResponse.getCode());
                if (code == 0) {
                    showToast("感谢您的宝贵建议");
                    finishActivity(FeedbackActivity.this);
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });

    }

}
