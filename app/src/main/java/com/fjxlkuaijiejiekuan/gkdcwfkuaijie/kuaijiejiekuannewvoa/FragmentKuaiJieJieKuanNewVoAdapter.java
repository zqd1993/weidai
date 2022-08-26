package com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoa;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class FragmentKuaiJieJieKuanNewVoAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments;

    public FragmentKuaiJieJieKuanNewVoAdapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle, List<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        this.fragments = fragments;
    }

    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
