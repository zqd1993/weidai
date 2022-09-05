package com.ruyijiekuandfwetdg.nnrdtydfgsd.adapterruyijiekuandfwetr;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.R;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.base.SimpleRecAdapter;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.RuYiJieKuanAdgFsdfGoodsModel;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr.RuYiJieKuanAdgFsdfSharedPreferencesUtilis;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.kit.KnifeKit;

import butterknife.BindView;

public class GoodsItemAdapter1RuYiJieKuanAdgFsdf extends SimpleRecAdapter<RuYiJieKuanAdgFsdfGoodsModel, GoodsItemAdapter1RuYiJieKuanAdgFsdf.ViewHolder> {

    public GoodsItemAdapter1RuYiJieKuanAdgFsdf(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_goods_item_ru_yi_jie_kuan_dfs_wetg;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        RuYiJieKuanAdgFsdfGoodsModel model = data.get(i);
        if (!TextUtils.isEmpty(model.getFan_time()) && model.getFan_time().length() > 2) {
            viewHolder.cycleTv.setText("最长可分期" + model.getFan_time().substring(0, 2) + "期");
        }
        viewHolder.people_num_tv.setText(model.getNum() + "人申请");
        viewHolder.productNameTv.setText(model.getTitle());
        viewHolder.info_tv.setText(model.getInfo());
        if (!TextUtils.isEmpty(RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            Glide.with(context).load(RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + model.getImgs()).into(viewHolder.productImg);
        }
        viewHolder.limitTv.setText(model.getMax_money());
        viewHolder.clickView.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, model, 1, viewHolder);
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_name_tv)
        TextView productNameTv;
        @BindView(R.id.product_img)
        ImageView productImg;
        @BindView(R.id.limit_tv)
        TextView limitTv;
        @BindView(R.id.cycle_tv)
        TextView cycleTv;
        @BindView(R.id.click_view)
        View clickView;
        @BindView(R.id.people_num_tv)
        TextView people_num_tv;
        @BindView(R.id.info_tv)
        TextView info_tv;
        @BindView(R.id.parent_fl)
        View parent_fl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
