package com.meiwen.speedmw.yemian;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.meiwen.speedmw.R;
import com.meiwen.speedmw.gongju.MyYouBeiToast;
import com.meiwen.speedmw.gongju.StatusYouBeiBarUtil;

import cn.droidlover.xstatecontroller.XStateController;

public class FeedbackYouBeiActivity extends AppCompatActivity {

    private XStateController xStateController;
    private EditText feedbackEt;
    private TextView commitBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusYouBeiBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_feedback);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("意见反馈");
        xStateController = findViewById(R.id.content_layout);
        feedbackEt = findViewById(R.id.feedback_et);
        commitBtn = findViewById(R.id.commit_btn);
        xStateController.loadingView(View.inflate(this, R.layout.view_loading, null));
        commitBtn.setOnClickListener(v -> {
            if (feedbackEt.getText().toString().trim().isEmpty()){
                MyYouBeiToast.showShort("请输入您的意见反馈");
                return;
            } else {
                if (xStateController != null){
                    xStateController.showLoading();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            xStateController.showContent();
                            MyYouBeiToast.showShort("提交成功");
                            finish();
                        }
                    }, 2000);
                }
            }
        });
    }
}
