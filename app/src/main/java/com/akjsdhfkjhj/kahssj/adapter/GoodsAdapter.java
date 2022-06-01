package com.akjsdhfkjhj.kahssj.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akjsdhfkjhj.kahssj.R;
import com.akjsdhfkjhj.kahssj.base.SimpleRecAdapter;
import com.akjsdhfkjhj.kahssj.imageloader.ILFactory;
import com.akjsdhfkjhj.kahssj.imageloader.ILoader;
import com.akjsdhfkjhj.kahssj.kit.KnifeKit;
import com.akjsdhfkjhj.kahssj.model.ProductModel;
import com.akjsdhfkjhj.kahssj.net.Api;

import java.text.DecimalFormat;

import butterknife.BindView;

public class GoodsAdapter extends SimpleRecAdapter<ProductModel, GoodsAdapter.ViewHolder> {

    @Override
    public int getLayoutId() {
        return R.layout.adapter_goods_item;
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    public static CharSequence getAmount(String text1, int sizeChar1, String text2, int sizeChar2) {
        return "";
    }

    public static String getPrice(float price) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(price);
    }


    public GoodsAdapter(Context context) {
        super(context);
    }


    /***
     * 从相册中取图片
     */
    public static void pickPhoto(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, 999);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.parent_fl)
        View parentFl;
        @BindView(R.id.passing_rate_tv)
        TextView passingRateTv;
        @BindView(R.id.tag_tv)
        TextView tagTv;
        @BindView(R.id.product_name_tv)
        TextView productNameTv;
        @BindView(R.id.product_img)
        ImageView productImg;
        @BindView(R.id.limit_tv)
        TextView limitTv;
        @BindView(R.id.cycle_tv)
        TextView cycleTv;
        @BindView(R.id.click_view)
        View clickView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

    /**
     * 手机号 间隔
     *
     * @param mobile
     * @return
     */
    public static String getMobileByChar(String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return mobile;
        }
        if (mobile.contains("-")) {
            return mobile;
        }
        StringBuffer sb = new StringBuffer(mobile);
        sb.insert(7, " ");
        sb.insert(3, " ");
        return sb.toString().trim();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ProductModel model = data.get(i);
        if (!TextUtils.isEmpty(model.getDes()) && model.getDes().length() > 2) {
            viewHolder.cycleTv.setText(model.getDes());
        }
        viewHolder.passingRateTv.setText(String.valueOf(model.getPassingRate()));
        viewHolder.tagTv.setText(model.getTag());
        viewHolder.productNameTv.setText(model.getProductName());
        ILFactory.getLoader().loadNet(viewHolder.productImg, Api.API_BASE_URL + model.getProductLogo(), new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        viewHolder.limitTv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
        viewHolder.clickView.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, model, 1, viewHolder);
        });
    }

}
