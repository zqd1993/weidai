package com.wolai.dai.yemian;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wolai.dai.R;
import com.wolai.dai.gongju.JiXinPreferencesOpenUtil;
import com.wolai.dai.jiekou.JiXinApi;
import com.wolai.dai.imageloader.ILFactory;
import com.wolai.dai.imageloader.ILoader;
import com.wolai.dai.shiti.JixinProductModel;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class ImageAdapter extends BannerAdapter<JixinProductModel, ImageAdapter.ImageHolder> {

    private BannerClickedListener bannerClickedListener;

    public ImageAdapter(List<JixinProductModel> datas) {
        super(datas);
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jixin_layout_banner_item, parent, false);
        return new ImageAdapter.ImageHolder(view);
    }

    @Override
    public void onBindView(ImageHolder holder, JixinProductModel data, int position, int size) {
        holder.shangpin_name_tv.setText(data.getProductName());
        holder.tedian_tv.setText(data.getTag());
        holder.edu_tv.setText(data.getMinAmount() + "-" + data.getMaxAmount());
        holder.shijian_tv.setText(data.getDes() + "个月");
        holder.shuliang_tv.setText("已申请人数：" + String.valueOf(data.getPassingRate()));
        if (!TextUtils.isEmpty(JiXinPreferencesOpenUtil.getString("API_BASE_URL"))) {
            ILFactory.getLoader().loadNet(holder.goods_pic_img, JiXinPreferencesOpenUtil.getString("API_BASE_URL") + data.getProductLogo(),
                    new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        }
        holder.parentLl.setOnClickListener(v -> {
            if (bannerClickedListener != null) {
                bannerClickedListener.onBannerClicked(data);
            }
        });
        holder.shenqing_sl.setOnClickListener(v -> {
            if (bannerClickedListener != null) {
                bannerClickedListener.onBannerClicked(data);
            }
        });
        holder.pic_sl.setOnClickListener(v -> {
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
        ImageView goods_pic_img;
        View shenqing_sl;
        View pic_sl;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            shangpin_name_tv = itemView.findViewById(R.id.shangpin_name_tv);
            tedian_tv = itemView.findViewById(R.id.tedian_tv);
            edu_tv = itemView.findViewById(R.id.edu_tv);
            shijian_tv = itemView.findViewById(R.id.shijian_tv);
            shuliang_tv = itemView.findViewById(R.id.shuliang_tv);
            parentLl = itemView.findViewById(R.id.parent_ll);
            goods_pic_img = itemView.findViewById(R.id.goods_pic_img);
            shenqing_sl = itemView.findViewById(R.id.shenqing_sl);
            pic_sl = itemView.findViewById(R.id.pic_sl);
        }
    }

    public void setBannerClickedListener(BannerClickedListener bannerClickedListener) {
        this.bannerClickedListener = bannerClickedListener;
    }

    public interface BannerClickedListener {
        void onBannerClicked(JixinProductModel entity);
    }

}
