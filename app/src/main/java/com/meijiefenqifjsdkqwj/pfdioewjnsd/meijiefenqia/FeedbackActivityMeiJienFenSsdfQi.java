package com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqia;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.meijiefenqifjsdkqwj.pfdioewjnsd.R;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqiu.MyMeiJienFenSsdfQiToast;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqiu.PreferencesMeiJienFenSsdfQiOpenUtil;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqiu.MeiJienFenSsdfQiStatusBarUtil;

import java.lang.reflect.Field;

import cn.droidlover.xstatecontroller.XStateController;

public class FeedbackActivityMeiJienFenSsdfQi extends AppCompatActivity {

    private XStateController xStateController;
    private EditText feedbackEt;
    private TextView commitBtn;

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void fixInputMethodMemoryLeak(Context context) {
        if (context == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) return;
        String[] viewArr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
        Field field;
        Object fieldObj;
        for (String view : viewArr) {
            try {
                field = inputMethodManager.getClass().getDeclaredField(view);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                fieldObj = field.get(inputMethodManager);
                if (fieldObj != null && fieldObj instanceof View) {
                    View fieldView = (View) fieldObj;
                    if (fieldView.getContext() == context) {// 被InputMethodManager持有引用的context是想要目标销毁的
                        field.set(inputMethodManager, null);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PreferencesMeiJienFenSsdfQiOpenUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        MeiJienFenSsdfQiStatusBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_mei_jie_fen_qi_feedback);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("意见反馈");
        xStateController = findViewById(R.id.content_layout);
        feedbackEt = findViewById(R.id.feedback_et);
        commitBtn = findViewById(R.id.commit_btn);
        xStateController.loadingView(View.inflate(this, R.layout.view_loading_mei_jie_fen_qi, null));
        commitBtn.setOnClickListener(v -> {
            if (feedbackEt.getText().toString().trim().isEmpty()){
                MyMeiJienFenSsdfQiToast.showShort("请输入您的意见反馈");
                return;
            } else {
                if (xStateController != null){
                    xStateController.showLoading();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            xStateController.showContent();
                            MyMeiJienFenSsdfQiToast.showShort("提交成功");
                            finish();
                        }
                    }, 2000);
                }
            }
        });
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void asdferyhgf(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void uydrtgjhfgj(Context context) {
        if (context == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) return;
        String[] viewArr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
        Field field;
        Object fieldObj;
        for (String view : viewArr) {
            try {
                field = inputMethodManager.getClass().getDeclaredField(view);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                fieldObj = field.get(inputMethodManager);
                if (fieldObj != null && fieldObj instanceof View) {
                    View fieldView = (View) fieldObj;
                    if (fieldView.getContext() == context) {// 被InputMethodManager持有引用的context是想要目标销毁的
                        field.set(inputMethodManager, null);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
