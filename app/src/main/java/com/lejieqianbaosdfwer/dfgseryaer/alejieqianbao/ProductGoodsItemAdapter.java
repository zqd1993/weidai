package com.lejieqianbaosdfwer.dfgseryaer.alejieqianbao;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lejieqianbaosdfwer.dfgseryaer.R;
import com.lejieqianbaosdfwer.dfgseryaer.base.SimpleRecAdapter;
import com.lejieqianbaosdfwer.dfgseryaer.imageloader.ILFactory;
import com.lejieqianbaosdfwer.dfgseryaer.imageloader.ILoader;
import com.lejieqianbaosdfwer.dfgseryaer.kit.KnifeKit;
import com.lejieqianbaosdfwer.dfgseryaer.apilejieqianbao.HttpApiLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.mlejieqianbao.ProductModelLeJieQianBao;


import butterknife.BindView;

public class ProductGoodsItemAdapter extends SimpleRecAdapter<ProductModelLeJieQianBao, ProductGoodsItemAdapter.ProductGoodsItemHolder> {


    public ProductGoodsItemAdapter(Context context) {
        super(context);
    }

    @Override
    public ProductGoodsItemHolder newViewHolder(View itemView) {
        return new ProductGoodsItemHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_le_jie_qian_bao_product_item;
    }

    @Override
    public void onBindViewHolder(ProductGoodsItemHolder holder, int i) {
        ProductModelLeJieQianBao item = data.get(i);
        holder.shangpin_name_tv.setText(item.getProductName());
        holder.tedian_tv.setText(item.getTag());
        holder.edu_tv.setText(item.getMinAmount() + "-" + item.getMaxAmount());
        holder.shijian_tv.setText(item.getDes());
        holder.shuliang_tv.setText(String.valueOf(item.getPassingRate()));
        ILFactory.getLoader().loadNet(holder.product_img, HttpApiLeJieQianBao.HTTP_API_URL + item.getProductLogo(),
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
