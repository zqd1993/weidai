package com.dlnsg.ytjwhbm.hwshanjiebeiyongyemian;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class FragmentHWShanJieBeiYongJinAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments;

    public FragmentHWShanJieBeiYongJinAdapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle, List<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        this.fragments = fragments;
    }

    /**
     * 手机号中间插入-
     *
     * @param phone
     * @return
     */
    public static String showPhoneNum(String phone) {
        if (!TextUtils.isEmpty(phone) && phone.length() > 6) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < phone.length(); i++) {
                char c = phone.charAt(i);
                if (i == 3 || i == 7) {
                    sb.append('-');
                }
                sb.append(c);

            }
            return sb.toString();
        } else {
            return phone;
        }
    }

    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    public static final String OTHER_BRAND = "other";

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
