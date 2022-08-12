package com.nfsthjsrtuae.fghserytuxfh.ui.zhouzhuanzijinactivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinutils.ZhouZhuanZiJinToastUtil;
import com.victor.loading.rotate.RotateLoading;

import butterknife.BindView;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinutils.StatusBarUtil;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XActivity;

public class ZhouZhuanZiJinFeedBackActivity extends XActivity {

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
        StatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("意见反馈");
        commitBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(feedbackEt.getText().toString().trim())){
                ZhouZhuanZiJinToastUtil.showShort("请输入提交内容");
                return;
            }
            rotateLoading.start();
            loadingFl.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingFl.setVisibility(View.GONE);
                    rotateLoading.stop();
                    ZhouZhuanZiJinToastUtil.showShort("提交成功");
                    finish();
                }
            }, 2000);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback_zhou_zhuan_zi_jin;
    }

    @Override
    public Object newP() {
        return null;
    }
}
