package com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaadapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nsryryasdt.ioerdfjrtu.R;
import com.nsryryasdt.ioerdfjrtu.base.SimpleRecAdapter;
import com.nsryryasdt.ioerdfjrtu.imageloader.ILFactory;
import com.nsryryasdt.ioerdfjrtu.imageloader.ILoader;
import com.nsryryasdt.ioerdfjrtu.kit.KnifeKit;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.ZhouZhuanXinYongKaApi;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.GoodsZhouZhuanXinYongKaModel;

import butterknife.BindView;

public class GoodsItemAdapterZhouZhuanXinYongKa extends SimpleRecAdapter<GoodsZhouZhuanXinYongKaModel, GoodsItemAdapterZhouZhuanXinYongKa.ViewHolder> {

    public GoodsItemAdapterZhouZhuanXinYongKa(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_goods_item_zhou_zhuan_xin_yong_ka;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        GoodsZhouZhuanXinYongKaModel model = data.get(i);
        viewHolder.productNameTv.setText(model.getTitle());
        Glide.with(context).load(ZhouZhuanXinYongKaApi.API_BASE_URL + model.getImgs()).into(viewHolder.productImg);
        ILFactory.getLoader().loadNet(viewHolder.productImg, ZhouZhuanXinYongKaApi.API_BASE_URL + model.getImgs(), new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        viewHolder.text_1.setText("·" + model.getMax_money());
        viewHolder.text_2.setText("·" + model.getDay_money());
        viewHolder.text_3.setText("·" + model.getInfo());
        viewHolder.clickView.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, model, 1, viewHolder);
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_name_tv)
        TextView productNameTv;
        @BindView(R.id.product_img)
        ImageView productImg;
        @BindView(R.id.click_view)
        View clickView;
        @BindView(R.id.text_1)
        TextView text_1;
        @BindView(R.id.text_2)
        TextView text_2;
        @BindView(R.id.text_3)
        TextView text_3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
