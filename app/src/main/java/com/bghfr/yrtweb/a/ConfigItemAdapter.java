package com.bghfr.yrtweb.a;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bghfr.yrtweb.R;
import com.bghfr.yrtweb.m.SheZhiModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ConfigItemAdapter extends BaseQuickAdapter<SheZhiModel, BaseViewHolder> {

    private OnClickListener onClickListener;

    public ConfigItemAdapter(int layoutResId, @Nullable List<SheZhiModel> data) {
        super(layoutResId, data);
    }

    private static final int[] APPCOMPAT_CHECK_ATTRS = {R.attr.colorPrimary};

    public static void checkAppCompatTheme(Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(APPCOMPAT_CHECK_ATTRS);
        final boolean failed = !typedArray.hasValue(0);
        if (typedArray != null) {
            typedArray.recycle();
        }
        if (failed) {
            throw new IllegalArgumentException("You need to use a Theme.AppCompat theme (or descendant) with the design library.");
        }
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, SheZhiModel item) {
        ImageView imageView = helper.getView(R.id.set_pic);
        helper.setText(R.id.set_name, item.getName());
        Glide.with(mContext).load(item.getPic()).into(imageView);
        helper.getView(R.id.parent_fl).setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClicked(helper.getLayoutPosition());
            }
        });
    }

    // 关闭键盘输入法
    public static void closeSoftInput(Context context, View v) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClicked(int position);
    }

}
