package com.werwerd.ertegdfg.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.werwerd.ertegdfg.R;
import com.werwerd.ertegdfg.base.SimpleRecAdapter;
import com.werwerd.ertegdfg.imageloader.ILFactory;
import com.werwerd.ertegdfg.imageloader.ILoader;
import com.werwerd.ertegdfg.kit.KnifeKit;
import com.werwerd.ertegdfg.model.GoodsWinAModel;
import com.werwerd.ertegdfg.net.Api;

import butterknife.BindView;

public class GoodsItemAdapterYouXin extends SimpleRecAdapter<GoodsWinAModel, GoodsItemAdapterYouXin.ViewHolder> {

    @Override
    public int getLayoutId() {
        return R.layout.adapter_goods_item;
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    public GoodsItemAdapterYouXin(Context context) {
        super(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.parent_fl)
        View parentFl;
        @BindView(R.id.passing_rate_tv)
        TextView passingRateTv;
        @BindView(R.id.tag_tv)
        TextView tagTv;
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        GoodsWinAModel model = data.get(i);
        if (!TextUtils.isEmpty(model.getDes()) && model.getDes().length() > 2) {
            viewHolder.cycleTv.setText("周期" + model.getDes() + "个月");
        }
        viewHolder.passingRateTv.setText(String.valueOf(model.getPassingRate()));
        viewHolder.tagTv.setText(model.getTag());
        viewHolder.productNameTv.setText(model.getProductName());
        ILFactory.getLoader().loadNet(viewHolder.productImg, Api.API_BASE_URL + model.getProductLogo(), new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        viewHolder.limitTv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
        viewHolder.clickView.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, model, 1, viewHolder);
        });
    }

}
