package com.pwekqha.itmgs.koudaibeiyongjina;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pwekqha.itmgs.R;
import com.pwekqha.itmgs.koudaibeiyongjinm.SetKouDaiBeiYongJinModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class SetItemKouDaiBeiYongJinAdapter extends BaseQuickAdapter<SetKouDaiBeiYongJinModel, BaseViewHolder> {

    private OnClickListener onClickListener;

    public SetItemKouDaiBeiYongJinAdapter(int layoutResId, @Nullable List<SetKouDaiBeiYongJinModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SetKouDaiBeiYongJinModel item) {
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
