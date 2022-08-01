package com.jinyu.xiaopu.fenfujieadapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jinyu.xiaopu.R;
import com.jinyu.xiaopu.base.SimpleRecAdapter;
import com.jinyu.xiaopu.imageloader.ILFactory;
import com.jinyu.xiaopu.imageloader.ILoader;
import com.jinyu.xiaopu.kit.KnifeKit;
import com.jinyu.xiaopu.fenfujiemodel.GoodsFenFuJieModel;
import com.jinyu.xiaopu.fenfujienet.FenFuJieApi;

import butterknife.BindView;

public class GoodsItemAdapterFenFuJie extends SimpleRecAdapter<GoodsFenFuJieModel, GoodsItemAdapterFenFuJie.ViewHolder> {

    public GoodsItemAdapterFenFuJie(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_goods_item_fen_fu_jie;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        GoodsFenFuJieModel model = data.get(i);
        if (!TextUtils.isEmpty(model.getDes()) && model.getDes().length() > 2) {
            viewHolder.cycleTv.setText("周期" + model.getDes() + "个月");
        }
        if (i == 0) {
            viewHolder.parentFl.setBackgroundResource(R.drawable.xhrtusrtu);
            viewHolder.limitTv.setTextColor(context.getResources().getColor(R.color.white));
            viewHolder.chanpinedu_tv.setTextColor(context.getResources().getColor(R.color.white));
            viewHolder.productNameTv.setTextColor(context.getResources().getColor(R.color.white));
            viewHolder.cycleTv.setTextColor(context.getResources().getColor(R.color.white));
            viewHolder.passingRateTv.setTextColor(context.getResources().getColor(R.color.white));
            viewHolder.tagTv.setBackgroundResource(R.drawable.goods_bg_normal);
        } else {
            viewHolder.parentFl.setBackgroundResource(R.drawable.vcnrtusrtu);
            viewHolder.limitTv.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.chanpinedu_tv.setTextColor(context.getResources().getColor(R.color.color_999999));
            viewHolder.productNameTv.setTextColor(context.getResources().getColor(R.color.text_normal_color));
            viewHolder.cycleTv.setTextColor(context.getResources().getColor(R.color.color_999999));
            viewHolder.passingRateTv.setTextColor(context.getResources().getColor(R.color.color_999999));
            viewHolder.tagTv.setBackgroundResource(R.drawable.goods_bg_2);
        }
        viewHolder.passingRateTv.setText(model.getPassingRate() + "下款");
        viewHolder.tagTv.setText(model.getTag());
        viewHolder.productNameTv.setText(model.getProductName());
        ILFactory.getLoader().loadNet(viewHolder.productImg, FenFuJieApi.API_BASE_URL + model.getProductLogo(), new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        viewHolder.limitTv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
        viewHolder.clickView.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, model, 1, viewHolder);
        });
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
        @BindView(R.id.chanpinedu_tv)
        TextView chanpinedu_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
