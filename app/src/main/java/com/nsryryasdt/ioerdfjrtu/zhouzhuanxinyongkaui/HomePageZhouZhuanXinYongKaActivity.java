package com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.nsryryasdt.ioerdfjrtu.R;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.ToastZhouZhuanXinYongKaUtil;
import com.nsryryasdt.ioerdfjrtu.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaadapter.MyZhouZhuanXinYongKaFragmentAdapter;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkapresent.MainZhouZhuanXinYongKaPresent;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui.zhouzhuanxinyongkafragment.HomePageFragment;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui.zhouzhuanxinyongkafragment.MineFragment;

public class HomePageZhouZhuanXinYongKaActivity extends XActivity<MainZhouZhuanXinYongKaPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "我的"};
    private int[] uncheckedIcon = {R.drawable.lpyfidt, R.drawable.xzvhrtu};
    private int[] checkedIcon = {R.drawable.lpyidtu, R.drawable.eeryxfgj};
    private ArrayList<CustomTabEntity> customTabEntities;
    private MyZhouZhuanXinYongKaFragmentAdapter myZhouZhuanXinYongKaFragmentAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
//        StatusBarUtilZhouZhuanXinYongKa.setTransparent(this, false);
//        StatusBarUtilZhouZhuanXinYongKa.setLightMode(this);
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
        mFragments.add(new HomePageFragment());
//        mFragments.add(new ProductFragment());
        mFragments.add(new MineFragment());

        homeViewPager.setAdapter(new MyZhouZhuanXinYongKaFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    public void changePage(){
        tabLayout.setCurrentTab(1);
        homeViewPager.setCurrentItem(1, false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhou_zhuan_xin_yong_ka_home_page;
    }

    @Override
    public MainZhouZhuanXinYongKaPresent newP() {
        return new MainZhouZhuanXinYongKaPresent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastZhouZhuanXinYongKaUtil.showShort("再按一次退出程序");
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
