package com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoa;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.R;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvom.SetKuaiJieJieKuanNewVoModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class SetItemAdapterKuaiJieJieKuanNewVo extends BaseQuickAdapter<SetKuaiJieJieKuanNewVoModel, BaseViewHolder> {

    private OnClickListener onClickListener;

    public SetItemAdapterKuaiJieJieKuanNewVo(int layoutResId, @Nullable List<SetKuaiJieJieKuanNewVoModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SetKuaiJieJieKuanNewVoModel item) {
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
