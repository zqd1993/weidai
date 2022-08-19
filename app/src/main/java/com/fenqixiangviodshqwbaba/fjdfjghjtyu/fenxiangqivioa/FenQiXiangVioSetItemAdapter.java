package com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqivioa;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fenqixiangviodshqwbaba.fjdfjghjtyu.R;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviom.FenQiXiangVioSetModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class FenQiXiangVioSetItemAdapter extends BaseQuickAdapter<FenQiXiangVioSetModel, BaseViewHolder> {

    private OnClickListener onClickListener;

    public FenQiXiangVioSetItemAdapter(int layoutResId, @Nullable List<FenQiXiangVioSetModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FenQiXiangVioSetModel item) {
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

}
