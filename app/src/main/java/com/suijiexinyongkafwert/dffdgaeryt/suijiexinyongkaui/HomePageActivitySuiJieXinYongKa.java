package com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.suijiexinyongkafwert.dffdgaeryt.R;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui.suijiexinyongkafragment.HomePageFragmentSuiJieXinYongKa;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils.StatusBarUtilSuiJieXinYongKa;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils.ToastSuiJieXinYongKaUtil;
import com.suijiexinyongkafwert.dffdgaeryt.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaadapter.MySuiJieXinYongKaFragmentAdapter;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkapresent.MainSuiJieXinYongKaPresent;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui.suijiexinyongkafragment.MineSuiJieXinYongKaFragment;

public class HomePageActivitySuiJieXinYongKa extends XActivity<MainSuiJieXinYongKaPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "我的"};
    private int[] uncheckedIcon = {R.drawable.bgmsrtfb, R.drawable.qegsrtufgj};
    private int[] checkedIcon = {R.drawable.xxvnsrta, R.drawable.erhxfjsut};
    private ArrayList<CustomTabEntity> customTabEntities;
    private MySuiJieXinYongKaFragmentAdapter mySuiJieXinYongKaFragmentAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilSuiJieXinYongKa.setTransparent(this, false);
        StatusBarUtilSuiJieXinYongKa.setLightMode(this);
//        getP().login();
        customTabEntities = new ArrayList<>();
        homeViewPager.setUserInputEnabled(false);
        for (int i = 0; i < 2; i++) {
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
        mFragments.add(new HomePageFragmentSuiJieXinYongKa());
//        mFragments.add(new ProductFragment());
        mFragments.add(new MineSuiJieXinYongKaFragment());

        homeViewPager.setAdapter(new MySuiJieXinYongKaFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sui_jie_xin_yong_ka_home_page;
    }

    @Override
    public MainSuiJieXinYongKaPresent newP() {
        return new MainSuiJieXinYongKaPresent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastSuiJieXinYongKaUtil.showShort("再按一次退出程序");
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
