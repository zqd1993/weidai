package com.jwcf.zdclm.ui;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.jwcf.zdclm.R;
import com.jwcf.zdclm.ui.fragment.MineYingJiFragment;
import com.jwcf.zdclm.utils.StatusBarUtilYingJi;
import com.jwcf.zdclm.utils.ToastYingJiUtil;
import com.jwcf.zdclm.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.jwcf.zdclm.ada.YingJiMyFragmentAdapter;
import com.jwcf.zdclm.pre.MainYingJiPresent;
import com.jwcf.zdclm.ui.fragment.HomePageFragmentYingJi;

public class HomePageActivityYingJi extends XActivity<MainYingJiPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "产品", "我的"};
    private int[] uncheckedIcon = {R.drawable.zhuye_unselected, R.drawable.goods_unselected, R.drawable.user_unselected};
    private int[] checkedIcon = {R.drawable.zhuye_selected, R.drawable.goods_selected, R.drawable.user_selected};
    private ArrayList<CustomTabEntity> customTabEntities;
    private YingJiMyFragmentAdapter myFragmentAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilYingJi.setTransparent(this, false);
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
        mFragments.add(HomePageFragmentYingJi.getInstant(1));
        mFragments.add(HomePageFragmentYingJi.getInstant(2));
        mFragments.add(new MineYingJiFragment());

        homeViewPager.setAdapter(new YingJiMyFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page_yingji;
    }

    @Override
    public MainYingJiPresent newP() {
        return new MainYingJiPresent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastYingJiUtil.showShort("再按一次退出程序");
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
