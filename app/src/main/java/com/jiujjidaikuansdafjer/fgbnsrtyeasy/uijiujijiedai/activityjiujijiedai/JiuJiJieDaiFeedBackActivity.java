package com.jiujjidaikuansdafjer.fgbnsrtyeasy.uijiujijiedai.activityjiujijiedai;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiujjidaikuansdafjer.fgbnsrtyeasy.R;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.utilsjiujijiedai.JiuJiJieDaiToastUtil;
import com.victor.loading.rotate.RotateLoading;

import butterknife.BindView;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.utilsjiujijiedai.JiuJiJieDaiStatusBarUtil;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.mvp.XActivity;

public class JiuJiJieDaiFeedBackActivity extends XActivity {

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
        JiuJiJieDaiStatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("意见反馈");
        commitBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(feedbackEt.getText().toString().trim())){
                JiuJiJieDaiToastUtil.showShort("请输入提交内容");
                return;
            }
            rotateLoading.start();
            loadingFl.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingFl.setVisibility(View.GONE);
                    rotateLoading.stop();
                    JiuJiJieDaiToastUtil.showShort("提交成功");
                    finish();
                }
            }, 2000);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback_jiu_ji_jie_dai;
    }

    @Override
    public Object newP() {
        return null;
    }
}
