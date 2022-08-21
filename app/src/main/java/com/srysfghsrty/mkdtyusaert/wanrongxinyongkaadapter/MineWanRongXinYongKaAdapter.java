package com.srysfghsrty.mkdtyusaert.wanrongxinyongkaadapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.srysfghsrty.mkdtyusaert.R;
import com.srysfghsrty.mkdtyusaert.base.SimpleRecAdapter;
import com.srysfghsrty.mkdtyusaert.kit.KnifeKit;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel.MineWanRongXinYongKaItemModel;

import butterknife.BindView;

public class MineWanRongXinYongKaAdapter extends SimpleRecAdapter<MineWanRongXinYongKaItemModel, MineWanRongXinYongKaAdapter.ViewHolder> {

    public MineWanRongXinYongKaAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_mine_item_wan_rong_xin_yong_ka;
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
