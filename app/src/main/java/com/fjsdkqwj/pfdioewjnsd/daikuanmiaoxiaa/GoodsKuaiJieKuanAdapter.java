package com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiaa;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fjsdkqwj.pfdioewjnsd.R;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiaapi.KuaiJieKuanHttpApi;
import com.fjsdkqwj.pfdioewjnsd.base.SimpleRecAdapter;
import com.fjsdkqwj.pfdioewjnsd.imageloader.ILFactory;
import com.fjsdkqwj.pfdioewjnsd.imageloader.ILoader;
import com.fjsdkqwj.pfdioewjnsd.kit.KnifeKit;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiam.ProductKuaiJieKuanModel;

import butterknife.BindView;

public class GoodsKuaiJieKuanAdapter extends SimpleRecAdapter<ProductKuaiJieKuanModel, GoodsKuaiJieKuanAdapter.GoodsHolder> {

    public GoodsKuaiJieKuanAdapter(Context context) {
        super(context);
    }

    @Override
    public GoodsHolder newViewHolder(View itemView) {
        return new GoodsHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_kuai_jie_kuan_product_item;
    }

    @Override
    public void onBindViewHolder(GoodsHolder goodsHolder, int i) {
        ProductKuaiJieKuanModel model = data.get(i);
        goodsHolder.product_name_tv.setText(model.getProductName());
        ILFactory.getLoader().loadNet(goodsHolder.product_img, KuaiJieKuanHttpApi.HTTP_API_URL + model.getProductLogo(),
                new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        goodsHolder.remind_tv.setText(model.getTag());
        goodsHolder.money_number_tv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
        goodsHolder.yjsq_sl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecItemClick().onItemClick(i, model, 1, goodsHolder);
            }
        });
        goodsHolder.product_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecItemClick().onItemClick(i, model, 1, goodsHolder);
            }
        });
        goodsHolder.parent_fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecItemClick().onItemClick(i, model, 1, goodsHolder);
            }
        });
    }

    public class GoodsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_name_tv)
        TextView product_name_tv;
        @BindView(R.id.product_img)
        ImageView product_img;
        @BindView(R.id.money_number_tv)
        TextView money_number_tv;
        @BindView(R.id.remind_tv)
        TextView remind_tv;
        @BindView(R.id.yjsq_sl)
        View yjsq_sl;
        @BindView(R.id.parent_fl)
        View parent_fl;

        public GoodsHolder(@NonNull  View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
