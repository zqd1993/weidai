package com.rtvfbgfh.yuiyjghn.a;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rtvfbgfh.yuiyjghn.R;
import com.rtvfbgfh.yuiyjghn.api.NewApi;
import com.rtvfbgfh.yuiyjghn.imageloader.ILFactory;
import com.rtvfbgfh.yuiyjghn.imageloader.ILoader;
import com.rtvfbgfh.yuiyjghn.m.RenRenGoodsModel;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class ImageAdapter extends BannerAdapter<RenRenGoodsModel, ImageAdapter.MyHolder> {

    private Context context;

    private BannerClickedListener bannerClickedListener;

    public ImageAdapter(List<RenRenGoodsModel> mDatas, Context context) {
        super(mDatas);
        this.context = context;
    }

    //更新数据
    public void updateData(List<RenRenGoodsModel> data) {
        //这里的代码自己发挥，比如如下的写法等等
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }


    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public ImageAdapter.MyHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_goods_view, parent, false);
        return new ImageAdapter.MyHolder(view);
    }

    @Override
    public void onBindView(ImageAdapter.MyHolder holder, RenRenGoodsModel data, int position, int size) {

        ILFactory.getLoader().loadNet(holder.goodsPic, NewApi.HTTP_API_URL + data.getProductLogo(),
                new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));

        holder.rixiTv.setText(data.getTag());
        holder.personalNumberTv.setText(String.valueOf(data.getPassingRate()));
        holder.timeTv.setText(data.getDes() + "个月");
        holder.goodsNameTv.setText(data.getProductName());
        holder.moneyNumberTv.setText(data.getMinAmount() + "-" + data.getMaxAmount());
        holder.clickView.setOnClickListener(v -> {
            if (bannerClickedListener != null){
                bannerClickedListener.onBannerClicked(data);
            }
        });
    }

    class MyHolder extends RecyclerView.ViewHolder {
        public TextView rixiTv;
        public TextView personalNumberTv;
        public TextView timeTv;
        public ImageView goodsPic;
        public TextView goodsNameTv;
        public TextView moneyNumberTv;
        public View clickView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            this.rixiTv = itemView.findViewById(R.id.rixi_tv);
            this.personalNumberTv = itemView.findViewById(R.id.personal_number_tv);
            this.timeTv = itemView.findViewById(R.id.time_tv);
            this.goodsPic = itemView.findViewById(R.id.goods_pic);
            this.goodsNameTv = itemView.findViewById(R.id.goods_name_tv);
            this.moneyNumberTv = itemView.findViewById(R.id.money_number_tv);
            this.clickView = itemView.findViewById(R.id.click_view);
        }
    }

    public void setBannerClickedListener(BannerClickedListener bannerClickedListener){
        this.bannerClickedListener = bannerClickedListener;
    }

    public interface BannerClickedListener{
        void onBannerClicked(RenRenGoodsModel entity);
    }

}
