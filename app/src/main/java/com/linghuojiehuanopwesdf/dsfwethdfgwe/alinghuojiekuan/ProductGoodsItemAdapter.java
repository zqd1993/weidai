package com.linghuojiehuanopwesdf.dsfwethdfgwe.alinghuojiekuan;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.linghuojiehuanopwesdf.dsfwethdfgwe.R;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.base.SimpleRecAdapter;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.imageloader.ILFactory;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.imageloader.ILoader;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.kit.KnifeKit;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.apilinghuojiekuan.HttpLingHuoJieHuanApi;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mlinghuojiekuan.ProductLingHuoJieHuanModel;


import butterknife.BindView;

public class ProductGoodsItemAdapter extends SimpleRecAdapter<ProductLingHuoJieHuanModel, ProductGoodsItemAdapter.ProductGoodsItemHolder> {


    public ProductGoodsItemAdapter(Context context) {
        super(context);
    }

    @Override
    public ProductGoodsItemHolder newViewHolder(View itemView) {
        return new ProductGoodsItemHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_ling_huo_jie_huan_product_item;
    }

    @Override
    public void onBindViewHolder(ProductGoodsItemHolder holder, int i) {
        ProductLingHuoJieHuanModel item = data.get(i);
        holder.shangpin_name_tv.setText(item.getProductName());
        holder.tedian_tv.setText(item.getTag());
        holder.edu_tv.setText(item.getMinAmount() + "-" + item.getMaxAmount());
        holder.shijian_tv.setText(item.getDes());
        holder.shuliang_tv.setText(String.valueOf(item.getPassingRate()));
        ILFactory.getLoader().loadNet(holder.product_img, HttpLingHuoJieHuanApi.HTTP_API_URL + item.getProductLogo(),
                new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        holder.click_view.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, item, 1, holder);
        });
    }

    public class ProductGoodsItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.shangpin_name_tv)
        TextView shangpin_name_tv;
        @BindView(R.id.tedian_tv)
        TextView tedian_tv;
        @BindView(R.id.edu_tv)
        TextView edu_tv;
        @BindView(R.id.shijian_tv)
        TextView shijian_tv;
        @BindView(R.id.shuliang_tv)
        TextView shuliang_tv;
        @BindView(R.id.product_img)
        ImageView product_img;
        @BindView(R.id.click_view)
        View click_view;

        public ProductGoodsItemHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
