package com.mmaeryrusu.qqzdryty.fenqihuanqianbeiadapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mmaeryrusu.qqzdryty.R;
import com.mmaeryrusu.qqzdryty.base.SimpleRecAdapter;
import com.mmaeryrusu.qqzdryty.kit.KnifeKit;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.GoodsFenQiHuanQianBeiModel;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.FenQiHuanQianBeiSharedPreferencesUtilis;

import butterknife.BindView;

public class GoodsItemAdapterFenQiHuanQianBei extends SimpleRecAdapter<GoodsFenQiHuanQianBeiModel, GoodsItemAdapterFenQiHuanQianBei.ViewHolder> {

    public GoodsItemAdapterFenQiHuanQianBei(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_fen_qi_huan_qian_bei_goods_item;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        GoodsFenQiHuanQianBeiModel model = data.get(i);
        if (!TextUtils.isEmpty(model.getFan_time()) && model.getFan_time().length() > 2) {
            viewHolder.cycleTv.setText("1-" + model.getFan_time());
        }
//        viewHolder.people_num_tv.setText(model.getNum() + "人申请");
        viewHolder.productNameTv.setText(model.getTitle());
//        viewHolder.info_tv.setText(model.getInfo());
        if (!TextUtils.isEmpty(FenQiHuanQianBeiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            Glide.with(context).load(FenQiHuanQianBeiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + model.getImgs()).into(viewHolder.productImg);
        }
//        viewHolder.limitTv.setText(model.getMax_money());
        viewHolder.edu_tv.setText(String.valueOf(model.getMax_money()));
        viewHolder.day_money_tv.setText(model.getDay_money());
        viewHolder.clickView.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, model, 1, viewHolder);
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_name_tv)
        TextView productNameTv;
        @BindView(R.id.product_img)
        ImageView productImg;
//        @BindView(R.id.limit_tv)
//        TextView limitTv;
        @BindView(R.id.cycle_tv)
        TextView cycleTv;
        @BindView(R.id.click_view)
        View clickView;
//        @BindView(R.id.people_num_tv)
//        TextView people_num_tv;
//        @BindView(R.id.info_tv)
//        TextView info_tv;
        @BindView(R.id.day_money_tv)
        TextView day_money_tv;
        @BindView(R.id.edu_tv)
        TextView edu_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
