package com.ufaofqsbxo.uunllhykas.a;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ufaofqsbxo.uunllhykas.R;
import com.ufaofqsbxo.uunllhykas.api.MyApi;
import com.ufaofqsbxo.uunllhykas.base.SimpleRecAdapter;
import com.ufaofqsbxo.uunllhykas.imageloader.ILFactory;
import com.ufaofqsbxo.uunllhykas.imageloader.ILoader;
import com.ufaofqsbxo.uunllhykas.kit.KnifeKit;
import com.ufaofqsbxo.uunllhykas.m.ShangPinModel;

import butterknife.BindView;

public class GoodsAdapter extends SimpleRecAdapter<ShangPinModel, GoodsAdapter.GoodsHolder> {

    public GoodsAdapter(Context context) {
        super(context);
    }

    @Override
    public GoodsHolder newViewHolder(View itemView) {
        return new GoodsHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_product_view;
    }

    @Override
    public void onBindViewHolder(GoodsHolder goodsHolder, int i) {
        ShangPinModel model = data.get(i);
        ILFactory.getLoader().loadNet(goodsHolder.imgGoods, MyApi.HTTP_API_URL + model.getProductLogo(),
                new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        goodsHolder.tvTime.setText(model.getDes() + "个月");
        goodsHolder.tvPeopleNumber.setText(String.valueOf(model.getPassingRate()));
        goodsHolder.goodsNameTv.setText(model.getProductName());
        goodsHolder.tvTag.setText(model.getTag());
        goodsHolder.tvMoneyNumber.setText(model.getMinAmount() + "-" + model.getMaxAmount());
        goodsHolder.clickView.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, model, 1, goodsHolder);
        });
    }

    public class GoodsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_goods)
        ImageView imgGoods;
        @BindView(R.id.tv_goods_name)
        TextView goodsNameTv;
        @BindView(R.id.tv_people_number)
        TextView tvPeopleNumber;
        @BindView(R.id.tv_money_number)
        TextView tvMoneyNumber;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_tag)
        TextView tvTag;
        @BindView(R.id.click_view)
        View clickView;

        public GoodsHolder(@NonNull View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
