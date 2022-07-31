package com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.mmaeryrusu.qqzdryty.R;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.fragment.MineFragmentFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.fragment.FenQiHuanQianBeiProductFragment;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.StatusFenQiHuanQianBeiBarUtil;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.ToastUtilFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiadapter.MyFragmentFenQiHuanQianBeiAdapter;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeipresent.MainFenQiHuanQianBeiPresent;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.fragment.FenQiHuanQianBeiHomePageFragment;

public class HomePageFenQiHuanQianBeiActivity extends XActivity<MainFenQiHuanQianBeiPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "产品", "我的"};
    private int[] uncheckedIcon = {R.drawable.cbnmgyi, R.drawable.xfurtiui, R.drawable.jfderiurti};
    private int[] checkedIcon = {R.drawable.qqetxdrtyrtu, R.drawable.fghsretu,R.drawable.mcgyicfgtyi};
    private ArrayList<CustomTabEntity> customTabEntities;
    private MyFragmentFenQiHuanQianBeiAdapter myFragmentFenQiHuanQianBeiAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusFenQiHuanQianBeiBarUtil.setTransparent(this, false);
        StatusFenQiHuanQianBeiBarUtil.setLightMode(this);
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
        mFragments.add(new FenQiHuanQianBeiHomePageFragment());
        mFragments.add(new FenQiHuanQianBeiProductFragment());
        mFragments.add(new MineFragmentFenQiHuanQianBei());

        homeViewPager.setAdapter(new MyFragmentFenQiHuanQianBeiAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_fen_qi_huan_qian_bei_page;
    }

    @Override
    public MainFenQiHuanQianBeiPresent newP() {
        return new MainFenQiHuanQianBeiPresent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtilFenQiHuanQianBei.showShort("再按一次退出程序");
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
