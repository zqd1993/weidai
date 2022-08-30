package com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqadapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yingjijiefasdfbbdr.dfgeryxfg.R;
import com.yingjijiefasdfbbdr.dfgeryxfg.base.SimpleRecAdapter;
import com.yingjijiefasdfbbdr.dfgeryxfg.kit.KnifeKit;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.YjjdFqjqMineItemModel;

import butterknife.BindView;

public class MineAdapter1YjjdFqjq extends SimpleRecAdapter<YjjdFqjqMineItemModel, MineAdapter1YjjdFqjq.ViewHolder> {

    public MineAdapter1YjjdFqjq(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_yjjdfqjq_mine_item_1;
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
