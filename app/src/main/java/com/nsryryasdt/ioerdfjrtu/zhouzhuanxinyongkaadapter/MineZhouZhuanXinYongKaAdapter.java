package com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaadapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nsryryasdt.ioerdfjrtu.R;
import com.nsryryasdt.ioerdfjrtu.base.SimpleRecAdapter;
import com.nsryryasdt.ioerdfjrtu.kit.KnifeKit;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.MineItemZhouZhuanXinYongKaModel;

import butterknife.BindView;

public class MineZhouZhuanXinYongKaAdapter extends SimpleRecAdapter<MineItemZhouZhuanXinYongKaModel, MineZhouZhuanXinYongKaAdapter.ViewHolder> {

    public MineZhouZhuanXinYongKaAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_zhou_zhuan_xin_yong_ka_mine_item;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemImg.setImageResource(data.get(i).getImgRes());
        viewHolder.tvItem.setText(data.get(i).getItemTv());
        if (i == 2 || i == 4){
            viewHolder.right_view.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.right_view.setVisibility(View.VISIBLE);
        }
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
        @BindView(R.id.right_view)
        View right_view;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
