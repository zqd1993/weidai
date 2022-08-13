package com.chenqi.lecheng.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.chenqi.lecheng.R;
import com.chenqi.lecheng.model.BaseRespYouXinModel;
import com.chenqi.lecheng.model.ConfigYouXinModel;
import com.chenqi.lecheng.net.Api;
import com.chenqi.lecheng.net.ApiSubscriber;
import com.chenqi.lecheng.net.NetError;
import com.chenqi.lecheng.net.XApi;
import com.chenqi.lecheng.utils.SharedPreferencesYouXinUtilis;
import com.chenqi.lecheng.utils.StatusBarYouXinUtil;
import com.chenqi.lecheng.utils.ToastYouXinUtil;
import com.chenqi.lecheng.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.chenqi.lecheng.adapter.MyFragmentAdapterYouXin;
import com.chenqi.lecheng.present.MainYouXinPresent;
import com.chenqi.lecheng.ui.fragment.HomePageYouXinFragment;
import com.chenqi.lecheng.ui.fragment.MineFragment;

public class HomePageYouXinActivity extends XActivity<MainYouXinPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "个人中心","产品",};
    private int[] uncheckedIcon = {R.drawable.footer_icon_n_sy, R.drawable.footer_icon_n_cp, R.drawable.footer_icon_n_wd};
    private int[] checkedIcon = {R.drawable.footer_icon_f_sy, R.drawable.footer_icon_f_cp, R.drawable.footer_icon_f_wd};
    private ArrayList<CustomTabEntity> customTabEntities;
    private MyFragmentAdapterYouXin miaoJieMyFragmentAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarYouXinUtil.setTransparent(this, false);
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
        mFragments.add(HomePageYouXinFragment.getInstant(1));
        mFragments.add(new MineFragment());
        mFragments.add(HomePageYouXinFragment.getInstant(2));

        homeViewPager.setAdapter(new MyFragmentAdapterYouXin(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page;
    }

    public String getOrderCommodityNumbers() {
        return "";
    }

    @Override
    public MainYouXinPresent newP() {
        return new MainYouXinPresent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastYouXinUtil.showShort("再按一次退出程序");
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
        Api.getGankService().getValve("VIDEOTAPE")
                .compose(XApi.<BaseRespYouXinModel<ConfigYouXinModel>>getApiTransformer())
                .compose(XApi.<BaseRespYouXinModel<ConfigYouXinModel>>getScheduler())
                .subscribe(new ApiSubscriber<BaseRespYouXinModel<ConfigYouXinModel>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseRespYouXinModel<ConfigYouXinModel> gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getData() != null) {
                                SharedPreferencesYouXinUtilis.saveBoolIntoPref("NO_RECORD", !gankResults.getData().getVideoTape().equals("0"));
                                if (SharedPreferencesYouXinUtilis.getBoolFromPref("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }
}
