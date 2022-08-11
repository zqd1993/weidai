package com.aklsfasad.fsjhfkk.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.aklsfasad.fsjhfkk.R;
import com.aklsfasad.fsjhfkk.imageloader.ILFactory;
import com.aklsfasad.fsjhfkk.imageloader.ILoader;
import com.aklsfasad.fsjhfkk.model.GoodsHuiMinModel;
import com.aklsfasad.fsjhfkk.net.Api;
import com.google.android.material.appbar.AppBarLayout;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class ImageAdapter extends BannerAdapter<GoodsHuiMinModel, ImageAdapter.ImageHolder> {

    private BannerClickedListener bannerClickedListener;

    public ImageAdapter(List<GoodsHuiMinModel> datas) {
        super(datas);
    }

    /**
     * 设置appbar偏移量
     *
     * @param appBar
     * @param offset
     */
    public void setAppBarLayoutOffset(AppBarLayout appBar, int offset) {
        CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) appBar.getLayoutParams()).getBehavior();
        if (behavior instanceof AppBarLayout.Behavior) {
            AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
            int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
            if (topAndBottomOffset != offset) {
                appBarLayoutBehavior.setTopAndBottomOffset(offset);
//                appBarLayoutBehavior.onNestedPreScroll(cl, appBar, view, 0, ScreenUtil.dp2px(view.getTop()), new int[]{0, 0}, 1);
            }
        }
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_banner_item, parent, false);
        return new ImageAdapter.ImageHolder(view);
    }

    @Override
    public void onBindView(ImageHolder holder, GoodsHuiMinModel data, int position, int size) {
        holder.shangpin_name_tv.setText(data.getProductName());
        holder.tedian_tv.setText(data.getTag());
//        holder.edu_tv.setText(data.getMinAmount() + "-" + data.getMaxAmount());
        holder.edu_tv.setText(data.getMaxAmount());
        holder.shijian_tv.setText("周期" + data.getDes());
        holder.shuliang_tv.setText(data.getPassingRate() + "下款");
        ILFactory.getLoader().loadNet(holder.goods_pic, Api.API_BASE_URL + data.getProductLogo(), new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        holder.click_view.setOnClickListener(v -> {
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
        ImageView goods_pic;
        View yjsq_sl;
        View click_view;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            shangpin_name_tv = itemView.findViewById(R.id.shangpin_name_tv);
            tedian_tv = itemView.findViewById(R.id.tedian_tv);
            edu_tv = itemView.findViewById(R.id.edu_tv);
            shijian_tv = itemView.findViewById(R.id.shijian_tv);
            shuliang_tv = itemView.findViewById(R.id.shuliang_tv);
            parentLl = itemView.findViewById(R.id.parent_ll);
            goods_pic = itemView.findViewById(R.id.goods_pic);
            yjsq_sl = itemView.findViewById(R.id.yjsq_sl);
            click_view = itemView.findViewById(R.id.click_view);
        }
    }

    public void setBannerClickedListener(BannerClickedListener bannerClickedListener){
        this.bannerClickedListener = bannerClickedListener;
    }

    public interface BannerClickedListener{
        void onBannerClicked(GoodsHuiMinModel entity);
    }

}
