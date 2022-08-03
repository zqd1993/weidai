package com.sdyqwjqwias.fdpwejqwdjew.jiedianqianui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.sdyqwjqwias.fdpwejqwdjew.R;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.JieDianQianSharedPreferencesUtilis;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.StatusBarUtilJieDianQian;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.ToastUtilJieDianQian;
import com.sdyqwjqwias.fdpwejqwdjew.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianadapter.MyFragmentAdapterJieDianQian;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianpresent.MainPresentJieDianQian;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianui.jiedianqianfragment.HomePageJieDianQianFragment;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianui.jiedianqianfragment.MineFragmentJieDianQian;

public class HomePageJieDianQianActivity extends XActivity<MainPresentJieDianQian> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "我的"};
    private int[] uncheckedIcon = {R.drawable.footer_icon_n_sy, R.drawable.footer_icon_n_wd};
    private int[] checkedIcon = {R.drawable.footer_icon_f_sy, R.drawable.footer_icon_f_wd};
    private ArrayList<CustomTabEntity> customTabEntities;
    private MyFragmentAdapterJieDianQian myFragmentAdapterJieDianQian;

    @Override
    public void initData(Bundle savedInstanceState) {
        if (JieDianQianSharedPreferencesUtilis.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusBarUtilJieDianQian.setTransparent(this, false);
        StatusBarUtilJieDianQian.setLightMode(this);
        getP().login();
        customTabEntities = new ArrayList<>();
        homeViewPager.setUserInputEnabled(false);
        for (int i = 0; i < 2; i++) {
            int position = i;
            CustomTabEntity customTabEntity = new CustomTabEntity() {
                @Override
                public String getTabTitle() {
                    return mTitles[position];
                }

                @Override
                public int getTabSelectedIcon() {
                    return checkedIcon[position];
                }

                @Override
                public int getTabUnselectedIcon() {
                    return uncheckedIcon[position];
                }
            };
            customTabEntities.add(customTabEntity);
        }
        tabLayout.setTabData(customTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                homeViewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mFragments.add(HomePageJieDianQianFragment.getInstant(1));
        mFragments.add(new MineFragmentJieDianQian());

        homeViewPager.setAdapter(new MyFragmentAdapterJieDianQian(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page;
    }

    @Override
    public MainPresentJieDianQian newP() {
        return new MainPresentJieDianQian();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtilJieDianQian.showShort("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
