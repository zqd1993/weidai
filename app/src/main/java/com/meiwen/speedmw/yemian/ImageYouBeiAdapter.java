package com.meiwen.speedmw.yemian;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.meiwen.speedmw.R;
import com.meiwen.speedmw.gongju.PreferencesYouBeiOpenUtil;
import com.meiwen.speedmw.imageloader.ILFactory;
import com.meiwen.speedmw.imageloader.ILoader;
import com.meiwen.speedmw.jiekou.HttpYouBeiApi;
import com.meiwen.speedmw.moxing.ProductYouBeiModel;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class ImageYouBeiAdapter extends BannerAdapter<ProductYouBeiModel, ImageYouBeiAdapter.ImageHolder> {

    private BannerClickedListener bannerClickedListener;
    private Context context;

    public ImageYouBeiAdapter(List<ProductYouBeiModel> datas, Context context) {
        super(datas);
        this.context = context;
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_banner_item, parent, false);
        return new ImageYouBeiAdapter.ImageHolder(view);
    }

    @Override
    public void onBindView(ImageHolder holder, ProductYouBeiModel data, int position, int size) {
        holder.shangpin_name_tv.setText(data.getProductName());
        holder.tedian_tv.setText(data.getTag());
        holder.edu_tv.setText(data.getMinAmount() + "-" + data.getMaxAmount());
        holder.shijian_tv.setText(data.getDes() + "个月");
        holder.shuliang_tv.setText(String.valueOf(data.getPassingRate()));
        if (!TextUtils.isEmpty(PreferencesYouBeiOpenUtil.getString("HTTP_API_URL"))) {
            ILFactory.getLoader().loadNet(holder.shangpin_img, PreferencesYouBeiOpenUtil.getString("HTTP_API_URL") + data.getProductLogo(),
                    new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        }
        holder.parentLl.setOnClickListener(v -> {
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
        ImageView shangpin_img;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            shangpin_name_tv = itemView.findViewById(R.id.shangpin_name_tv);
            tedian_tv = itemView.findViewById(R.id.tedian_tv);
            edu_tv = itemView.findViewById(R.id.edu_tv);
            shijian_tv = itemView.findViewById(R.id.shijian_tv);
            shuliang_tv = itemView.findViewById(R.id.shuliang_tv);
            parentLl = itemView.findViewById(R.id.parent_ll);
            shangpin_img = itemView.findViewById(R.id.shangpin_img);
        }
    }

    public void setBannerClickedListener(BannerClickedListener bannerClickedListener){
        this.bannerClickedListener = bannerClickedListener;
    }

    public interface BannerClickedListener{
        void onBannerClicked(ProductYouBeiModel entity);
    }

}
