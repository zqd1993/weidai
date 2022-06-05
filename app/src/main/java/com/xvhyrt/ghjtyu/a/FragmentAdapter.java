package com.xvhyrt.ghjtyu.a;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FragmentAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments;

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle, List<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        this.fragments = fragments;
    }

    public static String getTimeNoline(String src) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        Date date = null;
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
            date = format1.parse(src);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (date != null) {
                return format.format(date);
            } else {
                return "";
            }
        }
    }

    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    public static String getMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        return format.format(date);
    }

    public static String getDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        return format.format(date);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public static String getContrastContent(int year, int month) {
        String str = String.valueOf(month);
        if (month < 10) {
            str = "0" + str;
        }
        return year + "-" + str;
    }
}
