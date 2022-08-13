package com.chenqi.lecheng.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenqi.lecheng.R;
import com.chenqi.lecheng.utils.SharedPreferencesYouXinUtilis;
import com.victor.loading.rotate.RotateLoading;

import butterknife.BindView;
import com.chenqi.lecheng.utils.StatusBarYouXinUtil;
import com.chenqi.lecheng.utils.ToastYouXinUtil;
import com.chenqi.lecheng.mvp.XActivity;

public class FeedBackYouXinActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView  titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.feedback_et)
    EditText feedbackEt;
    @BindView(R.id.commit_btn)
    TextView commitBtn;
    @BindView(R.id.rotate_loading)
    RotateLoading rotateLoading;
    @BindView(R.id.loading_fl)
    View loadingFl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (SharedPreferencesYouXinUtilis.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusBarYouXinUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("意见反馈");
        commitBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(feedbackEt.getText().toString().trim())){
                ToastYouXinUtil.showShort("请输入提交内容");
                return;
            }
            rotateLoading.start();
            loadingFl.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingFl.setVisibility(View.GONE);
                    rotateLoading.stop();
                    ToastYouXinUtil.showShort("提交成功");
                    finish();
                }
            }, 2000);
        });
    }

}
