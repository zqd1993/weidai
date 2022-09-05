package com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.ruyijiekuandfwetdg.nnrdtydfgsd.R;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr.fragmentyijiekuandfwetr.RuYiJieKuanAdgFsdfHomePageFragment;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr.fragmentyijiekuandfwetr.MineFragmentRuYiJieKuanAdgFsdf;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr.fragmentyijiekuandfwetr.RuYiJieKuanAdgFsdfProductFragment;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr.RuYiJieKuanAdgFsdfStatusBarUtil;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr.RuYiJieKuanAdgFsdfToastUtil;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.adapterruyijiekuandfwetr.MyFragmentRuYiJieKuanAdgFsdfAdapter;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.presentyijiekuandfwetr.MainPresentRuYiJieKuanAdgFsdf;

public class HomePageActivityRuYiJieKuanAdgFsdf extends XActivity<MainPresentRuYiJieKuanAdgFsdf> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "产品", "我的"};
    private int[] uncheckedIcon = {R.drawable.fvbae, R.drawable.lpyufjd, R.drawable.srfgns};
    private int[] checkedIcon = {R.drawable.dsryufg, R.drawable.rthjfgas,R.drawable.qwhxfgj};
    private ArrayList<CustomTabEntity> customTabEntities;
    private MyFragmentRuYiJieKuanAdgFsdfAdapter myFragmentRuYiJieKuanAdgFsdfAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        RuYiJieKuanAdgFsdfStatusBarUtil.setTransparent(this, false);
//        RuYiJieKuanAdgFsdfStatusBarUtil.setLightMode(this);
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
        mFragments.add(new RuYiJieKuanAdgFsdfHomePageFragment());
        mFragments.add(new RuYiJieKuanAdgFsdfProductFragment());
        mFragments.add(new MineFragmentRuYiJieKuanAdgFsdf());

        homeViewPager.setAdapter(new MyFragmentRuYiJieKuanAdgFsdfAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page_ru_yi_jie_kuan_dfs_wetg;
    }

    @Override
    public MainPresentRuYiJieKuanAdgFsdf newP() {
        return new MainPresentRuYiJieKuanAdgFsdf();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                RuYiJieKuanAdgFsdfToastUtil.showShort("再按一次退出程序");
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
