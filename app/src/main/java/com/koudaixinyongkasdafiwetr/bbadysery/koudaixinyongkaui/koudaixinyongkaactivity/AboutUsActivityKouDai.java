package com.koudaixinyongkasdafiwetr.bbadysery.koudaixinyongkaui.koudaixinyongkaactivity;

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

import butterknife.BindView;

import com.koudaixinyongkasdafiwetr.bbadysery.R;
import com.koudaixinyongkasdafiwetr.bbadysery.koudaixinyongkautils.StatusKouDaiBarUtil;
import com.koudaixinyongkasdafiwetr.bbadysery.mvp.XActivity;

public class AboutUsActivityKouDai extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;

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

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusKouDaiBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
    }

    private void werdzgdfh(ViewGroup.LayoutParams layoutParams, int top, int bottom) {
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
    public void vbergfh(EditText mEditText, Context mContext, View v) {
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
        return R.layout.activity_about_us_kou_dai;
    }

    @Override
    public Object newP() {
        return null;
    }
}
