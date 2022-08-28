package com.queqianmasdfjiert.bdafgawetr.adapterqueqianmaboss;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.queqianmasdfjiert.bdafgawetr.R;
import com.queqianmasdfjiert.bdafgawetr.base.SimpleRecAdapter;
import com.queqianmasdfjiert.bdafgawetr.kit.KnifeKit;
import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.MineItemModelQueQianMaBoss;

import butterknife.BindView;

public class MineAdapterQueQianMaBoss extends SimpleRecAdapter<MineItemModelQueQianMaBoss, MineAdapterQueQianMaBoss.ViewHolder> {

    public MineAdapterQueQianMaBoss(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_que_qian_ma_boss_mine_item_1;
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
