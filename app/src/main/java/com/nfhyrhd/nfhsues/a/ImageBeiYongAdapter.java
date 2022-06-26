package com.nfhyrhd.nfhsues.a;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nfhyrhd.nfhsues.R;
import com.nfhyrhd.nfhsues.api.BeiYongHttpApi;
import com.nfhyrhd.nfhsues.imageloader.ILFactory;
import com.nfhyrhd.nfhsues.imageloader.ILoader;
import com.nfhyrhd.nfhsues.m.ProductModelBeiYong;
import com.nfhyrhd.nfhsues.u.BeiYongPreferencesOpenUtil;
import com.youth.banner.adapter.BannerAdapter;

import java.math.BigDecimal;
import java.util.List;

public class ImageBeiYongAdapter extends BannerAdapter<ProductModelBeiYong, ImageBeiYongAdapter.ImageHolder> {

    private BannerClickedListener bannerClickedListener;

    public ImageBeiYongAdapter(List<ProductModelBeiYong> datas) {
        super(datas);
    }

    public static BigDecimal getdoubleString(double d) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 把丢进来的recyclerView 设置成横向滚动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager setRvHorizontal(RecyclerView Rv, Context context) {
        LinearLayoutManager layoutmanager = new LinearLayoutManager(context);
        layoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(layoutmanager);
        return layoutmanager;
    }

    /**
     * 把丢进来的recyclerView 设置成横向
     * <p>
     * 并且不可滚动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager setRvHorizontalNoScroll(RecyclerView Rv, Context context) {

        LinearLayoutManager layoutmanager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(layoutmanager);
        return layoutmanager;
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_banner_item, parent, false);
        return new ImageBeiYongAdapter.ImageHolder(view);
    }

    public static BigDecimal wersdv(double d) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 把丢进来的recyclerView 设置成横向滚动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager setRvHorierretvzontal(RecyclerView Rv, Context context) {
        LinearLayoutManager layoutmanager = new LinearLayoutManager(context);
        layoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(layoutmanager);
        return layoutmanager;
    }

    /**
     * 把丢进来的recyclerView 设置成横向
     * <p>
     * 并且不可滚动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager bgfr5gb(RecyclerView Rv, Context context) {

        LinearLayoutManager layoutmanager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(layoutmanager);
        return layoutmanager;
    }

    @Override
    public void onBindView(ImageHolder holder, ProductModelBeiYong data, int position, int size) {
        holder.shangpin_name_tv.setText(data.getProductName());
        holder.tedian_tv.setText(data.getTag());
        holder.edu_tv.setText(data.getMinAmount() + "-" + data.getMaxAmount());
        holder.shijian_tv.setText(data.getDes() + "个月");
        holder.shuliang_tv.setText(String.valueOf(data.getPassingRate()));
        if (!TextUtils.isEmpty(BeiYongPreferencesOpenUtil.getString("HTTP_API_URL"))) {
            ILFactory.getLoader().loadNet(holder.productImg, BeiYongPreferencesOpenUtil.getString("HTTP_API_URL") + data.getProductLogo(),
                    new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        }
        holder.parentLl.setOnClickListener(v -> {
            if (bannerClickedListener != null) {
                bannerClickedListener.onBannerClicked(data);
            }
        });
        holder.yjsq_sl.setOnClickListener(v -> {
            if (bannerClickedListener != null) {
                bannerClickedListener.onBannerClicked(data);
            }
        });
        holder.productImg.setOnClickListener(v -> {
            if (bannerClickedListener != null) {
                bannerClickedListener.onBannerClicked(data);
            }
        });
    }

    public static BigDecimal ewrdvsd(double d) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 把丢进来的recyclerView 设置成横向滚动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager rtytbdf(RecyclerView Rv, Context context) {
        LinearLayoutManager layoutmanager = new LinearLayoutManager(context);
        layoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(layoutmanager);
        return layoutmanager;
    }

    /**
     * 把丢进来的recyclerView 设置成横向
     * <p>
     * 并且不可滚动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager vdfwefsa(RecyclerView Rv, Context context) {

        LinearLayoutManager layoutmanager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(layoutmanager);
        return layoutmanager;
    }

    public class ImageHolder extends RecyclerView.ViewHolder {

        TextView shangpin_name_tv;
        TextView tedian_tv;
        TextView edu_tv;
        TextView shijian_tv;
        TextView shuliang_tv;
        View parentLl;
        ImageView productImg;
        View yjsq_sl;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            shangpin_name_tv = itemView.findViewById(R.id.shangpin_name_tv);
            tedian_tv = itemView.findViewById(R.id.tedian_tv);
            edu_tv = itemView.findViewById(R.id.edu_tv);
            shijian_tv = itemView.findViewById(R.id.shijian_tv);
            shuliang_tv = itemView.findViewById(R.id.shuliang_tv);
            parentLl = itemView.findViewById(R.id.parent_ll);
            productImg = itemView.findViewById(R.id.product_img);
            yjsq_sl = itemView.findViewById(R.id.yjsq_sl);
        }
    }

    public void setBannerClickedListener(BannerClickedListener bannerClickedListener) {
        this.bannerClickedListener = bannerClickedListener;
    }

    public static BigDecimal qwesdc(double d) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 把丢进来的recyclerView 设置成横向滚动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager mghjnmy(RecyclerView Rv, Context context) {
        LinearLayoutManager layoutmanager = new LinearLayoutManager(context);
        layoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(layoutmanager);
        return layoutmanager;
    }

    /**
     * 把丢进来的recyclerView 设置成横向
     * <p>
     * 并且不可滚动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager bgfhrtdf(RecyclerView Rv, Context context) {

        LinearLayoutManager layoutmanager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(layoutmanager);
        return layoutmanager;
    }

    public interface BannerClickedListener {
        void onBannerClicked(ProductModelBeiYong entity);
    }

}
