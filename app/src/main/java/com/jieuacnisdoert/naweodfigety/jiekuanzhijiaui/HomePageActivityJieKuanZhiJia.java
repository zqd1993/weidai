package com.jieuacnisdoert.naweodfigety.jiekuanzhijiaui;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.jieuacnisdoert.naweodfigety.R;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiaui.jiekuanzhijiafragment.JieKuanZhiJiaHomePageFragment;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiaui.jiekuanzhijiafragment.MineFragmentJieKuanZhiJia;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiaui.jiekuanzhijiafragment.JieKuanZhiJiaProductFragment;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiautils.JieKuanZhiJiaStatusBarUtil;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiautils.JieKuanZhiJiaToastUtil;
import com.jieuacnisdoert.naweodfigety.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiaadapter.MyFragmentJieKuanZhiJiaAdapter;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiapresent.MainPresentJieKuanZhiJia;

public class HomePageActivityJieKuanZhiJia extends XActivity<MainPresentJieKuanZhiJia> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "产品", "我的"};
    private int[] uncheckedIcon = {R.drawable.xccvbntu, R.drawable.mxfgudrtu, R.drawable.lfyuofdt};
    private int[] checkedIcon = {R.drawable.mxfgjdrstui, R.drawable.qqerryu,R.drawable.xcvbnrtftui};
    private ArrayList<CustomTabEntity> customTabEntities;
    private MyFragmentJieKuanZhiJiaAdapter myFragmentJieKuanZhiJiaAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        JieKuanZhiJiaStatusBarUtil.setTransparent(this, false);
        JieKuanZhiJiaStatusBarUtil.setLightMode(this);
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
        mFragments.add(new JieKuanZhiJiaHomePageFragment());
        mFragments.add(new JieKuanZhiJiaProductFragment());
        mFragments.add(new MineFragmentJieKuanZhiJia());

        homeViewPager.setAdapter(new MyFragmentJieKuanZhiJiaAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page_jie_kuan_zhi_jia;
    }

    @Override
    public MainPresentJieKuanZhiJia newP() {
        return new MainPresentJieKuanZhiJia();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                JieKuanZhiJiaToastUtil.showShort("再按一次退出程序");
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
