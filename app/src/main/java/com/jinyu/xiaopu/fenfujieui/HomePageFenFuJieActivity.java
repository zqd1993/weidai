package com.jinyu.xiaopu.fenfujieui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.jinyu.xiaopu.R;
import com.jinyu.xiaopu.fenfujiemodel.BaseRespModelFenFuJie;
import com.jinyu.xiaopu.fenfujiemodel.FenFuJieConfigModel;
import com.jinyu.xiaopu.fenfujienet.ApiSubscriber;
import com.jinyu.xiaopu.fenfujienet.FenFuJieApi;
import com.jinyu.xiaopu.fenfujienet.NetError;
import com.jinyu.xiaopu.fenfujienet.XApi;
import com.jinyu.xiaopu.fenfujieui.fenfujiefragment.HomePageFragmentFenFuJie;
import com.jinyu.xiaopu.fenfujieui.fenfujiefragment.MineFenFuJieFragment;
import com.jinyu.xiaopu.fenfujieutils.SharedPreferencesUtilisFenFuJie;
import com.jinyu.xiaopu.fenfujieutils.FenFuJieStatusBarUtil;
import com.jinyu.xiaopu.fenfujieutils.ToastFenFuJieUtil;
import com.jinyu.xiaopu.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.jinyu.xiaopu.fenfujieadapter.MyFragmentFenFuJieAdapter;
import com.jinyu.xiaopu.fenfujiepresent.FenFuJieMainPresent;

public class HomePageFenFuJieActivity extends XActivity<FenFuJieMainPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "产品", "我的"};
    private int[] uncheckedIcon = {R.drawable.zdretyurftuj, R.drawable.szdseryufgu, R.drawable.kkjsrtysret};
    private int[] checkedIcon = {R.drawable.nfgghjsdrtu, R.drawable.zzdfyhrtu, R.drawable.xnxftthsrtu};
    private ArrayList<CustomTabEntity> customTabEntities;
    private MyFragmentFenFuJieAdapter myFragmentFenFuJieAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        FenFuJieStatusBarUtil.setTransparent(this, false);
        getP().login();
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
        mFragments.add(HomePageFragmentFenFuJie.getInstant(1));
        mFragments.add(HomePageFragmentFenFuJie.getInstant(2));
        mFragments.add(new MineFenFuJieFragment());

        homeViewPager.setAdapter(new MyFragmentFenFuJieAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page_fen_fu_jie;
    }

    @Override
    public FenFuJieMainPresent newP() {
        return new FenFuJieMainPresent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastFenFuJieUtil.showShort("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getValue();
    }

    public void getValue() {
        FenFuJieApi.getGankService().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelFenFuJie<FenFuJieConfigModel>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseRespModelFenFuJie<FenFuJieConfigModel> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                SharedPreferencesUtilisFenFuJie.saveBoolIntoPref("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (SharedPreferencesUtilisFenFuJie.getBoolFromPref("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }
}
