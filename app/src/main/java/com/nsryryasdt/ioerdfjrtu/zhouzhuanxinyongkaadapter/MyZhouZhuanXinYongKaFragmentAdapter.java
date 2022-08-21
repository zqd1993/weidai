package com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaadapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class MyZhouZhuanXinYongKaFragmentAdapter extends FragmentStateAdapter {

    private List<Fragment> mFragmentList;

    public MyZhouZhuanXinYongKaFragmentAdapter(@NonNull FragmentManager fragmentManager,
                                               @NonNull Lifecycle lifecycle, List<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        mFragmentList = fragments;
    }

    @Override
    public Fragment createFragment(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }
}
