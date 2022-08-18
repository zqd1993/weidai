package com.dsjqlqwmsd.fjfnrfnaj.anyijieqianyemian;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dsjqlqwmsd.fjfnrfnaj.R;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqiangongju.AnYiJieQianHwMyToast;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqiangongju.PreferencesAnYiJieQianHwOpenUtil;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqiangongju.StatusBarAnYiJieQianHwUtil;

import cn.droidlover.xstatecontroller.XStateController;

public class FeedbackAnYiJieQianHwActivity extends AppCompatActivity {

    private XStateController xStateController;
    private EditText feedbackEt;
    private TextView commitBtn;

    /***
     * 获取url 指定name的value;
     * @param url
     * @param name
     * @return
     */
    public static String getValueByName(String url, String name) {
        String result;
        try {
            Uri uri = Uri.parse(url);
            result = uri.getQueryParameter(name);
        } catch (Exception e) {
            result = "";
        }
        try {
            Integer.valueOf(result);
        } catch (Exception e) {
            result = "";
        }

        return result;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarAnYiJieQianHwUtil.setTransparent(this, false);
        if (PreferencesAnYiJieQianHwOpenUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.j_an_yi_jie_qian_activity_feedback);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("意见反馈");
        xStateController = findViewById(R.id.content_layout);
        feedbackEt = findViewById(R.id.feedback_et);
        commitBtn = findViewById(R.id.commit_btn);
        xStateController.loadingView(View.inflate(this, R.layout.an_yi_jie_qian_view_loading, null));
        commitBtn.setOnClickListener(v -> {
            if (feedbackEt.getText().toString().trim().isEmpty()){
                AnYiJieQianHwMyToast.showShort("请输入您的意见反馈");
                return;
            } else {
                if (xStateController != null){
                    xStateController.showLoading();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            xStateController.showContent();
                            AnYiJieQianHwMyToast.showShort("提交成功");
                            finish();
                        }
                    }, 2000);
                }
            }
        });
    }

    /**
     * 手机号中间四位显示*号
     *
     * @param phone
     * @return
     */
    public static String HidePhoneNum(String phone) {
        if (!TextUtils.isEmpty(phone) && phone.length() > 6) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < phone.length(); i++) {
                char c = phone.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return phone;
        }
    }
}
