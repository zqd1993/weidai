package com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoadapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jiujijietiaodsfwet.bsdwefhert.R;
import com.jiujijietiaodsfwet.bsdwefhert.base.SimpleRecAdapter;
import com.jiujijietiaodsfwet.bsdwefhert.kit.KnifeKit;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.MineItemModelJiuJiJieTiaojghsdf;

import butterknife.BindView;

public class MineAdapterJiuJiJieTiaojghsdf extends SimpleRecAdapter<MineItemModelJiuJiJieTiaojghsdf, MineAdapterJiuJiJieTiaojghsdf.ViewHolder> {

    public MineAdapterJiuJiJieTiaojghsdf(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_mine_item_jiu_ji_jie_tiao_boss;
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
