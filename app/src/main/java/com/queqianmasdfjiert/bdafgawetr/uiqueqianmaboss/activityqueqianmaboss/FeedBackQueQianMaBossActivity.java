package com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.activityqueqianmaboss;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.queqianmasdfjiert.bdafgawetr.R;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.ToastQueQianMaBossUtil;
import com.victor.loading.rotate.RotateLoading;

import butterknife.BindView;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.StatusBarQueQianMaBossUtil;
import com.queqianmasdfjiert.bdafgawetr.mvp.XActivity;

public class FeedBackQueQianMaBossActivity extends XActivity {

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
        StatusBarQueQianMaBossUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("意见反馈");
        commitBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(feedbackEt.getText().toString().trim())){
                ToastQueQianMaBossUtil.showShort("请输入提交内容");
                return;
            }
            rotateLoading.start();
            loadingFl.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingFl.setVisibility(View.GONE);
                    rotateLoading.stop();
                    ToastQueQianMaBossUtil.showShort("提交成功");
                    finish();
                }
            }, 2000);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_que_qian_ma_boss_feedbackdi;
    }

    @Override
    public Object newP() {
        return null;
    }
}
