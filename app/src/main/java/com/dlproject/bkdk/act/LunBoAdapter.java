package com.dlproject.bkdk.act;

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
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.dlproject.bkdk.R;
import com.dlproject.bkdk.net.WangLuoApi;
import com.dlproject.bkdk.imageloader.ILFactory;
import com.dlproject.bkdk.imageloader.ILoader;
import com.dlproject.bkdk.bean.ChanPinModel;
import com.dlproject.bkdk.uti.SPFile;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class LunBoAdapter extends BannerAdapter<ChanPinModel, LunBoAdapter.ImageHolder> {

    private BannerClickedListener bannerClickedListener;

    public LunBoAdapter(List<ChanPinModel> datas) {
        super(datas);
    }

    /**
     * 显示照片
     * @param context
     * @param imageView
     * @param url
     */
    public static void displays(Context context, ImageView imageView, Object url) {
        Glide.with(context).load(url).skipMemoryCache(true)  //跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL) //硬盘缓存全部
                .into(imageView);
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lunbo_item, parent, false);
        return new LunBoAdapter.ImageHolder(view);
    }

    /**
     * 显示图片
     * @param context
     * @param imageView
     * @param url
     * @param tag
     */
    public static void display(Context context, ImageView imageView, String url, Object tag) {
        Glide.with(context).load(url).into(imageView);
    }


    @Override
    public void onBindView(ImageHolder holder, ChanPinModel data, int position, int size) {
        holder.goods_mingzi_tv.setText(data.getProductName());
        holder.label_tv.setText(data.getTag());
        holder.price_tv.setText(data.getMinAmount() + "-" + data.getMaxAmount());
        holder.time_tv.setText(data.getDes() + "个月");
        holder.many_tv.setText(String.valueOf(data.getPassingRate()));
        if (!TextUtils.isEmpty(SPFile.getString("HTTP_API_URL"))) {
            ILFactory.getLoader().loadNet(holder.product_img, SPFile.getString("HTTP_API_URL") + data.getProductLogo(),
                    new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        }
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

    public static void display(Context context, ImageView imageView, String url, int progressId) {
        RequestManager manager = Glide.with(context);
//        DrawableTypeRequest<String> load = manager.load(url);
//        load.error(R.drawable.ic_error).placeholder(new ColorDrawable(Color.GRAY)).into
//                (imageView);
        if (progressId != -1) {
//            load.placeholder(progressId);
        }
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

    public static void display(Context context, ImageView imageView, String url) {
        display(context, imageView, url, -1);
    }

    public static void cancel(Context context) {
    }

    public interface BannerClickedListener{
        void onBannerClicked(ChanPinModel entity);
    }

    /**
     * 设置磁盘缓存大小和位置,这里设置150M
     */
    public static void setInnerCacheDir(Context context){
        GlideBuilder builder = new GlideBuilder();
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "ImgCache", 150 * 1024 * 1024));
    }

}
