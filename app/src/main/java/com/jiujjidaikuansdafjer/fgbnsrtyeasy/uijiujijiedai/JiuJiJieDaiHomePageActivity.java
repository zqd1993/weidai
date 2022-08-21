package com.jiujjidaikuansdafjer.fgbnsrtyeasy.uijiujijiedai;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.jiujjidaikuansdafjer.fgbnsrtyeasy.R;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.uijiujijiedai.fragmentjiujijiedai.JiuJiJieDaiHomePageFragment;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.uijiujijiedai.fragmentjiujijiedai.JiuJiJieDaiProductFragment;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.utilsjiujijiedai.JiuJiJieDaiStatusBarUtil;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.utilsjiujijiedai.JiuJiJieDaiToastUtil;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.adapterjiujijiedai.JiuJiJieDaiMyFragmentAdapter;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.presentjiujijiedai.JiuJiJieDaiMainPresent;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.uijiujijiedai.fragmentjiujijiedai.JiuJiJieDaiMineFragment;

public class JiuJiJieDaiHomePageActivity extends XActivity<JiuJiJieDaiMainPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "产品", "我的"};
    private int[] uncheckedIcon = {R.drawable.cxcbseuyi, R.drawable.llfyupdrg, R.drawable.qqwredzf};
    private int[] checkedIcon = {R.drawable.cfgjsrxfvb, R.drawable.qqrxfghjsr,R.drawable.hhxchsrt};
    private ArrayList<CustomTabEntity> customTabEntities;
    private JiuJiJieDaiMyFragmentAdapter jiuJiJieDaiMyFragmentAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        JiuJiJieDaiStatusBarUtil.setTransparent(this, false);
        JiuJiJieDaiStatusBarUtil.setLightMode(this);
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
        mFragments.add(new JiuJiJieDaiHomePageFragment());
        mFragments.add(new JiuJiJieDaiProductFragment());
        mFragments.add(new JiuJiJieDaiMineFragment());

        homeViewPager.setAdapter(new JiuJiJieDaiMyFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    public void changPage(){
        tabLayout.setCurrentTab(1);
        homeViewPager.setCurrentItem(1, false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page_jiu_ji_jie_dai;
    }

    @Override
    public JiuJiJieDaiMainPresent newP() {
        return new JiuJiJieDaiMainPresent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                JiuJiJieDaiToastUtil.showShort("再按一次退出程序");
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
