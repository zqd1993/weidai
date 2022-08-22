package com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaadapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.suijiexinyongkafwert.dffdgaeryt.R;
import com.suijiexinyongkafwert.dffdgaeryt.base.SimpleRecAdapter;
import com.suijiexinyongkafwert.dffdgaeryt.imageloader.ILFactory;
import com.suijiexinyongkafwert.dffdgaeryt.imageloader.ILoader;
import com.suijiexinyongkafwert.dffdgaeryt.kit.KnifeKit;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.GoodsSuiJieXinYongKaModel;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.SuiJieXinYongKaApi;

import butterknife.BindView;

public class SuiJieXinYongKaGoodsItemAdapter extends SimpleRecAdapter<GoodsSuiJieXinYongKaModel, SuiJieXinYongKaGoodsItemAdapter.ViewHolder> {

    public SuiJieXinYongKaGoodsItemAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_sui_jie_xin_yong_ka_goods_item;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        GoodsSuiJieXinYongKaModel model = data.get(i);
        viewHolder.productNameTv.setText(model.getTitle());
        Glide.with(context).load(SuiJieXinYongKaApi.API_BASE_URL + model.getImgs()).into(viewHolder.productImg);
        ILFactory.getLoader().loadNet(viewHolder.productImg, SuiJieXinYongKaApi.API_BASE_URL + model.getImgs(), new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
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
        @BindView(R.id.parent_fl)
        LinearLayout parent_fl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
