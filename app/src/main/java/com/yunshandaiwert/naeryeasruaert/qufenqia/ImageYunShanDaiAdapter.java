package com.yunshandaiwert.naeryeasruaert.qufenqia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yunshandaiwert.naeryeasruaert.R;
import com.yunshandaiwert.naeryeasruaert.imageloader.ILFactory;
import com.yunshandaiwert.naeryeasruaert.imageloader.ILoader;
import com.yunshandaiwert.naeryeasruaert.qufenqiapi.HttpYunShanDaiApi;
import com.yunshandaiwert.naeryeasruaert.qufenqim.YunShanDaiProductModel;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class ImageYunShanDaiAdapter extends BannerAdapter<YunShanDaiProductModel, ImageYunShanDaiAdapter.ImageHolder> {

    private BannerClickedListener bannerClickedListener;

    public ImageYunShanDaiAdapter(List<YunShanDaiProductModel> datas) {
        super(datas);
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_banner_item_yun_shan_dai, parent, false);
        return new ImageYunShanDaiAdapter.ImageHolder(view);
    }

    @Override
    public void onBindView(ImageHolder holder, YunShanDaiProductModel data, int position, int size) {
        holder.shangpin_name_tv.setText(data.getProductName());
        holder.tedian_tv.setText(data.getTag());
        holder.edu_tv.setText(data.getMinAmount() + "-" + data.getMaxAmount());
        holder.shijian_tv.setText(data.getDes() + "个月");
        holder.shuliang_tv.setText(String.valueOf(data.getPassingRate()));
        ILFactory.getLoader().loadNet(holder.product_img, HttpYunShanDaiApi.HTTP_API_URL + data.getProductLogo(),
                new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        holder.parentLl.setOnClickListener(v -> {
            if (bannerClickedListener != null) {
                bannerClickedListener.onBannerClicked(data);
            }
        });
    }

    public class ImageHolder extends RecyclerView.ViewHolder {

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
            ;
        }
    }

    public void setBannerClickedListener(BannerClickedListener bannerClickedListener) {
        this.bannerClickedListener = bannerClickedListener;
    }

    public interface BannerClickedListener {
        void onBannerClicked(YunShanDaiProductModel entity);
    }

}
