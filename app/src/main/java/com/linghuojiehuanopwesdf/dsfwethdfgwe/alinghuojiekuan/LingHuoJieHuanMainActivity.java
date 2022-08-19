package com.linghuojiehuanopwesdf.dsfwethdfgwe.alinghuojiekuan;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.linghuojiehuanopwesdf.dsfwethdfgwe.R;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.apilinghuojiekuan.HttpLingHuoJieHuanApi;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.flinghuojiekuan.LingHuoJieHuanProductFragment;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.flinghuojiekuan.MainFragmentLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.flinghuojiekuan.SetFragmentLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mlinghuojiekuan.BaseModelLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mlinghuojiekuan.ConfigEntityLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mvp.XActivity;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.net.ApiSubscriber;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.net.NetError;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.net.XApi;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan.MyToastLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan.PreferencesOpenUtilLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan.LingHuoJieHuanStatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LingHuoJieHuanMainActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private TabLingHuoJieHuanAdapter tabAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        LingHuoJieHuanStatusBarUtil.setTransparent(this, false);
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.rdtusaery);
        tabModel.setSelectedIcon(R.drawable.xcvbery);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.xcvbserya);
        tabModel1.setSelectedIcon(R.drawable.bzrdsruy);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.xcvbaerya);
        tabModel2.setSelectedIcon(R.drawable.srtaserya);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new MainFragmentLingHuoJieHuan());
        fragments.add(new LingHuoJieHuanProductFragment());
        fragments.add(new SetFragmentLingHuoJieHuan());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new LingHuoJieHuanFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    private void initAdapter(){
        if (tabAdapter == null){
            tabAdapter = new TabLingHuoJieHuanAdapter(R.layout.adapter_ling_huo_jie_huan_tab, tabModels);
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
        return R.layout.activity_main_ling_huo_jie_huan;
    }

    @Override
    public Object newP() {
        return null;
    }

    public class TabModel{

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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                MyToastLingHuoJieHuan.showShort("再按一次退出程序");
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
        HttpLingHuoJieHuanApi.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelLingHuoJieHuan<ConfigEntityLingHuoJieHuan>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseModelLingHuoJieHuan<ConfigEntityLingHuoJieHuan> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesOpenUtilLingHuoJieHuan.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (PreferencesOpenUtilLingHuoJieHuan.getBool("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }

}
