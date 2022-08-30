package com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui.yjjefqjqactivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yingjijiefasdfbbdr.dfgeryxfg.R;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.ToastYjjdFqjqUtil;
import com.victor.loading.rotate.RotateLoading;

import butterknife.BindView;

import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.YjjdFqjqStatusBarUtil;
import com.yingjijiefasdfbbdr.dfgeryxfg.mvp.XActivity;

public class FeedBackYjjdFqjqActivity extends XActivity {

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
    public void initData(Bundle savedInstanceState) {
        YjjdFqjqStatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("意见反馈");
        commitBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(feedbackEt.getText().toString().trim())){
                ToastYjjdFqjqUtil.showShort("请输入提交内容");
                return;
            }
            rotateLoading.start();
            loadingFl.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingFl.setVisibility(View.GONE);
                    rotateLoading.stop();
                    ToastYjjdFqjqUtil.showShort("提交成功");
                    finish();
                }
            }, 2000);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedbackdi_yjjdfqjq;
    }

    @Override
    public Object newP() {
        return null;
    }
}
