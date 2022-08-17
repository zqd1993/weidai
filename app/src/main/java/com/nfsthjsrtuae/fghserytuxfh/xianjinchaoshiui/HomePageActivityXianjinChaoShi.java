package com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui.fragment.HomePageFragmentXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui.fragment.ProductXianjinChaoShiFragment;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.StatusBarUtilXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.ToastUtilXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiadapter.MXianjinChaoShiFragmentAdapter;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshipresent.XianjinChaoShiMainPresent;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui.fragment.XianjinChaoShiMineFragment;

public class HomePageActivityXianjinChaoShi extends XActivity<XianjinChaoShiMainPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "产品", "我的"};
    private int[] uncheckedIcon = {R.drawable.cvndrtusru, R.drawable.ssrtysru, R.drawable.xfheruyaseru};
    private int[] checkedIcon = {R.drawable.xxbnrstu, R.drawable.zdhgeruy,R.drawable.ewtrsey};
    private ArrayList<CustomTabEntity> customTabEntities;
    private MXianjinChaoShiFragmentAdapter MXianjinChaoShiFragmentAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilXianjinChaoShi.setTransparent(this, false);
//        StatusBarUtilKuaiDianFenQiDai.setLightMode(this);
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
        mFragments.add(new HomePageFragmentXianjinChaoShi());
        mFragments.add(new ProductXianjinChaoShiFragment());
        mFragments.add(new XianjinChaoShiMineFragment());

        homeViewPager.setAdapter(new MXianjinChaoShiFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_xian_jin_chao_shihome_page;
    }

    @Override
    public XianjinChaoShiMainPresent newP() {
        return new XianjinChaoShiMainPresent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtilXianjinChaoShi.showShort("再按一次退出程序");
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
