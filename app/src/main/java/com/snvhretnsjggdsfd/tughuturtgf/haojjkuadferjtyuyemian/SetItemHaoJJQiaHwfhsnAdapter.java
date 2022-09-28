package com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuyemian;

import android.app.Activity;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.snvhretnsjggdsfd.tughuturtgf.R;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyushiti.SetModelHaoJJQiaHwfhsn;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.lang.reflect.Method;
import java.util.List;

public class SetItemHaoJJQiaHwfhsnAdapter extends BaseQuickAdapter<SetModelHaoJJQiaHwfhsn, BaseViewHolder> {

    private OnClickListener onClickListener;

    public SetItemHaoJJQiaHwfhsnAdapter(int layoutResId, @Nullable List<SetModelHaoJJQiaHwfhsn> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SetModelHaoJJQiaHwfhsn item) {
        ImageView imageView = helper.getView(R.id.set_pic);
        helper.setText(R.id.set_name, item.getName());
        Glide.with(mContext).load(item.getPic()).into(imageView);
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
