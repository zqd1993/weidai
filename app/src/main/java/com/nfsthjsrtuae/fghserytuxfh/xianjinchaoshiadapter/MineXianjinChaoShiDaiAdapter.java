package com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiadapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.base.SimpleRecAdapter;
import com.nfsthjsrtuae.fghserytuxfh.kit.KnifeKit;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.MineXianjinChaoShiItemModel;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.SharedPreferencesXianjinChaoShiUtilis;

import butterknife.BindView;

public class MineXianjinChaoShiDaiAdapter extends SimpleRecAdapter<MineXianjinChaoShiItemModel, MineXianjinChaoShiDaiAdapter.ViewHolder> {

    public MineXianjinChaoShiDaiAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_xian_jin_chao_shi_mine_item;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemImg.setImageResource(data.get(i).getImgRes());
        viewHolder.tvItem.setText(data.get(i).getItemTv());
        if (i == data.size() - 1){
            viewHolder.line.setVisibility(View.GONE);
        } else {
            viewHolder.line.setVisibility(View.VISIBLE);
        }
        if (i == 3){
            viewHolder.mail_tv.setText(SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("APP_MAIL"));
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
        @BindView(R.id.line)
        View line;
        @BindView(R.id.right_img)
        View right_img;
        @BindView(R.id.mail_tv)
        TextView mail_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
