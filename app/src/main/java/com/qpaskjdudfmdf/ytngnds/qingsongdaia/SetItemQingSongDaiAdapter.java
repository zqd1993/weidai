package com.qpaskjdudfmdf.ytngnds.qingsongdaia;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qpaskjdudfmdf.ytngnds.R;
import com.qpaskjdudfmdf.ytngnds.qingsongdaim.SetQingSongDaiModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class SetItemQingSongDaiAdapter extends BaseQuickAdapter<SetQingSongDaiModel, BaseViewHolder> {

    private OnClickListener onClickListener;

    public SetItemQingSongDaiAdapter(int layoutResId, @Nullable List<SetQingSongDaiModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SetQingSongDaiModel item) {
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
