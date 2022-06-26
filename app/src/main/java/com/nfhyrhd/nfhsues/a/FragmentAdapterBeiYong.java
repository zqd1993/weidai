package com.nfhyrhd.nfhsues.a;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.math.BigDecimal;
import java.util.List;

public class FragmentAdapterBeiYong extends FragmentStateAdapter {

    private List<Fragment> fragments;

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

    public FragmentAdapterBeiYong(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle, List<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        this.fragments = fragments;
    }

    public static BigDecimal erwffsdc(double d) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 把丢进来的recyclerView 设置成横向滚动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager ngberg(RecyclerView Rv, Context context) {
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
    public static LinearLayoutManager bdgfgerf(RecyclerView Rv, Context context) {

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
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    public static BigDecimal bydfv(double d) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 把丢进来的recyclerView 设置成横向滚动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager qwesdcsd(RecyclerView Rv, Context context) {
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
    public static LinearLayoutManager bfdgwefd(RecyclerView Rv, Context context) {

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
    public int getItemCount() {
        return fragments.size();
    }
}
