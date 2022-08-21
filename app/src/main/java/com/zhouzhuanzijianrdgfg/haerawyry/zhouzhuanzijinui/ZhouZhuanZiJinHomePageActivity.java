package com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinui;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.zhouzhuanzijianrdgfg.haerawyry.R;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinui.zhouzhuanzijinfragment.ZhouZhuanZiJinHomePageFragment;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinui.zhouzhuanzijinfragment.ZhouZhuanZiJinProductFragment;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinutils.StatusBarUtil;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinutils.ZhouZhuanZiJinToastUtil;
import com.zhouzhuanzijianrdgfg.haerawyry.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinadapter.ZhouZhuanZiJinMyFragmentAdapter;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinpresent.ZhouZhuanZiJinMainPresent;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinui.zhouzhuanzijinfragment.ZhouZhuanZiJinMineFragment;

public class ZhouZhuanZiJinHomePageActivity extends XActivity<ZhouZhuanZiJinMainPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "产品", "我的"};
    private int[] uncheckedIcon = {R.drawable.msrrstfgh, R.drawable.qqerhxf, R.drawable.ksrserdxfh};
    private int[] checkedIcon = {R.drawable.zseaweetrtr, R.drawable.lolpyfurts,R.drawable.ersrtrt};
    private ArrayList<CustomTabEntity> customTabEntities;
    private ZhouZhuanZiJinMyFragmentAdapter zhouZhuanZiJinMyFragmentAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        StatusBarUtil.setLightMode(this);
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
        mFragments.add(new ZhouZhuanZiJinHomePageFragment());
        mFragments.add(new ZhouZhuanZiJinProductFragment());
        mFragments.add(new ZhouZhuanZiJinMineFragment());

        homeViewPager.setAdapter(new ZhouZhuanZiJinMyFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    public void changPage(){
        tabLayout.setCurrentTab(1);
        homeViewPager.setCurrentItem(1, false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page_zhou_zhuan_zi_jin;
    }

    @Override
    public ZhouZhuanZiJinMainPresent newP() {
        return new ZhouZhuanZiJinMainPresent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ZhouZhuanZiJinToastUtil.showShort("再按一次退出程序");
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
