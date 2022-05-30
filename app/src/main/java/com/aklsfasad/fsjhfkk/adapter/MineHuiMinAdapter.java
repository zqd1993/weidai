package com.aklsfasad.fsjhfkk.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aklsfasad.fsjhfkk.R;
import com.aklsfasad.fsjhfkk.base.SimpleRecAdapter;
import com.aklsfasad.fsjhfkk.kit.KnifeKit;
import com.aklsfasad.fsjhfkk.model.MineItemHuiMinModel;

import butterknife.BindView;

public class MineHuiMinAdapter extends SimpleRecAdapter<MineItemHuiMinModel, MineHuiMinAdapter.ViewHolder> {

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_mine_item;
    }

    public MineHuiMinAdapter(Context context) {
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
