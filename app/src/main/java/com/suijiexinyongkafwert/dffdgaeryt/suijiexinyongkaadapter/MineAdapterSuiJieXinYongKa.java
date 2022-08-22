package com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaadapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.suijiexinyongkafwert.dffdgaeryt.R;
import com.suijiexinyongkafwert.dffdgaeryt.base.SimpleRecAdapter;
import com.suijiexinyongkafwert.dffdgaeryt.kit.KnifeKit;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.MineItemModelSuiJieXinYongKa;

import butterknife.BindView;

public class MineAdapterSuiJieXinYongKa extends SimpleRecAdapter<MineItemModelSuiJieXinYongKa, MineAdapterSuiJieXinYongKa.ViewHolder> {

    public MineAdapterSuiJieXinYongKa(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_sui_jie_xin_yong_ka_mine_item;
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
