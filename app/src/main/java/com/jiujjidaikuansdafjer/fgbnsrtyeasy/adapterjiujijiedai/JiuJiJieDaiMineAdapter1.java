package com.jiujjidaikuansdafjer.fgbnsrtyeasy.adapterjiujijiedai;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jiujjidaikuansdafjer.fgbnsrtyeasy.R;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.base.SimpleRecAdapter;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.kit.KnifeKit;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.modeljiujijiedai.JiuJiJieDaiMineItemModel;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.utilsjiujijiedai.JiuJiJieDaiSharedPreferencesUtilis;

import butterknife.BindView;

public class JiuJiJieDaiMineAdapter1 extends SimpleRecAdapter<JiuJiJieDaiMineItemModel, JiuJiJieDaiMineAdapter1.ViewHolder> {

    public JiuJiJieDaiMineAdapter1(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_mine_item_jiu_ji_jie_dai;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemImg.setImageResource(data.get(i).getImgRes());
        viewHolder.tvItem.setText(data.get(i).getItemTv());
        if (i == 3){
            viewHolder.mail_tv.setText(JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("APP_MAIL"));
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
