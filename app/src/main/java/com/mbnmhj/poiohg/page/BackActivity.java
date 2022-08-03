package com.mbnmhj.poiohg.page;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mbnmhj.poiohg.R;
import com.mbnmhj.poiohg.util.NewToast;
import com.mbnmhj.poiohg.util.SBarUtil;
import com.mbnmhj.poiohg.util.SpUtil;

import java.util.regex.Pattern;

import cn.droidlover.xstatecontroller.XStateController;

public class BackActivity extends AppCompatActivity {

    private XStateController xStateController;
    private EditText feedbackEt;
    private TextView commitBtn;

    /** 正则表达式：以0或正整数开头后跟0或1个(小数点后面跟0到2位数字) */
    private static final String FORMAT = "^(0|[1-9]\\d*)(\\.\\d{0,%s})?$";
    /** 正则表达式：0-9.之外的字符 */
    private static final Pattern SOURCE_PATTERN = Pattern.compile("[^0-9.]");

    /** 默认保留小数点后2位 */
    private Pattern mPattern = Pattern.compile(String.format(FORMAT, "2"));

    /** 允许输入的最大金额 */
    private double maxValue = 999999;

    private String remindStr = "可输入最大数量";

    /**
     * 设置保留小数点后的位数，默认保留2位
     *
     * @param length
     */
    public void setDecimalLength(int length) {
        mPattern = Pattern.compile(String.format(FORMAT, length));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SpUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        SBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_back);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("意见反馈");
        xStateController = findViewById(R.id.content_layout);
        feedbackEt = findViewById(R.id.feedback_et);
        commitBtn = findViewById(R.id.commit_btn);
        xStateController.loadingView(View.inflate(this, R.layout.view_zaijia, null));
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

    /**
     * 设置允许输入的最大金额
     *
     * @param maxValue
     */
    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * 设置允许输入的最大金额
     *
     * @param maxValue
     */
    public void setMaxValue(String remindStr, double maxValue) {
        this.maxValue = maxValue;
        this.remindStr = remindStr;
    }

}
