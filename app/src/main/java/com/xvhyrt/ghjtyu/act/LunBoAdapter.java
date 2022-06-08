package com.xvhyrt.ghjtyu.act;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xvhyrt.ghjtyu.R;
import com.xvhyrt.ghjtyu.net.WangLuoApi;
import com.xvhyrt.ghjtyu.imageloader.ILFactory;
import com.xvhyrt.ghjtyu.imageloader.ILoader;
import com.xvhyrt.ghjtyu.bean.ChanPinModel;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class LunBoAdapter extends BannerAdapter<ChanPinModel, LunBoAdapter.ImageHolder> {

    private BannerClickedListener bannerClickedListener;

    public LunBoAdapter(List<ChanPinModel> datas) {
        super(datas);
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lunbo_item, parent, false);
        return new LunBoAdapter.ImageHolder(view);
    }

    @Override
    public void onBindView(ImageHolder holder, ChanPinModel data, int position, int size) {
        holder.goods_mingzi_tv.setText(data.getProductName());
        holder.label_tv.setText(data.getTag());
        holder.price_tv.setText(data.getMinAmount() + "-" + data.getMaxAmount());
        holder.time_tv.setText(data.getDes() + "个月");
        holder.many_tv.setText(String.valueOf(data.getPassingRate()));
        ILFactory.getLoader().loadNet(holder.product_img, WangLuoApi.HTTP_API_URL + data.getProductLogo(),
                new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        holder.parentLl.setOnClickListener(v -> {
            if (bannerClickedListener != null){
                bannerClickedListener.onBannerClicked(data);
            }
        });
        holder.yjsqSl.setOnClickListener(v -> {
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

        TextView goods_mingzi_tv;
        TextView label_tv;
        TextView price_tv;
        TextView time_tv;
        TextView many_tv;
        View parentLl;
        ImageView product_img;
        View yjsqSl;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            goods_mingzi_tv = itemView.findViewById(R.id.goods_mingzi_tv);
            label_tv = itemView.findViewById(R.id.label_tv);
            price_tv = itemView.findViewById(R.id.price_tv);
            time_tv = itemView.findViewById(R.id.time_tv);
            many_tv = itemView.findViewById(R.id.many_tv);
            parentLl = itemView.findViewById(R.id.parent_ll);
            product_img = itemView.findViewById(R.id.product_img);
            yjsqSl = itemView.findViewById(R.id.yjsq_sl);
        }
    }

    public void setBannerClickedListener(BannerClickedListener bannerClickedListener){
        this.bannerClickedListener = bannerClickedListener;
    }

    public interface BannerClickedListener{
        void onBannerClicked(ChanPinModel entity);
    }

}
