package com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewa;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dqlsdjdhwnew.fdhqwenhwnew.R;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewm.MangGuoHWNewSetModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MangGuoHWNewSetItemAdapter extends BaseQuickAdapter<MangGuoHWNewSetModel, BaseViewHolder> {

    private OnClickListener onClickListener;

    public MangGuoHWNewSetItemAdapter(int layoutResId, @Nullable List<MangGuoHWNewSetModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MangGuoHWNewSetModel item) {
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
