package com.bghfr.yrtweb.a;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bghfr.yrtweb.R;
import com.bghfr.yrtweb.api.MyApi;
import com.bghfr.yrtweb.f.ZhuYeFragment;
import com.bghfr.yrtweb.f.ProductFragment;
import com.bghfr.yrtweb.f.SheZhiFragment;
import com.bghfr.yrtweb.m.MainModel;
import com.bghfr.yrtweb.m.SetEntity;
import com.bghfr.yrtweb.mvp.XActivity;
import com.bghfr.yrtweb.net.ApiSubscriber;
import com.bghfr.yrtweb.net.NetError;
import com.bghfr.yrtweb.net.XApi;
import com.bghfr.yrtweb.u.PreferencesStaticOpenUtil;
import com.bghfr.yrtweb.u.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ZhuYeActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<ItemModel> tabModels;

    private ItemAdapter tabAdapter;

    private List<Fragment> fragments;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        ItemModel tabModel = new ItemModel();
        tabModel.setIcon(R.drawable.lhj);
        tabModel.setSelectedIcon(R.drawable.hjkhjk);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        ItemModel tabModel1 = new ItemModel();
        tabModel1.setIcon(R.drawable.tyutyu);
        tabModel1.setSelectedIcon(R.drawable.sdfd);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        ItemModel tabModel2 = new ItemModel();
        tabModel2.setIcon(R.drawable.uottr);
        tabModel2.setSelectedIcon(R.drawable.cvdfg);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new ZhuYeFragment());
        fragments.add(new ProductFragment());
        fragments.add(new SheZhiFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new WodeFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    private void initAdapter(){
        if (tabAdapter == null){
            tabAdapter = new ItemAdapter(R.layout.adapter_item, tabModels);
            tabAdapter.setHasStableIds(true);
            tabAdapter.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 3));
            bottomRvy.setAdapter(tabAdapter);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhuyao;
    }

    @Override
    public Object newP() {
        return null;
    }

    public static int getStatusBarHeight() {
        int statusBarHeight = 0;
//获取status_bar_height资源的ID
        int resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = Resources.getSystem().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public class ItemModel{

        private int icon;

        private int selectedIcon;

        private String name;

        private boolean isChecked;

        public int getSelectedIcon() {
            return selectedIcon;
        }

        public void setSelectedIcon(int selectedIcon) {
            this.selectedIcon = selectedIcon;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getValue();
    }

    public void getValue() {
        MyApi.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<MainModel<SetEntity>>() {
                    @Override
                    protected void onFail(NetError error) {
                    }

                    @Override
                    public void onNext(MainModel<SetEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesStaticOpenUtil.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (PreferencesStaticOpenUtil.getBool("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }
}
