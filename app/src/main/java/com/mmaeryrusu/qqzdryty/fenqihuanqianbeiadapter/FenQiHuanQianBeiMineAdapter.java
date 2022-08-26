package com.mmaeryrusu.qqzdryty.fenqihuanqianbeiadapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mmaeryrusu.qqzdryty.R;
import com.mmaeryrusu.qqzdryty.base.SimpleRecAdapter;
import com.mmaeryrusu.qqzdryty.kit.KnifeKit;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.FenQiHuanQianBeiMineItemModel;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.FenQiHuanQianBeiSharedPreferencesUtilis;

import butterknife.BindView;

public class FenQiHuanQianBeiMineAdapter extends SimpleRecAdapter<FenQiHuanQianBeiMineItemModel, FenQiHuanQianBeiMineAdapter.ViewHolder> {

    public FenQiHuanQianBeiMineAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_mine_fen_qi_huan_qian_bei_item;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemImg.setImageResource(data.get(i).getImgRes());
        viewHolder.tvItem.setText(data.get(i).getItemTv());
        viewHolder.parentLl.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, data.get(i), 1, viewHolder);
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.item_title_tv)
        TextView tvItem;
        @BindView(R.id.item_img)
        ImageView itemImg;
        @BindView(R.id.parent_ll)
        View parentLl;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
