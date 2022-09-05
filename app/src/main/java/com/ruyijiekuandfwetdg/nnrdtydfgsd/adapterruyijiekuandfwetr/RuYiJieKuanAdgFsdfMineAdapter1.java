package com.ruyijiekuandfwetdg.nnrdtydfgsd.adapterruyijiekuandfwetr;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ruyijiekuandfwetdg.nnrdtydfgsd.R;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.base.SimpleRecAdapter;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.kit.KnifeKit;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.MineItemModelRuYiJieKuanAdgFsdf;

import butterknife.BindView;

public class RuYiJieKuanAdgFsdfMineAdapter1 extends SimpleRecAdapter<MineItemModelRuYiJieKuanAdgFsdf, RuYiJieKuanAdgFsdfMineAdapter1.ViewHolder> {

    public RuYiJieKuanAdgFsdfMineAdapter1(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_mine_item_1_ru_yi_jie_kuan_dfs_wetg;
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
