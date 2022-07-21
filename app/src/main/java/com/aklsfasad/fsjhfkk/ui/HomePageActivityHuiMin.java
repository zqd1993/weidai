package com.aklsfasad.fsjhfkk.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.aklsfasad.fsjhfkk.R;
import com.aklsfasad.fsjhfkk.utils.SharedPreferencesUtilisHuiMin;
import com.aklsfasad.fsjhfkk.utils.StatusBarUtilHuiMin;
import com.aklsfasad.fsjhfkk.utils.ToastUtilHuiMin;
import com.aklsfasad.fsjhfkk.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.aklsfasad.fsjhfkk.adapter.MyFragmentHuiMinAdapter;
import com.aklsfasad.fsjhfkk.present.MainPresentHuiMin;
import com.aklsfasad.fsjhfkk.ui.fragment.HomePageFragmentHuiMin;
import com.aklsfasad.fsjhfkk.ui.fragment.MineHuiMinFragment;

public class HomePageActivityHuiMin extends XActivity<MainPresentHuiMin> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "设置", "产品"};
    private int[] uncheckedIcon = {R.drawable.footer_icon_n_sy, R.drawable.footer_icon_n_wd, R.drawable.footer_icon_n_cp};
    private int[] checkedIcon = {R.drawable.footer_icon_f_sy, R.drawable.footer_icon_f_wd, R.drawable.footer_icon_f_cp};
    private ArrayList<CustomTabEntity> customTabEntities;
    private MyFragmentHuiMinAdapter miaoJieMyFragmentAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilHuiMin.setTransparent(this, false);
        if (SharedPreferencesUtilisHuiMin.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        getP().login();
        customTabEntities = new ArrayList<>();
        homeViewPager.setUserInputEnabled(false);
        for (int i = 0; i < 3; i++) {
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
        mFragments.add(HomePageFragmentHuiMin.getInstant(1));
        mFragments.add(new MineHuiMinFragment());
        mFragments.add(HomePageFragmentHuiMin.getInstant(2));

        homeViewPager.setAdapter(new MyFragmentHuiMinAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtilHuiMin.showShort("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public float density;

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page;
    }

    public String getOrderCommodityNumbers() {
        return "";
    }

    @Override
    public MainPresentHuiMin newP() {
        return new MainPresentHuiMin();
    }
}
