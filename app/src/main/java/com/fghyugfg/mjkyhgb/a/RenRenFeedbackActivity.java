package com.fghyugfg.mjkyhgb.a;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fghyugfg.mjkyhgb.R;
import com.fghyugfg.mjkyhgb.u.NewToast;
import com.fghyugfg.mjkyhgb.u.StatusBarUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.droidlover.xstatecontroller.XStateController;

public class RenRenFeedbackActivity extends AppCompatActivity {

    private XStateController xStateController;
    private EditText feedbackEt;
    private TextView commitBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_renren_fankui);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("意见反馈");
        xStateController = findViewById(R.id.content_layout);
        feedbackEt = findViewById(R.id.feedback_et);
        commitBtn = findViewById(R.id.commit_btn);
        xStateController.loadingView(View.inflate(this, R.layout.loading_view, null));
        commitBtn.setOnClickListener(v -> {
            if (feedbackEt.getText().toString().trim().isEmpty()){
                NewToast.showShort("请输入您的意见反馈");
                return;
            } else {
                if (xStateController != null){
                    xStateController.showLoading();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            xStateController.showContent();
                            NewToast.showShort("提交成功");
                            finish();
                        }
                    }, 2000);
                }
            }
        });
    }

    public static String getYear(String src) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Date date = null;
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
            date = format1.parse(src);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (date != null) {
                return format.format(date);
            } else {
                return "";
            }
        }
    }
}
