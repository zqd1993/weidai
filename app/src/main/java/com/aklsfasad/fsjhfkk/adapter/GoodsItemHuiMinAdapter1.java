package com.aklsfasad.fsjhfkk.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aklsfasad.fsjhfkk.R;
import com.aklsfasad.fsjhfkk.base.SimpleRecAdapter;
import com.aklsfasad.fsjhfkk.imageloader.ILFactory;
import com.aklsfasad.fsjhfkk.imageloader.ILoader;
import com.aklsfasad.fsjhfkk.kit.KnifeKit;
import com.aklsfasad.fsjhfkk.model.GoodsHuiMinModel;
import com.aklsfasad.fsjhfkk.net.Api;

import butterknife.BindView;

public class GoodsItemHuiMinAdapter1 extends SimpleRecAdapter<GoodsHuiMinModel, GoodsItemHuiMinAdapter1.ViewHolder> {

    @Override
    public int getLayoutId() {
        return R.layout.layout_banner_item;
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    public GoodsItemHuiMinAdapter1(Context context) {
        super(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.shuliang_tv)
        TextView passingRateTv;
        @BindView(R.id.tedian_tv)
        TextView tagTv;
        @BindView(R.id.shangpin_name_tv)
        TextView productNameTv;
        @BindView(R.id.goods_pic)
        ImageView productImg;
        @BindView(R.id.edu_tv)
        TextView limitTv;
        @BindView(R.id.shijian_tv)
        TextView cycleTv;
        @BindView(R.id.parent_ll)
        View parent_ll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        GoodsHuiMinModel model = data.get(i);
        if (!TextUtils.isEmpty(model.getDes()) && model.getDes().length() > 2) {
            viewHolder.cycleTv.setText("周期" + model.getDes());
        }
        viewHolder.passingRateTv.setText(model.getPassingRate() + "下款");
        viewHolder.tagTv.setText(model.getTag());
        viewHolder.productNameTv.setText(model.getProductName());
        ILFactory.getLoader().loadNet(viewHolder.productImg, Api.API_BASE_URL + model.getProductLogo(), new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        viewHolder.limitTv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
//        viewHolder.limitTv.setText(String.valueOf(model.getMaxAmount()));
        viewHolder.productImg.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, model, 1, viewHolder);
        });
        viewHolder.parent_ll.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, model, 1, viewHolder);
        });
    }

}
