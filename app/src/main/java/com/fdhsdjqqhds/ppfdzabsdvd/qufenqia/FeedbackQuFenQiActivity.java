package com.fdhsdjqqhds.ppfdzabsdvd.qufenqia;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fdhsdjqqhds.ppfdzabsdvd.R;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.QuFenQiMyToast;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.PreferencesQuFenQiOpenUtil;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.QuFenQiStatusBarUtil;

import cn.droidlover.xstatecontroller.XStateController;

public class FeedbackQuFenQiActivity extends AppCompatActivity {

    private XStateController xStateController;
    private EditText feedbackEt;
    private TextView commitBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QuFenQiStatusBarUtil.setTransparent(this, false);
        if (PreferencesQuFenQiOpenUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_feedback_qu_fen_qi);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("意见反馈");
        xStateController = findViewById(R.id.content_layout);
        feedbackEt = findViewById(R.id.feedback_et);
        commitBtn = findViewById(R.id.commit_btn);
        xStateController.loadingView(View.inflate(this, R.layout.view_qu_fen_qi_loading, null));
        commitBtn.setOnClickListener(v -> {
            if (feedbackEt.getText().toString().trim().isEmpty()){
                QuFenQiMyToast.showShort("请输入您的意见反馈");
                return;
            } else {
                if (xStateController != null){
                    xStateController.showLoading();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            xStateController.showContent();
                            QuFenQiMyToast.showShort("提交成功");
                            finish();
                        }
                    }, 2000);
                }
            }
        });
    }
}
