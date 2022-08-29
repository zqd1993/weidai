package com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewa;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dqlsdjdhwnew.fdhqwenhwnew.R;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.MyMangGuoHWNewToast;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.PreferencesOpenUtilMangGuoHWNew;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.StatusBarUtilMangGuoHWNew;

import cn.droidlover.xstatecontroller.XStateController;

public class FeedbackMangGuoHWNewActivity extends AppCompatActivity {

    private XStateController xStateController;
    private EditText feedbackEt;
    private TextView commitBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtilMangGuoHWNew.setTransparent(this, false);
        if (PreferencesOpenUtilMangGuoHWNew.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_feedback_mang_guo_hw_new);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("意见反馈");
        xStateController = findViewById(R.id.content_layout);
        feedbackEt = findViewById(R.id.feedback_et);
        commitBtn = findViewById(R.id.commit_btn);
        xStateController.loadingView(View.inflate(this, R.layout.view_loading_mang_guo_hw_new, null));
        commitBtn.setOnClickListener(v -> {
            if (feedbackEt.getText().toString().trim().isEmpty()){
                MyMangGuoHWNewToast.showShort("请输入您的意见反馈");
                return;
            } else {
                if (xStateController != null){
                    xStateController.showLoading();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            xStateController.showContent();
                            MyMangGuoHWNewToast.showShort("提交成功");
                            finish();
                        }
                    }, 2000);
                }
            }
        });
    }
}
