package com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuyemian;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.snvhretnsjggdsfd.tughuturtgf.R;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuapi.HaoJJQiaHwfhsnApi;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuu.PreferenceHaoJJQiaHwfhsnOpenUtil;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyushiti.BaseHaoJJQiaHwfhsnModel;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyushiti.ConfigHaoJJQiaHwfhsnEntity;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyusuipian.MainHaoJJQiaHwfhsnFragment;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyusuipian.SetHaoJJQiaHwfhsnFragment;
import com.snvhretnsjggdsfd.tughuturtgf.mvp.XActivity;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuu.HaoJJQiaHwfhsnMyToast;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuu.StatusBarHaoJJQiaHwfhsnUtil;
import com.snvhretnsjggdsfd.tughuturtgf.net.ApiSubscriber;
import com.snvhretnsjggdsfd.tughuturtgf.net.NetError;
import com.snvhretnsjggdsfd.tughuturtgf.net.XApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainHaoJJQiaHwfhsnActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private TabHaoJJQiaHwfhsnAdapter tabAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarHaoJJQiaHwfhsnUtil.setTransparent(this, false);
//        StatusBarHWShanJieBeiYongJinUtil.setLightMode(this);
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.eeerysdrtu);
        tabModel.setSelectedIcon(R.drawable.xcvbnsrtu);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.eeerysdrtu);
        tabModel1.setSelectedIcon(R.drawable.xcvbnsrtu);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.wqetsrty);
        tabModel2.setSelectedIcon(R.drawable.seaeruxfghj);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
//        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new MainHaoJJQiaHwfhsnFragment());
//        fragments.add(new ProductHWShanJieBeiYongJinFragment());
        fragments.add(new SetHaoJJQiaHwfhsnFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new FragmentHaoJJQiaHwfhsnAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    private void initAdapter(){
        if (tabAdapter == null){
            tabAdapter = new TabHaoJJQiaHwfhsnAdapter(R.layout.hao_jjqian_dfjr_uert_hw__adapter_tab, tabModels);
            tabAdapter.setHasStableIds(true);
            tabAdapter.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 2));
            bottomRvy.setAdapter(tabAdapter);
        }
    }

    public void jumpMore(){
        mainViewPager.setCurrentItem(1, false);
        for (int i = 0; i < tabModels.size(); i++){
            if (i == 1){
                tabModels.get(i).setChecked(true);
            } else {
                tabModels.get(i).setChecked(false);
            }
        }
        tabAdapter.notifyDataSetChanged();
    }

    @Override
    public int getLayoutId() {
        return R.layout.hao_jjqian_dfjr_uert_hw_activity_main;
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
                HaoJJQiaHwfhsnMyToast.showShort("再按一次退出程序");
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
        HaoJJQiaHwfhsnApi.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseHaoJJQiaHwfhsnModel<ConfigHaoJJQiaHwfhsnEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseHaoJJQiaHwfhsnModel<ConfigHaoJJQiaHwfhsnEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferenceHaoJJQiaHwfhsnOpenUtil.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (PreferenceHaoJJQiaHwfhsnOpenUtil.getBool("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }



}
