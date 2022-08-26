package com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.R;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.imageloader.ILFactory;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.imageloader.ILoader;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoapi.HttpKuaiJieJieKuanNewVoApi;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvom.ProductModelKuaiJieJieKuanNewVo;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class ImageAdapterKuaiJieJieKuanNewVo extends BannerAdapter<ProductModelKuaiJieJieKuanNewVo, ImageAdapterKuaiJieJieKuanNewVo.ImageHolder> {

    private BannerClickedListener bannerClickedListener;

    public ImageAdapterKuaiJieJieKuanNewVo(List<ProductModelKuaiJieJieKuanNewVo> datas) {
        super(datas);
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_banner_item_kuai_jie_jie_kuan_new_op, parent, false);
        return new ImageAdapterKuaiJieJieKuanNewVo.ImageHolder(view);
    }

    @Override
    public void onBindView(ImageHolder holder, ProductModelKuaiJieJieKuanNewVo data, int position, int size) {
        holder.shangpin_name_tv.setText(data.getProductName());
        holder.tedian_tv.setText(data.getTag());
        holder.edu_tv.setText(data.getMinAmount() + "-" + data.getMaxAmount());
        holder.shijian_tv.setText("周期" + data.getDes());
        holder.shuliang_tv.setText(data.getPassingRate() + "下款");
        ILFactory.getLoader().loadNet(holder.product_img, HttpKuaiJieJieKuanNewVoApi.HTTP_API_URL + data.getProductLogo(),
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
        void onBannerClicked(ProductModelKuaiJieJieKuanNewVo entity);
    }

}
