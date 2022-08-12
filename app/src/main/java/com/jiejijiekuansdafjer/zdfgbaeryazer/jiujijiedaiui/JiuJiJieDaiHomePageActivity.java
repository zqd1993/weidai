package com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiui;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.jiejijiekuansdafjer.zdfgbaeryazer.R;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiui.jiujijiedaifragment.JiuJiJieDaiHomePageFragment;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiui.jiujijiedaifragment.JiuJiJieDaiProductFragment;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiutils.JiuJiJieDaiStatusBarUtil;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiutils.JiuJiJieDaiToastUtil;
import com.jiejijiekuansdafjer.zdfgbaeryazer.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiadapter.JiuJiJieDaiMyFragmentAdapter;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaipresent.JiuJiJieDaiMainPresent;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiui.jiujijiedaifragment.JiuJiJieDaiMineFragment;

public class JiuJiJieDaiHomePageActivity extends XActivity<JiuJiJieDaiMainPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "产品", "我的"};
    private int[] uncheckedIcon = {R.drawable.qqwrewytj, R.drawable.reyesua, R.drawable.llfyuodtyu};
    private int[] checkedIcon = {R.drawable.mxseyry, R.drawable.mmtrusr,R.drawable.zzdfrtuy};
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
