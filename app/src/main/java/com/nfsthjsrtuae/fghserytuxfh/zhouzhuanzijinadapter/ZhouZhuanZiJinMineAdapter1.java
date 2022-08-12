package com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinadapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.base.SimpleRecAdapter;
import com.nfsthjsrtuae.fghserytuxfh.kit.KnifeKit;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinmodel.ZhouZhuanZiJinMineItemModel;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinutils.ZhouZhuanZiJinSharedPreferencesUtilis;

import butterknife.BindView;

public class ZhouZhuanZiJinMineAdapter1 extends SimpleRecAdapter<ZhouZhuanZiJinMineItemModel, ZhouZhuanZiJinMineAdapter1.ViewHolder> {

    public ZhouZhuanZiJinMineAdapter1(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_mine_item_zhou_zhuan_zi_jin;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemImg.setImageResource(data.get(i).getImgRes());
        viewHolder.tvItem.setText(data.get(i).getItemTv());
        if (i == 3){
            viewHolder.mail_tv.setText(ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("APP_MAIL"));
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
        ImageView right_img;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
