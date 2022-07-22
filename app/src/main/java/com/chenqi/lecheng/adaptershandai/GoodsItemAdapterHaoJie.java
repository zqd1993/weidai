package com.chenqi.lecheng.adaptershandai;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chenqi.lecheng.R;
import com.chenqi.lecheng.base.SimpleRecAdapter;
import com.chenqi.lecheng.imageloader.ILFactory;
import com.chenqi.lecheng.imageloader.ILoader;
import com.chenqi.lecheng.kit.KnifeKit;
import com.chenqi.lecheng.shadnaihttp.ApiHaoJie;
import com.chenqi.lecheng.shadnaimodel.GoodsWinAHaoJieModel;
import com.chenqi.lecheng.utilsshandai.SharedPreferencesHaoJieUtilis;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;

public class GoodsItemAdapterHaoJie extends SimpleRecAdapter<GoodsWinAHaoJieModel, GoodsItemAdapterHaoJie.ViewHolder> {


    /**
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    public static void setSnackbarColor(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void setSnackbarColor(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_goods_item_haojie;
    }

    /**
     * 向Snackbar中添加view
     * @param snackbar
     * @param layoutId
     * @param index 新加布局在Snackbar中的位置
     */
    public static void SnackbarAddView(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view,index,p);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    public GoodsItemAdapterHaoJie(Context context) {
        super(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rate_tv)
        TextView rateTv;
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
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    public static void utynfgh(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void ngfdhdfg(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     * @param snackbar
     * @param layoutId
     * @param index 新加布局在Snackbar中的位置
     */
    public static void qeefsdf(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view,index,p);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        GoodsWinAHaoJieModel model = data.get(i);
        viewHolder.rateTv.setText(String.valueOf(model.getPassingRate()));
        if (!TextUtils.isEmpty(SharedPreferencesHaoJieUtilis.getStringFromPref("API_BASE_URL"))) {
            ILFactory.getLoader().loadNet(viewHolder.productImg, SharedPreferencesHaoJieUtilis.getStringFromPref("API_BASE_URL") + model.getProductLogo(), new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        }
        if (!TextUtils.isEmpty(model.getDes()) && model.getDes().length() > 2) {
            viewHolder.cycleTv.setText("周期" + model.getDes());
        }
        viewHolder.limitTv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
        viewHolder.clickView.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, model, 1, viewHolder);
        });
        viewHolder.tagTv.setText(model.getTag());
        viewHolder.productNameTv.setText(model.getProductName());
    }

    /**
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    public static void vbdfg(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void wersdf(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     * @param snackbar
     * @param layoutId
     * @param index 新加布局在Snackbar中的位置
     */
    public static void dftgert(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view,index,p);
    }

}
