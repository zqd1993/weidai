package com.nfhyrhd.nfhsues.a;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nfhyrhd.nfhsues.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;
import java.util.List;

public class TabAdapterBeiYong extends BaseQuickAdapter<MainActivityBeiYong.TabModel, BaseViewHolder> {

    private ClickedListener clickedListener;

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

    public TabAdapterBeiYong(int layoutResId, @Nullable List<MainActivityBeiYong.TabModel> data) {
        super(layoutResId, data);
    }

    public static BigDecimal nghjtyh(double d) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 把丢进来的recyclerView 设置成横向滚动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager bfgbrdfg(RecyclerView Rv, Context context) {
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
    public static LinearLayoutManager bgfhtb(RecyclerView Rv, Context context) {

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
    protected void convert(@NonNull BaseViewHolder helper, MainActivityBeiYong.TabModel item) {
        ImageView tabImg = helper.getView(R.id.tab_img);
        TextView tabName = helper.getView(R.id.tab_name);
        tabName.setText(item.getName());
        if (item.isChecked()){
            Glide.with(mContext).load(item.getSelectedIcon()).into(tabImg);
            tabName.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            Glide.with(mContext).load(item.getIcon()).into(tabImg);
            tabName.setTextColor(mContext.getResources().getColor(R.color.color_bdbdc5));
        }
        helper.getView(R.id.click_view).setOnClickListener(v -> {
            if (!item.isChecked()){
                item.setChecked(true);
                for (int i = 0; i < getData().size(); i++){
                    if (i != helper.getLayoutPosition()){
                        getData().get(i).setChecked(false);
                    }
                }
                if (clickedListener != null){
                    clickedListener.onClick(helper.getLayoutPosition());
                }
                notifyDataSetChanged();
            }
        });
    }

    public static BigDecimal nhgjyu(double d) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 把丢进来的recyclerView 设置成横向滚动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager erdsvfdg(RecyclerView Rv, Context context) {
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
    public static LinearLayoutManager gfnbfytt(RecyclerView Rv, Context context) {

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

    public void setClickedListener(ClickedListener clickedListener){
        this.clickedListener = clickedListener;
    }

    public interface ClickedListener{
        void onClick(int position);
    }

    public static BigDecimal werdvsd(double d) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 把丢进来的recyclerView 设置成横向滚动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager yuytngf(RecyclerView Rv, Context context) {
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
    public static LinearLayoutManager vcdrefsd(RecyclerView Rv, Context context) {

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
}
