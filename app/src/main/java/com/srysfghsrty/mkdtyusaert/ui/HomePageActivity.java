package com.srysfghsrty.mkdtyusaert.ui;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.srysfghsrty.mkdtyusaert.R;
import com.srysfghsrty.mkdtyusaert.utils.StatusBarUtil;
import com.srysfghsrty.mkdtyusaert.utils.ToastUtil;
import com.srysfghsrty.mkdtyusaert.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.srysfghsrty.mkdtyusaert.adapter.MyFragmentAdapter;
import com.srysfghsrty.mkdtyusaert.present.MainPresent;
import com.srysfghsrty.mkdtyusaert.ui.fragment.HomePageFragment;
import com.srysfghsrty.mkdtyusaert.ui.fragment.MineFragment;

public class HomePageActivity extends XActivity<MainPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "我的"};
    private int[] uncheckedIcon = {R.drawable.wertdthdrtu, R.drawable.ertrhftu};
    private int[] checkedIcon = {R.drawable.lyuosrtyfyh, R.drawable.zgbzdryrtsy};
    private ArrayList<CustomTabEntity> customTabEntities;
    private MyFragmentAdapter myFragmentAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        StatusBarUtil.setLightMode(this);
//        getP().login();
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
        mFragments.add(new HomePageFragment());
//        mFragments.add(new ProductFragment());
        mFragments.add(new MineFragment());

        homeViewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page;
    }

    @Override
    public MainPresent newP() {
        return new MainPresent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showShort("再按一次退出程序");
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
