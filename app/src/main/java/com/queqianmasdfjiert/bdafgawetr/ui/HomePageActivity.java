package com.queqianmasdfjiert.bdafgawetr.ui;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.queqianmasdfjiert.bdafgawetr.R;
import com.queqianmasdfjiert.bdafgawetr.ui.jiejijietiaofragment.MineFragment;
import com.queqianmasdfjiert.bdafgawetr.ui.jiejijietiaofragment.HomePageFragment;
import com.queqianmasdfjiert.bdafgawetr.ui.jiejijietiaofragment.ProductFragment;
import com.queqianmasdfjiert.bdafgawetr.utils.StatusBarUtil;
import com.queqianmasdfjiert.bdafgawetr.utils.ToastUtil;
import com.queqianmasdfjiert.bdafgawetr.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.queqianmasdfjiert.bdafgawetr.adapter.MyFragmentAdapter;
import com.queqianmasdfjiert.bdafgawetr.present.MainPresent;

public class HomePageActivity extends XActivity<MainPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "产品", "我的"};
    private int[] uncheckedIcon = {R.drawable.xcvbawy, R.drawable.xfgjsrrey, R.drawable.mnxcvnsftsa};
    private int[] checkedIcon = {R.drawable.cglyupsrth, R.drawable.xvbnaeydftui,R.drawable.zdutcyi};
    private ArrayList<CustomTabEntity> customTabEntities;
    private MyFragmentAdapter myFragmentAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        StatusBarUtil.setLightMode(this);
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
        mFragments.add(new HomePageFragment());
        mFragments.add(new ProductFragment());
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
