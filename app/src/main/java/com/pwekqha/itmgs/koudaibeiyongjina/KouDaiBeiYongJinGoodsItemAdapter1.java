package com.pwekqha.itmgs.koudaibeiyongjina;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pwekqha.itmgs.R;
import com.pwekqha.itmgs.koudaibeiyongjinapi.HttpKouDaiBeiYongJinApi;
import com.pwekqha.itmgs.base.SimpleRecAdapter;
import com.pwekqha.itmgs.imageloader.ILFactory;
import com.pwekqha.itmgs.imageloader.ILoader;
import com.pwekqha.itmgs.kit.KnifeKit;
import com.pwekqha.itmgs.koudaibeiyongjinm.ProductKouDaiBeiYongJinModel;

import butterknife.BindView;

public class KouDaiBeiYongJinGoodsItemAdapter1 extends SimpleRecAdapter<ProductKouDaiBeiYongJinModel, KouDaiBeiYongJinGoodsItemAdapter1.GoodsItemHolder> {

    public KouDaiBeiYongJinGoodsItemAdapter1(Context context) {
        super(context);
    }

    @Override
    public GoodsItemHolder newViewHolder(View itemView) {
        return new GoodsItemHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_kou_dai_bei_yong_jin_product_view;
    }

    @Override
    public void onBindViewHolder(GoodsItemHolder goodsItemHolder, int i) {
        ProductKouDaiBeiYongJinModel model = data.get(i);
        ILFactory.getLoader().loadNet(goodsItemHolder.product_img, HttpKouDaiBeiYongJinApi.HTTP_API_URL + model.getProductLogo(),
                new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        goodsItemHolder.product_name_tv.setText(model.getProductName());
        goodsItemHolder.remind_tv.setText(model.getTag());
        goodsItemHolder.money_number_tv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
        goodsItemHolder.zhouqi_tv.setText("周期" + model.getDes() + "个月");
        goodsItemHolder.xiazai_tv.setText(model.getPassingRate() + "下款");
        goodsItemHolder.click_view.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, model, 1, goodsItemHolder);
        });
    }

    public class GoodsItemHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.product_img)
        ImageView product_img;
        @BindView(R.id.product_name_tv)
        TextView product_name_tv;
        @BindView(R.id.remind_tv)
        TextView remind_tv;
        @BindView(R.id.money_number_tv)
        TextView money_number_tv;
        @BindView(R.id.zhouqi_tv)
        TextView zhouqi_tv;
        @BindView(R.id.xiazai_tv)
        TextView xiazai_tv;
        @BindView(R.id.click_view)
        View click_view;

        public GoodsItemHolder( View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}