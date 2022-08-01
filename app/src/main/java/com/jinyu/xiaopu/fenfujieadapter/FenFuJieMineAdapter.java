package com.jinyu.xiaopu.fenfujieadapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jinyu.xiaopu.R;
import com.jinyu.xiaopu.base.SimpleRecAdapter;
import com.jinyu.xiaopu.kit.KnifeKit;
import com.jinyu.xiaopu.fenfujiemodel.FenFuJieMineItemModel;

import butterknife.BindView;

public class FenFuJieMineAdapter extends SimpleRecAdapter<FenFuJieMineItemModel, FenFuJieMineAdapter.ViewHolder> {

    public FenFuJieMineAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_fen_fu_jie_mine_item;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvItem.setText(data.get(i).getItemTv());
        viewHolder.itemImg.setImageResource(data.get(i).getImgRes());
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
