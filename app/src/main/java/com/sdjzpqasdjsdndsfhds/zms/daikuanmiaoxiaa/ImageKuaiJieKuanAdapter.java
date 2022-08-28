package com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiaa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sdjzpqasdjsdndsfhds.zms.R;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiaapi.KuaiJieKuanHttpApi;
import com.sdjzpqasdjsdndsfhds.zms.imageloader.ILFactory;
import com.sdjzpqasdjsdndsfhds.zms.imageloader.ILoader;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiam.ProductKuaiJieKuanModel;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class ImageKuaiJieKuanAdapter extends BannerAdapter<ProductKuaiJieKuanModel, ImageKuaiJieKuanAdapter.ImageHolder> {

    private BannerClickedListener bannerClickedListener;

    public ImageKuaiJieKuanAdapter(List<ProductKuaiJieKuanModel> datas) {
        super(datas);
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_kuai_jie_kuan_banner_item, parent, false);
        return new ImageKuaiJieKuanAdapter.ImageHolder(view);
    }

    @Override
    public void onBindView(ImageHolder holder, ProductKuaiJieKuanModel data, int position, int size) {
        holder.shangpin_name_tv.setText(data.getProductName());
        holder.tedian_tv.setText(data.getTag());
        holder.edu_tv.setText(data.getMinAmount() + "-" + data.getMaxAmount());
        holder.shijian_tv.setText("周期" + data.getDes() + "个月");
        holder.shuliang_tv.setText(data.getPassingRate() + "下款");
        ILFactory.getLoader().loadNet(holder.product_img, KuaiJieKuanHttpApi.HTTP_API_URL + data.getProductLogo(),
                new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        holder.parentLl.setOnClickListener(v -> {
            if (bannerClickedListener != null){
                bannerClickedListener.onBannerClicked(data);
            }
        });
        holder.product_img.setOnClickListener(v -> {
            if (bannerClickedListener != null){
                bannerClickedListener.onBannerClicked(data);
            }
        });
    }

    public class ImageHolder extends RecyclerView.ViewHolder{

        TextView shangpin_name_tv;
        TextView tedian_tv;
        TextView edu_tv;
        TextView shijian_tv;
        TextView shuliang_tv;
        View parentLl;
        ImageView product_img;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            shangpin_name_tv = itemView.findViewById(R.id.shangpin_name_tv);
            tedian_tv = itemView.findViewById(R.id.tedian_tv);
            edu_tv = itemView.findViewById(R.id.edu_tv);
            shijian_tv = itemView.findViewById(R.id.shijian_tv);
            shuliang_tv = itemView.findViewById(R.id.shuliang_tv);
            parentLl = itemView.findViewById(R.id.parent_ll);
            product_img = itemView.findViewById(R.id.product_img);
        }
    }

    public void setBannerClickedListener(BannerClickedListener bannerClickedListener){
        this.bannerClickedListener = bannerClickedListener;
    }

    public interface BannerClickedListener{
        void onBannerClicked(ProductKuaiJieKuanModel entity);
    }

}