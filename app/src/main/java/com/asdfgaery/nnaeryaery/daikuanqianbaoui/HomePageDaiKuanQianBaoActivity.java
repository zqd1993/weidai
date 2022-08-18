package com.asdfgaery.nnaeryaery.daikuanqianbaoui;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.asdfgaery.nnaeryaery.R;
import com.asdfgaery.nnaeryaery.daikuanqianbaoui.daikuanqianbaofragment.DaiKuanQianBaoMineFragment;
import com.asdfgaery.nnaeryaery.daikuanqianbaoui.daikuanqianbaofragment.HomePageDaiKuanQianBaoFragment;
import com.asdfgaery.nnaeryaery.daikuanqianbaoui.daikuanqianbaofragment.ProductDaiKuanQianBaoFragment;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.DaiKuanQianBaoStatusBarUtil;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.ToastUtilDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.asdfgaery.nnaeryaery.daikuanqianbaodapter.MyFragmentDaiKuanQianBaoAdapter;
import com.asdfgaery.nnaeryaery.daikuanqianbaopresent.MainDaiKuanQianBaoPresent;

public class HomePageDaiKuanQianBaoActivity extends XActivity<MainDaiKuanQianBaoPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "产品", "我的"};
    private int[] uncheckedIcon = {R.drawable.ehxeryy, R.drawable.dxfhezsy, R.drawable.ldyusrtuy};
    private int[] checkedIcon = {R.drawable.dgbchdrt, R.drawable.hhxcbhery,R.drawable.yreyfxgjh};
    private ArrayList<CustomTabEntity> customTabEntities;
    private MyFragmentDaiKuanQianBaoAdapter myFragmentDaiKuanQianBaoAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        DaiKuanQianBaoStatusBarUtil.setTransparent(this, false);
        DaiKuanQianBaoStatusBarUtil.setLightMode(this);
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
        mFragments.add(new HomePageDaiKuanQianBaoFragment());
        mFragments.add(new ProductDaiKuanQianBaoFragment());
        mFragments.add(new DaiKuanQianBaoMineFragment());

        homeViewPager.setAdapter(new MyFragmentDaiKuanQianBaoAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dai_kuan_qian_bao_home_page;
    }

    @Override
    public MainDaiKuanQianBaoPresent newP() {
        return new MainDaiKuanQianBaoPresent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtilDaiKuanQianBao.showShort("再按一次退出程序");
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
