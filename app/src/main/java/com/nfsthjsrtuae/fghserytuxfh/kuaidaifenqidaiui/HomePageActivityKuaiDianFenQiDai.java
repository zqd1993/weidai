package com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui.fragment.HomePageFragmentKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui.fragment.ProductKuaiDianFenQiDaiFragment;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.StatusBarUtilKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.ToastUtilKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiadapter.MyKuaiDianFenQiDaiFragmentAdapter;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaipresent.KuaiDianFenQiDaiMainPresent;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui.fragment.KuaiDianFenQiDaiMineFragment;

public class HomePageActivityKuaiDianFenQiDai extends XActivity<KuaiDianFenQiDaiMainPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "产品", "我的"};
    private int[] uncheckedIcon = {R.drawable.ccyrcty, R.drawable.bbtctr, R.drawable.rersxrt};
    private int[] checkedIcon = {R.drawable.hsryfdyh, R.drawable.mnbyutyf,R.drawable.vvuyfuy};
    private ArrayList<CustomTabEntity> customTabEntities;
    private MyKuaiDianFenQiDaiFragmentAdapter myKuaiDianFenQiDaiFragmentAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilKuaiDianFenQiDai.setTransparent(this, false);
        StatusBarUtilKuaiDianFenQiDai.setLightMode(this);
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
        mFragments.add(new HomePageFragmentKuaiDianFenQiDai());
        mFragments.add(new ProductKuaiDianFenQiDaiFragment());
        mFragments.add(new KuaiDianFenQiDaiMineFragment());

        homeViewPager.setAdapter(new MyKuaiDianFenQiDaiFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_kuai_dian_fen_qi_dai_home_page;
    }

    @Override
    public KuaiDianFenQiDaiMainPresent newP() {
        return new KuaiDianFenQiDaiMainPresent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtilKuaiDianFenQiDai.showShort("再按一次退出程序");
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
