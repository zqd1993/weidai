package com.linghuojiehuanopwesdf.dsfwethdfgwe.alinghuojiekuan;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linghuojiehuanopwesdf.dsfwethdfgwe.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class TabLingHuoJieHuanAdapter extends BaseQuickAdapter<LingHuoJieHuanMainActivity.TabModel, BaseViewHolder> {

    private ClickedListener clickedListener;

    public TabLingHuoJieHuanAdapter(int layoutResId, @Nullable List<LingHuoJieHuanMainActivity.TabModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LingHuoJieHuanMainActivity.TabModel item) {
        ImageView tabImg = helper.getView(R.id.tab_img);
        TextView tabName = helper.getView(R.id.tab_name);
        tabName.setText(item.getName());
        if (item.isChecked()){
            Glide.with(mContext).load(item.getSelectedIcon()).into(tabImg);
            tabName.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            Glide.with(mContext).load(item.getIcon()).into(tabImg);
            tabName.setTextColor(mContext.getResources().getColor(R.color.color_bdbdc5));
        }
        helper.getView(R.id.click_view).setOnClickListener(v -> {
            if (!item.isChecked()){
                item.setChecked(true);
                for (int i = 0; i < getData().size(); i++){
                    if (i != helper.getLayoutPosition()){
                        getData().get(i).setChecked(false);
                    }
                }
                if (clickedListener != null){
                    clickedListener.onClick(helper.getLayoutPosition());
                }
                notifyDataSetChanged();
            }
        });
    }

    public void setClickedListener(ClickedListener clickedListener){
        this.clickedListener = clickedListener;
    }

    public interface ClickedListener{
        void onClick(int position);
    }
}
