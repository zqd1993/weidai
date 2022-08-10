package com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.mmaeryrusu.qqzdryty.R;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.fenqihuanqianbeifragment.HomePageFenQiHuanQianBeiFragment;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.fenqihuanqianbeifragment.FenQiHuanQianBeiProductFragment;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.StatusBarUtilFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.ToastUtilFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiadapter.MyFragmentFenQiHuanQianBeiAdapter;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeipresent.FenQiHuanQianBeiMainPresent;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.fenqihuanqianbeifragment.MineFenQiHuanQianBeiFragment;

public class HomePageActivityFenQiHuanQianBei extends XActivity<FenQiHuanQianBeiMainPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "产品", "我的"};
    private int[] uncheckedIcon = {R.drawable.kdtiosrtu, R.drawable.zxzzhdruuj, R.drawable.xdxfdghrtu};
    private int[] checkedIcon = {R.drawable.ksrtustu, R.drawable.fnsrusrtu,R.drawable.nnsrtuasru};
    private ArrayList<CustomTabEntity> customTabEntities;
    private MyFragmentFenQiHuanQianBeiAdapter myFragmentFenQiHuanQianBeiAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilFenQiHuanQianBei.setTransparent(this, false);
        StatusBarUtilFenQiHuanQianBei.setLightMode(this);
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
        mFragments.add(new HomePageFenQiHuanQianBeiFragment());
        mFragments.add(new FenQiHuanQianBeiProductFragment());
        mFragments.add(new MineFenQiHuanQianBeiFragment());

        homeViewPager.setAdapter(new MyFragmentFenQiHuanQianBeiAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fen_qi_huan_qian_home_page;
    }

    @Override
    public FenQiHuanQianBeiMainPresent newP() {
        return new FenQiHuanQianBeiMainPresent();
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
