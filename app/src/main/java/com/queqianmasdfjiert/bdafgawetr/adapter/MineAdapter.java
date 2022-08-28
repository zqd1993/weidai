package com.queqianmasdfjiert.bdafgawetr.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.queqianmasdfjiert.bdafgawetr.R;
import com.queqianmasdfjiert.bdafgawetr.base.SimpleRecAdapter;
import com.queqianmasdfjiert.bdafgawetr.utils.SharedPreferencesUtilis;
import com.queqianmasdfjiert.bdafgawetr.kit.KnifeKit;
import com.queqianmasdfjiert.bdafgawetr.model.MineItemModel;

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
        if (i == 0){
            viewHolder.mail_tv.setVisibility(View.VISIBLE);
            viewHolder.rightImg.setVisibility(View.GONE);
            viewHolder.mail_tv.setText(SharedPreferencesUtilis.getStringFromPref("APP_MAIL"));
        } else {
            viewHolder.mail_tv.setVisibility(View.GONE);
            viewHolder.rightImg.setVisibility(View.VISIBLE);
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
        @BindView(R.id.line)
        View line;
        @BindView(R.id.mail_tv)
        TextView mail_tv;
        @BindView(R.id.right_img)
        View rightImg;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
