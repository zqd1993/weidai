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
import com.srysfghsrty.mkdtyusaert.utils.SharedPreferencesUtilis;

import butterknife.BindView;

public class MineAdapter1 extends SimpleRecAdapter<MineItemModel, MineAdapter1.ViewHolder> {

    public MineAdapter1(Context context) {
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
        if (i == 1){
            viewHolder.mail_tv.setText(SharedPreferencesUtilis.getStringFromPref("APP_MAIL"));
            viewHolder.mail_tv.setVisibility(View.VISIBLE);
            viewHolder.right_img.setVisibility(View.GONE);
        } else {
            viewHolder.mail_tv.setVisibility(View.GONE);
            viewHolder.right_img.setVisibility(View.VISIBLE);
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
        @BindView(R.id.mail_tv)
        TextView mail_tv;
        @BindView(R.id.right_img)
        View right_img;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
