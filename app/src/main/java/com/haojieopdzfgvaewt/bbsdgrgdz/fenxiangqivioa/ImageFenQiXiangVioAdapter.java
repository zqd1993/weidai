package com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqivioa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haojieopdzfgvaewt.bbsdgrgdz.R;
import com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqivioapi.HttpApiFenQiXiangVio;
import com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqiviom.ProductModelFenQiXiangVio;
import com.haojieopdzfgvaewt.bbsdgrgdz.imageloader.ILFactory;
import com.haojieopdzfgvaewt.bbsdgrgdz.imageloader.ILoader;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class ImageFenQiXiangVioAdapter extends BannerAdapter<ProductModelFenQiXiangVio, ImageFenQiXiangVioAdapter.ImageHolder> {

    private BannerClickedListener bannerClickedListener;

    public ImageFenQiXiangVioAdapter(List<ProductModelFenQiXiangVio> datas) {
        super(datas);
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_banner_item_fen_xiang_qi_vio, parent, false);
        return new ImageFenQiXiangVioAdapter.ImageHolder(view);
    }

    @Override
    public void onBindView(ImageHolder holder, ProductModelFenQiXiangVio data, int position, int size) {
        holder.shangpin_name_tv.setText(data.getProductName());
        holder.tedian_tv.setText(data.getTag());
        holder.edu_tv.setText(data.getMinAmount() + "-" + data.getMaxAmount());
        holder.shijian_tv.setText(data.getDes());
        holder.shuliang_tv.setText(String.valueOf(data.getPassingRate()));
        ILFactory.getLoader().loadNet(holder.product_img, HttpApiFenQiXiangVio.HTTP_API_URL + data.getProductLogo(),
                new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        holder.parentLl.setOnClickListener(v -> {
            if (bannerClickedListener != null){
                bannerClickedListener.onBannerClicked(data);
            }
        });
        holder.product_img_sl.setOnClickListener(v -> {
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
        View product_img_sl;
        ImageView product_img;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            shangpin_name_tv = itemView.findViewById(R.id.shangpin_name_tv);
            tedian_tv = itemView.findViewById(R.id.tedian_tv);
            edu_tv = itemView.findViewById(R.id.edu_tv);
            shijian_tv = itemView.findViewById(R.id.shijian_tv);
            shuliang_tv = itemView.findViewById(R.id.shuliang_tv);
            parentLl = itemView.findViewById(R.id.parent_ll);
            product_img_sl = itemView.findViewById(R.id.product_img_sl);
            product_img = itemView.findViewById(R.id.product_img);
        }
    }

    public void setBannerClickedListener(BannerClickedListener bannerClickedListener){
        this.bannerClickedListener = bannerClickedListener;
    }

    public interface BannerClickedListener{
        void onBannerClicked(ProductModelFenQiXiangVio entity);
    }

}
