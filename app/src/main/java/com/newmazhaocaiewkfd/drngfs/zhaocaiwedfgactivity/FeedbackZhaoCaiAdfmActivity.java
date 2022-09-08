package com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgactivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.newmazhaocaiewkfd.drngfs.R;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.MyZhaoCaiAdfmToas;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.PreferencesOpenUtilZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.StatusBarZhaoCaiAdfmUtil;

import cn.droidlover.xstatecontroller.XStateController;

public class FeedbackZhaoCaiAdfmActivity extends AppCompatActivity {

    private XStateController xStateController;
    private EditText feedbackEt;
    private TextView commitBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarZhaoCaiAdfmUtil.setTransparent(this, false);
        if (PreferencesOpenUtilZhaoCaiAdfm.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_feedback_zhao_cai_endfi_weng);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("意见反馈");
        xStateController = findViewById(R.id.content_layout);
        feedbackEt = findViewById(R.id.feedback_et);
        commitBtn = findViewById(R.id.commit_btn);
        xStateController.loadingView(View.inflate(this, R.layout.view_zhao_cai_endfi_weng_loading, null));
        commitBtn.setOnClickListener(v -> {
            if (feedbackEt.getText().toString().trim().isEmpty()){
                MyZhaoCaiAdfmToas.showShort("请输入您的意见反馈");
                return;
            } else {
                if (xStateController != null){
                    xStateController.showLoading();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            xStateController.showContent();
                            MyZhaoCaiAdfmToas.showShort("提交成功");
                            finish();
                        }
                    }, 2000);
                }
            }
        });
    }
}
