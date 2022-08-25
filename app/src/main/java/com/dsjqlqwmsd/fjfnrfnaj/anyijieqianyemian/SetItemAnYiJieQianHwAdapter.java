package com.dsjqlqwmsd.fjfnrfnaj.anyijieqianyemian;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dsjqlqwmsd.fjfnrfnaj.R;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqianshiti.SetModelAnYiJieQianHw;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.lang.reflect.Method;
import java.util.List;

public class SetItemAnYiJieQianHwAdapter extends BaseQuickAdapter<SetModelAnYiJieQianHw, BaseViewHolder> {

    private OnClickListener onClickListener;

    public SetItemAnYiJieQianHwAdapter(int layoutResId, @Nullable List<SetModelAnYiJieQianHw> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SetModelAnYiJieQianHw item) {
        ImageView imageView = helper.getView(R.id.set_pic);
        helper.setText(R.id.set_name, item.getName());
        Glide.with(mContext).load(item.getPic()).into(imageView);
        View line_view = helper.getView(R.id.line_view);
        line_view.setVisibility(View.VISIBLE);
        if (helper.getLayoutPosition() == getData().size() - 1) {
            line_view.setVisibility(View.GONE);
        }
        helper.getView(R.id.parent_fl).setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClicked(helper.getLayoutPosition());
            }
        });
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClicked(int position);
    }

    /**
     * Convert a translucent themed Activity
     * {@link android.R.attr#windowIsTranslucent} to a fullscreen opaque
     * Activity.
     * <p>
     * Call this whenever the background of a translucent Activity has changed
     * to become opaque. Doing so will allow the {@link android.view.Surface} of
     * the Activity behind to be released.
     * <p>
     * This call has no effect on non-translucent activities or on activities
     * with the {@link android.R.attr#windowIsFloating} attribute.
     */
    public static void convertActivityFromTranslucent(Activity activity) {
        try {
            Method method = Activity.class.getDeclaredMethod("convertFromTranslucent");
            method.setAccessible(true);
            method.invoke(activity);
        } catch (Throwable t) {
        }
    }

}
