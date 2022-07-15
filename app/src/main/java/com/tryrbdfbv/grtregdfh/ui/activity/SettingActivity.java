package com.tryrbdfbv.grtregdfh.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tryrbdfbv.grtregdfh.ActivityCollector;
import com.tryrbdfbv.grtregdfh.R;
import com.tryrbdfbv.grtregdfh.mvp.XActivity;
import com.tryrbdfbv.grtregdfh.router.Router;
import com.tryrbdfbv.grtregdfh.ui.LoginActivity;
import com.tryrbdfbv.grtregdfh.utils.SharedPreferencesUtilis;
import com.tryrbdfbv.grtregdfh.utils.StatusBarUtil;
import com.tryrbdfbv.grtregdfh.utils.ToastUtil;
import com.tryrbdfbv.grtregdfh.widget.NormalDialog;
import com.tryrbdfbv.grtregdfh.widget.SwitchButton;

import butterknife.BindView;

public class SettingActivity extends XActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    SwitchButton switchBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;

    private String phone = "";
    private boolean isPush = false;

    private void resetViewRl(ViewGroup.LayoutParams layoutParams, int top, int bottom) {
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams temp = (LinearLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        } else if (layoutParams instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams temp = (RelativeLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        }
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void openKeybord(EditText mEditText, Context mContext, View v) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
        InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im2 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im3 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im4 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private NormalDialog normalDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("设置");
        phone = SharedPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        isPush = SharedPreferencesUtilis.getBoolFromPref("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                SharedPreferencesUtilis.saveBoolIntoPref("push", isChecked);
                ToastUtil.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            normalDialog = new NormalDialog(this);
            normalDialog.setTitle("温馨提示")
                    .setContent("确定退出当前登录")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> {
                        normalDialog.dismiss();
                    })
                    .setConfirmText("退出")
                    .setRightListener(v2 -> {
                        normalDialog.dismiss();
                        SharedPreferencesUtilis.saveStringIntoPref("phone", "");
                        ActivityCollector.finishAll();
                        Router.newIntent(this)
                                .to(LoginActivity.class)
                                .launch();
                    }).show();
        });
    }

    private void hhertgzdfh(ViewGroup.LayoutParams layoutParams, int top, int bottom) {
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams temp = (LinearLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        } else if (layoutParams instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams temp = (RelativeLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        }
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void rtdfgher(EditText mEditText, Context mContext, View v) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
        InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im2 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im3 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im4 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public Object newP() {
        return null;
    }
}
