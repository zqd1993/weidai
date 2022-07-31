package com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanui;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.dixidaikuanwerwt.ioerdfjrtu.R;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanui.fragment.DiXiDaiKuanMineFragment;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanui.fragment.HomePageDiXiDaiKuanFragment;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanui.fragment.ProductDiXiDaiKuanFragment;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanutils.DiXiDaiKuanStatusBarUtil;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanutils.DiXiDaiKuanToastUtil;
import com.dixidaikuanwerwt.ioerdfjrtu.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanadapter.MyFragmentDiXiDaiKuanAdapter;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanpresent.MainDiXiDaiKuanPresent;

public class HomePageDiXiDaiKuanActivity extends XActivity<MainDiXiDaiKuanPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "产品", "我的"};
    private int[] uncheckedIcon = {R.drawable.ccyrcty, R.drawable.bbtctr, R.drawable.rersxrt};
    private int[] checkedIcon = {R.drawable.llfyuofy, R.drawable.qqegzdryry,R.drawable.zzbrtusrtu};
    private ArrayList<CustomTabEntity> customTabEntities;
    private MyFragmentDiXiDaiKuanAdapter myFragmentDiXiDaiKuanAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        DiXiDaiKuanStatusBarUtil.setTransparent(this, false);
        DiXiDaiKuanStatusBarUtil.setLightMode(this);
//        getP().login();
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
        mFragments.add(new HomePageDiXiDaiKuanFragment());
        mFragments.add(new ProductDiXiDaiKuanFragment());
        mFragments.add(new DiXiDaiKuanMineFragment());

        homeViewPager.setAdapter(new MyFragmentDiXiDaiKuanAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_di_xi_dai_kuan_home_page;
    }

    @Override
    public MainDiXiDaiKuanPresent newP() {
        return new MainDiXiDaiKuanPresent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                DiXiDaiKuanToastUtil.showShort("再按一次退出程序");
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
