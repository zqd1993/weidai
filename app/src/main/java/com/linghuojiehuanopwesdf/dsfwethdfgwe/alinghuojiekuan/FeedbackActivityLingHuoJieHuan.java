package com.linghuojiehuanopwesdf.dsfwethdfgwe.alinghuojiekuan;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.linghuojiehuanopwesdf.dsfwethdfgwe.R;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan.MyToastLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan.PreferencesOpenUtilLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan.LingHuoJieHuanStatusBarUtil;

import cn.droidlover.xstatecontroller.XStateController;

public class FeedbackActivityLingHuoJieHuan extends AppCompatActivity {

    private XStateController xStateController;
    private EditText feedbackEt;
    private TextView commitBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LingHuoJieHuanStatusBarUtil.setTransparent(this, false);
        if (PreferencesOpenUtilLingHuoJieHuan.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_feedback_ling_huo_jie_huan);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("意见反馈");
        xStateController = findViewById(R.id.content_layout);
        feedbackEt = findViewById(R.id.feedback_et);
        commitBtn = findViewById(R.id.commit_btn);
        xStateController.loadingView(View.inflate(this, R.layout.view_loading_ling_huo_jie_huan, null));
        commitBtn.setOnClickListener(v -> {
            if (feedbackEt.getText().toString().trim().isEmpty()){
                MyToastLingHuoJieHuan.showShort("请输入您的意见反馈");
                return;
            } else {
                if (xStateController != null){
                    xStateController.showLoading();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            xStateController.showContent();
                            MyToastLingHuoJieHuan.showShort("提交成功");
                            finish();
                        }
                    }, 2000);
                }
            }
        });
    }
}
