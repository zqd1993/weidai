package com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoa;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.R;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvou.MyKuaiJieJieKuanNewVoToast;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvou.PreferencesOpenUtilKuaiJieJieKuanNewVo;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvou.StatusBarKuaiJieJieKuanNewVoUtil;

import cn.droidlover.xstatecontroller.XStateController;

public class FeedbackActivityKuaiJieJieKuanNewVo extends AppCompatActivity {

    private XStateController xStateController;
    private EditText feedbackEt;
    private TextView commitBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarKuaiJieJieKuanNewVoUtil.setTransparent(this, false);
        if (PreferencesOpenUtilKuaiJieJieKuanNewVo.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_kuai_jie_jie_kuan_new_op_feedback);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("意见反馈");
        xStateController = findViewById(R.id.content_layout);
        feedbackEt = findViewById(R.id.feedback_et);
        commitBtn = findViewById(R.id.commit_btn);
        xStateController.loadingView(View.inflate(this, R.layout.view_kuai_jie_jie_kuan_new_op_loading, null));
        commitBtn.setOnClickListener(v -> {
            if (feedbackEt.getText().toString().trim().isEmpty()){
                MyKuaiJieJieKuanNewVoToast.showShort("请输入您的意见反馈");
                return;
            } else {
                if (xStateController != null){
                    xStateController.showLoading();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            xStateController.showContent();
                            MyKuaiJieJieKuanNewVoToast.showShort("提交成功");
                            finish();
                        }
                    }, 2000);
                }
            }
        });
    }
}
