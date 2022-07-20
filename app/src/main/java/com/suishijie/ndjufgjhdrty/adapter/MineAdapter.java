package com.suishijie.ndjufgjhdrty.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.suishijie.ndjufgjhdrty.R;
import com.suishijie.ndjufgjhdrty.base.SimpleRecAdapter;
import com.suishijie.ndjufgjhdrty.kit.KnifeKit;
import com.suishijie.ndjufgjhdrty.model.MineItemModel;

import butterknife.BindView;

public class MineAdapter extends SimpleRecAdapter<MineItemModel, MineAdapter.ViewHolder> {

    public MineAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_mine_item;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemImg.setImageResource(data.get(i).getImgRes());
        viewHolder.tvItem.setText(data.get(i).getItemTv());
        viewHolder.rightText.setVisibility(View.GONE);
        viewHolder.arrowIcon.setVisibility(View.VISIBLE);
        viewHolder.line.setVisibility(View.VISIBLE);
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
        @BindView(R.id.line)
        View line;
        @BindView(R.id.arrow_icon)
        ImageView arrowIcon;
        @BindView(R.id.right_text)
        TextView rightText;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
