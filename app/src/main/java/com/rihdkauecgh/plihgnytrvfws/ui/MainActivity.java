package com.rihdkauecgh.plihgnytrvfws.ui;

import android.os.Bundle;

import com.rihdkauecgh.plihgnytrvfws.R;
import com.rihdkauecgh.plihgnytrvfws.base.XFragmentAdapter;
import com.rihdkauecgh.plihgnytrvfws.model.BaseRespModel;
import com.rihdkauecgh.plihgnytrvfws.model.ConfigModel;
import com.rihdkauecgh.plihgnytrvfws.net.Api;
import com.rihdkauecgh.plihgnytrvfws.net.ApiSubscriber;
import com.rihdkauecgh.plihgnytrvfws.net.NetError;
import com.rihdkauecgh.plihgnytrvfws.net.XApi;
import com.rihdkauecgh.plihgnytrvfws.router.Router;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.rihdkauecgh.plihgnytrvfws.utils.SharedPreferencesUtilis;
import com.rihdkauecgh.plihgnytrvfws.mvp.XActivity;

/**
 * Created by wanglei on 2016/12/22.
 */

public class MainActivity extends XActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    List<Fragment> fragmentList = new ArrayList<>();
    String[] titles = {"首页", "干货", "妹子"};

    XFragmentAdapter adapter;

    private String phone;


    @Override
    public void initData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        phone = SharedPreferencesUtilis.getStringFromPref("phone");
        if (TextUtils.isEmpty(phone)) {
            Router.newIntent(this)
                    .to(LoginActivity.class)
                    .launch();
            finish();
        }
        fragmentList.clear();
        fragmentList.add(HomeFragment.newInstance());
        fragmentList.add(GanhuoFragment.newInstance());
        fragmentList.add(GirlFragment.newInstance());

        if (adapter == null) {
            adapter = new XFragmentAdapter(getSupportFragmentManager(), fragmentList, titles);
        }
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public int getOptionsMenuId() {
        return R.menu.menu_main;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_droid:
                AboutActivity.launch(context);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getValue();
    }

    public void getValue() {
        Api.getGankService().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModel<ConfigModel>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseRespModel<ConfigModel> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                SharedPreferencesUtilis.saveBoolIntoPref("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (SharedPreferencesUtilis.getBoolFromPref("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }


}
