package com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.queqianmasdfjiert.bdafgawetr.R;
import com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.fragmentqueqianmaboss.ProductQueQianMaBossFragment;
import com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.fragmentqueqianmaboss.QueQianMaBossMineFragment;
import com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.fragmentqueqianmaboss.QueQianMaBossHomePageFragment;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.StatusBarQueQianMaBossUtil;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.ToastQueQianMaBossUtil;
import com.queqianmasdfjiert.bdafgawetr.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.queqianmasdfjiert.bdafgawetr.adapterqueqianmaboss.MyFragmentAdapterQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.presentqueqianmaboss.MainQueQianMaBossPresent;

public class HomePageActivityQueQianMaBoss extends XActivity<MainQueQianMaBossPresent> {

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
    private MyFragmentAdapterQueQianMaBoss myFragmentAdapterQueQianMaBoss;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarQueQianMaBossUtil.setTransparent(this, false);
//        StatusBarQueQianMaBossUtil.setLightMode(this);
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
        mFragments.add(new QueQianMaBossHomePageFragment());
        mFragments.add(new ProductQueQianMaBossFragment());
        mFragments.add(new QueQianMaBossMineFragment());

        homeViewPager.setAdapter(new MyFragmentAdapterQueQianMaBoss(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page_que_qian_ma_boss;
    }

    @Override
    public MainQueQianMaBossPresent newP() {
        return new MainQueQianMaBossPresent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastQueQianMaBossUtil.showShort("再按一次退出程序");
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
