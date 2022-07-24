package com.srysfghsrty.mkdtyusaert.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.srysfghsrty.mkdtyusaert.R;
import com.srysfghsrty.mkdtyusaert.base.SimpleRecAdapter;
import com.srysfghsrty.mkdtyusaert.kit.KnifeKit;
import com.srysfghsrty.mkdtyusaert.model.MineItemModel;

import butterknife.BindView;

public class MineAdapter2 extends SimpleRecAdapter<MineItemModel, MineAdapter2.ViewHolder> {

    public MineAdapter2(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_mine_item_1;
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
