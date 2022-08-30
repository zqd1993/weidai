package com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.yingjijiefasdfbbdr.dfgeryxfg.R;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui.yjjefqjqfragment.MineYjjdFqjqFragment;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui.yjjefqjqfragment.YjjdFqjqHomePageFragment;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui.yjjefqjqfragment.YjjdFqjqProductFragment;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.YjjdFqjqStatusBarUtil;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.ToastYjjdFqjqUtil;
import com.yingjijiefasdfbbdr.dfgeryxfg.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqadapter.YjjdFqjqMyFragmentAdapter;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqpresent.MainYjjdFqjqPresent;

public class HomePageActivityYjjdFqjq extends XActivity<MainYjjdFqjqPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "产品", "我的"};
    private int[] uncheckedIcon = {R.drawable.xxcberysrtu, R.drawable.eeryxchjn, R.drawable.qqtgdzfh};
    private int[] checkedIcon = {R.drawable.xdnrsu, R.drawable.lklyfixfgn,R.drawable.zedfgn};
    private ArrayList<CustomTabEntity> customTabEntities;
    private YjjdFqjqMyFragmentAdapter yjjdFqjqMyFragmentAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        YjjdFqjqStatusBarUtil.setTransparent(this, false);
        YjjdFqjqStatusBarUtil.setLightMode(this);
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
        mFragments.add(new YjjdFqjqHomePageFragment());
        mFragments.add(new YjjdFqjqProductFragment());
        mFragments.add(new MineYjjdFqjqFragment());

        homeViewPager.setAdapter(new YjjdFqjqMyFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_yjjdfqjq_home_page;
    }

    @Override
    public MainYjjdFqjqPresent newP() {
        return new MainYjjdFqjqPresent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastYjjdFqjqUtil.showShort("再按一次退出程序");
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
