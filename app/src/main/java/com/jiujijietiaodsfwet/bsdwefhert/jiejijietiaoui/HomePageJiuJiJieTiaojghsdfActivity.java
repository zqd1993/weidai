package com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoui;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.jiujijietiaodsfwet.bsdwefhert.R;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoui.jiejijietiaofragment.JiuJiJieTiaojghsdfMineFragment;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoui.jiejijietiaofragment.HomePageJiuJiJieTiaojghsdfFragment;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoui.jiejijietiaofragment.ProductJiuJiJieTiaojghsdfFragment;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoutils.JiuJiJieTiaojghsdfStatusBarUtil;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoutils.JiuJiJieTiaojghsdfToastUtil;
import com.jiujijietiaodsfwet.bsdwefhert.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoadapter.MyFragmentJiuJiJieTiaojghsdfAdapter;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaopresent.MainJiuJiJieTiaojghsdfPresent;

public class HomePageJiuJiJieTiaojghsdfActivity extends XActivity<MainJiuJiJieTiaojghsdfPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "产品", "我的"};
    private int[] uncheckedIcon = {R.drawable.nbnsertyuzdfch, R.drawable.trttsrhxfgj, R.drawable.tyusfghs};
    private int[] checkedIcon = {R.drawable.xcvbaeryueru, R.drawable.ereyxdfhftj,R.drawable.xcvberryreu};
    private ArrayList<CustomTabEntity> customTabEntities;
    private MyFragmentJiuJiJieTiaojghsdfAdapter myFragmentJiuJiJieTiaojghsdfAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        JiuJiJieTiaojghsdfStatusBarUtil.setTransparent(this, false);
//        JiuJiJieTiaojghsdfStatusBarUtil.setLightMode(this);
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
        mFragments.add(new HomePageJiuJiJieTiaojghsdfFragment());
        mFragments.add(new ProductJiuJiJieTiaojghsdfFragment());
        mFragments.add(new JiuJiJieTiaojghsdfMineFragment());

        homeViewPager.setAdapter(new MyFragmentJiuJiJieTiaojghsdfAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_jiu_ji_jie_tiao_boss_home_page;
    }

    @Override
    public MainJiuJiJieTiaojghsdfPresent newP() {
        return new MainJiuJiJieTiaojghsdfPresent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                JiuJiJieTiaojghsdfToastUtil.showShort("再按一次退出程序");
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
