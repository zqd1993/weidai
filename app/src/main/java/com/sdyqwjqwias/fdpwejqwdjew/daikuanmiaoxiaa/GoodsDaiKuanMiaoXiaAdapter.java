package com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaa;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sdyqwjqwias.fdpwejqwdjew.R;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaapi.DaiKuanMiaoXiaHttpApi;
import com.sdyqwjqwias.fdpwejqwdjew.base.SimpleRecAdapter;
import com.sdyqwjqwias.fdpwejqwdjew.imageloader.ILFactory;
import com.sdyqwjqwias.fdpwejqwdjew.imageloader.ILoader;
import com.sdyqwjqwias.fdpwejqwdjew.kit.KnifeKit;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiam.ProductDaiKuanMiaoXiaModel;

import butterknife.BindView;

public class GoodsDaiKuanMiaoXiaAdapter extends SimpleRecAdapter<ProductDaiKuanMiaoXiaModel, GoodsDaiKuanMiaoXiaAdapter.GoodsHolder> {

    public GoodsDaiKuanMiaoXiaAdapter(Context context) {
        super(context);
    }

    @Override
    public GoodsHolder newViewHolder(View itemView) {
        return new GoodsHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_dai_kuan_miao_xia_product_item;
    }

    @Override
    public void onBindViewHolder(GoodsHolder goodsHolder, int i) {
        ProductDaiKuanMiaoXiaModel model = data.get(i);
        goodsHolder.product_name_tv.setText(model.getProductName());
        ILFactory.getLoader().loadNet(goodsHolder.product_img, DaiKuanMiaoXiaHttpApi.HTTP_API_URL + model.getProductLogo(),
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
