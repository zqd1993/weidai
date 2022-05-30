package com.werwerd.ertegdfg.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.werwerd.ertegdfg.R;
import com.werwerd.ertegdfg.base.SimpleRecAdapter;
import com.werwerd.ertegdfg.kit.KnifeKit;
import com.werwerd.ertegdfg.model.MineItemModelYouXin;

import butterknife.BindView;

public class MineAdapterYouXin extends SimpleRecAdapter<MineItemModelYouXin, MineAdapterYouXin.ViewHolder> {

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_mine_item;
    }

    public MineAdapterYouXin(Context context) {
        super(context);
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

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvItem.setText(data.get(i).getItemTv());
        viewHolder.itemImg.setImageResource(data.get(i).getImgRes());
        viewHolder.parentLl.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, data.get(i), 1, viewHolder);
        });
    }

}
